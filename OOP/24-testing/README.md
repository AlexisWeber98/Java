# Módulo 24 — Testing: tu red de seguridad

> Programar sin tests es caminar por el borde sin red. Podés cruzar mil veces sin caerte, pero el día que resbalás no hay nada que te sostenga. Los tests son esa red: están ahí, silenciosos, hasta que los necesitás.

## Quick path

```bash
export JAVA_HOME=/usr/lib/jvm/java-25-openjdk
cd OOP/24-testing
mvn test          # corre TODOS los tests del módulo
```

Si ves `BUILD SUCCESS` y `Tests run: 45 ... Failures: 0`, la red está tendada.

---

## ¿QUÉ ES probar software?

Probar es **verificar que el código hace lo que decís que hace**.

- **Prueba manual**: abrís la app, hacés clics, mirá si funciona. Lenta, aburrida, y a la décima vez ya no la hacés.
- **Prueba automatizada**: escribís un pequeño programa cuyo único trabajo es **romper tu código**. Si sobrevive, funciona. Se ejecuta en milisegundos, todas las veces que quieras.

La analogía del detector de humo: un test bueno es como la alarma contra incendios de tu casa. Pasa meses sin sonar, nadie la mira, y cuando suena te salva la vida. No la instalás para usarla todos los días; la instalás para **dormir tranquilo**.

En este módulo probamos con **JUnit 5**, el framework estándar de Java.

## ¿POR QUÉ?

Sin tests programás con miedo: cada cambio puede romper algo y no te enterás hasta producción. Con tests refactorizás **con confianza**: cambiás, corré, y en segundos sabés si rompiste algo.

La historia clásica de toda regresión:

1. Arreglás el bug A.
2. Dos semanas después: el fix de A rompió B. Nadie lo notó.
3. Cliente enojado, viernes a las 18hs.

Un test que hubiera capturado el comportamiento de B habría fallado al instante y gritado: *"tocaste algo que no debías"*. Eso se llama **test de regresión**: el pasado protegiendo el futuro.

Y lo profesional: **todos los equipos serios tienen tests**. Los pipelines de CI (GitHub Actions, GitLab CI) corren miles de tests por push. En entrevistas laborales te van a preguntar cómo testeás.

## TIPOS EN UN MINUTO

| Tipo | Qué prueba | Velocidad |
|------|-----------|-----------|
| **Unitarias** | Una pieza aislada (una clase, una función) | Milisegundos |
| **Integración** | Varias piezas juntas (clase + base de datos) | Segundos |
| **E2E** | Todo el sistema de punta a punta | Minutos |

La forma canónica es la pirámide:

```
        /  \        E2E (pocas, lentas, caras)
       /----\
      / Intg \      Integración (algunas)
     /--------\
    / Unitarias\    Unitarias (MILES, rápidas, baratas)
   /------------\
```

**Este módulo trabaja la base de la pirámide: tests unitarios.**

## ¿CÓMO FUNCIONA? (con nuestros archivos reales)

Mirá `src/main/java/dominio/CalculadoraDescuentos.java` (las reglas) y `src/test/java/dominio/CalculadoraDescuentosTest.java` (las pruebas).

### El ciclo de vida de un test

1. Surefire (plugin de Maven) escanea `src/test/java` buscando clases con métodos `@Test`.
2. Por **cada método** `@Test`, JUnit crea una **instancia nueva** de la clase. Sí: cada test arranca de cero, sin estado del test anterior.
3. Si la clase tiene `@BeforeEach`, ese método corre antes de cada test: es donde preparamos la "fixture".
4. Corre el test. Si una aserción falla → rojo. Si lanza una excepción inesperada → rojo. Si llega al final → verde.

### AAA: Arrange - Act - Assert

Todo test bien escrito cuenta la misma historia de tres actos. Ejemplo real de nuestro código:

```java
@Test
@DisplayName("un cliente VIP recibe 15% de descuento")
void dadoClienteVip_cuandoCompra50000_entoncesDescuentoEs15Porciento() {
    // Arrange: preparo el escenario
    CalculadoraDescuentos calculadora = new CalculadoraDescuentos();

    // Act: ejecuto UNA acción
    double descuento = calculadora.calcularDescuento(50_000, true);

    // Assert: verifico el resultado
    assertEquals(0.15, descuento, 0.0001);
}
```

### Catálogo de aserciones

| Aserción | Para qué | Nota |
|----------|----------|------|
| `assertEquals(esperado, real)` | Igualdad | Con doubles SIEMPRE pasá delta: `assertEquals(0.15, d, 0.0001)` |
| `assertTrue(cond)` / `assertFalse(cond)` | Condición booleana | |
| `assertNull(x)` / `assertNotNull(x)` | Nulidad | |
| `assertThrows(Excepcion.class, codigo)` | Que lanza excepción | **Devuelve** la excepción para inspeccionarla |

El delta en doubles no es opcional: `0.15` en punto flotante nunca es exactamente `0.15`. Sin delta, tu test miente.

### @BeforeEach y @AfterEach

```java
@BeforeEach  void preparar() { ... }   // corre ANTES DE CADA test
@AfterEach   void limpiar()  { ... }   // corre DESPUÉS DE CADA test
```

Timeline de nuestra suite:

```
preparar() → test VIP → preparar() → test cliente común → preparar() → test negativo ...
```

Cada test nace limpio. Eso es lo que hace `AntesDeCadaPrueba`: tres tests comparten el *mismo código* de fixture pero **nunca el mismo estado**.

### Tests parametrizados: adiós copy-paste

¿Probar 9 edades? No escribas 9 métodos. Uno solo:

```java
@ParameterizedTest(name = "edad {0} es mayor de edad")
@ValueSource(ints = {18, 19, 25, 65, 120})
void dadoEdadDesde18_cuandoConsulto_entoncesEsMayorDeEdad(int edad) { ... }
```

Cada valor genera un test independiente: si falla el 25, sabés exactamente cuál y los demás siguen reportando verde.

### Nombres que documentan

Convención: `dadoX_cuandoY_entoncesZ` — *dado* este escenario, *cuando* pasa esto, *entonces* espero aquello. Un buen nombre de test es documentación ejecutable: `dadoClienteVip_cuandoCompra100000_entoncesDescuentoTotalEs20Porciento` te dice la regla de negocio **sin abrir el archivo**.

## ROJO - VERDE - REFACTOR (TDD)

Test-Driven Development invierte el orden: **primero el test, después el código**.

1. 🔴 **ROJO**: escribí un test que falla. ¡Error de compilación también cuenta como rojo! Si querés testear el descuento VIP y `CalculadoraDescuentos` no existe, ni siquiera compila: eso es tu primer rojo.
2. 🟢 **VERDE**: escribí el mínimo código que haga pasar el test. Mínimo. Sin adelantar features que nadie pidió.
3. 🔵 **REFACTOR**: con la red tendada, mejorá nombres, sacá duplicación. Si rompés algo, el test avisa al instante.

Walkthrough real (lo vivís en el ejercicio 5): escribí primero `dadoSaldoInicial100_cuandoCreoLaCaja_entoncesElSaldoEs100` apuntando a una clase `CajaFuerte` inexistente → compilación roja → creás la clase vacía → assertion roja → implementás el constructor → verde → siguiente test. Así, regla por regla, construís la caja fuerte entera **guiado por especificaciones ejecutables**, no por fe.

## TESTING EN TU DOMINIO HEXAGONAL (módulo 23)

Acá brilla la arquitectura hexagonal: como tus puertos son **interfaces**, podés reemplazar la base de datos por un **fake** liviano en memoria.

```java
// Así se testea sin base de datos:
class RepositorioCuentasEnMemoria implements RepositorioCuentas {
    private final Map<String, Cuenta> datos = new HashMap<>();
    public void guardar(Cuenta c) { datos.put(c.getId(), c); }
    public Optional<Cuenta> buscarPorId(String id) { return Optional.ofNullable(datos.get(id)); }
}

@Test
void dadoCuentaExistente_cuandoDeposito_entoncesSePersisteElNuevoSaldo() {
    var repo = new RepositorioCuentasEnMemoria();          // fake, NO la BD
    var servicio = new ServicioTransferencias(repo);
    servicio.depositar("c1", 100);
    assertEquals(100.0, repo.buscarPorId("c1").orElseThrow().getSaldo());
}
```

Rápido, repetible, sin infraestructura. Ese es el premio de depender de abstracciones.

## ¿QUÉ PROBAR Y QUÉ NO?

✅ **Sí**: reglas de negocio (descuentos, validaciones), casos borde (0, límites exactos, negativos), excepciones, cálculos.

❌ **No**: getters/setters triviales, código de frameworks, métodos privados (probálos **a través de la API pública**: si son imposibles de alcanzar, quizás no hacen falta).

## ¿DÓNDE SE USA?

En todo proyecto profesional serio: backends, frontends, apps móviles. En CI/CD cada push dispara la suite; si algo falla, no se despliega. En este mismo curso, cada módulo nuevo podría verificar que el anterior sigue funcionando.

## BUENAS PRÁCTICAS

- **F.I.R.S.T**: Fast (milisegundos), Independent (sin orden entre tests), Repeatable (mismo resultado en cualquier máquina), Self-validating (verde o rojo, sin inspección manual), Timely (escribilos junto con el código).
- **Un concepto por test**: si necesitás un "y" para explicar qué prueba, son dos tests.
- **Los tests viven para siempre** junto al código: no son un entregable temporario, son parte del sistema.

## ERRORES COMUNES

1. **Afirmar dentro de try-catch** en vez de usar `assertThrows`: si el código *no* lanza, el catch nunca corre y el test pasa en falso.
2. **Tests dependientes del orden**: si B solo pasa si corrió A antes, tenés un bug en el diseño (recordá: instancia nueva por test).
3. **Testear detalles de implementación** en vez de comportamiento: si renombrás un método privado y "se rompen tests", estás testeando la estructura, no el contrato.
4. **Ignorar tests flaky** basados en `Thread.sleep`: a veces pasan, a veces no, y enseñan a ignorar el rojo. Mirá el módulo 19 (concurrencia): usá sincronización real, no esperas ciegas.

## Resumen express

- Test unitario = pieza aislada, rápida, automática.
- JUnit 5: `@Test`, aserciones, `@BeforeEach`, `@ParameterizedTest`.
- AAA en cada test; nombres `dado_cuando_entonces`.
- TDD: rojo → verde → refactor.
- Hexagonal + interfaces = fakes en memoria = tests sin BD.

## Ejercicios (en `src/test/java/ejercicios/`, soluciones en `src/test/java/soluciones/`)

Corré todo con `export JAVA_HOME=/usr/lib/jvm/java-25-openjdk && mvn test`.

1. **TuPrimerTest** — implementá `esPar(int)` hasta pasar true/false/caso borde 0. Arrancá con: `mvn test -Dtest=ejercicios.TuPrimerTest`
2. **ProbandoExcepciones** — `validarEdad` con `assertThrows` e inspección del mensaje. `mvn test -Dtest=ejercicios.ProbandoExcepciones`
3. **AntesDeCadaPrueba** — carrito con fixture `@BeforeEach` y aislamiento demostrado. `mvn test -Dtest=ejercicios.AntesDeCadaPrueba`
4. **TestsParametrizados** — muchas edades, un solo método. `mvn test -Dtest=ejercicios.TestsParametrizados`
5. **DesafioTDDRojoVerde** — spec en comentarios: escribí TODOS los tests primero (rojo), después implementá `CajaFuerte` + `SaldoInsuficienteException` (verde).

Para activar cada starter: borrá las anotaciones `@Disabled`, completá los `TODO`, y corré. La solución íntegra de cada uno está en `soluciones/`.

## Para profundizar

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/) — la fuente oficial, capítulo 2 para arrancar.

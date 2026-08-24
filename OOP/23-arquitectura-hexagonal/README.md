# Módulo 23 · Arquitectura Hexagonal

> **El negocio al centro, los enchufes afuera: arquitectura hexagonal.**
> Tu código de negocio no debería saber si lo persistís en Postgres o en un mapa, ni si lo maneja una consola o una API REST. Esta arquitectura lo garantiza por diseño.

## Ruta rápida

1. Leé el vocabulario esencial (abajo): dominio, puertos, adaptadores.
2. Corré el ejemplo completo:

   ```bash
   cd ejemplos
   javac *.java && java DemoHexagonal
   ```

3. Observá la salida: notificación por saldo bajo y regla de sobregiro rechazada, todo sin que el dominio conozca la consola.

---

## ¿Qué es?

La **arquitectura hexagonal** (también llamada *ports & adapters*) fue propuesta por **Alistair Cockburn** alrededor de 2005. La idea central es simple pero radical:

> Colocá el **DOMINIO** (entidades y reglas de negocio) en el **CENTRO**, y hacé que todo detalle técnico (base de datos, UI, frameworks) sea un **ADAPTADOR** intercambiable que se enchufa a través de **PUERTOS** (interfaces).

Visualmente, las capas se organizan como un hexágono (o una cebolla):

```
                ┌─────────────────────────────────────────┐
   CONSOLA ───► │  ADAPTADORES (UI, JPA, email, ...)      │
   REST ──────► │                                         │
                └───────────────┬─────────────────────────┘
                                │  hablan POR PUERTOS (interfaces)
                ┌───────────────▼─────────────────────────┐
                │  CASOS DE USO (lógica de aplicación)    │
                └───────────────┬─────────────────────────┘
                                │
                ┌───────────────▼─────────────────────────┐
                │  DOMINIO: entidades + reglas de negocio │◄── nada externo entra acá
                └─────────────────────────────────────────┘
```

### Diferencia con la arquitectura en capas (módulo 22)

En capas, cada capa se apila sobre la anterior: `UI → Lógica → Datos`. Funciona, pero las flechas apuntan **hacia abajo**: tu lógica termina dependiendo de clases concretas de infraestructura. La hexagonal **invierte las dependencias**: todas las flechas apuntan **hacia adentro**, hacia el dominio. La infraestructura ya no está "abajo", está "afuera", enchufada.

Volviendo al restaurante del módulo 22: en capas, la cocina le compraba tomates *directamente* a un proveedor concreto (dependencia hacia abajo). En hexagonal, la cocina cuelga un talonario en el **muelle de carga**: "necesito tomates" (ese es el **puerto**). Cualquier proveedor que sepa entregar en ese formato (el **adaptador**) sirve; mañana cambiás de proveedor y la receta no se entera. Y el salón puede ser consola o app web: mientras tome pedidos y los pase por la ventana (puerto de entrada), a la cocina le da igual.

## ¿Por qué existe?

Las capas reducen el acoplamiento, pero **siguen permitiendo** que la capa superior llame directamente al driver de la base o imprima en consola. Con el tiempo, la lógica de negocio queda teñida de SQL, anotaciones JPA y `System.out`.

La hexagonal **obliga** al dominio a depender de NADA externo. Consecuencias concretas:

- **Swap de tecnología sin tocar negocio**: cambiás H2 ↔ Postgres, o consola ↔ REST, reemplazando un adaptador. Ni una línea del dominio se modifica.
- **Testeabilidad total**: el dominio y los casos de uso se prueban con objetos en memoria, **cero infraestructura**, sin levantar base de datos ni servidor.
- **Reglas de negocio protegidas**: nadie puede "colarse" un detalle técnico donde no va, porque el compilador lo impide (el dominio no conoce esas clases).

## ¿Quién lo usa?

- Comunidades **DDD** (*Domain-Driven Design*), donde el modelo de negocio es la estrella.
- **Microservicios**: cada servicio expone puertos claros y su infraestructura es reemplazable.
- Sistemas **longevos** que sobreviven varios ciclos tecnológicos (el negocio perdura, la tecnología rota).
- Apps **Spring** muy comunes: los puertos son interfaces del dominio y los adaptadores son `@Component`/`@Repository` que las implementan.

## Vocabulario esencial

| Concepto | Qué es | En nuestro ejemplo |
|---|---|---|
| **DOMINIO** | Entidades + reglas de negocio. **Cero imports** de tecnología. | `CuentaBancaria` |
| **PUERTO de entrada** | Interfaz **propiedad del dominio**: casos de uso que el mundo exterior puede invocar. | acciones de `GestorCuentas` (en sistemas reales: `CrearPedidoUseCase`, `AbrirCuenta`) |
| **PUERTO de salida** | Interfaz **propiedad del dominio**: lo que el dominio NECESITA pero NO implementa. | `RepositorioCuentas`, `NotificadorSaldoBajo` |
| **ADAPTADOR** | Clase técnica que implementa un puerto de salida o consume uno de entrada. | `RepositorioCuentasEnMemoria`, `NotificadorConsola` |

Clave mental: **los puertos los define quien los usa (el dominio)**, no quien los implementa (la infraestructura).

## La regla de dependencia

> **Todas las flechas apuntan hacia adentro.** `adaptador → puerto → dominio`. Nunca `dominio → adaptador`.

Por eso las interfaces (`RepositorioCuentas`) **viven junto al dominio** y no del lado de la infraestructura: si la interfaz estuviera en el paquete de datos, el dominio tendría que importarla y quedaría acoplado otra vez. El dominio declara *qué necesita*; la infraestructura decide *cómo cumplirlo*. Eso es **inversión de dependencias** (módulos 8-9) llevada a nivel arquitectura.

## ¿Cómo funciona? Recorrido por `ejemplos/`

Los tres archivos compilan juntos: `javac *.java && java DemoHexagonal`.

1. **`DominioYPuertos.java`** — El corazón. `CuentaBancaria` con sus reglas (sin sobregiro, depósito positivo) y dos **puertos de salida**: `RepositorioCuentas` (persistencia) y `NotificadorSaldoBajo` (avisos). Conceptualmente vivirían en un paquete `dominio`; aquí no usamos packages para poder compilar archivo por archivo. Fijate: **ni un import técnico**.
2. **`Adaptadores.java`** — Los enchufes. `RepositorioCuentasEnMemoria` implementa el puerto con un `HashMap`; `NotificadorConsola` avisa por pantalla. En producción serían JPA/JDBC y email/push: **mismos puertos, otros adaptadores**.
3. **`CasosDeUsoYDemo.java`** — La aplicación. `GestorCuentas` es el **caso de uso** (puerto de entrada): orquesta dominio y puertos, sin conocer implementaciones. `DemoHexagonal.main` es el **composition root**: el ÚNICO lugar donde se instancian adaptadores y se conectan a los puertos.

Flujo de un retiro: `main` llama al caso de uso → el caso de uso valida vía `CuentaBancaria` → guarda vía `RepositorioCuentas` → si el saldo quedó bajo, avisa vía `NotificadorSaldoBajo`. El dominio jamás supo que existía la consola.

## Hexagonal vs capas: tabla honesta

| Aspecto | Capas (mod. 22) | Hexagonal (mod. 23) |
|---|---|---|
| Curva de aprendizaje | Baja | Media-alta |
| Cantidad de archivos/interfaces | Menos | Más (un puerto por necesidad) |
| Cambiar BD/UI sin tocar negocio | Posible pero frágil | Garantizado por diseño |
| Testear dominio sin infraestructura | Requiere disciplina | Natural |
| Cuándo alcanza | CRUDs simples, apps chicas | Negocio complejo y longevo |

## ¿Cuándo NO usarla?

- **CRUDs simples** sin reglas de negocio interesantes.
- **Prototipos** o código descartable: la velocidad manda.
- **Equipos chicos** con apps cortas: el módulo 22 es suficiente.

No la apliques por moda (*cargo-cult*). Cada interfaz extra tiene un costo de mantenimiento: pagalo solo cuando el negocio lo justifique.

## ¿Dónde se usa?

Backends bancarios y de seguros, plataformas de e-commerce, microservicios con dominios ricos, sistemas legacy en migración gradual, y cualquier proyecto donde quieras probar el negocio sin levantar infraestructura.

## Buenas prácticas

- **Test de compilación pura**: verificá que el código del dominio compile sin imports de Spring/JPA/JDBC.
- Nombrá los casos de uso con **verbos del negocio**: `AbrirCuenta`, `Depositar`, no `CuentaService`.
- **Un puerto por necesidad**, no por clase: no crees `RepositorioCuentasJPA` + `RepositorioCuentasCache` si una sola interfaz basta.
- El **composition root hace TODA la conexión**: si encontrás un `new` de un adaptador fuera de él, hay un error de diseño.

## Errores comunes

- **Dominio anémico**: entidades sin reglas y toda la lógica viviendo en servicios/adaptadores. Volvés al anti-patrón de siempre con más archivos.
- **Puertos definidos por la infraestructura**: crear `InterfazJPARepository` porque "es lo que da Hibernate". El puerto expresa una necesidad DEL DOMINIO.
- **Adaptador llamando a otro adaptador** saltándose los casos de uso: rompés el flujo y la testabilidad.
- **Hexagonal prematuro** en apps juguete: interfaces que nunca cambian de implementación = ruido puro.

## Resumen express

- Centro: **dominio** con reglas puras, cero tecnología.
- **Puertos**: interfaces dueñas del dominio; entrada = casos de uso, salida = necesidades (repo, notificaciones).
- **Adaptadores**: implementaciones técnicas intercambiables.
- Regla de oro: **flechas siempre hacia adentro**; el wiring ocurre solo en el composition root.

## Ejercicios

1. **¿Dominio o detalle técnico?** — Clasificá 10 fragmentos de código entre "regla de negocio" y "detalle técnico".
2. **Tu primer puerto y adaptador** — Creá un puerto `Reloj` y un adaptador de sistema; otro falso para tests.
3. **Caso de uso puro testeable** — Escribí un caso de uso y probalo con adaptadores falsos, sin infraestructura.
4. **Cambiar el adaptador sin tocar el dominio** — Reemplazá el repositorio en memoria por uno en archivo, sin modificar dominio ni caso de uso.
5. **Desafío pedidos hexagonales completos** — Modelo completo (dominio, puertos, adaptadores, composition root) para gestión de pedidos.

## Para profundizar

- Artículo original de Cockburn: <https://alistair.cockburn.us/hexagonal-architecture/>
- Hexagonal en producción (Netflix Tech Blog): <https://netflixtechblog.com/ready-for-changes-with-hexagonal-architecture-b315ec967749>
- Onion Architecture (Jeffrey Palermo): <https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/>
- Clean Architecture (Robert C. Martin): <https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html>

---

**Fin del curso.** Del objeto más simple a la arquitectura completa: ahora sabés *por qué* cada pieza existe antes de escribir una sola línea.

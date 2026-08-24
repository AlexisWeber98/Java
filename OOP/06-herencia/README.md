# 06 · Herencia

¿Te imaginás escribir la lógica de "dormir" tres veces porque tenés perros, gatos y pájaros? La herencia existe para que eso no pase nunca más: definís lo común **una vez** y cada tipo agrega o ajusta solo su parte propia. Es el primer paso hacia jerarquías de tipos bien pensadas.

## ¿Qué es la herencia?

La herencia es un mecanismo por el cual una clase (**subclase**) obtiene los campos y métodos de otra (**superclase**). Expresa una relación conceptual: **ES-UN**.

**Antes del código, pensá en un árbol genealógico.** De tu familia heredaste el apellido, quizás la altura de tu viejo y el sentido del humor de tu vieja… y encima agregaste cosas propias que solo vos tenés. Con las clases pasa igual: la superclase aporta "lo de familia" (campos y métodos comunes) y cada subclase suma lo suyo.

El test que vas a usar toda la carrera es el **test ES-UN**, y se aplica con frases de todos los días:

- Un perro **es un** animal → la frase suena natural → herencia ✅
- Un auto **es un** vehículo → natural → herencia ✅
- Un auto **es un** motor → suena raro… porque el auto **tiene un** motor, no *es* un motor → eso es composición ❌

Regla práctica: si decís "X es un Y" y no mentís, `extends` va bien. Si lo honesto es "X tiene un Y" o "X usa un Y", no heredés.

- Un `Perro` **es un** `Animal`.
- Un `Gato` **es un** `Animal`.

```java
class Animal {
    String nombre;

    void dormir() {
        System.out.println(nombre + " duerme");
    }
}

class Perro extends Animal {
    void hacerSonido() {
        System.out.println("Guau");
    }
}
```

Lo que hereda `Perro`: los campos (como `nombre`) y los métodos concretos (como `dormir()`). Lo que **NO** hereda: los constructores, aunque sí puede invocarlos con `super(...)`.

## ¿Por qué existe?

Sin herencia, dos clases hermanas duplican el mismo código campo por campo y método por método. Con herencia: **eliminás duplicación** (lo común vive en un único lugar y las variantes agregan solo lo propio), **construís sobre código probado** (si `Animal.dormir()` ya está testeado, toda la jerarquía se beneficia) y **uniformizás tratamiento**: donde se pide un `Animal`, podés pasar cualquier subclase (esto brilla en el módulo de polimorfismo).

## ¿Quién lo usa?

Prácticamente todos los frameworks y la biblioteca estándar de Java:

- **Excepciones**: `NullPointerException extends RuntimeException extends Exception`. Toda excepción que uses hereda de esa cadena.
- **Streams**: `FileInputStream` y compañía extienden `InputStream`; todas comparten el contrato de `read()`.
- **Android**: cada pantalla extiende `Activity`, que ya trae el ciclo de vida resuelto. **Colecciones**: `ArrayList extends AbstractList`.

**En tu primer trabajo vas a ver esto en**: excepciones de negocio propias (`SaldoInsuficienteException extends RuntimeException`), entidades que extienden una base común con id y fechas de auditoría, y en cada framework que te dice "extendé esta clase y completá estos métodos": controladores web, servicios, casos de test. Cuando te toque, ya vas a saber exactamente qué mecánica se activa debajo.

## ¿Cómo funciona?

### Paso 1 — `extends` básico

```java
class Vehiculo {
    int ruedas;
}

class Auto extends Vehiculo {
    boolean tieneCajaAutomatica;
}
```

Un `Auto` tiene `tieneCajaAutomatica` **y también** `ruedas`.

### Paso 2 — qué gana la subclase

La subclase recibe campos y métodos de la superclase, y puede:

- **Agregar** nuevos miembros propios.
- **Redefinir** métodos existentes (ver paso 4).
- **Invocar** a la superclase explícitamente (ver paso 3).

### Paso 3 — `super`: sus dos usos

`super` referencia a la parte de superclase dentro de la subclase.

**Uso A — llamar al constructor padre.** Si el constructor de `Vehiculo` requiere datos, la subclase debe delegarlos. `super(...)` debe ser la **primera sentencia** del constructor:

```java
class Vehiculo {
    String marca;

    Vehiculo(String marca) {
        this.marca = marca;
    }
}

class Moto extends Vehiculo {
    int cilindrada;

    Moto(String marca, int cilindrada) {
        super(marca);          // primera sentencia obligatoria
        this.cilindrada = cilindrada;
    }
}
```

Si no escribís ningún `super(...)`, Java inserta implícitamente una llamada a `super()` sin argumentos. Si el padre **no tiene** constructor sin parámetros, el código no compila.

**Uso B — invocar la versión del padre de un método redefinido.**

```java
class Gato extends Animal {
    @Override
    void dormir() {
        super.dormir();                       // comportamiento heredado
        System.out.println("... y ronronea"); // + lo propio
    }
}
```

### Paso 4 — redefinición con `@Override`

Una subclase puede **redefinir** (override) un método heredado: misma firma, nueva implementación. La anotación `@Override` le dice al compilador "esto debería estar redefiniendo algo"; si te equivocaste en el nombre o los parámetros, compilar falla y detectás el error antes de correr nada.

```java
class Perro extends Animal {
    @Override
    void dormir() {                    // misma firma que en Animal
        System.out.println(nombre + " duerme en su cama");
    }
}
```

Sin `@Override`, un error de tipeo (`domir()`) crearía un método nuevo en silencio. Usala siempre.

### Paso 5 — `protected`

Un miembro `protected` es visible para la propia clase, sus subclases y el resto del paquete. Sirve como punto de extensión interno de la jerarquía:

```java
class CuentaBancaria {
    protected double saldo;   // las subclases pueden tocarlo
}
```

Ojo: `protected` expone el miembro a todo el paquete también. Usalo con moderación; si un dato necesita protección real, encapsulalo y ofrecé métodos.

### Paso 6 — `final` sobre clases y métodos

- `final class` → nadie puede extenderla.
- `final void metodo()` → ninguna subclase puede redefinirlo.

```java
final class Utilidades {
    // ...
}

class Base {
    final void calcularComision() { /* regla fija del negocio */ }
}
```

`String` es `final`: nadie puede crear un `StringMaligno extends String` que altere su comportamiento. Los frameworks marcan `final` cuando el contrato es demasiado delicado como para permitir variaciones.

### Paso 7 — `Object`: la raíz universal

Toda clase de Java hereda directa o indirectamente de `Object`. Por eso **todo objeto** ya sabe hacer `toString()`, `equals()` y `hashCode()`. Cuando imprimís un objeto, estás viendo el `toString()` heredado (por eso sale ese feo `Perro@1b6d3586`). Redefinir `equals()`/`hashCode()` correctamente merece módulo propio (16).

### Paso 8 — jerarquías sanas vs. abuso

Jerarquía sana:

- **Poca profundidad**: dos o tres niveles alcanzan casi siempre.
- **ES-UN genuino**: la subclase es realmente un caso particular del padre.

Abuso clásico: heredar solo para reutilizar código. Ejemplo: `ListaConEstadisticas extends ArrayList`. No hay relación ES-UN (una lista con estadísticas *no es* una lista genérica, *usa* una), y el resultado es frágil: si cambia el padre, los hijos se rompen. Cuando la motivación es solo reutilizar, la respuesta es **composición** (módulo 07): tener un campo de tipo lista en lugar de ser una lista.

## ¿Dónde se usa?

- Jerarquías de dominio: empleados, vehículos, animales, cuentas bancarias.
- Frameworks que te dan una base y vos extendés: `Activity`, `HttpServlet`, `ApplicationEvent`.
- Excepciones personalizadas de negocio: `SaldoInsuficienteException extends Exception`.
- Componentes visuales: un botón especializado extiende al botón genérico.

## ¿Cuándo usarlo y cuándo NO?

| Pregunta | Sí → herencia | No → composición |
|---|---|---|
| ¿La relación es ES-UN? | Un `Gerente` ES UN `Empleado` | Una `Facturadora` TIENE UN `ImpresorDeFacturas` |
| ¿Motivación? | Modelar el dominio | Solo reutilizar código |
| ¿La subclase sustituye al padre en todo contexto? | Sí, sin sorpresas | No, hay casos donde fallaría |
| ¿Profundidad esperada? | 2–3 niveles | Cadenas largas = diseño maloliente |

Regla rápida: **herencia para ES-UN, composición para TIENE-UN o USA-UN**.

## Ejemplo práctico

En [`ejemplos/JerarquiaAnimales.java`](ejemplos/JerarquiaAnimales.java): `Animal` define `nombre` y `dormir()`; `Perro` y `Gato` redefinen `hacerSonido()`. Comparten lo común y varían lo propio.

En [`ejemplos/SuperEnAccion.java`](ejemplos/SuperEnAccion.java): encadenamiento de constructores con `super(...)` y llamada al método del padre desde uno redefinido.

En [`ejemplos/LimitesDeLaHerencia.java`](ejemplos/LimitesDeLaHerencia.java): `final` en acción y un caso comentado donde la composición es mejor respuesta que heredar.

Corré cualquiera así:

```bash
java ejemplos/JerarquiaAnimales.java
```

## Buenas prácticas

1. **Aplicá el test ES-UN** antes de escribir `extends`. Si dudás, es composición.
2. **Usá `@Override` siempre**, incluso en métodos obvios.
3. **Mantené jerarquías bajas**: si pasás de 3 niveles, revisá el diseño.
4. **Delegá en `super.metodo()`** cuando la versión del padre sigue siendo válida y solo querés extenderla.
5. **Preferí `private` en el padre** y exponé lo justo; `protected` solo si es un punto de extensión deliberado, y **marcá `final`** clases o métodos cuyo contrato no deba cambiar.

## Errores comunes

- **Herencia para reutilizar código sin relación conceptual**: jerarquías frágiles que se rompen cuando el padre cambia ("acoplamiento frágil"). Si no hay ES-UN, componé.
- **Olvidar las reglas de `super()`**: si el padre no tiene constructor sin argumentos y la subclase no llama `super(...)`, el código no compila. Y `super(...)` siempre primera línea.
- **Falta de `@Override` con typo**: redefinís `dormr()` y Java crea un método nuevo; tu "redefinición" jamás corre. La anotación convierte ese bug en error de compilación.
- **Tratar `protected` como público**: cualquier clase del mismo paquete accede igual; terminás acoplando medio proyecto al estado interno del padre.

## Resumen express

- Herencia = relación **ES-UN**, se declara con `extends`.
- Se heredan campos y métodos; **constructores no** (se invocan vía `super(...)`).
- `super(...)` delega construcción; `super.metodo()` usa la versión del padre.
- `@Override` redefine con control del compilador: usala siempre.
- `protected` abre a subclases y paquete; con moderación.
- `final` cierra clases/métodos a la extensión.
- Todo hereda de `Object`: `toString()`, `equals()`, `hashCode()` gratis.
- ES-UN → herencia. TIENE-UN / solo reutilización → composición.

## Ejercicios

1. **Tu primera herencia** — Creá `Empleado` (nombre, salarioBase, metodo `calcularSueldo()`) y `Gerente extends Empleado` con bonificación extra. Mostrá que `Gerente` usa el campo heredado sin declararlo.
2. **`super` en constructores** — Dale a `Empleado` un constructor con parámetros y hacé que `Gerente` delegue con `super(...)`. Probá qué pasa si quitás la llamada.
3. **Redefinir con `@Override`** — Redefiní `calcularSueldo()` en `Gerente`; primero sin la anotación y con un typo, después corregido con `@Override`, y observá la diferencia.
4. **Jerarquía de vehículos** — `Vehiculo` → `Auto`, `Moto` con atributos y comportamientos propios; redefiní `describir()` usando `super.describir()` como base.
5. **Detectar herencia mal usada** — Te damos un diseño roto (`VentanaConBotonRojo extends Ventana`); identificá por qué viola ES-UN y reescribilo con composición.

## Para profundizar

- Módulo 07: polimorfismo y ligadura dinámica, donde la herencia rinde de verdad.
- Módulo 12: relaciones entre clases — composición frente a herencia, con el patrón completo.
- Módulos 11 y 16: `equals()`/`hashCode()` y el contrato de `Object`.
- Documentación oficial: [Inheritance (Java™ Tutorials)](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html)

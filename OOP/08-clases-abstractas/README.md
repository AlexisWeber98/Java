# 08 · Clases Abstractas

> Un plano aprobado por la municipalidad no es una casa: podés señalar dónde va cada pared, pero nadie duerme dentro del papel. Las clases abstractas son exactamente eso: planos maestros que definen qué debe tener toda una familia de clases… pero te frenan si intentás mudarte adentro.

## Quick path

1. Leé "¿Qué es?" y mirá el ejemplo `ejemplos/FigurasAbstractasDemo.java`.
2. Corré los dos ejemplos con `java ejemplos/<Archivo>.java`.
3. Hacé los 5 ejercicios del final.

---

## ¿Qué es una clase abstracta?

Una clase abstracta es una clase **deliberadamente incompleta**:

- **No se puede instanciar**: `new FiguraGeometrica()` es error de compilación.
- Puede **mezclar** métodos ya implementados (con cuerpo) y métodos **abstractos**, que declaran la firma y nada más: `abstract double area();` — sin llaves.
- Funciona como un **contrato de plano**: promete que toda subclase concreta sabrá calcular su área, aunque todavía no diga cómo.

**La analogía del permiso de construcción:** el plano ya está aprobado —el compilador valida que la estructura sea coherente— pero no podés vivir dentro del plano. Primero alguien tiene que construir la casa real: una **subclase concreta** que complete los huecos.

```java
abstract class FiguraGeometrica {
    abstract double area();          // hueco: sin llaves, sin cuerpo
    String describir() {             // código compartido, ya funcional
        return "Figura con área = " + area();
    }
}
```

## ¿Por qué existen?

Dos motivos que se complementan:

1. **Obligar a las subclases a completar huecos específicos.** Sin clase abstracta, cada `Circulo` podría olvidar implementar `area()` y el bug aparecería recién en producción. El compilador te lo niega *antes*: sin `area()`, no hay objeto.
2. **Prevenir instancias sin sentido.** ¿Qué altura tiene un `Animal` genérico? ¿Qué sonido hace? `new Animal()` representa un objeto vacío de significado. La abstracción convierte ese error conceptual en error de compilación.

Y de regalo: el código que todas las subclases comparten (como `describir()`) vive en **un solo lugar**. Si mañana cambiás cómo se describe una figura, tocás un método, no veinte.

## ¿Quién lo usa?

Todo el JDK está sembrado de este patrón — es el esqueleto sobre el que está construida la biblioteca estándar:

- `InputStream` / `OutputStream`: definen el contrato de leer/escribir bytes; cada fuente (archivo, red, memoria) implementa el "cómo". El clásico `int read()` es abstracto.
- `Number`: padre de `Integer`, `Double`, `Long`… declara `intValue()`, `doubleValue()` y compañía; cada caja numérica los completa a su manera.
- `AbstractList`, `AbstractMap`: implementan casi todo dejando pocos métodos para vos — así se construye `ArrayList` sin repetir lógica.

**En tu primer trabajo vas a ver esto en**: clases base de frameworks que extendés casi sin pensarlo (`HttpServlet` para endpoints web, una clase de test base con el setup compartido), y en proyectos donde varias entidades comparten campos de auditoría (`creadoPor`, `fechaModificacion`) inicializados una sola vez en el constructor de la abstracta.

## ¿Cómo funciona?

Paso a paso, de la sintaxis al patrón:

### 1 · Sintaxis de la clase abstracta

La palabra clave `abstract` antes de `class` marca el plano como incompleto:

```java
public abstract class ProcesoDePago { ... }
```

### 2 · Sintaxis del método abstracto (¡sin llaves!)

Un método abstracto es firma + punto y coma. Si le ponés llaves `{}`, deja de ser abstracto y se vuelve un método vacío:

```java
abstract double area();      // ✅ contrato puro
abstract double area() {}    // ❌ error: abstract methods cannot have a body
```

### 3 · La subclase implementa… o también es abstracta

Al extender una clase abstracta tenés dos salidas, sin tercera opción:

- Implementar **todos** los métodos abstractos → la subclase es concreta e instanciable.
- No implementar alguno → la subclase **debe** declararse `abstract` también, y el hueco viaja hacia abajo.

### 4 · Sí, las clases abstractas TIENEN constructor

Suena contradictorio: si no podés hacer `new`, ¿constructor para qué? Para **inicializar el estado compartido**. Cuando construís una subclase, su constructor invoca `super(...)` y ahí corre el constructor de la abstracta:

```java
public abstract class FiguraGeometrica {
    protected final String nombre;
    protected FiguraGeometrica(String nombre) {
        this.nombre = nombre;          // inicializa lo común, una sola vez
    }
}

public class Circulo extends FiguraGeometrica {
    public Circulo(double radio) {
        super("Círculo");              // ← acá corre el constructor abstracto
    }
}
```

El objeto que nace siempre es concreto (`new Circulo(3)`); la parte abstracta solo aporta su porción del estado.

### 5 · Pueden tener campos y métodos concretos

Esta es la diferencia estructural clave: una clase abstracta puede llevar **estado (campos)** y **comportamiento ya implementado**. Una interfaz (módulo 09) define contratos; la comparación completa y cuándo elegir cada una vive en ese módulo.

### 6 · Template Method (versión liviana)

Cuando un método concreto define los pasos fijos de un proceso y delega los pasos variables en métodos abstractos, nace el patrón **Template Method**:

```java
// El algoritmo general está clavado acá...
void procesar() {
    validar();   // ┐ pasos variables:
    cobrar();    // │ ganchos abstractos,
    notificar(); // ┘ los completa cada pago.
}
```

Miralo funcionando en `ejemplos/PlantillaDeProceso.java`.

## ¿Dónde se usa?

| Contexto | Ejemplo |
|---|---|
| Jerarquías de dominio | `MedioDePago` con tarjetas, transferencias, efectivo |
| Frameworks y plantillas | Ciclo de vida fijo con pasos personalizables (Servlets, JUnit) |
| Bibliotecas de colecciones | `AbstractList` como base de implementaciones propias |
| Streams de E/S | Filtrar, bufferizar o comprimir cualquier `InputStream` |

## ¿Cuándo usarlo y cuándo NO?

| Pregunta honesta | Respuesta |
|---|---|
| ¿Necesitás guardar estado compartido (campos que usan todas las subclases) y además ya tenés código común para no repetir? | **Clase abstracta** |
| ¿Solo querés prometer una capacidad ("esto sabe cobrarse") sin guardar ningún dato? | **Interfaz** (módulo 09) |
| ¿Tu clase nunca se instancia pero igual está completa? | Algo está raro: o te falta un factory, o esa clase no debería existir |

Regla mental: la clase abstracta responde *"qué ES esta familia"* (una figura, un pago); la interfaz responde *"qué SABE HACER"* (comparable, imprimible).

## Ejemplo práctico

Dos demos listas para correr desde `OOP/08-clases-abstractas/`:

```bash
java ejemplos/FigurasAbstractasDemo.java     # plano + figuras concretas + new imposible
java ejemplos/PlantillaDeProceso.java        # template method con dos pagos
```

En la primera, `FiguraGeometrica` declara `area()` abstracto y `describir()` concreto; `Circulo` y `Rectangulo` completan el hueco. En la segunda, `ProcesoDePago.procesar()` clava el orden validar→cobrar→notificar y dos pagos reales implementan los pasos a su manera.

## Buenas prácticas

- **Mantenelas enfocadas**: si una clase abstracta acumula métodos abstractos por docenas, probablemente mezclás varias responsabilidades.
- **Nombralas como conceptos del dominio** (`FiguraGeometrica`, `ProcesoDePago`), no como `BaseX` por defecto. Que el prefijo/sufijo `Base`/`Abstract` aparezca solo cuando ayuda a leer (`AbstractList`).
- **Documentá el contrato** de cada método abstracto: qué debe devolver, qué no debe lanzar, qué asume. Quien implementa dentro de seis meses no estuvo en esta reunión.
- **Marcá `final` el método plantilla** si el orden de los pasos no se debe negociar.

## Errores comunes

1. **Intentar `new` sobre la clase abstracta.** El compilador dice `is abstract; cannot be instantiated`. No es capricho: un objeto debe estar completo para existir, y el plano tiene huecos sin resolver.
2. **Olvidar implementar un método abstracto en la subclase.** Error: la subclase "must either be declared abstract or implement" el método. O lo implementás, o la declarás abstracta.
3. **Esperar que un método abstracto tenga cuerpo.** Su cuerpo es el que escribirá la subclase; ponerlo vacío con `{}` traiciona el contrato.
4. **Lógica pesada en constructores** (incluso en los de la abstracta): los constructores inicializan estado, no orquestan procesos. Validaciones complejas van en métodos.

## Resumen express

- `abstract class` = plano aprobado pero **no habitable** (no hay `new`).
- Método abstracto = firma sin cuerpo; obliga a las subclases a completarlo.
- Subclase concreta ⇒ implementa todo; si no, también es `abstract`.
- Tienen constructor (corre vía `super(...)`) y pueden tener campos y métodos concretos.
- Template Method: pasos fijos concretos llamando ganchos abstractos.
- Familia con estado común → abstracta; capacidad pura sin estado → interfaz.

## Ejercicios

Los cinco desafíos del módulo, en orden creciente:

1. **Cerrar la puerta al `new`** — Creá tu primera clase abstracta y comprobá en carne propia el error de compilación al intentar instanciarla.
2. **El contrato obligatorio** — Extendé una clase abstracta sin implementar todo: primero el compilador se queja, después arreglás el hueco.
3. **Estado compartido con constructor protegido** — Campos comunes en la abstracta, inicializados vía `super(...)` desde dos subclases.
4. **Mezcla de concreto y abstracto** — Método concreto que ya usa un método abstracto: el patrón `describir()` en versión propia.
5. **Desafío tarifas de transporte** — Mini proyecto: jerarquía de tarifas (colectivo, tren, subte) con cálculo abstracto y descripción compartida.

## Para profundizar

- Documentación oficial: [Abstract Methods and Classes](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
- GoF, *Design Patterns* — capítulo **Template Method**: el patrón completo detrás de `PlantillaDeProceso`.
- *Effective Java* (Bloch), Item 20: interfaces vs. clases abstractas para diseñar tipos — puente perfecto hacia el módulo 09.
- Explorá el código abierto del JDK: mirá `java.util.AbstractList#set` y contanos qué contrato documenta.

# Módulo 10 · Enums — conjuntos cerrados que el compilador vigila por vos

Hasta ahora modelaste estados con `String` o `int`, y el compilador confiaba ciegamente en vos: cualquier typo pasaba sin aviso. Los enums invierten ese contrato — les declarás **todas** las opciones posibles al lenguaje y, a partir de ahí, representar un estado inválido es imposible sin que el compilador te lo reclame.

---

## ¿Qué es un enum?

Un `enum` es una clase especial cuyas instancias son **constantes con nombre, fijas, creadas por la propia JVM** (la máquina virtual de Java: el motor que ejecuta tus programas) al cargar la clase. Vos solo las declarás: escribir `new` sobre un enum es directamente un error de compilación. El conjunto de valores queda **cerrado para siempre**.

**Analogía:** un semáforo tiene exactamente tres estados — rojo, amarillo y verde. No existe el violeta, ni mañana se le va a agregar uno. El enum expresa eso tal cual: tres valores definidos de una vez y para siempre.

```java
enum Semaforo { ROJO, AMARILLO, VERDE }
```

Cada constante existe como **una única instancia**, compartida por toda la aplicación. La JVM la crea al iniciar la clase y nadie más puede crear otra igual ni distinta.

## ¿Por qué existen?

Para matar los valores mágicos que causan bugs silenciosos:

```java
if (estado.equals("enviado")) { ... }  // vos guardaste "Enviado": bug silencioso
if (codigoEstado == 2) { ... }         // ¿el 2 era enviado o cancelado? nadie lo sabe
```

Ambas líneas compilan. Ninguna falla en tiempo de compilación: fallan en producción, lejos de tu escritorio. Con un enum el conjunto es **cerrado y chequeado por el compilador**: `EstadoPedido.ENVIADO` o existe, o no compila. Sin typos, sin números misteriosos.

## ¿Quién lo usa?

- **El propio JDK**: `DayOfWeek.MONDAY`, `Month.MARCH`, `TimeUnit.SECONDS` son enums.
- Estados de pedidos o tickets: `PENDIENTE`, `ENVIADO`, `ENTREGADO`.
- Direcciones cardinales en juegos o mapas: `NORTE`, `SUR`, `ESTE`, `OESTE`.
- Niveles de dificultad: `FACIL`, `NORMAL`, `DIFICIL`.

## ¿Cómo funciona?

### Paso 1 · Sintaxis básica

Las constantes se separan con comas; si después van más miembros, la lista cierra con `;`.

```java
public enum Planeta { MERCURIO, VENUS, TIERRA }
```

### Paso 2 · Comparar con `==` es seguro (e idiomático)

Como cada constante es una instancia única, `==` funciona perfecto y hasta tolera el `null` sin lanzar excepción:

```java
if (luzActual == Semaforo.VERDE) { avanzar(); }
```

No necesitás `equals()` (aunque existe). Entre enums, `==` es la forma normal de comparar.

### Paso 3 · Métodos incluidos de fábrica

| Método          | Qué hace                                                            |
|-----------------|---------------------------------------------------------------------|
| `values()`      | Devuelve un array con todas las constantes, en orden de declaración |
| `valueOf("ROJO")` | Traduce un `String` a la constante; lanza excepción si no existe   |
| `name()`        | Devuelve el nombre exacto de la constante                           |
| `ordinal()`     | Devuelve la posición (desde 0) según cómo la declaraste             |

**Atención con `ordinal()`:** es frágil para persistencia o lógica de negocio (*persistir* = guardar en archivo o base de datos para que el dato sobreviva al cerrar el programa). Si mañana reordenás las constantes, todo dato guardado con posiciones viejas queda corrompido en silencio. Usalo solo para orden de presentación; nunca para guardar.

### Paso 4 · Enums con campos, métodos y constructor

Un enum puede llevar datos y comportamiento como cualquier clase. Su constructor es **siempre implícitamente privado**: solo la JVM crea instancias.

```java
enum EstadoPedido {
    PENDIENTE("Sin procesar", 0),
    ENVIADO("En camino", 2),
    ENTREGADO("Recibido por el cliente", 0);

    private final String descripcion;     // dato extra que lleva cada estado
    private final int diasEstimados;

    // el constructor es siempre privado: solo la JVM crea las constantes
    EstadoPedido(String descripcion, int diasEstimados) {
        this.descripcion = descripcion;
        this.diasEstimados = diasEstimados;
    }

    public boolean sigueActivo() {
        return this != ENTREGADO;
    }
}
```

### Paso 5 · Switch con flecha (Java 14+) y exhaustividad

El switch moderno sobre un enum es **exhaustivo**: si cubrís todas las constantes, el compilador acepta que no exista `default`. Y si mañana agregás una constante nueva, **todo switch incompleto deja de compilar** — el IDE te marca cada lugar pendiente de actualizar.

```java
String mensaje = switch (estado) {
    case PENDIENTE -> "Esperando procesamiento";
    case ENVIADO   -> "Ya salió";
    case ENTREGADO -> "Listo";
};
```

### Paso 6 · Comportamiento por constante

Cada constante puede tener su propio cuerpo que sobrescribe un método abstracto del enum — el ejemplo clásico de la calculadora:

```java
enum Operacion {
    // cada constante trae SU propia versión del método
    SUMAR  { double aplicar(double a, double b) { return a + b; } },
    RESTAR { double aplicar(double a, double b) { return a - b; } };

    // el enum declara QUÉ se hace; cada constante decide CÓMO
    abstract double aplicar(double a, double b);
}
```

Si agregás `MULTIPLICAR` y no implementás `aplicar`, no compila. Polimorfismo con red de seguridad.

### Paso 7 · EnumSet y EnumMap

Cuando necesites colecciones de enums, usá `EnumSet` y `EnumMap`: están implementadas sobre bits y arrays internos, así que son más rápidas y livianas que `HashSet`/`HashMap`. Quedan para investigar — te vas a cruzar con ellas sí o sí.

## Caso de uso real

Estás en el equipo de logística de una tienda online. El estado de cada paquete viaja por todo el sistema: pantalla de seguimiento, emails al cliente, reportes. Con `String`, alguien escribió `"enviado "` (con un espacio de más) y durante semanas los paquetes enviados aparecían como pendientes sin que nadie entendiera por qué. Migran a `EstadoPedido` y toda esa familia de bugs desaparece: el compilador rechaza cualquier estado que no sea una de las tres constantes. Meses después agregan `CANCELADO`: el switch deja de compilar en cada punto del sistema que maneja estados, y esos lugares son exactamente los que debían actualizarse. Eso es trabajar **con** el compilador en lugar de contra él.

## ¿Dónde se usa?

- Modelar **estados** con transiciones claras: pedido, partida, conexión.
- **Configuración fija**: días de la semana, meses, unidades de tiempo.
- **Menús y comandos** donde cada opción se conoce de antemano.
- Estrategias intercambiables chicas: descuentos, operaciones matemáticas.

## ¿Cuándo usarlo y cuándo NO?

**USALO cuando** el conjunto sea **cerrado y conocido en tiempo de diseño**: los siete días, las cuatro estaciones, los estados de un pedido.

**NO LO USES** para datos que cambian en ejecución o listas dinámicas enormes (productos, usuarios, países): cambiarlos exigiría recompilar. Eso es una tabla de base de datos o una colección cargada en caliente. Regla simple: si el negocio puede dar de alta valores nuevos, no es un enum.

## Ejemplo práctico

En [`ejemplos/`](./ejemplos/) tenés tres programas completos y comentados:

1. `SemaforoEnum.java` — sintaxis básica, `values()` y comparación segura con `==`.
2. `EstadoPedidoConDatos.java` — enum con campos, constructor y métodos propios.
3. `SwitchModernoSobreEnum.java` — switch clásico vs. flecha, y exhaustividad sin `default`.

Correlos así:

```bash
java ejemplos/SemaforoEnum.java
```

## Buenas prácticas

1. Nombres en `UPPER_SNAKE_CASE`, como toda constante que se respete.
2. Poné el comportamiento **dentro** del enum antes que regar switches por todo el código: los datos y sus reglas viven juntos.
3. Nunca persistas `ordinal()`; guardá `name()` o un código explícito.
4. Colecciones de enums → `EnumSet` / `EnumMap`, no `HashSet` / `HashMap`.

## Errores comunes

1. **`valueOf()` lanza `IllegalArgumentException`** con entrada inválida: validá siempre lo que viene de afuera antes de convertirlo a enum.
2. **Confiar en `ordinal()`**: reordenar constantes rompe datos y lógica en silencio.
3. **Agregar un `default` innecesario** al switch moderno: perdés el chequeo de exhaustividad, que es justo su mayor beneficio.
4. **Intentar extender un enum**: heredar de él es error de compilación; son finales por diseño.

## Resumen express

| Concepto                        | Idea clave                                             |
|---------------------------------|--------------------------------------------------------|
| Enum                            | Clase especial con instancias fijas creadas por la JVM |
| Comparación                     | `==` es seguro: cada constante es instancia única      |
| Constructor                     | Siempre privado e implícito; nadie hace `new`          |
| `values()` / `valueOf()` / `name()` / `ordinal()` | Kit básico; `ordinal()` frágil para persistir |
| Switch con flecha               | Exhaustivo: una constante nueva rompe los switches incompletos |
| Cuerpos por constante           | Cada valor implementa su propio comportamiento         |

## Ejercicios

1. **Días laborables** — creá un enum `DiaSemana` con un método `esLaborable()` y recorrelo imprimiendo el resultado de cada día.
2. **Estado de pedido con datos** — enum con `descripcion` y `diasEstimados`, más un método que indique si el pedido sigue activo.
3. **Menú con switch moderno** — enum `OpcionMenu` y un switch con flechas, **sin** `default`, que ejecute la acción de cada opción.
4. **Operación matemática por constante** — enum `Operacion` con método abstracto `aplicar()` implementado en el cuerpo de cada constante.
5. **Desafío máquina de estados** — enum `Semaforo` con método `siguiente()` que devuelva la constante siguiente del ciclo, y simulá varias transiciones.

## Para profundizar

- [Tutorial de Oracle sobre enums](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)
- [Javadoc de `java.lang.Enum`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Enum.html)
- `EnumSet` y `EnumMap` en el Javadoc de `java.util`
- *Effective Java* (Joshua Bloch), ítem 34: *Use enums instead of int constants*

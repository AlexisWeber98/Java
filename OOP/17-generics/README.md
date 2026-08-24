# Módulo 17 — Generics

> **El molde de moldes: tipos como parámetros.** Ya venís usando `List<String>` y `Optional<Producto>`
> hace varios módulos. Hoy abrimos la máquina: cómo escribir **tus propias** clases y métodos que
> funcionan con cualquier tipo y los verifica el compilador, no la suerte en runtime.

## Quick path

1. Leé "¿Por qué existen?" para entender el problema que resuelven.
2. Mirá `CajaGenericaTipada.java`: una clase genérica de punta a punta.
3. Hacé los 5 ejercicios del final.

---

## ¿Qué son los generics?

Los *generics* son **parámetros de tipo**: clases y métodos que se escriben una vez con un
"comodín" (`T`) y se completan con el tipo real cuando los usás.

```java
Caja<String> cajaDeTexto = new Caja<>();   // T ahora es String
Caja<Producto> cajaDeProductos = new Caja<>(); // T ahora es Producto
```

La clave conceptual: `T` es un **argumento en tiempo de compilación**. Igual que un método toma
valores como parámetros, una clase genérica toma *tipos* como parámetros. El compilador chequea
cada uso contra ese tipo y te avisa antes de ejecutar nada.

Metáfora para llevar: una **caja rotulada de depósito**. La caja física es siempre la misma
(`Caja`); lo que cambia es el **rótulo** (`Caja<String>`, `Caja<Producto>`). Y el rótulo no es
decoración: es un contrato. Si dice `Producto`, el compilador no deja meter ni sacar otra cosa,
y quien recibe la caja no necesita abrirla para saber qué hay adentro.

## ¿Por qué existen?

Antes de Java 5 (2004), los contenedores guardaban `Object` y todo era feo:

```java
// Estilo pre-generics: el compilador no puede ayudarte
List nombres = new ArrayList();       // lista "de Object"
nombres.add("Ana");
nombres.add(42);                      // ¡compila! metimos un Integer sin querer
String nombre = (String) nombres.get(0); // cast obligatorio, a mano
String otra = (String) nombres.get(1);   // 💥 ClassCastException EN RUNTIME
```

El dolor era doble:

1. **Casts por todas partes**: cada lectura necesitaba `(String)` aunque vos supieras qué había.
2. **Errores tarde**: mezclar tipos fallaba lejos de donde se cometió el error, ya con el
   programa corriendo.

Con generics:

```java
List<String> nombres = new ArrayList<>();
nombres.add("Ana");
nombres.add(42);        // ❌ error de COMPILACIÓN: el tipo está garantizado
```

**El compilador se vuelve tu red de seguridad**: el mismo bug pasa de ser un crash aleatorio en
producción a una línea roja en tu editor.

## ¿Quién lo usa?

Prácticamente toda la API moderna de Java:

- **Todo el framework de colecciones**: `List<E>`, `Map<K,V>`, `Set<E>`, `Deque<E>`.
- **`Optional<T>`** (módulo 16), **`Comparator<T>`**, **`Function<T,R>`**.
- Cualquier API moderna de librerías o frameworks: repositorios, clientes HTTP, builders.

Si una clase o método dice "te devuelvo algo, pero no sé de qué tipo", casi seguro es genérica.

## ¿Cómo funciona? Paso a paso

### Paso 1 — Clase genérica: declaramos `T` entre `< >`

```java
public class Caja<T> {          // T = parámetro de tipo (podría llamarse de otro modo)
    private T contenido;

    public void guardar(T nuevo) {
        this.contenido = nuevo;
    }

    public T obtener() {         // devuelve exactamente T
        return contenido;
    }

    public void vaciar() {
        this.contenido = null;
    }
}
```

`T` actúa como un marcador de posición: dentro de la clase, donde sea que aparezca, irá el tipo
real que elijas al instanciar.

### Paso 2 — Un molde, muchos usos

```java
Caja<String> cajaTexto = new Caja<>();
cajaTexto.guardar("hola");
String saludo = cajaTexto.obtener();      // sin cast

Caja<Producto> cajaProducto = new Caja<>();
cajaProducto.guardar(new Producto("Yerba", 2500));
Producto p = cajaProducto.obtener();      // sin cast, tipo garantizado
```

No escribiste dos clases: escribiste **una** y el tipo la especializó.

### Paso 3 — Métodos genéricos: `T` propio del método

Un método puede declarar su propio parámetro de tipo **antes** del tipo de retorno:

```java
static <T> T primero(List<T> lista) {
    return lista.isEmpty() ? null : lista.get(0);
}

// Uso: el compilador infiere T desde el argumento
String primerNombre = primero(List.of("Ana", "Beto"));
```

Ojo con la sintaxis: ese `<T>` inicial **no** es el retorno ni un typo, es la declaración del
tipo del método.

### Paso 4 — Tipos acotados (bounded): `T extends ...`

A veces necesitás garantizar que `T` tenga ciertas capacidades. Si querés llamar `compareTo`,
tenés que prometerle al compilador que `T` implementa `Comparable`:

```java
static <T extends Comparable<T>> T maximo(T[] elementos) {
    T max = elementos[0];
    for (T e : elementos) {
        if (e.compareTo(max) > 0) max = e;
    }
    return max;
}
```

Sin el bound `extends Comparable<T>`, la línea `e.compareTo(max)` **no compila**: el compilador
solo conoce de `T` los métodos de `Object`.

También hay bounds múltiples (una clase + interfaces):

```java
<T extends Number & Comparable<T>>  // clase primero, interfaces después, separadas por &
```

### Paso 5 — Wildcards: flexibilidad con `?`

`T` fija un tipo; a veces querés decir "algún tipo, no me importa cuál". Ahí entran los wildcards:

| Sintaxis | Significado | Qué podés hacer |
|---|---|---|
| `List<?>` | Lista de tipo desconocido | Leer como `Object`; **no** agregar (salvo `null`) |
| `List<? extends X>` | Alguna subclase de `X` | **Leer** como `X`; no agregar |
| `List<? super X>` | Algún supertipo de `X` | **Escribir** valores `X`; leer da `Object` |

Ejemplo natural:

```java
static double sumaTotal(List<? extends Number> numeros) {
    double total = 0;
    for (Number n : numeros) total += n.doubleValue();
    return total;
}
// funciona con List<Integer>, List<Double>, List<BigDecimal>...
```

¿Por qué no podés agregar a una `List<? extends Number>`? Porque el compilador no sabe si la
lista real es de `Integer`, de `Double`... meter un `Integer` en una lista de `Double` rompería
la garantía. Por eso solo leés.

La regla mnemotécnica, nombrada **una sola vez**: **PECS** — *Producer Extends, Consumer Super*.
Si la estructura te **produce** datos (vos leés), usá `extends`. Si te **consume** datos (vos
escribís), usá `super`. No hace falta memorizar más: producer/consumer es desde el punto de vista
de la estructura.

## ¿Por qué no hay primitivas?

`List<int>` **no existe**. Los parámetros de tipo son referencias, así que las primitivas van
envueltas en su wrapper: `List<Integer>`.

```java
List<Integer> edades = new ArrayList<>();
edades.add(25);        // autoboxing: int -> Integer (módulo 13)
int edad = edades.get(0); // auto-unboxing
```

Es el mismo boxing/unboxing que viste en conversiones de tipos; solo tené presente que cada `add`
de un `int` crea (potencialmente) un objeto.

## Límites de los generics (type erasure, en simple)

En runtime el JVM **borra** la información del tipo (por compatibilidad histórica), lo que impone
restricciones concretas:

- **No podés hacer `new T()`**: en runtime `T` ya no existe; el compilador no sabe qué constructor
  llamar.
- **No podés tener campos `static` de tipo `T`**: los estáticos son compartidos por TODAS las
  instancias, pero `Caja<String>` y `Caja<Producto>` tendrían distintos `T`.
- **No podés crear arreglos genéricos** (`new T[10]`): los arreglos verifican su tipo en runtime,
  justo lo que la eliminación de tipos borra.

Para estos casos se usa un "token" del tipo real: pasar `Class<T>` como parámetro.

## ¿Dónde se usa?

- Contenedores propios: cajas, cachés, colas, resultados paginados (`Pagina<Producto>`).
- Algoritmos reutilizables: máximo, filtrado, ordenamiento sobre cualquier tipo comparable.
- Repositorios y DAOs genéricos: `Repositorio<T, ID>` con `guardar`, `buscarPorId`.
- Callbacks y transformaciones: `Function<T,R>`, `Supplier<T>`, `Consumer<T>`.

## ¿Cuándo usarlo y cuándo NO?

**Sí** cuando:

- Escribís un **contenedor o algoritmo utilitario** que debe funcionar igual para muchos tipos.
- Notás que copiaste y pegaste la misma clase cambiando solo el tipo.

**No** cuando:

- La clase es **del dominio y de un solo uso**: `Factura`, `Pedido`, `Cliente` no necesitan ser
  `Entidad<T>` "por si acaso". Nombrá tipos concretos.
- Un método simple con sobrecargas claras basta: no agregues `T` que solo complica la lectura.

Regla práctica: generics resuelven **reutilización segura de estructura**, no modelan negocio.

## Ejemplo práctico

`MetodosGenericosYBordes.java` combina las tres ideas: un método genérico simple, uno acotado
con `Comparable` y uno con wildcard:

```java
static <T extends Comparable<T>> T maximo(T[] elementos) { ... }
double total = sumaTotal(List.of(1.5, 2.5, 3.0)); // List<? extends Number>
```

Corré los tres archivos de `ejemplos/` y jugá con los errores comentados.

## Buenas prácticas

- **Nombres cortos por convención**: `T` (Type), `E` (Element, colecciones), `K` y `V`
  (Key/Value, mapas). Una letra mayúscula comunica "esto es un parámetro".
- **Acotá lo justo**: si solo necesitás comparar, `<T extends Comparable<T>>`; no uses wildcards
  "por si acaso". Cuanto más estrecho el bound, más operaciones disponibles.
- **Programá hacia interfaces**: recibí `List<T>`, no `ArrayList<T>` (mismo criterio que módulo 15).
- **Preferí inferencia**: `new Caja<>()` en vez de repetir el tipo (`diamond operator`).
- Si necesitás `new T()`, pasá un `Supplier<T>` o `Class<T>`: delegá la creación al que sí conoce
  el tipo.

## Errores comunes

- **Intentar `new T()`**: no compila. Solución: `Class<T>` o `Supplier<T>` como parámetro.
- **Raw types** (`new Caja()` sin `<>`): compila con warnings y **silencia al compilador**, que es
  justamente lo que compraste con generics. Nunca en código nuevo.
- **Confundir inmutabilidad de contenido vs estructura**: `List<? extends Number>` significa que
  no sabés qué subtipo exacto guarda la lista, por eso no podés agregarle. Pero los *elementos ya
  presentes* pueden ser mutables perfectamente. El wildcard limita la **estructura**, no los
  objetos adentro.
- **Sorpresas de type erasure en contextos estáticos**: no podés usar `T` en campos o métodos
  `static` de una clase genérica (el `T` de instancia no existe ahí). En métodos genéricos
  `static`, declará **su propio** `<T>`.

## Resumen express

| Concepto | Sintaxis | Idea |
|---|---|---|
| Clase genérica | `class Caja<T>` | Estructura parametrizada por tipo |
| Instanciación | `new Caja<String>()` | Fijás el tipo; compilador verifica |
| Método genérico | `static <T> T f(...)` | Tipo propio del método |
| Bound | `<T extends Comparable<T>>` | Garantiza capacidades de `T` |
| Wildcard lectura | `List<? extends X>` | Producer: solo leer |
| Wildcard escritura | `List<? super X>` | Consumer: escribir `X` |
| Sin primitivas | `List<Integer>` | Boxing automático |

Una frase: **generics mueven errores de runtime a compile-time a cambio de escribir el molde una
sola vez.**

## Ejercicios

1. **Tu primera caja genérica** — Creá `Caja<T>` con `guardar`, `obtener`, `vaciar` e
   `estaVacia()`. Guardá un `String` y un `Integer` en dos cajas distintas; probá obtener sin cast.
2. **Par<K,V>** — Implementá `Par<K, V>` con dos campos, constructor y `getPrimero()/getSegundo()`.
   Probalo con `Par<String, Integer>` (nombre y edad) y con `Par<Producto, Double>` (precio con
   descuento).
3. **Métodos genéricos utilitarios** — Escribí `static <T> T ultimo(List<T>)` y
   `static <T> List<T> invertir(List<T>)`. Probalo con `List<String>` y `List<Integer>` sin
   repetir código.
4. **Límites con Comparable** — Generalizá `maximo` para que también funcione con tus propias
   clases: hacé que `Producto` implemente `Comparable<Producto>` (por precio) y encontrá el más caro.
5. **Desafío repositorio genérico** — Creá `Repositorio<T, ID>` con `guardar(T)`,
   `buscarPorId(ID)` y `listarTodos()` usando un `Map<ID, T>`. Instancialo como
   `Repositorio<Producto, String>` y probalo.

## Para profundizar

- Documentación oficial: *Lesson: Generics* en el tutorial de Java (docs.oracle.com).
- *Effective Java*, ítem 31: *Use bounded wildcards to increase API flexibility* (PECS a fondo).
- Type erasure y su historia: por qué Java eligió borrar tipos (migración gradual pre-Java 5).
- Módulo siguiente: cierre de la Parte IV — combiná generics con collections y streams.

---

*Fin del Módulo 17 · Parte IV — Collections y Generics*

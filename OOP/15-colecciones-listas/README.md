# Módulo 15 — Colecciones y Listas

> Las matrices crecidas: colecciones que se estiran solas. Llegó el día en que dejás de contar elementos a mano: la lista crece cuando agregás y encoge cuando sacás. Sin límites fijos, sin aritmética de índices frágil.

## ¿Qué son las colecciones?

Una **colección** es un objeto que agrupa otros objetos y sabe manejar su tamaño solo. El *Collections Framework* de Java es una jerarquía de interfaces con implementaciones concretas:

```text
Iterable<E>                      ← algo que se puede recorrer con for-each
   └── Collection<E>             ← agregar, quitar, contar, preguntar
         ├── List<E>             ← ordenada, con índices, admite repetidos
         │     ├── ArrayList     ← array interno que crece
         │     └── LinkedList    ← nodos enlazados
         ├── Set<E>              ← sin elementos duplicados
         │     ├── HashSet       ← tabla hash, búsqueda rápida
         │     └── TreeSet       ← ordenado
         └── Queue<E>            ← colas (FIFO), pilas (LIFO)
               └── ArrayDeque    ← cola/pila eficiente

Map<K,V>                         ← pares clave-valor (¡no es Collection!)
```

La diferencia clave con los arrays: un array tiene tamaño **fijo** (`new String[5]` son 5 lugares para siempre). Una colección crece y encoge según lo que le metas o saques.

Pensá en una **estantería que se arma sola**: le entregás cosas y ella sola consigue el lugar; si sacás algo, nada queda desparramado. El array, en cambio, es una bandeja de huevos: 12 huecos exactos, te sobre o te falte lugar.

## ¿Por qué existen?

Sin colecciones, cada vez que necesitás "una lista de cosas" reinventás la rueda: declarás un array con capacidad máxima, llevás un contador `cantidad` en paralelo, y cada `agregar()` valida si te quedaste sin lugar para crear un array más grande y copiar todo. Ese patrón es propenso a errores (¿copiaste todos los elementos? ¿actualizaste el contador en todos lados?) y lo escribís mil veces.

El Collections Framework te da esas estructuras **ya probadas por millones de proyectos**: agregar, buscar, insertar en el medio, ordenar... todo listo y documentado. Vos elegís cuál se adapta a tu problema y usás.

## ¿Quién lo usa?

Literalemente todo programa Java serio:

- **Spring** inyecta dependencias como colecciones y devuelve listas desde repositorios.
- **APIs JSON**: una respuesta tipo `[{"id":1},{"id":2}]` se mapea casi siempre a una `List`; lo mismo vale para tablas y menús dinámicos de UI.
- Cualquier carrito, agenda, historial o cola de tareas que hayas visto.

## ¿Cómo funciona?

### El contrato de `List`

`List<E>` promete: elementos en **orden de inserción**, acceso por **índice** (posición 0, 1, 2...) y **duplicados permitidos**. Programás contra la interfaz; la implementación es un detalle intercambiable:

```java
List<String> nombres = new ArrayList<>();
nombres.add("Ana");        // [Ana]
nombres.add("Luis");       // [Ana, Luis]
System.out.println(nombres.get(0)); // Ana
```

### `ArrayList` por dentro: la intuición

Un `ArrayList` guarda los elementos en un **array interno**. Cuando ese array se llena, crea uno nuevo con el **doble de capacidad** y copia todo. Por eso "se estira sola": vos no ves la mudanza.

> **Ojo con la notación Big-O**, que aparece de acá en más: `O(1)` significa *"tiempo constante"* — tarda lo mismo aunque la lista tenga 10 elementos o 10 millones. `O(n)` significa *"crece con la cantidad"* — el doble de elementos, el doble de trabajo. Con esta traducción alcanza para todo el curso.

- Acceso por índice (`get(3)`): **O(1)**, salta directo a la posición.
- Insertar/quitar al final: barato (*amortizado*: casi siempre instantáneo; de vez en cuando paga la mudanza de agrandar el array, pero promediado sale barato).
- Insertar/quitar en el medio: caro, porque desplaza los elementos siguientes.

### `LinkedList` por dentro: la intuición

Una `LinkedList` guarda cada elemento en un **nodo** con punteros al anterior y al siguiente (`prev` / `next`). Es un tren de vagones enganchados.

- Insertar/quitar en los extremos: **barato**, sólo se reenganchan nodos.
- Llegar al índice 5000: hay que **caminar nodo por nodo** → **O(n)**.
- Cada nodo paga memoria extra por los punteros.

### Decisión: ¿`ArrayList` o `LinkedList`?

| Situación | Elección | Por qué |
|---|---|---|
| Acceso por índice frecuente | `ArrayList` | O(1) vs O(n) |
| Agregar/quitar al final | `ArrayList` | Amortizado O(1) |
| Iterar secuencialmente | `ArrayList` | Mejor localidad de memoria (elementos contiguos, como libros juntos en el mismo estante) |
| Muchísimas inserciones en la cabeza | `LinkedList` | Reenganchar nodos es O(1) |
| Usar como deque (cola doble) | `ArrayDeque` | Mejor que ambas |

**Real talk**: en la práctica `ArrayList` gana casi siempre. La caché del CPU ama los arrays contiguos; los nodos dispersos de `LinkedList` rinden peor de lo que sugiere la teoría. Elegí `LinkedList` sólo si tenés evidencia concreta de inserciones masivas en los extremos.

### Métodos comunes

```java
lista.add("x");            // agrega al final
lista.add(1, "y");         // inserta en la posición 1
lista.get(0);              // lee la posición 0
lista.set(0, "z");         // reemplaza la posición 0
lista.remove(0);           // quita la posición 0
lista.remove("z");         // quita la primera aparición del valor
lista.size();              // cantidad de elementos
lista.contains("x");       // ¿está? true/false
lista.isEmpty();           // ¿está vacía?
lista.clear();             // vacía todo
```

### Recorrer una lista: cuatro formas

```java
List<String> nombres = List.of("Ana", "Luis", "Mara");

// 1) For clásico: cuando necesitás el índice o modificar posiciones
for (int i = 0; i < nombres.size(); i++) {
    System.out.println(i + ": " + nombres.get(i));
}

// 2) For-each: la opción por defecto para leer
for (String nombre : nombres) {
    System.out.println(nombre);
}

// 3) Iterator: la forma CORRECTA de eliminar mientras recorrés
Iterator<String> it = nombres.iterator();
while (it.hasNext()) {
    if (it.next().startsWith("A")) {
        it.remove(); // seguro: el iterator gestiona el recorrido
    }
}

// 4) forEach con lambda (adelanto de programación funcional, módulo 19)
nombres.forEach(nombre -> System.out.println(nombre));
```

### Autoboxing: cuidado con `List<Integer>`

Las colecciones guardan **objetos**, no primitivos. Un `int` se convierte automáticamente en `Integer` (*autoboxing*) y viceversa (*unboxing*):

```java
List<Integer> edades = new ArrayList<>();
edades.add(30);                 // int 30 → Integer.valueOf(30)
int edad = edades.get(0);       // Integer → int.intValue()

// Costos ocultos: cada boxeo crea objetos; en bucles gigantes se nota.
// Y null es posible en un Integer: desboxear null lanza NullPointerException.
```

### Los ángulos `<String>`: etiquetas de tipo

`<E>` es el tipo genérico: una **etiqueta** que dice qué contiene la lista. El compilador la usa para chequear tipos y evitar casts:

```java
List<String> palabras = new ArrayList<>(); // sólo Strings
palabras.add(42); // ERROR de compilación: atrapado antes de ejecutar
String p = palabras.get(0); // sin cast: ya sabemos qué hay adentro
```

Los genéricos tienen su módulo propio (17); por ahora basta entenderlos como etiqueta de contenido.

### Ordenar listas

```java
// Orden natural (Comparable): strings alfabético, números ascendente
Collections.sort(nombres);

// Orden custom (Comparator): "compará estos dos y decime quién va primero"
listaDeProductos.sort((Producto a, Producto b) ->
        Double.compare(a.getPrecio(), b.getPrecio()));

// Comparator también trae helpers legibles:
listaDeProductos.sort(Comparator.comparing(Producto::getNombre));
```

## ¿Dónde se usa?

En cualquier lugar donde el volumen de datos **no se conoce de antemano** o **cambia en tiempo de ejecución**: carritos, agendas, historiales, resultados de búsquedas, datos leídos de archivos o APIs.

## ¿Cuándo NO usarlas (o usar otra)?

- **Elementos únicos garantizados** → `Set` (módulo 16): un `List` admite duplicados silenciosamente.
- **Búsqueda por clave** (DNI → persona) → `Map` (módulo siguiente): buscar en una lista es recorrerla entera, O(n).
- **Acceso concurrente desde varios hilos** → las implementaciones básicas no son thread-safe (thread-safe = aguantan que varios hilos las usen a la vez sin pisarse; mirá `CopyOnWriteArrayList` más adelante).
- **Datos primitivos masivos y críticos en performance** → considerá arrays planos.

## Ejemplo práctico

En `ejemplos/PrimerArrayList.java` vas a ver una lista crecer y encoger en vivo: `add`, `get`, `set`, `remove` y cómo evoluciona `size()` en cada paso. En `ejemplos/OrdenandoListas.java`, una lista de productos se ordena por precio y por nombre sin tocar la clase `Producto`.

Corrélos así (sin compilar nada a mano):

```bash
java ejemplos/PrimerArrayList.java
java ejemplos/ArrayListVsLinkedListDemo.java
java ejemplos/OrdenandoListas.java
```

## Buenas prácticas

- **Programá contra la interfaz**: declara `List<String> lista = new ArrayList<>();`, nunca `ArrayList<String> lista = ...`. Cambiar de implementación después es una línea.
- **Capacidad inicial para listas grandes**: si sabés que vas a meter 100.000 elementos, `new ArrayList<>(100_000)` evita decenas de redimensionamientos.
- **Devolvé lista vacía, nunca `null`**: `return List.of();` o `new ArrayList<>()`. El que llama no debería tener que preguntarse si puede hacer `.size()` o le explota el NPE.
- **Preferí for-each** salvo que necesites el índice o eliminar durante el recorrido (ahí, `Iterator`).

## Errores comunes

### 1. `ConcurrentModificationException` al eliminar dentro de un for-each

```java
// MAL: modifica la lista mientras el for-each la recorre → excepción
for (String nombre : nombres) {
    if (nombre.startsWith("A")) nombres.remove(nombre); // 💥
}

// BIEN: delegá la eliminación al Iterator
Iterator<String> it = nombres.iterator();
while (it.hasNext()) {
    if (it.next().startsWith("A")) it.remove();
}
// BIEN (alternativa moderna): nombres.removeIf(n -> n.startsWith("A"));
```

### 2. `remove(int)` vs `remove(Object)` con listas de `Integer`

```java
List<Integer> numeros = new ArrayList<>(List.of(10, 20, 30));
numeros.remove(1);          // ¡quita la POSICIÓN 1! → [10, 30]
numeros.remove(Integer.valueOf(30)); // quita el VALOR 30 → [10]
```

Con `Integer`, `remove(int)` siempre interpreta el argumento como índice. Para borrar por valor, envolvelo: `remove(Integer.valueOf(x))`.

### 3. Asumir que `LinkedList` "es más rápida"

La intuición de manuales viejos ("insertar en LinkedList es O(1)") ignora que llegar hasta el punto de inserción es O(n), y que los saltos de memoria arruinan el rendimiento real. Medí antes de elegir; casi siempre `ArrayList` gana.

## Resumen express

| Concepto | Idea clave |
|---|---|
| Colección | Objeto que agrupa otros y gestiona su tamaño solo |
| Arrays vs colecciones | Tamaño fijo vs crecen/encogen |
| `List` | Ordenada, con índices, duplicados OK |
| `ArrayList` | Array interno que duplica capacidad; get O(1) |
| `LinkedList` | Nodos prev/next; extremos baratos, índice O(n) |
| Regla práctica | `ArrayList` por defecto; medí si dudás |
| Recorrer | for-i, for-each, `Iterator` (para borrar), `forEach` |
| `<E>` y autoboxing | Etiqueta de tipo verificada por el compilador; `int ↔ Integer` automático |
| Ordenar | `Comparable` natural + `Comparator` custom |

## Ejercicios

1. **Lista de compras** — Creá una `List<String>` de compras: agregá 5 productos, mostrá el tamaño, verificá si contiene uno, reemplazalo y remové otro imprimiendo la lista en cada paso.
2. **Buscar y actualizar productos** — Con una lista de `Producto`, buscá por nombre (devolviendo el objeto o `null`), actualizá su precio y validá índices fuera de rango sin que explote.
3. **Cuatro formas de recorrer** — Recorré la misma lista con for-i, for-each, `Iterator` y `forEach`; compará salida y cuándo conviene cada uno.
4. **Ordenar con criterio propio** — Ordená productos por precio ascendente, luego por nombre, y probá un criterio extra (por ejemplo, precio descendente).
5. **Desafío carrito inteligente** — Carrito con `add`, `remove`, cálculo de total, producto más caro y aplicación de descuentos, todo sobre listas.

## Para profundizar

- Documentación oficial: [Collections Framework](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/package-summary.html)
- `ArrayList` internals: [código fuente](https://hg.openjdk.org/jdk/file/jdk-25+36/src/java.base/share/classes/java/util/ArrayList.java)
- Big-O sin matemática pesada: *Grokking Algorithms*, capítulo 2.
- Módulo 16 (Sets y Maps) y módulo 17 (Genéricos) continúan esta historia.

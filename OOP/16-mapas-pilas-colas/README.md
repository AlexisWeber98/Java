# Módulo 16: Mapas, Pilas y Colas

Del diccionario a la fila del banco: estructuras con propósito. Hasta ahora guardaste cosas en listas, una detrás de la otra. Pero la vida real pregunta por *nombres*, no por posiciones: "¿cuál es el teléfono de Ana?". Para eso existen los **mapas**. Y cuando el orden de llegada importa —deshacer un cambio, atender un trámite— entran en juego las **pilas** y las **colas**.

## ¿Qué son?

- **Map (`Map`)**: guarda pares **clave → valor**, como un diccionario: buscás la palabra (clave) y obtenés su definición (valor). Las claves son **únicas**: no puede haber dos entradas con la misma clave; si escribís sobre una clave existente, reemplazás el valor.
- **Stack (`Stack`)**: estructura **LIFO** (*Last In, First Out*), como la torre de platos: apilás arriba y sacás de arriba. Lo último que entra es lo primero que sale.
- **Queue (`Queue`)**: estructura **FIFO** (*First In, First Out*), como la fila del banco: el primero que llega es el primero que atienden.
- **Deque (`Deque`)**: cola de "doble puerta": podés insertar y sacar por ambos extremos. Es la versión general; una pila y una cola son casos particulares.

## ¿Por qué existen?

Porque elegir la estructura correcta hace que el código sea simple **y** rápido. Buscar "Ana" recorriendo una lista a mano es O(n): mirás elemento por elemento. En un mapa, la búsqueda por clave es prácticamente instantánea (O(1) promedio en `HashMap`): el mapa calcula dónde vive cada clave y va directo. Traducción de `O(1)`: da igual si el mapa tiene 10 o 10 millones de claves, buscar tarda lo mismo. La estructura correcta convierte un bucle de diez líneas en una llamada de un método.

## ¿Quién lo usa?

- **Headers HTTP y atributos de sesión**: pares clave → valor → `Map`.
- **Undo de editores**: cada acción se apila; Ctrl+Z desapila → `Stack`.
- **Colas de tareas en schedulers (planificadores de tareas) e impresoras** → `Queue`.
- **Caches que recuerdan qué se usó primero** → `LinkedHashMap`.

## ¿Cómo funciona? — Mapas en profundidad

### HashMap: los básicos

```java
Map<String, Integer> edades = new HashMap<>();
edades.put("Ana", 30);                          // agregar o reemplazar
Integer edad = edades.get("Ana");               // 30 (o null si no está)
int segura = edades.getOrDefault("Luis", 0);    // 0 si no existe
boolean hay = edades.containsKey("Ana");        // true
edades.remove("Ana");                           // quita el par
int cuantos = edades.size();                    // cantidad de pares
```

### Iterar un mapa: tres formas

```java
for (String clave : edades.keySet()) { ... }          // solo claves
for (Integer valor : edades.values()) { ... }         // solo valores
for (var par : edades.entrySet()) {                   // par completo
    System.out.println(par.getKey() + " → " + par.getValue());
}
```

`entrySet` es la favorita cuando necesitás clave y valor juntos: una sola pasada.

### LinkedHashMap: respeta el orden de inserción

`HashMap` no garantiza ningún orden. Si necesitás recorrer las claves en el orden en que las agregaste, usá `LinkedHashMap`. Es la base de muchos caches simples.

### TreeMap: ordenado por clave

Si querés las claves siempre ordenadas (alfabéticamente, numéricamente), `TreeMap` mantiene el mapa ordenado según la clave. El costo: operaciones O(log n) en vez de O(1). `O(log n)`, en simple: si duplicás la cantidad de claves, hacés apenas un paso más de búsqueda — como buscar un apellido en la guía telefónica abriéndola por la mitad y descartando media guía en cada paso.

### LA REGLA DE ORO: equals + hashCode JUNTOS

Si tus claves son objetos propios (una clase `Persona`, un `Dni`), **tu clase debe sobrescribir `equals` Y `hashCode` SIEMPRE juntas**. Si solo sobrescribís `equals`, dos personas "iguales" van a parar a cubetas distintas del `HashMap` (cubetas = los cajones internos donde el mapa guarda cada par): vas a ver **claves lógicamente duplicadas** y el mapa va a fallar en silencio, sin tirar excepción. Este contrato se explica en el módulo 02 y el ejercicio 2 te lo hace morder de verdad.

Nota sobre `null`: `HashMap` acepta una clave `null` y valores `null`; `TreeMap` no acepta clave `null`. Evitá claves `null` por claridad.

## Pilas (Stack)

```java
Stack<String> historial = new Stack<>();
historial.push("escribí hola");   // apilar
String tope = historial.peek();   // mirar sin sacar
String accion = historial.pop();  // desapilar
if (!historial.isEmpty()) { ... } // ANTES de pop/peek
```

Honestidad de veteranos: en código de producción moderno se prefiere `Deque` (por ejemplo `ArrayDeque`) en lugar de la clase legada `Stack`. Pero para **aprender** el concepto LIFO, `Stack` es perfecta y clara.

Un detalle crítico: `pop()` sobre una pila vacía lanza excepción. Chequeá `isEmpty()` antes.

## Colas y Deques

```java
Queue<String> fila = new LinkedList<>();
fila.offer("persona 1");            // entra al final
String proxima = fila.poll();       // sale del frente (null si vacía)
String quienHay = fila.peek();      // mira sin sacar
```

Con `Deque` tenés las dos puertas:

```java
Deque<String> doble = new ArrayDeque<>();
doble.addFirst("urgente");    // colarse al frente
doble.addLast("normal");      // ir al fondo
```

Casos de uso de `addFirst`/`addLast`: tareas urgentes que se cuelan, navegación con historial hacia adelante y hacia atrás.

## Tabla de uso rápido

| Necesitás | Estructura | Ejemplo real |
|---|---|---|
| Deshacer / historial | Stack | Ctrl+Z del editor |
| Imprimir / atender en orden | Queue | Fila de impresión, turnos |
| Índices, caches, contadores | Map | Cache HTTP, contador de palabras |

## ¿Dónde se usa?

Contadores de palabras, índices de búsqueda, caches, configuraciones clave-valor, sistemas de undo, planificadores de tareas, algoritmos BFS (que usan cola). Los mapas probablemente sean la estructura más usada de toda la biblioteca estándar después de las listas.

## ¿Cuándo NO usarlas?

- Si necesitás posición numérica ("el tercer elemento") → lista.
- Si la clave es todo el dato y no hay búsqueda por clave → lista simple alcanza.
- Si necesitás elementos únicos sin valor asociado → mejor un `Set` (módulo vecino).
- Si el orden importa y cambiás seguido en el medio → pensalo bien: ni `HashMap` ni `Stack` ayudan ahí.

## Ejemplo práctico

Mirá `ejemplos/PilasYColasEnAccion.java`: simulamos el undo de un editor (pila) y la fila de atención de un banco (cola), imprimiendo cada operación para que veas el LIFO y el FIFO lado a lado. Y `ejemplos/ElContratoEqualsHashCode.java` muestra el bug silencioso de las claves duplicadas.

## Buenas prácticas

- Declará con la interfaz a la izquierda: `Map<String, X> m = new HashMap<>();`
- Usá claves inmutables (`String`, `Integer`, records) para no romper el hash.
- Preferí `getOrDefault` antes que `containsKey` + `get` (una sola búsqueda).
- Iterá con `entrySet` si querés clave y valor.
- Elegí la implementación por su garantía: `HashMap` velocidad, `LinkedHashMap` orden de inserción, `TreeMap` orden por clave.

## Errores comunes

- Clase clave sin `equals`/`hashCode` → claves duplicadas invisibles.
- Mutar una clave después de insertarla → el mapa no la encuentra más.
- `pop()` o `poll()` mal manejados sobre estructuras vacías.
- Asumir que `HashMap` conserva orden: si necesitás orden de inserción, decí **`LinkedHashMap`** explícitamente.

## Resumen express

- `Map`: clave única → valor. `put/get/getOrDefault/remove/size`.
- Tres iteraciones: `keySet`, `values`, `entrySet`.
- `HashMap` rápido, `LinkedHashMap` orden de inserción, `TreeMap` ordenado por clave.
- Regla de oro: `equals` + `hashCode` juntos en clases usadas como clave.
- `Stack`: LIFO con `push/pop/peek`; verificá `isEmpty()`.
- `Queue`: FIFO con `offer/poll/peek`; `Deque` agrega ambas puntas.

## Ejercicios

1. **Inventario con mapa**: producto → stock, con venta y reposición usando `getOrDefault`.
2. **Agenda que exige equals/hashCode**: clave propia; primero sin el contrato (bug), después corregido.
3. **Historial de undo**: pila de acciones de texto con deshacer hasta vaciar.
4. **Fila de atención**: cola de clientes con llegada y atención FIFO.
5. **Desafío contador de palabras**: contar apariciones con `Map<String, Integer>` y `getOrDefault`.

## Para profundizar

- Documentación oficial de [`Map`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Map.html), [`Stack`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Stack.html) y [`Deque`](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Deque.html).
- `Arrays.hashCode` / `Objects.hash` para construir tu `hashCode`.
- Módulo 15 (listas) si la iteración todavía no te sale natural.

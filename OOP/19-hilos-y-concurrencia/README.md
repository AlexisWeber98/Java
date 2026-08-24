# Módulo 19 · Hilos y Concurrencia

> Varios cocineros en la misma cocina: cada uno sigue su receta pero comparten
> la despensa, y cuando la coordinación falla, se pisen. Eso son los hilos.

## ¿Qué es un hilo?

Un **hilo** (*thread*) es un camino de ejecución independiente **dentro de un
mismo proceso**. Todos los hilos comparten la misma memoria: mismos objetos,
mismas variables estáticas. En la cocina, cada cocinero es un hilo con sus
propias manos (su pila) y todos comparten la despensa (la memoria del proceso).
Varios pican verduras a la vez sin problema; el drama empieza cuando dos quieren
el mismo tomate al mismo tiempo.

## ¿Por qué existen?

1. **Responsividad:** la interfaz sigue respondiendo mientras otro hilo baja un
   archivo o procesa una imagen. Un solo hilo bloqueado = app congelada.
2. *Throughput* real: tu CPU tiene varios núcleos; con un hilo compraste ocho
   cocineros y pusiste siete de vacaciones. Los hilos reparten trabajo entre núcleos.

## ¿Quién lo usa?

Prácticamente todo lo que usás a diario:

- **Todos los toolkits gráficos** (Swing, JavaFX, Android): el hilo de la UI pinta botones mientras otros cargan datos.
- **Servidores web**: cada request suele atenderse en su propio hilo; mil usuarios simultáneos, mil conversaciones a la vez.
- **El recolector de basura**: el GC es en sí un equipo de hilos trabajando desde antes de tu primer `main`.

## ¿Cómo funciona?

### 1. Ya estás usando hilos (aunque no lo sepas)

Tu `main` **ya es un hilo**: `Thread.currentThread().getName()` devuelve `"main"`.
Todo lo que escribiste hasta ahora corrió en ese único camino; concurrencia es
agregar caminos, no empezar de cero.

### 2. Crear un hilo: dos caminos

```java
class Saludo extends Thread {                    // Camino A: heredar
    @Override public void run() { System.out.println("¡Hola!"); }
}
new Saludo().start();

class Tarea implements Runnable {                // Camino B: implementar (preferido)
    @Override public void run() { System.out.println("¡Hola!"); }
}
new Thread(new Tarea()).start();
```

**Preferimos `Runnable`**: composición sobre herencia. La lógica no necesita *ser*
un Thread, solo describir *trabajo*: tu clase queda libre para extender lo que de
verdad necesite, se testea por separado y los executors aceptan `Runnable` directo.

### 3. La forma moderna: lambda

Como `Runnable` tiene un único método abstracto:

```java
new Thread(() -> System.out.println("Tarea anónima")).start();
```

Perfecta para tareas cortas y desechables.

### 4. LA trampa clásica: `start()` no es `run()`

```java
tarea.run();    // ❌ corre ACÁ, en el hilo actual, como cualquier método
tarea.start(); // ✅ crea un hilo NUEVO que invoca run()
```

Llamar `run()` directo no da error ni warning: tu código se vuelve secuencial
mientras creés tener concurrencia. Memorizalo: **`start()` crea el hilo;
`run()` es solo el cuerpo que ese hilo ejecutará.**

### 5. Ciclo de vida

```
        start()                    run() termina
NEW ────────────► RUNNABLE ──────► TERMINATED
                    ▲   │   │
      sleep termina │   │   └── intenta tomar lock ocupado → BLOCKED
      o join llega  │   ├── sleep(ms) → TIMED_WAITING · join()/wait() → WAITING
```

NEW: creado · RUNNABLE: listo o corriendo (decide el **planificador**, la parte de la JVM que reparte núcleos entre hilos) · WAITING / TIMED_WAITING / BLOCKED: pausada (BLOCKED = quiere un *lock*, candado, que otro tiene) · TERMINATED: terminó, y un Thread no se reusa.

### 6. Dos herramientas básicas

- `Thread.sleep(ms)`: duerme **al hilo actual** un rato. Útil para esperar;
  nunca como "sincronización de verdad".
- `hilo.join()`: el hilo actual se congela hasta que `hilo` termine. Es el
  "esperame afuera" entre hilos.

### 7. El problema estrella: la condición de carrera

Antes del código, la historia. Queda **una sola sartén** en la despensa y dos
cocineros la necesitan ya. Cada uno mira (¿hay sartén? sí), la agarra y sigue
su camino. El segundo miró *justo antes* de que el primero la levantara: ambos
vieron lo mismo y actuaron sobre esa misma información. Nadie rompió nada,
nadie avisó... pero un cocinero quedó sin sartén y ni cuenta se dieron.

Con memoria compartida pasa idéntico. Este incremento inocente NO es una
operación, son tres pasos (**leer** → **modificar** → **escribir**):
(**leer** → **modificar** → **escribir**):

```
Hilo A: lee 41 ──► suma 1 ──► escribe 42
Hilo B: lee 41 ─────────▲────► escribe 42   ¡una suma perdida!
```

Si B lee **antes** de que A escriba, ambos parten del mismo valor y un incremento
se esfuma. Eso es una **race condition**: el resultado depende del orden
impredecible con que el planificador entrelaza hilos. Puede andar bien mil veces...
y fallar un viernes a las 18:00 en producción.

### 8. La escalera de soluciones

Elegí siempre el peldaño más simple que resuelva tu caso.

**a) `synchronized` — el portero de la despensa**, un cocinero por vez:

```java
synchronized void incrementar() { contador++; }  // método completo
synchronized (candado)          { contador++; }  // o solo lo crítico
```

Regla de oro: todos los hilos deben sincronizar **sobre el mismo lock**; candados
distintos sobre los mismos datos = portero mirando para otro lado.

**b) `AtomicInteger` — incremento atómico sin lock**, apoyado en instrucciones
del hardware:

```java
AtomicInteger contador = new AtomicInteger();
contador.incrementAndGet(); // leer-sumar-escribir en un paso indivisible
```

**c) `volatile` — una línea, alcance limitado.** Garantiza **visibilidad** (lo
escrito por un hilo se ve ya en los demás) pero **NO atomicidad**: sirve para
flags (`boolean corriendo`), no arregla un `contador++`.

### 9. ExecutorService: no fabriques hilos a mano en producción

Un `new Thread(...)` por tarea es contratar un chef nuevo por plato. Mejor un
**pool**: equipo fijo de cocineros con cola de pedidos.

```java
ExecutorService cocina = Executors.newFixedThreadPool(4); // 4 cocineros fijos
cocina.submit(() -> procesarPedido(pedido));              // encolá tareas
cocina.shutdown();                                        // cerramos: terminen pendientes
```

Sin `shutdown()` los hilos del pool quedan vivos y **tu JVM nunca se apaga**.

### 10. Colecciones concurrentes

Un `HashMap` compartido entre hilos sin protección produce datos corruptos o bucles
infinitos. Usá `ConcurrentHashMap`: sincroniza de forma fina y permite que varios
hilos lean y escriban zonas distintas sin pisarse.

### 11. Hilos virtuales (Java 21+, teaser)

Java 21 estabilizó los *virtual threads*: tan baratos que podés manejar millones,
ideales cuando el trabajo es esperar E/S (red, disco). Cambian cuánto cuesta un
hilo, no las reglas de coordinación de este módulo.

## ¿Dónde se usa?

Interfaces gráficas (trabajo pesado fuera del hilo de eventos), servidores y APIs
(un request, un worker del pool), lotes partidos en partes paralelas, y todo lo
que mezcla espera (red/disco) con cómputo.

## ¿Cuándo NO conviene?

La concurrencia no es azúcar: se agrega a propósito o amarga todo.

- Lógica inherentemente secuencial: si el paso 2 necesita el resultado del paso 1, no hay nada que paralelizar.
- Problemas chicos: crear y coordinar hilos cuesta más que resolverlo directo.
- "Va lento, meto hilos": primero medí. Muchas veces el cuello es la red o la base de datos, y los hilos solo agregan bugs nuevos a la lentitud vieja.

## Ejemplo práctico

En [`ejemplos/`](ejemplos/) tenés tres archivos listos para ejecutar:

1. **`MiPrimerHilo.java`** — letras y números entrelazados, `join()` al final y la trampa `run()` comentada.
2. **`CondicionDeCarreraVisible.java`** — cinco hilos pierden sumas en vivo; corrélo varias veces.
3. **`ArreglandoLaCarrera.java`** — el mismo escenario arreglado con método y bloque `synchronized`, y `AtomicInteger`: los tres llegan a 5000.

Compilá, corré, y después rompé cosas a propósito.

## Buenas prácticas

- **Inmutabilidad por defecto**: un objeto que no cambia no puede corromperse bajo hilos; los `record` te regalan esto casi gratis.
- **Confinamiento**: que cada hilo tenga SUS datos es la mejor sincronización, porque es la que no hace falta escribir.
- **Pools, no hilos sueltos**: `ExecutorService` siempre en producción.
- **Secciones críticas mínimas**: dentro del lock, solo lo imprescindible. Y documentá
  qué se comparte y quién lo protege; lo obvio hoy es misterio en seis meses.

## Errores comunes

| Error | Síntoma |
|---|---|
| Llamar `run()` en vez de `start()` | Todo "funciona", pero secuencial: nunca hubo segundo hilo |
| Asumir orden entre hilos | Resultados distintos en cada corrida |
| Locks distintos protegiendo los mismos datos | La carrera persiste igual que sin sincronizar |
| Olvidar `shutdown()` del executor | El programa nunca termina: la JVM queda viva |

## Resumen express

- Un hilo = camino de ejecución propio dentro de un proceso que comparte memoria.
- Tu `main` ya es un hilo; preferí `Runnable` (+ lambda) antes que extender `Thread`.
- `start()` crea el hilo; `run()` directo es un método común y corriente.
- `sleep(ms)` pausa al hilo actual; `join()` espera a otro hilo.
- Memoria compartida + escritura simultánea = condición de carrera (`contador++` son 3 pasos).
- Escalera: `synchronized` → `AtomicInteger` → `volatile` (visibilidad, no atomicidad).
- En producción: pools, colecciones concurrentes e inmutabilidad.

## Ejercicios

1. **Hilo saludador** — Una clase que extienda `Thread`, salude 5 veces con pausas de medio segundo entre saludo y saludo. Arrancala desde `main`.
2. **Corredores con lambda** — Tres corredores como lambdas `Runnable`; cada uno avanza 10 pasos con `sleep` de velocidad distinta. ¿Quién gana? Corrélo varias veces.
3. **La espera correcta con join** — Un `main` que lanza 3 trabajadores e imprime el resultado combinado SOLO cuando todos terminaron. Sin `join`, sale antes de tiempo.
4. **Caja fuerte sincronizada** — Un saldo compartido con `depositar(monto)` y `extraer(monto)`; varios hilos a la vez deben dejar el saldo exacto, protegido con `synchronized`.
5. **Desafío: venta de entradas con ExecutorService** — Quedan 10 entradas y 4 puestos venden en paralelo con un pool fijo. Nadie compra dos veces ni se vende una entrada inexistente. Al final, detalle completo de ventas.

## Para profundizar

- [Tutorial oficial de Oracle — Concurrency](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- *Effective Java* (Joshua Bloch) — ítems sobre concurrencia y executors.
- *Java Concurrency in Practice* (Brian Goetz) — la referencia seria del tema.
- [JEP 444: Virtual Threads](https://openjdk.org/jeps/444)

---

*Antes de seguir: corré los tres ejemplos. Ver una carrera perder sumas frente a tus ojos enseña más que diez párrafos.*

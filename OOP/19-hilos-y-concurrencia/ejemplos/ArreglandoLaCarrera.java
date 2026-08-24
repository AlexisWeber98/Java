// Módulo 19 - Arreglando la carrera, tres veces
//
// El mismo escenario de CondicionDeCarreraVisible (5 hilos × 1000 incrementos
// sobre un contador compartido), pero arreglado con los tres peldaños clásicos:
//
//   1. synchronized en el método   → el portero en toda la puerta.
//   2. synchronized en un bloque   → la sección crítica más chica posible.
//   3. AtomicInteger               → incremento atómico sin locks.
//
// Los tres llegan a 5000 SIEMPRE. Esa es la definición de "arreglado".

import java.util.concurrent.atomic.AtomicInteger;

public class ArreglandoLaCarrera {

    private static final int CANTIDAD_HILOS = 5;
    private static final int VUELTAS_POR_HILO = 1000;

    /** Camino 1: método sincronizado. Un cocinero por vez, sin excepciones. */
    static class ContadorSincronizadoMetodo {
        int valor;

        synchronized void incrementar() {
            valor++;
        }
    }

    /** Camino 2: bloque sincronizado sobre un lock privado. Solo lo crítico. */
    static class ContadorSincronizadoBloque {
        int valor;
        private final Object candado = new Object();

        void incrementar() {
            // Ojo: si cada instancia usara un candado distinto por hilo,
            // la carrera volvería. Todos deben usar EL MISMO lock.
            synchronized (candado) {
                valor++;
            }
        }
    }

    /** Camino 3: AtomicInteger. Leer-sumar-escribir en un paso indivisible. */
    static class ContadorAtomico {
        final AtomicInteger valor = new AtomicInteger();

        void incrementar() {
            valor.incrementAndGet();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Mismo experimento, ahora bien portado:\n");

        ContadorSincronizadoMetodo metodo = new ContadorSincronizadoMetodo();
        correrEnParalelo(metodo::incrementar);
        mostrar("synchronized en método", metodo.valor);

        ContadorSincronizadoBloque bloque = new ContadorSincronizadoBloque();
        correrEnParalelo(bloque::incrementar);
        mostrar("synchronized en bloque", bloque.valor);

        ContadorAtomico atomico = new ContadorAtomico();
        correrEnParalelo(atomico::incrementar);
        mostrar("AtomicInteger", atomico.valor.get());

        System.out.println("\nLos tres caminos llegan a 5000 siempre.");
        System.out.println("¿Cuál elegir? El más simple que tu caso necesite:");
        System.out.println("- Pocos datos y lógica clara → synchronized (método o bloque).");
        System.out.println("- Contadores y acumuladores → AtomicInteger.");
    }

    /** Lanza 5 hilos que incrementan 1000 veces cada uno usando la acción dada. */
    private static void correrEnParalelo(Runnable accionIncremento) throws InterruptedException {
        Thread[] equipo = new Thread[CANTIDAD_HILOS];
        for (int i = 0; i < CANTIDAD_HILOS; i++) {
            equipo[i] = new Thread(() -> {
                for (int vuelta = 0; vuelta < VUELTAS_POR_HILO; vuelta++) {
                    accionIncremento.run();
                }
            });
            equipo[i].start();
        }
        for (Thread hilo : equipo) {
            hilo.join();
        }
    }

    private static void mostrar(String estrategia, int valorFinal) {
        String veredicto = (valorFinal == CANTIDAD_HILOS * VUELTAS_POR_HILO)
                ? "✓ exacto"
                : "✗ PERDIMOS SUMAS";
        System.out.printf("%-25s → %5d  %s%n", estrategia, valorFinal, veredicto);
    }
}

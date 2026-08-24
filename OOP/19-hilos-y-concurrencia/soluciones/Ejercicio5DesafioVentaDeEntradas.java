/*
 * ============================================================================
 *  Módulo 19 - Hilos y Concurrencia
 *  Ejercicio 5 (DESAFÍO): La venta que no puede sobrevender — SOLUCIÓN
 * ============================================================================
 *
 *  ENUNCIADO (resumen):
 *  Recital con cupo de 50 entradas; un ExecutorService de 5 hilos procesa
 *  200 intentos de compra (cada uno pide 1 a 4 entradas). Informe final con
 *  aceptadas + rechazadas == 200 SIEMPRE, sin sobreturno, y pool bien apagado.
 *
 *  IDEAS CLAVE:
 *   - Elección de técnica: el estado es UN contador, así que AtomicInteger con
 *     compareAndSet resuelve todo sin locks. synchronized sería igual de
 *     correcto y sería preferible si comprar() tocara varios campos con
 *     invariantes combinadas (ej.: stock + lista de compradores + auditoría).
 *   - El bucle CAS: leo el valor actual, si alcanza intento restar CON la
 *     condición de que nadie haya cambiado nada en el medio. Si otro hilo me
 *     ganó de mano, reintento con el valor fresco. Chequeo y descuento quedan
 *     atómicos: es imposible sobrevender.
 *   - Apagado correcto del pool: shutdown() deja de aceptar tareas y termina
 *     las pendientes; awaitTermination() espera con vencimiento; si no alcanzó,
 *     shutdownNow() interrumpe lo que quede.
 *   - Verificaciones finales: los contadores y el stock deben cuadrar SIEMPRE.
 *     Si alguna verificación falla, hay un bug de concurrencia: no lo tapemos.
 * ============================================================================
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Recital {

    static final int CUPO_TOTAL = 50;

    private final AtomicInteger entradasDisponibles = new AtomicInteger(CUPO_TOTAL);

    /**
     * Intenta vender la cantidad pedida (todo o nada).
     * Devuelve true si la compra fue aceptada.
     */
    public boolean comprar(int cantidad) {
        while (true) {
            int actuales = entradasDisponibles.get();
            if (actuales < cantidad) {
                return false; // falta de stock: el intento se rechaza completo
            }
            // CAS: resta SOLO si el valor sigue siendo `actuales`.
            if (entradasDisponibles.compareAndSet(actuales, actuales - cantidad)) {
                return true; // ganamos la carrera: venta confirmada
            }
            // Otro hilo nos ganó de mano: reintentamos con el valor fresco.
        }
    }

    public int getEntradasDisponibles() {
        return entradasDisponibles.get();
    }
}

public class Ejercicio5DesafioVentaDeEntradas {

    static final int INTENTOS = 200;
    static final int HILOS_EN_POOL = 5;

    public static void main(String[] args) throws InterruptedException {
        Recital recital = new Recital();
        AtomicInteger compradas = new AtomicInteger();
        AtomicInteger rechazadas = new AtomicInteger();
        AtomicInteger entradasVendidas = new AtomicInteger();

        ExecutorService piscina = Executors.newFixedThreadPool(HILOS_EN_POOL);

        for (int i = 0; i < INTENTOS; i++) {
            piscina.submit(() -> {
                int cantidadPedida = ThreadLocalRandom.current().nextInt(1, 5); // 1..4
                if (recital.comprar(cantidadPedida)) {
                    compradas.incrementAndGet();
                    entradasVendidas.addAndGet(cantidadPedida);
                } else {
                    rechazadas.incrementAndGet();
                }
            });
        }

        // Apagado prolijo: no se aceptan más tareas y esperamos a las pendientes.
        piscina.shutdown();
        if (!piscina.awaitTermination(10, TimeUnit.SECONDS)) {
            piscina.shutdownNow(); // algo quedó trabado: interrumpimos
        }

        int total = compradas.get() + rechazadas.get();
        System.out.println("== Informe de ventas ==");
        System.out.println("Intentos procesados : " + total + " (esperados: " + INTENTOS + ")");
        System.out.println("Compras aceptadas   : " + compradas.get());
        System.out.println("Rechazadas sin stock: " + rechazadas.get());
        System.out.println("Entradas vendidas   : " + entradasVendidas.get()
                + " de " + Recital.CUPO_TOTAL);
        System.out.println("Entradas restantes  : " + recital.getEntradasDisponibles());

        // Verificaciones duras: deben cumplirse en CADA ejecución.
        verificar("aceptadas + rechazadas == intentos", total == INTENTOS);
        verificar("no hubo sobreturno", entradasVendidas.get() <= Recital.CUPO_TOTAL);
        verificar("vendidas + restantes == cupo",
                entradasVendidas.get() + recital.getEntradasDisponibles() == Recital.CUPO_TOTAL);
        System.out.println("Verificación de invariantes: OK");
    }

    private static void verificar(String descripcion, boolean condicion) {
        if (!condicion) {
            throw new IllegalStateException("Invariante rota: " + descripcion);
        }
    }
}

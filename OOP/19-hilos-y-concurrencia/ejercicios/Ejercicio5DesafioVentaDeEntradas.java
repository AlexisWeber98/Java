/*
 * ============================================================================
 *  Módulo 19 - Hilos y Concurrencia
 *  Ejercicio 5 (DESAFÍO): La venta que no puede sobrevender
 * ============================================================================
 *
 *  ENUNCIADO:
 *  Un recital tiene cupo de 50 entradas. Llegan 200 intentos de compra que un
 *  ExecutorService con pool FIJO de 5 hilos procesa en paralelo. Cada intento
 *  pide una cantidad al azar entre 1 y 4: o se venden todas las pedidas, o el
 *  intento completo se rechaza por falta de stock. Al final imprimí un informe
 *  donde compradas + rechazadas == 200 SIEMPRE, sin sobreturno del cupo.
 *
 *  REQUISITOS:
 *   - Recital.comprar(cantidad): todo-o-nada, seguro para hilos. Elegí entre
 *     AtomicInteger (CAS) o synchronized y defendé tu elección en comentario.
 *   - Pool fijo de 5 hilos procesando los 200 intentos.
 *   - Apagado correcto: shutdown() + awaitTermination() (con fallback a
 *     shutdownNow()).
 *   - Informe final + verificación dura: si vendidas > cupo o la suma no da
 *     200, tu solución tiene una carrera.
 *
 *  PISTAS:
 *   - El chequeo "¿hay stock?" y el descuento deben ser UNA sola operación:
     por separado, dos hilos pasan el if juntos y sobrevenden.
 *   - Para CAS: get(), compareAndSet(esperado, nuevo) en bucle; si falla,
     reintentá con el valor fresco.
 *   - Contadores globales: AtomicInteger.incrementAndGet()/addAndGet().
 *   - Cantidad al azar dentro del hilo: ThreadLocalRandom.current().nextInt(1, 5).
 * ============================================================================
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

class Recital {

    static final int CUPO_TOTAL = 50;

    // TODO 1: esta variable necesita protección; elegí tu técnica y aplicala.
    private int entradasDisponibles = CUPO_TOTAL;

    public boolean comprar(int cantidad) {
        // TODO 1: convertí chequear-y-descontar en una operación atómica
        //         (todo o nada). Devolvé true sólo si la venta fue aceptada.
        if (entradasDisponibles >= cantidad) {
            entradasDisponibles = entradasDisponibles - cantidad;
            return true;
        }
        return false;
    }

    public int getEntradasDisponibles() {
        return entradasDisponibles;
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

        // TODO 2: creá el ExecutorService con pool fijo de HILOS_EN_POOL.

        // TODO 3: enviá INTENTOS tareas; cada una pide 1..4 entradas al azar,
        //         llama a recital.comprar(cantidad) y actualiza contadores.

        // TODO 4: apagá bien el pool: shutdown() + awaitTermination(...)
        //         con fallback a shutdownNow().

        // TODO 5: informe final (intentos procesados, compradas, rechazadas,
        //         entradas vendidas y restantes) + verificá que
        //         compradas + rechazadas == 200 y que no haya sobreturno.

        System.out.println("(falta implementar el desafío)");
    }
}

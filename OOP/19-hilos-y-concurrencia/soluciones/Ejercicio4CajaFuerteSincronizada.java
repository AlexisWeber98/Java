/*
 * ============================================================================
 *  Módulo 19 - Hilos y Concurrencia
 *  Ejercicio 4: La caja fuerte sincronizada — SOLUCIÓN
 * ============================================================================
 *
 *  ENUNCIADO (resumen):
 *  5 hilos depositan $1 mil veces cada uno sobre una CajaFuerte compartida.
 *  Esperado: $5000 exactos. La versión sin sincronizar pierde plata; la idea
 *  es arreglarlo con synchronized y discutir la alternativa AtomicInteger.
 *
 *  IDEAS CLAVE:
 *   - El bug: saldo = saldo + monto son TRES pasos (leer, sumar, escribir).
 *     Si dos hilos leen el mismo valor, ambos escriben lo mismo y un depósito
 *     se pierde. Es una condición de carrera clásica de leer-modificar-escribir.
 *   - synchronized (metodo): cada objeto tiene UN monitor; un solo hilo por vez
 *     ejecuta los métodos sincronizados de esa instancia. También sincronizamos
 *     getSaldo(): una lectura coherente también merece el lock.
 *   - ALTERNATIVA con AtomicLong: addAndGet() hace el incremento atómico a nivel
 *     de hardware (CAS), sin locks. Ideal para contadores simples. Si la
 *     operación tocara varios campos con invariantes combinadas, synchronized
 *     es más simple y difícil de romper.
 * ============================================================================
 */
class CajaFuerte {

    private long saldo;

    // CLAVE: synchronized convierte leer-sumar-escribir en una sección crítica.
    public synchronized void depositar(long monto) {
        saldo = saldo + monto;
    }

    public synchronized boolean extraer(long monto) {
        if (saldo >= monto) {
            saldo = saldo - monto;
            return true;
        }
        return false;
    }

    public synchronized long getSaldo() {
        return saldo;
    }
}

// Variante sin locks, para comparar en la fase 2 del main.
class CajaFuerteAtomica {

    private final java.util.concurrent.atomic.AtomicLong saldo =
            new java.util.concurrent.atomic.AtomicLong();

    public void depositar(long monto) {
        saldo.addAndGet(monto); // incremento atómico vía CAS
    }

    public long getSaldo() {
        return saldo.get();
    }
}

public class Ejercicio4CajaFuerteSincronizada {

    private static final int HILOS = 5;
    private static final int DEPOSITOS_POR_HILO = 1000;
    private static final long MONTO = 1;

    public static void main(String[] args) throws InterruptedException {
        long esperado = (long) HILOS * DEPOSITOS_POR_HILO * MONTO;

        // FASE 1: caja con synchronized.
        CajaFuerte caja = new CajaFuerte();
        correrDepositos(caja::depositar);
        System.out.println("synchronized -> saldo: " + caja.getSaldo()
                + " / esperado: " + esperado
                + (caja.getSaldo() == esperado ? "  [OK]" : "  [PERDIMOS PLATA]"));

        // FASE 2: caja con AtomicLong.
        CajaFuerteAtomica atomica = new CajaFuerteAtomica();
        correrDepositos(atomica::depositar);
        System.out.println("AtomicLong    -> saldo: " + atomica.getSaldo()
                + " / esperado: " + esperado
                + (atomica.getSaldo() == esperado ? "  [OK]" : "  [PERDIMOS PLATA]"));

        // Ojo: si sacás el synchronized de CajaFuerte y corréis varias veces,
        // vas a ver saldos menores que 5000. Probalo para SENTIR el bug.
    }

    private static void correrDepositos(java.util.function.LongConsumer deposito)
            throws InterruptedException {
        Thread[] productores = new Thread[HILOS];
        for (int i = 0; i < HILOS; i++) {
            productores[i] = new Thread(() -> {
                for (int d = 0; d < DEPOSITOS_POR_HILO; d++) {
                    deposito.accept(MONTO);
                }
            }, "depositante-" + i);
            productores[i].start();
        }
        for (Thread hilo : productores) {
            hilo.join();
        }
    }
}

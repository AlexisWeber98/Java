/*
 * ============================================================================
 *  Módulo 19 - Hilos y Concurrencia
 *  Ejercicio 4: La caja fuerte sincronizada
 * ============================================================================
 *
 *  ENUNCIADO:
 *  Una CajaFuerte guarda un saldo y expone depositar(monto), extraer(monto)
 *  y getSaldo(). Cinco hilos depositan $1 mil veces cada uno (total esperado:
 *  $5000). El código de abajo NO sincroniza: corrélo varias veces y observá
 *  que el saldo final suele dar MENOS que 5000.
 *
 *  REQUISITOS:
 *   - Explicar en comentario por qué se pierden depósitos (leer-modificar-
 *     escribir no atómico).
 *   - Arreglar CajaFuerte con synchronized para que SIEMPRE dé 5000 exactos.
 *   - Probar la alternativa con AtomicInteger/AtomicLong: dejar escrito acá
 *     cuándo conviene cada técnica.
 *
 *  PISTAS:
 *   - saldo = saldo + monto compila a leer, sumar y escribir: tres pasos que
 *     otros hilos pueden intercalar entre medio.
 *   - synchronized en el método usa el monitor del objeto: uno por instancia.
 *   - No te olvides de la LECTURA: getSaldo() también debería ser coherente.
 * ============================================================================
 */
class CajaFuerte {

    private long saldo;

    public void depositar(long monto) {
        // ¡SIN sincronizar A PROPÓSITO! Este método pierde depósitos bajo carga.
        saldo = saldo + monto;
    }

    public boolean extraer(long monto) {
        if (saldo >= monto) {
            saldo = saldo - monto;
            return true;
        }
        return false;
    }

    public long getSaldo() {
        return saldo;
    }
}

public class Ejercicio4CajaFuerteSincronizada {

    private static final int HILOS = 5;
    private static final int DEPOSITOS_POR_HILO = 1000;
    private static final long MONTO = 1;

    public static void main(String[] args) throws InterruptedException {
        CajaFuerte caja = new CajaFuerte();
        Thread[] productores = new Thread[HILOS];

        // TODO 1: arrancá HILOS hilos; cada uno hace DEPOSITOS_POR_HILO veces
        //         caja.depositar(MONTO) dentro de una lambda.

        // TODO 2: esperá a los cinco con join().

        // TODO 3: imprimí "Saldo final: X / esperado: 5000". Corré varias veces
        //         ANTES de sincronizar: ¿cuánta plata se perdió esta vez?

        // TODO 4: arreglá CajaFuerte con synchronized y volvé a correr hasta
        //         ver 5000 siempre. Respondé también: ¿qué aporta AtomicLong
        //         aquí y cuándo preferirías synchronized en su lugar?

        System.out.println("Saldo final: " + caja.getSaldo()
                + " / esperado: " + ((long) HILOS * DEPOSITOS_POR_HILO * MONTO));
    }
}

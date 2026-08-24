/*
 * ============================================================================
 *  Módulo 19 - Hilos y Concurrencia
 *  Ejercicio 1: Tu primer hilo — SOLUCIÓN
 * ============================================================================
 *
 *  ENUNCIADO (resumen):
 *  Un hilo llamado "saludador" imprime 3 veces "Hola desde el hilo <nombre>"
 *  con pausas; el main espera con join() antes de despedirse.
 *
 *  IDEAS CLAVE:
 *   - start() NO ejecuta el Runnable en el hilo actual: le pide al planificador
 *     que lo corra en paralelo. Llamar run() directo sería un error clásico.
 *   - join() es la forma más simple de esperar: el main queda bloqueado hasta
 *     que el saludador muere. Garantiza el orden del cartel final.
 *   - Al capturar InterruptedException restauramos el flag con interrupt():
 *     nunca nos "tragamos" la interrupción en silencio.
 * ============================================================================
 */
public class Ejercicio1HiloSaludador {

    public static void main(String[] args) throws InterruptedException {
        // El trabajo del hilo, expresado como lambda: tres saludos con pausa.
        Runnable saludador = () -> {
            String nombre = Thread.currentThread().getName();
            for (int i = 1; i <= 3; i++) {
                System.out.println("Hola desde el hilo " + nombre);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // preservamos el estado de interrupción
                    return;
                }
            }
        };

        Thread hilo = new Thread(saludador, "saludador");
        System.out.println("Principal: lanzo el saludador");
        hilo.start();

        // CLAVE: sin este join(), el main sigue de largo y se despide primero.
        hilo.join();

        System.out.println("Chau desde el hilo principal");
    }
}

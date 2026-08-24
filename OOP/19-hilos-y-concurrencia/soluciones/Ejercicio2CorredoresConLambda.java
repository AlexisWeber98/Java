/*
 * ============================================================================
 *  Módulo 19 - Hilos y Concurrencia
 *  Ejercicio 2: Cuatro corredores con lambdas — SOLUCIÓN
 * ============================================================================
 *
 *  ENUNCIADO (resumen):
 *  4 lambdas-corredores avanzan 5 pasos en paralelo; el main espera a todos
 *  antes de cantar la llegada.
 *
 *  IDEAS CLAVE:
 *   - NO determinismo del entrelazado: los mensajes se mezclan distinto en cada
 *     ejecución porque el scheduler del SO intercala los hilos cuando quiere.
 *     Java no garantiza orden ni "equidad" entre hilos que compiten por CPU.
 *   - join() sí es determinista como BARRERA: garantiza que el cartel final
 *     sale después de los 20 pasos, aunque no el orden interno de esos pasos.
 *   - Un único Runnable parametrizado por nombre evita duplicar lógica:
 *     mismo comportamiento, cuatro instancias de hilo.
 * ============================================================================
 */
public class Ejercicio2CorredoresConLambda {

    private static final String[] CORREDORES = {"Ana", "Bruno", "Caro", "Dami"};

    public static void main(String[] args) throws InterruptedException {
        Thread[] hilos = new Thread[CORREDORES.length];

        for (int i = 0; i < CORREDORES.length; i++) {
            String corredor = CORREDORES[i]; // debe ser final (o efectivamente final) para la lambda

            Runnable carrera = () -> {
                for (int paso = 1; paso <= 5; paso++) {
                    System.out.println(corredor + ": paso " + paso + " de 5");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            };

            hilos[i] = new Thread(carrera, corredor);
            hilos[i].start(); // los cuatro largan casi juntos
        }

        // Barrera: esperamos a CADA corredor antes del cierre.
        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println("Fin de la carrera");

        // Respuesta al TODO 3: el orden cambia entre ejecuciones porque el
        // planificador del sistema operativo reparte núcleos y franjas de CPU
        // sin reglas fijas. Los sleep() sugieren un ritmo, pero no imponen un
        // orden global: solo garantizan que cada hilo pausa a sí mismo.
    }
}

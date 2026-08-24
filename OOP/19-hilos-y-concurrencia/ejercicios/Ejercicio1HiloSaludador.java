/*
 * ============================================================================
 *  Módulo 19 - Hilos y Concurrencia
 *  Ejercicio 1: Tu primer hilo
 * ============================================================================
 *
 *  ENUNCIADO:
 *  Escribí un programa que cree UN hilo extra llamado "saludador". Ese hilo
 *  imprime tres veces la línea "Hola desde el hilo <nombre>", con una pausa
 *  de unos 300 ms entre cada saludo. El hilo principal debe imprimir
 *  "Chau desde el hilo principal" recién CUANDO el saludador terminó.
 *
 *  REQUISITOS:
 *   - El trabajo del hilo definido como Runnable con expresión lambda.
 *   - El hilo creado con nombre propio ("saludador") y arrancado con start().
 *   - Pausas con Thread.sleep() entre saludo y saludo.
 *   - El main espera con join() ANTES de imprimir su despedida.
 *
 *  PISTAS:
 *   - new Thread(runnable, "nombre") crea un hilo ya bautizado.
 *   - Dentro de la lambda podés obtener el nombre con
 *     Thread.currentThread().getName().
 *   - Thread.sleep() lanza InterruptedException: capturala adentro de la lambda
 *     (Runnable.run() no permite excepciones chequeadas).
 *   - Corrélo varias veces: los saludos SIEMPRE deben aparecer antes del chau.
 * ============================================================================
 */
public class Ejercicio1HiloSaludador {

    public static void main(String[] args) throws InterruptedException {
        // TODO 1: definí un Runnable (lambda) que imprima 3 veces
        //         "Hola desde el hilo <nombre>" usando
        //         Thread.currentThread().getName(), durmiendo ~300 ms entre saludo y saludo.

        // TODO 2: creá el Thread con ese Runnable y el nombre "saludador".

        // TODO 3: arrancalo con start().

        // TODO 4: esperá a que termine con join() ANTES de la línea de abajo.

        System.out.println("Chau desde el hilo principal");
    }
}

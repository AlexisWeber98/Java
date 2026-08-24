/*
 * ============================================================================
 *  Módulo 19 - Hilos y Concurrencia
 *  Ejercicio 2: Cuatro corredores con lambdas
 * ============================================================================
 *
 *  ENUNCIADO:
 *  Simulá una carrera con 4 corredores (Ana, Bruno, Caro y Dami). Cada uno es
 *  un Runnable definido como LAMBDA que da 5 pasos, imprimiendo por paso
 *  "<Nombre>: paso N de 5", con una pausa de ~100 ms entre pasos. Largá a los
 *  cuatro casi al mismo tiempo y esperá a TODOS antes de imprimir
 *  "Fin de la carrera".
 *
 *  REQUISITOS:
 *   - Un único bloque lambda de carrera, reutilizado por los 4 hilos.
 *   - start() de los cuatro y join() a cada uno antes del cartel final.
 *   - Corré el programa varias veces: el entrelazado cambia entre ejecuciones.
 *
 *  PISTAS:
 *   - La variable que captura la lambda debe ser final o efectivamente final:
 *     copiá el nombre del array a una variable local dentro del for.
 *   - sleep() adentro de la lambda obliga a manejar InterruptedException.
 * ============================================================================
 */
public class Ejercicio2CorredoresConLambda {

    private static final String[] CORREDORES = {"Ana", "Bruno", "Caro", "Dami"};

    public static void main(String[] args) throws InterruptedException {
        // TODO 1: para cada corredor, creá un Thread cuya lambda dé 5 pasos
        //         imprimiendo "<Nombre>: paso N de 5" con pausas de ~100 ms.

        // TODO 2: arrancá los 4 hilos y hacé join() de los 4 antes del cartel final.

        // TODO 3: después de correrlo varias veces, respondé acá abajo en un
        //         comentario: ¿por qué el orden de los mensajes cambia entre
        //         ejecuciones si el código no cambió?

        System.out.println("Fin de la carrera");
    }
}

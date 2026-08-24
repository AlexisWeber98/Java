/*
 * ============================================================================
 *  Módulo 19 - Hilos y Concurrencia
 *  Ejercicio 3: La espera correcta
 * ============================================================================
 *
 *  ENUNCIADO:
 *  Un trabajador (SumadorLento) calcula la suma de 1 a 20 pero se toma su
 *  tiempo: duerme ~150 ms entre suma y suma (~3 s en total). El main NO debe
 *  usar join() infinito: tiene que esperar con la variante join(milisegundos)
 *  y consultar isAlive() para saber si el hilo sigue vivo. Mientras siga
 *  trabajando, mostrá "El sumador sigue trabajando..." una vez por tanda.
 *  El resultado se imprime UNA sola vez y SOLO cuando el hilo terminó.
 *
 *  REQUISITOS:
 *   - Completar SumadorLento: acumular 1..20 con pausas y guardar el total.
 *   - Espera con vencimiento: bucle while + join(500) + chequeo isAlive().
 *   - El resultado sale recién cuando isAlive() es false.
 *
 *  PISTAS:
 *   - join(500) vuelve después de 500 ms PASE lo que pase: puede volver con
 *     el hilo todavía vivo. Por eso existe isAlive().
 *   - Cuando join() confirma que el hilo murió, todo lo que ese hilo escribió
 *     es visible para vos: no necesitás volatile ni synchronized acá.
 * ============================================================================
 */
class SumadorLento implements Runnable {

    static final int PASOS = 20;
    private long resultado;

    @Override
    public void run() {
        long acumulado = 0;
        // TODO 1: sumá i a acumulado para i de 1 a PASOS, durmiendo ~150 ms
        //         entre paso y paso (manejando InterruptedException).
        // TODO 2: guardá el valor final en this.resultado.
    }

    public long getResultado() {
        return resultado;
    }
}

public class Ejercicio3LaEsperaCorrecta {

    public static void main(String[] args) throws InterruptedException {
        SumadorLento tarea = new SumadorLento();

        // TODO 3: creá el Thread, arrancalo y esperalo BIEN:
        //         mientras isAlive() sea true, hacé join(500) y avisá
        //         "El sumador sigue trabajando...".

        // TODO 4: cuando ya no esté vivo, imprimí tarea.getResultado()
        //         junto con el valor esperado (210).

        System.out.println("(falta implementar la espera correcta)");
    }
}

/*
 * ============================================================================
 *  Módulo 19 - Hilos y Concurrencia
 *  Ejercicio 3: La espera correcta — SOLUCIÓN
 * ============================================================================
 *
 *  ENUNCIADO (resumen):
 *  Un sumador lento tarda ~3 s en calcular la suma de 1 a 20. El main espera
 *  con join(milisegundos) consultando isAlive(), y muestra el resultado
 *  UNA sola vez y SOLO si el hilo realmente terminó.
 *
 *  IDEAS CLAVE:
 *   - join() sin argumentos bloquea indefinido; join(ms) es una espera con
 *     vencimiento: puede volver ANTES de que el hilo muera. Por eso hay que
 *     preguntar con isAlive() qué pasó realmente.
 *   - El patrón while (isAlive()) { join(500); avisar(); } es el idioma
 *     estándar para "esperar con paciencia y reportar progreso".
 *   - Visibilidad gratis: cuando join() confirma que el hilo terminó, existe
 *     una relación happens-before: todo lo que el hilo escribió (resultado)
 *     ya es visible para el main. No hace falta volatile acá.
 * ============================================================================
 */
class SumadorLento implements Runnable {

    static final int PASOS = 20;
    private long resultado;

    @Override
    public void run() {
        long acumulado = 0;
        for (int i = 1; i <= PASOS; i++) {
            acumulado += i;
            try {
                Thread.sleep(150); // simula cómputo lento (~3 s en total)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        resultado = acumulado; // se publica al morir el hilo
    }

    public long getResultado() {
        return resultado;
    }
}

public class Ejercicio3LaEsperaCorrecta {

    public static void main(String[] args) throws InterruptedException {
        SumadorLento tarea = new SumadorLento();
        Thread trabajador = new Thread(tarea, "sumador");
        trabajador.start();

        // Espera por tandas: join(500) puede volver sin que termine.
        while (trabajador.isAlive()) {
            trabajador.join(500);
            if (trabajador.isAlive()) {
                System.out.println("El sumador sigue trabajando...");
            }
        }

        // Acá el hilo YA murió: happens-before garantiza ver `resultado`.
        System.out.println("Resultado final: " + tarea.getResultado()
                + " (esperado: 210)");
    }
}

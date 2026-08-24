// Módulo 19 - Mi primer hilo
//
// Tu programa YA arranca con un hilo (el main). Acá creamos un segundo hilo
// trabajador y miramos cómo ambos avanzan al mismo tiempo: las letras y los
// números aparecen entrelazados, en un orden distinto cada corrida.
//
// TRAMPA CLÁSICA documentada más abajo: run() no crea hilos, start() sí.

public class MiPrimerHilo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Arranca el hilo principal: " + Thread.currentThread().getName());

        // Camino A (clásico): extender Thread. Funciona, pero acopla herencia
        // con concurrencia: la clase queda "gastando" su única herencia.
        Thread contador = new ContadorDeNumeros();

        // Camino B (preferido): implementar Runnable y entregárselo a un Thread.
        Thread letras = new Thread(new ImpresorDeLetras(), "hilo-letras");

        contador.start();
        letras.start();

        // ─────────────────────────────────────────────────────────────────
        // LA TRAMPA: si en vez de start() llamaras run() directamente,
        //
        //     letras.run(); // ¡NO!
        //
        // el método se ejecutaría ACÁ, en este hilo, como cualquier otro
        // método común. Sin hilo nuevo, sin entrelazado, sin concurrencia.
        // start() es quien crea el hilo; run() es solo lo que ese hilo hace.
        // ─────────────────────────────────────────────────────────────────

        // join(): el main se congela hasta que cada trabajador termine.
        // Sin esto, el main seguiría y podría despedirse antes de tiempo.
        letras.join();
        contador.join();

        System.out.println("Ambos trabajadores terminaron. El main se despide.");
    }

    /** Camino A: hereda de Thread y sobrescribe run(). */
    private static class ContadorDeNumeros extends Thread {

        ContadorDeNumeros() {
            super("hilo-numeros");
        }

        @Override
        public void run() {
            for (int numero = 1; numero <= 10; numero++) {
                System.out.println(getName() + "  → número " + numero);
                dormirUnPoquito();
            }
        }
    }

    /** Camino B: implementa Runnable. La tarea NO necesita ser un Thread. */
    private static class ImpresorDeLetras implements Runnable {

        @Override
        public void run() {
            String abecedario = "abcdefghij";
            for (int i = 0; i < abecedario.length(); i++) {
                System.out.println(Thread.currentThread().getName() + " → letra   " + abecedario.charAt(i));
                dormirUnPoquito();
            }
        }
    }

    private static void dormirUnPoquito() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Ejemplo 1 — Las anotaciones que lee el COMPILADOR.
 *
 * Acá no hay reflexión ni magia en runtime: el compilador de Java
 * interpreta estas etiquetas mientras compila y reacciona.
 *
 * Compilar y correr:
 *   java AnotacionesDelCompilador.java
 * Para ver el warning de deprecación explícito:
 *   javac -Xlint:deprecation AnotacionesDelCompilador.java
 */
public class AnotacionesDelCompilador {

    // ---------- 1) @Override: el detector de typos ----------

    static class Animal {
        public void hacerSonido() {
            System.out.println("El animal hace un sonido genérico");
        }
    }

    static class Perro extends Animal {

        @Override
        public void hacerSonido() {
            System.out.println("¡Guau!");
        }

        /*
         * DESCOMENTÁ esto y compilá: el compilador te caza el typo al instante,
         * porque hacerRuido() NO existe en Animal. Sin @Override, este mismo
         * error compila feliz y se convierte en un bug silencioso:
         *
         * @Override
         * public void hacerRuido() {
         *     System.out.println("método que no sobrescribe nada");
         * }
         */
    }

    // ---------- 2) @Deprecated: avisar "esto está en desuso" ----------

    /**
     * Versión vieja del envío, sin destinatario.
     *
     * @deprecated Usar {@link #enviarMensaje(String, String)}, que permite
     *             indicar el destinatario.
     */
    @Deprecated(forRemoval = true)
    public static void enviarEmail(String texto) {
        System.out.println("Enviando (forma vieja): " + texto);
    }

    /** Reemplazo moderno del método deprecado. */
    public static void enviarMensaje(String destinatario, String texto) {
        System.out.println("Enviando a " + destinatario + ": " + texto);
    }

    // ---------- 3) @SuppressWarnings: silenciar UN warning, acotado ----------

    @SuppressWarnings("unused")
    private void datoReservadoParaFuturo() {
        int datoPendiente = 42; // sin warning gracias a la etiqueta...
    }                           // ...y SOLO dentro de este método

    // ---------- Demo ----------

    public static void main(String[] args) {
        // @Override en acción: polimorfismo normal
        Animal mascota = new Perro();
        mascota.hacerSonido();

        // Llamar a un método deprecado compila... y acá NO aparece warning:
        // Java suprime el aviso cuando el uso ocurre dentro de la misma clase.
        // Para verlo, llamalo desde otra clase en otro archivo (ejercicio 2):
        //   javac -Xlint:deprecation AnotacionesDelCompilador.java
        enviarEmail("mensaje con formato antiguo");

        // El camino recomendado hoy:
        enviarMensaje("ana@ejemplo.com", "mensaje con destinatario");
    }
}

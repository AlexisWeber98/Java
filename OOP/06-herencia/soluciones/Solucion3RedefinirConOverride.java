/*
 * =============================================================================
 * Ejercicio 3 (SOLUCIÓN) — Redefinir métodos con @Override
 * Módulo 06 · Herencia
 * =============================================================================
 *
 * IDEAS CLAVE DE LA SOLUCIÓN
 *   - super.describir() reutiliza la versión del padre y debajo agregamos lo
 *     nuestro: extendemos comportamiento en lugar de duplicarlo.
 *   - @Override convierte un bug silencioso en error de compilación.
 *   - Con la referencia de tipo Animal apuntando a un Perro, igual corre el
 *     describir() del PERRO: eso es despacho dinámico (dynamic dispatch).
 * =============================================================================
 */
public class Solucion3RedefinirConOverride {

    static class Animal {
        String nombre;

        Animal(String nombre) {
            this.nombre = nombre;
        }

        void describir() {
            System.out.println("Soy " + nombre + ", un animal.");
        }
    }

    static class Perro extends Animal {
        Perro(String nombre) {
            super(nombre);
        }

        // CLAVE 1: primero lo del padre, después lo del hijo.
        @Override
        void describir() {
            super.describir();
            System.out.println("...y como soy un perro, también ladro: ¡guau!");
        }

        /*
         * CLAVE 2 — ¿Qué rompe exactamente la ausencia de @Override?
         *
         * Imaginate que tipeás mal y escribís:
         *
         *     void describiro() {
         *         super.describir();
         *         System.out.println("...ladro: ¡guau!");
         *     }
         *
         * SIN @Override ese archivo COMPILA sin quejarse. Java interpreta
         * que describiro() es un método NUEVO e independiente; tu intención
         * de redefinir se perdió. Resultado: perro.describir() sigue
         * mostrando la descripción genérica de Animal, el "guau" nunca
         * aparece, y nadie te avisa. El bug viaja callado hasta producción.
         *
         * CON @Override el compilador responde al instante:
         *     "method does not override or implement a method from a supertype"
         * Tres segundos de feedback contra semanas de debugging invisible.
         *
         * REGLA: cada vez que redefinás un método, poné @Override. Siempre.
         */
    }

    public static void main(String[] args) {
        // Polimorfismo: la referencia es de tipo Animal,
        // pero el objeto es un Perro... y quien responde es el Perro.
        Animal mascota = new Perro("Firulais");
        mascota.describir();

        // La salida tiene DOS líneas:
        //   Soy Firulais, un animal.          <- viene de super.describir()
        //   ...y como soy un perro...          <- lo agrega la subclase
    }
}

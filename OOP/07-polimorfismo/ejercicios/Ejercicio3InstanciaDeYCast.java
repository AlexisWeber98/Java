/*
 * ============================================================================
 * Módulo 07 – Polimorfismo | Ejercicio 3: instanceof y cast seguro
 * ============================================================================
 *
 * ENUNCIADO:
 *   Tenés un arreglo mezclado de Perro y Gato. Solo Perro sabe buscarPelota().
 *   Recorré el arreglo: todos presentan su sonido y, si son perros, además
 *   buscan la pelota. Usá instanceof con pattern matching para no explotar.
 *
 * REQUISITOS:
 *   1. Animal: atributo nombre y método hacerSonido() que devuelve un String.
 *   2. Perro y Gato sobrescriben hacerSonido() ("Guau" / "Miau").
 *   3. SOLO Perro agrega el método buscarPelota().
 *   4. En main: Animal[] con perros y gatos mezclados; en un mismo bucle
 *      imprimí el sonido de cada animal y, solo para los perros, la búsqueda
 *      de la pelota.
 *
 * PISTAS:
 *   - Pattern matching (Java 16+): if (animal instanceof Perro perro) { ... }
 *     pregunta el tipo Y te da la variable ya convertida, lista para usar.
 *   - El else del if es tu red: ahí caen los gatos sin romper nada.
 *   - Mirá el bloque comentado al final de main: castear a ciegas compila,
 *     pero revienta en ejecución. Probalo si querés entender el porqué.
 *
 * Ejecución:  java Ejercicio3InstanciaDeYCast.java
 */
public class Ejercicio3InstanciaDeYCast {

    static class Animal {
        final String nombre;

        Animal(String nombre) {
            this.nombre = nombre;
        }

        String hacerSonido() {
            return "...";
        }
    }

    static class Perro extends Animal {
        Perro(String nombre) {
            super(nombre);
        }

        @Override
        String hacerSonido() {
            return "Guau";
        }

        String buscarPelota() {
            // TODO: devolvé algo como "Firulais salió corriendo por la pelota."
            return "";
        }
    }

    static class Gato extends Animal {
        Gato(String nombre) {
            super(nombre);
        }

        @Override
        String hacerSonido() {
            return "Miau";
        }
    }

    public static void main(String[] args) {
        Animal[] animales = {
                new Perro("Firulais"),
                new Gato("Mishi"),
                new Perro("Lola"),
                new Gato("Garfield")
        };

        // TODO: un bucle que imprima el sonido de todos y, solo si el animal
        // es un Perro (instanceof pattern matching), también busque la pelota.

        /*
         * La versión ingenua (NO la hagas sin entender):
         *
         *   Perro apurado = (Perro) animales[1];   // animales[1] es un Gato
         *   apurado.buscarPelota();
         *
         * Compila perfecto... y lanza ClassCastException al correr.
         */
    }
}

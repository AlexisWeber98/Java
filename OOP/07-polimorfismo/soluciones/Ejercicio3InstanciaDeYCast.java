/*
 * ============================================================================
 * Módulo 07 – Polimorfismo | Ejercicio 3: instanceof y cast seguro (SOLUCIÓN)
 * ============================================================================
 * Idea clave: el cast a ciegas convence al compilador, no a la JVM.
 * instanceof con pattern matching pregunta ANTES y te da la variable lista.
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

        // Método EXCLUSIVO de Perro: existe solo en esta subclase.
        String buscarPelota() {
            return nombre + " salió corriendo por la pelota.";
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

        for (Animal animal : animales) {
            // El sonido lo resuelve el despacho dinámico: ni un if acá.
            System.out.println(animal.nombre + " dice: " + animal.hacerSonido());

            // Pattern matching para instanceof (Java 16+): si animal es Perro,
            // la variable perro nace YA convertida dentro del bloque.
            if (animal instanceof Perro perro) {
                System.out.println("   -> " + perro.buscarPelota());
            } else {
                System.out.println("   -> " + animal.nombre + " mira con cara de que no.");
            }
        }

        /*
         * ¿Por qué no castear directo? Descomentá esto y corrilo para verlo
         * explotar con tus propios ojos:
         *
         *   Perro apurado = (Perro) animales[1];   // animales[1] es un Gato
         *   apurado.buscarPelota();
         *
         * El compilador confía en tu cast porque un Gato PODRÍA ser un Perro
         * desde su punto de vista (misma jerarquía). La JVM, en cambio,
         * verifica el tipo REAL del objeto en ejecución y lanza
         * ClassCastException. Moraleja: preguntá primero (instanceof),
         * castees después; o mejor aún, dejá que el pattern matching haga
         * las dos cosas en un solo paso, como arriba.
         */
    }
}

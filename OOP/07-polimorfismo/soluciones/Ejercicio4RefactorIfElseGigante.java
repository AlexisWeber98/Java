/*
 * ============================================================================
 * Módulo 07 – Polimorfismo | Ejercicio 4: Refactor del if-else gigante (SOLUCIÓN)
 * ============================================================================
 * Idea clave: el if-else por tipo se reemplaza por jerarquía + despacho
 * dinámico. Sumar especies deja de significar editar código existente.
 */
public class Ejercicio4RefactorIfElseGigante {

    // ANTES (versión del ejercicio): un if por animal, y cada especie nueva
    // significaba abrir el archivo y estirar la cadena. Con 4 especies va bien;
    // con 40, es una bomba de mantenimiento esperando detonar.
    //
    // DESPUÉS: la jerarquía se encarga. Cada clase nueva es SOLO un archivo que
    // sabe hacer su propio sonido, y el bucle de abajo ni se entera.

    static abstract class Animal {
        abstract String hacerSonido();
    }

    static class Perro extends Animal {
        @Override
        String hacerSonido() {
            return "Guau";
        }
    }

    static class Gato extends Animal {
        @Override
        String hacerSonido() {
            return "Miau";
        }
    }

    static class Vaca extends Animal {
        @Override
        String hacerSonido() {
            return "Muu";
        }
    }

    static class Pato extends Animal {
        @Override
        String hacerSonido() {
            return "Cuac";
        }
    }

    // REQUISITO 5 (bonus): Dragon nació DESPUÉS del bucle, sin tocarlo.
    static class Dragon extends Animal {
        @Override
        String hacerSonido() {
            return "¡Roar!";
        }
    }

    public static void main(String[] args) {
        Animal[] animales = { new Perro(), new Gato(), new Vaca(), new Pato(), new Dragon() };

        for (Animal animal : animales) {
            System.out.printf("%s hace: %s%n",
                    animal.getClass().getSimpleName(), animal.hacerSonido());
        }

        /*
         * MORAL DEL REFACTOR:
         *   - Versión String + if-else: los DATOS no tienen comportamiento, así
         *     que el comportamiento termina amontonado en un solo método que
         *     crece para siempre y se rompe con cada cambio.
         *   - Versión polimórfica: cada tipo lleva su comportamiento adentro
         *     (cohesión) y agregar uno nuevo es extensión pura: cero riesgo de
         *     romper a los demás. A ese principio lo van a presentar como
         *     "abierto para extensión, cerrado para modificación" (OCP).
         */
    }
}

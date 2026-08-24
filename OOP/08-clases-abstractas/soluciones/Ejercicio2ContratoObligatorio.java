/*
 * ===========================================================================
 *  Ejercicio 2 — Contrato obligatorio · SOLUCIÓN
 *  Módulo 08 · Clases abstractas
 * ===========================================================================
 *
 *  Cambio clave: Gallina ahora extiende Animal y firma el contrato con
 *  @Override; VacaIncompleta demuestra qué pasa cuando una subclase concreta
 *  no cumple.
 *
 *  Ejecutá así:  java Ejercicio2ContratoObligatorio.java
 */

// La clase principal de la solución se llama distinto (y sin public) para
// que ejercicios y soluciones puedan compilarse juntos sin choque de
// nombres. El lanzador java toma igualmente la primera clase del archivo.
class Solucion2ContratoObligatorio {

    static abstract class Animal {
        protected final String nombre;

        protected Animal(String nombre) {
            this.nombre = nombre;
        }

        abstract String hacerSonido();

        void dormir() {
            System.out.println(nombre + " duerme plácidamente. Zzz...");
        }
    }

    static class Perro extends Animal {
        Perro() {
            super("Perro");
        }

        @Override
        String hacerSonido() {
            return "Guau guau";
        }
    }

    // SOLUCIÓN: Gallina es parte de la familia. El @Override no es decorativo:
    // le dice al compilador "esto implementa el contrato de la base", y si el
    // nombre o la firma no coinciden, te lo hace notar al instante.
    static class Gallina extends Animal {
        Gallina() {
            super("Gallina");
        }

        @Override
        String hacerSonido() {
            return "Cocorocó";
        }
    }

    // Prueba del contrato: si descomentamos esta clase, el compilador frena
    // el programa con este error (probalo, vale la pena verlo en vivo):
    //
    //     error: VacaIncompleta is not abstract and does not override
    //            abstract method hacerSonido() in Animal
    //
    // static class VacaIncompleta extends Animal {
    //     VacaIncompleta() {
    //         super("Vaca");
    //     }
    // }

    public static void main(String[] args) {
        // SOLUCIÓN: Perro y Gallina viven juntos en un arreglo del tipo de la
        // base. El bucle no sabe (ni le importa) cuál es cuál: a todos les
        // pide lo mismo porque el contrato se lo garantiza Animal.
        Animal[] animales = { new Perro(), new Gallina() };

        for (Animal animal : animales) {
            System.out.println(animal.nombre + " dice: " + animal.hacerSonido());
            animal.dormir();
        }
    }
}

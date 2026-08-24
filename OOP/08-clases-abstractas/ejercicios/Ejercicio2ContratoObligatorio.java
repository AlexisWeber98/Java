/*
 * ===========================================================================
 *  Ejercicio 2 — Contrato obligatorio
 *  Módulo 08 · Clases abstractas
 * ===========================================================================
 *
 *  ENUNCIADO
 *  Animal declara hacerSonido() como abstracto: ese método es un CONTRATO.
 *  Toda subclase concreta está obligada a implementarlo y el compilador
 *  verifica que así sea. Perro ya cumple; Gallina tiene un método con el
 *  mismo nombre pero todavía no forma parte de la familia.
 *
 *  REQUISITOS
 *  1. Hacé que Gallina extienda Animal y marcá su método con @Override.
 *  2. Sumá a Gallina al arreglo del main para comprobar que responde al
 *     mismo contrato que Perro.
 *  3. Descomentá el bloque de VacaIncompleta, compilá y estudiá el error;
 *     después volvé a comentarlo para dejar todo verde.
 *
 *  PISTAS
 *  - Tener un método llamado igual NO basta: el contrato se firma con
 *    extends + @Override.
 *  - Si una subclase concreta olvida implementar el método abstracto, el
 *    compilador avisa así:
 *        error: VacaIncompleta is not abstract and does not override
 *               abstract method hacerSonido() in Animal
 *  - Notá que Animal también aporta comportamiento CONCRETO compartido
 *    (dormir()): lo abstracto y lo concreto conviven sin problema.
 *
 *  Ejecutá así:  java Ejercicio2ContratoObligatorio.java
 */

public class Ejercicio2ContratoObligatorio {

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

    // TODO 1: Gallina debería pertenecer a la familia Animal. Hoy es una
    // clase suelta: su método coincide en nombre, pero no firma contrato
    // alguno. Hacela subclase de Animal y marcá el override.
    static class Gallina {
        String hacerSonido() {
            return "Cocorocó";
        }
    }

    // Requisito 3: descomentá este bloque y compilá para ver el error.
    //
    // static class VacaIncompleta extends Animal {
    //     VacaIncompleta() {
    //         super("Vaca");
    //     }
    //     // Sin hacerSonido() la clase no compila: el contrato es
    //     // obligatorio para toda subclase concreta.
    // }

    public static void main(String[] args) {
        Animal[] animales = { new Perro() };

        for (Animal animal : animales) {
            System.out.println(animal.nombre + " dice: " + animal.hacerSonido());
            animal.dormir();
        }

        // TODO 2: sumá la Gallina al arreglo de arriba y volvé a correr.
    }
}

// Ejemplo: jerarquía de animales — la clase base concentra lo común,
// las subclases redefinen solo su parte propia.
//
// Ejecutar:  java ejemplos/JerarquiaAnimales.java

public class JerarquiaAnimales {

    static class Animal {
        String nombre;

        Animal(String nombre) {
            this.nombre = nombre;
        }

        // Comportamiento compartido por TODA la jerarquía:
        // se escribe una vez y lo heredan todas las subclases.
        void dormir() {
            System.out.println(nombre + " duerme tranquilamente");
        }

        // Versión por defecto; cada subclase puede redefinirla.
        void hacerSonido() {
            System.out.println(nombre + " hace un sonido");
        }
    }

    static class Perro extends Animal {

        Perro(String nombre) {
            super(nombre);
        }

        @Override
        void hacerSonido() {
            System.out.println(nombre + " dice: Guau guau");
        }
    }

    static class Gato extends Animal {

        Gato(String nombre) {
            super(nombre);
        }

        @Override
        void hacerSonido() {
            System.out.println(nombre + " dice: Miau");
        }
    }

    public static void main(String[] args) {
        Perro firulais = new Perro("Firulais");
        Gato michi = new Gato("Michi");

        firulais.hacerSonido(); // redefinido en Perro
        michi.hacerSonido();    // redefinido en Gato

        // dormir() NO está en las subclases: viene heredado de Animal.
        // Código compartido, escrito una sola vez.
        firulais.dormir();
        michi.dormir();
    }
}

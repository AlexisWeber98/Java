/*
 * ============================================================================
 *  Ejercicio 1 — Constructor básico: Persona · SOLUCIÓN COMENTADA
 *  Módulo 04 · Constructores
 * ============================================================================
 *  Idea clave: el constructor recibe los datos y los instala ANTES de que
 *  alguien pueda usar el objeto. Desde afuera no hay forma de obtener una
 *  Persona "a medias": nace completa o no nace.
 * ============================================================================
 */

public class Ejercicio1ConstructorBasicoPersona {

    public static void main(String[] args) {
        Persona ana = new Persona("Ana", 34);
        Persona bruno = new Persona("Bruno", 27);

        ana.presentar();
        bruno.presentar();
    }
}

class Persona {

    // final: se asignan una única vez, acá adentro, y nunca más cambian.
    private final String nombre;
    private final int edad;

    Persona(String nombre, int edad) {
        // this.nombre (atributo) = nombre (parámetro): el prefijo this
        // desambigua cuando ambos se llaman igual.
        this.nombre = nombre;
        this.edad = edad;
    }

    void presentar() {
        System.out.printf("Hola, soy %s y tengo %d años.%n", nombre, edad);
    }
}

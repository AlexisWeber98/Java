/*
 * ============================================================================
 *  Ejercicio 1 — Constructor básico: Persona
 *  Módulo 04 · Constructores
 * ============================================================================
 *
 *  ENUNCIADO
 *  Sin constructor propio, toda clase nace con valores neutros (null, 0):
 *  nadie garantiza que el objeto arranque en un estado con sentido. El
 *  constructor es el acto de fundación del objeto.
 *
 *  Completá la clase Persona:
 *    - atributos privados nombre (String) y edad (int),
 *    - un constructor que reciba ambos valores,
 *    - el método presentar(), que imprima algo así:
 *
 *          Hola, soy Ana y tengo 34 años.
 *
 *  En el main, construí DOS personas con datos distintos y hacelas
 *  presentarse.
 *
 *  REQUISITOS
 *    1. Atributos privados desde el día uno (encapsulación).
 *    2. Constructor público con parámetros (nombre, edad).
 *    3. presentar() lee los atributos del objeto actual.
 *    4. Dos instancias en el main, cada una con sus propios datos.
 *
 *  PISTAS
 *    - El constructor NO lleva tipo de retorno: es Persona(String nombre, int edad).
 *    - Dentro del constructor, this.nombre = nombre desambigua el atributo
 *      del parámetro cuando se llaman igual.
 *    - printf arma mensajes prolijos: System.out.printf("Hola, soy %s...%n", nombre);
 *
 *  Ejecutalo:  java Ejercicio1ConstructorBasicoPersona.java
 * ============================================================================
 */

public class Ejercicio1ConstructorBasicoPersona {

    public static void main(String[] args) {
        // TODO: creá una persona "Ana" de 34 años

        // TODO: creá otra persona con otros datos

        // TODO: presentá a las dos y mirá la salida
    }
}

class Persona {

    private String nombre;
    private int edad;

    // TODO: constructor Persona(String nombre, int edad) que asigne los atributos

    // TODO: método presentar() que imprima la presentación
}

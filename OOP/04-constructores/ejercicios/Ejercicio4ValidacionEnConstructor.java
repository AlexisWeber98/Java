/*
 * ============================================================================
 *  Ejercicio 4 — Validación en el constructor
 *  Módulo 04 · Constructores
 * ============================================================================
 *
 *  ENUNCIADO
 *  El trabajo del constructor no es solo asignar: es GARANTIZAR que el
 *  objeto nazca en un estado válido. Si te pasan una edad de -5, ese objeto
 *  ya nació roto. Un adelanto amable del mundo de las excepciones: cuando un
 *  dato es imposible, lo correcto es rechazarlo y avisar con claridad.
 *
 *  Completá la clase Alumno:
 *    - En el constructor, si edad < 0, lanzá:
 *
 *          throw new IllegalArgumentException("La edad no puede ser negativa, recibimos: " + edad);
 *
 *    - Recién después de esa guardá, asigná los atributos.
 *
 *  En el main:
 *    - creá un alumno válido y mostrá su estado,
 *    - intentá crear uno con edad negativa usando try/catch, y mostrá el
 *      mensaje de la excepción.
 *
 *  REQUISITOS
 *    1. La validación ocurre ANTES de cualquier asignación.
 *    2. El mensaje de la excepción incluye el valor recibido.
 *    3. El main demuestra el caso válido y el rechazado.
 *
 *  PISTAS
 *    - IllegalArgumentException vive en java.lang: no hace falta importarla.
 *    - ¿Y en vez de lanzar, "normalizamos" (por ejemplo, Math.max(edad, 0))?
 *      Mirá el comentario de la solución: la respuesta corta es que callar un
 *      dato sospechoso esconde bugs.
 *
 *  Ejecutalo:  java Ejercicio4ValidacionEnConstructor.java
 * ============================================================================
 */

public class Ejercicio4ValidacionEnConstructor {

    public static void main(String[] args) {
        // TODO: creá un alumno válido y mostrá su estado

        // TODO: en un bloque try, intentá crear un alumno con edad negativa;
        //       en el catch, imprimí el mensaje de la excepción
    }
}

class Alumno {

    private String nombre;
    private int edad;

    Alumno(String nombre, int edad) {
        // TODO: guardá — si edad < 0, lanzá IllegalArgumentException con mensaje claro

        this.nombre = nombre;
        this.edad = edad;
    }

    // TODO: método mostrarEstado() que imprima nombre y edad
}

/*
 * ============================================================================
 *  Ejercicio 3 — Métodos genéricos: intercambiar y contarSi
 * ============================================================================
 *
 *  ENUNCIADO
 *  --------
 *  Los métodos TAMBIÉN pueden ser genéricos, sin que la clase lo sea.
 *  Creá dos utilitarios estáticos:
 *
 *      static <T> void intercambiar(T[] arreglo, int i, int j)
 *          → deja en i lo que había en j, y viceversa.
 *
 *      static <T> int contarSi(T[] arreglo, java.util.function.Predicate<T> condicion)
 *          → cuenta cuántos elementos cumplen la condición (usa test()).
 *
 *  Demostrá ambos con un String[] y con un Integer[]:
 *      - Intercambiá posiciones de un array de nombres y mostralo antes/después.
 *      - Contá cuántos nombres arrancan con mayúscula.
 *      - Contá cuántas notas son mayores o iguales a 6.
 *
 *  REQUISITOS
 *  ----------
 *      - La <T> va ANTES del tipo de retorno: static <T> void ...
 *      - NADA de casts adentro de los utilitarios: T es un tipo más.
 *      - Las condiciones pasálas como lambdas: nombre -> ..., nota -> ...
 *
 *  PISTAS
 *  ------
 *      - Necesitás una variable temporal... ¡de tipo T! Ni Object ni nada.
 *      - Predicate<T> tiene un método test(T elemento) que devuelve boolean.
 *      - Cuidado: T[] funciona con Integer[] pero NO con int[] — los
 *        parámetros de tipo solo aceptan tipos de referencia.
 * ============================================================================
 */
public class Ejercicio3UtilitarioGenerico {

    // TODO 1: implementá intercambiar(T[] arreglo, int i, int j).


    // TODO 2: implementá contarSi(T[] arreglo, Predicate<T> condicion).


    public static void main(String[] args) {
        // TODO 3: demo con String[] — intercambio + conteo con lambda.


        // TODO 4: demo con Integer[] — conteo de aprobados con lambda.


        System.out.println("Completá los TODOs y volvé a ejecutar.");
    }
}

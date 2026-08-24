/*
 * ============================================================================
 * Módulo 03 — Ejercicio 4: Varargs (promedio de notas)
 * ============================================================================
 *
 * ENUNCIADO:
 *   Implementá promedioNotas(double... notas) que devuelva el promedio de
 *   la cantidad de notas que sea: cero, una o muchas.
 *
 * REQUISITOS:
 *   1. Firma con varargs: static double promedioNotas(double... notas).
 *   2. Lista vacía NO puede romper el programa: devolvé un valor por
 *      defecto y avisá por consola (documentá la decisión).
 *   3. Desde main llamá al método con 0, 1 y muchas notas.
 *   4. No armés arreglos a mano en main: el varargs los crea por vos.
 *
 * PISTAS:
 *   - Dentro del método, notas es un double[] común: length y for-each
 *     funcionan como siempre.
 *   - La llamada promedioNotas() es válida: el arreglo queda de largo 0.
 *   - Reglas del varargs: uno solo por método y siempre último parámetro.
 *   - Podés pasar enteros en la llamada (7, 10): se ensanchan a double.
 */
public class Ejercicio4PromedioVarargs {

    // TODO: implementá promedioNotas(double... notas) defendiendo el caso
    //  de lista vacía.


    public static void main(String[] args) {
        // TODO: llamá a promedioNotas() sin argumentos e imprimí el resultado.

        // TODO: llamá con una sola nota e imprimí el resultado.

        // TODO: llamá con varias notas (al menos cuatro) e imprimí el promedio.
    }
}

/*
 * ============================================================================
 * Ejercicio 3 — Cuatro formas de recorrer una lista
 * ============================================================================
 *
 * ENUNCIADO:
 *   Con una misma lista de lenguajes de programación, implementá cuatro
 *   recorridos distintos, cada uno con su encabezado:
 *     A. For clásico con índice.
 *     B. For mejorado (enhanced-for).
 *     C. Iterator, eliminando de forma SEGURA los lenguajes que empiezan
 *        con "J" mientras se recorre.
 *     D. forEach con lambda.
 *
 * REQUISITOS:
 *   - Cada estilo imprime primero un encabezado propio.
 *   - En C, la eliminación durante la iteración debe ser segura (sin
 *     ConcurrentModificationException): usá iterador.remove().
 *   - Partir siempre del mismo estado: usá crearLenguajes() para obtener una
 *     lista nueva antes de cada recorrido.
 *
 * PISTAS:
 *   - Nunca llames lista.remove(...) dentro de un for mejorado: lanza
 *     ConcurrentModificationException. Con Iterator, el método correcto
 *     es iterador.remove().
 *   - iter.hasNext() pregunta si queda otro elemento; iter.next() lo obtiene.
 *   - lenguaje.startsWith("J") resuelve el filtro del punto C.
 *   - forEach espera una lambda: lenguajes.forEach(x -> System.out.println(x))
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ejercicio3CuatroFormasDeRecorrer {

    public static void main(String[] args) {
        // TODO A: recorrerConForClasico(crearLenguajes());

        // TODO B: recorrerConForEachMejorado(crearLenguajes());

        // TODO C: eliminarLenguajesConJ(crearLenguajes()) y mostrar el estado final

        // TODO D: recorrerConForEachLambda(crearLenguajes());
    }

    /**
     * Devuelve una lista nueva con el mismo contenido inicial,
     * para que cada recorrido parta del mismo estado.
     */
    private static List<String> crearLenguajes() {
        return new ArrayList<>(Arrays.asList("Java", "Python", "JavaScript", "Kotlin", "Go"));
    }

    private static void recorrerConForClasico(List<String> lenguajes) {
        System.out.println("--- A) For clásico con índice ---");
        // TODO: for con i desde 0 hasta size() - 1; imprimí get(i)
    }

    private static void recorrerConForEachMejorado(List<String> lenguajes) {
        System.out.println("--- B) For mejorado ---");
        // TODO: for (String lenguaje : lenguajes) { ... }
    }

    private static List<String> eliminarLenguajesConJ(List<String> lenguajes) {
        System.out.println("--- C) Iterator con eliminación segura ---");
        // TODO: obtené el iterator, avanzá con next() y eliminá con remove()
        //       los lenguajes que empiecen con "J"; mostrá cada eliminación
        return lenguajes;
    }

    private static void recorrerConForEachLambda(List<String> lenguajes) {
        System.out.println("--- D) forEach con lambda ---");
        // TODO: lenguajes.forEach(lenguaje -> ...)
    }
}

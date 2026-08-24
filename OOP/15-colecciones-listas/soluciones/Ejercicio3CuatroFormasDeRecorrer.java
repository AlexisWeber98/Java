/*
 * ============================================================================
 * Ejercicio 3 — Cuatro formas de recorrer una lista (SOLUCIÓN)
 * ============================================================================
 * Mismo conjunto de datos recorrido con: for clásico, enhanced-for,
 * Iterator con eliminación segura y forEach con lambda.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Ejercicio3CuatroFormasDeRecorrer {

    public static void main(String[] args) {
        // Cada recorrido parte de una lista nueva: mismo estado inicial garantizado.
        recorrerConForClasico(crearLenguajes());
        recorrerConForEachMejorado(crearLenguajes());

        List<String> filtrados = eliminarLenguajesConJ(crearLenguajes());
        System.out.println("Estado final tras el filtro: " + filtrados);

        recorrerConForEachLambda(crearLenguajes());
    }

    private static List<String> crearLenguajes() {
        return new ArrayList<>(Arrays.asList("Java", "Python", "JavaScript", "Kotlin", "Go"));
    }

    private static void recorrerConForClasico(List<String> lenguajes) {
        System.out.println("--- A) For clásico con índice ---");
        // Elegilo cuando necesitás el índice o querés modificar posiciones.
        for (int i = 0; i < lenguajes.size(); i++) {
            System.out.println(i + ": " + lenguajes.get(i));
        }
    }

    private static void recorrerConForEachMejorado(List<String> lenguajes) {
        System.out.println("--- B) For mejorado (enhanced-for) ---");
        // Elección por defecto: más legible, sin manejo manual de índices.
        for (String lenguaje : lenguajes) {
            System.out.println("- " + lenguaje);
        }
    }

    private static List<String> eliminarLenguajesConJ(List<String> lenguajes) {
        System.out.println("--- C) Iterator con eliminación segura ---");
        Iterator<String> iterador = lenguajes.iterator();
        while (iterador.hasNext()) {
            String lenguaje = iterador.next();
            if (lenguaje.startsWith("J")) {
                // CLAVE: remove() del ITERADOR, no de la lista. Llamar a
                // lenguajes.remove(...) acá lanzaría ConcurrentModificationException.
                iterador.remove();
                System.out.println("Eliminado: " + lenguaje);
            }
        }
        return lenguajes;
    }

    private static void recorrerConForEachLambda(List<String> lenguajes) {
        System.out.println("--- D) forEach con lambda ---");
        lenguajes.forEach(lenguaje -> System.out.println("* " + lenguaje));
    }
}

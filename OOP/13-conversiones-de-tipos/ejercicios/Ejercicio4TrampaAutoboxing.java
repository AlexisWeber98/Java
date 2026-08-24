/*
 * Módulo 13 — Conversiones de tipos
 * Ejercicio 4: La trampa del autoboxing
 *
 * ENUNCIADO:
 * Dos pares de Integer con EXACTAMENTE el mismo valor... ¿se van a comportar
 * igual con ==? Escribí tu predicción (true/false) en CADA comentario TODO
 * ANTES de ejecutar. Después ejecutá y preparate: uno de los resultados
 * suele romper la intuición de todo el mundo.
 *
 * REQUISITOS:
 * - Predecir las cinco comparaciones sin ejecutar el programa.
 * - Ejecutar y comparar cada resultado con tu predicción.
 * - Explicar en un comentario final por qué las dos primeras comparaciones,
 *   aparentemente idénticas, dan resultados distintos.
 *
 * PISTAS:
 * - Integer es una clase (wrapper): == entre objetos compara REFERENCIAS
 *   (¿es la misma caja?), no el contenido.
 * - El autoboxing no usa new: llama a Integer.valueOf(...), que tiene una
 *   caché interna para ciertos valores.
 * - Cuando comparás un Integer con un int primitivo, el wrapper se
 *   desempaqueta automáticamente antes de comparar.
 */
public class Ejercicio4TrampaAutoboxing {

    public static void main(String[] args) {
        Integer primeraCajaA = 127;   // autoboxing: esto es realmente Integer.valueOf(127)
        Integer primeraCajaB = 127;
        Integer segundaCajaC = 128;
        Integer segundaCajaD = 128;

        System.out.println("127 == 127 : " + (primeraCajaA == primeraCajaB));   // TODO Predicción 1: ____
        System.out.println("128 == 128 : " + (segundaCajaC == segundaCajaD));   // TODO Predicción 2: ____

        System.out.println("127.equals(127): " + primeraCajaA.equals(primeraCajaB));   // TODO Predicción 3: ____
        System.out.println("128.equals(128): " + segundaCajaC.equals(segundaCajaD));   // TODO Predicción 4: ____

        // Bonus: mezclar wrapper con primitivo cambia las reglas del juego.
        int primitivo = 128;
        System.out.println("Integer 128 == int 128: " + (segundaCajaC == primitivo));   // TODO Predicción 5: ____

        // TODO 6: explicá acá, con tus palabras, por qué Predicción 1 y
        //   Predicción 2 dieron resultados DISTINTOS siendo código idéntico
        //   salvo el número. ¿Qué tiene de especial ese rango?
    }
}

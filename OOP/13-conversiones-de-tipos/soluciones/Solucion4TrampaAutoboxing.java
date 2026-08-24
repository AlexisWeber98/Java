/*
 * Módulo 13 — Conversiones de tipos
 * Ejercicio 4: La trampa del autoboxing (SOLUCIÓN)
 *
 * SALIDA REAL DEL PROGRAMA:
 *   127 == 127 : true
 *   128 == 128 : false     <- la sorpresa
 *   127.equals(127): true
 *   128.equals(128): true
 *   Integer 128 == int 128: true
 *
 * CLAVES DE LA SOLUCIÓN:
 * - Autoboxing: `Integer x = 127;` NO crea un objeto con new. Compila como
 *   `Integer.valueOf(127)`.
 * - La especificación de Java (JLS 5.1.7) OBLIGA a Integer.valueOf a devolver
 *   LA MISMA referencia para valores entre -128 y 127 (la famosa caché
 *   IntegerCache). Por eso 127 == 127 da true: son la misma caja.
 * - Fuera de ese rango no hay garantía de identidad: cada boxing crea un
 *   objeto nuevo. 128 == 128 compara dos referencias distintas -> false.
 *   (El límite superior de la caché es configurable en la JVM HotSpot con
 *   -XX:AutoBoxCacheMax, pero el rango -128..127 es lo único garantizado.)
 * - equals SIEMPRE compara el valor contenido: true en ambos casos.
 * - Bonus: cuando un lado es primitivo, el wrapper se DESempaqueta
 *   automáticamente (intValue()) y el == pasa a comparar ints: true.
 * - Regla práctica para siempre: == solo entre primitivos; para objetos,
 *   equals. Y ojo extra: si el wrapper fuera null, desempaquetarlo lanza
 *   NullPointerException.
 */
public class Solucion4TrampaAutoboxing {

    public static void main(String[] args) {
        Integer primeraCajaA = 127;
        Integer primeraCajaB = 127;
        Integer segundaCajaC = 128;
        Integer segundaCajaD = 128;

        System.out.println("127 == 127 : " + (primeraCajaA == primeraCajaB));
        System.out.println("128 == 128 : " + (segundaCajaC == segundaCajaD));

        System.out.println("127.equals(127): " + primeraCajaA.equals(primeraCajaB));
        System.out.println("128.equals(128): " + segundaCajaC.equals(segundaCajaD));

        // Con un primitivo de por medio, segundaCajaC se desempaqueta a int
        // y recién entonces se comparan los valores: true, sin sorpresa.
        int primitivo = 128;
        System.out.println("Integer 128 == int 128: " + (segundaCajaC == primitivo));
    }
}

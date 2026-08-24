/*
 * Módulo 13 — Conversiones de tipos
 * Ejercicio 3: Parsing seguro
 *
 * ENUNCIADO:
 * Convertir Strings a enteros es una conversión diaria (¡todo lo que entra por
 * teclado o por red llega como texto!). El problema: Integer.parseInt lanza
 * NumberFormatException cuando el texto no sirve. Vas a implementar DOS
 * estrategias para convertir sin que el programa explote NUNCA:
 *
 *   1) convertirAEntero(String): devuelve el número, o -1 como valor centinela
 *      si no se puede convertir.
 *   2) esEnteroValido(String): devuelve true/false según se pueda convertir.
 *
 * REQUISITOS:
 * - Implementar ambos métodos SIN que el programa termine con excepción.
 * - Probar con: "42", "hola", "", "3.14" y null (el main ya lo hace).
 * - Ningún caso puede tirar NullPointerException ni NumberFormatException.
 *
 * PISTAS:
 * - try/catch con catch (NumberFormatException e) es tu red de seguridad.
 * - Cuidado con null y con texto vacío: chequealo ANTES de parsear.
 * - El centinela -1 tiene una falla de diseño escondida... ¿qué pasa si el
 *   usuario legítimamente teclea "-1"? Por eso existe la variante booleana.
 */
public class Ejercicio3ParsingSeguro {

    public static void main(String[] args) {
        String[] casosDePrueba = {"42", "hola", "", "3.14", null};

        for (String texto : casosDePrueba) {
            System.out.println("convertirAEntero(" + texto + ") = " + convertirAEntero(texto));
            System.out.println("esEnteroValido(" + texto + ")  = " + esEnteroValido(texto));
            System.out.println("---");
        }
    }

    // TODO 1: implementar. Devuelve el entero convertido, o -1 si el texto
    //   no representa un entero válido (incluye null y vacío).
    static int convertirAEntero(String texto) {
        return -1;
    }

    // TODO 2: implementar. Devuelve true solo si el texto representa un
    //   entero válido. Nunca deja escapar una excepción.
    static boolean esEnteroValido(String texto) {
        return false;
    }
}

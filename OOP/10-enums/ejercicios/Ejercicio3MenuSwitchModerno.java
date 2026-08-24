/*
 * ============================================================================
 * Ejercicio 3 — Menú bancario con switch de flechas
 * ============================================================================
 *
 * ENUNCIADO
 * Desde Java 14 el switch moderno usa flechas, no cae entre casos y hasta
 * puede DEVOLVER un valor (switch expresión). Vas a procesar la opción que
 * "ingresa" un usuario en un cajero: convertir el texto al enum Opcion con
 * valueOf() y resolver el mensaje de cada caso con un switch de flechas.
 * Bonus realista: el texto llega con espacios, en minúsculas... o directamente
 * es una opción que no existe.
 *
 * REQUISITOS
 *   1. Enum Opcion con VER_SALDO, DEPOSITAR y SALIR.
 *   2. procesarEntrada(String) debe:
 *      a) normalizar el texto (trim + toUpperCase),
 *      b) convertirlo a Opcion con valueOf(),
 *      c) capturar IllegalArgumentException si el texto no coincide con
 *         ninguna constante, devolviendo un mensaje amable,
 *      d) resolver el mensaje con un switch de flechas (switch expresión).
 *   3. main ya prueba: "VER_SALDO", "  depositar ", "SALIR" y
 *      "PEDIR_PRESTAMO" (no existe: debe fallar con elegancia).
 *
 * PISTAS
 *   - valueOf() distingue mayúsculas: "depositar" NO es "DEPOSITAR". Por eso
 *     primero normalizamos con trim() + toUpperCase().
 *   - Si cubrís TODAS las constantes del enum, el switch expresión compila
 *     sin default: el compilador verifica la exhaustividad por vos.
 *   - Con flechas no hace falta break; cada caso termina en sí mismo.
 */
public class Ejercicio3MenuSwitchModerno {

    enum Opcion {
        VER_SALDO,
        DEPOSITAR,
        SALIR
    }

    static String procesarEntrada(String textoIngresado) {
        // TODO a): normalizá el texto con trim() y toUpperCase().
        // TODO b): convertilo a Opcion con valueOf(...) dentro de un try,
        //          porque valueOf lanza IllegalArgumentException si el texto
        //          no coincide con ninguna constante.
        // TODO c): en el catch devolvé un mensaje amable tipo:
        //          "\"PEDIR_PRESTAMO\" no es una opción válida del menú."
        // TODO d): con la opción lograda, usá un switch expresión de flechas
        //          que DEVUELVA el mensaje correspondiente a cada caso.
        return "";
    }

    public static void main(String[] args) {
        String[] entradas = {"VER_SALDO", "  depositar ", "SALIR", "PEDIR_PRESTAMO"};

        for (String entrada : entradas) {
            System.out.printf("El usuario escribió \"%s\"%n", entrada);
            System.out.println("   -> " + procesarEntrada(entrada));
            System.out.println();
        }
    }
}

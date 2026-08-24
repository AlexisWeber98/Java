/*
 * ConversionesPrimitivos.java
 * Módulo 13 · Conversiones de tipos
 *
 * Muestra la escalera de ensanchamiento (widening) automática y el
 * estrechamiento (narrowing) con cast, donde la pérdida es silenciosa.
 */
public class ConversionesPrimitivos {

    public static void main(String[] args) {
        demostrarEnsanchamiento();
        System.out.println();
        demostrarEstrechamientoConPerdida();
        System.out.println();
        demostrarDualidadChar();
    }

    /*
     * Widening: cada tipo entra en el siguiente sin pérdida.
     * El compilador convierte solo, sin cast.
     */
    static void demostrarEnsanchamiento() {
        System.out.println("=== ENSANCHAMIENTO (automático, seguro) ===");

        byte chico = 120;
        short mediano = chico;      // byte -> short
        int entero = mediano;       // short -> int
        long grande = entero;       // int -> long
        float decimal = grande;     // long -> float
        double preciso = decimal;   // float -> double

        System.out.println("byte   : " + chico);
        System.out.println("short  : " + mediano);
        System.out.println("int    : " + entero);
        System.out.println("long   : " + grande);
        System.out.println("float  : " + decimal);
        System.out.println("double : " + preciso);
        System.out.println("-> Ningún valor se perdió: siempre hay lugar en el tipo más grande.");
    }

    /*
     * Narrowing: exige cast y pierde información SIN avisar.
     * Trunca decimales y desborda por wraparound.
     */
    static void demostrarEstrechamientoConPerdida() {
        System.out.println("=== ESTRECHAMIENTO (cast explícito, pérdida silenciosa) ===");

        double precio = 9.99;
        int precioTruncado = (int) precio;
        System.out.println("(int) 9.99  = " + precioTruncado + "   <- trunca, NO redondea");
        System.out.println("Math.round(9.99) = " + Math.round(precio) + " <- si querés redondear");

        int enorme = 300;
        byte desbordado = (byte) enorme;
        System.out.println("\n(byte) 300  = " + desbordado + "  <- overflow: byte va de -128 a 127");
        System.out.println("300 - 256 = 44: los bits que no entran se cortan.");

        long millonLargo = 9_000_000_000L;
        int millonCortado = (int) millonLargo;
        System.out.println("(int) 9000000000L = " + millonCortado + " <- irreconocible");
        System.out.println("-> Firmaste con el cast: el compilador no avisa nada.");
    }

    /*
     * char es un número disfrazado de letra (código Unicode).
     */
    static void demostrarDualidadChar() {
        System.out.println("=== DUALIDAD char <-> int ===");

        char letra = 'A';
        int codigoLetra = letra;              // automático: char cabe en int
        char siguienteLetra = (char) (letra + 1); // aritmética: necesita cast

        System.out.println("char 'A' como int  : " + codigoLetra);
        System.out.println("'A' + 1 como int   : " + (letra + 1));
        System.out.println("(char) ('A' + 1)   : " + siguienteLetra);
        System.out.println("-> 'A' + 1 suma códigos (66), no concatena texto.");
    }
}

/*
 * Módulo 13 — Conversiones de tipos
 * Ejercicio 1: La escalera de conversiones (SOLUCIÓN)
 *
 * SALIDA REAL DEL PROGRAMA:
 *   short : 125
 *   int   : 125
 *   long  : 125
 *   float : 125.0
 *   double: 125.0
 *   long  original: 9000000007
 *   float copia   : 9.000001E9
 *   (int) 3.99  = 3
 *   (int) -8.75 = -8
 *   (byte) 130  = -126
 *
 * EXPLICACIÓN DE CADA RESULTADO:
 * - Subir por la escalera (byte -> short -> int -> long) es exacto: cada tipo
 *   contiene al anterior sin perder ni un bit.
 * - long -> float es implícito pero NO exacto: float solo tiene 24 bits de
 *   mantisa (unos 7 dígitos decimales). 9.000.000.007 necesita más, así que se
 *   redondea al múltiplo de 1024 más cercano en ese rango, y se imprime como
 *   9.000001E9. Widening no significa "sin pérdida": significa "entra".
 * - Con 125 no hay sorpresa en float/double: pasa a ser 125.0 porque ahora el
 *   tipo representa decimales.
 * - (int) 3.99 = 3: el cast TRUNCA, no redondea.
 * - (int) -8.75 = -8: trunca hacia CERO, no hacia abajo (por eso no da -9).
 * - (byte) 130 = -126: un int de 32 bits recortado a los 8 bits bajos de un
 *   byte. 130 en binario es 10000010; ese primer 1 pasa a ser el bit de signo,
 *   y en complemento a dos representa -126. El compilador exige el cast
 *   precisamente para avisarte: "sabés que podés estar rompiendo el valor".
 */
public class Solucion1EscaleraDeConversiones {

    public static void main(String[] args) {
        // --- Tramo ascendente: conversiones implícitas ---
        byte pasoByte = 125;
        short pasoShort = pasoByte;   // Resultado: 125 (exacto)
        int pasoInt = pasoShort;      // Resultado: 125 (exacto)
        long pasoLong = pasoInt;      // Resultado: 125 (exacto)
        float pasoFloat = pasoLong;   // Resultado: 125.0 (ahora es un flotante)
        double pasoDouble = pasoFloat;

        System.out.println("short : " + pasoShort);
        System.out.println("int   : " + pasoInt);
        System.out.println("long  : " + pasoLong);
        System.out.println("float : " + pasoFloat);
        System.out.println("double: " + pasoDouble);

        // --- Sorpresa anunciada: un long enorme entra en un float sin cast ---
        long largoGrande = 9_000_000_007L;
        float flotanteDesdeLargo = largoGrande;   // ¡Pierde precisión aunque sea implícito!
        System.out.println("long  original: " + largoGrande);
        System.out.println("float copia   : " + flotanteDesdeLargo);

        // --- Tramo descendente: ahora sí, casts explícitos ---
        double precioConDecimales = 3.99;
        int enteroTruncado = (int) precioConDecimales;   // Trunca: 3 (no redondea)
        System.out.println("(int) 3.99  = " + enteroTruncado);

        double saldoNegativo = -8.75;
        int enteroNegativo = (int) saldoNegativo;        // Hacia cero: -8 (no -9)
        System.out.println("(int) -8.75 = " + enteroNegativo);

        int fueraDeRango = 130;
        byte byteDesbordado = (byte) fueraDeRango;       // Solo entran 8 bits: -126
        System.out.println("(byte) 130  = " + byteDesbordado);
    }
}

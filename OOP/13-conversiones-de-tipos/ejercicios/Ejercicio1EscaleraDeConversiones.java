/*
 * Módulo 13 — Conversiones de tipos
 * Ejercicio 1: La escalera de conversiones
 *
 * ENUNCIADO:
 * Tenés una cadena de asignaciones que sube por la escalera de tipos
 * (byte -> short -> int -> long -> float -> double) y después vuelve a bajar
 * con casts explícitos. ANTES de ejecutar nada, escribí tu predicción de cada
 * línea impresa en los comentarios marcados con TODO. Recién después compilá,
 * ejecutá y compará con lo que pensabas. Si te sorprendió algo, anotalo.
 *
 * REQUISITOS:
 * - Completar TODAS las predicciones sin ejecutar el programa primero.
 * - Ejecutar y verificar cada predicción junto al TODO correspondiente.
 * - Explicar en un comentario final por qué long -> float puede perder
 *   precisión aunque sea una conversión implícita.
 *
 * PISTAS:
 * - Las conversiones que "ensanchan" (widening) son implícitas... pero
 *   ensanchar no siempre es sinónimo de exacto.
 * - float usa 32 bits en total y solo 24 para la mantisa: unos 7 dígitos
 *   decimales confiables.
 * - El cast a entero TRUNCA (no redondea) y va hacia cero.
 * - El cast a un tipo más chico conserva solo los bits que entran
 *   (complemento a dos: el bit más alto pasa a ser el signo).
 */
public class Ejercicio1EscaleraDeConversiones {

    public static void main(String[] args) {
        // --- Tramo ascendente: conversiones implícitas ---
        byte pasoByte = 125;
        short pasoShort = pasoByte;   // TODO Predicción 1: ¿qué imprime "short : ..."?
        int pasoInt = pasoShort;      // TODO Predicción 2: ¿y esta?
        long pasoLong = pasoInt;
        float pasoFloat = pasoLong;   // TODO Predicción 3: ¿125? ¿125.0? ¿otra cosa?
        double pasoDouble = pasoFloat;

        System.out.println("short : " + pasoShort);
        System.out.println("int   : " + pasoInt);
        System.out.println("long  : " + pasoLong);
        System.out.println("float : " + pasoFloat);
        System.out.println("double: " + pasoDouble);

        // --- Sorpresa anunciada: un long enorme entra en un float sin cast ---
        long largoGrande = 9_000_000_007L;
        float flotanteDesdeLargo = largoGrande;   // TODO Predicción 4: ¿se conserva el valor exacto?
        System.out.println("long  original: " + largoGrande);
        System.out.println("float copia   : " + flotanteDesdeLargo);

        // --- Tramo descendente: ahora sí, casts explícitos ---
        double precioConDecimales = 3.99;
        int enteroTruncado = (int) precioConDecimales;   // TODO Predicción 5: ¿3? ¿4?
        System.out.println("(int) 3.99  = " + enteroTruncado);

        double saldoNegativo = -8.75;
        int enteroNegativo = (int) saldoNegativo;        // TODO Predicción 6: ¿-8? ¿-9?
        System.out.println("(int) -8.75 = " + enteroNegativo);

        int fueraDeRango = 130;
        byte byteDesbordado = (byte) fueraDeRango;       // TODO Predicción 7: ¿error? ¿130? ¿otro valor?
        System.out.println("(byte) 130  = " + byteDesbordado);

        // TODO 8: explicá acá, con tus palabras, por qué la conversión
        // long -> float pierde precisión sin necesidad de cast.
    }
}

/*
 * AutoboxingYTusTrampas.java
 * Módulo 13 · Conversiones de tipos
 *
 * El autoboxing convierte int <-> Integer sin que lo pidas, pero esconde
 * dos trampas: el == que miente fuera del caché y la NPE al descajonar null.
 */
public class AutoboxingYTusTrampas {

    public static void main(String[] args) {
        demostrarMecanica();
        System.out.println();
        demostrarTrampaDelIgualIgual();
        System.out.println();
        demostrarTrampaDelNull();
    }

    static void demostrarMecanica() {
        System.out.println("=== MECÁNICA DEL AUTOBOXING ===");

        Integer caja = 5;      // autoboxing: int -> Integer (el compilador llama Integer.valueOf(5))
        int pelado = caja;     // unboxing:   Integer -> int (llama caja.intValue())

        caja++;                // se descajona, suma, y se vuelve a cajar
        System.out.println("Integer caja = 5  -> " + caja);
        System.out.println("int pelado = caja -> " + pelado);
        System.out.println("-> Java convierte solo, pero cada paso tiene costo.");
    }

    /*
     * TRAMPA 1: Java cachea Integer de -128 a 127.
     * Dentro del caché, dos variables apuntan al MISMO objeto y == da true.
     * Afuera, son objetos distintos y == da false aunque el valor sea igual.
     */
    static void demostrarTrampaDelIgualIgual() {
        System.out.println("=== TRAMPA 1: EL == MIENTE CON WRAPPERS ===");

        Integer primeraChica = 127;
        Integer segundaChica = 127;
        System.out.println("127 == 128? No. 127 == 127 con == : "
                + (primeraChica == segundaChica) + "   <- mismo objeto del caché");

        Integer primeraGrande = 128;
        Integer segundaGrande = 128;
        System.out.println("128 == 128 con ==  : " + (primeraGrande == segundaGrande)
                + "  <- ¡objetos distintos!");
        System.out.println("128 equals 128     : " + primeraGrande.equals(segundaGrande)
                + " <- SIEMPRE así");

        System.out.println("-> Regla: wrappers se comparan con .equals(), nunca con ==.");
    }

    /*
     * TRAMPA 2: unboxing de null lanza NullPointerException.
     * Descomentá las líneas marcadas para verlo explotar.
     */
    static void demostrarTrampaDelNull() {
        System.out.println("=== TRAMPA 2: NULL AL DESCAJONAR ===");

        Integer valorPosible = buscarEnMemoria();
        System.out.println("buscarEnMemoria() devolvió: " + valorPosible);

        // int rompio = valorPosible;
        // -> NPE: el compilador traduce a valorPosible.intValue() sobre null.

        if (valorPosible != null) {
            int seguro = valorPosible; // unboxing solo si hay valor
            System.out.println("Unboxing seguro tras validar: " + seguro);
        } else {
            System.out.println("Sin dato: evitamos el NPE validando antes de descajonar.");
        }
    }

    static Integer buscarEnMemoria() {
        return null; // simulamos que no había nada guardado
    }
}

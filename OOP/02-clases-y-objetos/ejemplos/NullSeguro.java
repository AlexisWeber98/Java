/**
 * NullSeguro.java
 *
 * Demo 3 del Módulo 02: primer contacto con null y NullPointerException.
 * Patrón defensivo: verificá antes de desreferenciar.
 *
 * Ejecutar: java NullSeguro.java
 */
public class NullSeguro {

    static class Socio {
        String nombre;
    }

    // Busca un socio por nombre; si no existe, devuelve null.
    // Convención honesta: el que llama DEBE saber que puede venir null.
    static Socio buscarSocio(Socio[] padron, String nombreBuscado) {
        for (Socio socio : padron) {
            if (socio != null && socio.nombre.equals(nombreBuscado)) {
                return socio;
            }
        }
        return null;   // "no lo encontré": la referencia no apunta a nada
    }

    public static void main(String[] args) {
        Socio[] padron = new Socio[2];
        padron[0] = new Socio();
        padron[0].nombre = "Lucía";
        // padron[1] quedó en null: el arreglo es de referencias, no de socios

        System.out.println("=== Caso 1: uso SEGURO (verificás antes de usar) ===");
        Socio encontrada = buscarSocio(padron, "Lucía");
        if (encontrada != null) {
            System.out.println("Encontrada: " + encontrada.nombre);
        } else {
            System.out.println("Socia inexistente, avisamos amablemente.");
        }

        System.out.println("\n=== Caso 2: uso SEGURO del resultado vacío ===");
        Socio fantasma = buscarSocio(padron, "Ramiro");
        if (fantasma == null) {
            System.out.println("Ramiro no está en el padrón. Sin drama.");
        }

        System.out.println("\n=== Caso 3: la versión que REVIENTA (comentada) ===");
        /*
         * Socio inexistente = buscarSocio(padron, "Ramiro");
         * System.out.println(inexistente.nombre);
         *
         * 💥 Exception in thread "main" java.lang.NullPointerException:
         *    Cannot read field "nombre" because "inexistente" is null
         *
         * Java te está diciendo: "me pediste leer .nombre de una referencia
         * que no apunta a ningún objeto". Descomentá las dos líneas si querés
         * verlo con tus propios ojos.
         */

        System.out.println("=== Moraleja ===");
        System.out.println("null no es un objeto: es la ausencia de objeto.");
        System.out.println("Antes del punto, preguntate: ¿podría ser null?");
    }
}

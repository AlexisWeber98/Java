/*
 * ============================================================================
 * Módulo 14 · Excepciones — Solución 4: Traducir y relanzar (encadenamiento)
 * ============================================================================
 * IDEA CLAVE: traducir NO es borrar. El mensaje nuevo le habla al usuario del
 * dominio; la causa original viaja adentro con super(mensaje, causa) y queda
 * disponible con getCause() para diagnóstico y para el stack trace completo.
 * Perder la causa es sabotear el debugging futuro.
 *
 * Detalle fino: el caso estructural (partes de más o de menos) no surge de un
 * parseInt, así que fabricamos a mano la NumberFormatException que hace de
 * causa. La cadena puede construirse, no solo heredarse.
 * ============================================================================
 */
public class Ejercicio4TraducirYRelanzarSolucion {

    /** Traduce errores técnicos de parseo a una excepción del dominio. */
    static class FechaInvalidaException extends Exception {

        public FechaInvalidaException(String mensaje, Throwable causa) {
            // El par (mensaje, causa) es lo que preserva toda la historia.
            super(mensaje, causa);
        }
    }

    /**
     * Valida una fecha con formato "dd/mm/aaaa".
     *
     * @throws FechaInvalidaException si el texto no cumple el formato.
     */
    static void convertirFecha(String fechaTexto) throws FechaInvalidaException {
        String[] partes = fechaTexto.split("/");
        try {
            if (partes.length != 3) {
                // Problema estructural: fabricamos a mano la excepción técnica,
                // para que el catch de abajo la traduzca igual que las demás.
                throw new NumberFormatException(
                        "se esperaban 3 partes separadas por \"/\" y había "
                                + partes.length);
            }
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);
            System.out.println("Fecha \"" + fechaTexto + "\" aceptada (día=" + dia
                    + ", mes=" + mes + ", año=" + anio + ").");
        } catch (NumberFormatException e) {
            // TRADUCCIÓN: mensaje humano arriba, detalle técnico como causa.
            throw new FechaInvalidaException(
                    "\"" + fechaTexto + "\" no es una fecha válida: formato esperado dd/mm/aaaa",
                    e);
        }
    }

    public static void main(String[] args) {
        String[] fechas = {"17/08/2026", "ayer/08/2026", "2026/08"};

        for (String fecha : fechas) {
            System.out.println("--- " + fecha + " ---");
            try {
                convertirFecha(fecha);
            } catch (FechaInvalidaException e) {
                System.out.println("Mensaje del dominio: " + e.getMessage());
                System.out.println("Causa original conservada: "
                        + e.getCause().getClass().getSimpleName() + ": "
                        + e.getCause().getMessage());
            }
        }
    }
}

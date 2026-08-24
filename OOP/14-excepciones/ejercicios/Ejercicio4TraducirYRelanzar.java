/*
 * ============================================================================
 * Módulo 14 · Excepciones — Ejercicio 4: Traducir y relanzar (encadenamiento)
 * ============================================================================
 *
 * ENUNCIADO:
 * convertirFecha(String) valida fechas con formato "dd/mm/aaaa". Adentro
 * pueden estallar NumberFormatException que no le sirven a nadie fuera del
 * método. Capturalas y relanzalas como FechaInvalidaException, conservando la
 * original como CAUSA. El main debe imprimir AMBOS mensajes para probar que
 * la cadena quedó intacta.
 *
 * REQUISITOS:
 *   1. FechaInvalidaException extiende Exception y ofrece el constructor
 *      (String mensaje, Throwable causa).
 *   2. convertirFecha declara y lanza FechaInvalidaException; jamás deja
 *      escapar un NumberFormatException crudo.
 *   3. El mensaje nuevo aclara el "formato esperado dd/mm/aaaa".
 *   4. El caso estructural (cantidad equivocada de partes) también termina en
 *      FechaInvalidaException con su causa.
 *   5. El main muestra e.getMessage() Y e.getCause().getMessage().
 *
 * PISTAS:
 *   - new FechaInvalidaException(mensaje, causa) encadena: la historia
 *     completa viaja adentro de la nueva excepción.
 *   - Para las partes faltantes podés fabricar la causa a mano:
 *     new NumberFormatException("explicación de lo que pasó").
 *   - Este patrón se llama "traducción de excepciones": tu método habla el
 *     idioma del dominio, no el de Integer.parseInt.
 * ============================================================================
 */
public class Ejercicio4TraducirYRelanzar {

    /** Traduce errores técnicos de parseo a una excepción del dominio. */
    static class FechaInvalidaException extends Exception {

        public FechaInvalidaException(String mensaje, Throwable causa) {
            // TODO: delegá en super(mensaje, causa) para que la cadena se conserve
            super(mensaje);
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
                        "se esperaban 3 partes separadas por \"/\"");
            }
            int dia = Integer.parseInt(partes[0]);  // puede lanzar NumberFormatException
            int mes = Integer.parseInt(partes[1]);  // puede lanzar NumberFormatException
            int anio = Integer.parseInt(partes[2]); // puede lanzar NumberFormatException
            System.out.println("Fecha \"" + fechaTexto + "\" aceptada (día=" + dia
                    + ", mes=" + mes + ", año=" + anio + ").");
        } catch (NumberFormatException e) {
            // TODO: relanzá new FechaInvalidaException(mensajeDelFormatoEsperado, e).
            //  OJO: un catch vacío se "traga" el error sin dejar rastro; no lo dejes así.
        }
    }

    public static void main(String[] args) {
        String[] fechas = {"17/08/2026", "ayer/08/2026", "2026/08"};

        for (String fecha : fechas) {
            try {
                convertirFecha(fecha);
            } catch (FechaInvalidaException e) {
                // TODO: imprimí e.getMessage() y además e.getCause().getMessage(),
                //  demostrando que la causa original viajó dentro de la nueva.
                System.out.println("(completá el manejo)");
            }
        }
    }
}

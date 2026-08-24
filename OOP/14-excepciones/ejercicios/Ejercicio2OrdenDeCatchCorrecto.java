/*
 * ============================================================================
 * Módulo 14 · Excepciones — Ejercicio 2: Orden correcto de los catch
 * ============================================================================
 *
 * ENUNCIADO:
 * Abajo hay un defecto clásico: un único catch (Exception) atrapa TODO y
 * muestra el mismo mensaje para cualquier problema. Refactorizalo a una
 * cadena de catch ordenados de lo más específico a lo más general, con un
 * mensaje propio para cada tipo de error.
 *
 * REQUISITOS:
 *   1. NumberFormatException -> mensaje de formato inválido.
 *   2. ArithmeticException   -> mensaje de división por cero.
 *   3. RuntimeException      -> mensaje de error de ejecución inesperado.
 *   4. Exception             -> última red de seguridad.
 *   5. Respetá el orden: el compilador rechaza poner un supertipo antes que
 *      su subtipo, porque esos catch quedarían inalcanzables.
 *
 * PISTAS:
 *   - NumberFormatException y ArithmeticException heredan de
 *     RuntimeException, y RuntimeException hereda de Exception.
 *   - Es un triaje de guardia: primero los casos específicos, al final el
 *     caso general.
 * ============================================================================
 */
public class Ejercicio2OrdenDeCatchCorrecto {

    /** Convierte la entrada a entero y divide 100 por ese valor. */
    static void procesarOperacion(String entrada) {
        int numero = Integer.parseInt(entrada); // puede lanzar NumberFormatException
        int resultado = 100 / numero;           // puede lanzar ArithmeticException
        System.out.println("100 / " + numero + " = " + resultado);
    }

    public static void main(String[] args) {
        String[] entradas = {"5", "veinte", "0", "-4", "2"};

        for (String entrada : entradas) {
            try {
                procesarOperacion(entrada);
            } catch (Exception e) {
                // DEFECTO: este catch atrapa todo y trata todos los errores igual.
                // TODO: reemplazalo por varios catch ordenados de más específico a
                //  más general: NumberFormatException, luego ArithmeticException,
                //  luego RuntimeException y por último Exception, cada uno con su
                //  propio mensaje.
                System.out.println("Ocurrió un error con \"" + entrada + "\": " + e);
            }
        }

        System.out.println();
        System.out.println("Fin del programa.");
    }
}

/*
 * ============================================================================
 * Módulo 14 · Excepciones — Ejercicio 1: División segura
 * ============================================================================
 *
 * ENUNCIADO:
 * Implementá dividir(String textoA, String textoB): convierte ambos textos a
 * enteros y devuelve la división entera. Desde el main procesá la lista de
 * casos de abajo: algunos son válidos y otros van a fallar. El programa debe
 * seguir de pie ante cualquier error y procesar TODOS los casos.
 *
 * REQUISITOS:
 *   1. Manejar NumberFormatException cuando algún operando no sea un número.
 *   2. Manejar ArithmeticException cuando se intente dividir por cero.
 *   3. Mensajes amigables y distintos según el tipo de error.
 *   4. Un error nunca debe cortar el programa: el flujo continúa.
 *
 * PISTAS:
 *   - Integer.parseInt(texto) lanza NumberFormatException si el texto no es
 *     un entero válido.
 *   - Dividir enteros por cero lanza ArithmeticException.
 *   - Un mismo try puede tener varios catch, uno por tipo de excepción.
 * ============================================================================
 */
public class Ejercicio1DivisionSegura {

    /**
     * Divide dos números que llegan como texto.
     *
     * @throws NumberFormatException si algún texto no representa un entero.
     * @throws ArithmeticException   si textoB vale cero.
     */
    static int dividir(String textoA, String textoB) {
        // TODO: convertí los dos textos con Integer.parseInt(...)
        // TODO: devolvé la división entera a / b
        return 0;
    }

    public static void main(String[] args) {
        String[][] casos = {
                {"20", "4"},      // caso feliz
                {"7", "0"},       // división por cero
                {"diez", "2"},    // formato inválido en el primer operando
                {"100", "cinco"}, // formato inválido en el segundo operando
                {"-15", "3"}      // caso feliz con negativos
        };

        for (String[] caso : casos) {
            try {
                // TODO: llamá a dividir(...) y mostrá el resultado como "20 / 4 = 5"
                System.out.println("(pendiente)");
            } catch (NumberFormatException e) {
                // TODO: avisá que alguno de los dos valores no es un entero válido
                System.out.println("(pendiente)");
            } catch (ArithmeticException e) {
                // TODO: avisá que no se puede dividir por cero
                System.out.println("(pendiente)");
            }
        }

        // TODO: mostrá un mensaje final que confirme que llegaste hasta acá
        //  pese a los errores del camino.
    }
}

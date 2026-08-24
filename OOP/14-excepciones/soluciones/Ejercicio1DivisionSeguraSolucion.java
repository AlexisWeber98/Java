/*
 * ============================================================================
 * Módulo 14 · Excepciones — Solución 1: División segura
 * ============================================================================
 * IDEA CLAVE: cada tipo de fallo tiene su propio catch, y el ciclo del main
 * sigue avanzando aunque algunos casos fallen. Los errores se informan con
 * mensajes claros y el programa nunca muere en plena ejecución: capturar una
 * excepción es, literalmente, retomar el control del flujo.
 * ============================================================================
 */
public class Ejercicio1DivisionSeguraSolucion {

    /**
     * Divide dos números que llegan como texto.
     *
     * @throws NumberFormatException si algún texto no representa un entero.
     * @throws ArithmeticException   si textoB vale cero.
     */
    static int dividir(String textoA, String textoB) {
        int a = Integer.parseInt(textoA); // puede lanzar NumberFormatException
        int b = Integer.parseInt(textoB); // puede lanzar NumberFormatException
        return a / b;                     // puede lanzar ArithmeticException
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
                int resultado = dividir(caso[0], caso[1]);
                System.out.println(caso[0] + " / " + caso[1] + " = " + resultado);
            } catch (NumberFormatException e) {
                System.out.println("Error de formato: \"" + caso[0] + "\" o \""
                        + caso[1] + "\" no es un número entero válido.");
            } catch (ArithmeticException e) {
                System.out.println("Error aritmético: no se puede dividir "
                        + caso[0] + " por cero.");
            }
            // Cada vuelta del ciclo arranca fresca: el error quedó contenido
            // dentro del try y jamás salpicó al resto de los casos.
        }

        System.out.println();
        System.out.println("Fin del programa: se procesaron los 5 casos pese a los errores.");
    }
}

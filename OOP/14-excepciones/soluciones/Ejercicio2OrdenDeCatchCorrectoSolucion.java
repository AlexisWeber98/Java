/*
 * ============================================================================
 * Módulo 14 · Excepciones — Solución 2: Orden correcto de los catch
 * ============================================================================
 * IDEA CLAVE: los catch se evalúan en orden y gana el primero que coincide.
 * Por eso van de lo más específico a lo más general. Si invertís el orden, el
 * compilador lo rechaza con "exception ... has already been caught", porque
 * esos bloques serían inalcanzables. Un catch (Exception) único y primero no
 * es manejo de errores: es enterrar el diagnóstico.
 * ============================================================================
 */
public class Ejercicio2OrdenDeCatchCorrectoSolucion {

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
            } catch (NumberFormatException e) { // la más específica primero
                System.out.println("\"" + entrada
                        + "\" -> Error de formato: no es un número entero válido.");
            } catch (ArithmeticException e) { // específica, pero distinta a la anterior
                System.out.println("\"" + entrada
                        + "\" -> Error aritmético: no se puede dividir por cero.");
            } catch (RuntimeException e) { // más general que las dos anteriores
                System.out.println("\"" + entrada
                        + "\" -> Error de ejecución inesperado: " + e);
            } catch (Exception e) { // última red de seguridad
                System.out.println("\"" + entrada + "\" -> Error genérico: " + e);
            }
        }

        System.out.println();
        System.out.println("Fin del programa: cada error tuvo su propio diagnóstico.");
    }
}

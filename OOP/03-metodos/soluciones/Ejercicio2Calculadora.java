/*
 * ============================================================================
 * Módulo 03 — Ejercicio 2: Calculadora con métodos que devuelven double
 * (SOLUCIÓN)
 * ============================================================================
 *
 * ENUNCIADO:
 *   Implementá una calculadora con cuatro métodos static:
 *     - sumar(double a, double b)
 *     - restar(double a, double b)
 *     - multiplicar(double a, double b)
 *     - dividir(double dividendo, double divisor)
 *   Todos devuelven double. dividir debe defenderse de la división por cero.
 *
 * REQUISITOS:
 *   1. Los cuatro métodos son static y devuelven double.
 *   2. dividir: si el divisor es 0, NO se divide; se devuelve 0 y se avisa
 *      por consola (documentá ese contrato en el comentario del método).
 *   3. Desde main probá al menos un caso por operación.
 *   4. Incluí en main una división por cero para ver el comportamiento
 *      defendido en acción.
 *
 * PISTAS:
 *   - Al declarar parámetros como double podés pasar enteros sin conversión:
 *     Java los ensancha solo (int -> double).
 *   - El guard se escribe ANTES de operar: primero validás, después dividís.
 *   - Devolver 0 como valor centinela es una decisión de diseño: dejala
 *     documentada para quien use el método.
 */
public class Ejercicio2Calculadora {

    static double sumar(double a, double b) {
        return a + b;
    }

    static double restar(double a, double b) {
        return a - b;
    }

    static double multiplicar(double a, double b) {
        return a * b;
    }

    /**
     * Contrato defensivo: si divisor es 0 no se realiza la división.
     * ¿Por qué? En punto flotante 9.0 / 0.0 NO lanza excepción: produce
     * Infinity (o NaN si el dividendo también es 0), que contamina los
     * cálculos silenciosamente. Elegimos devolver 0 como valor centinela y
     * avisar por consola; en módulos futuros veremos alternativas más
     * robustas (excepciones u Optional).
     */
    static double dividir(double dividendo, double divisor) {
        if (divisor == 0) {
            System.out.println("[ADVERTENCIA] División por cero: se devuelve 0.");
            return 0;
        }
        return dividendo / divisor;
    }

    public static void main(String[] args) {
        // Los literales enteros se ensanchan a double automáticamente.
        System.out.println("sumar(4, 3)         -> " + sumar(4, 3));
        System.out.println("restar(10, 6.5)     -> " + restar(10, 6.5));
        System.out.println("multiplicar(2.5, 4) -> " + multiplicar(2.5, 4));

        System.out.println("dividir(9, 2)       -> " + dividir(9, 2));
        System.out.println("dividir(9, 0)       -> " + dividir(9, 0));
    }
}

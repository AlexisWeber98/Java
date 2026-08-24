/*
 * Módulo 13 — Conversiones de tipos
 * Ejercicio 3: Parsing seguro (SOLUCIÓN)
 *
 * SALIDA REAL DEL PROGRAMA:
 *   convertirAEntero(42) = 42        | esEnteroValido(42)   = true
 *   convertirAEntero(hola) = -1      | esEnteroValido(hola) = false
 *   convertirAEntero() = -1          | esEnteroValido()     = false
 *   convertirAEntero(3.14) = -1      | esEnteroValido(3.14) = false
 *   convertirAEntero(null) = -1      | esEnteroValido(null) = false
 *
 * CLAVES DE LA SOLUCIÓN:
 * - El orden de los controles importa: primero null/vacío, DESPUÉS parsear.
 * - "3.14" NO es un entero: Integer.parseInt rechaza el punto decimal. Si
 *   quisieramos decimales usaríamos Double.parseDouble.
 * - Falla de diseño del centinela -1: si el texto legítimo fuera "-1", no
 *   podríamos distinguir éxito de fracaso. La variante booleana separa
 *   "¿se puede?" de "dame el valor": dos preguntas, dos métodos.
 */
public class Solucion3ParsingSeguro {

    public static void main(String[] args) {
        String[] casosDePrueba = {"42", "hola", "", "3.14", null};

        for (String texto : casosDePrueba) {
            System.out.println("convertirAEntero(" + texto + ") = " + convertirAEntero(texto));
            System.out.println("esEnteroValido(" + texto + ")  = " + esEnteroValido(texto));
            System.out.println("---");
        }
    }

    // Estrategia centinela: útil para prototipos rápidos, frágil si "-1" es
    // un valor de negocio posible.
    static int convertirAEntero(String texto) {
        if (texto == null || texto.isBlank()) {
            return -1;
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Estrategia booleana: más limpia y sin colisión de valores.
    static boolean esEnteroValido(String texto) {
        if (texto == null || texto.isBlank()) {
            return false;
        }
        try {
            Integer.parseInt(texto.trim());   // solo probamos: descartamos el resultado
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

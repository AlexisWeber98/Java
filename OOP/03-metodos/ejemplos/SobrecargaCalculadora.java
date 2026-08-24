/**
 * SobrecargaCalculadora.java — Tres métodos "sumar" con firmas distintas.
 * El compilador elige cuál ejecutar mirando los ARGUMENTOS de la llamada.
 * Ejecutar: java ejemplos/SobrecargaCalculadora.java
 */
public class SobrecargaCalculadora {

    // Firma 1: sumar(int, int)
    public static int sumar(int a, int b) {
        System.out.println("  -> elegida: sumar(int, int)");
        return a + b;
    }

    // Firma 2: sumar(double, double) — mismo nombre, otros tipos.
    public static double sumar(double a, double b) {
        System.out.println("  -> elegida: sumar(double, double)");
        return a + b;
    }

    // Firma 3: sumar(int, int, int) — mismo nombre, más parámetros.
    public static int sumar(int a, int b, int c) {
        System.out.println("  -> elegida: sumar(int, int, int)");
        return a + b + c;
    }

    // ESTO NO COMPILARÍA si lo descomentaras:
    //
    // public static double sumar(int a, int b) { return a + b; }
    //
    // ¿Por qué? Su firma (int, int) ya existe arriba. Cambiar SOLO el tipo
    // de retorno no crea una sobrecarga válida: el compilador no podría
    // decidir a cuál llamar con sumar(2, 3). El retorno NO es parte de la firma.

    public static void main(String[] args) {
        // Literales sin decimales y dos argumentos -> firma (int, int).
        System.out.println("sumar(2, 3):");
        System.out.println("  resultado: " + sumar(2, 3));

        // Literales con decimales -> firma (double, double).
        System.out.println("sumar(2.5, 0.4):");
        System.out.println("  resultado: " + sumar(2.5, 0.4));

        // Tres argumentos -> firma (int, int, int).
        System.out.println("sumar(1, 2, 3):");
        System.out.println("  resultado: " + sumar(1, 2, 3));

        // ¡Ojo con la mezcla! Un double obliga al compilador a ampliar:
        // el int 3 se promueve a double y gana la firma (double, double).
        System.out.println("sumar(2, 0.5):");
        System.out.println("  resultado: " + sumar(2, 0.5));

        // Y acá NO hay ambigüedad porque solo hay un método de tres parámetros.
        System.out.println("sumar(10, 20, 30):");
        System.out.println("  resultado: " + sumar(10, 20, 30));
    }
}

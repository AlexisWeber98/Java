/*
 * ============================================================================
 * Módulo 03 — Ejercicio 3: Sobrecarga de métodos (áreas) (SOLUCIÓN)
 * ============================================================================
 *
 * ENUNCIADO:
 *   Implementá tres versiones (sobrecargas) del método area en la misma
 *   clase:
 *     - area(double radio): área del círculo.
 *     - area(double base, double altura): área del rectángulo.
 *     - area(double base, double altura, String figura): área de la figura
 *       nombrada ("triangulo" -> base * altura / 2).
 *
 * REQUISITOS:
 *   1. Las tres sobrecargas comparten nombre pero NO la lista de parámetros.
 *   2. Círculo: Math.PI * radio * radio.
 *   3. Rectángulo: base * altura.
 *   4. Tercera versión: si figura es "triangulo" devolvé base * altura / 2;
 *      si no, avisá por consola y devolvé 0.
 *   5. Desde main llamá las tres formas e identificá cuál elige el
 *      compilador según la cantidad y el tipo de argumentos.
 *
 * PISTAS:
 *   - La sobrecarga se resuelve en TIEMPO DE COMPILACIÓN: el compilador
 *     mira cantidad y tipos de los argumentos de la llamada.
 *   - El tipo de retorno NO participa en esa elección: dos métodos que solo
 *     difieren en el retorno no compilan.
 *   - equalsIgnoreCase("triangulo") hace la comparación tolerante a
 *     mayúsculas y minúsculas.
 */
public class Ejercicio3SobrecargaArea {

    /**
     * Sobrecarga 1: un solo double. El compilador la elige cuando la llamada
     * pasa exactamente un argumento convertible a double.
     */
    static double area(double radio) {
        System.out.println("-> elegida: area(double radio) [círculo]");
        return Math.PI * radio * radio;
    }

    /**
     * Sobrecarga 2: dos doubles. Misma firma de nombre, distinta lista de
     * parámetros: eso es sobrecargar, no redefinir.
     */
    static double area(double base, double altura) {
        System.out.println("-> elegida: area(double, double) [rectángulo]");
        return base * altura;
    }

    /**
     * Sobrecarga 3: dos doubles + String. El tercer parámetro cambia la
     * firma completa, así que convive sin conflicto con las otras dos.
     */
    static double area(double base, double altura, String figura) {
        System.out.println("-> elegida: area(double, double, String)");
        if (figura.equalsIgnoreCase("triangulo")) {
            return base * altura / 2;
        }
        System.out.println("   Figura \"" + figura + "\" no soportada: se devuelve 0.");
        return 0;
    }

    public static void main(String[] args) {
        // Cada llamada resuelve en compilación a UNA sobrecarga concreta:
        // fijate qué mensaje imprime cada una al ejecutar.

        double circulo = area(2.0);
        System.out.printf("Área del círculo r=2.0      -> %.4f%n%n", circulo);

        double rectangulo = area(4.0, 3.0);
        System.out.printf("Área del rectángulo 4x3     -> %.4f%n%n", rectangulo);

        double triangulo = area(4.0, 3.0, "triangulo");
        System.out.printf("Área del triángulo 4x3      -> %.4f%n%n", triangulo);

        // Caso por defecto: la firma coincide, la figura no.
        double desconocida = area(4.0, 3.0, "hexagono");
        System.out.printf("Área de figura desconocida  -> %.4f%n", desconocida);
    }
}

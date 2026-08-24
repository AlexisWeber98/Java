/*
 * ============================================================================
 * Módulo 03 — Ejercicio 3: Sobrecarga de métodos (áreas)
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

    // TODO: sobrecarga area(double radio) para el círculo.


    // TODO: sobrecarga area(double base, double altura) para el rectángulo.


    // TODO: sobrecarga area(double base, double altura, String figura).


    public static void main(String[] args) {
        // TODO: llamá area(2.0) y mostrá el resultado.

        // TODO: llamá area(4.0, 3.0) y mostrá el resultado.

        // TODO: llamá area(4.0, 3.0, "triangulo") y también con una figura
        //  desconocida para ver el caso por defecto.
    }
}

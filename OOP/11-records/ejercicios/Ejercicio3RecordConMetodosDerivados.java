/*
 * ============================================================================
 * Módulo 11 - Records | Ejercicio 3: Record con métodos derivados: Rectángulo
 * ============================================================================
 *
 * ENUNCIADO:
 * Un rectángulo se describe con base y altura; su área, su perímetro y la
 * condición de ser cuadrado se CALCULAN a partir de esos dos datos.
 * Agregá al record Rectangulo los métodos:
 *   - area(): base * altura
 *   - perimetro(): 2 * (base + altura)
 *   - esCuadrado(): true si base y altura son iguales
 * En el main creá un rectángulo de 4.0 x 7.0 y otro de 5.0 x 5.0 y ejercitá
 * los tres métodos con cada uno.
 *
 * REQUISITOS:
 * - Implementar los tres métodos dentro del record Rectangulo.
 * - Crear dos rectángulos (uno cuadrado, otro no) y mostrar area, perímetro
 *   y esCuadrado de ambos.
 *
 * PISTAS:
 * - Los métodos derivados NO agregan campos: leen los componentes base y
 *   altura directamente, como cualquier método de instancia.
 * - Para comparar doubles con precisión, Double.compare(base, altura) == 0
 *   es más seguro que usar ==.
 */
public class Ejercicio3RecordConMetodosDerivados {

    public static void main(String[] args) {
        // TODO: creá un Rectangulo de base 4.0 y altura 7.0

        // TODO: creá un Rectangulo de base 5.0 y altura 5.0

        // TODO: para cada uno, mostrá area(), perimetro() y esCuadrado()
    }

    record Rectangulo(double base, double altura) {

        // TODO: implementá area()

        // TODO: implementá perimetro()

        // TODO: implementá esCuadrado()
    }
}

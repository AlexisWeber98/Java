/*
 * ============================================================================
 * Solución 4 — Una calculadora dentro de un enum
 * ============================================================================
 *
 * ENUNCIADO
 * Enum Operacion con método abstracto aplicar(double, double); cada constante
 * (SUMA, RESTA, MULTIPLICACION, DIVISION) lleva su propio cuerpo, y la
 * división protege contra el cero. Aplicar las cuatro a 10 y 3, y probar la
 * división por cero con try-catch.
 *
 * CLAVES DE ESTA SOLUCIÓN
 *   - Cada constante con cuerpo {...} es una subclase anónima del enum:
 *     polimorfismo puro, sin jerarquías de clases visibles.
 *   - El patrón Strategy queda resuelto en diez líneas: elegir una operación
 *     es elegir un objeto que sabe calcular.
 *   - La regla "no dividir por cero" vive adentro del propio DIVISION: quien
 *     la use no puede olvidarla, porque el enum se defiende solo.
 */
public class Solucion4OperacionMatematica {

    enum Operacion {
        SUMA {
            @Override
            double aplicar(double a, double b) {
                return a + b;
            }
        },
        RESTA {
            @Override
            double aplicar(double a, double b) {
                return a - b;
            }
        },
        MULTIPLICACION {
            @Override
            double aplicar(double a, double b) {
                return a * b;
            }
        },
        DIVISION {
            @Override
            double aplicar(double a, double b) {
                if (b == 0) {
                    throw new ArithmeticException("no se puede dividir por cero");
                }
                return a / b;
            }
        };

        abstract double aplicar(double a, double b);
    }

    public static void main(String[] args) {
        double a = 10;
        double b = 3;

        for (Operacion operacion : Operacion.values()) {
            System.out.printf("%-16s(%s, %s) = %.4f%n",
                    operacion, a, b, operacion.aplicar(a, b));
        }

        System.out.println();
        try {
            System.out.println("Probando DIVISION(8, 0)...");
            System.out.println("   Resultado: " + Operacion.DIVISION.aplicar(8, 0));
        } catch (ArithmeticException error) {
            System.out.println("   La calculadora se defendió sola: "
                    + error.getMessage());
        }
    }
}

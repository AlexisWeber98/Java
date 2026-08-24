/*
 * ============================================================================
 * Ejercicio 4 — Una calculadora dentro de un enum
 * ============================================================================
 *
 * ENUNCIADO
 * Los enums pueden declarar métodos ABSTRACTOS que cada constante implementa
 * a su manera, con su propio cuerpo entre llaves. Es la forma más compacta de
 * escribir el patrón Strategy. Creá el enum Operacion con SUMA, RESTA,
 * MULTIPLICACION y DIVISION: cada una implementa double aplicar(double a,
 * double b), y la división debe protegerse de la temible división por cero.
 *
 * REQUISITOS
 *   1. Enum Operacion con el método abstracto double aplicar(double, double).
 *   2. Cada constante define su cuerpo {...} con la cuenta correcta.
 *   3. DIVISION: si el divisor es 0, lanzar ArithmeticException con un
 *      mensaje claro; si no, devolver a / b.
 *   4. En main: aplicarle las cuatro operaciones a 10 y 3 con values() e
 *      imprimir cada resultado (usá %.4f para el formato).
 *   5. Intentar también DIVISION.aplicar(8, 0) dentro de un try-catch y
 *      mostrar el mensaje de la excepción sin que explote nada.
 *
 * PISTAS
 *   - El método abstracto se declara UNA sola vez, al final, después del
 *     punto y coma que cierra la lista de constantes.
 *   - Cada constante sobrescribe aplicar() con @Override, como una clase
 *     anónima pero mucho más legible.
 *   - Fijate que el starter compila: los cuerpos devuelven 0 provisoriamente.
 *     Tu trabajo es reemplazarlos.
 */
public class Ejercicio4OperacionMatematica {

    enum Operacion {
        SUMA {
            @Override
            double aplicar(double a, double b) {
                return 0; // TODO: devolvé la suma de a y b
            }
        },
        RESTA {
            @Override
            double aplicar(double a, double b) {
                return 0; // TODO: devolvé a menos b
            }
        },
        MULTIPLICACION {
            @Override
            double aplicar(double a, double b) {
                return 0; // TODO: devolvé el producto de a por b
            }
        },
        DIVISION {
            @Override
            double aplicar(double a, double b) {
                // TODO: si b es 0, lanzá new ArithmeticException("mensaje claro")
                // TODO: si no, devolvé a / b
                return 0;
            }
        };

        abstract double aplicar(double a, double b);
    }

    public static void main(String[] args) {
        double a = 10;
        double b = 3;

        // TODO: recorré Operacion.values() e imprimí, por ejemplo:
        //       SUMA(10.0, 3.0) = 13.0000

        System.out.println();

        // TODO: intentá Operacion.DIVISION.aplicar(8, 0) dentro de un
        //       try-catch de ArithmeticException y mostrá error.getMessage().
    }
}

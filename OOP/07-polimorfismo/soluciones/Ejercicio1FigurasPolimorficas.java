/*
 * ============================================================================
 * Módulo 07 – Polimorfismo | Ejercicio 1: Figuras polimórficas (SOLUCIÓN)
 * ============================================================================
 * Idea clave: un solo bucle trabaja con el tipo de la base y la JVM ejecuta
 * el override correcto en tiempo de ejecución (despacho dinámico).
 *
 * Nota: la clase principal lleva el mismo nombre que el archivo, así la
 * receta de siempre anda sin memoria:
 *   javac Ejercicio1FigurasPolimorficas.java && java Ejercicio1FigurasPolimorficas
 *
 * Detalle: al compartir nombres de clase con ejercicios/, cada carpeta se
 * compila por separado (un solo javac con las dos daría "duplicate class"
 * en el paquete por defecto).
 */
public class Ejercicio1FigurasPolimorficas {

    static abstract class FiguraGeometrica {
        final String nombre;

        FiguraGeometrica(String nombre) {
            this.nombre = nombre;
        }

        abstract double calcularArea();
    }

    static class Circulo extends FiguraGeometrica {
        final double radio;

        Circulo(double radio) {
            super("Círculo");
            this.radio = radio;
        }

        @Override
        double calcularArea() {
            return Math.PI * radio * radio;
        }
    }

    static class Rectangulo extends FiguraGeometrica {
        final double base;
        final double altura;

        Rectangulo(double base, double altura) {
            super("Rectángulo");
            this.base = base;
            this.altura = altura;
        }

        @Override
        double calcularArea() {
            return base * altura;
        }
    }

    static class Triangulo extends FiguraGeometrica {
        final double base;
        final double altura;

        Triangulo(double base, double altura) {
            super("Triángulo");
            this.base = base;
            this.altura = altura;
        }

        @Override
        double calcularArea() {
            return base * altura / 2;
        }
    }

    public static void main(String[] args) {
        // UPCASTING: guardamos subclases en variables del tipo de la base.
        // Un Círculo ES una FiguraGeometrica, así que entra sin cast.
        FiguraGeometrica[] figuras = {
                new Circulo(2),
                new Rectangulo(3, 4),
                new Triangulo(6, 5)
        };

        // DESPACHO DINÁMICO: la misma línea ejecuta un método distinto según el
        // tipo real de cada objeto. Si mañana agregás Trapecio, este bucle no
        // cambia NI UNA línea: eso es programar contra la abstracción.
        for (FiguraGeometrica figura : figuras) {
            System.out.printf("%s -> área = %.2f%n", figura.nombre, figura.calcularArea());
        }
    }
}

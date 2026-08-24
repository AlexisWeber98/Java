/*
 * ============================================================================
 * Módulo 07 – Polimorfismo | Ejercicio 1: Figuras polimórficas
 * ============================================================================
 *
 * ENUNCIADO:
 *   Creá la jerarquía de figuras geométricas y recorré un arreglo de
 *   FiguraGeometrica mostrando el área de cada una con UN solo bloque de
 *   código, sin ifs ni instanceof.
 *
 * REQUISITOS:
 *   1. FiguraGeometrica: clase abstracta con el atributo nombre y el método
 *      abstracto calcularArea().
 *   2. Circulo, Rectangulo y Triangulo extienden la base y cada una implementa
 *      calcularArea() con su propia fórmula (con @Override).
 *   3. En main: declará FiguraGeometrica[] figuras con al menos una figura de
 *      cada tipo y, con un único for, imprimí "nombre -> area" por cada una.
 *
 * PISTAS:
 *   - Círculo: Math.PI * radio * radio. Rectángulo: base * altura.
 *     Triángulo: base * altura / 2.
 *   - Cuando escribís figura.calcularArea(), la JVM decide EN TIEMPO DE
 *     EJECUCIÓN qué override ejecutar según el tipo real del objeto:
 *     eso es el despacho dinámico.
 *   - El bucle no necesita saber si la figura es un círculo o un triángulo:
 *     tratalas a todas como FiguraGeometrica.
 *
 * Ejecución:  java Ejercicio1FigurasPolimorficas.java
 */
public class Ejercicio1FigurasPolimorficas {

    static abstract class FiguraGeometrica {
        final String nombre;

        FiguraGeometrica(String nombre) {
            this.nombre = nombre;
        }

        // Sin cuerpo: cada subclase está obligada a definir su propia área.
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
            // TODO: reemplazá el 0 por la fórmula del área del círculo.
            return 0;
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
            // TODO: reemplazá el 0 por la fórmula del área del rectángulo.
            return 0;
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
            // TODO: reemplazá el 0 por la fórmula del área del triángulo.
            return 0;
        }
    }

    public static void main(String[] args) {
        FiguraGeometrica[] figuras = {
                new Circulo(2),
                new Rectangulo(3, 4),
                new Triangulo(6, 5)
        };

        // TODO: recorré el arreglo e imprimí "nombre -> área" con dos decimales.
        // Una sola línea dentro del for: nada de instanceof ni cadenas de if.
        System.out.println("(Falta implementar los áreas y el recorrido)");
    }
}

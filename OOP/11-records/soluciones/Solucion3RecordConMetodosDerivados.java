/*
 * ============================================================================
 * Módulo 11 - Records | Solución 3: Record con métodos derivados: Rectángulo
 * ============================================================================
 * Idea clave: los métodos derivados se calculan a partir de los componentes;
 * como el record es inmutable, sus resultados nunca quedan desactualizados.
 */
public class Solucion3RecordConMetodosDerivados {

    public static void main(String[] args) {
        Rectangulo pizarra = new Rectangulo(4.0, 7.0);
        Rectangulo caldera = new Rectangulo(5.0, 5.0);

        mostrarDatos("Pizarra", pizarra);
        System.out.println();
        mostrarDatos("Caldera", caldera);
    }

    static void mostrarDatos(String nombre, Rectangulo rectangulo) {
        System.out.println(nombre + " -> " + rectangulo);
        System.out.println("  Área         = " + rectangulo.area());
        System.out.println("  Perímetro    = " + rectangulo.perimetro());
        System.out.println("  ¿Es cuadrado? = " + rectangulo.esCuadrado());
    }

    record Rectangulo(double base, double altura) {

        double area() {
            return base * altura;
        }

        double perimetro() {
            return 2 * (base + altura);
        }

        boolean esCuadrado() {
            // Double.compare evita sorpresas de precisión con doubles.
            return Double.compare(base, altura) == 0;
        }
    }
}

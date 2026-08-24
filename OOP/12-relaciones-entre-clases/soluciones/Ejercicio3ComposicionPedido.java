/*
 * ============================================================================
 * Ejercicio 3 (SOLUCIÓN) — Composición: Pedido y sus LineaPedido
 * ============================================================================
 * Gemelo de ejercicios/Ejercicio3ComposicionPedido.java (clase sin public para
 * permitir la compilación conjunta de ejercicios/ y soluciones/).
 *
 * CONCEPTO CLAVE:
 *   Composición = todo–parte donde la parte ES del todo: nace adentro,
 *   vive mientras el todo viva y muere con él.
 *
 *   Pruebas en este diseño (leelas como contrato, no como comentarios sueltos):
 *   1) agregarLinea(...) recibe DATOS crudos y ejecuta new LineaPedido(...)
 *      adentro: el exterior jamás fabrica partes del pedido.
 *   2) No existe getter que exponga la lista ni las líneas individuales:
 *      nadie de afuera puede guardar una referencia a una parte.
 *   3) Por (1) y (2), si el pedido desaparece, sus líneas desaparecen con él:
 *      ninguna línea tiene existencia propia fuera de su pedido.
 */
import java.util.ArrayList;
import java.util.List;

class Solucion3ComposicionPedido {

    static class LineaPedido {
        private final String producto;
        private final int cantidad;
        private final double precioUnitario;

        LineaPedido(String producto, int cantidad, double precioUnitario) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
        }

        double subtotal() {
            return cantidad * precioUnitario;
        }

        String descripcion() {
            return producto + " x" + cantidad + " ($" + dinero(subtotal()) + ")";
        }
    }

    static class Pedido {
        private final String numero;

        // Composición: única dueña y fabricante de sus partes.
        private final List<LineaPedido> lineas = new ArrayList<>();

        Pedido(String numero) {
            this.numero = numero;
        }

        void agregarLinea(String producto, int cantidad, double precioUnitario) {
            // NACIMIENTO de la parte: ocurre dentro del todo, nunca afuera.
            lineas.add(new LineaPedido(producto, cantidad, precioUnitario));
        }

        double calcularTotal() {
            double total = 0;
            for (LineaPedido linea : lineas) {
                total += linea.subtotal();
            }
            return total;
        }

        void detalle() {
            System.out.println("Pedido " + numero);
            for (LineaPedido linea : lineas) {
                System.out.println("  - " + linea.descripcion());
            }
            System.out.println("  TOTAL: $" + dinero(calcularTotal()));
        }
    }

    private static String dinero(double monto) {
        return String.format(java.util.Locale.US, "%.2f", monto);
    }

    public static void main(String[] args) {
        Pedido pedido = new Pedido("PED-001");
        pedido.agregarLinea("Teclado mecánico", 2, 18500.0);
        pedido.agregarLinea("Mouse inalámbrico", 1, 9200.50);
        pedido.detalle();

        // Si pedido = null, las líneas mueren junto con él: nadie más en el
        // programa pudo jamás referenciarlas. Esa es la composición.
    }
}

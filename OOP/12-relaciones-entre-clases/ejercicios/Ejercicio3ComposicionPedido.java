/*
 * ============================================================================
 * Ejercicio 3 — Composición: Pedido y sus LineaPedido
 * ============================================================================
 *
 * ENUNCIADO:
 *   Un Pedido está COMPUESTO por líneas: cada LineaPedido NACE adentro del
 *   pedido (el pedido jamás recibe líneas ya armadas desde afuera) y no tiene
 *   sentido existir fuera de él. El total se calcula dentro del pedido.
 *
 * REQUISITOS:
 *   1. LineaPedido: producto, cantidad, precioUnitario y subtotal().
 *   2. Pedido: numero + colección interna de líneas. La única forma de cargar
 *      líneas es agregarLinea(producto, cantidad, precioUnitario), que crea
 *      la línea INTERNAMENTE. No debe existir manera de pasarle una línea
 *      construida afuera ni de obtener las líneas individuales desde afuera.
 *   3. Pedido.calcularTotal(): suma de subtotales, calculada dentro del todo.
 *   4. Pedido.detalle(): imprime el número, cada línea y el total.
 *   5. En comentarios, dejá escrito POR QUÉ esto es composición y no
 *      agregación (pensá en quién crea a la línea y cuánto vive).
 *
 * PISTAS:
 *   - Composición = todo–parte con la parte ESCLAVA del todo: nace, vive y
 *     muere con él. El todo controla la creación de sus partes.
 *   - Compará con el Ejercicio 2: ¿quién creaba al profesor? ¿Y acá, quién
 *     crea a la línea?
 *   - Si nadie de afuera puede referenciar una línea, su ciclo de vida queda
 *     atado para siempre al pedido.
 */
import java.util.ArrayList;
import java.util.List;

public class Ejercicio3ComposicionPedido {

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
            // TODO: devolver cantidad * precioUnitario
            return 0;
        }

        String descripcion() {
            // TODO: devolver "<producto> x<cantidad> ($<subtotal>)"
            return "";
        }
    }

    static class Pedido {
        private final String numero;

        // Composición: las partes nacen ACÁ ADENTRO.
        private final List<LineaPedido> lineas = new ArrayList<>();

        Pedido(String numero) {
            this.numero = numero;
        }

        void agregarLinea(String producto, int cantidad, double precioUnitario) {
            // TODO: crear la línea DENTRO de este método y agregarla a la colección
        }

        double calcularTotal() {
            // TODO: sumar los subtotales de todas las líneas
            return 0;
        }

        void detalle() {
            // TODO: imprimir "Pedido <numero>", cada línea y "  TOTAL: $<total>"
        }
    }

    public static void main(String[] args) {
        Pedido pedido = new Pedido("PED-001");
        pedido.agregarLinea("Teclado mecánico", 2, 18500.0);
        pedido.agregarLinea("Mouse inalámbrico", 1, 9200.50);
        pedido.detalle();
    }
}

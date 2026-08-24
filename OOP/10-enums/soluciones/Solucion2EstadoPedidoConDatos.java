/*
 * ============================================================================
 * Solución 2 — Pedidos con datos: enums con campos y constructor
 * ============================================================================
 *
 * ENUNCIADO
 * Enum EstadoPedido (PENDIENTE, PAGADO, ENVIADO, ENTREGADO, CANCELADO) donde
 * cada estado guarda una descripcion y diasEstimados, y se describe con
 * resumen(). Recorrer los estados desde main imprimiendo cada resumen().
 *
 * CLAVES DE ESTA SOLUCIÓN
 *   - El constructor es implícitamente privado: las únicas instancias de
 *     EstadoPedido en toda la JVM son las cinco constantes.
 *   - La lista de constantes lleva los datos entre paréntesis y cierra con
 *     punto y coma; recién ahí empiezan campos y métodos.
 *   - Campos private + final = inmutabilidad: cada estado es un valor seguro
 *     de compartir sin miedo a que lo modifiquen.
 *   - resumen() encapsula la presentación: afuera nadie necesita getters para
 *     armar el texto.
 */
public class Solucion2EstadoPedidoConDatos {

    enum EstadoPedido {
        PENDIENTE("Esperando la acreditación del pago", 2),
        PAGADO("Pago recibido: estamos preparando tu pedido", 1),
        ENVIADO("Tu pedido salió del depósito y está en camino", 3),
        ENTREGADO("Entrega realizada. ¡Que lo disfrutes!", 0),
        CANCELADO("El pedido quedó cancelado", 0);

        private final String descripcion;
        private final int diasEstimados;

        // Implícitamente privado: solo la propia declaración del enum puede
        // invocarlo, una vez por constante, en el orden en que aparecen.
        EstadoPedido(String descripcion, int diasEstimados) {
            this.descripcion = descripcion;
            this.diasEstimados = diasEstimados;
        }

        public String resumen() {
            String base = name() + ": " + descripcion;
            if (diasEstimados > 0) {
                base += " (" + diasEstimados + " día(s) hábiles)";
            }
            return base;
        }
    }

    public static void main(String[] args) {
        System.out.println("Ciclo de vida de tu pedido:");
        for (EstadoPedido estado : EstadoPedido.values()) {
            System.out.println("  " + estado.resumen());
        }
    }
}

/*
 * ============================================================================
 * Solución 5 (DESAFÍO) — La máquina de estados del pedido
 * ============================================================================
 *
 * ENUNCIADO
 * EstadoPedido sabe responder puedeTransicionarA(destino) según las reglas:
 * PENDIENTE -> PAGADO | CANCELADO; PAGADO -> ENVIADO | CANCELADO;
 * ENVIADO -> ENTREGADO; ENTREGADO y CANCELADO terminales. Evaluar varios
 * pares desde main e imprimir el veredicto.
 *
 * CLAVES DE ESTA SOLUCIÓN
 *   - Las reglas de negocio quedan encapsuladas DENTRO del enum: es imposible
 *     consultarlas mal o duplicarlas en otro rincón del sistema.
 *   - El switch expresión sobre this devuelve el conjunto de destinos válidos
 *     por estado; al cubrir todas las constantes no necesita default y el
 *     compilador exige tratar a cualquier constante futura.
 *   - EnumSet.noneOf() modela "estado terminal": un conjunto vacío rechaza
 *     todo destino sin un solo if adicional.
 *   - puedeTransicionarA() es una consulta pura (true/false): la impresión
 *     queda en main, separando decisión de presentación.
 */
import java.util.EnumSet;

public class Solucion5DesafioMaquinaDeEstados {

    enum EstadoPedido {
        PENDIENTE,
        PAGADO,
        ENVIADO,
        ENTREGADO,
        CANCELADO;

        boolean puedeTransicionarA(EstadoPedido destino) {
            EnumSet<EstadoPedido> destinosValidos = switch (this) {
                case PENDIENTE -> EnumSet.of(PAGADO, CANCELADO);
                case PAGADO    -> EnumSet.of(ENVIADO, CANCELADO);
                case ENVIADO   -> EnumSet.of(ENTREGADO);
                case ENTREGADO, CANCELADO -> EnumSet.noneOf(EstadoPedido.class);
            };
            return destinosValidos.contains(destino);
        }
    }

    static void evaluarTransicion(EstadoPedido origen, EstadoPedido destino) {
        String veredicto = origen.puedeTransicionarA(destino)
                ? "PERMITIDA"
                : "rechazada";
        System.out.printf("  %-10s -> %-10s : %s%n", origen, destino, veredicto);
    }

    public static void main(String[] args) {
        System.out.println("Auditoría de transiciones del pedido #1024:");

        evaluarTransicion(EstadoPedido.PENDIENTE, EstadoPedido.PAGADO);    // camino feliz
        evaluarTransicion(EstadoPedido.PENDIENTE, EstadoPedido.ENVIADO);   // saltea el pago
        evaluarTransicion(EstadoPedido.PAGADO, EstadoPedido.ENVIADO);      // camino feliz
        evaluarTransicion(EstadoPedido.PAGADO, EstadoPedido.CANCELADO);    // arrepentimiento
        evaluarTransicion(EstadoPedido.ENVIADO, EstadoPedido.ENTREGADO);   // camino feliz
        evaluarTransicion(EstadoPedido.ENVIADO, EstadoPedido.PENDIENTE);   // viaje en el tiempo
        evaluarTransicion(EstadoPedido.ENTREGADO, EstadoPedido.PAGADO);    // terminal
        evaluarTransicion(EstadoPedido.CANCELADO, EstadoPedido.PENDIENTE); // resurrección

        // Desafío extra para casa: ¿agregarías REEMBOLSADO? ¿Desde qué estados
        // debería poder transicionarse? El enum ya te da el molde.
    }
}

/*
 * ============================================================================
 * Ejercicio 5 (DESAFÍO) — La máquina de estados del pedido
 * ============================================================================
 *
 * ENUNCIADO
 * Un pedido no salta de "pendiente" a "entregado" por arte de magia: hay
 * reglas de negocio que definen qué transiciones son legales. ESO es una
 * máquina de estados, y el mejor hogar para ese conocimiento es el propio
 * enum. Extendé EstadoPedido con el método puedeTransicionarA(EstadoPedido
 * destino) que responda si pasar del estado actual al destino está permitido:
 *
 *     PENDIENTE -> PAGADO | CANCELADO
 *     PAGADO    -> ENVIADO | CANCELADO
 *     ENVIADO   -> ENTREGADO
 *     ENTREGADO y CANCELADO son terminales: no llevan a ningún lado.
 *
 * REQUISITOS
 *   1. Implementar puedeTransicionarA() con esas reglas.
 *   2. El método solo responde true/false; imprimir es tarea de quien llama.
 *   3. En main, evaluar al menos estos pares e imprimir el veredicto:
 *      PENDIENTE->PAGADO, PENDIENTE->ENVIADO (ilegal: saltea el pago),
 *      PAGADO->ENVIADO, PAGADO->CANCELADO, ENVIADO->ENTREGADO,
 *      ENVIADO->PENDIENTE (ilegal: viaje en el tiempo), ENTREGADO->PAGADO,
 *      CANCELADO->PENDIENTE.
 *
 * PISTAS
 *   - Un switch expresión sobre this puede devolver el conjunto de destinos
 *     válidos de cada estado: EnumSet.of(PAGADO, CANCELADO), etc.
 *   - Para los estados terminales usá EnumSet.noneOf(EstadoPedido.class).
 *   - Como el switch cubre TODAS las constantes, no necesita default.
 *   - Importá java.util.EnumSet al inicio del archivo.
 */
import java.util.EnumSet;

public class Ejercicio5DesafioMaquinaDeEstados {

    enum EstadoPedido {
        PENDIENTE,
        PAGADO,
        ENVIADO,
        ENTREGADO,
        CANCELADO;

        boolean puedeTransicionarA(EstadoPedido destino) {
            // TODO: con un switch expresión sobre this, calculá el conjunto
            //       de destinos válidos desde el estado actual...
            // TODO: ...y devolvé si destino pertenece a ese conjunto.
            return false;
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
        evaluarTransicion(EstadoPedido.PENDIENTE, EstadoPedido.PAGADO);
        // TODO: completá el resto de los pares que pide el enunciado.
    }
}

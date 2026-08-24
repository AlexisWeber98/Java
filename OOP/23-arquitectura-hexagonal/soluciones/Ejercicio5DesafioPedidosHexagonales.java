/*
 * ============================================================================
 * Solución 5 — Desafío: pedidos hexagonales
 * ============================================================================
 * Puntos clave:
 * - Las reglas (mínimo 1 ítem, máximo 20 unidades, total calculado) viven
 *   SOLO en Pedido.crear: el dominio decide qué entra y qué no.
 * - GestorDePedidos orquesta pero no valida ni imprime ni persiste.
 * - Los puertos entrada los implementa la aplicación; los puertos salida los
 *   implementan adaptadores tontos. El main es el único que conoce concretos.
 * - El escenario prueba el borde: exactamente 20 unidades SÍ se acepta.
 *
 * CÓMO COMPILAR Y CORRER (desde soluciones/):
 *   javac *.java && java Ejercicio5DesafioPedidosHexagonales
 */
import java.util.ArrayList;
import java.util.List;

public class Ejercicio5DesafioPedidosHexagonales {

    // ===== DOMINIO ============================================================
    record LineaDePedido(String sku, int cantidad, double precioUnitario) {
        double subtotal() {
            return cantidad * precioUnitario;
        }
    }

    static class PedidoInvalidoException extends RuntimeException {
        PedidoInvalidoException(String mensaje) {
            super(mensaje);
        }
    }

    static class Pedido {
        static final int MAXIMO_UNIDADES = 20;

        private final String id;
        private final List<LineaDePedido> lineas;
        private final double total;

        private Pedido(String id, List<LineaDePedido> lineas, double total) {
            this.id = id;
            this.lineas = lineas;
            this.total = total;
        }

        // Fábrica con las invariantes del dominio. Nadie más puede construir
        // pedidos inválidos: el constructor es privado.
        static Pedido crear(String id, List<LineaDePedido> lineas) {
            if (lineas == null || lineas.isEmpty()) {
                throw new PedidoInvalidoException("un pedido necesita al menos un ítem");
            }
            int unidades = 0;
            for (LineaDePedido linea : lineas) {
                if (linea.cantidad() < 1) {
                    throw new PedidoInvalidoException("cantidad inválida para " + linea.sku());
                }
                unidades += linea.cantidad();
            }
            if (unidades > MAXIMO_UNIDADES) {
                throw new PedidoInvalidoException(
                        "supera el máximo de " + MAXIMO_UNIDADES + " unidades (pidió " + unidades + ")");
            }
            double total = 0.0;
            for (LineaDePedido linea : lineas) {
                total += linea.subtotal();
            }
            return new Pedido(id, List.copyOf(lineas), total);
        }

        String id() { return id; }
        List<LineaDePedido> lineas() { return lineas; }
        double total() { return total; }
    }

    // ===== PUERTOS ENTRADA ====================================================
    record SolicitudDePedido(String id, List<LineaDePedido> lineas) {}

    interface CrearPedidoUseCase {
        Pedido crearPedido(SolicitudDePedido solicitud);
    }

    interface ListarPedidosUseCase {
        List<Pedido> listarPedidos();
    }

    // ===== PUERTOS SALIDA =====================================================
    interface RepositorioPedidos {
        void guardar(Pedido pedido);
        List<Pedido> todos();
    }

    interface NotificadorConfirmacion {
        void notificarConfirmacion(Pedido pedido);
    }

    // ===== APLICACIÓN =========================================================
    static class GestorDePedidos implements CrearPedidoUseCase, ListarPedidosUseCase {
        private final RepositorioPedidos repositorio;
        private final NotificadorConfirmacion notificador;

        GestorDePedidos(RepositorioPedidos repositorio, NotificadorConfirmacion notificador) {
            this.repositorio = repositorio;
            this.notificador = notificador;
        }

        @Override
        public Pedido crearPedido(SolicitudDePedido solicitud) {
            // Delegación pura: si la solicitud viola reglas, el dominio tira
            // PedidoInvalidoException y acá no se atrapa (no es problema suyo).
            Pedido pedido = Pedido.crear(solicitud.id(), solicitud.lineas());
            repositorio.guardar(pedido);
            notificador.notificarConfirmacion(pedido);
            return pedido;
        }

        @Override
        public List<Pedido> listarPedidos() {
            return repositorio.todos();
        }
    }

    // ===== ADAPTADORES SALIDA =================================================
    static class RepositorioPedidosEnMemoria implements RepositorioPedidos {
        private final List<Pedido> pedidos = new ArrayList<>();

        @Override
        public void guardar(Pedido pedido) {
            pedidos.add(pedido);
        }

        @Override
        public List<Pedido> todos() {
            return List.copyOf(pedidos);
        }
    }

    static class ImpresoraDeConfirmacionEnConsola implements NotificadorConfirmacion {
        @Override
        public void notificarConfirmacion(Pedido pedido) {
            System.out.println("=== CONFIRMACIÓN DE PEDIDO ===");
            System.out.println("Pedido " + pedido.id());
            for (LineaDePedido linea : pedido.lineas()) {
                System.out.printf(" - %-8s x%-2d ($%.2f c/u) => $%.2f%n",
                        linea.sku(), linea.cantidad(), linea.precioUnitario(), linea.subtotal());
            }
            System.out.printf(" TOTAL: $%.2f%n", pedido.total());
        }
    }

    // ===== COMPOSITION ROOT + escenario ========================================
    public static void main(String[] args) {
        RepositorioPedidos repositorio = new RepositorioPedidosEnMemoria();
        NotificadorConfirmacion impresora = new ImpresoraDeConfirmacionEnConsola();
        GestorDePedidos gestor = new GestorDePedidos(repositorio, impresora);

        // --- Escenario 1: pedido válido ---
        Pedido aceptado = gestor.crearPedido(new SolicitudDePedido("PED-1", List.of(
                new LineaDePedido("TE-01", 2, 45000.0),
                new LineaDePedido("MO-05", 1, 22000.0))));
        System.out.println("Creado " + aceptado.id() + " con total " + aceptado.total());

        // --- Escenario 2: listar ---
        System.out.println("--- Pedidos en el sistema ---");
        for (Pedido p : gestor.listarPedidos()) {
            System.out.println("- " + p.id() + " (" + p.total() + ")");
        }

        // --- Escenario 3: sin líneas -> rechazado por el dominio ---
        try {
            gestor.crearPedido(new SolicitudDePedido("PED-2", List.of()));
            System.out.println("[FAIL] un pedido vacío fue aceptado");
        } catch (PedidoInvalidoException e) {
            System.out.println("[OK] rechazado por el dominio: " + e.getMessage());
        }

        // --- Escenario 4: 21 unidades -> rechazado ---
        try {
            gestor.crearPedido(new SolicitudDePedido("PED-3", List.of(
                    new LineaDePedido("CA-09", 11, 900.0),
                    new LineaDePedido("HD-02", 10, 1200.0))));
            System.out.println("[FAIL] un pedido de 21 unidades fue aceptado");
        } catch (PedidoInvalidoException e) {
            System.out.println("[OK] rechazado por el dominio: " + e.getMessage());
        }

        // --- Escenario 5: borde aceptable, exactamente 20 unidades ---
        Pedido alBorde = gestor.crearPedido(new SolicitudDePedido("PED-4", List.of(
                new LineaDePedido("CA-09", 10, 900.0),
                new LineaDePedido("HD-02", 10, 1200.0))));
        System.out.println("[OK] el borde funciona: " + alBorde.id()
                + " con 20 unidades aceptado (total " + alBorde.total() + ")");
    }
}

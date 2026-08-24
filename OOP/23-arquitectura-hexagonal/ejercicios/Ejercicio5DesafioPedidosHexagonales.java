/*
 * ============================================================================
 * Ejercicio 5 — Desafío: pedidos hexagonales
 * ============================================================================
 *
 * ENUNCIADO
 * Desafío integrador: armá un mini sistema de pedidos con arquitectura
 * hexagonal COMPLETA. Muchas clases, pero todo en este archivo y un solo
 * runnable. El escenario del main ya está escrito: es tu especificación.
 *
 * Mini-diagrama de la arquitectura que vas a construir:
 *
 *        ADENTRO (no depende de nadie: dominio + casos de uso)
 *   +--------------------------------------------------------------+
 *   |  DOMINIO:  Pedido, LineaDePedido, PedidoInvalidoException     |
 *   |  APLICACION: GestorDePedidos (implementa los puertos entrada) |
 *   +--------------------------------------------------------------+
 *       ^                                    ^
 *       | PUERTOS ENTRADA                    | PUERTOS SALIDA
 *       | CrearPedidoUseCase                 | RepositorioPedidos
 *       | ListarPedidosUseCase               | NotificadorConfirmacion
 *       |                                    |
 *   +---+----------------+          +------+-------------------------+
 *   | ADAPTADORES        |          | ADAPTADORES                   |
 *   | main (composition  |  llama-> | RepositorioPedidosEnMemoria   |
 *   | root / UI / API)   |          | ImpresoraDeConfirmacionConsola|
 *   +--------------------+          +-------------------------------+
 *
 * Reglas del dominio (en Pedido.crear):
 *   - Al menos UNA línea de pedido.
 *   - Máximo 20 unidades TOTALES (sumando cantidades de todas las líneas).
 *   - El total se CALCULA (suma de cantidad * precioUnitario).
 *   - Si algo no cumple: PedidoInvalidoException.
 *
 * REQUISITOS
 * - Las reglas viven SOLO en el dominio; GestorDePedidos no re-valida nada.
 * - Los casos de uso dependen únicamente de puertos.
 * - Los adaptadores no conocen reglas: solo traducen tecnología <-> modelo.
 * - La composición (qué adaptador concreto entra) ocurre SOLO en el main.
 * - El escenario debe mostrar también un pedido RECHAZADO por el dominio.
 *
 * PISTAS
 * - Empezá por el dominio: si Pedido.crear() valida bien, todo lo demás es
 *   cableado.
 * - Excepción de dominio para rechazos esperables + try/catch en el main.
 * - Probá el borde: exactamente 20 unidades DEBE aceptarse.
 *
 * CÓMO COMPILAR Y CORRER (desde esta carpeta):
 *   javac *.java && java Ejercicio5DesafioPedidosHexagonales
 */
import java.util.ArrayList;
import java.util.List;

public class Ejercicio5DesafioPedidosHexagonales {

    // =========================================================================
    // DOMINIO
    // =========================================================================
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

        static Pedido crear(String id, List<LineaDePedido> lineas) {
            // TODO: regla 1) al menos una línea; si no -> new PedidoInvalidoException(...)
            // TODO: regla 2) cada línea con cantidad >= 1
            // TODO: regla 3) suma de unidades <= MAXIMO_UNIDADES
            // TODO: regla 4) calcular el total y devolver new Pedido(id, List.copyOf(lineas), total)
            throw new UnsupportedOperationException("TODO: implementá Pedido.crear");
        }

        String id() { return id; }
        List<LineaDePedido> lineas() { return lineas; }
        double total() { return total; }
    }

    // =========================================================================
    // PUERTOS ENTRADA (los define la aplicación)
    // =========================================================================
    record SolicitudDePedido(String id, List<LineaDePedido> lineas) {}

    interface CrearPedidoUseCase {
        Pedido crearPedido(SolicitudDePedido solicitud);
    }

    interface ListarPedidosUseCase {
        List<Pedido> listarPedidos();
    }

    // =========================================================================
    // PUERTOS SALIDA
    // =========================================================================
    interface RepositorioPedidos {
        void guardar(Pedido pedido);
        List<Pedido> todos();
    }

    interface NotificadorConfirmacion {
        void notificarConfirmacion(Pedido pedido);
    }

    // =========================================================================
    // APLICACIÓN: el caso de uso orquesta dominio + puertos. No valida reglas:
    // eso es cosa del dominio. No imprime ni persiste: eso es cosa de los
    // adaptadores.
    // =========================================================================
    static class GestorDePedidos implements CrearPedidoUseCase, ListarPedidosUseCase {
        private final RepositorioPedidos repositorio;
        private final NotificadorConfirmacion notificador;

        GestorDePedidos(RepositorioPedidos repositorio, NotificadorConfirmacion notificador) {
            this.repositorio = repositorio;
            this.notificador = notificador;
        }

        @Override
        public Pedido crearPedido(SolicitudDePedido solicitud) {
            // TODO: 1) delegar la creación en Pedido.crear (el dominio decide)
            //       2) guardar en el repositorio
            //       3) notificar la confirmación
            //       4) devolver el pedido creado
            throw new UnsupportedOperationException("TODO: implementá crearPedido");
        }

        @Override
        public List<Pedido> listarPedidos() {
            // TODO: devolvé lo que dice el repositorio.
            throw new UnsupportedOperationException("TODO: implementá listarPedidos");
        }
    }

    // =========================================================================
    // ADAPTADORES SALIDA
    // =========================================================================
    static class RepositorioPedidosEnMemoria implements RepositorioPedidos {
        private final List<Pedido> pedidos = new ArrayList<>();

        @Override
        public void guardar(Pedido pedido) {
            // TODO
            throw new UnsupportedOperationException("TODO: implementá guardar");
        }

        @Override
        public List<Pedido> todos() {
            // TODO
            throw new UnsupportedOperationException("TODO: implementá todos");
        }
    }

    static class ImpresoraDeConfirmacionEnConsola implements NotificadorConfirmacion {
        @Override
        public void notificarConfirmacion(Pedido pedido) {
            // TODO: imprimí un ticket: encabezado, una línea por ítem con su
            //  subtotal y el total final.
            throw new UnsupportedOperationException("TODO: implementá notificarConfirmacion");
        }
    }

    // =========================================================================
    // COMPOSITION ROOT: único lugar que conoce adaptadores concretos.
    // =========================================================================
    public static void main(String[] args) {
        RepositorioPedidos repositorio = new RepositorioPedidosEnMemoria();
        NotificadorConfirmacion impresora = new ImpresoraDeConfirmacionEnConsola();
        GestorDePedidos gestor = new GestorDePedidos(repositorio, impresora);

        // --- Escenario 1: pedido válido ---
        Pedido aceptado = gestor.crearPedido(new SolicitudDePedido("PED-1", List.of(
                new LineaDePedido("TE-01", 2, 45000.0),
                new LineaDePedido("MO-05", 1, 22000.0))));
        System.out.println("Creado " + aceptado.id() + " con total " + aceptado.total());

        System.out.println("--- Pedidos en el sistema ---");
        for (Pedido p : gestor.listarPedidos()) {
            System.out.println("- " + p.id() + " (" + p.total() + ")");
        }

        // --- Escenario 2: pedido sin líneas -> RECHAZADO por el dominio ---
        try {
            gestor.crearPedido(new SolicitudDePedido("PED-2", List.of()));
            System.out.println("[FAIL] un pedido vacío fue aceptado");
        } catch (PedidoInvalidoException e) {
            System.out.println("[OK] rechazado por el dominio: " + e.getMessage());
        }

        // --- Escenario 3: más de 20 unidades totales -> RECHAZADO ---
        try {
            gestor.crearPedido(new SolicitudDePedido("PED-3", List.of(
                    new LineaDePedido("CA-09", 11, 900.0),
                    new LineaDePedido("HD-02", 10, 1200.0))));
            System.out.println("[FAIL] un pedido de 21 unidades fue aceptado");
        } catch (PedidoInvalidoException e) {
            System.out.println("[OK] rechazado por el dominio: " + e.getMessage());
        }
    }
}

/*
 * Módulo 10 · Ejemplo 2: enum con campos, constructor y métodos.
 * Igual que cualquier clase, un enum guarda datos y expone comportamiento;
 * su constructor es implícitamente privado y la JVM lo llama por constante.
 */
public class EstadoPedidoConDatos {

    enum EstadoPedido {
        PENDIENTE("Pedido recibido, aún sin procesar", 0),
        EN_PREPARACION("En preparación en el depósito", 1),
        ENVIADO("Despachado, en camino", 2),
        ENTREGADO("Recibido por el cliente", 0);

        private final String descripcion;
        private final int diasEstimados;

        // Se invoca exactamente una vez por constante, al cargar la clase.
        EstadoPedido(String descripcion, int diasEstimados) {
            this.descripcion = descripcion;
            this.diasEstimados = diasEstimados;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public int getDiasEstimados() {
            return diasEstimados;
        }

        // Comportamiento apoyado en los campos: cada constante sabe responder.
        public boolean sigueActivo() {
            return this != ENTREGADO;
        }

        @Override
        public String toString() {
            return name() + " → " + descripcion
                    + " (" + diasEstimados + " día(s) estimado(s))";
        }
    }

    public static void main(String[] args) {
        System.out.println("Estados del pedido:");
        for (EstadoPedido estado : EstadoPedido.values()) {
            System.out.println("  " + estado);
        }

        EstadoPedido actual = EstadoPedido.ENVIADO;
        System.out.println("\n¿Sigue activo? " + actual.sigueActivo());

        // Conversión desde String: lanza IllegalArgumentException si no existe.
        try {
            EstadoPedido leido = EstadoPedido.valueOf("EN_PREPARACION");
            System.out.println("Leído desde texto: " + leido.getDescripcion());
        } catch (IllegalArgumentException e) {
            System.out.println("Texto inválido para un estado: " + e.getMessage());
        }
    }
}

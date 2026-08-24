// Excepción checked propia para una regla de negocio: el que llama DEBE manejarla.
// Ejecutar: java ExcepcionPropiaDeNegocio.java

public class ExcepcionPropiaDeNegocio {

    // Excepción de dominio: lleva datos propias del fallo (montoFaltante).
    static class SaldoInsuficienteException extends Exception {
        private final double montoFaltante;

        public SaldoInsuficienteException(String mensaje, double montoFaltante) {
            super(mensaje);
            this.montoFaltante = montoFaltante;
        }

        public double getMontoFaltante() {
            return montoFaltante;
        }
    }

    // El cajero declara (throws) la posibilidad y lanza (throw) con datos útiles.
    static class Cajero {
        private double saldo;

        public Cajero(double saldoInicial) {
            this.saldo = saldoInicial;
        }

        public void retirar(double monto) throws SaldoInsuficienteException {
            if (monto <= 0) {
                throw new IllegalArgumentException("El monto debe ser positivo");
            }
            if (monto > saldo) {
                double faltante = monto - saldo;
                throw new SaldoInsuficienteException(
                        "Saldo insuficiente para retirar $" + monto, faltante);
            }
            saldo -= monto;
            System.out.println("Retiro exitoso. Saldo restante: $" + saldo);
        }
    }

    public static void main(String[] args) {
        Cajero cajero = new Cajero(1000);

        try {
            cajero.retirar(300);   // camino feliz
            cajero.retirar(2500);  // acá explota la regla de negocio
        } catch (SaldoInsuficienteException e) {
            // Gracias al campo propio, el mensaje es accionable:
            System.out.println("No se pudo completar el retiro.");
            System.out.println(e.getMessage());
            System.out.printf("Te faltaron $%.2f para cubrirlo.%n", e.getMontoFaltante());
        }

        // El programa sigue vivo y el flujo continúa.
        System.out.println("\nSesión finalizada con gracia, sin crash.");
    }
}

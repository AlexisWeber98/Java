/*
 * ============================================================================
 * Módulo 14 · Excepciones — Solución 3: Excepción propia para saldo insuficiente
 * ============================================================================
 * IDEA CLAVE: una excepción verificada es parte de la firma del método: obliga
 * a quien llama a ocuparse de la regla de negocio. Y cargarle contexto (saldo
 * actual, monto solicitado) convierte al catch en un punto de decisión con
 * datos concretos, no en un adivinador de qué pasó.
 *
 * super(mensaje) del constructor es lo que alimenta getMessage(): la excepción
 * describe su propio contexto desde que nace.
 * ============================================================================
 */
public class Ejercicio3ExcepcionPropiaSaldoSolucion {

    /** Excepción de negocio: el saldo no alcanza para cubrir el monto pedido. */
    static class SaldoInsuficienteException extends Exception {

        private final double saldoActual;
        private final double montoSolicitado;

        public SaldoInsuficienteException(double saldoActual, double montoSolicitado) {
            // super(mensaje) alimenta getMessage(): el error se explica solo.
            super("Saldo insuficiente: solicitaste " + montoSolicitado
                    + " pero solo tenés " + saldoActual + " disponibles.");
            this.saldoActual = saldoActual;
            this.montoSolicitado = montoSolicitado;
        }

        public double getSaldoActual() {
            return saldoActual;
        }

        public double getMontoSolicitado() {
            return montoSolicitado;
        }
    }

    /** Cuenta bancaria mínima para el ejercicio. */
    static class Cuenta {
        private double saldo;

        Cuenta(double saldoInicial) {
            this.saldo = saldoInicial;
        }

        double getSaldo() {
            return saldo;
        }

        void debitar(double monto) {
            this.saldo -= monto;
        }
    }

    /** Cajero que opera sobre una cuenta. */
    static class Cajero {

        /**
         * Debita el monto de la cuenta.
         *
         * @throws SaldoInsuficienteException si el monto supera el saldo disponible.
         */
        static void retirar(Cuenta cuenta, double monto) throws SaldoInsuficienteException {
            if (monto > cuenta.getSaldo()) {
                // Lanzamos CON contexto: quien capture podrá decidir con datos,
                // no reconstruyendo la escena del crimen.
                throw new SaldoInsuficienteException(cuenta.getSaldo(), monto);
            }
            cuenta.debitar(monto);
            System.out.println("Retiro exitoso de " + monto + ".");
        }
    }

    public static void main(String[] args) {
        Cuenta cuenta = new Cuenta(100000);
        double[] intentos = {50000, 80000, 30000}; // el segundo no alcanza

        for (double intento : intentos) {
            System.out.println("--- Intento de retiro de " + intento + " ---");
            try {
                Cajero.retirar(cuenta, intento);
            } catch (SaldoInsuficienteException e) {
                System.out.println(e.getMessage());
                System.out.println("Detalle: saldo actual = " + e.getSaldoActual()
                        + ", monto solicitado = " + e.getMontoSolicitado());
            }
            System.out.println("Saldo actual de la cuenta: " + cuenta.getSaldo());
        }

        System.out.println();
        System.out.println("Fin del programa: el segundo intento falló y el flujo siguió.");
    }
}

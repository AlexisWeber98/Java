/*
 * ============================================================================
 * Módulo 14 · Excepciones — Ejercicio 3: Excepción propia para saldo insuficiente
 * ============================================================================
 *
 * ENUNCIADO:
 * Creá tu propia excepción verificada SaldoInsuficienteException que cargue
 * el contexto del error (saldo actual y monto solicitado). El cajero debe
 * lanzarla desde retirar(...) cuando el monto supere el saldo, y el main debe
 * intentar tres extracciones capturándola adecuadamente.
 *
 * REQUISITOS:
 *   1. SaldoInsuficienteException extiende Exception (verificada).
 *   2. Guarda saldoActual y montoSolicitado, con sus getters.
 *   3. getMessage() devuelve un mensaje descriptivo con ambos montos.
 *   4. Cajero.retirar(...) declara throws y lanza la excepción.
 *   5. El main hace tres intentos: exitoso, fallido y exitoso; tras cada error
 *      el programa continúa normalmente.
 *
 * PISTAS:
 *   - super(mensaje) en el constructor es lo que alimenta getMessage().
 *   - Una excepción verificada obliga a quien llama a usar try/catch o a
 *     declarar throws. Para reglas de negocio, eso es una virtud, no un molesto.
 *   - Podés declarar varias clases en un mismo archivo mientras solo una sea
 *     public; acá usamos clases anidadas static para mantener todo junto.
 * ============================================================================
 */
public class Ejercicio3ExcepcionPropiaSaldo {

    /** Excepción de negocio: el saldo no alcanza para cubrir el monto pedido. */
    static class SaldoInsuficienteException extends Exception {

        // TODO: campos privados saldoActual y montoSolicitado (double)

        public SaldoInsuficienteException(double saldoActual, double montoSolicitado) {
            // TODO: pasale a super(...) un mensaje descriptivo que incluya ambos
            //  montos, por ejemplo:
            //  "Saldo insuficiente: solicitaste 80000.0 pero tenés 50000.0 disponibles."
            super("Saldo insuficiente");
            // TODO: guardá los valores en los campos
        }

        // TODO: getters getSaldoActual() y getMontoSolicitado()
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
            // TODO: si monto > saldo disponible, lanzá
            //  new SaldoInsuficienteException(saldoDisponible, monto)
            // TODO: si alcanza, debitá y avisá el retiro exitoso
        }
    }

    public static void main(String[] args) {
        Cuenta cuenta = new Cuenta(100000);
        double[] intentos = {50000, 80000, 30000}; // el segundo no alcanza

        // TODO: recorré los intentos. Alrededor de cada Cajero.retirar(...) poné
        //  try/catch sobre SaldoInsuficienteException mostrando e.getMessage(),
        //  e.getSaldoActual() y e.getMontoSolicitado(). Después de cada intento
        //  (falle o no), mostrá el saldo actual de la cuenta.
        System.out.println("(completá el main)");
    }
}

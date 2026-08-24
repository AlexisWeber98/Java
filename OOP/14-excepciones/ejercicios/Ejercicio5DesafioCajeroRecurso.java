/*
 * ============================================================================
 * Módulo 14 · Excepciones — Ejercicio 5 (DESAFÍO): Cajero con recursos
 * ============================================================================
 *
 * ENUNCIADO:
 * Combiná excepciones propias con manejo automático de recursos.
 * RecursoConexion modela una conexión que debe abrirse y cerrarse siempre;
 * la transacción del cajero corre dentro de un try-with-resources y ejecuta
 * un depósito y un retiro. El desafío: comprobar que el cierre ocurre INCLUSO
 * cuando SaldoInsuficienteException escapa del bloque try.
 *
 * REQUISITOS:
 *   1. RecursoConexion implementa AutoCloseable: al crearse anuncia que abre
 *      y su close() anuncia que cierra (ya está listo, no lo toques).
 *   2. La transacción usa try-with-resources sobre el recurso.
 *   3. Dentro: cuenta.depositar(...) y cuenta.retirar(...), que puede lanzar
 *      SaldoInsuficienteException (la misma idea del ejercicio 3).
 *   4. Dos escenarios: uno exitoso y otro que explota a mitad de camino.
 *   5. Mirá el ORDEN de la salida: "[CONEXION] cerrada" aparece ANTES de que
 *      quien llamó capture la excepción. Esa es la prueba clave.
 *   6. Informe final con el saldo de ambas cuentas.
 *
 * PISTAS:
 *   - try (RecursoConexion conexion = new RecursoConexion()) { ... } llama a
 *     close() automáticamente al salir, haya excepción o no.
 *   - No escribas finally ni cierres a mano: eso es justamente lo que el
 *     try-with-resources elimina.
 *   - El saldo del depósito queda hecho aunque después falle el retiro:
 *     pensalo como una transacción incompleta y comentalo en el reporte.
 * ============================================================================
 */
public class Ejercicio5DesafioCajeroRecurso {

    /** Excepción de negocio (misma idea que el ejercicio 3). */
    static class SaldoInsuficienteException extends Exception {

        public SaldoInsuficienteException(double saldoActual, double montoSolicitado) {
            super("Saldo insuficiente: solicitaste " + montoSolicitado
                    + " pero solo tenés " + saldoActual + " disponibles.");
        }
    }

    /**
     * RECURSO YA LISTO: simula una conexión costosa que siempre debe cerrarse.
     * Implementar AutoCloseable es lo que habilita usarlo en try-with-resources.
     */
    static class RecursoConexion implements AutoCloseable {

        public RecursoConexion() {
            System.out.println("  [CONEXION] abierta");
        }

        @Override
        public void close() {
            System.out.println("  [CONEXION] cerrada");
        }
    }

    /** Cuenta mínima con operaciones de depósito y retiro. */
    static class Cuenta {
        private double saldo;

        Cuenta(double saldoInicial) {
            this.saldo = saldoInicial;
        }

        double getSaldo() {
            return saldo;
        }

        void depositar(double monto) {
            saldo += monto;
            System.out.println("  [CAJERO] depósito de " + monto + " realizado.");
        }

        void retirar(double monto) throws SaldoInsuficienteException {
            if (monto > saldo) {
                throw new SaldoInsuficienteException(saldo, monto);
            }
            saldo -= monto;
            System.out.println("  [CAJERO] retiro de " + monto + " realizado.");
        }
    }

    /**
     * Ejecuta una transacción completa: abre conexión, deposita y retira.
     *
     * @throws SaldoInsuficienteException si el retiro no puede cubrirse.
     */
    static void ejecutarTransaccion(Cuenta cuenta, double deposito, double retiro)
            throws SaldoInsuficienteException {
        // TODO: envolvé las operaciones en un try-with-resources que cree el
        //  RecursoConexion. Adentro: cuenta.depositar(deposito) y
        //  cuenta.retirar(retiro). Sin finally y sin close() manual.
    }

    public static void main(String[] args) {
        // TODO escenario 1 (exitoso): cuenta nueva con saldo 100000; deposito 20000,
        //  retiro 15000. Capturá lo improbable y mostrá el saldo final.

        // TODO escenario 2 (fallido): otra cuenta con saldo 50000; deposito 10000,
        //  retiro 999999. Capturá SaldoInsuficienteException, mostrá su mensaje y
        //  comprobá en la salida que "[CONEXION] cerrada" apareció IGUAL aunque la
        //  excepción escapó. Mostrá el saldo final (el depósito quedó hecho).

        System.out.println("(completá los escenarios)");
    }
}

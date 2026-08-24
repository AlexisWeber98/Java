/*
 * ============================================================================
 * Módulo 14 · Excepciones — Solución 5 (DESAFÍO): Cajero con recursos
 * ============================================================================
 * IDEA CLAVE: try-with-resources ejecuta close() durante el desenrollado de la
 * excepción, ANTES de que el catch externo la reciba. Por eso en la salida
 * "[CONEXION] cerrada" antecede al "[ERROR]". Es el equivalente moderno y
 * conciso del viejo finally { recurso.close(); }, sin el ruido y sin riesgo de
 * olvidarse del cierre.
 *
 * Observación honesta sobre transacciones: si el retiro falla, el depósito ya
 * quedó hecho. Con una sola conexión no hay rollback automático; para eso
 * existe el patrón Unit of Work con commit/rollback. Acá nos alcanza con
 * REPORTAR el estado final real de cada cuenta.
 * ============================================================================
 */
public class Ejercicio5DesafioCajeroRecursoSolucion {

    /** Excepción de negocio (misma idea que el ejercicio 3). */
    static class SaldoInsuficienteException extends Exception {

        public SaldoInsuficienteException(double saldoActual, double montoSolicitado) {
            super("Saldo insuficiente: solicitaste " + montoSolicitado
                    + " pero solo tenés " + saldoActual + " disponibles.");
        }
    }

    /**
     * Recurso que simula una conexión costosa: abrir y cerrar quedan registrados.
     * Implementar AutoCloseable es lo que habilita usarlo en try-with-resources.
     */
    static class RecursoConexion implements AutoCloseable {

        public RecursoConexion() {
            System.out.println("  [CONEXION] abierta");
        }

        @Override
        public void close() {
            // try-with-resources garantiza esta llamada SIEMPRE,
            // termine bien el bloque o vuele todo por los aires.
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
        // El recurso vive SOLO dentro del try: al salir —bien o mal— close()
        // corre ANTES de que la excepción siga su camino hacia quien llamó.
        try (RecursoConexion conexion = new RecursoConexion()) {
            cuenta.depositar(deposito);
            cuenta.retirar(retiro); // acá puede estallar todo
        }
        // Ni finally ni close() manual: eso justamente lo elimina esta sintaxis.
    }

    public static void main(String[] args) {
        System.out.println("Escenario 1: transacción exitosa");
        Cuenta cuentaExitosa = new Cuenta(100000);
        try {
            ejecutarTransaccion(cuentaExitosa, 20000, 15000);
        } catch (SaldoInsuficienteException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
        System.out.println("  [REPORTE] saldo final: " + cuentaExitosa.getSaldo());

        System.out.println();
        System.out.println("Escenario 2: transacción con saldo insuficiente");
        Cuenta cuentaFallida = new Cuenta(50000);
        try {
            ejecutarTransaccion(cuentaFallida, 10000, 999999);
        } catch (SaldoInsuficienteException e) {
            // Mirá el orden de la salida: "[CONEXION] cerrada" aparece ANTES de
            // este mensaje. close() corre durante el desenrollado, antes de que
            // este catch reciba la excepción. Esa es la prueba del desafío.
            System.out.println("  [ERROR] " + e.getMessage());
        }
        System.out.println("  [REPORTE] saldo final: " + cuentaFallida.getSaldo()
                + " (el depósito quedó hecho; el retiro, abortado)");
    }
}

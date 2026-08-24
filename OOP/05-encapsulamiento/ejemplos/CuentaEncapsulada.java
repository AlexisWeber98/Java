/*
 * CuentaEncapsulada.java
 *
 * El mismo escenario que CampoPublicoPeligroso, pero con encapsulamiento:
 * la corrupción se vuelve imposible y los errores aparecen en el momento exacto.
 */
public class CuentaEncapsulada {

    static class CuentaBancaria {
        private String titular;   // privado: solo esta clase toca el estado
        private double saldo;

        public CuentaBancaria(String titular, double saldoInicial) {
            // El constructor es el primer punto de control.
            if (titular == null || titular.isBlank()) {
                throw new IllegalArgumentException("El titular es obligatorio");
            }
            if (saldoInicial < 0) {
                throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
            }
            this.titular = titular;
            this.saldo = saldoInicial;
        }

        // Getters: lectura permitida, escritura directa prohibida.
        public String getTitular() { return titular; }
        public double getSaldo()   { return saldo; }

        // Comportamiento en lugar de asignaciones externas.
        public void depositar(double monto) {
            if (monto <= 0) {
                throw new IllegalArgumentException("El depósito debe ser positivo");
            }
            saldo += monto;
        }

        public void extraer(double monto) {
            if (monto <= 0) {
                throw new IllegalArgumentException("La extracción debe ser positiva");
            }
            if (monto > saldo) {
                throw new IllegalStateException("Saldo insuficiente: " + saldo);
            }
            saldo -= monto;
        }

        @Override
        public String toString() {
            return "CuentaBancaria{titular='" + titular + "', saldo=" + saldo + "}";
        }
    }

    public static void main(String[] args) {
        CuentaBancaria cuenta = new CuentaBancaria("Lucía Fernández", 5000);
        System.out.println("Estado inicial:    " + cuenta);

        // Los mismos "accidentes" del ejemplo peligroso, ahora frenados:
        intentar(() -> cuenta.extraer(-150),
                "descuento con signo invertido");
        intentar(() -> cuenta.extraer(99999),
                "extracción mayor al saldo");
        intentar(() -> new CuentaBancaria("", 100),
                "crear cuenta sin titular");

        // Las operaciones legítimas siguen funcionando igual de simple:
        cuenta.depositar(500);
        System.out.println("\nTras depósito válido: " + cuenta);

        System.out.println("\n========== MORAL DE LA COMPARACIÓN ==========");
        System.out.println("""
                \
                Campo público  -> el error viaja lejos y explota después.
                Encapsulado    -> el error grita EN la línea culpable.
                 - Invariantes defendidas en un único punto.
                 - El compilador bloquea el acceso directo: no compila ni por error.
                 - La clase comunica QUÉ se puede hacer, no solo qué guarda.
                """);
    }

    // Ejecuta una operación esperando que falle, e informa el motivo.
    private static void intentar(Runnable operacion, String descripcion) {
        try {
            operacion.run();
            System.out.println("NO falló (¡mal!): " + descripcion);
        } catch (RuntimeException e) {
            System.out.println("Rechazado '" + descripcion + "': " + e.getMessage());
        }
    }
}

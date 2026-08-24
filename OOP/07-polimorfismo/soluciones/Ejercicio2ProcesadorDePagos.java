/*
 * ============================================================================
 * Módulo 07 – Polimorfismo | Ejercicio 2: Procesador de pagos (SOLUCIÓN)
 * ============================================================================
 * Idea clave: procesarPedido(...) recibe MetodoPago y jamás nombra una
 * subclase concreta. Un único punto de código atiende todos los medios.
 */
public class Ejercicio2ProcesadorDePagos {

    static abstract class MetodoPago {
        // El conocimiento del cobro vive en la subclase correcta: cada medio
        // sabe cobrarse solo. A esto apunta el polimorfismo.
        abstract String procesar(double monto);
    }

    static class TarjetaCredito extends MetodoPago {
        @Override
        String procesar(double monto) {
            return String.format("[Tarjeta] Autorizando crédito por $%.2f...", monto);
        }
    }

    static class Paypal extends MetodoPago {
        @Override
        String procesar(double monto) {
            return String.format("[PayPal] Redirigiendo a PayPal para pagar $%.2f...", monto);
        }
    }

    static class Transferencia extends MetodoPago {
        @Override
        String procesar(double monto) {
            return String.format("[Transferencia] Generando CBU y orden de $%.2f...", monto);
        }
    }

    // Recibe la ABSTRACCIÓN: no hay ifs por tipo ni casts. Un solo método que
    // funciona para todos los medios actuales y futuros.
    static void procesarPedido(MetodoPago metodo, double monto) {
        System.out.println(metodo.procesar(monto));
    }

    public static void main(String[] args) {
        // Upcasting en el arreglo: los tres entran como MetodoPago.
        MetodoPago[] medios = { new TarjetaCredito(), new Paypal(), new Transferencia() };

        for (MetodoPago medio : medios) {
            procesarPedido(medio, 25000);
        }
    }
}

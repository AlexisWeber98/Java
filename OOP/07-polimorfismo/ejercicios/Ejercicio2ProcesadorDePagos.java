/*
 * ============================================================================
 * Módulo 07 – Polimorfismo | Ejercicio 2: Procesador de pagos
 * ============================================================================
 *
 * ENUNCIADO:
 *   Modelá un sistema de cobros donde MetodoPago declara procesar(double
 *   monto) y cada medio concreto lo implementa a su manera. Después escribí UN
 *   único método procesarPedido(MetodoPago metodo, double monto) que sirva
 *   para TODOS los medios, y probalo con los tres desde main.
 *
 * REQUISITOS:
 *   1. MetodoPago: clase abstracta con el método abstracto procesar(double)
 *      que devuelve un String describiendo la operación.
 *   2. TarjetaCredito, Paypal y Transferencia sobrescriben procesar() con su
 *      propio mensaje (cada una incluye el monto en el texto).
 *   3. procesarPedido(...) recibe MetodoPago y NO menciona ninguna subclase:
 *      adentro solo llama metodo.procesar(monto) e imprime el resultado.
 *   4. Desde main, generá un pedido por cada medio usando SIEMPRE el mismo
 *      procesarPedido.
 *
 * PISTAS:
 *   - Si te tentás con poner un if por tipo de pago adentro de procesarPedido,
 *     frená: ese es exactamente el código que el polimorfismo elimina.
 *   - Pensalo así: cada subclase sabe COBRARSE sola; procesarPedido solo pide.
 *   - ¿Y si mañana agregás MercadoPago o cripto? Nueva subclase, y
 *     procesarPedido queda intacto.
 *
 * Ejecución:  java Ejercicio2ProcesadorDePagos.java
 */
public class Ejercicio2ProcesadorDePagos {

    static abstract class MetodoPago {
        // Cada medio concreto define cómo se cobra.
        abstract String procesar(double monto);
    }

    static class TarjetaCredito extends MetodoPago {
        @Override
        String procesar(double monto) {
            // TODO: devolvé un mensaje tipo "[Tarjeta] Autorizando crédito por $X...".
            return "";
        }
    }

    static class Paypal extends MetodoPago {
        @Override
        String procesar(double monto) {
            // TODO: devolvé tu mensaje de PayPal con el monto incluido.
            return "";
        }
    }

    static class Transferencia extends MetodoPago {
        @Override
        String procesar(double monto) {
            // TODO: devolvé tu mensaje de transferencia con el monto incluido.
            return "";
        }
    }

    // Un solo método para TODOS los medios: recibe la abstracción, no casos.
    static void procesarPedido(MetodoPago metodo, double monto) {
        System.out.println(metodo.procesar(monto));
    }

    public static void main(String[] args) {
        // TODO: llamá tres veces a procesarPedido, una por medio de pago,
        // sin duplicar lógica ni agregar condicionales.
        System.out.println("(Falta implementar procesar() y las llamadas de prueba)");
    }
}

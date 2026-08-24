package solucion;

/**
 * Excepción de dominio del cajero (solución): se lanza cuando un retiro
 * supera el saldo disponible.
 */
public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

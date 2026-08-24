package practica;

/**
 * Excepción de dominio del cajero: se lanza cuando un retiro supera el saldo
 * disponible. Hereda de RuntimeException porque es un error de reglas de
 * negocio que no obliga a quien llama a declararla.
 */
public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

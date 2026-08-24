package soluciones;

/** Excepción propia del dominio: la lanza CajaFuerte cuando no alcanza el saldo. */
class SaldoInsuficienteException extends RuntimeException {

    SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

package servicio;

/** El producto pedido no existe en la base. */
public class EntidadNoEncontradaException extends RuntimeException {

    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}

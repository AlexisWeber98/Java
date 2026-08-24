package servicio;

/** Regla de negocio violada (módulo 14: excepciones propias, no genéricas). */
public class ValidacionException extends RuntimeException {

    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}

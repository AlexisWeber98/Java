/*
 * ExcepcionesBiblioteca.java — Proyecto Integrador N°1: Biblioteca
 * Familia completa de excepciones propias del dominio, reunida en UN archivo.
 *
 * Ninguna clase es pública a propósito: Java permite varias clases de nivel
 * superior por archivo siempre que NINGUNA sea pública (una clase pública
 * obligaría a que su nombre coincida con el nombre del archivo). Como todas
 * viven en la misma carpeta-paquete por defecto, el resto de las clases las
 * usa sin ningún trámite extra.
 *
 * Jerarquía:
 *   Exception
 *     └── ExcepcionBiblioteca            (base común, abstracta)
 *           ├── ItemNoDisponibleException
 *           ├── SocioConLimiteAlcanzadoException
 *           └── ItemInexistenteException
 */

/** Base común: son excepciones "checked" y comparten origen en el dominio. */
abstract class ExcepcionBiblioteca extends Exception {

    protected ExcepcionBiblioteca(String mensaje) {
        super(mensaje);
    }
}

/** El ítem existe pero hoy no puede prestarse (estado o regla del tipo). */
class ItemNoDisponibleException extends ExcepcionBiblioteca {

    private final String codigoItem;
    private final EstadoItem estadoActual;

    ItemNoDisponibleException(String codigoItem, String titulo, EstadoItem estadoActual) {
        super("No se pudo prestar «%s» (%s): su situación actual es %s — %s. ".formatted(
                titulo, codigoItem, estadoActual, estadoActual.getDescripcion())
                + (estadoActual == EstadoItem.DISPONIBLE
                        ? "Ojo: está en el estante, pero la regla propia de este tipo de ítem no permite llevarlo."
                        : "Mirá el detalle del ítem o probá con otro."));
        this.codigoItem = codigoItem;
        this.estadoActual = estadoActual;
    }

    public String getCodigoItem() {
        return codigoItem;
    }

    public EstadoItem getEstadoActual() {
        return estadoActual;
    }
}

/** El socio ya alcanzó su cupo máximo de préstamos simultáneos. */
class SocioConLimiteAlcanzadoException extends ExcepcionBiblioteca {

    private final String nombreSocio;
    private final int limitePermitido;

    SocioConLimiteAlcanzadoException(Socio socio) {
        super("El socio %s (#%d) ya tiene %d préstamos activos, que es justo su límite. "
                .formatted(socio.getNombre(), socio.getId(), Socio.LIMITE_PRESTAMOS_SIMULTANEOS)
                + "Devolvé algo antes de llevarte más material.");
        this.nombreSocio = socio.getNombre();
        this.limitePermitido = Socio.LIMITE_PRESTAMOS_SIMULTANEOS;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public int getLimitePermitido() {
        return limitePermitido;
    }
}

/** La búsqueda por clave no encontró nada en el repositorio. */
class ItemInexistenteException extends ExcepcionBiblioteca {

    private final String identificadorBuscado;

    ItemInexistenteException(String identificadorBuscado, String contexto) {
        super("No existe ningún registro para «%s» (%s). Revisá el dato e intentá de nuevo."
                .formatted(identificadorBuscado, contexto));
        this.identificadorBuscado = identificadorBuscado;
    }

    public String getIdentificadorBuscado() {
        return identificadorBuscado;
    }
}

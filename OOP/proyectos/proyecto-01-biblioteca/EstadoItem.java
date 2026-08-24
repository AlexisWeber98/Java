/*
 * EstadoItem.java — Proyecto Integrador N°1: Biblioteca
 * Estados posibles de un ítem del catálogo. Cada constante lleva una
 * descripción lista para mostrar al usuario final.
 *
 * Compilación y ejecución: javac *.java && java Main
 */
public enum EstadoItem {

    DISPONIBLE("en estante, listo para prestarse"),
    PRESTADO("entregado a un socio, fuera del estante"),
    EN_REPARACION("fuera de circulación hasta terminar su revisión");

    private final String descripcion;

    EstadoItem(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

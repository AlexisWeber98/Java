/*
 * RegistroPrestamo.java — Proyecto Integrador N°1: Biblioteca
 * Record inmutable: una línea del historial de movimientos.
 * El enum anidado Accion distingue préstamos de devoluciones.
 * Al ser record, gana equals/hashCode/toString base gratis; acá ajustamos
 * toString para que la línea quede linda en el historial.
 *
 * Compilación y ejecución: javac *.java && java Main
 */
public record RegistroPrestamo(String itemTitulo, String socioNombre,
                               String fechaTexto, Accion accion) {

    /** Tipo de movimiento registrado en el historial. */
    public enum Accion {
        PRESTAMO("-->"),
        DEVOLUCION("<--");

        private final String flecha;

        Accion(String flecha) {
            this.flecha = flecha;
        }

        public String getFlecha() {
            return flecha;
        }
    }

    /** Línea lista para mostrar en el historial de movimientos. */
    @Override
    public String toString() {
        return "%s %s «%s» — socio/a: %s".formatted(fechaTexto, accion.getFlecha(), itemTitulo, socioNombre);
    }
}

/*
 * Revista.java — Proyecto Integrador N°1: Biblioteca
 * Regla editorial propia: la edición del año en curso se consulta SOLO en sala
 * (no sale de la biblioteca); las ediciones de años anteriores sí se prestan.
 * Esa diferencia respecto del Libro es polimorfismo puro: el gestor pregunta,
 * la subclase contesta con su propio criterio.
 *
 * Compilación y ejecución: javac *.java && java Main
 */
public class Revista extends ItemBiblioteca {

    /** Año considerado "edición actual"; fijo a mano para que la demo sea estable. */
    public static final int ANIO_EDICION_ACTUAL = 2026;

    private final int numeroEdicion;
    private final String periodicidad;

    public Revista(String codigo, String titulo, int anio, CategoriaItem categoria,
                   int numeroEdicion, String periodicidad) {
        super(codigo, titulo, anio, categoria);
        this.numeroEdicion = numeroEdicion;
        this.periodicidad = periodicidad;
    }

    /** Disponible solo si está en estante Y no es la edición del año corriente. */
    @Override
    public boolean estaDisponibleParaPrestamo() {
        return getEstado() == EstadoItem.DISPONIBLE && getAnio() < ANIO_EDICION_ACTUAL;
    }

    @Override
    public String descripcionDetallada() {
        String politica = (getAnio() < ANIO_EDICION_ACTUAL)
                ? "préstamo a domicilio permitido"
                : "edición del año: solo consulta en sala";
        return "[REVISTA] «%s» — edición N° %d (%s, %d) · %s · %s"
                .formatted(getTitulo(), numeroEdicion, periodicidad, getAnio(),
                        getCategoria().getDescripcion(), politica);
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }
}

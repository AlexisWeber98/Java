/*
 * DVD.java — Proyecto Integrador N°1: Biblioteca
 * Material audiovisual: aporta director y duración.
 *
 * Su regla de disponibilidad hoy coincide con la del libro, y eso es parte de
 * la lección: si mañana cambia la política (por ejemplo "los estrenos no
 * salen"), se edita ESTA clase y ni el gestor ni nadie más se entera.
 *
 * Compilación y ejecución: javac *.java && java Main
 */
public class DVD extends ItemBiblioteca {

    private final String director;
    private final int duracionMinutos;

    public DVD(String codigo, String titulo, int anio, CategoriaItem categoria,
               String director, int duracionMinutos) {
        super(codigo, titulo, anio, categoria);
        this.director = director;
        this.duracionMinutos = duracionMinutos;
    }

    @Override
    public boolean estaDisponibleParaPrestamo() {
        return getEstado() == EstadoItem.DISPONIBLE;
    }

    @Override
    public String descripcionDetallada() {
        return "[DVD] «%s» — dirigido por %s (%d) · %d min · %s · préstamo máx.: %d días"
                .formatted(getTitulo(), director, getAnio(), duracionMinutos,
                        getCategoria().getDescripcion(), getCategoria().getDiasMaximoPrestamo());
    }

    public String getDirector() {
        return director;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }
}

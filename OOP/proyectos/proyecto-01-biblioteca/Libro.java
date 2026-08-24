/*
 * Libro.java — Proyecto Integrador N°1: Biblioteca
 * Subclase concreta: aporta autor y cantidad de páginas, y define su propia
 * regla de disponibilidad y su ficha detallada (polimorfismo en acción).
 *
 * Compilación y ejecución: javac *.java && java Main
 */
public class Libro extends ItemBiblioteca {

    private final String autor;
    private final int cantidadPaginas;

    public Libro(String codigo, String titulo, int anio, CategoriaItem categoria,
                 String autor, int cantidadPaginas) {
        super(codigo, titulo, anio, categoria);
        this.autor = autor;
        this.cantidadPaginas = cantidadPaginas;
    }

    /** Un libro se presta siempre que esté físicamente en el estante. */
    @Override
    public boolean estaDisponibleParaPrestamo() {
        return getEstado() == EstadoItem.DISPONIBLE;
    }

    @Override
    public String descripcionDetallada() {
        return "[LIBRO] «%s» — %s (%d) · %d págs. · %s · préstamo máx.: %d días"
                .formatted(getTitulo(), autor, getAnio(), cantidadPaginas,
                        getCategoria().getDescripcion(), getCategoria().getDiasMaximoPrestamo());
    }

    public String getAutor() {
        return autor;
    }
}

/*
 * ItemBiblioteca.java — Proyecto Integrador N°1: Biblioteca
 * Clase abstracta madre de todo el catálogo (Libro, Revista, DVD).
 *
 * Encapsula lo compartido (código, título, año, estado, categoría) y define
 * el contrato polimórfico: cada subclase decide CÓMO responder a
 * estaDisponibleParaPrestamo() y a descripcionDetallada().
 *
 * Además implementa Prestable con la mecánica concreta común de préstamo y
 * devolución: el esqueleto vive acá una sola vez (idea de método plantilla).
 *
 * Compilación y ejecución: javac *.java && java Main
 */
public abstract class ItemBiblioteca implements Prestable {

    private final String codigo;
    private final String titulo;
    private final int anio;
    private EstadoItem estado;
    private final CategoriaItem categoria;

    protected ItemBiblioteca(String codigo, String titulo, int anio, CategoriaItem categoria) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del ítem es obligatorio");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título del ítem es obligatorio");
        }
        this.codigo = codigo;
        this.titulo = titulo;
        this.anio = anio;
        this.categoria = categoria;
        this.estado = EstadoItem.DISPONIBLE; // todo ítem nace disponible
    }

    // ---------- contrato Prestable: mecánica compartida por todos ----------

    @Override
    public void prestar(Socio socio) {
        this.estado = EstadoItem.PRESTADO;
        socio.registrarPrestamo(codigo);
    }

    @Override
    public void devolver() {
        this.estado = EstadoItem.DISPONIBLE;
    }

    // ---------- operaciones comunes adicionales ----------

    /** Manda el ítem al taller (estado EN_REPARACION). */
    public void enviarAReparacion() {
        this.estado = EstadoItem.EN_REPARACION;
    }

    /** Lo saca del taller; devuelve false si ni siquiera estaba en reparación. */
    public boolean finalizarReparacion() {
        if (estado != EstadoItem.EN_REPARACION) {
            return false;
        }
        this.estado = EstadoItem.DISPONIBLE;
        return true;
    }

    // ---------- contrato polimórfico: cada subclase define SU criterio ----------

    /** ¿Corresponde entregar este ítem hoy, según sus propias reglas? */
    public abstract boolean estaDisponibleParaPrestamo();

    /** Ficha técnica extendida, distinta para cada tipo concreto. */
    public abstract String descripcionDetallada();

    // ---------- getters ----------

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnio() {
        return anio;
    }

    public EstadoItem getEstado() {
        return estado;
    }

    public CategoriaItem getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "[%s] %s (%d) — %s".formatted(codigo, titulo, anio, estado);
    }
}

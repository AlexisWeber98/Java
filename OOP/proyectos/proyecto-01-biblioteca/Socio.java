/*
 * Socio.java — Proyecto Integrador N°1: Biblioteca
 * Persona asociada a la biblioteca: lleva la cuenta de los ítems que tiene
 * en su poder ahora mismo y conoce su propio cupo (límite = 3).
 *
 * Compilación y ejecución: javac *.java && java Main
 */
import java.util.ArrayList;
import java.util.List;

public class Socio {

    /** Cupo máximo de préstamos simultáneos según el reglamento. */
    public static final int LIMITE_PRESTAMOS_SIMULTANEOS = 3;

    private final int id;
    private final String nombre;
    /** Códigos de los ítems que este socio tiene en su poder en este momento. */
    private final List<String> codigosPrestados = new ArrayList<>();

    public Socio(int id, String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del socio es obligatorio");
        }
        this.id = id;
        this.nombre = nombre;
    }

    /** ¿Tiene lugar para un préstamo más? */
    public boolean puedeTomarPrestamo() {
        return codigosPrestados.size() < LIMITE_PRESTAMOS_SIMULTANEOS;
    }

    /** Suma un código a sus posesiones (lo invoca el ítem al momento de prestarse). */
    public void registrarPrestamo(String codigoItem) {
        codigosPrestados.add(codigoItem);
    }

    /** Quita un código cuando el ítem vuelve al estante. */
    public void quitarPrestamo(String codigoItem) {
        codigosPrestados.remove(codigoItem);
    }

    public int getCantidadPrestamosActivos() {
        return codigosPrestados.size();
    }

    /** Copia defensiva: afuera nadie debe poder mutar la lista interna. */
    public List<String> getCodigosPrestados() {
        return new ArrayList<>(codigosPrestados);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "#%d %-18s préstamos activos: %d/%d %s"
                .formatted(id, nombre, codigosPrestados.size(),
                        LIMITE_PRESTAMOS_SIMULTANEOS, codigosPrestados);
    }
}

import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================================
 * Módulo 22 · Arquitectura en capas — Ejercicio 3
 * Repositorio con interfaz: cambiá el almacén tocando una línea
 * ============================================================================
 *
 * ENUNCIADO
 * El main programa contra la INTERFAZ RepositorioLibros, no contra una clase
 * concreta. Ya existe la implementación en memoria; te falta completar
 * RepositorioLibrosEnArchivoSimulado, que "persiste" los libros dentro de un
 * String (un snapshot con formato "titulo~autor;titulo~autor").
 *
 * REQUISITOS
 *   1. Implementar exportarSnapshot() e importarSnapshot(String) en
 *      RepositorioLibrosEnArchivoSimulado.
 *   2. Comprobar el ciclo completo: guardar → exportar → instancia nueva →
 *      importar → los libros siguen ahí.
 *   3. Cambiar de implementación en main moviendo UNA sola línea (comentar
 *      una, descomentar la otra) y verificar que el resto del código ni se
 *      entera.
 *
 * PISTAS
 *   - split(";") y split("~") hacen casi todo el trabajo del snapshot.
 *   - Fijate que el main declara la variable con el TIPO DE LA INTERFAZ:
 *     ese es todo el secreto del polimorfismo acá. El contrato no cambia;
 *     cambia quién lo cumple.
 *   - El String es un archivo de mentira; el día de mañana el mismo contrato
 *     se implementa contra un archivo real o una base de datos, y el main
 *     sigue idéntico.
 * ============================================================================
 */
public class Ejercicio3RepositorioConInterfaz {

    public static void main(String[] args) {
        // === Línea intercambiable: acá se elige el almacén. Solo se toca ESTA línea.
        RepositorioLibros repositorio = new RepositorioLibrosEnMemoria();
        // RepositorioLibros repositorio = new RepositorioLibrosEnArchivoSimulado();

        repositorio.guardar(new Libro("El Aleph", "Borges"));
        repositorio.guardar(new Libro("Rayuela", "Cortázar"));

        System.out.println("Busco 'Rayuela': " + repositorio.buscarPorTitulo("Rayuela"));
        System.out.println("Busco 'Ficciones': " + repositorio.buscarPorTitulo("Ficciones"));
        System.out.println("Catálogo completo:");
        repositorio.listarTodos().forEach(l -> System.out.println("  - " + l));

        // === Ciclo de persistencia del archivo simulado =====================
        RepositorioLibrosEnArchivoSimulado origen = new RepositorioLibrosEnArchivoSimulado();
        origen.guardar(new Libro("Ficciones", "Borges"));
        String snapshot = origen.exportarSnapshot();            // "grabamos el archivo"
        System.out.println("Snapshot generado: \"" + snapshot + "\"");

        RepositorioLibrosEnArchivoSimulado copia = new RepositorioLibrosEnArchivoSimulado();
        copia.importarSnapshot(snapshot);                       // "lo leemos en otra sesión"
        System.out.println("Libros recuperados del snapshot: " + copia.listarTodos());
    }
}

/** Modelo simple del dominio: un record, datos inmutables y nada más. */
record Libro(String titulo, String autor) { }

/** PUERTO de persistencia: el contrato que toda implementación debe cumplir. */
interface RepositorioLibros {
    void guardar(Libro libro);

    /** Devuelve el libro con ese título (ignorando mayúsculas) o null si no existe. */
    Libro buscarPorTitulo(String titulo);

    List<Libro> listarTodos();
}

/** Implementación en memoria: vive mientras corre el programa y nada más. */
class RepositorioLibrosEnMemoria implements RepositorioLibros {
    private final List<Libro> libros = new ArrayList<>();

    @Override
    public void guardar(Libro libro) {
        libros.add(libro);
    }

    @Override
    public Libro buscarPorTitulo(String titulo) {
        for (Libro libro : libros) {
            if (libro.titulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

    @Override
    public List<Libro> listarTodos() {
        return List.copyOf(libros);
    }
}

/**
 * Implementación que simula un archivo: la persistencia es un String.
 * Asumimos títulos sin ';' ni '~' para no complicar la serialización.
 */
class RepositorioLibrosEnArchivoSimulado implements RepositorioLibros {
    private final List<Libro> libros = new ArrayList<>();

    @Override
    public void guardar(Libro libro) {
        libros.add(libro);
    }

    @Override
    public Libro buscarPorTitulo(String titulo) {
        for (Libro libro : libros) {
            if (libro.titulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

    @Override
    public List<Libro> listarTodos() {
        return List.copyOf(libros);
    }

    // TODO: serializar la lista al formato "titulo~autor;titulo~autor".
    String exportarSnapshot() {
        throw new UnsupportedOperationException("TODO: implementar exportarSnapshot");
    }

    // TODO: vaciar la lista y reconstruir los libros a partir del snapshot.
    void importarSnapshot(String snapshot) {
        throw new UnsupportedOperationException("TODO: implementar importarSnapshot");
    }
}

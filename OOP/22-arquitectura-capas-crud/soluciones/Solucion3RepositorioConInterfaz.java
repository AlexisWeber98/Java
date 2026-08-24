import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================================
 * SOLUCIÓN · Módulo 22 · Ejercicio 3 — Repositorio con interfaz
 * ============================================================================
 * Decisiones clave:
 *   - El main declara la variable como RepositorioLibros (la INTERFAZ).
 *     Cambiar de almacén es comentar una línea y descomentar otra: cero
 *     cambios en la lógica de demostración. Eso es programar contra el
 *     contrato, no contra la implementación.
 *   - El snapshot usa "titulo~autor" separado por ";": suficiente para
 *     simular un archivo sin meterse con I/O real.
 *   - exportarSnapshot devuelve "" si no hay libros; importarSnapshot acepta
 *     ese vacío sin romper.
 * ============================================================================
 */
public class Solucion3RepositorioConInterfaz {

    public static void main(String[] args) {
        // Demo A — polimorfismo: elegí el almacén acá y SOLO acá.
        RepositorioLibros repositorio = new RepositorioLibrosEnArchivo(); // ← probá también EnMemoria()
        // RepositorioLibros repositorio = new RepositorioLibrosEnMemoria();

        repositorio.guardar(new Libro("El Aleph", "Borges"));
        repositorio.guardar(new Libro("Rayuela", "Cortázar"));

        System.out.println("Busco 'Rayuela': " + repositorio.buscarPorTitulo("Rayuela"));
        System.out.println("Busco 'Ficciones': " + repositorio.buscarPorTitulo("Ficciones"));
        System.out.println("Catálogo completo:");
        repositorio.listarTodos().forEach(l -> System.out.println("  - " + l));

        // Demo B — ciclo de persistencia completo del archivo simulado.
        System.out.println("--- ciclo de persistencia ---");
        RepositorioLibrosEnArchivo origen = new RepositorioLibrosEnArchivo();
        origen.guardar(new Libro("Ficciones", "Borges"));
        String snapshot = origen.exportarSnapshot();
        System.out.println("Snapshot generado: \"" + snapshot + "\"");

        RepositorioLibrosEnArchivo copia = new RepositorioLibrosEnArchivo();
        copia.importarSnapshot(snapshot);
        System.out.println("Libros recuperados: " + copia.listarTodos());
    }

    record Libro(String titulo, String autor) { }

    /** Contrato de persistencia. Ni el main ni el servicio conocen implementaciones. */
    interface RepositorioLibros {
        void guardar(Libro libro);

        Libro buscarPorTitulo(String titulo);

        List<Libro> listarTodos();
    }

    static class RepositorioLibrosEnMemoria implements RepositorioLibros {
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

    static class RepositorioLibrosEnArchivo implements RepositorioLibros {
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

        /** Serializa la lista a "titulo~autor;titulo~autor" (vacío si no hay libros). */
        String exportarSnapshot() {
            StringBuilder sb = new StringBuilder();
            for (Libro libro : libros) {
                if (!sb.isEmpty()) {
                    sb.append(';');
                }
                sb.append(libro.titulo()).append('~').append(libro.autor());
            }
            return sb.toString();
        }

        /** Reconstruye la lista desde el snapshot, descartando lo anterior. */
        void importarSnapshot(String snapshot) {
            libros.clear();
            if (snapshot == null || snapshot.isBlank()) {
                return;
            }
            for (String parte : snapshot.split(";")) {
                String[] campos = parte.split("~", 2);
                libros.add(new Libro(campos[0], campos[1]));
            }
        }
    }
}

/*
 * ============================================================================
 * Ejercicio 5 (SOLUCIÓN) — DESAFÍO integrador: Biblioteca
 * ============================================================================
 * Gemelo de ejercicios/Ejercicio5DesafioBibliotecaRelaciones.java (clase sin
 * public para permitir la compilación conjunta de ejercicios/ y soluciones/).
 *
 * MAPA DE RELACIONES RESPONDIDO:
 *   Biblioteca -> Libro     : AGREGACIÓN. El libro llega de afuera y se lo
 *                             cataloga; existe independientemente de cualquier
 *                             biblioteca.
 *   Biblioteca -> Ejemplar  : COMPOSICIÓN. La copia física nace DENTRO de
 *                             registrarEjemplar(); es parte constitutiva de la
 *                             colección administrada por la biblioteca.
 *   Biblioteca -> Socio     : AGREGACIÓN. El socio se registra llegando de
 *                             afuera; sigue siendo persona sin biblioteca.
 *   Ejemplar   -> Libro     : ASOCIACIÓN. Referencia fija a su obra; la obra
 *                             conceptual sobrevive al descarte de la copia.
 *   Prestamo   -> Socio     : ASOCIACIÓN. Vínculo entre dos objetos que
 *   Prestamo   -> Ejemplar  : existen antes y después del préstamo.
 *   registrarPrestamo(Socio, Ejemplar): DEPENDENCIA. El método usa ambos
 *                             parámetros de forma transitoria para fabricar
 *                             el Prestamo; no guarda referencias directas a
 *                             socio ni ejemplar (lo que persiste es el
 *                             préstamo creado, que sí asocia por su cuenta).
 */
import java.util.ArrayList;
import java.util.List;

class Solucion5DesafioBibliotecaRelaciones {

    enum EstadoEjemplar { DISPONIBLE, PRESTADO }

    static class Libro {
        private final String isbn;
        private final String titulo;
        private final String autor;

        Libro(String isbn, String titulo, String autor) {
            this.isbn = isbn;
            this.titulo = titulo;
            this.autor = autor;
        }

        String getTitulo() {
            return titulo;
        }
    }

    static class Ejemplar {
        private final String codigo;

        // Asociación: cada copia conoce a su obra (que vive por su cuenta).
        private final Libro libro;
        private EstadoEjemplar estado = EstadoEjemplar.DISPONIBLE;

        Ejemplar(String codigo, Libro libro) {
            this.codigo = codigo;
            this.libro = libro;
        }

        boolean estaDisponible() {
            return estado == EstadoEjemplar.DISPONIBLE;
        }

        void marcarPrestado() {
            estado = EstadoEjemplar.PRESTADO;
        }

        void marcarDevuelto() {
            estado = EstadoEjemplar.DISPONIBLE;
        }

        String descripcion() {
            return "[" + codigo + "] " + libro.getTitulo() + " - " + estado;
        }

        String getCodigo() {
            return codigo;
        }
    }

    static class Socio {
        private final String numero;
        private final String nombre;

        Socio(String numero, String nombre) {
            this.numero = numero;
            this.nombre = nombre;
        }

        String descripcion() {
            return numero + " (" + nombre + ")";
        }
    }

    static class Prestamo {
        // Asociación: el préstamo VINCULA dos objetos independientes.
        private final Socio socio;
        private final Ejemplar ejemplar;
        private boolean devuelto = false;

        Prestamo(Socio socio, Ejemplar ejemplar) {
            this.socio = socio;
            this.ejemplar = ejemplar;
        }

        void devolver() {
            devuelto = true;
            ejemplar.marcarDevuelto();
        }

        boolean isDevuelto() {
            return devuelto;
        }

        Ejemplar getEjemplar() {
            return ejemplar;
        }

        String descripcion(int n) {
            return "#" + n + " " + ejemplar.getCodigo() + " -> " + socio.descripcion()
                    + (devuelto ? " [DEVUELTO]" : " [ACTIVO]");
        }
    }

    static class Biblioteca {
        private final List<Libro> catalogo = new ArrayList<>();
        private final List<Ejemplar> ejemplares = new ArrayList<>();
        private final List<Socio> socios = new ArrayList<>();
        private final List<Prestamo> prestamos = new ArrayList<>();

        void registrarLibro(Libro libro) {
            // Agregación: se recibe una referencia externa, nunca se crea acá.
            catalogo.add(libro);
        }

        Ejemplar registrarEjemplar(Libro libro) {
            // Composición: la copia física NACE dentro del método.
            Ejemplar ejemplar = new Ejemplar("EJ-" + (ejemplares.size() + 1), libro);
            ejemplares.add(ejemplar);
            return ejemplar;
        }

        void registrarSocio(Socio socio) {
            // Agregación: el socio existía antes de anotarse.
            socios.add(socio);
        }

        Prestamo registrarPrestamo(Socio socio, Ejemplar ejemplar) {
            // Dependencia: uso transitorio de los parámetros para fabricar
            // el préstamo (que SÍ asocia por su cuenta). El método no guarda
            // campos con socio ni con ejemplar.
            if (!ejemplar.estaDisponible()) {
                throw new IllegalStateException("Ejemplar no disponible");
            }
            Prestamo prestamo = new Prestamo(socio, ejemplar);
            ejemplar.marcarPrestado();
            prestamos.add(prestamo);
            return prestamo;
        }

        void devolver(Ejemplar ejemplar) {
            for (Prestamo prestamo : prestamos) {
                if (!prestamo.isDevuelto() && prestamo.getEjemplar() == ejemplar) {
                    prestamo.devolver();
                    return;
                }
            }
        }

        void informarEstado() {
            System.out.println("== Estado de la biblioteca ==");
            System.out.println("Catalogo (" + catalogo.size() + "):");
            for (Libro libro : catalogo) {
                System.out.println("  * " + libro.getTitulo());
            }
            System.out.println("Ejemplares:");
            for (Ejemplar ejemplar : ejemplares) {
                System.out.println("  " + ejemplar.descripcion());
            }
            System.out.println("Socios registrados: " + socios.size());
            System.out.println("Prestamos (" + prestamos.size() + "):");
            for (int i = 0; i < prestamos.size(); i++) {
                System.out.println("  " + prestamos.get(i).descripcion(i + 1));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // Libro y socio nacen AFUERA (agregación); el ejemplar nace ADENTRO.
        Libro libro = new Libro("978-3-16-148410-0", "Cien anos de soledad", "Gabriel Garcia Marquez");
        biblioteca.registrarLibro(libro);

        Ejemplar ejemplar = biblioteca.registrarEjemplar(libro);

        Socio socio = new Socio("S-001", "Martina Sanchez");
        biblioteca.registrarSocio(socio);

        biblioteca.informarEstado();

        biblioteca.registrarPrestamo(socio, ejemplar); // dependencia del método
        System.out.println("-- Tras el prestamo --");
        biblioteca.informarEstado();

        biblioteca.devolver(ejemplar);
        System.out.println("-- Tras la devolucion --");
        biblioteca.informarEstado();
    }
}

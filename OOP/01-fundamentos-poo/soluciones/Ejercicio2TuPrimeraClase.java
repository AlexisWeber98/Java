/*
 * ============================================
 *  Solución 2: Tu primera clase
 * ============================================
 * Clase Libro completa con sus dos campos y su método presentar().
 */
class Solucion2TuPrimeraClase {

    public static void main(String[] args) {
        Libro miLibro = new Libro();
        miLibro.titulo = "El Aleph";
        miLibro.autor = "Jorge Luis Borges";

        Libro otroLibro = new Libro();
        otroLibro.titulo = "Rayuela";
        otroLibro.autor = "Julio Cortázar";

        // Dos objetos del mismo molde, cada uno con su propia copia de los campos:
        miLibro.presentar();
        otroLibro.presentar();
    }

    /*
     * Nota: acá Libro vive DENTRO de la clase principal (clase estática interna)
     * para que este archivo pueda compilarse junto con el ejercicio sin choque
     * de nombres. En tu resolución declarala afuera, tal cual el enunciado.
     */
    static class Libro {
        String titulo;
        String autor;

        void presentar() {
            System.out.println("\"" + titulo + "\" de " + autor);
        }
    }
}

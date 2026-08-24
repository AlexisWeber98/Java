/*
 * EJEMPLO 2: Mi primera clase, de cero.
 *
 * Problema: modelar libros de una biblioteca personal.
 *
 * La idea clave: una clase es un MOLDE. Define qué datos tienen todos
 * los objetos creados a partir de ella (campos) y qué saben hacer
 * (métodos). Cada objeto creado con `new` es una copia INDEPENDIENTE:
 * cambiar un libro no afecta al otro.
 *
 * Corrélo con: java Ejemplo2MiPrimeraClase.java
 */
public class Ejemplo2MiPrimeraClase {

    public static void main(String[] args) {

        // Cada `new` construye un objeto nuevo e independiente del molde Libro.
        Libro libroDeCocina = new Libro("El arte de la pasta", "Nonna Rosa", 120);
        Libro novela = new Libro("Niebla en el puerto", "J. Ferreyra", 480);

        System.out.println("--- Estado inicial ---");
        libroDeCocina.presentar();
        novela.presentar();

        /*
         * Los objetos son independientes: le cambio el título a UNO solo
         * (accediendo al campo directamente; en el módulo 05 vemos por qué
         * conviene encapsular esto detrás de métodos).
         */
        System.out.println("--- Cambiamos el titulo de la novela (correccion) ---");
        novela.titulo = "Niebla sobre el puerto";
        novela.presentar();

        System.out.println("--- Preguntamos a cada libro si es largo ---");
        System.out.println(libroDeCocina.titulo + " es largo? -> " + libroDeCocina.esLargo());
        System.out.println(novela.titulo + " es largo? -> " + novela.esLargo());

        /*
         * MORALEJA:
         * - El molde se escribe UNA vez; los objetos salen a pedido.
         * - Cada objeto guarda SU estado: mismo campo `paginas`,
         *   valores distintos por libro.
         * - Llamar a un método es pedirle algo AL objeto: novela.esLargo()
         */
    }
}

/*
 * EL MOLDE: clase Libro.
 * No declara "package" a propósito: corre standalone con `java Ejemplo2MiPrimeraClase.java`.
 */
class Libro {

    // ---- ESTADO: lo que todo libro sabe sobre sí mismo ----
    String titulo;
    String autor;
    int paginas;

    // Nota didáctica: esto es un constructor; se estudia a fondo en el módulo 04.
    Libro(String tituloInicial, String autorInicial, int paginasIniciales) {
        this.titulo = tituloInicial;
        this.autor = autorInicial;
        this.paginas = paginasIniciales;
    }

    // ---- COMPORTAMIENTO: lo que todo libro sabe hacer ----

    void presentar() {
        System.out.println("\"" + titulo + "\", de " + autor + " (" + paginas + " paginas)");
    }

    boolean esLargo() {
        return paginas > 300;
    }
}

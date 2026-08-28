/*
 * ============================================
 *  Ejercicio 2: Tu primera clase
 * ============================================
 *
 * ENUNCIADO:
 *   Una clase es el MOLDE; un objeto es el ejemplar concreto que nace cuando
 *   lo instanciás con "new". En este ejercicio escribís tu primer molde: la
 *   clase Libro, con sus campos y su primer método.
 *
 * REQUISITOS:
 *   1. La clase Libro ya tiene declarados sus 2 campos: titulo y autor (String).
 *   2. Implementá presentar(): debe imprimir por pantalla el titulo y el autor.
 *   3. El main ya crea un libro, le carga datos y llama a presentar().
 *      Cuando completes el método, esa llamada tiene que mostrar algo.
 *   4. Creá un SEGUNDO libro con otros datos y presentalo también.
 *
 * PISTAS:
 *   - Dentro de la clase usás los campos directamente: titulo, autor.
 *   - Para concatenar texto con datos: "Título: " + titulo
 *   - Fijate en el main: miLibro.titulo = "El Aleph"; así se carga un campo.
 */
class Libro {
  String titulo;
  String autor;

  // TODO 1: implementá presentar(): que imprima titulo y autor.
  void presentar() {

    System.out.println(" \n\n---------------- Presentación del libro -------------");
    System.out.println("Título: " + titulo);
    System.out.println("Autor: " + autor);
  }
}

public class Ejercicio2TuPrimeraClase {

  public static void main(String[] args) {
    // Este bloque ya está resuelto para que veas el flujo completo:
    Libro miLibro = new Libro(); // nace el objeto (el ejemplar del molde)
    miLibro.titulo = "El Aleph";
    miLibro.autor = "Jorge Luis Borges";
    miLibro.presentar();

    // TODO 2: creá un segundo libro con otros datos y presentalo.

    Libro libro2 = new Libro();

    libro2.titulo = "Mar hermosa";
    libro2.autor = "Claudia Piñeiro";
    libro2.presentar();

  }
}

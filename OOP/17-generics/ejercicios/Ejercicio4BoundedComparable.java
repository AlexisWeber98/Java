/*
 * ============================================================================
 *  Ejercicio 4 — Genéricos con límite: <T extends Comparable<T>>
 * ============================================================================
 *
 *  ENUNCIADO
 *  --------
 *  Un genérico "pelado" T no promete nada: no podés llamar compareTo porque
 *  no todo objeto se sabe comparar. La solución es un BOUND (límite).
 *
 *  Implementá:
 *
 *      static <T extends Comparable<T>> T mayorDe(T a, T b, T c)
 *
 *  y probalo con:
 *      1. Integer: mayorDe(7, 42, 13)
 *      2. String: mayorDe("alfajor", "bonete", "churro")
 *      3. Tu propia clase Producto(nombre, precio) que implemente
 *         Comparable<Producto> ordenando POR PRECIO, para obtener el
 *         producto más caro.
 *
 *  REQUISITOS
 *  ----------
 *      - Sin el extends, javac te niega compareTo: comprobalo y anotalo.
 *      - Producto debe compararse por precio (pista: Double.compare).
 *      - Sobrescribí toString() en Producto para una salida linda.
 *
 *  PISTAS
 *  ------
 *      - El bound "recursivo" <T extends Comparable<T>> se lee: "T sabe
 *        compararse consigo mismo". Es EL idioma estándar del ecosistema
 *        (mirá la firma de Collections.max cuando tengas ganas).
 *      - Empezá asumindo que el mayor es a; después desafiá con b y c.
 *      - String compara en orden lexicográfico: no esperes magia con ñ ni tildes.
 * ============================================================================
 */
public class Ejercicio4BoundedComparable {

    // TODO 1: implementá mayorDe con su bound.


    // TODO 2: creá la clase estática Producto(String nombre, double precio)
    //         implements Comparable<Producto>, comparando por precio.


    public static void main(String[] args) {
        // TODO 3: probá mayorDe con Integer y String.


        // TODO 4: armá tres productos y encontrá el más caro SIN escribir
        //         ningún if de precio en el main: el orden vive en Producto.


        System.out.println("Completá los TODOs y volvé a ejecutar.");
    }
}

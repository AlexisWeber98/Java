/*
 * ============================================================================
 *  Ejercicio 5 — Desafío integrador: Libro y Socio · SOLUCIÓN
 *  Módulo 02 · Clases y objetos
 * ============================================================================
 *
 *  QUÉ MIRAR DE ESTA SOLUCIÓN
 *  - pedir() es un MENSAJE que le llega a UN socio concreto: dentro del
 *    método, this ES ese socio. Por eso this.librosEnMano es "los libros de
 *    quien pide".
 *  - La decisión combina el estado de DOS objetos: el propio socio
 *    (this.librosEnMano) y el otro objeto (libro.prestado). Eso es
 *    colaboración entre objetos.
 *  - En los constructores, this.titulo = titulo resuelve el choque de
 *    nombres: sin this estarías asignándole el parámetro a sí mismo.
 *  - El rechazo devuelve false SIN mutar nada: si algo falla, el mundo no
 *    queda a medio cambiar.
 *
 *  SALIDA ESPERADA
 *    Ana se llevó "El Principito".
 *    Pedido rechazado: Ana no pudo llevarse "Fundamentos de objetos".
 *    Bruno se llevó "Fundamentos de objetos".
 *    Pedido rechazado: Bruno no pudo llevarse "El Principito".
 */
// Sin public y con nombre Solucion*: así ejercicios y soluciones compilan juntos.
class Solucion5DesafioLibroYSocio {

    public static void main(String[] args) {
        Libro elPrincipito = new Libro("El Principito");
        Libro fundamentos = new Libro("Fundamentos de objetos");
        Socio ana = new Socio("Ana");
        Socio bruno = new Socio("Bruno");

        // Escena pedida en el enunciado (a, b, c y d)
        informar(ana.pedir(elPrincipito), ana, elPrincipito);     // true
        informar(ana.pedir(fundamentos), ana, fundamentos);       // false: límite
        informar(bruno.pedir(fundamentos), bruno, fundamentos);   // true
        informar(bruno.pedir(elPrincipito), bruno, elPrincipito); // false: prestado
    }

    // Helper del main: traduce el boolean devuelto por pedir() a un mensaje
    static void informar(boolean exito, Socio socio, Libro libro) {
        if (exito) {
            System.out.println(socio.nombre + " se llevó \"" + libro.titulo + "\".");
        } else {
            System.out.println("Pedido rechazado: " + socio.nombre
                    + " no pudo llevarse \"" + libro.titulo + "\".");
        }
    }

    static class Libro {
        String titulo;
        boolean prestado;   // false por defecto: recién editado, en la estantería

        Libro(String titulo) {
            this.titulo = titulo;   // this obligatorio: el parámetro tapa al campo
        }
    }

    static class Socio {
        static final int LIMITE_LIBROS = 1;

        String nombre;
        int librosEnMano;

        Socio(String nombre) {
            this.nombre = nombre;
        }

        boolean pedir(Libro libro) {
            if (libro.prestado || librosEnMano >= LIMITE_LIBROS) {
                return false;   // rechazo limpio: nada quedó modificado
            }
            libro.prestado = true;   // colabora con el estado del OTRO objeto...
            this.librosEnMano++;     // ...y con el propio
            return true;
        }
    }
}

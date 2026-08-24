/*
 * ============================================================================
 *  Ejercicio 5 — Desafío integrador: Libro y Socio
 *  Módulo 02 · Clases y objetos
 * ============================================================================
 *
 *  ENUNCIADO
 *  Desafío final del módulo: dos clases que COLABORAN entre sí. Un socio le
 *  pide libros a la biblioteca; los objetos se pasan mensajes y cada decisión
 *  depende del estado de los DOS. Primer contacto formal con this.
 *
 *  REQUISITOS
 *  1. Clase Libro: atributos titulo (String) y prestado (boolean).
 *  2. Clase Socio: constante LIMITE_LIBROS = 1 y atributos nombre (String)
 *     e librosEnMano (int).
 *  3. Método boolean pedir(Libro libro) en Socio:
 *     - Si el libro ya está prestado O el socio llegó a su límite:
 *       devolver false SIN cambiar nada.
 *     - Si no: marcar libro.prestado = true, sumar 1 a this.librosEnMano
 *       y devolver true.
 *  4. En el main reproducí esta escena e imprimí el resultado de cada pedido:
 *     a) Ana pide "El Principito"      -> esperado: true
 *     b) Ana pide "Fundamentos"        -> esperado: false (límite del socio)
 *     c) Bruno pide "Fundamentos"      -> esperado: true
 *     d) Bruno pide "El Principito"    -> esperado: false (libro ocupado)
 *
 *  PISTAS
 *  - Dentro de pedir(), this ES el socio que recibe el mensaje, no otro.
 *  - La decisión usa estado de los dos objetos: libro.prestado y
 *    this.librosEnMano.
 *  - Devolver boolean deja que el main decida cómo informar cada caso.
 */
public class Ejercicio5DesafioLibroYSocio {

    public static void main(String[] args) {
        // TODO 1: crear dos libros ("El Principito" y "Fundamentos")
        //         y dos socios ("Ana" y "Bruno")

        // TODO 2 a 4: los cuatro pedidos de la escena, imprimiendo resultado
    }

    static class Libro {
        // TODO: atributos titulo y prestado
    }

    static class Socio {
        // TODO: constante LIMITE_LIBROS y atributos nombre / librosEnMano

        boolean pedir(Libro libro) {
            return false;   // TODO: reemplazá esta línea por la lógica real
        }
    }
}

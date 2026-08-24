/*
 * ============================================================================
 * Ejercicio 5 — DESAFÍO integrador: Biblioteca con las 4 relaciones
 * ============================================================================
 *
 * ENUNCIADO:
 *   Modelá una biblioteca mínima con Libro, Ejemplar, Socio y Prestamo.
 *   Antes de escribir código, completá el MAPA DE RELACIONES de acá abajo
 *   decidiendo el tipo exacto de cada vínculo. Después implementá un flujo
 *   completo: alta de libro, creación de ejemplar, alta de socio, préstamo,
 *   consulta de estado, devolución y consulta final.
 *
 * MAPA DE RELACIONES (completalo en comentarios ANTES de programar):
 *   Biblioteca -> Libro     : ¿agregación, composición, asociación o
 *                              dependencia? ¿Quién crea a quién?
 *   Biblioteca -> Ejemplar  : ídem. OJO: ¿dónde debería nacer el ejemplar?
 *   Biblioteca -> Socio     : ídem.
 *   Ejemplar   -> Libro     : ídem. ¿La copia puede vivir sin su obra?
 *                              ¿La obra sin esa copia?
 *   Prestamo   -> Socio     : ídem. ¿Qué pasa con cada uno al terminar
 *   Prestamo   -> Ejemplar  :    el préstamo?
 *   registrarPrestamo(Socio, Ejemplar): ¿qué tipo de relación tiene este
 *                              MÉTODO con sus parámetros? ¿Guarda algo de
 *                              ellos directamente?
 *
 * REQUISITOS:
 *   1. Libro: isbn, titulo, autor (obra conceptual del catálogo).
 *   2. Ejemplar: codigo, referencia a su Libro y estado (DISPONIBLE/PRESTADO).
 *      Debe crearse SOLO dentro de la biblioteca.
 *   3. Socio: numero, nombre; se registra llegando de afuera.
 *   4. Prestamo: vincula Socio + Ejemplar, con estado devuelto.
 *   5. Biblioteca: registrarLibro(Libro), registrarEjemplar(Libro) que RETORNA
 *      el ejemplar creado, registrarSocio(Socio), registrarPrestamo(Socio,
 *      Ejemplar), devolver(Ejemplar) e informarEstado().
 *   6. En main: flujo completo con dos informes de estado intermedios.
 *
 * PISTAS:
 *   - Para decidir, preguntate SIEMPRE: ¿quién hace new?, ¿quién sobrevive a
 *     quién?, ¿la relación vive para siempre o solo durante la llamada?
 *   - Un libro puede tener varios ejemplares físicos: la copia es cosa distinta de la obra.
 *   - El método que fabrica el Préstamo no necesita recordar socio ni ejemplar:
 *     quien recuerda es el propio Prestamo (por eso es dependencia + asociación).
 */
import java.util.ArrayList;
import java.util.List;

public class Ejercicio5DesafioBibliotecaRelaciones {

    enum EstadoEjemplar { DISPONIBLE, PRESTADO }

    static class Libro {
        // TODO: campos isbn, titulo, autor + constructor + getTitulo()
    }

    static class Ejemplar {
        // TODO: campos codigo, libro (referencia), estado inicial DISPONIBLE
        // TODO: estaDisponible(), marcarPrestado(), marcarDevuelto(), descripcion()
    }

    static class Socio {
        // TODO: campos numero, nombre + constructor + descripcion()
    }

    static class Prestamo {
        // TODO: campos socio, ejemplar, devuelto (boolean)
        // TODO: constructor, devolver(), isDevuelto(), getEjemplar(), descripcion(int n)
    }

    static class Biblioteca {
        // TODO: colecciones catalogo, ejemplares, socios, prestamos

        void registrarLibro(Libro libro) {
            // TODO: ¿qué tipo de relación implica este método? Implementalo acorde.
        }

        Ejemplar registrarEjemplar(Libro libro) {
            // TODO: crear el ejemplar ACÁ ADENTRO (¿qué relación es?), guardarlo y retornarlo
            return null;
        }

        void registrarSocio(Socio socio) {
            // TODO
        }

        Prestamo registrarPrestamo(Socio socio, Ejemplar ejemplar) {
            // TODO: validar disponibilidad, crear el Prestamo, marcar el ejemplar,
            //       guardarlo y retornarlo. ¿Guardás el socio o el ejemplar como campo?
            return null;
        }

        void devolver(Ejemplar ejemplar) {
            // TODO: buscar el préstamo activo de ese ejemplar y devolverlo
        }

        void informarEstado() {
            // TODO: imprimir catálogo, ejemplares, cantidad de socios y préstamos
        }
    }

    public static void main(String[] args) {
        // Flujo sugerido:
        // TODO 1: crear un libro AFUERA y registrarlo
        // TODO 2: pedirle a la biblioteca un ejemplar de ese libro (¿desde dónde nace?)
        // TODO 3: crear un socio AFUERA y registrarlo
        // TODO 4: registrar el préstamo del ejemplar al socio
        // TODO 5: informarEstado()
        // TODO 6: devolver el ejemplar y volver a informar el estado
    }
}

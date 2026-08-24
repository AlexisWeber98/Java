/*
 * ============================================================================
 * Ejercicio 2 — Agregación: Universidad y su plantel de Profesores
 * ============================================================================
 *
 * ENUNCIADO:
 *   Una Universidad AGREGA profesores: los recibe desde afuera, los organiza
 *   en su plantel y puede dejar de tenerlos; pero los profesores existen por
 *   sí mismos antes, durante y después de la universidad.
 *
 *   Completá el modelo y DEMOSTRÁ en main que, al eliminar la referencia a la
 *   universidad, los profesores siguen perfectamente utilizables.
 *
 * REQUISITOS:
 *   1. Profesor: nombre, materia y presentarse().
 *   2. Universidad: nombre + colección de profesores; contratar(Profesor)
 *      agrega la referencia recibida (jamás instancia profesores adentro).
 *   3. Universidad.listarPlantel(): imprime el nombre de la universidad y su
 *      plantel completo.
 *   4. En main: crear dos profesores AFUERA, contratarlos en una universidad,
 *      listar el plantel, eliminar la referencia a la universidad y volver a
 *      usar a los profesores.
 *
 * PISTAS:
 *   - Agregación = relación todo–parte donde la parte es AUTÓNOMA.
 *   - Diferencia con la asociación simple (Ejercicio 1): acá el "todo"
 *     administra una COLECCIÓN de partes, no un único objeto.
 *   - Diferencia con la composición (Ejercicio 3): las partes no nacen
 *     adentro del todo ni mueren junto con él.
 */
import java.util.ArrayList;
import java.util.List;

public class Ejercicio2AgregacionUniversidad {

    static class Profesor {
        private final String nombre;
        private final String materia;

        Profesor(String nombre, String materia) {
            this.nombre = nombre;
            this.materia = materia;
        }

        String getNombre() {
            return nombre;
        }

        String getMateria() {
            return materia;
        }

        void presentarse() {
            // TODO: imprimir "Soy <nombre> y dicto <materia>"
        }
    }

    static class Universidad {
        private final String nombre;

        // Agregación: contenedor de referencias recibidas desde afuera.
        private final List<Profesor> profesores = new ArrayList<>();

        Universidad(String nombre) {
            this.nombre = nombre;
        }

        void contratar(Profesor profesor) {
            // TODO: agregar a la lista SIN crear profesores acá adentro
        }

        void listarPlantel() {
            // TODO: imprimir "Plantel de <nombre>: (<cantidad> profesores)"
            // TODO: y cada profesor numerado con su nombre y materia
        }
    }

    public static void main(String[] args) {
        // Los profesores nacen AFUERA de cualquier universidad.
        Profesor ana = new Profesor("Ana Torres", "Algoritmos");
        Profesor luis = new Profesor("Luis Gómez", "Base de Datos");

        Universidad universidad = new Universidad("Universidad del Plata");
        universidad.contratar(ana);
        universidad.contratar(luis);
        universidad.listarPlantel();

        // TODO: eliminá la referencia a la universidad (universidad = null)...
        // TODO: ...y demostrá con presentarse() que los profesores siguen vivos.
    }
}

/*
 * ============================================================================
 * Ejercicio 2 (SOLUCIÓN) — Agregación: Universidad y su plantel de Profesores
 * ============================================================================
 * Gemelo de ejercicios/Ejercicio2AgregacionUniversidad.java (clase sin public
 * para permitir la compilación conjunta de ejercicios/ y soluciones/).
 *
 * CONCEPTO CLAVE:
 *   Agregación = todo–parte con parte AUTÓNOMA. La universidad administra una
 *   colección de referencias que le llegan ya construidas; si el "todo" deja
 *   de existir, las "partes" sobreviven sin ningún problema.
 */
import java.util.ArrayList;
import java.util.List;

class Solucion2AgregacionUniversidad {

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
            System.out.println("Soy " + nombre + " y dicto " + materia);
        }
    }

    static class Universidad {
        private final String nombre;

        // Agregación: guarda referencias externas; nunca ejecuta new Profesor(...).
        private final List<Profesor> profesores = new ArrayList<>();

        Universidad(String nombre) {
            this.nombre = nombre;
        }

        void contratar(Profesor profesor) {
            // Solo se agrega la referencia recibida: el profesor ya existía.
            profesores.add(profesor);
        }

        void listarPlantel() {
            System.out.println("Plantel de " + nombre + ": (" + profesores.size() + " profesores)");
            for (int i = 0; i < profesores.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + profesores.get(i).getNombre()
                        + " - " + profesores.get(i).getMateria());
            }
        }
    }

    public static void main(String[] args) {
        // 1) Las partes nacen independientes del todo.
        Profesor ana = new Profesor("Ana Torres", "Algoritmos");
        Profesor luis = new Profesor("Luis Gómez", "Base de Datos");

        // 2) El todo solo AGREGA referencias que le vienen de afuera.
        Universidad universidad = new Universidad("Universidad del Plata");
        universidad.contratar(ana);
        universidad.contratar(luis);
        universidad.listarPlantel();

        // 3) Cerramos la universidad: desaparece el contenedor...
        universidad = null;

        // 4) ...pero las partes siguen vivas y plenamente funcionales.
        //    Esto DEMUESTRA la agregación: a diferencia de la composición,
        //    la vida de los profesores no depende de la de la universidad.
        ana.presentarse();
        luis.presentarse();
    }
}

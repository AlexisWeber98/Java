/*
 * ============================================================================
 *  Ejercicio 4 — Defensa contra null
 *  Módulo 02 · Clases y objetos
 * ============================================================================
 *
 *  ENUNCIADO
 *  NullPointerException: el error más famoso de Java. Ocurre cuando le pedís
 *  algo a una referencia que no apunta a ningún objeto. Hoy aprendés a
 *  defender tu código ANTES de que explote.
 *
 *  REQUISITOS
 *  1. Completar recibirVisita(Mascota paciente): si paciente es null,
 *     imprimir un mensaje amable tipo "Hoy no vino nadie a la consulta" y
 *     terminar SIN tocar al objeto.
 *  2. Si NO es null: imprimir "Atendiendo a <nombre>" y hacerlo presentarse.
 *  3. En el main: atender a una mascota real y después pasarle una variable
 *     declarada como Mascota pero con valor null.
 *  4. Probar mentalmente (o una sola vez, para CONOCER al enemigo): ¿qué
 *    pasa si sacás el chequeo y corrés?
 *
 *  PISTAS
 *  - Chequeo clásico: if (paciente == null) { ... return; }
 *  - null significa "esta referencia no apunta a ningún objeto".
 *  - Llamar paciente.metodo() con paciente == null lanza la excepción.
 */
public class Ejercicio4DefensaContraNull {

    static void recibirVisita(Mascota paciente) {
        // TODO 1: si paciente es null, mensaje amable + return

        System.out.println("Atendiendo a " + paciente.nombre);
        paciente.presentarse();
    }

    public static void main(String[] args) {
        // TODO 2: crear una mascota real y atenderla

        // TODO 3: declarar Mascota pacienteAusente = null; y atenderla también
    }

    static class Mascota {
        String nombre;

        void presentarse() {
            System.out.println("Guau, soy " + nombre);
        }
    }
}

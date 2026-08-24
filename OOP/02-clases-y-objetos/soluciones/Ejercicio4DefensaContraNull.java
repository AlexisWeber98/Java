/*
 * ============================================================================
 *  Ejercicio 4 — Defensa contra null · SOLUCIÓN
 *  Módulo 02 · Clases y objetos
 * ============================================================================
 *
 *  QUÉ MIRAR DE ESTA SOLUCIÓN
 *  - El chequeo if (paciente == null) va ANTES de cualquier uso de la
 *    referencia. Ese orden es la defensa completa.
 *  - El patrón "validar y salir temprano" (guard clause): si no hay paciente,
 *    mensaje amable + return, sin anidar el resto del método.
 *  - Sin ese chequeo, paciente.presentarse() lanzaría:
 *        Exception in thread "main" java.lang.NullPointerException
 *  - null NO es un objeto: es una referencia que no apunta a nada.
 */
// Sin public y con nombre Solucion*: así ejercicios y soluciones compilan juntos.
class Solucion4DefensaContraNull {

    static void recibirVisita(Mascota paciente) {
        // Guard clause: primero validar, después trabajar
        if (paciente == null) {
            System.out.println("Hoy no vino nadie a la consulta. "
                    + "¡La sala queda lista para mañana!");
            return;   // salimos sin tocar al objeto inexistente
        }

        System.out.println("Atendiendo a " + paciente.nombre);
        paciente.presentarse();
    }

    public static void main(String[] args) {
        Mascota pacienteReal = new Mascota();
        pacienteReal.nombre = "Rocco";
        recibirVisita(pacienteReal);   // caso feliz

        Mascota pacienteAusente = null;   // declarada pero sin objeto
        recibirVisita(pacienteAusente);   // caso null: mensaje amable, cero crash

        System.out.println("El programa terminó normalmente.");
    }

    static class Mascota {
        String nombre;

        void presentarse() {
            System.out.println("Guau, soy " + nombre);
        }
    }
}

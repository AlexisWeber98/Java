/*
 * =============================================================================
 * Ejercicio 1 — Primera herencia: extends y reutilización
 * Módulo 06 · Herencia
 * =============================================================================
 *
 * ENUNCIADO
 * Tu primer vínculo padre-hijo en Java. La empresa tiene Empleados; entre
 * ellos hay Gerentes, que hacen todo lo que hace un empleado y además lideran.
 *
 * REQUISITOS
 *   1. Empleado tiene: nombre (String), sueldoBase (double) y trabajar(),
 *      que imprime que la persona está trabajando.
 *   2. Gerente hereda de Empleado con EXTENDS.
 *   3. Gerente agrega: equipoACargo (int) y dirigirReunion(), que imprime
 *      cuántas personas tiene a cargo.
 *   4. El constructor de Gerente recibe nombre, sueldoBase y equipoACargo,
 *      y delega lo del padre con super(...).
 *   5. En main: creá un Empleado y un Gerente, y comprobá que el gerente
 *      también sabe trabajar() aunque nunca lo escribiste en su clase.
 *
 * PISTAS
 *   - class Gerente EXTENDS Empleado { ... }
 *   - Dentro del constructor del hijo, super(...) va SIEMPRE primero.
 *   - Un Gerente ES-UN Empleado: por eso hereda sus atributos y métodos.
 *   - No repitas en Gerente lo que ya vive en Empleado; delegale con super(...).
 * =============================================================================
 */
public class Ejercicio1PrimeraHerencia {

    static class Empleado {
        String nombre;
        double sueldoBase;

        Empleado(String nombre, double sueldoBase) {
            this.nombre = nombre;
            this.sueldoBase = sueldoBase;
        }

        void trabajar() {
            System.out.println(nombre + " está trabajando.");
        }
    }

    // TODO 1: hacé que Gerente herede de Empleado con la palabra clave extends.

    static class Gerente {
        // TODO 2: declará el campo equipoACargo (int).

        Gerente(String nombre, double sueldoBase, int equipoACargo) {
            // TODO 3: delegá nombre y sueldoBase al constructor del padre con
            // super(...). Recordatorio: va SIEMPRE como primera sentencia.
            // TODO 4: guardá equipoACargo en el campo del punto 2.

        }

        // TODO 4: implementá dirigirReunion() para que imprima cuántas
        // personas tiene a cargo. Es comportamiento NUEVO de Gerente.
    }

    public static void main(String[] args) {
        Empleado empleado = new Empleado("Lucía", 850000);
        empleado.trabajar();

        // TODO 5: creá un Gerente llamado "Ana" con sueldo 1200000 y 5 personas.

        // TODO 6: hacé que el gerente dirija una reunión...

        // TODO 7: ...y ahora llamá gerente.trabajar(). ¿De dónde salió ese
        // método si nunca lo escribiste en Gerente? Respondelo en un comentario.
    }
}

/*
 * ============================================================================
 * Módulo 03 — Ejercicio 5: Desafío — Contador static vs contador de instancia
 * ============================================================================
 *
 * ENUNCIADO:
 *   Completá la clase ContadorDeTareas para que tenga:
 *     - un campo STATIC tareasCreadas, incrementado en el constructor;
 *     - un campo de INSTANCIA tareasRegistradas, incrementado por el método
 *       registrar().
 *   Después creá varios objetos en main y mostrá ambos contadores.
 *
 * REQUISITOS:
 *   1. tareasCreadas es static: se incrementa una vez por cada new.
 *   2. tareasRegistradas NO es static: cada objeto tiene la suya.
 *   3. Exponé lecturas con métodos: uno static para el global y otro de
 *      instancia para el propio.
 *   4. En main creá al menos 3 objetos, llamá registrar() una cantidad
 *      distinta en cada uno e imprimí ambos contadores.
 *   5. Verificá en la salida que tareasCreadas da lo mismo mires a quien
 *      mires, mientras que tareasRegistradas cambia según el objeto.
 *
 * PISTAS:
 *   - static pertenece a la CLASE: una única copia compartida por todos.
 *   - Sin static pertenece al OBJETO: cada instancia estrena su copia en 0.
 *   - Para leer el global usá el nombre de la clase:
 *     ContadorDeTareas.getTareasCreadas()
 */

class ContadorDeTareas {

    // TODO: declará el campo static int tareasCreadas.


    // TODO: declará el campo de instancia int tareasRegistradas.


    // TODO: constructor que incremente tareasCreadas.


    // TODO: método registrar() que incremente tareasRegistradas.


    // TODO: getter static getTareasCreadas().


    // TODO: getter de instancia getTareasRegistradas().

}

public class Ejercicio5DesafioContadorInstancias {

    public static void main(String[] args) {
        // TODO: creá al menos tres objetos ContadorDeTareas.

        // TODO: llamá registrar() una cantidad distinta de veces por objeto.

        // TODO: imprimí el contador static (vía la clase) y el de cada
        //  instancia para compararlos.
    }
}

/*
 * ============================================================================
 * Módulo 03 — Ejercicio 5: Desafío — Contador static vs contador de instancia
 * (SOLUCIÓN)
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

/**
 * La distinción clave de este ejercicio, explicada a fondo:
 *
 * MEMORIA Y DUEÑO DEL DATO
 *   - tareasCreadas vive asociada al objeto Class de ContadorDeTareas que la
 *     JVM carga UNA sola vez. Existe aunque no haya instancias y es compartida
 *     por todas: cualquier constructor la incrementa "en el mismo lugar".
 *   - tareasRegistradas nace junto a cada objeto en el heap. Cada new estrena
 *     su propia copia inicializada en 0; mutarla en un objeto no toca a los
 *     demás.
 *
 * ACCESO
 *   - Lo static se lee vía la clase (ContadorDeTareas.getTareasCreadas()):
 *     deja explícito que no depende de ninguna instancia.
 *   - Lo de instancia requiere un receptor concreto (lavar.getTareasRegistradas()).
 *
 * USO TÍPICO
 *   - static: contadores globales, constantes, fábricas, utilidades.
 *   - instancia: el estado propio de cada objeto (el corazón del paradigma).
 */
class ContadorDeTareas {

    /** Nivel CLASE: una sola copia compartida por todas las instancias. */
    private static int tareasCreadas = 0;

    /** Nivel OBJETO: cada instancia tiene su propia copia. */
    private int tareasRegistradas = 0;

    /**
     * El constructor corre una vez por cada new, así que este incremento
     * acumula TODOS los objetos creados, sin importar desde dónde.
     */
    ContadorDeTareas() {
        tareasCreadas++;
    }

    /** Mutación local: solo cambia EL objeto que recibe esta llamada. */
    void registrar() {
        tareasRegistradas++;
    }

    /** Lectura global, expuesta a nivel de clase. */
    static int getTareasCreadas() {
        return tareasCreadas;
    }

    /** Lectura del estado propio de cada objeto. */
    int getTareasRegistradas() {
        return tareasRegistradas;
    }
}

public class Ejercicio5DesafioContadorInstancias {

    public static void main(String[] args) {
        // Cada new dispara el constructor: tareasCreadas sube a 1, 2, 3...
        ContadorDeTareas lavar = new ContadorDeTareas();
        ContadorDeTareas cocinar = new ContadorDeTareas();
        ContadorDeTareas estudiar = new ContadorDeTareas();

        // Registrar es decisión de cada objeto: 2, 1 y 0 veces.
        lavar.registrar();
        lavar.registrar();
        cocinar.registrar();
        // estudiar nunca llama a registrar().

        System.out.println("== Contador STATIC (compartido por la clase) ==");
        System.out.println("ContadorDeTareas.getTareasCreadas() -> "
                + ContadorDeTareas.getTareasCreadas());

        System.out.println();
        System.out.println("== Contadores DE INSTANCIA (uno por objeto) ==");
        System.out.println("lavar    -> registradas: "
                + lavar.getTareasRegistradas());
        System.out.println("cocinar  -> registradas: "
                + cocinar.getTareasRegistradas());
        System.out.println("estudiar -> registradas: "
                + estudiar.getTareasRegistradas());

        System.out.println();
        // Prueba definitiva del punto 5: el static da lo mismo desde
        // cualquier objeto porque TODOS leen la misma copia compartida.
        System.out.println("El static visto desde lavar:    "
                + lavar.getTareasCreadas());
        System.out.println("El static visto desde estudiar: "
                + estudiar.getTareasCreadas());
    }
}

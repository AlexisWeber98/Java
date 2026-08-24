/*
 * =============================================================================
 *  Ejercicio 2 — Un pato, tres habilidades
 *  Módulo 09 · Interfaces
 * =============================================================================
 *
 *  ENUNCIADO
 *  ---------
 *  Una clase puede firmar MUCHOS contratos a la vez (Java no permite heredar
 *  de varias clases, pero sí implementar varias interfaces: herencia múltiple
 *  de TIPO).
 *    1. Ya tenés las interfaces Nadador, Volador y Caminante declaradas.
 *    2. Hacé que la clase Pato implemente LAS TRES y cumpla cada contrato.
 *    3. Demostrá cada habilidad desde el main, primero llamando directo al
 *       objeto y después a través de referencias de tipo interfaz.
 *
 *  REQUISITOS
 *  ----------
 *    - class Pato implements Nadador, Volador, Caminante (las tres juntas).
 *    - Un método implementado por contrato, con su @Override.
 *    - El mismo Pato guardado en referencias Nadador, Volador y Caminante.
 *
 *  PISTAS
 *  ------
 *    - implements separa cada interfaz con coma: implements A, B, C.
 *    - Una referencia de tipo interfaz solo ve los métodos de ESE contrato,
 *      aunque el objeto real tenga más habilidades. Probalo.
 * =============================================================================
 */
public class Ejercicio2UnPatoTresHabilidades {

    interface Nadador {
        void nadar();
    }

    interface Volador {
        void volar();
    }

    interface Caminante {
        void caminar();
    }

    static class Pato {
        private final String nombre;

        Pato(String nombre) {
            this.nombre = nombre;
        }

        String getNombre() {
            return nombre;
        }

        // TODO 1: hacé que Pato implemente Nadador, Volador y Caminante.

        // TODO 2: implementá nadar(), volar() y caminar() con @Override,
        //         cada uno con un println que incluya el nombre del pato.
    }

    public static void main(String[] args) {
        System.out.println("(Stub) Completá los TODO y volvé a ejecutar este archivo.");
        // TODO 3: creá un Pato, mostrá sus tres habilidades y después guardalo
        //         en una referencia Nadador, una Volador y una Caminante para
        //         probar que el MISMO objeto responde por tres contratos.
    }
}

/*
 * =============================================================================
 *  Ejercicio 2 — Un pato, tres habilidades (SOLUCIÓN)
 *  Módulo 09 · Interfaces
 * =============================================================================
 *
 *  Idea clave: herencia múltiple de TIPO. Pato es Nadador, Volador y Caminante
 *  al mismo tiempo. Cada referencia de interfaz ve solo SU contrato.
 * =============================================================================
 */
// Sin modificador y con sufijo Solucion: evita colisionar con el starter
// al compilar ambos directorios juntos; java Ejercicio2UnPatoTresHabilidades.java
// sigue funcionando porque ejecuta la primera clase del archivo.
class Ejercicio2UnPatoTresHabilidadesSolucion {

    interface Nadador {
        void nadar();
    }

    interface Volador {
        void volar();
    }

    interface Caminante {
        void caminar();
    }

    // Una clase, tres contratos firmados a la vez.
    static class Pato implements Nadador, Volador, Caminante {
        private final String nombre;

        Pato(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void nadar() {
            System.out.println(nombre + " nada como un torpedo con plumas.");
        }

        @Override
        public void volar() {
            System.out.println(nombre + " vuela en formation V hacia el sur.");
        }

        @Override
        public void caminar() {
            System.out.println(nombre + " camina contoneándose sin vergüenza.");
        }
    }

    public static void main(String[] args) {
        Pato don = new Pato("Donald");

        // 1) Llamadas directas: el objeto real tiene las tres habilidades.
        don.nadar();
        don.volar();
        don.caminar();

        System.out.println("--- Ahora miramos cada contrato por separado ---");

        // 2) A través de referencias de interfaz: la referencia limita qué
        //    mensajes podés mandar, aunque el objeto tenga más habilidades.
        Nadador nadador = don;
        Volador volador = don;
        Caminante caminante = don;

        nadador.nadar();
        volador.volar();
        caminante.caminar();
    }
}

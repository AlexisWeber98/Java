// try-with-resources: el recurso se cierra SOLO, incluso si vuela una excepción.
// Ejecutar: java RecursosYTryWithResources.java

public class RecursosYTryWithResources {

    // Recurso de juguete que simula abrir/cerrar (archivo, conexión, socket...).
    static class RecursoSimulado implements AutoCloseable {
        private final String nombre;

        public RecursoSimulado(String nombre) {
            this.nombre = nombre;
            System.out.println("[" + nombre + "] abierto");
        }

        public void usar() {
            System.out.println("[" + nombre + "] en uso");
        }

        @Override
        public void close() {
            System.out.println("[" + nombre + "] cerrado");
        }
    }

    // Camino feliz: close() corre al salir del try, sin llamarlo manualmente.
    static void usoNormal() {
        System.out.println("--- Uso normal ---");
        try (RecursoSimulado recurso = new RecursoSimulado("A")) {
            recurso.usar();
        }
        System.out.println("Fin del bloque: ya está cerrado, sin finally.");
    }

    // Camino trágico: la excepción vuela... y close() corre IGUAL.
    static void usoConExcepcion() {
        System.out.println("\n--- Uso con excepción ---");
        try (RecursoSimulado recurso = new RecursoSimulado("B")) {
            recurso.usar();
            throw new IllegalStateException("Algo explotó a mitad del trabajo");
        } catch (IllegalStateException e) {
            System.out.println("Atrapamos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        usoNormal();
        usoConExcepcion();

        // Dos recursos a la vez: se cierran en orden inverso al declarado.
        System.out.println("\n--- Varios recursos ---");
        try (RecursoSimulado primero = new RecursoSimulado("C");
             RecursoSimulado segundo = new RecursoSimulado("D")) {
            primero.usar();
            segundo.usar();
        }

        System.out.println("\nCero fugas: todo recurso tuvo su close().");
    }
}

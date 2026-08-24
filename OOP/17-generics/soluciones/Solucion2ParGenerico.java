/*
 * ============================================================================
 *  Ejercicio 2 — Solución: Par<K, V>
 * ============================================================================
 *
 *  IDEA CLAVE: dos variables de tipo independientes en la misma clase.
 *  K y V no se conocen entre sí: cada una se congela por separado al
 *  instanciar. Es la pieza fundamental sobre la que está construido Map.
 * ============================================================================
 */
public class Solucion2ParGenerico {

    /** Par inmutable: los campos son final, nace y muere con sus valores. */
    static class Par<K, V> {
        private final K clave;
        private final V valor;

        Par(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }

        public K getClave() {
            return clave;
        }

        public V getValor() {
            return valor;
        }

        void mostrar() {
            System.out.printf("   %-16s → %s%n", clave, valor);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Mini diccionario dev (Par<String, String>) ===");

        Par<String, String> entrada1 = new Par<>(
                "genérico", "clase o método parametrizado por tipo: escribís una vez, usás con muchos");
        Par<String, String> entrada2 = new Par<>(
                "encapsulamiento", "esconder el CÓMO; exponer solo el QUÉ");
        Par<String, String> entrada3 = new Par<>(
                "bound", "límite que le ponés a un parámetro de tipo con extends");

        entrada1.mostrar();
        entrada2.mostrar();
        entrada3.mostrar();

        // Los getters devuelven tipos concretos, sin casts:
        System.out.println("\ngetClave() de entrada1 -> \"" + entrada1.getClave()
                + "\" (largo: " + entrada1.getClave().length() + ")");

        // --- K y V son independientes: ahora K=String, V=Double ---
        System.out.println("\n=== Lista de precios (Par<String, Double>) ===");
        Par<String, Double> precioCafe  = new Par<>("Café", 2850.50);
        Par<String, Double> precioMedialuna = new Par<>("Medialuna", 990.00);
        precioCafe.mostrar();
        precioMedialuna.mostrar();

        /*
         * Fijate el truco: la clase Par es UNA sola. Lo que cambia es la
         * "receta" de tipos que le pasás entre < y >. Eso es reutilización
         * real, sin herencia forzada ni Object por todos lados.
         */
    }
}

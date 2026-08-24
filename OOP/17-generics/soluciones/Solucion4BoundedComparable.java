/*
 * ============================================================================
 *  Ejercicio 4 — Solución: genéricos con límite (bounded types)
 * ============================================================================
 *
 *  IDEA CLAVE: el bound <T extends Comparable<T>> amplía tus poderes dentro
 *  del método: a cambio de aceptar SOLO tipos comparables, el compilador te
 *  deja llamar compareTo. Es un intercambio justo: menos tipos posibles,
 *  más garantías por tipo.
 * ============================================================================
 */
public class Solucion4BoundedComparable {

    /**
     * Devuelve el mayor de tres valores.
     * El bound se lee: "T es Comparable de sí mismo". Sin ese extends,
     * la línea del compareTo no compila — probalo y vas a ver el error.
     */
    static <T extends Comparable<T>> T mayorDe(T a, T b, T c) {
        T mayor = a;                          // hipótesis de arranque
        if (b.compareTo(mayor) > 0) {         // b gana a la hipótesis
            mayor = b;
        }
        if (c.compareTo(mayor) > 0) {         // c desafía al vigente
            mayor = c;
        }
        return mayor;
    }

    /** Dominio propio que SABE ordenarse. El criterio (precio) vive acá adentro. */
    static class Producto implements Comparable<Producto> {
        private final String nombre;
        private final double precio;

        Producto(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
        }

        @Override
        public int compareTo(Producto otro) {
            return Double.compare(this.precio, otro.precio);
        }

        @Override
        public String toString() {
            return nombre + " ($" + precio + ")";
        }
    }

    public static void main(String[] args) {
        // --- Con Integer: autoboxing a Integer y Comparable<Integer> ya existe ---
        System.out.println("Mayor entre enteros -> " + mayorDe(7, 42, 13));

        // --- Con String: compara lexicográficamente ("alfajor" < "bonete" < "churro") ---
        System.out.println("Mayor palabra       -> " + mayorDe("alfajor", "bonete", "churro"));

        // --- Con TU clase: el mismo método genérico, cero modificaciones ---
        System.out.println("Producto más caro   -> " + mayorDe(
                new Producto("Teclado mecánico", 45999.90),
                new Producto("Monitor 24\"", 185000.00),
                new Producto("Mouse inalámbrico", 12350.75)));

        /*
         * El punto fino: mayorDe no tiene NI IDEA de qué es un Producto ni
         * de que existe el precio. Solo exige "sé Comparable de vos mismo".
         * La política de orden la define la clase; el algoritmo genérico la
         * consume. Eso es inversión de dependencias en una firma de método.
         */
    }
}

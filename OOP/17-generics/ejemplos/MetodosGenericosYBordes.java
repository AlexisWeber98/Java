import java.util.List;

/**
 * Módulo 17 — Métodos genéricos, tipos acotados (bounds) y wildcards.
 * Tres herramientas para tres necesidades distintas.
 */
public class MetodosGenericosYBordes {

    /** Método genérico simple: T propio del método, inferido por el compilador. */
    static <T> T primero(List<T> lista) {
        return lista.isEmpty() ? null : lista.get(0);
    }

    /**
     * Bound: "T debe ser Comparable de sí mismo".
     * Sin el extends, e.compareTo(max) no compilaría.
     */
    static <T extends Comparable<T>> T maximo(T[] elementos) {
        if (elementos.length == 0) {
            throw new IllegalArgumentException("arreglo vacío");
        }
        T max = elementos[0];
        for (T e : elementos) {
            if (e.compareTo(max) > 0) {
                max = e;
            }
        }
        return max;
    }

    /**
     * Wildcard producer: acepta List<Integer>, List<Double>, etc.
     * Solo leemos; por eso ? extends.
     */
    static double sumaTotal(List<? extends Number> numeros) {
        double total = 0;
        for (Number n : numeros) {
            total += n.doubleValue();
        }
        return total;
    }

    public static void main(String[] args) {
        // --- Método genérico: un código, varios tipos ---
        String primerNombre = primero(List.of("Ana", "Beto", "Carla"));
        Integer primerNumero = primero(List.of(10, 20, 30));
        System.out.println("Primero de nombres: " + primerNombre);
        System.out.println("Primero de números: " + primerNumero);

        // --- Bound con Comparable: funciona con cualquier tipo comparable ---
        String palabraMax = maximo(new String[]{"mate", "bombilla", "yerba"});
        Integer numeroMax = maximo(new Integer[]{7, 42, 13});
        System.out.println("Máximo lexicográfico: " + palabraMax);
        System.out.println("Máximo numérico: " + numeroMax);

        // --- Wildcard: una sola firma para muchas listas ---
        List<Integer> enteros = List.of(1, 2, 3);
        List<Double> decimales = List.of(1.5, 2.5);
        System.out.println("Suma de enteros:  " + sumaTotal(enteros));
        System.out.println("Suma de decimales: " + sumaTotal(decimales));

        // sumaTotal(List.of("no")); // ❌ no compila: String no es Number
    }
}

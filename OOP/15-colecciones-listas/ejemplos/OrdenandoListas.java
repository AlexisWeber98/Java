// Módulo 15 — Ordenar listas: orden natural y criterios propios con Comparator.
// Ejecutar: java ejemplos/OrdenandoListas.java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrdenandoListas {

    // Record (módulo 14): datos inmutables sin boilerplate.
    record Producto(String nombre, double precio) {
        @Override
        public String toString() {
            return String.format("%-12s $%,8.2f", nombre, precio);
        }
    }

    public static void main(String[] args) {
        List<Producto> productos = new ArrayList<>(List.of(
                new Producto("Teclado", 45.99),
                new Producto("Monitor", 199.50),
                new Producto("Mouse", 19.90),
                new Producto("Cable HDMI", 8.25),
                new Producto("Auriculares", 59.00)
        ));

        System.out.println("Orden de inserción:");
        productos.forEach(p -> System.out.println("  " + p));

        // 1) Comparator explícito: por precio ascendente.
        // "Compará dos productos: el de menor precio va primero."
        List<Producto> copiaPrecio = new ArrayList<>(productos);
        copiaPrecio.sort((Producto a, Producto b) -> Double.compare(a.precio(), b.precio()));
        System.out.println("\nPor precio ascendente:");
        copiaPrecio.forEach(p -> System.out.println("  " + p));

        // 2) Orden natural: si la clase implementara Comparable, Collections.sort
        //    alcanzaría. Con Comparator.comparing obtenemos lo mismo, legible:
        List<Producto> copiaNombre = new ArrayList<>(productos);
        copiaNombre.sort(Comparator.comparing(Producto::nombre));
        System.out.println("\nPor nombre alfabético:");
        copiaNombre.forEach(p -> System.out.println("  " + p));

        // 3) Criterio compuesto y reverso, encadenando Comparators:
        copiaPrecio.sort(Comparator.comparingDouble(Producto::precio).reversed());
        System.out.println("\nPor precio descendente (reversed):");
        copiaPrecio.forEach(p -> System.out.println("  " + p));
    }
}

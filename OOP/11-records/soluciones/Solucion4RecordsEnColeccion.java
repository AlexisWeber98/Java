/*
 * ============================================================================
 * Módulo 11 - Records | Solución 4: Records en colección: lista de productos
 * ============================================================================
 * Idea clave: contains, indexOf y remove NO miran referencias: comparan por
 * equals (todos los componentes). Por eso encuentran el duplicado lógico,
 * y remove quita sólo su primera ocurrencia.
 */
import java.util.ArrayList;

public class Solucion4RecordsEnColeccion {

    public static void main(String[] args) {
        ArrayList<ProductoRecord> productos = new ArrayList<>();
        productos.add(new ProductoRecord("Café", 8500.0));
        productos.add(new ProductoRecord("Yerba", 4200.0));
        productos.add(new ProductoRecord("Azúcar", 1900.0));
        productos.add(new ProductoRecord("Café", 8500.0)); // duplicado lógico: otro objeto, mismo contenido

        System.out.println("Lista inicial (" + productos.size() + "): " + productos);

        ProductoRecord buscado = new ProductoRecord("Café", 8500.0); // objeto nuevo, jamás insertado

        boolean existe = productos.contains(buscado);
        System.out.println("\n¿contains(buscado)?  -> " + existe);

        int posicion = productos.indexOf(buscado);
        System.out.println("indexOf(buscado)     -> índice " + posicion);

        productos.remove(buscado);
        System.out.println("\nTras remove(buscado) quedan " + productos.size() + ": " + productos);
    }

    record ProductoRecord(String nombre, double precio) {
    }
}

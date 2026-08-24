// ConstructoresSobrecargadosYThis.java — sobrecarga de constructores
// encadenados con this(...): una sola fuente de verdad para la inicialización.

public class ConstructoresSobrecargadosYThis {

    static class Producto {
        String nombre;
        double precio;
        int stock;

        // Constructor mínimo: delega en el de dos parámetros.
        Producto(String nombre) {
            this(nombre, 0.0);          // primera sentencia obligatoria
        }

        // Constructor intermedio: delega en el canónico.
        Producto(String nombre, double precio) {
            this(nombre, precio, 0);
        }

        // Constructor canónico: el ÚNICO que asigna campos.
        Producto(String nombre, double precio, int stock) {
            if (precio < 0) {
                throw new IllegalArgumentException("Precio negativo: " + precio);
            }
            this.nombre = nombre;
            this.precio = precio;
            this.stock = stock;
        }

        String estado() {
            return "Producto{nombre='" + nombre + "', precio=" + precio + ", stock=" + stock + "}";
        }
    }

    public static void main(String[] args) {
        Producto teclado = new Producto("Teclado");
        Producto mouse = new Producto("Mouse", 1500.0);
        Producto monitor = new Producto("Monitor", 250000.0, 7);

        System.out.println(teclado.estado());
        System.out.println(mouse.estado());
        System.out.println(monitor.estado());

        // La validación vive solo en el canónico: cualquier camino pasa por ahí.
        try {
            new Producto("Truco", -1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Rechazado al nacer: " + e.getMessage());
        }

        System.out.println("Moraleja: la inicialización real está en UN solo constructor.");
    }
}

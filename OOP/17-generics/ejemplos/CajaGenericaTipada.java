import java.util.ArrayList;
import java.util.List;

/**
 * Módulo 17 — Tu propia clase genérica: Caja<T>.
 * Un molde, muchos usos; el compilador verifica cada instanciación.
 */
public class CajaGenericaTipada {

    static class Producto {
        private final String nombre;

        Producto(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return "Producto[" + nombre + "]";
        }
    }

    /** La clase genérica: T es un parámetro de tipo, decidido al instanciar. */
    static class Caja<T> {
        private T contenido;

        void guardar(T nuevo) {
            this.contenido = nuevo;
        }

        T obtener() {
            return contenido;
        }

        void vaciar() {
            this.contenido = null;
        }
    }

    public static void main(String[] args) {
        // Un molde, muchos usos: cada caja fija su propio T
        Caja<String> cajaTexto = new Caja<>();
        cajaTexto.guardar("regalo de cumpleaños");
        String mensaje = cajaTexto.obtener();      // sin cast: T es String
        System.out.println("Caja de texto contiene: " + mensaje.toUpperCase());

        Caja<Producto> cajaProducto = new Caja<>();
        cajaProducto.guardar(new Producto("Yerba"));
        Producto producto = cajaProducto.obtener(); // sin cast: T es Producto
        System.out.println("Caja de producto contiene: " + producto);

        // El compilador rechaza mezclar tipos:
        // cajaTexto.guardar(producto);   // ❌ incompatible types: Producto no es String
        // int largo = cajaTexto.obtener().length() + producto; // ❌ también atrapado

        cajaTexto.vaciar();
        System.out.println("¿Vacía? " + (cajaTexto.obtener() == null));

        System.out.println();

        // Bonus: contenedores genéricos se componen sin fricción
        List<Caja<Producto>> estante = new ArrayList<>();
        estante.add(cajaProducto);
        Caja<Producto> otra = new Caja<>();
        otra.guardar(new Producto("Azúcar"));
        estante.add(otra);
        for (Caja<Producto> caja : estante) {
            System.out.println("Estante -> " + caja.obtener());
        }
    }
}

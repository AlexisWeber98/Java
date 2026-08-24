import java.util.ArrayList;
import java.util.List;

/**
 * Módulo 17 — El mundo ANTES de los generics.
 * Contenedores basados en Object: casts a mano y ClassCastException en runtime.
 */
public class ElMundoSinGenerics {

    static class Producto {
        private final String nombre;

        Producto(String nombre) {
            this.nombre = nombre;
        }

        String nombre() {
            return nombre;
        }
    }

    public static void main(String[] args) {
        // ---------- Versión pre-generics: lista de Object ----------
        List carro = new ArrayList();          // raw list: guarda cualquier Object
        carro.add(new Producto("Yerba"));
        carro.add("texto suelto");             // compila: nadie impide mezclar tipos

        // Lectura: cast obligatorio, a mano, con riesgo
        Producto primero = (Producto) carro.get(0);
        System.out.println("Primero: " + primero.nombre());

        // El bug clásico: cast al tipo equivocado.
        // Descomentá la línea siguiente y mirá el ClassCastException EN RUNTIME:
        // String crash = (String) carro.get(0);

        // Y el caso "correcto pero feo": hay que recordar qué había en cada posición.
        String texto = (String) carro.get(1);  // cast correcto... porque ME ACUERDO que era String
        System.out.println("Texto: " + texto.toUpperCase());

        if (carro.get(0) instanceof Producto p) {
            System.out.println("Chequeo defensivo necesario: " + p.nombre());
        }

        System.out.println();

        // ---------- Versión con generics: el compilador es la red de seguridad ----------
        List<Producto> carrito = new ArrayList<>();
        carrito.add(new Producto("Mate"));
        carrito.add(new Producto("Bombilla"));

        Producto item = carrito.get(0);        // sin cast, tipo garantizado
        System.out.println("Item: " + item.nombre());

        // carrito.add("texto");               // ❌ no compila: error atrapado ANTES de ejecutar

        for (Producto p : carrito) {
            System.out.println("- " + p.nombre());
        }
    }
}

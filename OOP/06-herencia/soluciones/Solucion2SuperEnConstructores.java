/*
 * =============================================================================
 * Ejercicio 2 (SOLUCIÓN) — super(...) en constructores: el orden manda
 * Módulo 06 · Herencia
 * =============================================================================
 *
 * IDEAS CLAVE DE LA SOLUCIÓN
 *   - Vehiculo solo tiene Vehiculo(String marca): Java ya NO puede insertar
 *     el super() implícito sin argumentos. Sin super(marca) explícito,
 *     el código del hijo NO COMPILA ("cannot be applied to given types").
 *   - La salida demuestra el orden: primero construye el padre, después
 *     el hijo. Toda construcción arranca por la base de la jerarquía.
 *   - mostrarFicha() prueba que la marca viajó bien por super(marca).
 * =============================================================================
 */
public class Solucion2SuperEnConstructores {

    static class Vehiculo {
        String marca;

        // Único constructor de Vehiculo: obliga a todo hijo a pasarle la marca.
        Vehiculo(String marca) {
            this.marca = marca;
            System.out.println("[Vehiculo] guardo la marca " + marca);
        }
    }

    static class Auto extends Vehiculo {
        int cantidadPuertas;

        // CLAVE: super(marca) delega al padre lo que le corresponde al padre.
        // Va siempre primero: es una regla del lenguaje, no una sugerencia.
        Auto(String marca, int cantidadPuertas) {
            super(marca);
            this.cantidadPuertas = cantidadPuertas;
            System.out.println("[Auto] agrego las " + cantidadPuertas + " puertas");
        }

        void mostrarFicha() {
            // Si la ficha muestra bien la marca, super(...) hizo su trabajo.
            System.out.println("Auto " + marca + " de "
                    + cantidadPuertas + " puertas.");
        }
    }

    public static void main(String[] args) {
        Auto auto = new Auto("Toyota", 4);
        auto.mostrarFicha();

        // Fijate el ORDEN de la salida:
        //   1) [Vehiculo] guardo la marca Toyota   <- primero el padre
        //   2) [Auto] agrego las 4 puertas          <- después el hijo
        // Un objeto se construye de adentro (base) hacia afuera (subclase).
    }
}

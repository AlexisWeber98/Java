/*
 * =============================================================================
 * Ejercicio 2 — super(...) en constructores: el orden manda
 * Módulo 06 · Herencia
 * =============================================================================
 *
 * ENUNCIADO
 * Todo Vehículo nace con una marca. Un Auto, además, tiene puertas. El
 * constructor del hijo NO puede inventarse la marca: se la pide al padre.
 *
 * REQUISITOS
 *   1. Vehiculo tiene un único constructor: Vehiculo(String marca).
 *   2. Auto hereda de Vehiculo y agrega el campo cantidadPuertas (int).
 *   3. El constructor de Auto recibe (marca, cantidadPuertas) y pasa la
 *      marca al padre con super(marca).
 *   4. mostrarFicha() imprime marca y puertas, para PROBAR que la marca
 *      quedó bien inicializada a través de la cadena de constructores.
 *   5. En main: creá un Auto("Toyota", 4) y mostrá su ficha. Observá en qué
 *      orden aparecen los mensajes de cada constructor.
 *
 * PISTAS
 *   - super(...) es SIEMPRE la primera sentencia del constructor hijo.
 *   - Vehiculo NO tiene constructor sin parámetros: si te olvidás del
 *      super(marca), el archivo ni siquiera compila. Probalo como experimento.
 *   - La construcción siempre arranca por la clase base y baja hacia el hijo.
 * =============================================================================
 */
public class Ejercicio2SuperEnConstructores {

    static class Vehiculo {
        String marca;

        // Único constructor de Vehiculo: obliga a todo hijo a pasarle la marca.
        Vehiculo(String marca) {
            this.marca = marca;
            System.out.println("[Vehiculo] guardo la marca " + marca);
        }
    }

    static class Auto extends Vehiculo {
        // TODO 1: declará el campo cantidadPuertas (int).

        Auto(String marca, int cantidadPuertas) {
            // CLAVE: esta línea ya está. Es la que le presta la marca al padre.
            // EXPERIMENTO: comentala y compilá. ¿Qué error te tira javac?
            // ¿Te dice algo de "cannot be applied to given types"?
            super(marca);

            // TODO 2: guardá cantidadPuertas en su campo.

            System.out.println("[Auto] agrego las " + cantidadPuertas + " puertas");
        }

        void mostrarFicha() {
            // TODO 3: imprimí una ficha tipo "Auto Toyota de 4 puertas."
            // Si la marca aparece bien, super(marca) hizo su trabajo.
        }
    }

    public static void main(String[] args) {
        // TODO 4: creá new Auto("Toyota", 4), guardalo en una variable,
        // llamá auto.mostrarFicha() y mirá el ORDEN de los mensajes:
        // ¿se construye primero el padre o el hijo?
    }
}

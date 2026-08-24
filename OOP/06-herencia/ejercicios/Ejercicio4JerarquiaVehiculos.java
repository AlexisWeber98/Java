/*
 * =============================================================================
 * Ejercicio 4 — Jerarquía de vehículos: diseño de la subclase
 * Módulo 06 · Herencia
 * =============================================================================
 *
 * ENUNCIADO
 * Ahora diseñás vos la jerarquía. La base Vehiculo ya existe; te toca crear
 * TRES hijas, cada una con un atributo propio y un comportamiento propio,
 * y recorrerlas juntas como si fueran "solo" Vehiculos.
 *
 * REQUISITOS
 *   1. Auto agrega: cantidadPuertas (int) + abrirBaul().
 *   2. Moto agrega: cilindradaCc (int) + hacerCaballito().
 *   3. Camioneta agrega: capacidadCargaKg (double) + cargar(kilos).
 *   4. Las tres redefinen describir() con @Override: llaman a
 *      super.describir() y agregan su dato particular.
 *   5. En main: instanciá las tres, metelas en un Vehiculo[] y recorrélo
 *      con un for-each llamando describir(). Después invocá el comportamiento
 *      propio de cada una.
 *
 * PISTAS
 *   - El constructor de cada hija arranca con super(marca).
 *   - describir() en la base usa System.out.print (sin salto de línea):
 *     así cada hija completa la frase a su manera.
 *   - Un Vehiculo[] guarda Auto, Moto y Camioneta sin reclamar: todas SON
 *     vehiculos. Eso es sustitución.
 * =============================================================================
 */
public class Ejercicio4JerarquiaVehiculos {

    // CLASE BASE: ya está lista. Lo común vive acá; las hijas aportan lo propio.
    static class Vehiculo {
        String marca;

        Vehiculo(String marca) {
            this.marca = marca;
        }

        void describir() {
            System.out.print("Soy un vehículo de marca " + marca);
        }
    }

    static class Auto extends Vehiculo {
        // TODO 1: campo cantidadPuertas (int).

        Auto(String marca, int cantidadPuertas) {
            super(marca);
            // TODO 2: guardá cantidadPuertas.
        }

        // TODO 3: @Override describir(): primero super.describir(), después
        // imprimí ", y soy un AUTO de N puertas." (con println).

        // TODO 4: método propio abrirBaul() que imprima que el auto abre su baúl.
    }

    static class Moto extends Vehiculo {
        // TODO 5: campo cilindradaCc (int).

        Moto(String marca, int cilindradaCc) {
            super(marca);
            // TODO 6: guardá cilindradaCc.
        }

        // TODO 7: @Override describir() al estilo del Auto.

        // TODO 8: hacerCaballito() que imprima que la moto levanta la ruedita.
    }

    static class Camioneta extends Vehiculo {
        // TODO 9: campo capacidadCargaKg (double).

        Camioneta(String marca, double capacidadCargaKg) {
            super(marca);
            // TODO 10: guardá capacidadCargaKg.
        }

        // TODO 11: @Override describir() al estilo del Auto.

        // TODO 12: cargar(double kilos) que imprima cuántos kg carga
        // (podés mencionar su capacidad máxima para presumir un poco).
    }

    public static void main(String[] args) {
        // TODO 13: instanciá un Auto("Toyota", 4), una Moto("Honda", 250)
        // y una Camioneta("Ford Ranger", 950).

        // TODO 14: metelas en un Vehiculo[] flota y recorréla con for-each
        // llamando flota[i].describir(). Mirá cómo cada objeto responde DISTINTO
        // al mismo mensaje: eso va a ser polimorfismo en el módulo 07.

        // TODO 15: invocá abrirBaul(), hacerCaballito() y cargar(600)
        // sobre sus objetos correspondientes.
    }
}

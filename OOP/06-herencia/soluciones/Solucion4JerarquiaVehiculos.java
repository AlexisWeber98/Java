/*
 * =============================================================================
 * Ejercicio 4 (SOLUCIÓN) — Jerarquía de vehículos: diseño de la subclase
 * Módulo 06 · Herencia
 * =============================================================================
 *
 * IDEAS CLAVE DE LA SOLUCIÓN
 *   - La base guarda lo común (marca + describir()); cada hija agrega UN
 *     atributo y UN comportamiento. Herencia = especialización, no acumulación.
 *   - describir() se compone: super.describir() pone la parte común y la
 *     hija completa la frase. Cada objeto responde al mismo mensaje a SU manera.
 *   - El for-each sobre Vehiculo[] demuestra sustitución: donde se espera un
 *     Vehiculo puede entrar cualquier hija.
 * =============================================================================
 */
public class Solucion4JerarquiaVehiculos {

    // CLASE BASE: lo común vive acá.
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
        int cantidadPuertas;

        Auto(String marca, int cantidadPuertas) {
            super(marca);
            this.cantidadPuertas = cantidadPuertas;
        }

        @Override
        void describir() {
            super.describir(); // parte común...
            System.out.println(", y soy un AUTO con " + cantidadPuertas + " puertas.");
        }

        void abrirBaul() {
            System.out.println("El " + marca + " abre su baúl.");
        }
    }

    static class Moto extends Vehiculo {
        int cilindradaCc;

        Moto(String marca, int cilindradaCc) {
            super(marca);
            this.cilindradaCc = cilindradaCc;
        }

        @Override
        void describir() {
            super.describir();
            System.out.println(", y soy una MOTO de " + cilindradaCc + " cc.");
        }

        void hacerCaballito() {
            System.out.println("La " + marca + " levanta la rueda delantera: ¡caballito!");
        }
    }

    static class Camioneta extends Vehiculo {
        double capacidadCargaKg;

        Camioneta(String marca, double capacidadCargaKg) {
            super(marca);
            this.capacidadCargaKg = capacidadCargaKg;
        }

        @Override
        void describir() {
            super.describir();
            System.out.println(", y soy una CAMIONETA que carga hasta "
                    + capacidadCargaKg + " kg.");
        }

        void cargar(double kilos) {
            if (kilos > capacidadCargaKg) {
                System.out.println("La " + marca + " no puede cargar " + kilos
                        + " kg: supera su capacidad de " + capacidadCargaKg + " kg.");
                return;
            }
            System.out.println("La " + marca + " carga " + kilos
                    + " kg sin despeinarse.");
        }
    }

    public static void main(String[] args) {
        Auto auto = new Auto("Toyota", 4);
        Moto moto = new Moto("Honda", 250);
        Camioneta camioneta = new Camioneta("Ford Ranger", 950);

        System.out.println("=== La flota responde al mismo mensaje ===");
        // Un arreglo de la BASE aloja objetos de las HIJAS: sustitución.
        Vehiculo[] flota = { auto, moto, camioneta };
        for (Vehiculo vehiculo : flota) {
            vehiculo.describir(); // cada una completa la frase a su manera
        }

        System.out.println("\n=== Comportamientos propios ===");
        auto.abrirBaul();
        moto.hacerCaballito();
        camioneta.cargar(600);
        camioneta.cargar(2000); // caso límite: más carga de la que soporta
    }
}

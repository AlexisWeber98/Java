/*
 * ===========================================================================
 *  Ejercicio 5 — Desafío: tarifas de transporte
 *  Módulo 08 · Clases abstractas
 * ===========================================================================
 *
 *  ENUNCIADO
 *  Una logística cobra distinto según el vehículo: la Bicicleta cobra una
 *  tarifa fija, la Moto suma un monto por kilo y el Camión aplica una escala
 *  con descuento por volumen. La familia se modela con la clase abstracta
 *  Transporte, que guarda cargaKg, deja calcularTarifa() a cada hija y
 *  ofrece el método CONCRETO descripcion(), que combina datos base + tarifa.
 *
 *  REQUISITOS
 *  1. Completá Moto.calcularTarifa() y Camion.calcularTarifa() con sus
 *     fórmulas (indicadas en el comentario de cada clase).
 *  2. Sumá una moto y un camión a la flota del main.
 *  3. El bucle debe recorrer el arreglo Transporte[] pidiendo descripcion()
 *     a cada elemento: un solo bucle y CERO if/instanceof para decidir.
 *
 *  PISTAS
 *  - descripcion() es concreto en la base y llama a calcularTarifa(): otra
 *    vez lo abstracto y lo concreto trabajando juntos.
 *  - El polimorfismo hace el resto: transporte.descripcion() ejecuta la
 *    versión de cada subclase sin que el bucle sepa cuál es cuál.
 *  - Fórmulas: Moto = 300 + (15 por kg);
 *    Camion = 800 + (10 por kg), con 10% de descuento si supera los 1000 kg.
 *
 *  Ejecutá así:  java Ejercicio5DesafioTarifasTransporte.java
 */

public class Ejercicio5DesafioTarifasTransporte {

    static abstract class Transporte {
        protected final int cargaKg;

        protected Transporte(int cargaKg) {
            this.cargaKg = cargaKg;
        }

        // Cada vehículo define SU fórmula; la base ni siquiera la conoce.
        abstract double calcularTarifa();

        // Método CONCRETO que combina datos base + resultado polimórfico.
        String descripcion() {
            return getClass().getSimpleName()
                    + " | carga: " + cargaKg + " kg"
                    + " | tarifa: $ " + String.format("%.2f", calcularTarifa());
        }
    }

    // Modelo a imitar: la fórmula más simple de todas.
    static class Bicicleta extends Transporte {
        Bicicleta(int cargaKg) {
            super(cargaKg);
        }

        @Override
        double calcularTarifa() {
            return 200; // tarifa fija, sin importar los kilos
        }
    }

    static class Moto extends Transporte {
        Moto(int cargaKg) {
            super(cargaKg);
        }

        @Override
        double calcularTarifa() {
            // TODO 1: $ 300 de arranque + $ 15 por cada kilo.
            return 0;
        }
    }

    static class Camion extends Transporte {
        Camion(int cargaKg) {
            super(cargaKg);
        }

        @Override
        double calcularTarifa() {
            // TODO 2: $ 800 + $ 10 por kilo; si supera los 1000 kg,
            //         aplicá un 10% de descuento sobre el total.
            return 0;
        }
    }

    public static void main(String[] args) {
        Transporte[] flota = {
                new Bicicleta(8)
                // TODO 2: sumá una Moto de 45 kg y un Camion de 1200 kg.
        };

        for (Transporte transporte : flota) {
            System.out.println(transporte.descripcion());
        }
    }
}

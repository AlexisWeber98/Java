/*
 * ===========================================================================
 *  Ejercicio 5 — Desafío: tarifas de transporte · SOLUCIÓN
 *  Módulo 08 · Clases abstractas
 * ===========================================================================
 *
 *  Cambios clave: cada vehículo define su fórmula en calcularTarifa() y el
 *  main recorre un Transporte[] con UN solo bucle, sin ifs ni instanceof.
 *
 *  Salida esperada:
 *    Bicicleta | carga: 8 kg   | tarifa: $ 200.00
 *    Moto      | carga: 45 kg  | tarifa: $ 975.00
 *    Camion    | carga: 1200 kg | tarifa: $ 11520.00
 *
 *  Ejecutá así:  java Ejercicio5DesafioTarifasTransporte.java
 */

// La clase principal de la solución se llama distinto (y sin public) para
// que ejercicios y soluciones puedan compilarse juntos sin choque de
// nombres. El lanzador java toma igualmente la primera clase del archivo.
class Solucion5DesafioTarifasTransporte {

    static abstract class Transporte {
        protected final int cargaKg;

        protected Transporte(int cargaKg) {
            this.cargaKg = cargaKg;
        }

        // Hueco polimórfico: la base no sabe cómo se cobra, solo que TODOS
        // saben calcularse su tarifa.
        abstract double calcularTarifa();

        // Concreto + abstracto juntos: descripcion() arma la parte común y
        // delega en calcularTarifa(), que se resuelve según el tipo real.
        String descripcion() {
            return getClass().getSimpleName()
                    + " | carga: " + cargaKg + " kg"
                    + " | tarifa: $ " + String.format("%.2f", calcularTarifa());
        }
    }

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
            return 300 + 15 * cargaKg; // arranque + precio por kilo
        }
    }

    static class Camion extends Transporte {
        Camion(int cargaKg) {
            super(cargaKg);
        }

        @Override
        double calcularTarifa() {
            double tarifa = 800 + 10 * cargaKg;
            if (cargaKg > 1000) {
                tarifa *= 0.9; // descuento por volumen: más carga, mejor tasa
            }
            return tarifa;
        }
    }

    public static void main(String[] args) {
        // La flota completa vive en un arreglo del TIPO ABSTRACTO. Agregar un
        // vehículo nuevo no tocaría este bucle: solo una subclase nueva con
        // su fórmula. Esa es la victoria de abstraer bien.
        Transporte[] flota = {
                new Bicicleta(8),
                new Moto(45),
                new Camion(1200)
        };

        for (Transporte transporte : flota) {
            System.out.println(transporte.descripcion());
        }
    }
}

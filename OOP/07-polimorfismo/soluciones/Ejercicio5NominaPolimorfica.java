/*
 * ============================================================================
 * Módulo 07 – Polimorfismo | Ejercicio 5: Nómina polimórfica (SOLUCIÓN)
 * ============================================================================
 * Idea clave: el bucle de la nómina solo conoce Empleado; cada subclase aporta
 * su propia regla de cálculo vía override. super.calcularSueldo() reutiliza
 * la base en vez de duplicarla.
 */
public class Ejercicio5NominaPolimorfica {

    static class Empleado {
        final String nombre;
        final double salarioBase;

        Empleado(String nombre, double salarioBase) {
            this.nombre = nombre;
            this.salarioBase = salarioBase;
        }

        double calcularSueldo() {
            return salarioBase;
        }
    }

    static class Vendedor extends Empleado {
        final double ventas;
        final double comision; // porcentaje como fracción: 0.05 equivale a 5%

        Vendedor(String nombre, double salarioBase, double ventas, double comision) {
            super(nombre, salarioBase);
            this.ventas = ventas;
            this.comision = comision;
        }

        @Override
        double calcularSueldo() {
            // La base se pide con super (una sola fuente de verdad) y arriba
            // se suma lo propio del vendedor.
            return super.calcularSueldo() + ventas * comision;
        }
    }

    static class Gerente extends Empleado {
        final double bono;

        Gerente(String nombre, double salarioBase, double bono) {
            super(nombre, salarioBase);
            this.bono = bono;
        }

        @Override
        double calcularSueldo() {
            return super.calcularSueldo() + bono;
        }
    }

    public static void main(String[] args) {
        // Tres tipos distintos conviven como Empleado: upcasting puro.
        Empleado[] nomina = {
                new Empleado("Ana", 800_000),
                new Vendedor("Bruno", 700_000, 2_000_000, 0.05),
                new Gerente("Carla", 1_200_000, 300_000),
                new Vendedor("Dante", 700_000, 500_000, 0.08)
        };

        // UN bucle para TODA la empresa. ¿Nuevo tipo (Pasante, Socio)? Nueva
        // clase con su override, y este bucle sigue igual: cero ifs por tipo.
        double total = 0;
        for (Empleado empleado : nomina) {
            double sueldo = empleado.calcularSueldo();
            total += sueldo;
            System.out.printf("%-8s (%s) cobra $%,12.2f%n",
                    empleado.nombre, empleado.getClass().getSimpleName(), sueldo);
        }
        System.out.printf("Total de la nómina: $%,.2f%n", total);
    }
}

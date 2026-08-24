/*
 * ============================================================================
 * Módulo 07 – Polimorfismo | Ejercicio 5: Nómina polimórfica (desafío)
 * ============================================================================
 *
 * ENUNCIADO:
 *   Armá la nómina de una empresa con empleados de tres tipos y calculá el
 *   total a pagar recorriendo un Empleado[] con un solo bucle, mostrando el
 *   desglose de cada empleado.
 *
 * REQUISITOS:
 *   1. Empleado: atributos nombre y salarioBase; calcularSueldo() devuelve
 *      salarioBase.
 *   2. Vendedor extiende Empleado y agrega ventas y comision (porcentaje como
 *      fracción, ej. 0.05): su sueldo es salarioBase + ventas * comision.
 *      Sobrescribe calcularSueldo().
 *   3. Gerente extiende Empleado y agrega bono: su sueldo es salarioBase +
 *      bono. También sobrescribe.
 *   4. En main: Empleado[] nomina con al menos 4 empleados mezclados (de los
 *      tres tipos); un único bucle imprime nombre, tipo y sueldo de cada uno,
 *      acumula el total y al final muestra el total general.
 *
 * PISTAS:
 *   - Vendedor y Gerente SON empleados: heredan nombre y salarioBase; usá
 *     super(...) en sus constructores.
 *   - Adentro de los overrides, super.calcularSueldo() te da la parte base
 *     gratis: reutilizá en vez de repetir.
 *   - El bucle trabaja solo con Empleado: si escribís "if empleado es
 *     vendedor", volviste al if-else gigante del ejercicio anterior.
 *   - Formateá montos con printf (%,.2f te separa los miles).
 *
 * Ejecución:  java Ejercicio5NominaPolimorfica.java
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
            // TODO: sumale a la base (super.calcularSueldo()) las comisiones.
            return super.calcularSueldo();
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
            // TODO: sumale a la base el bono.
            return super.calcularSueldo();
        }
    }

    public static void main(String[] args) {
        Empleado[] nomina = {
                new Empleado("Ana", 800_000),
                new Vendedor("Bruno", 700_000, 2_000_000, 0.05),
                new Gerente("Carla", 1_200_000, 300_000),
                new Vendedor("Dante", 700_000, 500_000, 0.08)
        };

        // TODO: un solo bucle -> desglose por empleado + total acumulado,
        // y al final el total general de la nómina.
        System.out.println("(Falta implementar sueldos y el recorrido de la nómina)");
    }
}

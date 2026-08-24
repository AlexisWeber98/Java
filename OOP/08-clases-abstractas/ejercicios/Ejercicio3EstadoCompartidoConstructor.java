/*
 * ===========================================================================
 *  Ejercicio 3 — Estado compartido y constructor de la base
 *  Módulo 08 · Clases abstractas
 * ===========================================================================
 *
 *  ENUNCIADO
 *  Todo empleado tiene nombre y salarioBase. Esos datos viven UNA única vez
 *  en EmpleadoBase (campos protected) y se cargan a través de su constructor
 *  protegido. Cada subclase encadena con super(...) y la base ofrece el
 *  método común reciboDeSueldo(), que usa ese estado compartido.
 *
 *  REQUISITOS
 *  1. Escribí el constructor de Vendedor para que delegue en super(...).
 *  2. Implementá reciboDeSueldo() en EmpleadoBase: debe imprimir nombre y
 *     salarioBase leyendo los campos protegidos.
 *  3. Descomentá las líneas de Vendedor en main y verificá el resultado.
 *
 *  PISTAS
 *  - EmpleadoBase no tiene constructor sin parámetros, así que la primera
 *    línea de cada constructor hijo TIENE que ser super(nombre, salarioBase).
 *  - Si te olvidás, el compilador responde:
 *        error: constructor EmpleadoBase in class EmpleadoBase cannot be
 *               applied to given types
 *  - protected significa "visible para las subclases": el estado vive en la
 *    base y las hijas lo heredan sin volver a declararlo.
 *
 *  Ejecutá así:  java Ejercicio3EstadoCompartidoConstructor.java
 */

public class Ejercicio3EstadoCompartidoConstructor {

    static abstract class EmpleadoBase {
        protected final String nombre;
        protected final double salarioBase;

        // Constructor protegido: solo lo invocan las subclases vía super().
        protected EmpleadoBase(String nombre, double salarioBase) {
            this.nombre = nombre;
            this.salarioBase = salarioBase;
        }

        // TODO 2: reemplazá este cuerpo por un recibo legible que use
        //         nombre y salarioBase.
        void reciboDeSueldo() {
            System.out.println("[TODO: falta armar el recibo]");
        }
    }

    // Modelo a imitar: el constructor encadena con super(...) y no vuelve a
    // declarar ninguno de los campos de la base.
    static class Administrativo extends EmpleadoBase {
        Administrativo(String nombre, double salarioBase) {
            super(nombre, salarioBase);
        }
    }

    // TODO 1: descomentá esta clase y escribile su constructor, que debe
    // recibir (nombre, salarioBase) y delegar en super(...).
    //
    // static class Vendedor extends EmpleadoBase {
    //
    // }

    public static void main(String[] args) {
        Administrativo administrativo = new Administrativo("Ana", 850_000);
        administrativo.reciboDeSueldo();

        // TODO 3: descomentá al tener listo el constructor de Vendedor:
        //
        // Vendedor vendedor = new Vendedor("Bruno", 900_000);
        // vendedor.reciboDeSueldo();
    }
}

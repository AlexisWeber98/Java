/*
 * ============================================================================
 * Ejercicio 1 — Asociación simple: Cliente y su TarjetaFidelidad
 * ============================================================================
 *
 * ENUNCIADO:
 *   Modelá la relación entre Cliente y TarjetaFidelidad como una ASOCIACIÓN:
 *   el cliente "tiene una" tarjeta, pero la tarjeta le es entregada desde
 *   afuera y ambos objetos tienen ciclos de vida independientes.
 *
 *   La tarjeta NO se crea dentro del cliente: llega ya construida por el
 *   constructor. Si el cliente desaparece, la tarjeta puede seguir existiendo.
 *
 * REQUISITOS:
 *   1. TarjetaFidelidad: numero (String), puntos (int) y metodo acumular(int).
 *   2. Cliente: nombre (String) + referencia a su tarjeta, recibida por
 *      constructor (jamás instanciada adentro).
 *   3. Cliente.mostrarPuntos(): imprime los puntos accediendo a través de la
 *      referencia guardada.
 *   4. En main: crear la tarjeta primero, acumular puntos, crear el cliente,
 *      mostrar los puntos por medio del cliente y demostrar que la tarjeta
 *      sigue plenamente usable aunque se elimine la referencia al cliente.
 *
 * PISTAS:
 *   - La asociación se implementa como un campo común: la clase "conoce" al
 *     otro objeto pero no controla su ciclo de vida.
 *   - Fijate que ninguno de los dos es "parte" del otro: son objetos pares
 *     que simplemente se relacionan.
 */
public class Ejercicio1AsociacionSimple {

    static class TarjetaFidelidad {
        private final String numero;
        private int puntos;

        TarjetaFidelidad(String numero) {
            this.numero = numero;
        }

        void acumular(int puntosGanados) {
            // TODO: sumar puntosGanados al total acumulado
        }

        int getPuntos() {
            // TODO: devolver los puntos acumulados
            return 0;
        }

        String getNumero() {
            return numero;
        }
    }

    static class Cliente {
        private final String nombre;
        private final TarjetaFidelidad tarjeta;

        Cliente(String nombre, TarjetaFidelidad tarjeta) {
            this.nombre = nombre;
            // Clave de la asociación: la referencia llega de afuera, solo se guarda.
            this.tarjeta = tarjeta;
        }

        void mostrarPuntos() {
            // TODO: imprimir "<nombre> tiene <puntos> puntos (tarjeta <numero>)"
        }
    }

    public static void main(String[] args) {
        // La tarjeta nace ANTES que el cliente y vive por separado: eso es la asociación.
        TarjetaFidelidad tarjeta = new TarjetaFidelidad("TF-0001");
        tarjeta.acumular(100);
        tarjeta.acumular(50);

        Cliente cliente = new Cliente("Lucía Fernández", tarjeta);
        cliente.mostrarPuntos();

        // TODO: eliminá la referencia al cliente...
        // TODO: ...y acumulá puntos + mostrá que la tarjeta sigue operativa sola.
    }
}

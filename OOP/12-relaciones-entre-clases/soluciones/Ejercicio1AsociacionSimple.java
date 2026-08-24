/*
 * ============================================================================
 * Ejercicio 1 (SOLUCIÓN) — Asociación simple: Cliente y su TarjetaFidelidad
 * ============================================================================
 * Gemelo de ejercicios/Ejercicio1AsociacionSimple.java. La clase se llama
 * Solucion1AsociacionSimple (sin public) para poder compilar ejercicio y
 * solución juntos con un solo javac.
 *
 * CONCEPTO CLAVE:
 *   Asociación = "conoce a". El Cliente guarda la referencia de una tarjeta
 *   que le fue ENTREGADA desde afuera; ninguna clase crea ni destruye a la
 *   otra, así que los ciclos de vida son totalmente independientes.
 */
class Solucion1AsociacionSimple {

    static class TarjetaFidelidad {
        private final String numero;
        private int puntos;

        TarjetaFidelidad(String numero) {
            this.numero = numero;
        }

        void acumular(int puntosGanados) {
            puntos += puntosGanados;
        }

        int getPuntos() {
            return puntos;
        }

        String getNumero() {
            return numero;
        }
    }

    static class Cliente {
        private final String nombre;

        // Asociación: campo común que guarda la referencia recibida desde afuera.
        private final TarjetaFidelidad tarjeta;

        Cliente(String nombre, TarjetaFidelidad tarjeta) {
            this.nombre = nombre;
            // Clave de la asociación: la tarjeta NO se instancia acá, ya llegó construida.
            this.tarjeta = tarjeta;
        }

        void mostrarPuntos() {
            // El cliente accede a los datos de la tarjeta a través de su referencia.
            System.out.println(nombre + " tiene " + tarjeta.getPuntos()
                    + " puntos (tarjeta " + tarjeta.getNumero() + ")");
        }
    }

    public static void main(String[] args) {
        // 1) La tarjeta nace antes y por su cuenta: su ciclo de vida es propio.
        TarjetaFidelidad tarjeta = new TarjetaFidelidad("TF-0001");
        tarjeta.acumular(100);
        tarjeta.acumular(50);

        // 2) El cliente solo RECIBE la referencia: nunca hace new TarjetaFidelidad.
        Cliente cliente = new Cliente("Lucía Fernández", tarjeta);
        cliente.mostrarPuntos(); // 150 puntos

        // 3) Prueba de ciclos de vida independientes: "eliminamos" al cliente.
        cliente = null;

        // 4) La tarjeta sigue plenamente operativa sin su dueño registrado.
        tarjeta.acumular(25);
        System.out.println("La tarjeta " + tarjeta.getNumero()
                + " sigue viva sin cliente: " + tarjeta.getPuntos() + " puntos");
    }
}

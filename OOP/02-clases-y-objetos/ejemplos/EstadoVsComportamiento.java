/**
 * EstadoVsComportamiento.java
 *
 * Demo 1 del Módulo 02: un solo objeto cuyo estado evoluciona
 * a través de sus PROPIOS métodos. Nadie toca los campos desde afuera;
 * todos le piden al objeto que actúe.
 *
 * Ejecutar: java EstadoVsComportamiento.java
 */
public class EstadoVsComportamiento {

    // ---- ESTADO: lo que el objeto sabe ----
    String nombre;
    int bateria;      // porcentaje, arranca en algo razonable

    // ---- COMPORTAMIENTO: lo que el objeto hace con su estado ----

    void usar(int minutos) {
        int consumo = minutos / 2;
        if (bateria - consumo < 0) {
            System.out.println("[" + nombre + "] Batería insuficiente para " + minutos + " min.");
            return;
        }
        bateria -= consumo;
        System.out.println("[" + nombre + "] Usado " + minutos + " min. Batería: " + bateria + "%");
    }

    void cargar() {
        bateria = 100;
        System.out.println("[" + nombre + "] Cargado al 100%.");
    }

    void mostrarEstado() {
        System.out.println("[" + nombre + "] Estado actual -> batería: " + bateria + "%");
    }

    // ---- main: solo para probar la clase ----
    public static void main(String[] args) {
        EstadoVsComportamiento celu = new EstadoVsComportamiento();
        celu.nombre = "Pixel 9";
        celu.bateria = 80;

        System.out.println("=== Evolución del estado ===");
        celu.mostrarEstado();

        celu.usar(30);   // consume 15%
        celu.usar(60);   // consume 30%
        celu.usar(120);  // no alcanza: se defiende solo
        celu.mostrarEstado();

        celu.cargar();   // vuelve a 100%
        celu.mostrarEstado();

        System.out.println("\nMoraleja: el estado cambió SIEMPRE a través de sus métodos.");
    }
}

// Módulo 19 - La condición de carrera, en vivo
//
// Cinco hilos suman 1000 veces cada uno sobre un contador compartido.
// Matemática de primaria: 5000. Ejecutalo varias veces y mirá el resultado
// real. Spoiler: casi nunca da 5000.
//
// ¿Por qué? contador++ NO es atómico: son tres pasos (leer → sumar → escribir)
// que los hilos pueden entremezclar. Cuando dos leen el mismo valor antes de
// que alguno escriba, una suma se pierde para siempre.

public class CondicionDeCarreraVisible {

    /** Contador compartido SIN ninguna protección: el villano de la historia. */
    static class ContadorInseguro {
        int valor;

        void incrementar() {
            valor++; // leer → sumar uno → escribir. Tres pasos, cero coordinación.
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ContadorInseguro contador = new ContadorInseguro();
        int cantidadHilos = 5;
        int vueltasPorHilo = 1000;
        int esperado = cantidadHilos * vueltasPorHilo;

        System.out.println("Lanzando " + cantidadHilos + " hilos que incrementan "
                + vueltasPorHilo + " veces cada uno...");
        System.out.println("Sin sincronización alguna. Reza.");

        Thread[] equipo = new Thread[cantidadHilos];
        for (int i = 0; i < cantidadHilos; i++) {
            equipo[i] = new Thread(() -> {
                for (int vuelta = 0; vuelta < vueltasPorHilo; vuelta++) {
                    contador.incrementar();
                }
            }, "contador-" + i);
            equipo[i].start();
        }

        // Esperamos a todos antes de mirar el resultado.
        for (Thread hilo : equipo) {
            hilo.join();
        }

        int perdidas = esperado - contador.valor;

        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║  INFORME DE DESASTRE (condición de carrera) ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  Esperado:  " + esperado);
        System.out.println("║  Real:      " + contador.valor);
        System.out.println("║  Perdidas:  " + perdidas + " actualizaciones esfumadas");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("¿Qué pasó? Dos hilos leyeron el mismo valor al mismo tiempo,");
        System.out.println("ambos escribieron el mismo resultado, y una suma entera");
        System.out.println("desapareció sin dejar rastro. Nadie lanzó excepción.");
        System.out.println("Ese silencio es lo que vuelve peligrosa la carrera.");
        System.out.println();
        System.out.println("¿Te dio justo " + esperado + "? Suerte pura: ejecutalo otra vez,");
        System.out.println("el orden entre hilos cambia en cada corrida.");
    }
}

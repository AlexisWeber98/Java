/*
 * Módulo 10 · Ejemplo 1: enum básico.
 * Declara un conjunto cerrado de constantes, lo recorre con values()
 * y demuestra que comparar con == es seguro entre enums.
 */
public class SemaforoEnum {

    // El conjunto completo de estados del semáforo: tres, ni uno más.
    // Solo la JVM crea estas instancias; nadie puede hacer new Semaforo().
    enum Semaforo {
        ROJO,
        AMARILLO,
        VERDE
    }

    public static void main(String[] args) {
        System.out.println("Estados posibles del semáforo:");

        // values() devuelve todas las constantes en orden de declaración.
        for (Semaforo color : Semaforo.values()) {
            System.out.printf("  %s (posición %d)%n", color.name(), color.ordinal());
        }

        // == es seguro: cada constante es una única instancia compartida.
        Semaforo luzActual = Semaforo.VERDE;

        if (luzActual == Semaforo.VERDE) {
            System.out.println("\nLuz VERDE: avanzar.");
        } else {
            System.out.println("\nLuz distinta de verde: precaución o frenar.");
        }
    }
}

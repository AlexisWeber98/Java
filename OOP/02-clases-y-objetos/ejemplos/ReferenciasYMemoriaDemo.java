/**
 * ReferenciasYMemoriaDemo.java
 *
 * Demo 2 del Módulo 02: aliasing. Dos variables que apuntan al
 * MISMO objeto, y el contraste con los primitivos.
 *
 * MORALEJA (leéla al final): asignar un objeto NO copia el objeto,
 * copia la referencia. Dos etiquetas, una sola caja de zapatos.
 *
 * Ejecutar: java ReferenciasYMemoriaDemo.java
 */
public class ReferenciasYMemoriaDemo {

    static class Mochila {
        String color;
        int pesoGramos;

        void mostrar(String etiqueta) {
            System.out.println(etiqueta + " -> Mochila " + color + " (" + pesoGramos + " g)");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Parte 1: PRIMITIVOS (copiar valor) ===");
        int pesoA = 500;
        int pesoB = pesoA;   // copia el VALOR
        pesoB += 200;        // solo cambia pesoB

        System.out.println("pesoA = " + pesoA);   // 500, intacto
        System.out.println("pesoB = " + pesoB);   // 700

        System.out.println("\n=== Parte 2: OBJETOS (copiar referencia) ===");
        Mochila mochilaDeAna = new Mochila();
        mochilaDeAna.color = "roja";
        mochilaDeAna.pesoGramos = 800;

        // ¡Atención! No se creó una segunda mochila:
        // mochilaDeBruno ahora apunta a la MISMA mochila.
        Mochila mochilaDeBruno = mochilaDeAna;

        mochilaDeAna.mostrar("Antes del cambio   | vía Ana  ");
        mochilaDeBruno.mostrar("Antes del cambio   | vía Bruno");

        mochilaDeBruno.pesoGramos += 1000;      // Bruno carga la mochila...

        mochilaDeAna.mostrar("Después del cambio | vía Ana  ");  // ¡Ana también lo nota!
        mochilaDeBruno.mostrar("Después del cambio | vía Bruno");

        System.out.println("\n¿Son el mismo objeto? " + (mochilaDeAna == mochilaDeBruno));

        System.out.println("\n=== Parte 3: new SÍ crea otro objeto ===");
        Mochila mochilaNueva = new Mochila();
        mochilaNueva.color = "roja";
        mochilaNueva.pesoGramos = 1800;

        System.out.println("mochilaDeAna == mochilaNueva: "
                + (mochilaDeAna == mochilaNueva));  // false: == compara direcciones

        /*
         * ================== MORALEJA ==================
         * - Primitivo: la variable GUARDA el valor; asignar copia el valor.
         * - Objeto:     la variable GUARDA una dirección; asignar copia la dirección.
         * - Si querés un objeto independiente de verdad, necesitás OTRO `new`
         *   (una copia real es otro tema; por ahora, dos news).
         */
    }
}

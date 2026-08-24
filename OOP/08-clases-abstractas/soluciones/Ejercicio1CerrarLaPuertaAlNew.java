/*
 * ===========================================================================
 *  Ejercicio 1 — Cerrar la puerta al new · SOLUCIÓN
 *  Módulo 08 · Clases abstractas
 * ===========================================================================
 *
 *  Cambio clave: InstrumentoSonoro pasa a ser abstract. Sus subclases siguen
 *  funcionando exactamente igual; lo único que ya no se puede es instanciar
 *  la base directamente.
 *
 *  Ejecutá así:  java Ejercicio1CerrarLaPuertaAlNew.java
 */

// La clase principal de la solución se llama distinto (y sin public) para
// que ejercicios y soluciones puedan compilarse juntos sin choque de
// nombres. El lanzador java toma igualmente la primera clase del archivo.
class Solucion1CerrarLaPuertaAlNew {

    // SOLUCIÓN: la palabra abstract convierte a la clase en un molde.
    // Sigue teniendo constructor, campo y métodos concretos para que las
    // subclases los aprovechen.
    static abstract class InstrumentoSonoro {
        protected String nombre;

        InstrumentoSonoro(String nombre) {
            this.nombre = nombre;
        }

        void tocar() {
            System.out.println(nombre + ": suena su nota característica.");
        }
    }

    static class Guitarra extends InstrumentoSonoro {
        Guitarra() {
            super("Guitarra");
        }

        @Override
        void tocar() {
            System.out.println(nombre + ": rasguea sus seis cuerdas.");
        }
    }

    static class Flauta extends InstrumentoSonoro {
        Flauta() {
            super("Flauta");
        }

        @Override
        void tocar() {
            System.out.println(nombre + ": sopla una melodía dulce.");
        }
    }

    public static void main(String[] args) {
        // EXPERIMENTO resuelto: si intentamos crear la base...
        //
        //     InstrumentoSonoro intento = new InstrumentoSonoro("Intento");
        //
        // ...el compilador nos contesta:
        //
        //     error: InstrumentoSonoro is abstract; cannot be instantiated
        //
        // Es decir: la regla "esto es solo un molde" queda garantizada por el
        // compilador, no por un comentario ni por buena fe.

        InstrumentoSonoro[] banda = { new Guitarra(), new Flauta() };
        for (InstrumentoSonoro instrumento : banda) {
            instrumento.tocar();
        }

        // Clave del ejercicio: las subclases concretas se siguen creando sin
        // problema. La única puerta que cerramos es la del new sobre la clase
        // abstracta; el polimorfismo (el arreglo de tipo InstrumentoSonoro)
        // sigue funcionando intacto.
    }
}

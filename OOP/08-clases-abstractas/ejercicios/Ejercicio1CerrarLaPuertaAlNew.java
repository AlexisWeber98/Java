/*
 * ===========================================================================
 *  Ejercicio 1 — Cerrar la puerta al new
 *  Módulo 08 · Clases abstractas
 * ===========================================================================
 *
 *  ENUNCIADO
 *  InstrumentoSonoro hoy es una clase común: cualquier código puede escribir
 *  new InstrumentoSonoro(...) y crear un instrumento "genérico" que no
 *  representa nada real del dominio. En nuestra banda existen la Guitarra y
 *  la Flauta; la clase base existe para compartir comportamiento, no para
 *  instanciarse directamente.
 *
 *  REQUISITOS
 *  1. Convertí InstrumentoSonoro en clase abstracta.
 *  2. Eliminá (o comentá) la creación del instrumento genérico en main.
 *  3. Descomentá el bloque marcado como EXPERIMENTO, recompilá y leé con
 *     atención el error que tira el compilador.
 *  4. Verificá que la banda siga sonando igual que antes.
 *
 *  PISTAS
 *  - La palabra clave es abstract, junto a class en la declaración.
 *  - Una clase abstracta conserva constructores, campos y métodos concretos;
 *    lo único que pierde es el permiso para hacerle new directamente.
 *  - El error esperado tiene esta forma:
 *        error: InstrumentoSonoro is abstract; cannot be instantiated
 *
 *  Ejecutá así:  java Ejercicio1CerrarLaPuertaAlNew.java
 */

public class Ejercicio1CerrarLaPuertaAlNew {

    // Clases de apoyo anidadas: así cada archivo del curso es autónomo.
    static class InstrumentoSonoro {
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
        // Problema del día: un instrumento genérico que no existe en la vida
        // real y que igual se deja crear.
        InstrumentoSonoro generico = new InstrumentoSonoro("Genérico");
        generico.tocar();

        // EXPERIMENTO (requisito 3): descomentá estas líneas DESPUÉS de hacer
        // el TODO 1 y mirá qué responde el compilador:
        //
        // InstrumentoSonoro intento = new InstrumentoSonoro("Intento");
        // intento.tocar();

        InstrumentoSonoro[] banda = { new Guitarra(), new Flauta() };
        for (InstrumentoSonoro instrumento : banda) {
            instrumento.tocar();
        }
    }
}

/*
 * ============================================================================
 * Ejercicio 1: Override Detective
 * ============================================================================
 *
 * ENUNCIADO:
 * Tenemos un bug clásico de producción: Perro "cree" que sobrescribe
 * describir(), pero el método tiene una letra de más (describirr). Java no
 * avisa nada: simplemente tu Perro hereda la versión genérica y nadie se
 * entera hasta que un cliente se queja.
 *
 * REQUISITOS:
 * 1. Anotá con @Override los métodos que realmente sobrescriben.
 * 2. Compilá: el compilador ahora DEBE gritarte dónde está el typo.
 * 3. Corregí el nombre del método para que el error desaparezca.
 * 4. Ejecutá y verificá que mascota.describir() muestre la versión de Perro.
 *
 * PISTAS:
 * - @Override no cambia el comportamiento: es un pacto con el compilador
 *   ("si esto no sobrescribe nada, rompeme la compilación").
 * - El método anotado debe existir en la superclase EXACTAMENTE con ese
 *   nombre y esa firma.
 * - Acostumbrate a anotar SIEMPRE cada sobrescritura: es documentación
 *   ejecutable, gratis.
 */
public class Ejercicio1OverrideDetective {

    static class Animal {
        void describir() {
            System.out.println("Soy un animal genérico.");
        }
    }

    static class Perro extends Animal {
        // Ojo acá: la intención era sobrescribir describir()...
        void describirr() {
            System.out.println("Guau. Soy un perro que se describe solo.");
        }
    }

    public static void main(String[] args) {
        Animal mascota = new Perro();

        // Esperado: la voz del perro. Real (por ahora): el animal genérico.
        mascota.describir();

        // TODO 1: agregá @Override sobre describir() de Perro (y pensá por qué
        //         NO va sobre describir() de Animal).
        // TODO 2: compilá y leé el error: "method does not override...".
        // TODO 3: corregí describirr -> describir, recompilá y ejecutá.
    }
}

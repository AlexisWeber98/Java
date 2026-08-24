/*
 * =============================================================================
 * Ejercicio 3 — Redefinir métodos con @Override
 * Módulo 06 · Herencia
 * =============================================================================
 *
 * ENUNCIADO
 * Todo Animal sabe describirse. Un Perro hace lo mismo PERO agrega su toque
 * canino. Tu misión: redefinir describir() en Perro, reutilizando primero la
 * versión del padre con super.describir().
 *
 * REQUISITOS
 *   1. Redefiní describir() en Perro.
 *   2. Adentro, llamá PRIMERO a super.describir() y después agregá una línea
 *      propia de perro (por ejemplo: "...y como soy un perro, ladro: guau").
 *   3. Anotá el método con @Override.
 *   4. EXPERIMENTO OBLIGATORIO: escribí a propósito el nombre mal
 *      (describiro()) CON @Override y compilá. Leé el error del compilador.
 *      Después corregilo. Eso es @Override trabajando para vos.
 *
 * PISTAS
 *   - super.metodo() ejecuta la versión del padre; podés seguir escribiendo
 *     después de esa llamada. Extender NO es copiar y pegar.
 *   - Sin @Override, un typo como describiro() compila feliz: Java cree que
 *     creaste un método NUEVO y tu redefinición nunca se entera.
 *   - Con @Override, el compilador te avisa al instante: "eso no sobrescribe
 *     nada". Un error de tipeo muere antes de nacer.
 * =============================================================================
 */
public class Ejercicio3RedefinirConOverride {

    static class Animal {
        String nombre;

        Animal(String nombre) {
            this.nombre = nombre;
        }

        void describir() {
            System.out.println("Soy " + nombre + ", un animal.");
        }
    }

    static class Perro extends Animal {
        Perro(String nombre) {
            super(nombre);
        }

        // TODO 1: redefiní describir() acá:
        //   1) anotalo con @Override,
        //   2) llamá primero a super.describir(),
        //   3) agregá tu línea perruna debajo.

        // TODO 2 (EXPERIMENTO): cambiá el nombre del método por describiro()
        // dejando el @Override. Compilá. ¿Qué mensaje recibiste?
        // Anotalo en un comentario y volvé todo a su lugar.
    }

    public static void main(String[] args) {
        Perro perro = new Perro("Firulais");
        perro.describir();

        // TODO EXTRA: ahora probá el POLIMORFISMO. Cambiá la declaración a:
        //   Animal mascota = new Perro("Firulais");
        // Ejecutá de nuevo: ¿qué versión de describir() corre?
        // Pista: el tipo de la REFERENCIA decide qué podés llamar;
        // el tipo del OBJETO decide quién responde.
    }
}

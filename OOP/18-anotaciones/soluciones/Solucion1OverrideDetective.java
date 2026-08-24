/*
 * ============================================================================
 * Solución 1: Override Detective
 * ============================================================================
 *
 * La anotación @Override convierte un bug silencioso en un error de
 * compilación. Sin ella, describirr() era solo "otro método nuevo" y el
 * polimorfismo elegía la versión de Animal. Con ella, javac compara tu
 * método contra la superclase y falla si no encuentra coincidencia exacta.
 *
 * Este patrón atrapa errores reales de producción: renombrás un método en
 * la base, el IDE no actualiza una subclase, y sin @Override nadie se
 * entera hasta runtime. Con @Override, se entera el compilador, que es
 * mucho más barato.
 */
public class Solucion1OverrideDetective {

    static class Animal {
        void describir() {
            System.out.println("Soy un animal genérico.");
        }
    }

    static class Perro extends Animal {
        // Typo corregido. Ahora @Override tiene con qué cumplir su promesa:
        // si mañana alguien renombra describir() en Animal, acá explota la
        // compilación y no el cliente.
        @Override
        void describir() {
            System.out.println("Guau. Soy un perro que se describe solo.");
        }
    }

    public static void main(String[] args) {
        Animal mascota = new Perro();

        // Polimorfismo real: se ejecuta la versión de Perro.
        mascota.describir();

        // Nota: describir() de Animal NO lleva @Override porque introduce un
        // método nuevo; no sobrescribe nada. Esa distinción es justo lo que
        // la anotación le enseña a vigilar al compilador.
    }
}

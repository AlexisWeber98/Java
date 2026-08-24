import java.util.ArrayList;
import java.util.List;

/**
 * Módulo 12 — Agregación: Equipo tiene Jugadores.
 *
 * AGREGACIÓN = HAS-A donde la parte SOBREVIVE al todo.
 * Los jugadores llegan de afuera (ya existían), se fichan al equipo
 * y siguen existiendo aunque el equipo se disuelva.
 *
 * UML: rombo VACÍO del lado del Equipo.
 */
public class AgregacionEquipoJugador {

    static class Jugador {
        private final String nombre;
        private int numeroCamiseta;

        Jugador(String nombre, int numeroCamiseta) {
            this.nombre = nombre;
            this.numeroCamiseta = numeroCamiseta;
        }

        void setNumeroCamiseta(int numeroCamiseta) {
            this.numeroCamiseta = numeroCamiseta;
        }

        @Override
        public String toString() {
            return nombre + " (#" + numeroCamiseta + ")";
        }
    }

    static class Equipo {
        private final String nombre;
        private final List<Jugador> jugadores = new ArrayList<>();

        Equipo(String nombre) {
            this.nombre = nombre;
        }

        // La parte LLEGA DE AFUERA: el equipo no la crea.
        void fichar(Jugador jugador) {
            jugadores.add(jugador);
            System.out.println("  " + jugador + " fichó por " + nombre);
        }

        void disolver() {
            System.out.println("  El equipo " + nombre + " se disolvió.");
            jugadores.clear(); // solo soltamos las referencias; los jugadores NO mueren
        }
    }

    public static void main(String[] args) {
        // Los jugadores existen ANTES que el equipo:
        Jugador ana = new Jugador("Ana", 10);
        Jugador bruno = new Jugador("Bruno", 7);

        Equipo riverPlate = new Equipo("Los Cóndores");
        riverPlate.fichar(ana);
        riverPlate.fichar(bruno);

        // El mismo jugador puede pertenecer a otro equipo después:
        System.out.println("\n--- Temporada termina ---");
        riverPlate.disolver();

        // Ana y Bruno siguen vivos y fichan en otro lado:
        ana.setNumeroCamiseta(99);
        Equipo halcones = new Equipo("Los Halcones");
        halcones.fichar(ana);
        halcones.fichar(bruno);

        System.out.println("\n  Prueba de vida independiente: " + ana + " sigue en pie.");
    }
}

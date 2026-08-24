import java.util.ArrayList;
import java.util.List;

/**
 * Módulo 12 — Composición: Casa y Habitaciones.
 *
 * COMPOSICIÓN = HAS-A donde la parte MUERE con el todo.
 * La casa crea sus habitaciones DENTRO de su propia construcción,
 * nunca las recibe desde afuera y nunca las comparte.
 *
 * UML: rombo LLENO del lado de la Casa.
 */
public class ComposicionCasaHabitacion {

    // Habitación no tiene constructor público "para el mundo": nace para su casa.
    static class Habitacion {
        private final String nombre;
        private final double superficieM2;

        Habitacion(String nombre, double superficieM2) {
            this.nombre = nombre;
            this.superficieM2 = superficieM2;
        }

        String getNombre() {
            return nombre;
        }

        double getSuperficieM2() {
            return superficieM2;
        }
    }

    static class Casa {
        private final String direccion;
        private final List<Habitacion> habitaciones = new ArrayList<>();

        Casa(String direccion) {
            this.direccion = direccion;
            // COMPOSICIÓN: la casa CREA sus partes internamente.
            habitaciones.add(new Habitacion("cocina", 12.0));
            habitaciones.add(new Habitacion("dormitorio", 18.5));
            habitaciones.add(new Habitacion("baño", 6.0));
        }

        /**
         * Nunca devolvemos la lista interna: exponerla permitiría a otros
         * meter o sacar habitaciones y rompería la composición.
         * Devolvemos una vista de solo lectura (copia).
         */
        List<String> nombresDeHabitaciones() {
            List<String> nombres = new ArrayList<>();
            for (Habitacion habitacion : habitaciones) {
                nombres.add(habitacion.getNombre());
            }
            return nombres;
        }

        double superficieTotal() {
            double total = 0;
            for (Habitacion habitacion : habitaciones) {
                total += habitacion.getSuperficieM2();
            }
            return total;
        }

        void demoler() {
            System.out.println("  Se demolió la casa de " + direccion + ".");
            habitaciones.clear(); // las habitaciones mueren con la casa
        }
    }

    public static void main(String[] args) {
        Casa casa = new Casa("Av. Siempre Viva 742");

        System.out.println("=== La casa compone sus habitaciones ===");
        System.out.println("  Habitaciones: " + casa.nombresDeHabitaciones());
        System.out.printf("  Superficie total: %.1f m2%n", casa.superficieTotal());

        // No existe forma de crear una Habitacion desde afuera
        // ni de inyectarla en la casa: la composición lo impide por diseño.

        System.out.println("\n=== Fin del ciclo de vida del todo ===");
        casa.demoler(); // conceptualmente, las habitaciones desaparecen acá

        Casa nuevaCasa = new Casa("Calle Falsa 123");
        System.out.println("  Nueva casa con habitaciones propias: "
                + nuevaCasa.nombresDeHabitaciones());
    }
}

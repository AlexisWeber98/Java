import java.util.ArrayList;
import java.util.List;

/**
 * Módulo 12 — Dependencia vs Asociación.
 *
 * La DEPENDENCIA es transitoria: el objeto llega por parámetro, se usa y se olvida.
 * La ASOCIACIÓN es duradera: un campo guarda la referencia a largo plazo,
 * pero cada objeto conserva su propio ciclo de vida.
 */
public class DependenciaYAsociacion {

    static class Asesor {
        private final String nombre;

        Asesor(String nombre) {
            this.nombre = nombre;
        }

        String getNombre() {
            return nombre;
        }
    }

    static class Cliente {
        private final String nombre;
        private Asesor asesor; // ASOCIACIÓN: referencia duradera en un campo

        Cliente(String nombre) {
            this.nombre = nombre;
        }

        void asignarAsesor(Asesor nuevo) {
            this.asesor = nuevo;
        }

        String getNombre() {
            return nombre;
        }

        Asesor getAsesor() {
            return asesor;
        }
    }

    /**
     * DEPENDENCIA: el reporteador usa al cliente solo dentro del método.
     * No guarda ninguna referencia: cuando el método termina, no queda rastro.
     */
    static class GeneradorDeReportes {
        void exportar(Cliente cliente) {
            System.out.println("  Exportando reporte de " + cliente.getNombre() + "...");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Dependencia: uso transitorio ===");
        GeneradorDeReportes generador = new GeneradorDeReportes();
        generador.exportar(new Cliente("Lucía"));   // vive solo durante la llamada
        generador.exportar(new Cliente("Martín"));
        System.out.println("  El generador no recuerda a ningún cliente.");

        System.out.println("\n=== Asociación: conocimiento duradero ===");
        Asesor asesorOriginal = new Asesor("Carla");
        Asesor asesorNuevo = new Asesor("Diego");

        Cliente cliente = new Cliente("Sofía");
        cliente.asignarAsesor(asesorOriginal);
        System.out.println("  Sofía -> asesor " + cliente.getAsesor().getNombre());

        // El cliente puede cambiar de asesor sin dejar de existir:
        cliente.asignarAsesor(asesorNuevo);
        System.out.println("  Sofía -> asesor " + cliente.getAsesor().getNombre());

        // Y cada objeto tiene ciclo de vida independiente:
        List<Cliente> cartera = new ArrayList<>();
        cartera.add(cliente);
        asesorOriginal = null; // Carla deja la empresa...
        System.out.println("  Carla se fue, pero Sofía sigue viva con su nuevo asesor "
                + cliente.getAsesor().getNombre());
        System.out.println("  Clientes activos: " + cartera.size());
    }
}

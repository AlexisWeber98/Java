/*
 * ============================================================================
 * Solución 4 — Cambiar de adaptador sin tocar el dominio
 * ============================================================================
 * Puntos clave:
 * - El mismo escenario corre DOS VECES con dos adaptadores distintos y la
 *   salida tiene exactamente la misma forma: eso demuestra que dominio y
 *   casos de uso no saben (ni les importa) dónde viven los datos.
 * - Dominio, puerto, adaptador de memoria y caso de uso: CERO líneas cambiadas
 *   respecto del ejercicio. Todo el cambio vive en los adaptadores y en el main.
 * - El adaptador de archivo simulado hace round-trip real: guarda regenera el
 *   texto, leer SIEMPRE parsea ese texto.
 *
 * CÓMO COMPILAR Y CORRER (desde soluciones/):
 *   javac *.java && java Ejercicio4CambiarAdaptadorSinTocarDominio
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Ejercicio4CambiarAdaptadorSinTocarDominio {

    // ===== DOMINIO (intacto) ==================================================
    record Producto(String sku, String nombre, double precio) {}

    // ===== PUERTO (intacto) ===================================================
    interface RepositorioProductos {
        void guardar(Producto producto);
        Producto buscarPorSku(String sku);
        List<Producto> todos();
    }

    // ===== ADAPTADOR 1: en memoria (intacto) ==================================
    static class RepositorioProductosEnMemoria implements RepositorioProductos {
        // TreeMap: mismo criterio de orden (SKU) que el adaptador de archivo,
        // así ambas lecturas son deterministas e intercambiables.
        private final Map<String, Producto> porSku = new java.util.TreeMap<>();

        @Override
        public void guardar(Producto producto) {
            porSku.put(producto.sku(), producto);
        }

        @Override
        public Producto buscarPorSku(String sku) {
            return porSku.get(sku);
        }

        @Override
        public List<Producto> todos() {
            return List.copyOf(porSku.values());
        }
    }

    // ===== ADAPTADOR 2: archivo simulado (el nuevo) ===========================
    static class RepositorioProductosEnArchivoSimulado implements RepositorioProductos {
        // TreeMap: al regenerar el texto queda ordenado por SKU -> determinista.
        private final TreeMap<String, Producto> cache = new TreeMap<>();
        private String textoPersistido = "";

        @Override
        public void guardar(Producto producto) {
            cache.put(producto.sku(), producto);
            regenerarTexto();
        }

        @Override
        public Producto buscarPorSku(String sku) {
            for (Producto p : leerDesdeTexto()) {
                if (p.sku().equals(sku)) {
                    return p;
                }
            }
            return null;
        }

        @Override
        public List<Producto> todos() {
            return leerDesdeTexto();
        }

        private void regenerarTexto() {
            StringBuilder sb = new StringBuilder();
            for (Producto p : cache.values()) {
                sb.append(p.sku()).append('|')
                  .append(p.nombre()).append('|')
                  .append(p.precio()).append('\n');
            }
            textoPersistido = sb.toString();
        }

        private List<Producto> leerDesdeTexto() {
            List<Producto> leidos = new ArrayList<>();
            for (String linea : textoPersistido.split("\n")) {
                if (linea.isBlank()) {
                    continue;
                }
                String[] partes = linea.split("\\|");
                leidos.add(new Producto(partes[0], partes[1], Double.parseDouble(partes[2])));
            }
            return leidos;
        }

        String snapshot() {
            return textoPersistido;
        }
    }

    // ===== CASO DE USO (intacto) ==============================================
    static class ListadoDeProductos {
        private final RepositorioProductos repositorio;

        ListadoDeProductos(RepositorioProductos repositorio) {
            this.repositorio = repositorio;
        }

        List<Producto> ejecutar() {
            return repositorio.todos();
        }
    }

    // ===== COMPOSITION ROOT ====================================================
    public static void main(String[] args) {
        System.out.println("=== Escenario A: adaptador EN MEMORIA ===");
        RepositorioProductosEnMemoria enMemoria = new RepositorioProductosEnMemoria();
        List<Producto> desdeMemoria = escenarioCompleto(enMemoria);

        System.out.println();
        System.out.println("=== Escenario B: EL SWAP — adaptador EN ARCHIVO SIMULADO ===");
        // La línea clave sería esta (una sola línea cambia respecto del escenario A):
        //   RepositorioProductos repositorio = new RepositorioProductosEnArchivoSimulado();
        RepositorioProductosEnArchivoSimulado enArchivo = new RepositorioProductosEnArchivoSimulado();
        List<Producto> desdeArchivo = escenarioCompleto(enArchivo);

        System.out.println("--- snapshot persistido (texto plano) ---");
        System.out.print(enArchivo.snapshot());

        System.out.println();
        if (desdeMemoria.equals(desdeArchivo)) {
            System.out.println("[OK] ambos adaptadores devuelven lo mismo: son intercambiables.");
        } else {
            System.out.println("[ERROR] los adaptadores NO devuelven lo mismo.");
            System.exit(1);
        }
    }

    // Sembrar + listar usando SOLO el puerto: funciona igual para cualquier
    // adaptador. Esa es la prueba de sustituibilidad.
    private static List<Producto> escenarioCompleto(RepositorioProductos repositorio) {
        sembrar(repositorio);
        List<Producto> productos = new ListadoDeProductos(repositorio).ejecutar();
        imprimir(productos);
        return productos;
    }

    private static void sembrar(RepositorioProductos repositorio) {
        repositorio.guardar(new Producto("TE-01", "Teclado mecanico", 45000.0));
        repositorio.guardar(new Producto("MO-05", "Mouse inalambrico", 22000.0));
        repositorio.guardar(new Producto("MO-07", "Monitor 24 pulgadas", 315000.0));
    }

    private static void imprimir(List<Producto> productos) {
        System.out.printf("%-8s %-22s %12s%n", "SKU", "NOMBRE", "PRECIO");
        for (Producto p : productos) {
            System.out.printf("%-8s %-22s %12.2f%n", p.sku(), p.nombre(), p.precio());
        }
    }
}

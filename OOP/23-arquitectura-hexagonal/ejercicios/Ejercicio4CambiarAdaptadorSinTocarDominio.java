/*
 * ============================================================================
 * Ejercicio 4 — Cambiar de adaptador sin tocar el dominio
 * ============================================================================
 *
 * ENUNCIADO
 * Tenés un sistema chico FUNCIONANDO: catálogo de productos en memoria y un
 * caso de uso que lista el catálogo. Tu misión:
 *
 *   1) Implementar RepositorioProductosEnArchivoSimulado: "persiste" cada
 *      cambio como texto plano dentro de un String (líneas con formato
 *      sku|nombre|precio) y lee SIEMPRE parseando ese String (round-trip
 *      real, sin java.io todavía).
 *   2) Enchufarlo en el composition root cambiando EXACTAMENTE UNA línea.
 *
 * La prueba real de la arquitectura: Producto, RepositorioProductos,
 * RepositorioProductosEnMemoria y ListadoDeProductos quedan INTACTOS.
 *
 * REQUISITOS
 * - El adaptador nuevo implementa el MISMO puerto que el de memoria.
 * - guardar() regenera el texto persistido; buscarPorSku() y todos() leen
 *   parseando el texto (no vale leer del mapa en memoria).
 * - snapshot() te permite inspeccionar el "archivo" resultante.
 * - El único lugar del programa que conoce clases concretas es el main
 *   (composition root): ahí ocurre el swap de una línea.
 *
 * PISTAS
 * - split("\\|") porque | es un carácter especial de las regex.
 * - Si tu caso de uso necesitó cambios para aceptar el adaptador nuevo,
 *   hay un problema de diseño: revisá de qué depende realmente.
 * - Ordenar por SKU al regenerar el texto hace determinista el snapshot.
 *
 * CÓMO COMPILAR Y CORRER (desde esta carpeta):
 *   javac *.java && java Ejercicio4CambiarAdaptadorSinTocarDominio
 */
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Ejercicio4CambiarAdaptadorSinTocarDominio {

    // ===== DOMINIO ===========================================================
    record Producto(String sku, String nombre, double precio) {}

    // ===== PUERTO SALIDA (definido por la aplicación) ========================
    interface RepositorioProductos {
        void guardar(Producto producto);
        Producto buscarPorSku(String sku);
        List<Producto> todos();
    }

    // ===== ADAPTADOR 1: en memoria (ya funciona, NO lo toques) ===============
    static class RepositorioProductosEnMemoria implements RepositorioProductos {
        // TreeMap: orden por SKU determinista, igual que exigimos al de archivo.
        private final Map<String, Producto> porSku = new TreeMap<>();

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

    // ===== ADAPTADOR 2: archivo simulado (TU trabajo) =========================
    static class RepositorioProductosEnArchivoSimulado implements RepositorioProductos {
        private final TreeMap<String, Producto> cache = new TreeMap<>();
        private String textoPersistido = "";

        @Override
        public void guardar(Producto producto) {
            // TODO: actualizá el cache y regenerá textoPersistido con una
            //  línea "sku|nombre|precio" por producto, ordenado por SKU.
            throw new UnsupportedOperationException("TODO: implementá guardar");
        }

        @Override
        public Producto buscarPorSku(String sku) {
            // TODO: leé desde textoPersistido (parseá las líneas).
            throw new UnsupportedOperationException("TODO: implementá buscarPorSku");
        }

        @Override
        public List<Producto> todos() {
            // TODO: idem: PARSEÁ el texto, no devolvás el cache directo.
            throw new UnsupportedOperationException("TODO: implementá todos");
        }

        String snapshot() {
            return textoPersistido;
        }
    }

    // ===== CASO DE USO (puro: solo conoce el puerto) ==========================
    static class ListadoDeProductos {
        private final RepositorioProductos repositorio;

        ListadoDeProductos(RepositorioProductos repositorio) {
            this.repositorio = repositorio;
        }

        List<Producto> ejecutar() {
            return repositorio.todos();
        }
    }

    // ===== COMPOSITION ROOT ===================================================
    public static void main(String[] args) {
        // >>> Esta es LA línea del swap. Cambiala por el adaptador nuevo. <<<
        RepositorioProductos repositorio = new RepositorioProductosEnMemoria();

        sembrar(repositorio);
        imprimir(new ListadoDeProductos(repositorio).ejecutar());
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

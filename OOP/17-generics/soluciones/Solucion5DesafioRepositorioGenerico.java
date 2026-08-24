/*
 * ============================================================================
 *  Ejercicio 5 — Solución: Repositorio<T, ID> genérico en memoria
 * ============================================================================
 *
 *  IDEA CLAVE: dos parámetros de tipo con roles distintos — T es el QUÉ se
 *  guarda, ID es la CÓMO se encuentra. El truco del contador delega en un
 *  IntFunction<ID> la fabricación de ids: la política de ids vive FUERA del
 *  repositorio, así el mismo código sirve para Integer, String o lo que venga.
 *
 *  MIRÁ EL FUTURO: este contrato Repositorio<T, ID> con implementación
 *  intercambiable reaparece tal cual en el Módulo 22. Lo único que cambia
 *  ahí es el backend (base de datos en vez de un mapa): la interfaz y todos
 *  sus usuarios ni se enteran. Eso es programar contra abstracciones.
 * ============================================================================
 */
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

public class Solucion5DesafioRepositorioGenerico {

    /** El contrato. Quien usa esto no sabe (ni le importa) si hay un mapa o una BD detrás. */
    interface Repositorio<T, ID> {
        ID guardar(T elemento);
        T buscarPorId(ID id);
        List<T> listarTodos();
        boolean eliminar(ID id);
    }

    /**
     * Implementación en memoria. LinkedHashMap para conservar el orden de
     * inserción al listar (HashMap no garantiza orden y la demo queda fea).
     */
    static class RepositorioEnMemoria<T, ID> implements Repositorio<T, ID> {
        private final Map<ID, T> datos = new LinkedHashMap<>();
        private final IntFunction<ID> generadorDeIds;   // fábrica de ids inyectada
        private int secuencia = 0;                      // el contador autoincremental

        RepositorioEnMemoria(IntFunction<ID> generadorDeIds) {
            this.generadorDeIds = generadorDeIds;
        }

        @Override
        public ID guardar(T elemento) {
            ID nuevoId = generadorDeIds.apply(++secuencia);   // TRUCO: 1, 2, 3... tipado a gusto
            datos.put(nuevoId, elemento);
            return nuevoId;
        }

        @Override
        public T buscarPorId(ID id) {
            return datos.get(id);          // null si no existe: sin excepciones sorpresa
        }

        @Override
        public List<T> listarTodos() {
            return List.copyOf(datos.values());   // copia defensiva: nadie toca mi mapa
        }

        @Override
        public boolean eliminar(ID id) {
            return datos.remove(id) != null;      // false si el id no estaba
        }
    }

    // --- Dominio mínimo, sin lógica de persistencia: eso ya lo aporta el repo ---

    static class Producto {
        private final String nombre;
        private final double precio;

        Producto(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
        }

        @Override
        public String toString() {
            return nombre + " ($" + precio + ")";
        }
    }

    static class Cliente {
        private final String nombre;
        private final String email;

        Cliente(String nombre, String email) {
            this.nombre = nombre;
            this.email = email;
        }

        @Override
        public String toString() {
            return nombre + " <" + email + ">";
        }
    }

    public static void main(String[] args) {
        // ==================== PRODUCTOS: id Integer ====================
        System.out.println("========== REPOSITORIO DE PRODUCTOS (ID: Integer) ==========");
        Repositorio<Producto, Integer> productos =
                new RepositorioEnMemoria<>(n -> n);   // el número crudo ES el id

        Integer idTeclado = productos.guardar(new Producto("Teclado mecánico", 45999.90));
        Integer idMonitor = productos.guardar(new Producto("Monitor 24\"", 185000.00));
        Integer idMouse   = productos.guardar(new Producto("Mouse inalámbrico", 12350.75));
        System.out.println("IDs autoincrementales asignados: " + idTeclado + ", " + idMonitor + ", " + idMouse);

        System.out.println("\n-- LISTAR TODOS --");
        productos.listarTodos().forEach(p -> System.out.println("   " + p));

        System.out.println("\n-- BUSCAR POR ID --");
        System.out.println("id 2   -> " + productos.buscarPorId(2));
        System.out.println("id 99  -> " + productos.buscarPorId(99) + "   (no existe: null, sin drama)");

        System.out.println("\n-- ELIMINAR --");
        System.out.println("eliminar(2) -> " + productos.eliminar(idMonitor));
        System.out.println("eliminar(2) otra vez -> " + productos.eliminar(idMonitor) + "   (ya no estaba)");

        System.out.println("\n-- LISTAR DE NUEVO --");
        productos.listarTodos().forEach(p -> System.out.println("   " + p));

        // ==================== CLIENTES: id String ====================
        System.out.println("\n========== REPOSITORIO DE CLIENTES (ID: String) ==========");
        Repositorio<Cliente, String> clientes =
                new RepositorioEnMemoria<>(n -> String.format("CLI-%03d", n));   // misma clase, otra política

        String idAna   = clientes.guardar(new Cliente("Ana García", "ana@correo.com"));
        clientes.guardar(new Cliente("Bruno Díaz", "bruno@correo.com"));
        System.out.println("Primer id generado: " + idAna + "  ← ¡el contador también sirve para Strings!");

        System.out.println("-- LISTAR TODOS --");
        clientes.listarTodos().forEach(c -> System.out.println("   " + c));

        System.out.println("\nBuscar " + idAna + " -> " + clientes.buscarPorId(idAna));

        /*
         * Resumen del desafío: escribiste UNA estructura de datos y la usaste
         * con dos dominios distintos y dos políticas de id distintas, sin
         * duplicar una sola línea de lógica de persistencia. En el Módulo 22
         * este mismo contrato vuelve con base de datos detrás.
         */
    }
}

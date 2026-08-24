/*
 * ============================================================================
 *  Solución 3 — CRUD completo de productos
 * ============================================================================
 *
 *  Ejecutar (desde este directorio soluciones/):
 *    java -cp "../lib/h2.jar" Solucion3CrudProductos.java
 *
 *  O desde el directorio raíz del módulo:
 *    java -cp "lib/h2.jar" soluciones/Solucion3CrudProductos.java
 * ============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Solucion3CrudProductos {

    record Producto(int id, String nombre, double precio) {
    }

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:ejercicio3;DB_CLOSE_DELAY=-1";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {

            try (Statement sentencia = conexion.createStatement()) {
                sentencia.execute("CREATE TABLE productos ("
                        + "id INT PRIMARY KEY, "
                        + "nombre VARCHAR(80), "
                        + "precio DOUBLE)");
            }

            System.out.println("--- INSERT ---");
            insertar(conexion, new Producto(1, "Teclado mecánico", 18500.0));
            insertar(conexion, new Producto(2, "Mouse inalámbrico", 9200.0));
            insertar(conexion, new Producto(3, "Monitor 24\"", 210000.0));

            System.out.println("\n--- LISTADO inicial ---");
            listar(conexion);

            System.out.println("\n--- UPDATE precio del producto 2 → 8700.0 ---");
            int filas = actualizarPrecio(conexion, 2, 8700.0);
            System.out.println("Filas actualizadas: " + filas);

            System.out.println("\n--- DELETE producto 1 ---");
            filas = eliminar(conexion, 1);
            System.out.println("Filas eliminadas: " + filas);

            System.out.println("\n--- DELETE de id inexistente (99) ---");
            System.out.println("Filas eliminadas: " + eliminar(conexion, 99));

            System.out.println("\n--- LISTADO final ---");
            listar(conexion);

        } catch (SQLException excepcion) {
            System.err.println("Error de base de datos: " + excepcion.getMessage());
        }
    }

    static int insertar(Connection conexion, Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (id, nombre, precio) VALUES (?, ?, ?)";
        try (PreparedStatement insertar = conexion.prepareStatement(sql)) {
            insertar.setInt(1, producto.id());
            insertar.setString(2, producto.nombre());
            insertar.setDouble(3, producto.precio());
            return insertar.executeUpdate();
        }
    }

    static int actualizarPrecio(Connection conexion, int id, double nuevoPrecio)
            throws SQLException {
        String sql = "UPDATE productos SET precio = ? WHERE id = ?";
        try (PreparedStatement actualizar = conexion.prepareStatement(sql)) {
            actualizar.setDouble(1, nuevoPrecio);
            actualizar.setInt(2, id);
            return actualizar.executeUpdate();
        }
    }

    static int eliminar(Connection conexion, int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement eliminar = conexion.prepareStatement(sql)) {
            eliminar.setInt(1, id);
            return eliminar.executeUpdate();
        }
    }

    static void listar(Connection conexion) throws SQLException {
        String sql = "SELECT id, nombre, precio FROM productos ORDER BY id";
        try (PreparedStatement listar = conexion.prepareStatement(sql);
             ResultSet resultado = listar.executeQuery()) {

            System.out.printf("%-5s %-25s %12s%n", "ID", "NOMBRE", "PRECIO");
            while (resultado.next()) {
                System.out.printf("%-5d %-25s %12.2f%n",
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getDouble("precio"));
            }
        }
    }
}

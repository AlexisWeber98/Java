/*
 * ============================================================================
 *  Ejercicio 3 — CRUD completo de productos
 * ============================================================================
 *
 *  ENUNCIADO:
 *  Implementá un CRUD sobre la tabla productos(id INT PRIMARY KEY,
 *  nombre VARCHAR(80), precio DOUBLE):
 *    - insertar(Producto)
 *    - actualizarPrecio(int id, double nuevoPrecio)
 *    - eliminar(int id)
 *    - listar()  → imprime todos los productos
 *  Desde main: insertá 3 productos, listá, actualizá el precio de uno,
 *  eliminá otro y volvé a listar.
 *
 *  REQUISITOS:
 *    - Usar record Producto(int id, String nombre, double precio).
 *    - Todos los SQL con PreparedStatement (parámetros por índice).
 *    - Devolver cantidad de filas afectadas desde update/delete.
 *
 *  PISTAS:
 *    - executeUpdate() devuelve el número de filas afectadas (0 si no existía).
 *    - listar() puede usar PreparedStatement también (SQL sin parámetros).
 *
 *  CÓMO EJECUTAR (desde este directorio ejercicios/):
 *    java -cp "../lib/h2.jar" Ejercicio3CrudProductos.java
 * ============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ejercicio3CrudProductos {

    // TODO: declarar el record Producto(int id, String nombre, double precio)

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:ejercicio3;DB_CLOSE_DELAY=-1";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {

            // TODO 1: crear tabla productos
            // TODO 2: implementar e invocar insertar / listar / actualizarPrecio / eliminar
            // TODO 3: flujo del enunciado: 3 inserts → listar → update → delete → listar

        } catch (SQLException excepcion) {
            System.err.println("Error de base de datos: " + excepcion.getMessage());
        }
    }

    // TODO: insertar(Connection, Producto)          → int filasAfectadas
    // TODO: actualizarPrecio(Connection, int, double) → int filasAfectadas
    // TODO: eliminar(Connection, int)               → int filasAfectadas
    // TODO: listar(Connection)                      → void (imprime la tabla)
}

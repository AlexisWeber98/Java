/*
 * ============================================================================
 *  Solución 5 — Desafío: Mini patrón DAO completo
 * ============================================================================
 *
 *  Ejecutar (desde este directorio soluciones/):
 *    java -cp "../lib/h2.jar" Solucion5DesafioMiniDaoCompleto.java
 *
 *  O desde el directorio raíz del módulo:
 *    java -cp "lib/h2.jar" soluciones/Solucion5DesafioMiniDaoCompleto.java
 * ============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

record Cliente(int id, String nombre, String email) {
}

interface ClienteDao {

    void guardar(Cliente cliente) throws SQLException;

    Optional<Cliente> buscarPorId(int id) throws SQLException;

    List<Cliente> listarTodos() throws SQLException;

    boolean eliminar(int id) throws SQLException;
}

/**
 * Implementación del DAO con JDBC puro sobre H2.
 * La Connection se inyecta por constructor: el DAO no decide cómo conectarse.
 */
class ClienteDaoH2 implements ClienteDao {

    private final Connection conexion;

    ClienteDaoH2(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void guardar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (id, nombre, email) VALUES (?, ?, ?)";
        try (PreparedStatement insertar = conexion.prepareStatement(sql)) {
            insertar.setInt(1, cliente.id());
            insertar.setString(2, cliente.nombre());
            insertar.setString(3, cliente.email());
            insertar.executeUpdate();
        }
    }

    @Override
    public Optional<Cliente> buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, email FROM clientes WHERE id = ?";
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapear(resultado));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre, email FROM clientes ORDER BY id";
        List<Cliente> clientes = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                clientes.add(mapear(resultado));
            }
        }
        return clientes;
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (PreparedStatement eliminar = conexion.prepareStatement(sql)) {
            eliminar.setInt(1, id);
            return eliminar.executeUpdate() == 1;
        }
    }

    /**
     * Traduce la fila actual del ResultSet al modelo de dominio.
     * Único lugar donde se conoce el mapeo columnas → atributos.
     */
    private static Cliente mapear(ResultSet resultado) throws SQLException {
        return new Cliente(
                resultado.getInt("id"),
                resultado.getString("nombre"),
                resultado.getString("email"));
    }
}

public class Solucion5DesafioMiniDaoCompleto {

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:ejercicio5;DB_CLOSE_DELAY=-1";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {

            // El DDL vive FUERA del DAO: su única responsabilidad es el CRUD.
            try (Statement sentencia = conexion.createStatement()) {
                sentencia.execute("CREATE TABLE clientes ("
                        + "id INT PRIMARY KEY, "
                        + "nombre VARCHAR(60), "
                        + "email VARCHAR(80))");
            }

            ClienteDao dao = new ClienteDaoH2(conexion);

            // 1) GUARDAR
            System.out.println("--- GUARDAR ---");
            dao.guardar(new Cliente(1, "Ana García", "ana@ejemplo.com.ar"));
            dao.guardar(new Cliente(2, "Bruno Díaz", "bruno@ejemplo.com.ar"));
            System.out.println("2 clientes guardados.");

            // 2) BUSCAR POR ID
            System.out.println("\n--- BUSCAR POR ID 1 ---");
            dao.buscarPorId(1).ifPresentOrElse(
                    cliente -> System.out.println("Encontrado: " + cliente),
                    () -> System.out.println("No encontrado"));
            System.out.println("Buscar id 99: "
                    + dao.buscarPorId(99).orElse(null));

            // 3) LISTAR TODOS
            System.out.println("\n--- LISTAR TODOS ---");
            dao.listarTodos().forEach(cliente -> System.out.println("  " + cliente));

            // 4) ELIMINAR
            System.out.println("\n--- ELIMINAR ID 1 ---");
            System.out.println("¿Se eliminó? " + dao.eliminar(1));

            // 5) LISTAR FINAL
            System.out.println("\n--- LISTADO FINAL ---");
            dao.listarTodos().forEach(cliente -> System.out.println("  " + cliente));

        } catch (SQLException excepcion) {
            System.err.println("Error de base de datos: " + excepcion.getMessage());
        }
    }
}

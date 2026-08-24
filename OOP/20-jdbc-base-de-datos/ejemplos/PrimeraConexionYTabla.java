import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * PrimeraConexionYTabla.java
 * Conexión a H2 embebido en memoria: creamos la tabla productos, insertamos
 * tres filas y las volvemos a leer recorriendo el ResultSet.
 *
 * Ejecutar:  java -cp lib/h2.jar ejemplos/PrimeraConexionYTabla.java
 */
public class PrimeraConexionYTabla {

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:demo"; // base en RAM: vive solo mientras corre el proceso

        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {
            System.out.println("Conectado a H2 en memoria.");

            // 1) DDL: crear la estructura con execute()
            try (Statement sentencia = conexion.createStatement()) {
                sentencia.execute("""
                        CREATE TABLE productos (
                            id     BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL,
                            precio DECIMAL(10,2) NOT NULL
                        )
                        """);
            }

            // 2) INSERT de tres filas: executeUpdate devuelve filas afectadas
            try (Statement sentencia = conexion.createStatement()) {
                int filas = sentencia.executeUpdate("""
                        INSERT INTO productos (nombre, precio) VALUES
                            ('Teclado mecánico', 18500.00),
                            ('Mouse inalámbrico', 9200.50),
                            ('Monitor 24 pulgadas', 210000.00)
                        """);
                System.out.println("Filas insertadas: " + filas);
            }

            // 3) SELECT: el ResultSet es un cursor que avanza fila a fila
            try (Statement sentencia = conexion.createStatement();
                 ResultSet rs = sentencia.executeQuery("SELECT id, nombre, precio FROM productos")) {

                System.out.printf("%-3s %-22s %12s%n", "id", "nombre", "precio");
                while (rs.next()) {
                    long id = rs.getLong("id");           // por NOMBRE de columna
                    String nombre = rs.getString("nombre");
                    BigDecimal precio = rs.getBigDecimal("precio");
                    System.out.printf("%-3d %-22s %12s%n", id, nombre, precio);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
        }
    }
}

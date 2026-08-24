import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * PreparedStatementSeguro.java
 * Comparación lado a lado: buscar un usuario concatenando strings (PELIGROSO)
 * versus la misma búsqueda parametrizada con PreparedStatement (SEGURO).
 *
 * Ejecutar:  java -cp lib/h2.jar ejemplos/PreparedStatementSeguro.java
 */
public class PreparedStatementSeguro {

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:seguridad";
        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {
            crearEsquema(conexion);

            // Entrada "maligna": el clásico ataque de SQL Injection.
            // Si el usuario tipea esto en el campo de login, la concatenación
            // rompe las comillas y la condición queda siempre verdadera.
            String entradaMaligna = "' OR '1'='1";

            System.out.println("== Versión PELIGROSA: SQL concatenado ==");
            buscarConcatenando(conexion, entradaMaligna);

            System.out.println();
            System.out.println("== Versión SEGURA: PreparedStatement con ? ==");
            buscarSeguro(conexion, entradaMaligna);
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
        }
    }

    private static void crearEsquema(Connection conexion) throws SQLException {
        try (Statement st = conexion.createStatement()) {
            st.execute("""
                    CREATE TABLE usuarios (
                        id       BIGINT AUTO_INCREMENT PRIMARY KEY,
                        usuario  VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR(100) NOT NULL
                    )
                    """);
            st.execute("INSERT INTO usuarios (usuario, password) VALUES ('ana', 'clave123')");
        }
    }

    // NUNCA hagas esto: el texto del usuario se convierte en código SQL.
    private static void buscarConcatenando(Connection conexion, String usuario) throws SQLException {
        String sql = "SELECT usuario FROM usuarios WHERE usuario = '" + usuario + "'";
        System.out.println("SQL generado: " + sql);
        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                // Devuelve TODAS las filas: el atacante entra sin conocer la contraseña.
                System.out.println("[FALLO] Login concedido a: '" + rs.getString("usuario") + "'");
            } else {
                System.out.println("Login rechazado.");
            }
        }
    }

    // Siempre hacé esto: los valores viajan como DATOS, nunca como SQL.
    private static void buscarSeguro(Connection conexion, String usuario) throws SQLException {
        String sql = "SELECT usuario FROM usuarios WHERE usuario = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario); // se busca ese texto LITERAL como nombre de usuario
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("[OK] Login concedido a: '" + rs.getString("usuario") + "'");
                } else {
                    System.out.println("[OK] Ninguna fila coincide: el ataque no funciona.");
                }
            }
        }
    }
}

/*
 * ============================================================================
 *  Solución 2 — Consulta parametrizada con PreparedStatement
 * ============================================================================
 *
 *  Ejecutar (desde este directorio soluciones/):
 *    java -cp "../lib/h2.jar" Solucion2ConsultaParametrizada.java
 *
 *  O desde el directorio raíz del módulo:
 *    java -cp "lib/h2.jar" soluciones/Solucion2ConsultaParametrizada.java
 * ============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Solucion2ConsultaParametrizada {

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:ejercicio2;DB_CLOSE_DELAY=-1";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {

            // Datos de prueba
            try (Statement sentencia = conexion.createStatement()) {
                sentencia.execute("CREATE TABLE alumnos ("
                        + "dni INT PRIMARY KEY, "
                        + "nombre VARCHAR(50), "
                        + "nota DOUBLE)");
                sentencia.execute("INSERT INTO alumnos VALUES (40111222, 'Ana García', 8.5)");
                sentencia.execute("INSERT INTO alumnos VALUES (39222333, 'Bruno Díaz', 6.0)");
                sentencia.execute("INSERT INTO alumnos VALUES (41555444, 'Carla Ruiz', 9.25)");
                sentencia.execute("INSERT INTO alumnos VALUES (38666777, 'Andrés Molina', 7.0)");
            }
            System.out.println("Tabla creada con 4 alumnos.");

            System.out.println("Búsqueda por fragmento \"an\":");
            buscarPorFragmento(conexion, "an");

            // DEMOSTRACIÓN DE SEGURIDAD: ¿qué pasa si un usuario "malicioso" escribe esto?
            String entradaMaliciosa = "' OR '1'='1";
            System.out.println();
            System.out.println("Entrada maliciosa simulada: \"" + entradaMaliciosa + "\"");
            buscarPorFragmento(conexion, entradaMaliciosa);
            // Con PreparedStatement la entrada se trata SOLO como texto literal:
            // busca un nombre que contenga esa cadena (no hay filas) y NO inyecta SQL.

        } catch (SQLException excepcion) {
            System.err.println("Error de base de datos: " + excepcion.getMessage());
        }
    }

    /**
     * Busca alumnos cuyo nombre contenga el fragmento recibido.
     *
     * SEGURIDAD: el fragmento llega SIEMPRE por setString(...), nunca concatenado
     * al SQL. El driver lo envía como parámetro tipado y la base lo trata como
     * dato, no como código. Concatenar strings en el SQL (p. ej.
     * "... LIKE '%" + fragmento + "%'") habilita inyección SQL: el ejemplo con
     * "' OR '1'='1" devolvería TODA la tabla.
     */
    private static void buscarPorFragmento(Connection conexion, String fragmento)
            throws SQLException {
        String consulta = "SELECT dni, nombre, nota FROM alumnos WHERE LOWER(nombre) LIKE ?";

        try (PreparedStatement consultaSegura = conexion.prepareStatement(consulta)) {
            consultaSegura.setString(1, "%" + fragmento.toLowerCase() + "%");

            try (ResultSet resultado = consultaSegura.executeQuery()) {
                int encontrados = 0;
                while (resultado.next()) {
                    System.out.printf("  dni=%d  nombre=%s  nota=%.2f%n",
                            resultado.getInt("dni"),
                            resultado.getString("nombre"),
                            resultado.getDouble("nota"));
                    encontrados++;
                }
                if (encontrados == 0) {
                    System.out.println("  (sin resultados — la entrada fue tratada como dato)");
                } else {
                    System.out.println("  Total encontrados: " + encontrados);
                }
            }
        }
    }
}

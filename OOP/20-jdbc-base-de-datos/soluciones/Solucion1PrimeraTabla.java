/*
 * ============================================================================
 *  Solución 1 — Tu primera tabla con JDBC
 * ============================================================================
 *
 *  Ejecutar (desde este directorio soluciones/):
 *    java -cp "../lib/h2.jar" Solucion1PrimeraTabla.java
 *
 *  O desde el directorio raíz del módulo:
 *    java -cp "lib/h2.jar" soluciones/Solucion1PrimeraTabla.java
 * ============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Solucion1PrimeraTabla {

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:ejercicio1;DB_CLOSE_DELAY=-1";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {
            System.out.println("Conexión OK a H2 en memoria.");

            // 1) DDL: crear la tabla
            try (Statement sentencia = conexion.createStatement()) {
                sentencia.execute("CREATE TABLE alumnos ("
                        + "dni INT PRIMARY KEY, "
                        + "nombre VARCHAR(50), "
                        + "nota DOUBLE)");
                System.out.println("Tabla 'alumnos' creada.");
            }

            // 2) Inserts (PreparedStatement aunque los datos sean fijos: buen hábito)
            String insertar = "INSERT INTO alumnos (dni, nombre, nota) VALUES (?, ?, ?)";
            Object[][] datos = {
                    {40111222, "Ana García", 8.5},
                    {39222333, "Bruno Díaz", 6.0},
                    {41555444, "Carla Ruiz", 9.25}
            };
            try (PreparedStatement insert = conexion.prepareStatement(insertar)) {
                for (Object[] fila : datos) {
                    insert.setInt(1, (Integer) fila[0]);
                    insert.setString(2, (String) fila[1]);
                    insert.setDouble(3, (Double) fila[2]);
                    insert.executeUpdate();
                }
            }
            System.out.println("3 alumnos insertados.");

            // 3) SELECT ordenado por nota descendente
            String consulta = "SELECT dni, nombre, nota FROM alumnos ORDER BY nota DESC";
            try (Statement sentencia = conexion.createStatement();
                 ResultSet resultado = sentencia.executeQuery(consulta)) {

                System.out.printf("%-10s %-20s %s%n", "DNI", "NOMBRE", "NOTA");
                while (resultado.next()) {
                    int dni = resultado.getInt("dni");
                    String nombre = resultado.getString("nombre");
                    double nota = resultado.getDouble("nota");
                    System.out.printf("%-10d %-20s %.2f%n", dni, nombre, nota);
                }
            }

        } catch (SQLException excepcion) {
            System.err.println("Error de base de datos: " + excepcion.getMessage());
        }
    }
}

/*
 * ============================================================================
 *  Ejercicio 1 — Tu primera tabla con JDBC
 * ============================================================================
 *
 *  ENUNCIADO:
 *  Conectate a una base H2 en memoria y:
 *    1) Creá la tabla alumnos(dni INT PRIMARY KEY, nombre VARCHAR(50), nota DOUBLE).
 *    2) Insertá 3 alumnos con notas distintas.
 *    3) Listá todos los alumnos ordenados por nota descendente.
 *
 *  REQUISITOS:
 *    - Usar jdbc:h2:mem (la base vive solo durante esta ejecución).
 *    - Cerrar Connection, Statement y ResultSet con try-with-resources.
 *    - Leer el ResultSet con getInt / getString / getDouble por nombre de columna.
 *
 *  PISTAS:
 *    - DriverManager.getConnection("jdbc:h2:mem:...", "sa", "") abre la conexión.
 *    - Statement.execute(...) para DDL (CREATE TABLE).
 *    - Statement.executeUpdate(...) para INSERT.
 *    - Statement.executeQuery(...) para SELECT; rs.next() avanza fila a fila.
 *
 *  CÓMO EJECUTAR (desde este directorio ejercicios/):
 *    java -cp "../lib/h2.jar" Ejercicio1PrimeraTabla.java
 * ============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio1PrimeraTabla {

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:ejercicio1;DB_CLOSE_DELAY=-1";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {
            System.out.println("Conexión OK a H2 en memoria.");

            // TODO 1: crear la tabla alumnos con Statement.execute(...)
            // TODO 2: insertar 3 alumnos (dni, nombre, nota)
            // TODO 3: consultar con SELECT ... ORDER BY nota DESC e imprimir cada fila

        } catch (SQLException excepcion) {
            System.err.println("Error de base de datos: " + excepcion.getMessage());
        }
    }
}

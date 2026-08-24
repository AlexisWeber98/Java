/*
 * ============================================================================
 *  Ejercicio 2 — Consulta parametrizada con PreparedStatement
 * ============================================================================
 *
 *  ENUNCIADO:
 *  Sobre una tabla alumnos(dni INT PRIMARY KEY, nombre VARCHAR(50), nota DOUBLE):
 *    1) Insertá 4 alumnos.
 *    2) Buscá por fragmento de nombre usando LIKE ? (parámetro, no concatenación).
 *    3) Probalo con el fragmento "an" y mostrá los resultados.
 *
 *  REQUISITOS:
 *    - El patrón LIKE se arma como "%" + fragmento + "%", pero SIEMPRE via setString.
 *    - NUNCA concatenar la entrada del usuario dentro del SQL.
 *
 *  PISTAS:
 *    - SELECT ... WHERE LOWER(nombre) LIKE ? evita problemas de mayúsculas.
 *    - setString(1, "%" + fragmento + "%") es la forma correcta y segura.
 *
 *  CÓMO EJECUTAR (desde este directorio ejercicios/):
 *    java -cp "../lib/h2.jar" Ejercicio2ConsultaParametrizada.java
 * ============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ejercicio2ConsultaParametrizada {

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:ejercicio2;DB_CLOSE_DELAY=-1";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {

            // TODO 1: crear tabla alumnos e insertar 4 alumnos
            // TODO 2: buscar por fragmento con PreparedStatement + LIKE ?
            // TODO 3: llamar al método buscarPorFragmento(conexion, "an")

        } catch (SQLException excepcion) {
            System.err.println("Error de base de datos: " + excepcion.getMessage());
        }
    }

    // TODO: implementar buscarPorFragmento(Connection conexion, String fragmento)
    //       que ejecute el SELECT parametrizado e imprima las filas encontradas.
}

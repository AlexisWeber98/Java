/*
 * ============================================================================
 *  Ejercicio 5 — Desafío: Mini patrón DAO completo
 * ============================================================================
 *
 *  ENUNCIADO:
 *  Implementá el patrón DAO (Data Access Object) sobre la tabla
 *  clientes(id INT PRIMARY KEY, nombre VARCHAR(60), email VARCHAR(80)):
 *    - interface ClienteDao: guardar(Cliente), buscarPorId(int),
 *      listarTodos(), eliminar(int).
 *    - clase ClienteDaoH2 que implementa ClienteDao usando JDBC.
 *    - Un main que demuestre el ciclo de vida completo:
 *      guardar → buscarPorId → listarTodos → eliminar.
 *
 *  REQUISITOS:
 *    - try-with-resources en TODAS las operaciones JDBC.
 *    - buscarPorId devuelve Optional<Cliente> (¡no null!).
 *    - El DAO recibe la Connection por constructor: la tabla se crea fuera.
 *
 *  PISTAS:
 *    - record Cliente(int id, String nombre, String email) como modelo.
 *    - listarTodos arma una List<Cliente> recorriendo el ResultSet.
 *    - eliminar devuelve boolean: rs de executeUpdate() != 0... mejor,
 *      filas == 1.
 *
 *  CÓMO EJECUTAR (desde este directorio ejercicios/):
 *    java -cp "../lib/h2.jar" Ejercicio5DesafioMiniDaoCompleto.java
 * ============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ejercicio5DesafioMiniDaoCompleto {

    // TODO: record Cliente(int id, String nombre, String email)

    // TODO: interface ClienteDao con guardar / buscarPorId / listarTodos / eliminar

    // TODO: clase ClienteDaoH2 implements ClienteDao

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:ejercicio5;DB_CLOSE_DELAY=-1";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {

            // TODO 1: crear tabla clientes
            // TODO 2: instanciar ClienteDaoH2(conexion)
            // TODO 3: ciclo de vida completo: guardar 2 clientes, buscar uno,
            //         listar todos, eliminar uno y volver a listar

        } catch (SQLException excepcion) {
            System.err.println("Error de base de datos: " + excepcion.getMessage());
        }
    }
}

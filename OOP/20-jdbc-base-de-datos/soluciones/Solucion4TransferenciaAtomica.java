/*
 * ============================================================================
 *  Solución 4 — Transferencia bancaria atómica (transacciones)
 * ============================================================================
 *
 *  Ejecutar (desde este directorio soluciones/):
 *    java -cp "../lib/h2.jar" Solucion4TransferenciaAtomica.java
 *
 *  O desde el directorio raíz del módulo:
 *    java -cp "lib/h2.jar" soluciones/Solucion4TransferenciaAtomica.java
 * ============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Solucion4TransferenciaAtomica {

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:ejercicio4;DB_CLOSE_DELAY=-1";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {

            // Preparación de datos
            try (Statement sentencia = conexion.createStatement()) {
                sentencia.execute("CREATE TABLE cuentas ("
                        + "id INT PRIMARY KEY, "
                        + "titular VARCHAR(50), "
                        + "saldo DOUBLE)");
                sentencia.execute("INSERT INTO cuentas VALUES (1, 'Ana García', 1000.0)");
                sentencia.execute("INSERT INTO cuentas VALUES (2, 'Bruno Díaz', 1000.0)");
            }

            System.out.println("Estado inicial:");
            listarSaldos(conexion);

            // --- Transferencia EXITOSA ---
            System.out.println("\nTransferencia exitosa: $300.0 de cuenta 1 a cuenta 2");
            transferir(conexion, 1, 2, 300.0);
            listarSaldos(conexion);

            // --- Transferencia FALLIDA (fuerza el rollback) ---
            System.out.println("\nTransferencia fallida: $5000.0 de cuenta 2 a cuenta 1");
            try {
                transferir(conexion, 2, 1, 5000.0);
            } catch (IllegalStateException errorDeNegocio) {
                // El débito de la cuenta origen YA SE EJECUTÓ dentro de la transacción,
                // pero como nunca llegó al commit(), el rollback deshace TODO.
                System.out.println("Fallo esperado: " + errorDeNegocio.getMessage());
                System.out.println("Rollback aplicado: la base vuelve al estado anterior.");
            }
            listarSaldos(conexion);

        } catch (SQLException excepcion) {
            System.err.println("Error de base de datos: " + excepcion.getMessage());
        }
    }

    /**
     * Transfiere monto entre dos cuentas de forma ATÓMICA.
     *
     * Sin transacción, si el proceso muere entre el débito y el crédito,
     * el dinero desaparece. Con transacción, o se aplican los dos UPDATE
     * (commit) o no se aplica ninguno (rollback).
     */
    static void transferir(Connection conexion, int idOrigen, int idDestino, double monto)
            throws SQLException {

        boolean autoCommitOriginal = conexion.getAutoCommit();
        conexion.setAutoCommit(false); // inicia la transacción manual

        try {
            double saldoOrigen = obtenerSaldo(conexion, idOrigen);
            if (saldoOrigen < monto) {
                throw new IllegalStateException("Saldo insuficiente en cuenta " + idOrigen
                        + " (saldo=" + saldoOrigen + ", monto=" + monto + ")");
            }

            debitar(conexion, idOrigen, monto);
            acreditar(conexion, idDestino, monto);

            conexion.commit(); // TODO hecho: ambas operaciones quedan visibles juntas
            System.out.println("Transacción confirmada (commit).");

        } catch (SQLException | IllegalStateException error) {
            conexion.rollback(); // deshace TODOS los cambios desde setAutoCommit(false)
            throw error;

        } finally {
            conexion.setAutoCommit(autoCommitOriginal);
        }
    }

    private static double obtenerSaldo(Connection conexion, int idCuenta) throws SQLException {
        String sql = "SELECT saldo FROM cuentas WHERE id = ?";
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, idCuenta);
            try (ResultSet resultado = consulta.executeQuery()) {
                if (!resultado.next()) {
                    throw new IllegalStateException("No existe la cuenta " + idCuenta);
                }
                return resultado.getDouble("saldo");
            }
        }
    }

    private static void debitar(Connection conexion, int idCuenta, double monto)
            throws SQLException {
        String sql = "UPDATE cuentas SET saldo = saldo - ? WHERE id = ?";
        try (PreparedStatement actualizar = conexion.prepareStatement(sql)) {
            actualizar.setDouble(1, monto);
            actualizar.setInt(2, idCuenta);
            actualizar.executeUpdate();
        }
    }

    private static void acreditar(Connection conexion, int idCuenta, double monto)
            throws SQLException {
        debitar(conexion, idCuenta, -monto);
    }

    private static void listarSaldos(Connection conexion) throws SQLException {
        String sql = "SELECT id, titular, saldo FROM cuentas ORDER BY id";
        try (Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {
            while (resultado.next()) {
                System.out.printf("  Cuenta %d (%s): saldo=%.2f%n",
                        resultado.getInt("id"),
                        resultado.getString("titular"),
                        resultado.getDouble("saldo"));
            }
        }
    }
}

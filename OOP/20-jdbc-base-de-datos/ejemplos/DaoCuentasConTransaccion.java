import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/** Registro de dominio: una cuenta bancaria. */
record Cuenta(long id, String titular, BigDecimal saldo) { }

/** Interfaz del DAO: el "qué necesito", sin detalles de JDBC. */
interface CuentaDao {
    void crear(long id, String titular, BigDecimal saldo);
    Optional<Cuenta> buscarPorId(long id);
}

/** Implementación JDBC del DAO: el "cómo se obtiene". */
class CuentaDaoH2 implements CuentaDao {
    private final Connection conexion;

    CuentaDaoH2(Connection conexion) { this.conexion = conexion; }

    @Override
    public void crear(long id, String titular, BigDecimal saldo) {
        try (PreparedStatement ps = conexion.prepareStatement(
                "INSERT INTO cuentas (id, titular, saldo) VALUES (?, ?, ?)")) {
            ps.setLong(1, id);
            ps.setString(2, titular);
            ps.setBigDecimal(3, saldo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo crear la cuenta " + id, e);
        }
    }

    @Override
    public Optional<Cuenta> buscarPorId(long id) {
        try (PreparedStatement ps = conexion.prepareStatement(
                "SELECT id, titular, saldo FROM cuentas WHERE id = ?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(new Cuenta(rs.getLong("id"), rs.getString("titular"), rs.getBigDecimal("saldo")))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo buscar la cuenta " + id, e);
        }
    }
}

/**
 * Transferencia bancaria atómica: debitar y acreditar en UNA transacción.
 * Si algo falla en el medio, el rollback descarta los cambios parciales.
 *
 * Ejecutar:  java -cp lib/h2.jar ejemplos/DaoCuentasConTransaccion.java
 */
public class DaoCuentasConTransaccion {

    public static void main(String[] args) {
        try (Connection conexion = DriverManager.getConnection("jdbc:h2:mem:cuentas", "sa", "")) {
            crearEsquema(conexion);
            CuentaDao dao = new CuentaDaoH2(conexion); // la app solo ve la interfaz
            dao.crear(1, "Ana", new BigDecimal("5000"));
            dao.crear(2, "Bruno", new BigDecimal("800"));
            mostrarSaldos(dao, "Saldos iniciales");
            // Transferencia válida: se aplica completa.
            transferir(conexion, dao, 1, 2, new BigDecimal("1500"));
            mostrarSaldos(dao, "Tras transferir $1500 de Ana a Bruno");
            // Transferencia inválida: Bruno no tiene $99999. Fuerza rollback.
            try {
                transferir(conexion, dao, 2, 1, new BigDecimal("99999"));
            } catch (IllegalStateException e) {
                System.out.println("Rechazada: " + e.getMessage());
            }
            mostrarSaldos(dao, "Tras intento inválido (rollback: nada cambió)");
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
        }
    }

    /** Transfiere monto de origen a destino de forma atómica: todo o nada. */
    private static void transferir(Connection conexion, CuentaDao dao,
                                   long origen, long destino, BigDecimal monto) {
        try {
            conexion.setAutoCommit(false);
            BigDecimal saldoOrigen = dao.buscarPorId(origen).orElseThrow().saldo();
            BigDecimal saldoDestino = dao.buscarPorId(destino).orElseThrow().saldo();
            // Aplicamos ambos movimientos y validamos DESPUÉS: si quedó negativo, rollback.
            actualizarSaldo(conexion, origen, saldoOrigen.subtract(monto));
            actualizarSaldo(conexion, destino, saldoDestino.add(monto));
            if (dao.buscarPorId(origen).orElseThrow().saldo().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("saldo insuficiente en la cuenta origen");
            }
            conexion.commit(); // ambas operaciones quedan visibles juntas
        } catch (IllegalStateException e) {
            revertir(conexion);
            throw e;
        } catch (SQLException e) {
            revertir(conexion);
            throw new IllegalStateException("Fallo técnico en la transferencia", e);
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException ignorada) { /* conexión ya cerrada */ }
        }
    }

    private static void actualizarSaldo(Connection conexion, long id, BigDecimal nuevoSaldo)
            throws SQLException {
        try (PreparedStatement ps = conexion.prepareStatement(
                "UPDATE cuentas SET saldo = ? WHERE id = ?")) {
            ps.setBigDecimal(1, nuevoSaldo);
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }

    private static void revertir(Connection conexion) {
        try {
            conexion.rollback();
            System.out.println("(rollback ejecutado: cambios parciales descartados)");
        } catch (SQLException e) {
            System.err.println("No se pudo hacer rollback: " + e.getMessage());
        }
    }

    private static void crearEsquema(Connection conexion) throws SQLException {
        try (Statement st = conexion.createStatement()) {
            st.execute("""
                    CREATE TABLE cuentas (
                        id      BIGINT PRIMARY KEY,
                        titular VARCHAR(100) NOT NULL,
                        saldo   DECIMAL(12,2) NOT NULL
                    )
                    """);
        }
    }

    private static void mostrarSaldos(CuentaDao dao, String titulo) {
        System.out.println("-- " + titulo + ": Ana $" + dao.buscarPorId(1).orElseThrow().saldo()
                + " | Bruno $" + dao.buscarPorId(2).orElseThrow().saldo());
    }
}

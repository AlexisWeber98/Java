/*
 * ============================================================================
 *  Ejercicio 4 — Transferencia bancaria atómica (transacciones)
 * ============================================================================
 *
 *  ENUNCIADO:
 *  Sobre una tabla cuentas(id INT PRIMARY KEY, titular VARCHAR(50),
 *  saldo DOUBLE):
 *    1) Insertá 2 cuentas con saldo 1000.0 cada una.
 *    2) Implementá transferir(conexion, idOrigen, idDestino, monto) que debite
 *       de una cuenta y acredite en la otra DENTRO DE UNA TRANSACCIÓN:
 *       setAutoCommit(false) ... commit().
 *    3) Forzá un fallo a mitad de la transferencia (monto mayor al saldo)
 *       y demostrá que el rollback deja los saldos intactos.
 *
 *  REQUISITOS:
 *    - Sin transacción, un fallo entre el débito y el crédito dejaría dinero
 *      "perdido". Con transacción, todo o nada.
 *    - El método debe lanzar IllegalStateException si el saldo es insuficiente,
 *      capturarlo en main y hacer rollback().
 *
 *  PISTAS:
 *    - conexion.setAutoCommit(false) abre la transacción manual.
 *    - UPDATE cuentas SET saldo = saldo - ? WHERE id = ? para el débito.
 *    - Ante excepción: rollback(); ante éxito: commit(). Siempre restaurar
 *      autoCommit(true) si la conexión se va a reutilizar.
 *
 *  CÓMO EJECUTAR (desde este directorio ejercicios/):
 *    java -cp "../lib/h2.jar" Ejercicio4TransferenciaAtomica.java
 * ============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ejercicio4TransferenciaAtomica {

    public static void main(String[] args) {
        String url = "jdbc:h2:mem:ejercicio4;DB_CLOSE_DELAY=-1";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "")) {

            // TODO 1: crear tabla cuentas e insertar 2 cuentas con saldo 1000.0

            // TODO 2: transferencia EXITOSA de 300.0 de la cuenta 1 a la 2

            // TODO 3: transferencia FALLIDA (monto > saldo) → catch + rollback
            //         y verificar que los saldos no cambiaron

            // TODO: implementar transferir(Connection, int, int, double)

        } catch (SQLException excepcion) {
            System.err.println("Error de base de datos: " + excepcion.getMessage());
        }
    }
}

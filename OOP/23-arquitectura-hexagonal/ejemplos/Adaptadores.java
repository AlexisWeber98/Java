/*
 * Módulo 23 - Arquitectura Hexagonal (2 de 3): ADAPTADORES.
 *
 * Aquí viven los "enchufes": clases técnicas que IMPLEMENTAN los puertos de
 * salida declarados por el dominio. El dominio las ignora por completo;
 * solo conoce las interfaces RepositorioCuentas y NotificadorSaldoBajo.
 *
 * En producción, estos mismos puertos los implementarían adaptadores con
 * JPA/Hibernate (persistencia real) o email/SMS/push (notificaciones reales).
 * Hoy usamos HashMap y consola: MISMA interfaz, OTRA tecnología.
 * Mañana cambiás de adaptador sin tocar una línea de negocio.
 *
 * Compilar y ejecutar TODO el ejemplo juntos:
 *   javac *.java && java CasosDeUsoYDemo
 */

import java.util.HashMap;
import java.util.Map;

// Ancla nominal del archivo: clase pública homónima requerida por la
// convención del curso. Los adaptadores reales son las dos clases de abajo.
public class Adaptadores {

    private Adaptadores() {
        // Ancla nominal: nunca se instancia.
    }
}

// ADAPTADOR de persistencia en memoria (en prod: JPA/JDBC contra una BD real).
class RepositorioCuentasEnMemoria implements RepositorioCuentas {

    private final Map<String, CuentaBancaria> cuentas = new HashMap<>();

    @Override
    public void guardar(CuentaBancaria cuenta) {
        cuentas.put(cuenta.getTitular(), cuenta);
    }

    @Override
    public CuentaBancaria buscarPorTitular(String titular) {
        return cuentas.get(titular);
    }
}

// ADAPTADOR de notificaciones por consola (en prod: email/SMS/push).
class NotificadorConsola implements NotificadorSaldoBajo {

    @Override
    public void avisar(String titular, double saldoActual) {
        System.out.printf("[NOTIFICACIÓN] %s: tu saldo está bajo ($%.2f).%n", titular, saldoActual);
    }
}

/*
 * Módulo 23 - Arquitectura Hexagonal (1 de 3): DOMINIO y PUERTOS.
 *
 * Este archivo es el CORAZÓN del sistema: la entidad CuentaBancaria con sus
 * reglas de negocio, más los puertos de SALIDA (interfaces que el dominio
 * NECESITA pero NO implementa).
 *
 * Mirá los imports: no hay ninguno. Cero JPA, cero JDBC, cero consola.
 * El dominio no sabe que existirá un HashMap ni una pantalla.
 *
 * Compilar y ejecutar TODO el ejemplo juntos (los 3 archivos):
 *   javac *.java && java CasosDeUsoYDemo
 *
 * En un proyecto real, estas clases vivirían en paquetes como
 * dominio/ o puertos/; aquí evitamos packages para compilar suelto.
 */

// Ancla nominal del archivo: cada .java del curso expone una clase pública
// con su mismo nombre. El contenido real de esta capa son los tres tipos de
// abajo; acá no hay nada que instanciar.
public class DominioYPuertos {

    private DominioYPuertos() {
        // Ancla nominal: nunca se instancia.
    }
}

// ENTIDAD DEL DOMINIO: solo reglas de negocio. Nada de tecnología.
class CuentaBancaria {

    private final String titular;
    private double saldo;

    public CuentaBancaria(String titular) {
        this.titular = titular;
        this.saldo = 0.0;
    }

    // Regla 1: todo depósito debe ser positivo.
    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El depósito debe ser positivo");
        }
        saldo += monto;
    }

    // Regla 2: nunca se permite el sobregiro.
    public void retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El retiro debe ser positivo");
        }
        if (monto > saldo) {
            throw new IllegalStateException("Saldo insuficiente: " + saldo);
        }
        saldo -= monto;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }
}

/*
 * PUERTO DE SALIDA #1 — Persistencia.
 * Lo DECLARA el dominio porque lo necesita; lo IMPLEMENTARÁ un adaptador.
 * Fijate que habla el idioma del negocio (CuentaBancaria), no de tablas.
 */
interface RepositorioCuentas {

    void guardar(CuentaBancaria cuenta);

    CuentaBancaria buscarPorTitular(String titular);
}

/*
 * PUERTO DE SALIDA #2 — Notificaciones.
 * El dominio decide CUÁNDO avisar (regla de negocio); el adaptador decide
 * CÓMO (consola hoy, email mañana). Separación de preocupaciones.
 */
interface NotificadorSaldoBajo {

    void avisar(String titular, double saldoActual);
}

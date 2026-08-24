/*
 * ============================================================================
 * Módulo 11 - Records | Solución 5: Desafío ¿record o clase?
 * ============================================================================
 * Idea clave: record para VALORES inmutables comparables por contenido;
 * clase para entidades con IDENTIDAD y estado que muta con el tiempo.
 *
 * A) Cuenta bancaria  -> CLASE: el saldo cambia con cada operación y lo que
 *    importa es la identidad de ESA cuenta, no que dos cuentas "sean iguales".
 * B) Coordenada geográfica -> RECORD: valor fijo; dos coordenadas con los
 *    mismos números SON el mismo lugar => igualdad de valor perfecta.
 * C) Sesión de usuario -> CLASE: el indicador de login muta al iniciar o
 *    cerrar sesión; una sesión es una entidad con ciclo de vida.
 */
public class Solucion5DesafioRecordOClase {

    public static void main(String[] args) {
        // A) Clase: identidad + estado mutable.
        CuentaBancaria cuenta = new CuentaBancaria("Ana García");
        cuenta.depositar(50000.0);
        boolean pudoExtraer = cuenta.extraer(12500.0);
        System.out.println("A) Extracción exitosa: " + pudoExtraer + " -> " + cuenta);

        // B) Record: valor inmutable comparable por contenido.
        CoordenadaGeografica obelisco = new CoordenadaGeografica(-34.6037, -58.3816);
        CoordenadaGeografica mismoLugar = new CoordenadaGeografica(-34.6037, -58.3816);
        System.out.println("\nB) " + obelisco);
        System.out.println("   ¿equals con otra instancia igual? " + obelisco.equals(mismoLugar));

        // C) Clase: el flag de login evoluciona con el ciclo de vida.
        SesionUsuario sesion = new SesionUsuario("agus_dev");
        sesion.iniciarSesion();
        System.out.println("\nC) Tras iniciar:   " + sesion);
        sesion.cerrarSesion();
        System.out.println("   Tras cerrar:    " + sesion);
    }

    // ESCENARIO A -> CLASE: saldo mutable + identidad propia (la misma cuenta
    // sigue siendo la misma aunque su saldo cambie). Un record sería imposible:
    // sus campos son final y no admiten mutación controlada como depositar().
    static class CuentaBancaria {
        private final String titular;
        private double saldo;

        CuentaBancaria(String titular) {
            this.titular = titular;
        }

        void depositar(double monto) {
            saldo += monto;
        }

        boolean extraer(double monto) {
            if (monto > saldo) {
                return false;
            }
            saldo -= monto;
            return true;
        }

        @Override
        public String toString() {
            return "CuentaBancaria[titular=" + titular + ", saldo=" + saldo + "]";
        }
    }

    // ESCENARIO B -> RECORD: dos componentes inmutables, equals/hashCode/toString
    // gratis y correctos: mismos números == mismo lugar. Caso ideal para record.
    record CoordenadaGeografica(double latitud, double longitud) {
    }

    // ESCENARIO C -> CLASE: logueado cambia durante la vida del objeto; la
    // sesión tiene identidad y ciclo de vida, no es un valor estático.
    static class SesionUsuario {
        private final String usuario;
        private boolean logueado;

        SesionUsuario(String usuario) {
            this.usuario = usuario;
        }

        void iniciarSesion() {
            logueado = true;
        }

        void cerrarSesion() {
            logueado = false;
        }

        @Override
        public String toString() {
            return "SesionUsuario[usuario=" + usuario + ", logueado=" + logueado + "]";
        }
    }
}

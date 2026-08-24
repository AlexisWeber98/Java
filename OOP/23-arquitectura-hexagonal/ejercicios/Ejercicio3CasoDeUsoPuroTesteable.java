/*
 * ============================================================================
 * Ejercicio 3 — Caso de uso puro y testeable (con test manual)
 * ============================================================================
 *
 * ENUNCIADO
 * RegistradorUsuario es un caso de uso que debe poder probarse SIN base de
 * datos y SIN servicio de email real. Vas a:
 *   1) Implementar el caso de uso RegistradorUsuario: solo conoce sus PUERTOS.
 *   2) Implementar dos adaptadores falsos (fakes) en memoria.
 *   3) Usar el main como corredor de tests con asserts manuales que imprimen
 *      [PASS] / [FAIL] por cada escenario (todavía sin JUnit).
 *
 * Reglas del registro, EN ESTE ORDEN:
 *   a) email inválido          -> fallo EMAIL_INVALIDO
 *   b) email ya registrado     -> fallo EMAIL_DUPLICADO
 *   c) todo bien               -> se guarda el usuario y es éxito
 *
 * REQUISITOS
 * - El caso de uso solo conoce puertos y tipos del dominio. Cero tecnología.
 * - Los fakes implementan los mismos puertos que usaría un adaptador real.
 * - Tres escenarios probados: éxito, duplicado, inválido. Cada uno imprime su
 *   [PASS] o [FAIL]. Si algo falla, el proceso termina con código distinto de 0.
 *
 * PISTAS
 * - Corré el starter ANTES de programar: vas a ver [FAIL]; programá hasta ver
 *   todos [PASS].
 * - Devolver un resultado explícito (record ResultadoDelRegistro) suele ser
 *   mejor que excepciones para flujos de negocio esperables.
 * - Un fake NO es un mock de framework: es una implementación tonta y honesta
 *   del puerto, hecha por vos.
 *
 * CÓMO COMPILAR Y CORRER (desde esta carpeta):
 *   javac *.java && java Ejercicio3CasoDeUsoPuroTesteable
 */
import java.util.HashMap;
import java.util.Map;

public class Ejercicio3CasoDeUsoPuroTesteable {

    // ===== DOMINIO ===========================================================
    record Usuario(String email, String nombre) {}

    enum MotivoDeFallo { EMAIL_INVALIDO, EMAIL_DUPLICADO }

    record ResultadoDelRegistro(boolean exito, MotivoDeFallo fallo) {}

    // ===== PUERTOS SALIDA (definidos por la aplicación) ======================
    interface RepositorioUsuarios {
        boolean existeEmail(String email);
        void guardar(Usuario usuario);
    }

    interface ValidadorEmail {
        boolean esValido(String email);
    }

    // ===== CASO DE USO (puro: solo conoce puertos) ===========================
    static class RegistradorUsuario {
        private final RepositorioUsuarios repositorio;
        private final ValidadorEmail validador;

        RegistradorUsuario(RepositorioUsuarios repositorio, ValidadorEmail validador) {
            this.repositorio = repositorio;
            this.validador = validador;
        }

        ResultadoDelRegistro registrar(String email, String nombre) {
            // TODO: regla a) email inválido -> new ResultadoDelRegistro(false, MotivoDeFallo.EMAIL_INVALIDO)
            // TODO: regla b) email duplicado -> new ResultadoDelRegistro(false, MotivoDeFallo.EMAIL_DUPLICADO)
            // TODO: regla c) guardar en el repositorio y devolver éxito
            return new ResultadoDelRegistro(false, MotivoDeFallo.EMAIL_INVALIDO);
        }
    }

    // ===== ADAPTADORES FALSOS (solo para este test) ==========================
    static class RepositorioEnMemoria implements RepositorioUsuarios {
        private final Map<String, Usuario> porEmail = new HashMap<>();

        @Override
        public boolean existeEmail(String email) {
            // TODO: true si el mapa ya tiene esa clave.
            return false;
        }

        @Override
        public void guardar(Usuario usuario) {
            // TODO: guardá al usuario indexado por su email.
        }
    }

    static class ValidadorFalso implements ValidadorEmail {
        @Override
        public boolean esValido(String email) {
            // TODO: considerá válidos los emails que contienen '@'.
            // (es un fake: no hace falta un regex de la vida real)
            return true;
        }
    }

    // ===== TEST MANUAL: main hace de corredor =================================
    private static int fallas = 0;

    private static void verificar(boolean condicion, String descripcion) {
        if (condicion) {
            System.out.println("[PASS] " + descripcion);
        } else {
            System.out.println("[FAIL] " + descripcion);
            fallas++;
        }
    }

    public static void main(String[] args) {
        RegistradorUsuario caso = new RegistradorUsuario(new RepositorioEnMemoria(), new ValidadorFalso());

        ResultadoDelRegistro ok = caso.registrar("ana@ejemplo.com", "Ana");
        verificar(ok.exito(), "registra un usuario nuevo con email válido");

        ResultadoDelRegistro duplicado = caso.registrar("ana@ejemplo.com", "Ana otra vez");
        verificar(!duplicado.exito() && duplicado.fallo() == MotivoDeFallo.EMAIL_DUPLICADO,
                "rechaza un email ya registrado (EMAIL_DUPLICADO)");

        ResultadoDelRegistro invalido = caso.registrar("no-soy-un-email", "Alguien");
        verificar(!invalido.exito() && invalido.fallo() == MotivoDeFallo.EMAIL_INVALIDO,
                "rechaza un email con formato inválido (EMAIL_INVALIDO)");

        System.out.println(fallas == 0
                ? "RESULTADO: 3/3 PASS"
                : "RESULTADO: " + fallas + " assertion(s) en FAIL");
        if (fallas > 0) {
            System.exit(1);
        }
    }
}

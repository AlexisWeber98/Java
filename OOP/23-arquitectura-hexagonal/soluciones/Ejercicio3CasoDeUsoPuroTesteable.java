/*
 * ============================================================================
 * Solución 3 — Caso de uso puro y testeable (con test manual)
 * ============================================================================
 * Puntos clave:
 * - RegistradorUsuario NO sabe que sus datos viven en un HashMap ni que el
 *   validador es un fake: solo conoce puertos. Eso lo hace puro y testeable.
 * - Los fakes implementan los mismos puertos que un adaptador real (BD, API
 *   de email). Cambiarlos por reales no toca ni una línea del caso de uso.
 * - El main hace de corredor de tests: asserts manuales con [PASS]/[FAIL] y
 *   código de salida != 0 si algo falla (listo para automatizar después).
 *
 * CÓMO COMPILAR Y CORRER (desde soluciones/):
 *   javac *.java && java Ejercicio3CasoDeUsoPuroTesteable
 */
import java.util.HashMap;
import java.util.Map;

public class Ejercicio3CasoDeUsoPuroTesteable {

    // ===== DOMINIO ===========================================================
    record Usuario(String email, String nombre) {}

    enum MotivoDeFallo { EMAIL_INVALIDO, EMAIL_DUPLICADO }

    record ResultadoDelRegistro(boolean exito, MotivoDeFallo fallo) {}

    // ===== PUERTOS SALIDA ====================================================
    interface RepositorioUsuarios {
        boolean existeEmail(String email);
        void guardar(Usuario usuario);
    }

    interface ValidadorEmail {
        boolean esValido(String email);
    }

    // ===== CASO DE USO (puro) ================================================
    static class RegistradorUsuario {
        private final RepositorioUsuarios repositorio;
        private final ValidadorEmail validador;

        RegistradorUsuario(RepositorioUsuarios repositorio, ValidadorEmail validador) {
            this.repositorio = repositorio;
            this.validador = validador;
        }

        ResultadoDelRegistro registrar(String email, String nombre) {
            // Orden de validaciones: primero la forma (barata), después el
            // estado (consulta al puerto), recién ahí se persiste.
            if (!validador.esValido(email)) {
                return new ResultadoDelRegistro(false, MotivoDeFallo.EMAIL_INVALIDO);
            }
            if (repositorio.existeEmail(email)) {
                return new ResultadoDelRegistro(false, MotivoDeFallo.EMAIL_DUPLICADO);
            }
            repositorio.guardar(new Usuario(email, nombre));
            return new ResultadoDelRegistro(true, null);
        }
    }

    // ===== ADAPTADORES FALSOS ================================================
    static class RepositorioEnMemoria implements RepositorioUsuarios {
        private final Map<String, Usuario> porEmail = new HashMap<>();

        @Override
        public boolean existeEmail(String email) {
            return porEmail.containsKey(email);
        }

        @Override
        public void guardar(Usuario usuario) {
            porEmail.put(usuario.email(), usuario);
        }
    }

    static class ValidadorFalso implements ValidadorEmail {
        @Override
        public boolean esValido(String email) {
            return email != null && email.contains("@");
        }
    }

    // ===== TEST MANUAL ========================================================
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
        RepositorioEnMemoria repositorio = new RepositorioEnMemoria();
        RegistradorUsuario caso = new RegistradorUsuario(repositorio, new ValidadorFalso());

        ResultadoDelRegistro ok = caso.registrar("ana@ejemplo.com", "Ana");
        verificar(ok.exito(), "registra un usuario nuevo con email válido");

        verificar(repositorio.existeEmail("ana@ejemplo.com"),
                "el usuario quedó realmente guardado en el repositorio");

        ResultadoDelRegistro duplicado = caso.registrar("ana@ejemplo.com", "Ana otra vez");
        verificar(!duplicado.exito() && duplicado.fallo() == MotivoDeFallo.EMAIL_DUPLICADO,
                "rechaza un email ya registrado (EMAIL_DUPLICADO)");

        ResultadoDelRegistro invalido = caso.registrar("no-soy-un-email", "Alguien");
        verificar(!invalido.exito() && invalido.fallo() == MotivoDeFallo.EMAIL_INVALIDO,
                "rechaza un email con formato inválido (EMAIL_INVALIDO)");

        System.out.println(fallas == 0
                ? "RESULTADO: 4/4 PASS"
                : "RESULTADO: " + fallas + " assertion(s) en FAIL");
        if (fallas > 0) {
            System.exit(1);
        }
    }
}

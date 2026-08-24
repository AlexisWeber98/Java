/*
 * ============================================================================
 * Solución 5: Desafio Mini Validador
 * ============================================================================
 *
 * Validación declarativa en versión de bolsillo: las reglas viven pegadas
 * a los campos (datos) y la lógica de chequeo vive en un único validador
 * genérico (comportamiento). Agregar una regla nueva no toca el validador:
 * se anota el campo y listo. Esa separación datos/reglas es exactamente el
 * diseño de Jakarta Bean Validation, Hibernate Validator y compañía.
 *
 * Detalles que hacen la diferencia:
 * - setAccessible(true): un validador genérico necesita leer campos que
 *   normalmente son privados. Es reflexión con privilegios: usala con
 *   criterio, dentro de infraestructura.
 * - null + longitud: si el campo ya violó NoNulo, no sumamos el error de
   longitud; duplicar fallas por la misma causa ensucia el reporte.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class Solucion5DesafioMiniValidador {

    @Retention(RetentionPolicy.RUNTIME)   // tiene que ser visible por reflexión
    @Target(ElementType.FIELD)            // solo sobre campos
    @interface NoNulo {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface LongitudMaxima {
        int valor();
    }

    static class Cliente {
        @NoNulo
        String nombre;

        @NoNulo
        @LongitudMaxima(valor = 8)
        String alias;

        Cliente(String nombre, String alias) {
            this.nombre = nombre;
            this.alias = alias;
        }
    }

    static class Validador {
        static List<String> validar(Object objeto) {
            List<String> violaciones = new ArrayList<>();

            for (var campo : objeto.getClass().getDeclaredFields()) {
                campo.setAccessible(true);
                Object valor;
                try {
                    valor = campo.get(objeto);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }

                boolean nulo = valor == null;

                if (campo.isAnnotationPresent(NoNulo.class) && nulo) {
                    violaciones.add("El campo '" + campo.getName() + "' no puede ser nulo.");
                }

                if (!nulo && campo.isAnnotationPresent(LongitudMaxima.class)) {
                    int maximo = campo.getAnnotation(LongitudMaxima.class).valor();
                    if (((String) valor).length() > maximo) {
                        violaciones.add("El campo '" + campo.getName() + "' supera la "
                                + "longitud máxima (" + maximo + " caracteres).");
                    }
                }
            }
            return violaciones;
        }
    }

    public static void main(String[] args) {
        Cliente clienteOk = new Cliente("María González", "maria");
        Cliente clienteRoto = new Cliente(null, "unAliasDemasiadoLargo");

        System.out.println("--- Caso válido ---");
        List<String> resultadoOk = Validador.validar(clienteOk);
        System.out.println(resultadoOk.isEmpty()
                ? "Sin violaciones: cliente aceptado."
                : resultadoOk);

        System.out.println("--- Caso inválido ---");
        List<String> resultadoRoto = Validador.validar(clienteRoto);
        if (resultadoRoto.isEmpty()) {
            System.out.println("Sin violaciones.");
        } else {
            System.out.println("Se encontraron " + resultadoRoto.size() + " violaciones:");
            for (String violacion : resultadoRoto) {
                System.out.println("  - " + violacion);
            }
        }
    }
}

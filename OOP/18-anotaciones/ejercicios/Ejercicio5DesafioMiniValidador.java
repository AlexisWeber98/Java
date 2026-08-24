/*
 * ============================================================================
 * Ejercicio 5: Desafio Mini Validador
 * ============================================================================
 *
 * ENUNCIADO:
 * El desafío integrador del módulo: vas a construir validación declarativa,
 * como la de Jakarta Bean Validation (@NotNull, @Size) pero en versión de
 * bolsillo. Las reglas viajan como anotaciones sobre los CAMPOS y un
 * Validador genérico las lee por reflexión.
 *
 * REQUISITOS:
 * 1. Completá las meta-anotaciones de NoNulo y LongitudMaxima:
 *    retención RUNTIME y objetivo FIELD.
 * 2. Completá Validador.validar(Object): debe recorrer los campos del
 *    objeto, y para cada regla incumplida agregar un mensaje a la lista:
 *      - campo anotado con @NoNulo cuyo valor es null;
 *      - campo anotado con @LongitudMaxima(n) cuyo String supera n caracteres.
 * 3. En main, validá un Cliente correcto (sin violaciones) y uno roto
 *    (nombre null + alias demasiado largo) e imprimí ambos resultados.
 *
 * PISTAS:
 * - campo.setAccessible(true) te permite leer campos privados desde el
 *   validador; campo.get(objeto) devuelve el valor (¡como Object!).
 * - Si el valor es null, la regla de longitud no aplica: ya lo atrapó
 *   NoNulo. No acumules dos errores por el mismo problema.
 * - getDeclaredFields() NO hereda campos de la superclase; suficiente acá.
 */
public class Ejercicio5DesafioMiniValidador {

    // TODO 1: agregá @Retention(RetentionPolicy.RUNTIME) y @Target(ElementType.FIELD)
    @interface NoNulo {
    }

    // TODO 1: mismas meta-anotaciones que NoNulo (el atributo ya está declarado)
    @interface LongitudMaxima {
        int valor();
    }

    static class Cliente {
        @NoNulo
        String nombre;

        @NoNulo
        @LongitudMaxima(valor = 8)   // se completa cuando definas valor() arriba
        String alias;

        Cliente(String nombre, String alias) {
            this.nombre = nombre;
            this.alias = alias;
        }
    }

    static class Validador {
        static java.util.List<String> validar(Object objeto) {
            java.util.List<String> violaciones = new java.util.ArrayList<>();
            // TODO 2: recorré objeto.getClass().getDeclaredFields() y evaluá
            //  cada anotación contra el valor real del campo.
            return violaciones;
        }
    }

    public static void main(String[] args) {
        Cliente clienteOk = new Cliente("María González", "maria");
        // TODO 3: armá también un clienteRoto (nombre null, alias largo)
        //  e imprimí el resultado de validar a los dos, bonito y legible.

        System.out.println("Cliente OK -> " + Validador.validar(clienteOk));
    }
}

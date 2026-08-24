/*
 * ============================================================================
 * Solución 3 — Menú bancario con switch de flechas
 * ============================================================================
 *
 * ENUNCIADO
 * Convertir texto de usuario al enum Opcion con valueOf(), tolerando espacios
 * y minúsculas, manejando opciones inválidas con elegancia y resolviendo el
 * mensaje de cada caso con un switch expresión de flechas.
 *
 * CLAVES DE ESTA SOLUCIÓN
 *   - Normalizar SIEMPRE la entrada antes de valueOf(): trim() saca los
 *     espacios y toUpperCase() iguala el formato de las constantes.
 *   - valueOf lanza IllegalArgumentException ante un texto desconocido: lo
 *     capturamos y convertimos en un mensaje útil. Nada de stack traces para
 *     el usuario.
 *   - El switch expresión cubre las tres constantes, así que no lleva default
 *     y el compilador nos avisa si mañana agregamos una constante y olvidamos
 *     tratarla. Exhaustividad gratis.
 */
public class Solucion3MenuSwitchModerno {

    enum Opcion {
        VER_SALDO,
        DEPOSITAR,
        SALIR
    }

    static String procesarEntrada(String textoIngresado) {
        Opcion opcionElegida;
        try {
            // valueOf busca una constante cuyo nombre coincida EXACTAMENTE.
            opcionElegida = Opcion.valueOf(textoIngresado.trim().toUpperCase());
        } catch (IllegalArgumentException error) {
            return "\"" + textoIngresado.trim()
                    + "\" no es una opción válida del menú.";
        }

        // Switch expresión: cada flecha PRODUCE un valor y no hay caídas entre
        // casos. Al cubrir todas las constantes, no hace falta default.
        return switch (opcionElegida) {
            case VER_SALDO -> "Tu saldo disponible es $ 152.350,55.";
            case DEPOSITAR -> "Depósito registrado: el dinero ya está acreditado.";
            case SALIR -> "Cerrando sesión. ¡Gracias por confiar en nosotros!";
        };
    }

    public static void main(String[] args) {
        String[] entradas = {"VER_SALDO", "  depositar ", "SALIR", "PEDIR_PRESTAMO"};

        for (String entrada : entradas) {
            System.out.printf("El usuario escribió \"%s\"%n", entrada);
            System.out.println("   -> " + procesarEntrada(entrada));
            System.out.println();
        }
    }
}

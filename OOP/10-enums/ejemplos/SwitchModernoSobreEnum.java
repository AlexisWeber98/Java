/*
 * Módulo 10 · Ejemplo 3: switch clásico vs. switch con flecha sobre enums,
 * y el beneficio estrella: exhaustividad verificada por el compilador.
 */
public class SwitchModernoSobreEnum {

    enum Dia {
        LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
    }

    // Estilo clásico: si olvidás un break, la ejecución cae en cascada
    // al caso siguiente. Por eso casi siempre termina en default.
    static String horarioConSwitchClasico(Dia dia) {
        String horario;
        switch (dia) {
            case SABADO:
                horario = "Media jornada";
                break;
            case DOMINGO:
                horario = "Descanso";
                break;
            default:
                horario = "Jornada completa";
                break;
        }
        return horario;
    }

    // Switch con flecha (Java 14+): sin cascada y usado como expresión.
    static int horasConSwitchModerno(Dia dia) {
        return switch (dia) {
            case LUNES, MARTES, MIERCOLES, JUEVES, VIERNES -> 8;
            case SABADO -> 4;
            case DOMINGO -> 0;
        };
        // Sin default: como cubrimos TODAS las constantes, el compilador
        // acepta el switch. Si mañana agregamos una constante a Dia,
        // esta línea deja de compilar y nos obliga a revisarla.
    }

    public static void main(String[] args) {
        for (Dia dia : Dia.values()) {
            System.out.printf(
                    "%-10s → %s (%d h)%n",
                    dia, horarioConSwitchClasico(dia), horasConSwitchModerno(dia));
        }
    }
}

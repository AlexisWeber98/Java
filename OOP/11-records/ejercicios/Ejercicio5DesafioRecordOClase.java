/*
 * ============================================================================
 * Módulo 11 - Records | Ejercicio 5: Desafío ¿record o clase?
 * ============================================================================
 *
 * ENUNCIADO:
 * Analizá los tres escenarios de abajo. Para cada uno decidí si lo modelás
 * con record o con clase tradicional, JUSTIFICÁ tu elección en un comentario
 * dentro del main y escribí una versión mínima del tipo (declaralo anidado,
 * como en los ejercicios anteriores).
 *
 * ESCENARIO A - Cuenta bancaria:
 * Tiene un titular y un saldo. El saldo cambia con cada depósito o extracción;
 * dos cuentas son "la misma" si comparten identidad (la misma cuenta del
 * banco), aunque su saldo haya cambiado muchas veces.
 *
 * ESCENARIO B - Coordenada geográfica:
 * Tiene latitud y longitud. Un punto (-34.6, -58.4) ES ese valor siempre;
 * dos coordenadas con los mismos números representan exactamente el mismo lugar.
 *
 * ESCENARIO C - Sesión de usuario:
 * Tiene un usuario y un indicador de login. El indicador cambia cuando la
 * persona inicia o cierra sesión.
 *
 * REQUISITOS:
 * - Para CADA escenario: comentario con tu elección (record o clase) y el porqué.
 * - Una versión mínima de cada tipo: los campos justos para el escenario.
 * - En el main, un uso breve de cada tipo que demuestre su comportamiento clave.
 *
 * PISTAS:
 * - Preguntate: ¿el estado cambia con el tiempo o es un valor fijo?
 * - ¿Importa la IDENTIDAD (esta cuenta, esta sesión) o basta con el VALOR?
 * - Regla práctica: record = valor inmutable transparente;
 *   clase = cuando hay estado mutable que evoluciona.
 */
public class Ejercicio5DesafioRecordOClase {

    public static void main(String[] args) {
        System.out.println("Desafío pendiente: elegí record o clase para cada");
        System.out.println("escenario, justificá y codificá las versiones mínimas.");

        // ESCENARIO A - Cuenta bancaria
        // TU ELECCIÓN Y JUSTIFICACIÓN: ...
        // TODO: declará el tipo mínimo (anidado) y usalo acá

        // ESCENARIO B - Coordenada geográfica
        // TU ELECCIÓN Y JUSTIFICACIÓN: ...
        // TODO: declará el tipo mínimo (anidado) y usalo acá

        // ESCENARIO C - Sesión de usuario
        // TU ELECCIÓN Y JUSTIFICACIÓN: ...
        // TODO: declará el tipo mínimo (anidado) y usalo acá
    }
}

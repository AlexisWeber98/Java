/*
 * ============================================================================
 * Módulo 11 - Records | Ejercicio 2: Record con validación: RangoTemperatura
 * ============================================================================
 *
 * ENUNCIADO:
 * Un rango de temperaturas sólo tiene sentido si el mínimo no supera al máximo.
 * Completá el CONSTRUCTOR COMPACTO del record RangoTemperatura(double min, double max)
 * para rechazar con IllegalArgumentException todo intento de crear un rango
 * donde min sea mayor que max. En el main: creá un rango válido y mostralo;
 * después intentá crear uno inválido dentro de un try/catch y reportá el error.
 *
 * REQUISITOS:
 * - Escribir el constructor compacto (sin paréntesis después del nombre).
 * - Lanzar IllegalArgumentException con un mensaje claro si min > max.
 * - Capturar el intento inválido con try/catch e imprimir el mensaje de la excepción.
 *
 * PISTAS:
 * - La sintaxis del constructor compacto es: public RangoTemperatura { ... }
 * - Corre ANTES de que los parámetros se asignen a los campos; validás min y
 *   max directamente y no escribís ninguna asignación this.x = x.
 * - Si el constructor lanza la excepción, el objeto nunca llega a existir.
 */
public class Ejercicio2RecordConValidacion {

    public static void main(String[] args) {
        // TODO: creá un rango válido (por ejemplo de 18.0 a 32.0) y mostralo

        // TODO: dentro de un try/catch, intentá crear un rango inválido (min > max)

        // TODO: en el catch, avisá que el rango fue rechazado e imprimí getMessage()
    }

    record RangoTemperatura(double min, double max) {

        // TODO: escribí acá el constructor compacto con la validación
    }
}

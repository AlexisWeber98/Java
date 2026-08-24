/*
 * Módulo 13 — Conversiones de tipos
 * Ejercicio 2: Pérdida silenciosa
 *
 * ENUNCIADO:
 * Este programa calcula el total de una factura y NO da ningún error de
 * compilación ni de ejecución... pero el total está MAL. En algún punto hay
 * una conversión que recorta información y nadie te avisa. Tu misión:
 * encontrar dónde se pierde, documentarlo y arreglarlo con los tipos correctos.
 *
 * REQUISITOS:
 * - Ejecutá el programa tal cual está y mirá el total impreso.
 * - Escribí un comentario en la línea EXACTA donde se pierde información,
 *   indicando cuánto se perdió.
 * - Corregí el cálculo usando el tipo adecuado para precios (pista: double).
 * - No modifiques los valores de entrada (precioUnitario ni cantidad).
 *
 * PISTAS:
 * - El cast (int) trunca hacia cero: 999.99 se convierte en 999, y ese .99
 *   desaparece para siempre.
 * - Una vez perdida la información, ninguna operación posterior la recupera.
 * - Un precio necesita representar decimales: pensá qué tipo le corresponde.
 */
public class Ejercicio2PerdidaSilenciosa {

    public static void main(String[] args) {
        double precioUnitario = 999.99;
        int cantidad = 3;

        // Conversión problemática: guardamos el precio en un int.
        int precioEntero = (int) precioUnitario;   // TODO 1: comentá acá qué se pierde y cuánto

        int totalFactura = precioEntero * cantidad;
        System.out.println("Precio unitario declarado: " + precioUnitario);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("TOTAL FACTURA: " + totalFactura);   // TODO 2: ¿cuánto esperaba el cliente?

        // TODO 3: corregí el cálculo usando double en lugar de int
        //   (cambiá los tipos necesarios y eliminá el cast). Volvé a ejecutar:
        //   el total correcto ronda 2999.97.
        //
        // TODO 4: en un comentario final respondé: ¿por qué el compilador no
        //   nos avisó? (pista: el cast explícito es una decisión que ASUME el programador).
    }
}

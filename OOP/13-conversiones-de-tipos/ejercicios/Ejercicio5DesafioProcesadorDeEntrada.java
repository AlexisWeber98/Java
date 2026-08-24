/*
 * Módulo 13 — Conversiones de tipos
 * Ejercicio 5: Desafío — Procesador de entrada
 *
 * ENUNCIADO:
 * Te llega un lote de datos sucio: {"12", "3.5", "abc", "", "-7"}.
 * Escribí un procesador que recorra TODO el arreglo y produzca un informe:
 *
 *   - suma los enteros válidos (y cuenta cuántos fueron),
 *   - suma los decimales válidos (y cuenta cuántos fueron),
 *   - lista las entradas rechazadas junto con el MOTIVO del rechazo.
 *
 * REQUISITOS:
 * - Cero crashes: toda entrada inválida termina en la lista de rechazados.
 * - Distinguir al menos dos motivos: texto vacío/nulo y texto que no es número.
 * - Un entero válido NO debe contarse también como decimal.
 * - Imprimir un informe final con cantidades, sumas y rechazados detallados.
 *
 * PISTAS:
 * - Orden sugerido de pruebas por entrada: primero vacío/nulo, luego
 *   Integer.parseInt, después Double.parseDouble.
 * - Podés reutilizar la idea del ejercicio 3 (variante booleana) o escribir
 *   helpers que devuelvan null cuando fallan... ¿te suena a wrapper?
 * - Acumuladores: int sumaEnteros y double sumaDecimales.
 * - Una List<String> te sirve para ir juntando los rechazados con su motivo.
 */
import java.util.ArrayList;
import java.util.List;

public class Ejercicio5DesafioProcesadorDeEntrada {

    public static void main(String[] args) {
        String[] entrada = {"12", "3.5", "abc", "", "-7"};

        int sumaEnteros = 0;
        int contadorEnteros = 0;
        double sumaDecimales = 0.0;
        int contadorDecimales = 0;
        List<String> rechazados = new ArrayList<>();

        // TODO 1: recorré la entrada y clasificá cada elemento:
        //   vacío o nulo          -> rechazar con motivo "texto vacío"
        //   entero válido         -> sumar en sumaEnteros e incrementar contador
        //   decimal válido        -> sumar en sumaDecimales e incrementar contador
        //   cualquier otra cosa   -> rechazar con motivo "no es un número"

        // TODO 2: imprimí el informe final: total recibido, enteros válidos
        //   con su suma, decimales válidos con su suma y cada rechazado con motivo.

        System.out.println("(implementá el procesamiento: entradas recibidas = " + entrada.length + ")");
    }
}

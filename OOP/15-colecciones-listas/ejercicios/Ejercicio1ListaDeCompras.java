/*
 * ============================================================================
 * Ejercicio 1 — Lista de compras
 * ============================================================================
 *
 * ENUNCIADO:
 *   Vas a hacer las compras de la semana y necesitás administrar tu lista
 *   con un ArrayList<String>. El programa debe:
 *     1. Agregar 5 productos a la lista.
 *     2. Quitar uno de esos productos (cambio de planes).
 *     3. Insertar un producto olvidado en una posición intermedia (no al final).
 *     4. Imprimir la lista final numerada, con el formato: "1. Papa".
 *
 * REQUISITOS:
 *   - Usar ArrayList<String> declarado contra la interfaz List.
 *   - Practicar add(elemento), add(indice, elemento) y remove(elemento).
 *   - La numeración sale del índice del bucle (i + 1), no se escribe a mano.
 *
 * PISTAS:
 *   - lista.add("Papa") agrega al final; lista.add(1, "Huevos") inserta en
 *     esa posición y corre el resto un lugar.
 *   - lista.remove("Tomate") elimina la PRIMERA aparición exacta del texto.
 *     Cuidado: también existe remove(int), que remueve POR ÍNDICE (sobrecarga).
 *   - El tamaño de la lista se consulta con lista.size().
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;

public class Ejercicio1ListaDeCompras {

    public static void main(String[] args) {
        List<String> listaDeCompras = new ArrayList<>();

        // TODO 1: agregá 5 productos con listaDeCompras.add(...)

        // TODO 2: quitá uno de los productos con listaDeCompras.remove(...)

        // TODO 3: insertá un producto olvidado en una posición intermedia con
        //         listaDeCompras.add(indice, producto)

        // TODO 4: mostrá el resultado llamando a imprimirNumerada(listaDeCompras)
    }

    /**
     * Imprime la lista con numeración empezando en 1.
     */
    private static void imprimirNumerada(List<String> items) {
        // TODO: recorré la lista con un for clásico e imprimí "(i + 1). item"
    }
}

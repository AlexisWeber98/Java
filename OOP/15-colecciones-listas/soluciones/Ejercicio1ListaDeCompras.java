/*
 * ============================================================================
 * Ejercicio 1 — Lista de compras (SOLUCIÓN)
 * ============================================================================
 * Operaciones básicas de ArrayList: add al final, add en posición, remove
 * por valor e impresión numerada usando el índice del bucle.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;

public class Ejercicio1ListaDeCompras {

    public static void main(String[] args) {
        List<String> listaDeCompras = new ArrayList<>();

        // 1. add(elemento) siempre agrega al final de la lista.
        listaDeCompras.add("Papa");
        listaDeCompras.add("Cebolla");
        listaDeCompras.add("Tomate");
        listaDeCompras.add("Lechuga");
        listaDeCompras.add("Zanahoria");

        // 2. remove(valor) quita la primera aparición exacta del texto.
        //    Ojo con la sobrecarga: remove(int) remueve por ÍNDICE, no por valor.
        listaDeCompras.remove("Tomate"); // se acabó el presupuesto del tomate

        // 3. add(indice, elemento) inserta en esa posición y desplaza al resto.
        listaDeCompras.add(1, "Huevos"); // los habíamos olvidado

        // 4. Impresión numerada: el número es índice + 1.
        imprimirNumerada(listaDeCompras);
    }

    private static void imprimirNumerada(List<String> items) {
        System.out.println("Lista de compras (" + items.size() + " productos):");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }
}

/*
 * ============================================================================
 *  Ejercicio 2 — Par<K, V>: dos tipos, una sola clase
 * ============================================================================
 *
 *  ENUNCIADO
 *  --------
 *  Un Map guarda pares clave→valor... y vos hoy vas a construir el par.
 *  Creá la clase genérica Par<K, V> (inmutable) con:
 *
 *      - Constructor Par(K clave, V valor)
 *      - getClave() y getValor()
 *      - mostrar() → imprime la entrada con formato lindo: "clave → valor"
 *
 *  En el main armá entradas estilo diccionario:
 *      1. Tres Par<String, String>: término técnico → significado.
 *      2. Un Par<String, Double>: producto → precio (K y V son INDEPENDIENTES).
 *
 *  REQUISITOS
 *  ----------
 *      - Campos private final: un par no cambia después de crearse.
 *      - Los DOS parámetros de tipo van separados por coma: <K, V>
 *      - Guardá los pares en un array y recorralo con un for para mostrarlos.
 *
 *  PISTAS
 *  ------
 *      - class Par<K, V> { ... } — cada letra es una variable de tipo propia.
 *      - ¿String como K y Double como V? Perfectamente legal: son libres.
 *      - Para imprimir lindo probá System.out.printf("%-15s → %s%n", ...)
 * ============================================================================
 */
public class Ejercicio2ParGenerico {

    // TODO: declará acá tu clase estática Par<K, V>.

    public static void main(String[] args) {
        // TODO 1: creá tres pares término → significado.


        // TODO 2: creá un par producto → precio.


        // TODO 3: guardalos todos en un array de Par<String, ?>... o mejor,
        //         dos arrays separados, y mostrá cada entrada con mostrar().


        System.out.println("Completá los TODOs y volvé a ejecutar.");
    }
}

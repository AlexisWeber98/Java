/*
 * ============================================================================
 *  Ejercicio 1 — Tu primera clase genérica: Caja<T>
 * ============================================================================
 *
 *  ENUNCIADO
 *  --------
 *  Colecciones como ArrayList son genéricas: les decís entre < y > qué tipo
 *  guardan y el compilador te cuida. Hoy vas a construir la tuya desde cero.
 *
 *  Creá una clase genérica Caja<T> que guarde UN solo valor de tipo T:
 *
 *      guardar(T valor)   → guarda un valor en la caja.
 *      obtener()          → devuelve el valor guardado (null si está vacía).
 *      estaVacia()        → true si no se guardó nada.
 *
 *  Después, en el main:
 *      1. Usá Caja<String> para guardar un nombre y mostrarlo SIN castear.
 *      2. Usá Caja<Double> para guardar una temperatura y mostrarla.
 *      3. Mostrá estaVacia() antes y después de guardar.
 *
 *  REQUISITOS
 *  ----------
 *      - Caja<T> con UN campo privado de tipo T (declarala como clase
 *        estática adentro de esta misma, así el archivo sigue autónomo).
 *      - CERO casts en tu código cliente.
 *      - Al final, dejá comentada la línea que NO compila: intentar guardar
 *        un entero en la caja de String debe fallar EN COMPILACIÓN.
 *
 *  PISTAS
 *  ------
 *      - La declaración es class Caja<T> { ... }: esa <T> declara la
 *        variable de tipo; después la usás como cualquier tipo.
 *      - El diamante hace el trabajo sucio al instanciar: new Caja<>()
 *      - ¿Por qué obtener() devuelve String sin cast? Porque el chequeo de
 *        tipo ya lo hizo el compilador, no la máquina virtual en ejecución.
 * ============================================================================
 */
public class Ejercicio1PrimeraCajaGenerica {

    // TODO: declará acá tu clase estática Caja<T> con los tres métodos.


    public static void main(String[] args) {
        // TODO 1: creá una Caja<String>, mostrá que arranca vacía,
        //         guardá un nombre y obtenelo sin cast.


        // TODO 2: repetí la jugada con Caja<Double> y una temperatura.


        // TODO 3: pensá (y anotá en un comentario) qué pasaría si intentaras
        //         guardar un número entero en la caja de String. Escribí la
        //         línea comentada y explicá por qué javac la rechaza.


        System.out.println("Completá los TODOs y volvé a ejecutar.");
    }
}

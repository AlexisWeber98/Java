/*
 * ============================================================================
 * Módulo 03 — Ejercicio 1: Métodos con retorno (SOLUCIÓN)
 * ============================================================================
 *
 * ENUNCIADO:
 *   Implementá dos métodos utilitarios y usalos desde main:
 *     - doble(int numero): devuelve el número multiplicado por 2.
 *     - esPar(int numero): devuelve true si el número es par, false si no.
 *
 * REQUISITOS:
 *   1. Cada método debe declarar su tipo de retorno (int y boolean).
 *   2. Deben ser static para poder llamarlos desde main sin crear objetos.
 *   3. Usá la sentencia return para entregar el resultado.
 *   4. Desde main imprimí al menos dos llamadas de cada método.
 *   5. No repitas lógica de cálculo en main: todo cálculo vive en su método.
 *
 * PISTAS:
 *   - Firma general: static tipoRetorno nombreMetodo(tipo parametro) { ... }
 *   - El resto de dividir por 2 (numero % 2) te dice si el número es par.
 *   - Un método con retorno se puede imprimir directo:
 *     System.out.println(doble(7));
 */
public class Ejercicio1MetodosConRetorno {

    /**
     * Clave: el método declara int como tipo de retorno y entrega el valor
     * con return. Quien invoca recibe un dato utilizable, no una impresión.
     */
    static int doble(int numero) {
        return numero * 2;
    }

    /**
     * La expresión numero % 2 == 0 YA ES un boolean: se puede devolver
     * directamente, sin escribir if/else innecesario.
     */
    static boolean esPar(int numero) {
        return numero % 2 == 0;
    }

    public static void main(String[] args) {
        System.out.println("doble(7)  -> " + doble(7));
        System.out.println("doble(25) -> " + doble(25));

        System.out.println("esPar(8)  -> " + esPar(8));
        System.out.println("esPar(15) -> " + esPar(15));

        // El valor devuelto es una expresión más: se puede combinar y guardar.
        int sumaDeDobles = doble(5) + doble(6);
        System.out.println("doble(5) + doble(6) -> " + sumaDeDobles);
    }
}

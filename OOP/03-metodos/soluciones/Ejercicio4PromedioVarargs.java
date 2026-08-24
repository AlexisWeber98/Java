/*
 * ============================================================================
 * Módulo 03 — Ejercicio 4: Varargs (promedio de notas) (SOLUCIÓN)
 * ============================================================================
 *
 * ENUNCIADO:
 *   Implementá promedioNotas(double... notas) que devuelva el promedio de
 *   la cantidad de notas que sea: cero, una o muchas.
 *
 * REQUISITOS:
 *   1. Firma con varargs: static double promedioNotas(double... notas).
 *   2. Lista vacía NO puede romper el programa: devolvé un valor por
 *      defecto y avisá por consola (documentá la decisión).
 *   3. Desde main llamá al método con 0, 1 y muchas notas.
 *   4. No armés arreglos a mano en main: el varargs los crea por vos.
 *
 * PISTAS:
 *   - Dentro del método, notas es un double[] común: length y for-each
 *     funcionan como siempre.
 *   - La llamada promedioNotas() es válida: el arreglo queda de largo 0.
 *   - Reglas del varargs: uno solo por método y siempre último parámetro.
 *   - Podés pasar enteros en la llamada (7, 10): se ensanchan a double.
 */
public class Ejercicio4PromedioVarargs {

    /**
     * El varargs compila a un double[] común. El caso vacío (length == 0)
     * dividiría por cero al promediar: lo defendemos devolviendo 0 como
     * valor por defecto y avisando por consola. Sin ese guard, una llamada
     * "inocente" como promedioNotas() terminaría en NaN silencioso.
     */
    static double promedioNotas(double... notas) {
        if (notas.length == 0) {
            System.out.println("[AVISO] Lista de notas vacía: se devuelve 0.");
            return 0;
        }

        double suma = 0;
        for (double nota : notas) {
            suma += nota;
        }
        return suma / notas.length;
    }

    public static void main(String[] args) {
        // Cero argumentos: el arreglo existe pero mide 0.
        System.out.println("promedioNotas()                -> "
                + promedioNotas());

        // Un argumento: el promedio es la propia nota.
        System.out.println("promedioNotas(8.5)             -> "
                + promedioNotas(8.5));

        // Muchos argumentos: los enteros se ensanchan a double solos.
        System.out.println("promedioNotas(7, 9.5, 4, 10, 6.5) -> "
                + promedioNotas(7, 9.5, 4, 10, 6.5));
    }
}

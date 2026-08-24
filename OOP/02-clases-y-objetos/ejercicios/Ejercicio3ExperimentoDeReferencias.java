/*
 * ============================================================================
 *  Ejercicio 3 — Experimento de referencias
 *  Módulo 02 · Clases y objetos
 * ============================================================================
 *
 *  ENUNCIADO
 *  Este ejercicio NO se escribe: se PREDICE. Abajo tenés un experimento ya
 *  terminado que usa DOS variables para el MISMO objeto. Tu trabajo:
 *
 *  REQUISITOS
 *  1. ANTES de ejecutar: leé el código línea por línea y completá tu
 *     predicción en la ZONA PREDICCIÓN (TODO A, B y C).
 *  2. Ejecutá: java Ejercicio3ExperimentoDeReferencias.java
 *  3. COMPARÁ: anotá en TODO D qué imprimió realmente. Si erraste,
 *     ¡mejor todavía! Ahí está el aprendizaje.
 *
 *  PISTAS
 *  - Mascota b = a; NO copia el objeto: copia LA REFERENCIA.
 *  - Dos referencias al mismo objeto = dos nombres para la misma mascota.
 *  - Contá cuántos new hay en todo el programa. Ese número es tu pista
 *    para el TODO C.
 */
public class Ejercicio3ExperimentoDeReferencias {

    public static void main(String[] args) {
        // ================== ZONA PREDICCIÓN ==================
        // TODO A: ¿qué va a imprimir el println del paso 1?
        //         Predicción:
        //
        // TODO B: ¿y el del paso 2?
        //         Predicción:
        //
        // TODO C: ¿cuántos objetos Mascota existen en memoria?
        //         Predicción:
        // ======================================================

        Mascota a = new Mascota();
        a.nombre = "Rocco";

        Mascota b = a;              // paso clave

        b.nombre = "Firulais";      // mutación por la referencia b

        // paso 1
        System.out.println("a.nombre = " + a.nombre);

        // paso 2
        System.out.println("b.nombre = " + b.nombre);

        // TODO D: ejecutá y anotá acá qué imprimió realmente + tu explicación.
    }

    static class Mascota {
        String nombre;
    }
}

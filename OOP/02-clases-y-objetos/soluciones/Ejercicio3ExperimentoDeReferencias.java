/*
 * ============================================================================
 *  Ejercicio 3 — Experimento de referencias · SOLUCIÓN
 *  Módulo 02 · Clases y objetos
 * ============================================================================
 *
 *  EXPLICACIÓN (leela recién después de intentar tu propia predicción)
 *  Hay UN solo new => UN solo objeto en memoria. a y b son dos VARIABLES que
 *  guardan la MISMA referencia. Al asignar b = a se copia el valor de la
 *  referencia (la dirección), nunca el objeto. Por eso cambiar nombre vía b
 *  se ve también vía a: son dos etiquetas puestas en la misma mochila.
 *
 *  SALIDA REAL DEL EXPERIMENTO
 *    a.nombre = Firulais      <- sí, Firulais: a y b miran al mismo objeto
 *    b.nombre = Firulais
 *    Objetos en memoria: 1. Referencias a ese objeto: 2.
 */
// Sin public y con nombre Solucion*: así ejercicios y soluciones compilan juntos.
class Solucion3ExperimentoDeReferencias {

    public static void main(String[] args) {
        Mascota a = new Mascota();
        a.nombre = "Rocco";

        Mascota b = a;          // alias: segunda referencia, cero objetos nuevos

        b.nombre = "Firulais";  // mutación visible desde CUALQUIERA de las dos

        System.out.println("a.nombre = " + a.nombre);   // Firulais
        System.out.println("b.nombre = " + b.nombre);   // Firulais

        // BONUS: rompamos el alias reasignando b a un objeto NUEVO
        b = new Mascota();      // ahora sí: segundo objeto
        b.nombre = "Pelusa";
        System.out.println("Tras el bonus, a.nombre = " + a.nombre);   // Firulais
        System.out.println("Tras el bonus, b.nombre = " + b.nombre);   // Pelusa
        // Conclusión del bonus: el alias dura mientras ambas variables apunten
        // al mismo objeto; una reasignación separa sus destinos para siempre.
    }

    static class Mascota {
        String nombre;
    }
}

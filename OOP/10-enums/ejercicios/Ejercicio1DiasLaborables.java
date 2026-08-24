/*
 * ============================================================================
 * Ejercicio 1 — ¿Qué días hay que trabajar?
 * ============================================================================
 *
 * ENUNCIADO
 * Un enum no es solo una lista de constantes: también puede llevar métodos,
 * igual que cualquier clase. Vas a declarar un enum DiaSemana con los siete
 * días de la semana y darle un método esLaborable() que responda true de
 * lunes a viernes y false el fin de semana. Después recorré la semana entera
 * e imprimí el veredicto de cada día.
 *
 * REQUISITOS
 *   1. Enum DiaSemana con las siete constantes, una por línea.
 *   2. Método esLaborable() que devuelva boolean: true de LUNES a VIERNES,
 *      false para SABADO y DOMINGO.
 *   3. En main, recorrer todos los días con values() e imprimir, por ejemplo:
 *         LUNES     -> es laborable
 *         SABADO    -> no es laborable
 *   4. Contar cuántos días laborables tiene la semana e imprimir el total.
 *
 * PISTAS
 *   - values() te devuelve un array con todas las constantes del enum.
 *   - Para saber si es laborable alcanza con comparar: this != SABADO &&
 *     this != DOMINGO. Los enums se comparan con ==, no con equals().
 *   - Un for mejorado (for (DiaSemana dia : ...)) queda bien prolijo acá.
 *   - Las constantes van en MAYÚSCULAS y sin tildes: MIERCOLES, SABADO.
 */
public class Ejercicio1DiasLaborables {

    enum DiaSemana {
        LUNES,
        MARTES,
        MIERCOLES,
        JUEVES,
        VIERNES,
        SABADO,
        DOMINGO;

        // TODO: implementá esLaborable() -> true de lunes a viernes,
        //       false para SABADO y DOMINGO.
        boolean esLaborable() {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("La semana de una persona con proyectos:");
        int diasLaborables = 0;

        for (DiaSemana dia : DiaSemana.values()) {
            // TODO: imprimí el día y su veredicto usando dia.esLaborable(),
            //       con el formato "LUNES -> es laborable".
            // TODO: si el día es laborable, incrementá diasLaborables.
        }

        System.out.println();
        System.out.println("Total de días laborables: " + diasLaborables);
    }
}

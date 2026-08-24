/*
 * ============================================================================
 * Módulo 22 · Arquitectura en capas — Ejercicio 1
 * ¿De qué capa es esta regla?
 * ============================================================================
 *
 * ENUNCIADO
 * Antes de tirar código, hay que entrenar el ojo arquitectónico. Acá tenés
 * ocho reglas o comportamientos típicos de un sistema real. Para cada uno,
 * completá el comentario indicando a qué capa pertenece:
 *
 *   - PRESENTACIÓN : todo lo que dialoga con el usuario o con otro sistema:
 *                    menús, formatos de pantalla, JSON/HTML.
 *   - NEGOCIO      : las reglas del dominio, las que valdrían igual aunque
 *                    cambie la interfaz y el almacenamiento.
 *   - PERSISTENCIA : cómo se guardan y recuperan los datos: tablas, archivos,
 *                    memoria, conexiones y reintentos.
 *
 * Escribí tu respuesta debajo de cada regla, en la línea "Capa: ???", más una
 * justificación de una sola línea.
 *
 * REQUISITOS
 *   1. Asignar capa a las 8 reglas.
 *   2. Justificar cada elección en una línea.
 *   3. Marcar con (?) las que considerés discutibles y explicar la duda.
 *
 * PISTAS
 *   - Test del cambio: ¿la regla cambiaría si pasamos de consola a web? Es
 *     presentación. ¿Cambiaría si pasamos de tabla SQL a archivo? Es
 *     persistencia. ¿Sobrevive a los dos cambios? Es negocio.
 *   - Ojo: la MISMA información puede tocarse desde varias capas. Formatear
 *     un teléfono PARA MOSTRARLO es presentación; normalizarlo ANTES DE
 *     GUARDARLO es negocio. Lo que cambia es el porqué, no el dato.
 *   - Hay dos reglas puestas a propósito en la frontera: discutirlas vale
 *     tanto como acertarlas.
 * ============================================================================
 */

/**
 * Ejercicio de análisis: no hay lógica que programar, solo decisiones que tomar.
 * Corrélo si querés ver el recordatorio impreso; el trabajo real está en los
 * comentarios de abajo.
 */
public class Ejercicio1DeQueCapaEsEstaRegla {

    // -------------------------------------------------------------------------
    // Regla 1: formatear un número de teléfono como "(011) 4444-5555" para
    //          mostrarlo en pantalla.
    // Capa: ???
    // Justificación: ...
    //
    // Regla 2: rechazar una venta si el stock quedaría negativo.
    // Capa: ???
    // Justificación: ...
    //
    // Regla 3: insertar una fila en la tabla TAREAS de la base de datos.
    // Capa: ???
    // Justificación: ...
    //
    // Regla 4: mostrar el menú de opciones y leer la opción elegida.
    // Capa: ???
    // Justificación: ...
    //
    // Regla 5: aplicar 10% de descuento cuando el pedido lleva 100 unidades o más.
    // Capa: ???
    // Justificación: ...
    //
    // Regla 6: convertir la lista de productos a JSON para contestarle a una app.
    // Capa: ???   <- discutible, pensala bien
    // Justificación: ...
    //
    // Regla 7: exigir email con formato válido para dar de alta un cliente.
    // Capa: ???   <- discutible, pensala bien
    // Justificación: ...
    //
    // Regla 8: reintentar 3 veces la conexión a la base de datos si falla.
    // Capa: ???
    // Justificación: ...
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        System.out.println("Ejercicio 1 — ¿De qué capa es esta regla?");
        System.out.println("Abrí este archivo y completá capa + justificación para cada una de las 8 reglas.");
        System.out.println("Después compará con soluciones/Solucion1DeQueCapaEsEstaRegla.java");
    }
}

/*
 * ============================================================================
 * SOLUCIÓN · Módulo 22 · Ejercicio 1 — ¿De qué capa es esta regla?
 * ============================================================================
 * Respuestas justificadas + discusión de los dos casos borde (reglas 6 y 7).
 * ============================================================================
 */
public class Solucion1DeQueCapaEsEstaRegla {

    // -------------------------------------------------------------------------
    // Regla 1: formatear teléfono "(011) 4444-5555" para pantalla → PRESENTACIÓN.
    // El negocio ya decidió cuál es el teléfono; el paréntesis y el guion son
    // detalle visual. Caso borde: si la consigna fuera "guardar siempre el
    // teléfono normalizado", la misma transformación pasaría a ser NEGOCIO,
    // porque cambiaría el porqué (invariante del dato, no estética).
    //
    // Regla 2: rechazar venta con stock negativo → NEGOCIO.
    // "No se vende más de lo que hay" es verdad con consola, web o planilla.
    //
    // Regla 3: insertar fila en TAREAS → PERSISTENCIA.
    // Detalle de almacenamiento; el negocio solo pide "registrar la tarea".
    //
    // Regla 4: mostrar menú y leer opción → PRESENTACIÓN.
    // Con una API REST esto desaparece y nadie lo extraña.
    //
    // Regla 5: descuento del 10% desde 100 unidades → NEGOCIO.
    // Política comercial pura: sobrevive a cualquier cambio de interfaz o storage.
    //
    // Regla 6: convertir productos a JSON para responderle a una app → PRESENTACIÓN.
    // (?) Discusión: JSON es formato de intercambio con el cliente, o sea
    // "hablar hacia afuera": presentación/frontera. Pero si el JSON fuera el
    // FORMATO DE GUARDADO, sería persistencia. Misma serialización, distinta
    // capa según para quién trabaja.
    //
    // Regla 7: email con formato válido → NEGOCIO (con chequeo también en la entrada).
    // (?) Discusión: el INVARIANTE "todo cliente tiene email válido" es del
    // dominio: sin él, los datos quedan inservibles. En la práctica se valida
    // dos veces: en la frontera (respuesta rápida al usuario) y en el servicio
    // (para no depender de que alguien se acuerde). Lo inaceptable es validar
    // SOLO en la pantalla: cualquier otra interfaz se saltea la regla.
    //
    // Regla 8: reintentar conexión 3 veces → PERSISTENCIA / INFRAESTRUCTURA.
    // Detalle técnico de acceso a datos. Clave: el servicio de negocio NO debe
    // enterarse de que existen los reintentos.
    // -------------------------------------------------------------------------

    /*
     * MORALEJA
     * Las capas no son carpetas decorativas: son fronteras de cambio.
     * Preguntate siempre: "si mañana cambia X, ¿cuántas capas tengo que tocar?".
     * Una regla bien ubicada se modifica en un solo lugar.
     */

    public static void main(String[] args) {
        System.out.println("Solución 1 — respuestas comentadas en el código fuente.");
        System.out.println("Reglas 1,4,6 → Presentación · 2,5,7 → Negocio · 3,8 → Persistencia.");
    }
}

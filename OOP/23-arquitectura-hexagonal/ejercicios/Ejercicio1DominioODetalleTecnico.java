/*
 * ============================================================================
 * Ejercicio 1 — ¿Es dominio o es detalle técnico?
 * ============================================================================
 *
 * Antes de escribir una sola línea de arquitectura hay que entrenar el ojo:
 * la arquitectura hexagonal empieza por separar QUÉ problema resuelve tu
 * software (el dominio) de CÓMO se conecta con el mundo (detalles técnicos).
 *
 * ENUNCIADO
 * Debajo tenés 10 enunciados típicos de un sistema real. Junto a cada uno:
 *   1) Escribí en un comentario tu clasificación: DOMINIO o DETALLE_TECNICO.
 *   2) Justificá en UNA línea. Preguntate: "si mañana cambiamos de base de
 *      datos, de framework o de pantalla, ¿este enunciado cambia?".
 *      Si cambia, no es dominio.
 *
 * REQUISITOS
 * - No escribas código: este ejercicio se responde con comentarios.
 * - Hay al menos dos enunciados frontera: si dudás, anotalo y explicá la duda.
 *
 * PISTAS
 * - El dominio expresa reglas y decisiones del NEGOCIO (donde se gana o se
 *   pierde plata, donde viven las políticas de la empresa).
 * - Un detalle técnico es el MEDIO: cómo persisto, cómo aviso, cómo muestro.
 * - Cuidado con la trampa del email: "validar que sea válido" ¿qué es?
 *
 * CÓMO COMPILAR Y CORRER (desde esta carpeta):
 *   javac *.java && java Ejercicio1DominioODetalleTecnico
 * (patrón del curso: compilamos todo el directorio y después corremos)
 */
public class Ejercicio1DominioODetalleTecnico {

    // =========================================================================
    // LOS 10 ENUNCIADOS A CLASIFICAR
    // =========================================================================

    // 1) "Calcular el recargo por mora de una cuota vencida."
    //    Clasificación: // TODO
    //    Justificación: // TODO

    // 2) "Dar formato dd/MM/aaaa a una fecha para mostrarla en pantalla."
    //    Clasificación: // TODO
    //    Justificación: // TODO

    // 3) "Guardar una fila en la tabla pedidos de la base de datos."
    //    Clasificación: // TODO
    //    Justificación: // TODO

    // 4) "Regla: un pedido con más de 10 unidades tiene un descuento del 5%."
    //    Clasificación: // TODO
    //    Justificación: // TODO

    // 5) "Enviar el email de confirmación usando SMTP."
    //    Clasificación: // TODO
    //    Justificación: // TODO

    // 6) "Antes de registrar un usuario, validar que su email tenga formato válido."
    //    Clasificación: // TODO
    //    Justificación: // TODO

    // 7) "Mostrar la grilla de productos paginada, de a 20 filas por página."
    //    Clasificación: // TODO
    //    Justificación: // TODO

    // 8) "Decidir si un préstamo supera el límite de riesgo del cliente."
    //    Clasificación: // TODO
    //    Justificación: // TODO

    // 9) "Registrar en un log cada consulta que hace un usuario."
    //    Clasificación: // TODO
    //    Justificación: // TODO

    // 10) "Calcular el precio final con impuestos según el tipo de cliente."
    //    Clasificación: // TODO
    //    Justificación: // TODO

    public static void main(String[] args) {
        System.out.println("Ejercicio de clasificación: respondé con comentarios arriba.");
        System.out.println("Cuando termines, compará con soluciones/Ejercicio1DominioODetalleTecnico.java");
    }
}

/*
 * ============================================================================
 * Solución 1 — ¿Es dominio o es detalle técnico? (respuestas)
 * ============================================================================
 *
 * Nota: la clase NO se llama igual que el archivo porque ejercicios/ y
 * soluciones/ se compilan juntos en el mismo paquete por defecto y dos
 * clases con el mismo nombre chocarían (convención ya usada en el módulo 22).
 *
 * CÓMO COMPILAR Y CORRER (desde soluciones/):
 *   javac *.java && java Ejercicio1DominioODetalleTecnico
 */
public class Ejercicio1DominioODetalleTecnico {

    // -------------------------------------------------------------------------
    // RESPUESTAS CON JUSTIFICACIÓN
    //
    // 1) DOMINIO — La mora es plata: es una política del negocio. Sobrevive a
    //    cualquier cambio de tecnología; si desaparece, el negocio cambia.
    //
    // 2) DETALLE_TECNICO — Cómo SE MUESTRA una fecha es presentación. El
    //    dominio trabaja con LocalDate "pelado"; el formato lo decide la UI.
    //
    // 3) DETALLE_TECNICO — Tablas, SQL e INSERT son infraestructura. El
    //    negocio dice "persistir el pedido"; el cómo es un detalle enchufable.
    //
    // 4) DOMINIO — Regla de descuento explícita: umbral + porcentaje. Pura
    //    política comercial.
    //
    // 5) DETALLE_TECNICO — SMTP es el medio. Ojo con la frontera: el concepto
    //    "notificar la confirmación al cliente" SÍ puede ser parte del dominio;
    //    lo técnico es el canal (SMTP) y eso vive en un adaptador.
    //
    // 6) FRONTERA, respuesta defendible: DOMINIO — Si el email válido es parte
    //    de la invariante de Usuario ("no existe usuario sin email válido"),
    //    la regla es dominio y se protege ahí. Si solo fuera sanitización de
    //    entrada cruda, sería borde. En este curso la tratamos como dominio:
    //    el caso de uso 3 la valida ANTES de guardar, vía un puerto.
    //
    // 7) FRONTERA, respuesta esperada: DETALLE_TECNICO — Paginar es una
    //    decisión de interfaz... SALVO que el negocio diga "nunca listamos más
    //    de 20 por página por normativa". Sin esa regla explícita: presentación.
    //
    // 8) DOMINIO — Política de riesgo pura: decide si el negocio asume o no
    //    una operación. Cero que ver con tecnología.
    //
    // 9) DETALLE_TECNICO — Observar y loguear es un requerimiento técnico
    //    transversal. Ninguna regla del negocio depende del log.
    //
    // 10) DOMINIO — Calcular precio final con impuestos según tipo de cliente
    //     es el corazón del negocio: de ahí sale la facturación real.
    //
    // Regla mental final: "¿cambia si cambio de BD / framework / pantalla?"
    //   SÍ   -> detalle técnico (adaptador).
    //   NO   -> dominio (el hexágono de adentro).
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        System.out.println("Respuestas del ejercicio 1: mirá los comentarios de esta clase.");
        System.out.println("Fronteras discutidas: enunciados 5, 6 y 7.");
    }
}

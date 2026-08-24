/*
 * ============================================================================
 * Ejercicio 2: Deprecar Con Elegancia
 * ============================================================================
 *
 * ENUNCIADO:
 * calcularDescuentoViejo(int) quedó obsoleta: no considera la antigüedad
 * del cliente. Tu tarea es marcarla como obsoleta DE LA FORMA CORRECTA,
 * que es la única forma en que un equipo grande sobrevive.
 *
 * REQUISITOS:
 * 1. Anotá calcularDescuentoViejo con @Deprecated.
 * 2. Documentala con Javadoc usando la etiqueta @deprecated, explicando
 *    POR QUÉ y apuntando al reemplazo con {@link ...}.
 * 3. Compilá y observá el warning en main (probá también con
 *    javac -Xlint:deprecation para verlo en detalle).
 *
 * PISTAS:
 * - @Deprecated (anotación) le avisa al compilador e IDE;
 *   @deprecated (etiqueta Javadoc) le avisa a quien lee la documentación.
 *   Se usan juntas: una sin la otra es media noticia.
 * - Un buen deprecado SIEMPRE dice cómo migrar. Deprecar sin alternativa
 *   es dejar un cartel de "prohibido pasar" sin puente.
 */
public class Ejercicio2DeprecarConElegancia {

    // TODO 1: anotala con @Deprecated.
    // TODO 2: agregale Javadoc con @deprecated + {@link #calcularDescuento(int, int)}.
    static double calcularDescuentoViejo(int precioOriginal) {
        return precioOriginal * 0.10;
    }

    static double calcularDescuento(int precioOriginal, int antiguedadAnios) {
        double porcentaje = Math.min(0.10 + antiguedadAnios * 0.01, 0.30);
        return precioOriginal * porcentaje;
    }

    public static void main(String[] args) {
        // Después de tu cambio, esta línea debería encender un warning:
        double viejo = calcularDescuentoViejo(1000);
        System.out.println("Descuento (método viejo): " + viejo);

        System.out.println("Descuento (método nuevo): " + calcularDescuento(1000, 3));
    }
}

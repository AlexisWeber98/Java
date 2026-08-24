/*
 * ============================================================================
 * Solución 2: Deprecar Con Elegancia
 * ============================================================================
 *
 * El par @Deprecated + @deprecated es el protocolo formal de "no uses esto,
 * usá aquello". El compilador y el IDE leen la anotación y marcan cada
 * llamada con tachado/warning; el Javadoc generado muestra el bloque de
 * obsolescencia con el link al reemplazo.
 *
 * Probalo con: javac -Xlint:deprecation Solucion2DeprecarConElegancia.java
 * Vas a ver exactamente qué línea llama al método viejo.
 *
 * ¿Y si necesitás eliminarlo de verdad? Primero se deprecia (una versión),
 * después se elimina (otra versión). Nunca de un salto: el warning ES el
 * periodo de gracia del equipo.
 */
public class Solucion2DeprecarConElegancia {

    /**
     * Calcula un descuento fijo del 10% sin considerar la antigüedad.
     *
     * @deprecated No considera la antigüedad del cliente y subestima el
     *             beneficio. Usar {@link #calcularDescuento(int, int)} en
     *             su lugar, que aplica hasta 30% según antigüedad.
     */
    @Deprecated(since = "2.0", forRemoval = true)
    static double calcularDescuentoViejo(int precioOriginal) {
        return precioOriginal * 0.10;
    }

    static double calcularDescuento(int precioOriginal, int antiguedadAnios) {
        double porcentaje = Math.min(0.10 + antiguedadAnios * 0.01, 0.30);
        return precioOriginal * porcentaje;
    }

    public static void main(String[] args) {
        // Llamada consciente al método viejo: el warning aparece acá.
        // En código real, esta línea estaría migrada o suprimida con
        // @SuppressWarnings("deprecation") SOLO si no queda alternativa.
        double viejo = calcularDescuentoViejo(1000);
        System.out.println("Descuento (método viejo): " + viejo);

        System.out.println("Descuento (método nuevo): " + calcularDescuento(1000, 3));
    }
}

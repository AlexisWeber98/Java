/*
 * ============================================================================
 * Módulo 11 - Records | Solución 2: Record con validación: RangoTemperatura
 * ============================================================================
 * Idea clave: el constructor compacto corre antes de la asignación automática
 * de campos; si lanza excepción, el objeto nunca existe => la invariante
 * min <= max queda garantizada desde la creación.
 */
public class Solucion2RecordConValidacion {

    public static void main(String[] args) {
        RangoTemperatura confort = new RangoTemperatura(18.0, 32.0);
        System.out.println("Rango válido creado -> " + confort);

        try {
            RangoTemperatura absurdo = new RangoTemperatura(40.0, 15.0);
            System.out.println("Esto nunca se imprime: " + absurdo);
        } catch (IllegalArgumentException excepcion) {
            System.out.println("Intento rechazado por la validación: " + excepcion.getMessage());
        }
    }

    record RangoTemperatura(double min, double max) {

        public RangoTemperatura {
            if (min > max) {
                throw new IllegalArgumentException(
                        "El mínimo (" + min + ") no puede ser mayor que el máximo (" + max + ")");
            }
            // No hace falta this.min = min: el record lo asigna solo al terminar.
        }
    }
}

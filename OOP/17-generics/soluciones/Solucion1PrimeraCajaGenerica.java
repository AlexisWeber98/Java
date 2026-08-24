/*
 * ============================================================================
 *  Ejercicio 1 — Solución: Caja<T>, tu primera clase genérica
 * ============================================================================
 *
 *  IDEA CLAVE: la <T> convierte la clase en una PLANTILLA parametrizada por
 *  tipo. Una sola implementación sirve para infinitos tipos, y el compilador
 *  hace de portero: lo que no tipa bien, no entra.
 * ============================================================================
 */
public class Solucion1PrimeraCajaGenerica {

    /**
     * Caja genérica. La <T> es una variable de tipo que se resuelve al
     * instanciar: new Caja<String>() "congela" T = String para esa caja.
     */
    static class Caja<T> {
        private T contenido;

        public void guardar(T valor) {
            this.contenido = valor;
        }

        /** Devuelve el valor guardado o null si la caja está vacía. Sin cast: T ya es un tipo conocido. */
        public T obtener() {
            return contenido;
        }

        public boolean estaVacia() {
            return contenido == null;
        }
    }

    public static void main(String[] args) {
        // --- Con String: la caja "sabe" que adentro hay texto ---
        Caja<String> cajaNombre = new Caja<>();
        System.out.println("¿Vacía antes de guardar?    -> " + cajaNombre.estaVacia());
        cajaNombre.guardar("Compilando ando");
        System.out.println("¿Vacía después de guardar?  -> " + cajaNombre.estaVacia());

        String nombre = cajaNombre.obtener();   // mirá: ni un solo cast
        System.out.println("Contenido de la caja        -> " + nombre);

        // --- Con Double: misma clase, otro tipo, cero código duplicado ---
        Caja<Double> cajaTemperatura = new Caja<>();
        cajaTemperatura.guardar(23.7);
        Double temperatura = cajaTemperatura.obtener();   // tampoco cast acá
        System.out.println("Temperatura registrada      -> " + temperatura + " °C");

        /*
         * --- La prueba de fuego: el compilador es el portero ---
         *
         * cajaNombre.guardar(42);
         *
         * Si la descomentás, javac responde:
         *
         *   error: incompatible types: int cannot be converted to String
         *
         * Antes de los genéricos (Java 4 hacia atrás) esto COMPILABA usando
         * Object y explotaba en ejecución con ClassCastException. Hoy el
         * error aparece ANTES de correr el programa. Eso es seguridad de
         * tipos en compilación: el bug más barato que existe.
         */
    }
}

/*
 * ============================================================================
 *  Solución 2 — Setter con validación
 * ============================================================================
 *
 *  IDEA CLAVE
 *  El setter es el único punto de entrada del estado: ahí vive la regla.
 *  Encapsular sin validar es tener portón pero dejarlo abierto de noche.
 *
 *  DECISIÓN DOCUMENTADA: elegimos la OPCIÓN A (rechazar el cambio,
 *  conservar el valor anterior y avisar).
 *
 *    ¿Por qué no la B (clamp a 0)? Porque un precio de -50 casi seguro
 *    delata un error de cálculo aguas arriba. "Arreglarlo" en silencio
 *    guarda un dato que nadie pidió y esconde el bug: el programa sigue
 *    como si nada con un valor que nadie cargó. Rechazar explícito deja
 *    el problema VISIBLE y el objeto en su último estado válido.
 *
 *    Nota de diseño: acá avisamos por consola para que el ejemplo se
 *    explique solo. En un sistema real, una clase de dominio no debería
 *    imprimir: devolvería el rechazo (booleano, excepción o resultado)
 *    y quien llama decide qué mostrar. Lo retomamos en el ejercicio 4.
 * ============================================================================
 */
public class Solucion2SetterConValidacion {

    static class ProductoValidado {
        private final String nombre;
        private double precio;

        public ProductoValidado(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
        }

        public String getNombre() {
            return nombre;
        }

        public double getPrecio() {
            return precio;
        }

        /**
         * Opción A: rechazo + valor anterior intacto + aviso.
         * Invariante garantizada: después de este método, precio >= 0 SIEMPRE.
         */
        public void setPrecio(double nuevoPrecio) {
            if (nuevoPrecio < 0) {
                System.out.println("[Rechazado] Precio inválido (" + nuevoPrecio
                        + "). Se conserva el valor anterior: " + precio);
                return; // no tocamos nada: el estado queda como estaba
            }
            this.precio = nuevoPrecio;
        }
    }

    public static void main(String[] args) {
        ProductoValidado mate = new ProductoValidado("Mate imperial", 18000.0);

        System.out.println("Precio inicial: " + mate.getPrecio());

        // Intento rechazado: el setter avisa y NO toca el estado.
        mate.setPrecio(-50);
        System.out.println("Tras intentar -50   : " + mate.getPrecio()
                + "   <- intacto, el intento no coló");

        // Cambio válido: ese sí se aplica.
        mate.setPrecio(19500.0);
        System.out.println("Tras cargar 19500.0 : " + mate.getPrecio());

        // Probalo mentalmente al revés: sin importar cuántas veces llamen a
        // setPrecio con basura, es IMPOSIBLE leer un precio negativo de este
        // objeto. La regla está en UN lugar y protege a TODOS los llamadores.
    }
}

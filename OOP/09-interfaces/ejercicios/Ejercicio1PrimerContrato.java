/*
 * =============================================================================
 *  Ejercicio 1 — Tu primer contrato: la interfaz Imprimible
 *  Módulo 09 · Interfaces
 * =============================================================================
 *
 *  ENUNCIADO
 *  ---------
 *  Una interfaz es un CONTRATO: promete QUÉ se puede hacer, sin decir CÓMO.
 *    1. Declará la interfaz Imprimible con el método imprimir().
 *    2. Hacé que Documento y Factura firmen el contrato (implements) y lo
 *       cumplan CADA UNA A SU MANERA:
 *         - Documento imprime su título y su contenido.
 *         - Factura imprime número, cliente y total.
 *    3. Recorré un único arreglo Imprimible[] con un for-each e invocá
 *       imprimir() en cada elemento, sin importarte la clase concreta.
 *
 *  REQUISITOS
 *  ----------
 *    - Interfaz Imprimible con el método void imprimir().
 *    - Documento y Factura implementan Imprimible con lógica propia.
 *    - Un solo bucle sobre Imprimible[] que imprime todo por polimorfismo.
 *
 *  PISTAS
 *  ------
 *    - En la interfaz el método se declara SIN cuerpo; la clase que firma usa
 *      implements y queda OBLIGADA a implementarlo (si no, no compila).
 *    - El poder real aparece en el arreglo: Imprimible[] acepta cualquier
 *      clase que haya firmado el contrato. Eso es programar contra la interfaz.
 * =============================================================================
 */
public class Ejercicio1PrimerContrato {

    // TODO 1: declará acá la interfaz Imprimible con el método void imprimir().

    static class Documento {
        private final String titulo;
        private final String contenido;

        Documento(String titulo, String contenido) {
            this.titulo = titulo;
            this.contenido = contenido;
        }

        String getTitulo() {
            return titulo;
        }

        String getContenido() {
            return contenido;
        }

        // TODO 2: firmá el contrato arriba (implements Imprimible) e implementá
        //         imprimir() para mostrar título + contenido.
    }

    static class Factura {
        private final int numero;
        private final String cliente;
        private final double total;

        Factura(int numero, String cliente, double total) {
            this.numero = numero;
            this.cliente = cliente;
            this.total = total;
        }

        int getNumero() {
            return numero;
        }

        String getCliente() {
            return cliente;
        }

        double getTotal() {
            return total;
        }

        // TODO 3: misma tarea que Documento, pero imprimí número, cliente y total.
    }

    public static void main(String[] args) {
        System.out.println("(Stub) Completá los TODO y volvé a ejecutar este archivo.");
        // TODO 4: creá un Imprimible[] con un Documento y una Factura,
        //         recorrélo con un for-each y llamá a imprimir() en cada uno.
    }
}

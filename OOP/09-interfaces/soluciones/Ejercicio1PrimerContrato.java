/*
 * =============================================================================
 *  Ejercicio 1 — Tu primer contrato: la interfaz Imprimible (SOLUCIÓN)
 *  Módulo 09 · Interfaces
 * =============================================================================
 *
 *  Idea clave: la interfaz fija el QUÉ (imprimir); cada clase decide el CÓMO.
 *  El bucle trabaja contra el tipo Imprimible y ni se entera de qué clase
 *  concreta hay detrás de cada elemento. Eso es polimorfismo.
 * =============================================================================
 */
// Sin modificador y con sufijo Solucion: evita colisionar con el starter
// al compilar ambos directorios juntos; java Ejercicio1PrimerContrato.java
// sigue funcionando porque ejecuta la primera clase del archivo.
class Ejercicio1PrimerContratoSolucion {

    // El contrato: solo promete el QUÉ, nunca el CÓMO.
    interface Imprimible {
        void imprimir();
    }

    // Cumple el contrato mostrando título + contenido.
    static class Documento implements Imprimible {
        private final String titulo;
        private final String contenido;

        Documento(String titulo, String contenido) {
            this.titulo = titulo;
            this.contenido = contenido;
        }

        @Override
        public void imprimir() {
            System.out.printf("Documento: «%s» — %s%n", titulo, contenido);
        }
    }

    // Cumple EL MISMO contrato con SU propia lógica.
    static class Factura implements Imprimible {
        private final int numero;
        private final String cliente;
        private final double total;

        Factura(int numero, String cliente, double total) {
            this.numero = numero;
            this.cliente = cliente;
            this.total = total;
        }

        @Override
        public void imprimir() {
            System.out.printf("Factura Nº %d | Cliente: %s | Total: $%.2f%n",
                    numero, cliente, total);
        }
    }

    public static void main(String[] args) {
        // El arreglo habla en términos del CONTRATO, no de clases concretas.
        Imprimible[] imprimibles = {
                new Documento("TP Final", "Interfaces aplicadas al dominio"),
                new Factura(1001, "Ale", 25_000.0)
        };

        for (Imprimible imprimible : imprimibles) {
            // Un solo mensaje para todos; cada objeto responde a su manera.
            imprimible.imprimir();
        }
    }
}

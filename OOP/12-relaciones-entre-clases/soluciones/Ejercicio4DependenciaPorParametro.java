/*
 * ============================================================================
 * Ejercicio 4 (SOLUCIÓN) — Dependencia de uso: Impresora + Documento
 * ============================================================================
 * Gemelo de ejercicios/Ejercicio4DependenciaPorParametro.java (clase sin
 * public para permitir la compilación conjunta de ejercicios/ y soluciones/).
 *
 * CONCEPTO CLAVE:
 *   Dependencia = relación "usa" transitoria. El objeto llega por parámetro,
 *   se usa durante la llamada y se descarta; jamás se guarda como atributo.
 *   En UML suele dibujarse como flecha punteada hacia Documento.
 */
class Solucion4DependenciaPorParametro {

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

        String resumen(int maxCaracteres) {
            if (contenido.length() <= maxCaracteres) {
                return contenido;
            }
            return contenido.substring(0, maxCaracteres) + "...";
        }
    }

    static class Impresora {
        private final String modelo;

        Impresora(String modelo) {
            this.modelo = modelo;
        }

        /*
         * ANTI-PATRÓN (así NO se hace):
         *
         *     private Documento ultimoDocumento;           <- ¡estado que sobra!
         *
         *     public void imprimir(Documento documento) {
         *         this.ultimoDocumento = documento;        <- dependencia convertida
         *         ...                                        en asociación encubierta
         *     }
         *
         * Por qué rompe el modelo:
         *   1) Acopla los ciclos de vida: la impresora mantiene vivo al último
         *      documento impreso aunque nadie más lo use (fuga de memoria).
         *   2) Introduce estado oculto y stale: dos impresiones seguidas ya no
         *      son independientes entre sí.
         *   3) Complica el uso concurrente: hilos distintos pisan el campo.
         *
         * La impresora correcta USA el documento y lo OLVIDA: dependencia pura.
         */
        void imprimir(Documento documento) {
            System.out.println("[" + modelo + "] Imprimiendo '" + documento.getTitulo() + "'");
            System.out.println("   " + documento.resumen(60));
        }
    }

    public static void main(String[] args) {
        Impresora impresora = new Impresora("HP LaserJet");

        Documento informe = new Documento("Informe Q3",
                "Las ventas del trimestre crecieron un 12% impulsadas por la nueva linea de productos.");
        Documento recibo = new Documento("Recibo de sueldo",
                "Sueldo basico, presentismo y horas extra del periodo en curso.");

        // Dos llamadas independientes sobre la MISMA impresora:
        // no hay forma de preguntarle qué imprimió último (¡y eso es correcto!).
        impresora.imprimir(informe);
        impresora.imprimir(recibo);

        System.out.println("(La impresora no recuerda ninguno de los dos documentos.)");
    }
}

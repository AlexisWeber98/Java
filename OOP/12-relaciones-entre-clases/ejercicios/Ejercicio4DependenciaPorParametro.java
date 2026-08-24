/*
 * ============================================================================
 * Ejercicio 4 — Dependencia de uso: Impresora que imprime un Documento
 * ============================================================================
 *
 * ENUNCIADO:
 *   La Impresora USA un Documento para trabajar y luego lo OLVIDA: no lo
 *   guarda en ningún campo. Esa relación efímera, que solo existe durante la
 *   llamada al método, es una DEPENDENCIA: la relación "usa" más débil.
 *
 * REQUISITOS:
 *   1. Documento: titulo y contenido; resumen(int maxCaracteres) devuelve el
 *      contenido recortado a ese máximo.
 *   2. Impresora.imprimir(Documento documento): imprime título y contenido;
 *      NO debe almacenar el documento en ningún campo.
 *   3. En comentarios dentro de Impresora, escribí la versión INCORRECTA
 *      (guardar el documento en un campo) y explicá por qué rompe el modelo.
 *   4. En main: una misma impresora imprime DOS documentos distintos seguidos,
 *      sin quedarse con memoria de ninguno.
 *
 * PISTAS:
 *   - Dependencia = el otro objeto aparece en la FIRMA de un método (o se usa
 *     adentro) pero nunca se vuelve atributo.
 *   - Preguntate: ¿la impresora "pertenece a" algún documento? ¿Necesita
 *     recordarlo después de imprimirlo?
 */
public class Ejercicio4DependenciaPorParametro {

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
            // TODO: devolver contenido recortado a maxCaracteres (+ "..." si se cortó)
            return "";
        }
    }

    static class Impresora {
        private final String modelo;

        Impresora(String modelo) {
            this.modelo = modelo;
        }

        void imprimir(Documento documento) {
            // TODO: imprimir "[<modelo>] Imprimiendo '<titulo>'" y el resumen
            // TODO: escribir acá (comentado) el anti-patrón de guardar el documento
        }
    }

    public static void main(String[] args) {
        Impresora impresora = new Impresora("HP LaserJet");

        Documento informe = new Documento("Informe Q3",
                "Las ventas del trimestre crecieron un 12% impulsadas por la nueva linea de productos.");
        Documento recibo = new Documento("Recibo de sueldo",
                "Sueldo basico, presentismo y horas extra del periodo en curso.");

        // La MISMA impresora, dos documentos distintos: usa y olvida cada uno.
        impresora.imprimir(informe);
        impresora.imprimir(recibo);
    }
}

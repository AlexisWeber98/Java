/*
 * ============================================================================
 *  Ejercicio 5 — Diseño justificado: Paciente
 * ============================================================================
 *
 *  ENUNCIADO
 *  Ahora sin red: diseñá la superficie pública de Paciente. Los campos ya
 *  son private (eso no se discute); el desafío es decidir QUÉ exponés,
 *  CÓMO, y JUSTIFICARLO en un comentario al lado de cada miembro.
 *
 *  Datos del dominio:
 *   - nombreCompleto : la identidad del paciente. No cambia jamás.
 *   - edad           : pasa con el tiempo (¿se "asigna" o se "cumple"?).
 *   - pesoKilogramos y alturaMetros : cambian en cada control médico.
 *   - IMC            : peso / (altura * altura). Siempre deducible del
 *                      estado; nunca se guarda.
 *
 *  REQUISITOS
 *   1. Cada getter, setter o método que agregues lleva UN comentario que
 *      diga por qué existe (o por qué NO existe).
 *   2. El constructor valida sus datos: nadie nace inválido.
 *   3. pesoKilogramos y alturaMetros aceptan cambios SOLO con valores
 *      mayores a cero.
 *   4. getIndiceMasaCorporal() calcula al vuelo. Cero campos derivados
 *      (mirá lo que pasó en el ejercicio 3).
 *   5. Decidí entre setEdad(int) o cumplirAnios() y justificalo. Pista:
 *      en la vida real nadie "setea" su edad; cumple años.
 *   6. Extendé el main para probar: datos válidos, un cambio de peso, un
 *      intento inválido y un cumpleaños.
 *
 *  PISTAS
 *   - Cada miembro público es una promesa que mantenés para siempre:
 *     exponé poco y justificá mucho.
 *   - Ante la duda, preguntate: "¿esto es una característica del objeto
 *     (getter), algo que le puede pasar (comportamiento) o alguien
 *     pisándolo desde afuera (setter dudoso)?"
 * ============================================================================
 */
public class Ejercicio5DisenoJustificado {

    /*
     * Punto de partida MÍNIMO: estado privado garantizado. Todo lo demás
     * es decisión tuya (justificada en comentarios).
     */
    static class Paciente {
        private final String nombreCompleto;
        private int edad;
        private double pesoKilogramos;
        private double alturaMetros;

        public Paciente(String nombreCompleto, int edad,
                        double pesoKilogramos, double alturaMetros) {
            // TODO 1: validá acá también. Un objeto nace válido... o no nace.
            this.nombreCompleto = nombreCompleto;
            this.edad = edad;
            this.pesoKilogramos = pesoKilogramos;
            this.alturaMetros = alturaMetros;
        }

        public String getNombreCompleto() {
            return nombreCompleto;
        }

        // TODO 2: decidí entre setEdad(int) o cumplirAnios() y justificá.

        public void setPesoKilogramos(double pesoKilogramos) {
            // TODO 3: validá antes de asignar. ¿Qué valores tienen sentido?
            this.pesoKilogramos = pesoKilogramos;
        }

        public void setAlturaMetros(double alturaMetros) {
            // TODO 4: ídem.
            this.alturaMetros = alturaMetros;
        }

        public double getIndiceMasaCorporal() {
            // TODO 5: calculado, NUNCA guardado. ¿Por qué? Ejercicio 3 ;)
            return 0;
        }
    }

    public static void main(String[] args) {
        Paciente paciente = new Paciente("Lucía Fernández", 34, 62.5, 1.68);
        System.out.println("Paciente: " + paciente.getNombreCompleto());
        System.out.println("IMC     : " + paciente.getIndiceMasaCorporal());

        // TODO 6: extendé el main: nuevo peso válido, intento inválido
        //         rechazado, cumplirAnios()... y contá en comentarios qué
        //         decisiones tomaste y por qué.
    }
}

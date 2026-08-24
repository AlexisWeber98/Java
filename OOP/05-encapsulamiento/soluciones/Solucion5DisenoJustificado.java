/*
 * ============================================================================
 *  Solución 5 — Diseño justificado: Paciente
 * ============================================================================
 *
 *  IDEA CLAVE
 *  Encapsular no es "poner private y generar getters/setters para todo".
 *  Es decidir, miembro por miembro, qué merece salir al mundo y por qué.
 *  Acá cada decisión está justificada junto al código; el checklist de
 *  antipatrones está al final del archivo.
 *
 *  Resumen de decisiones:
 *   - nombreCompleto: final + getter. Identidad inmutable => sin setter.
 *   - edad          : getter + cumplirAnios(). La edad se cumple, no se setea.
 *   - peso/altura   : getters + setters VALIDADOS (valores > 0): cambian
 *                     legítimamente en controles médicos.
 *   - IMC           : getter calculado. Dato derivado jamás guardado.
 *   - Constructor   : valida TODO: un paciente nace válido o no nace.
 * ============================================================================
 */
public class Solucion5DisenoJustificado {

    static class Paciente {
        // JUSTIFICACIÓN: identidad del paciente. Nace con él y nunca cambia,
        // por eso es final: el compilador nos impide hasta equivocarnos
        // nosotras mismas mutándolo. Sin setter posible.
        private final String nombreCompleto;

        // JUSTIFICACIÓN: mutable (el tiempo pasa), pero NO por asignación:
        // nadie "setea" su edad. Se expone comportamiento (cumplirAnios),
        // no un setEdad que permitiría cualquier disparate tipo edad = -5.
        private int edad;

        // JUSTIFICACIÓN: cambian en controles médicos reales, así que SÍ
        // tienen setter... pero validado: peso y altura <= 0 no existen.
        private double pesoKilogramos;
        private double alturaMetros;

        public Paciente(String nombreCompleto, int edad,
                        double pesoKilogramos, double alturaMetros) {
            // JUSTIFICACIÓN: fail-fast. Si algún dato es absurdo, la falla
            // explota ACÁ, en el momento exacto, con un mensaje claro...
            // y no tres horas después cuando alguien lee un IMC imposible.
            if (nombreCompleto == null || nombreCompleto.isBlank()) {
                throw new IllegalArgumentException("El nombre es obligatorio.");
            }
            if (edad < 0 || edad > 130) {
                throw new IllegalArgumentException("Edad fuera de rango: " + edad);
            }
            if (pesoKilogramos <= 0) {
                throw new IllegalArgumentException("El peso debe ser mayor a cero.");
            }
            if (alturaMetros <= 0) {
                throw new IllegalArgumentException("La altura debe ser mayor a cero.");
            }
            this.nombreCompleto = nombreCompleto;
            this.edad = edad;
            this.pesoKilogramos = pesoKilogramos;
            this.alturaMetros = alturaMetros;
        }

        public String getNombreCompleto() {
            return nombreCompleto;
        }

        /**
         * JUSTIFICACIÓN: sin setEdad a propósito. La edad no es un valor que
         * alguien asigne desde afuera; es consecuencia de que pasa el tiempo.
         * cumplirAnios() modela lo que REALMENTE pasa y garantiza que la edad
         * solo sube de a uno. Comportamiento > asignación cruda.
         */
        public void cumplirAnios() {
            edad++;
        }

        public int getEdad() {
            return edad;
        }

        /** JUSTIFICACIÓN: cambio legítimo de un control médico, con regla. */
        public void setPesoKilogramos(double pesoKilogramos) {
            if (pesoKilogramos <= 0) {
                throw new IllegalArgumentException(
                        "El peso debe ser mayor a cero: " + pesoKilogramos);
            }
            this.pesoKilogramos = pesoKilogramos;
        }

        /** JUSTIFICACIÓN: ídem peso; misma regla, mismo lugar (puerta única). */
        public void setAlturaMetros(double alturaMetros) {
            if (alturaMetros <= 0) {
                throw new IllegalArgumentException(
                        "La altura debe ser mayor a cero: " + alturaMetros);
            }
            this.alturaMetros = alturaMetros;
        }

        public double getPesoKilogramos() {
            return pesoKilogramos;
        }

        public double getAlturaMetros() {
            return alturaMetros;
        }

        /**
         * JUSTIFICACIÓN: getter CALCULADO, como getTotal() en el ejercicio 3.
         * El IMC depende de peso y altura; si lo guardáramos, sería candidato
         * a dato viejo en cuanto cambie el peso. Calculado al vuelo, es
         * estructuralmente imposible que quede desactualizado.
         */
        public double getIndiceMasaCorporal() {
            return pesoKilogramos / (alturaMetros * alturaMetros);
        }
    }

    public static void main(String[] args) {
        Paciente lucia = new Paciente("Lucía Fernández", 34, 62.5, 1.68);

        System.out.println("Paciente : " + lucia.getNombreCompleto());
        System.out.println("Edad     : " + lucia.getEdad() + " años");
        System.out.printf("IMC      : %.2f%n", lucia.getIndiceMasaCorporal());

        // Cumpleaños: sin setEdad, con comportamiento.
        lucia.cumplirAnios();
        System.out.println("Tras su cumpleaños: " + lucia.getEdad() + " años");

        // Nuevo peso válido: el IMC se refresca solo (es calculado).
        lucia.setPesoKilogramos(64.1);
        System.out.printf("IMC con nuevo peso : %.2f%n", lucia.getIndiceMasaCorporal());

        // Intento inválido: la puerta única rechaza y avisa con claridad.
        try {
            lucia.setPesoKilogramos(-3);
        } catch (IllegalArgumentException error) {
            System.out.println("Rechazado: " + error.getMessage());
        }
        System.out.printf("Peso intacto: %.1f kg%n", lucia.getPesoKilogramos());

        // Intento inválido en el constructor: ni siquiera llega a existir.
        try {
            new Paciente("Nadie", 30, 70, -1.70);
        } catch (IllegalArgumentException error) {
            System.out.println("Rechazado al nacer: " + error.getMessage());
        }
    }
}

/*
 * ============================================================================
 *  CHECKLIST DE ANTIPATRONES — repasalo en cada diseño
 * ============================================================================
 *  Si tildás alguno, hay diseño para revisar:
 *
 *  [ ] Campo public "porque es más cómodo".
 *  [ ] Getter Y setter automáticos para todos los campos, sin preguntarse
 *      si ese campo debería poder cambiarse (hola, setNombre).
 *  [ ] Valor calculado guardado en un campo aparte (el total viejo del
 *      ejercicio 3 te está mirando).
 *  [ ] Setter que acepta cualquier valor: la invariante queda en manos de
 *      quien llama, y eso nadie te lo garantiza.
 *  [ ] Asignación cruda donde correspondería un comportamiento
 *      (setEdad vs cumplirAnios).
 *  [ ] Colección interna devuelta directa desde un getter (referencia viva
 *      al estado interno; tema que viene: copias defensivas).
 *
 *  Regla mnónica: el estado es DE LA CLASE. El mundo habla con ella solo
 *  por los métodos que ELLA decidió exponer. Exponé poco, justificá mucho.
 * ============================================================================
 */

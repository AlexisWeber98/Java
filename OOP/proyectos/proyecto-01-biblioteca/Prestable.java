/*
 * Prestable.java — Proyecto Integrador N°1: Biblioteca
 * Contrato mínimo de aquello que se puede prestar y devolver.
 * Quien implemente esta interfaz promete saber moverse entre estante y socio.
 *
 * Compilación y ejecución: javac *.java && java Main
 */
public interface Prestable {

    /** Registra la salida del ítem hacia el socio indicado. */
    void prestar(Socio socio);

    /** Registra la vuelta del ítem al estante. */
    void devolver();
}

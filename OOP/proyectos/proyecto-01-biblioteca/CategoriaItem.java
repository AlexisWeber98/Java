/*
 * CategoriaItem.java — Proyecto Integrador N°1: Biblioteca
 * Categorías temáticas; cada una trae su política propia de días máximo
 * de préstamo (así el enum deja de ser "una lista de constantes tontas").
 *
 * Compilación y ejecución: javac *.java && java Main
 */
public enum CategoriaItem {

    NOVELA("Narrativa y ficción literaria", 14),
    CIENCIA("Divulgación científica", 7),
    HISTORIA("Historia general y biografías", 10),
    INFANTIL("Lectura infantil y juvenil", 7),
    TECNICA("Manuales técnicos y de computación", 5),
    AUDIOVISUAL("Películas, documentales y música", 3);

    private final String descripcion;
    private final int diasMaximoPrestamo;

    CategoriaItem(String descripcion, int diasMaximoPrestamo) {
        this.descripcion = descripcion;
        this.diasMaximoPrestamo = diasMaximoPrestamo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDiasMaximoPrestamo() {
        return diasMaximoPrestamo;
    }
}

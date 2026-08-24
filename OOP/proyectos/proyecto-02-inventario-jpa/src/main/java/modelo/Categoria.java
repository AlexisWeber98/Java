package modelo;

/**
 * Enum del dominio (módulo 10): categoría con descripción legible.
 * Se persiste como STRING para que la base sea estable ante reordenamientos.
 */
public enum Categoria {

    GENERAL("Productos de uso general"),
    ALIMENTACION("Alimentos y bebidas"),
    ELECTRONICA("Dispositivos y accesorios electrónicos"),
    LIMPIEZA("Artículos de limpieza"),
    OFICINA("Papelería y material de oficina");

    private final String descripcion;

    Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

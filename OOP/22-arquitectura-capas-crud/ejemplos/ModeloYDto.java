// Módulo 22 · Arquitectura en capas — Archivo 1 de 4: MODELO y DTO.
//
// Cómo ejecutar la demo completa (las 4 clases viven en esta misma carpeta):
//   javac *.java
//   java ControllerConsolaYMain        <- SIN extensión .java
//
// IMPORTANTE 1: compilá TODO junto con "javac *.java", porque cada archivo
// usa clases definidas en otro; compilar uno solo falla.
//
// IMPORTANTE 2 (gotcha): "java ControllerConsolaYMain.java" NO anda acá.
// El lanzador de fuente única resuelve dependencias buscando archivos que
// se llamen igual que la clase (Producto -> Producto.java), pero nuestros
// archivos agrupan varias clases con nombres temáticos por capa. Por eso:
// primero javac, después ejecutá el bytecode sin extensión.

// CAPA MODELO: la entidad del dominio. No sabe nada de menús, consolas,
// servicios ni mapas: solo describe un producto del negocio.
class Producto {

    private final int id;
    private final String nombre;
    private final double precio;

    Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    int getId() {
        return id;
    }

    String getNombre() {
        return nombre;
    }

    double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " $" + precio;
    }
}

// DTO (Data Transfer Object): objeto de TRANSPORTE hacia la presentación.
// ¿Por qué no mandar la entidad directo? Porque exponerla acopla capas:
// si mañana cambia Producto, todo lo que la consume se rompe. Un record
// inmutable (módulo 11) es el DTO perfecto.
record ProductoDto(int id, String nombre, double precio) {

    // Helper de mapeo: convierte entidad -> DTO en un único lugar.
    static ProductoDto desde(Producto producto) {
        return new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio());
    }
}

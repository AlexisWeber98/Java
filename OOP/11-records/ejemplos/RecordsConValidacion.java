// Módulo 11 — Validación con constructor compacto y métodos derivados.
// El constructor compacto es la ÚNICA puerta de entrada de un record:
// si algo se construyó, es porque pasó la validación.
//
// Ejecutá: java RecordsConValidacion.java

public class RecordsConValidacion {

    record Producto(String nombre, double precio, int stock) {
        // Constructor compacto: sin parámetros repetidos ni asignaciones.
        // Solo las reglas; los campos se asignan solos al terminar.
        Producto {
            if (nombre == null || nombre.isBlank()) {
                throw new IllegalArgumentException("el nombre no puede estar vacío");
            }
            if (precio < 0) {
                throw new IllegalArgumentException("el precio no puede ser negativo: " + precio);
            }
            if (stock < 0) {
                throw new IllegalArgumentException("el stock no puede ser negativo: " + stock);
            }
        }

        // Método derivado: calculado a partir de los componentes.
        double valorInventario() {
            return precio * stock;
        }

        // "Modificación" inmutable: devolvemos un NUEVO record, el original queda intacto.
        Producto conDescuento(double porcentaje) {
            if (porcentaje <= 0 || porcentaje >= 100) {
                throw new IllegalArgumentException("el descuento debe estar entre 0 y 100");
            }
            return new Producto(nombre, precio * (1 - porcentaje / 100), stock);
        }
    }

    public static void main(String[] args) {
        var teclado = new Producto("Teclado mecánico", 45000.0, 12);
        System.out.println("Válido   : " + teclado);
        System.out.println("Inventario: $" + teclado.valorInventario());

        var enOferta = teclado.conDescuento(20);
        System.out.println("Con -20% : " + enOferta);
        System.out.println("Original intacto: " + teclado);

        // Ahora intentamos construir algo inválido. El record lo rechaza EN LA PUERTA:
        intentarCrear("Mouse gamer", -1500.0, 5);   // precio negativo → explota
        intentarCrear("", 100.0, 3);                // nombre vacío → explota
    }

    private static void intentarCrear(String nombre, double precio, int stock) {
        try {
            new Producto(nombre, precio, stock);
            System.out.println("(no debería llegar acá)");
        } catch (IllegalArgumentException error) {
            System.out.println("Rechazado: " + error.getMessage());
        }
    }
}

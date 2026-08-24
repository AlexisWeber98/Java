package dto;

import java.math.BigDecimal;

import modelo.Producto;

/**
 * Record DTO (módulo 11): transporte plano de datos entre capas.
 * Desacopla al controlador del modelo persistido: la capa de presentación
 * nunca maneja entidades JPA directamente.
 */
public record ProductoDto(Long id, String nombre, BigDecimal precio, Integer stock,
                          String categoriaDescripcion) {

    /** Mapper estático: entidad -> DTO. */
    public static ProductoDto desde(Producto producto) {
        return new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria().getDescripcion());
    }
}

import java.util.List;

/*
 * ============================================================================
 * SOLUCIÓN · Módulo 22 · Ejercicio 4 — DTO separado de entidad
 * ============================================================================
 * Decisiones clave:
 *   - El record EmpleadoPublicoDto declara por contrato qué campos viajan:
 *     nombre y área. El salario NO existe en el tipo, así que filtrarlo ya
 *     no depende de que nadie se olvide de ocultarlo: es imposible.
 *   - El mapeo vive en el SERVICIO, no en el controlador: la decisión de qué
 *     se expone es de negocio. El controlador solo muestra lo que recibe.
 *   - La prueba definitiva es del compilador: dto.salario() no compila.
 * ============================================================================
 */
public class Solucion4DtoSeparadoDeEntidad {

    public static void main(String[] args) {
        ServicioEmpleadosSol servicio = new ServicioEmpleadosSol();

        List<EmpleadoSol> empleados = List.of(
                new EmpleadoSol("Ana Torres", "Sistemas", 850_000),
                new EmpleadoSol("Luis Pérez", "RRHH", 720_000));

        List<EmpleadoPublicoDto> visibles = servicio.aDto(empleados);

        System.out.println("Plantilla (versión con DTO — BIEN):");
        visibles.forEach(dto -> System.out.println("  " + dto.nombre() + " (" + dto.area() + ")"));

        // Prueba del compilador: descomentá y vas a ver el error.
        // visibles.getFirst().salario();  // ← error: cannot find symbol 'salario'
        System.out.println("El salario quedó en la capa de negocio: el DTO no lo conoce.");
    }

    /** ENTIDAD del dominio: datos sensibles incluidos. Nunca viaja a la pantalla. */
    record EmpleadoSol(String nombre, String area, double salario) { }

    /** DTO de presentación: SOLO lo que la pantalla tiene permitido ver. */
    record EmpleadoPublicoDto(String nombre, String area) { }

    /** CAPA DE SERVICIO: dueña del mapeo entidad → DTO. */
    static class ServicioEmpleadosSol {

        EmpleadoPublicoDto aDto(EmpleadoSol empleado) {
            return new EmpleadoPublicoDto(empleado.nombre(), empleado.area());
        }

        List<EmpleadoPublicoDto> aDto(List<EmpleadoSol> empleados) {
            return empleados.stream().map(this::aDto).toList();
        }
    }
}

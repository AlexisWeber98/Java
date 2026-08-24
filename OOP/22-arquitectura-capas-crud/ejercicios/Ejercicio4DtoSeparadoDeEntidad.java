import java.util.List;

/*
 * ============================================================================
 * Módulo 22 · Arquitectura en capas — Ejercicio 4
 * DTO separado de entidad: el salario no sale de la capa de negocio
 * ============================================================================
 *
 * ENUNCIADO
 * El main de abajo le pasa la ENTIDAD completa a la pantalla, salario
 * incluido. Hoy es feo; mañana, cuando a Empleado le agregues dni o datos
 * bancarios, se filtran solos. La solución es un DTO: un objeto chico que
 * declara POR CONTRATO qué campos viajan hacia la presentación.
 *
 * REQUISITOS
 *   1. Crear el record EmpleadoPublicoDto(String nombre, String area),
 *      sin salario y sin nada más.
 *   2. En ServicioEmpleados, implementar el mapeo entidad → dto:
 *      EmpleadoPublicoDto aDto(Empleado) y List<EmpleadoPublicoDto> aDto(List<Empleado>).
 *   3. Cambiar el main para imprimir DTOS, nunca entidades.
 *   4. Probarlo: intentar escribir dto.salario() y leer el error del
 *      compilador. Esa es la prueba de que el salario no sale de la capa.
 *
 * PISTAS
 *   - El mapeo va en el SERVICIO, no en el main: decidir qué se expone es
 *     una decisión de negocio.
 *   - Un record te da constructor, accessors e implementación de equals,
 *     hashCode y toString gratis. Para un DTO es la herramienta ideal.
 *   - Si mañana agregás un campo sensible a la entidad, el DTO ni se entera:
 *     eso es exactamente el punto.
 * ============================================================================
 */
public class Ejercicio4DtoSeparadoDeEntidad {

    public static void main(String[] args) {
        List<Empleado> empleados = List.of(
                new Empleado("Ana Torres", "Sistemas", 850_000),
                new Empleado("Luis Pérez", "RRHH", 720_000));

        // === MAL: la entidad viaja completa hacia la pantalla =================
        System.out.println("Plantilla (versión con entidad — MAL):");
        for (Empleado empleado : empleados) {
            System.out.println("  " + empleado.nombre() + " (" + empleado.area()
                    + ") gana $" + empleado.salario());   // ← filtración de datos sensibles
        }

        // TODO 1: creá el record EmpleadoPublicoDto(String nombre, String area).
        // TODO 2: implementá los dos métodos aDto(...) en ServicioEmpleados.
        // TODO 3: reemplazá este bloque por la impresión de DTOS:
        //
        //     List<EmpleadoPublicoDto> visibles = new ServicioEmpleados().aDto(empleados);
        //     visibles.forEach(dto -> System.out.println("  " + dto.nombre() + " (" + dto.area() + ")"));
        //
        // TODO 4: descomentá esta línea y mirá lo que dice javac:
        //     visibles.getFirst().salario();
    }
}

/**
 * ENTIDAD del dominio: contiene todo, incluido lo sensible. Vive en la capa
 * de negocio y JAMÁS cruza esa frontera hacia la presentación.
 */
class Empleado {
    private final String nombre;
    private final String area;
    private final double salario;

    Empleado(String nombre, String area, double salario) {
        this.nombre = nombre;
        this.area = area;
        this.salario = salario;
    }

    String nombre() {
        return nombre;
    }

    String area() {
        return area;
    }

    double salario() {
        return salario;
    }
}

/**
 * CAPA DE SERVICIO: hoy vacía. Después del ejercicio conoce entidades y
 * produce DTOs; es la única que puede traducir entre ambos mundos.
 */
class ServicioEmpleados {
    // TODO: EmpleadoPublicoDto aDto(Empleado empleado) { ... }
    // TODO: List<EmpleadoPublicoDto> aDto(List<Empleado> empleados) { ... }
}

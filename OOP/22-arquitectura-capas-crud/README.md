# Módulo 22 — Arquitectura en Capas y CRUD

> **La idea del módulo:** dejá de mezclar todo en el `main`. La arquitectura en capas separa tu programa por **responsabilidad**: lo que habla con el usuario, lo que decide, y lo que guarda. Es el primer paso serio hacia software mantenible.

Abrimos la **Parte VI** del curso: hasta acá aprendiste herramientas (POO, colecciones, generics, JDBC, JPA); de acá en adelante aprendés a **organizarlas** en aplicaciones reales.

## ¿Qué es la arquitectura en capas?

Es organizar el código agrupando clases según **qué responsabilidad cumplen**, no según el tipo de archivo. Un proyecto por capas tiene (al menos) cuatro zonas:

| Capa | Responsabilidad | No debe saber de... |
|------|-----------------|---------------------|
| **Presentación** (Controller) | Habla con el usuario: menú, consola, UI | SQL, HashMaps, reglas de negocio |
| **Lógica / Servicio** | Reglas de negocio: validar, decidir, orquestar | `Scanner`, `System.out`, tablas |
| **Datos / Repositorio** | Persistencia: guardar, buscar, borrar | Menús, validaciones, impresión |
| **Modelo** | Entidades del dominio: `Producto`, `Cliente` | Todas las demás capas |

**Analogía del restaurante:** el salón toma tu pedido y te trae la comida (**presentación**), la cocina decide cómo se prepara cada plato y controla que salga bien (**lógica/servicio**), y la despensa guarda los ingredientes (**datos**). El cocinero no sale a tomar pedidos, y el mozo no entra a la despensa a buscar un kilo de tomates: cada uno habla con quien corresponde.

## ¿Por qué existe?

Mirá este clásico:

```java
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    // ... 80 líneas de menú ...
    if (!nombre.isBlank() && precio > 0) {          // regla de negocio
        try (var st = conn.prepareStatement(sql)) { // persistencia
            // ... 120 líneas más de mezcla total ...
        }
    }
}
```

Un `main` de 500 líneas tiene tres problemas fatales:

1. **No es testeable:** para probar la regla "precio > 0" tenés que ejecutar TODO el programa.
2. **No es cambiable:** ¿pasar de memoria a MySQL? Editás el mismo archivo donde vive el menú.
3. **No es mantenible:** cada feature toca todo, y cada bug aparece en cualquier lado.

Con capas, cambiás la base de datos tocando **una sola capa**; cambiás el menú sin mirar una línea de negocio.

## ¿Quién lo usa?

Literalmente **todo backend serio**. Si alguna vez viste Spring Boot, ya conociste el patrón sin saberlo:

- `@RestController` → capa de **presentación**
- `@Service` → capa de **lógica**
- `@Repository` → capa de **datos**

Este módulo es ese patrón, hecho a mano para que entiendas qué hace Spring debajo.

## ¿Cómo funciona?

### Paso 1 — Cada capa define su contrato

Cada capa expone **métodos claros** y oculta su interior (fragments reales de `ejemplos/`):

```java
// Datos: el repositorio promete guardar y recuperar
Producto guardar(Producto producto);
Optional<Producto> buscarPorId(int id);

// Lógica: el servicio promete respetar las reglas del negocio
Producto crear(String nombre, double precio);

// Presentación: el controlador solo lee opciones y delega
int leerOpcion();
```

### Paso 2 — La regla de oro: dirección de las dependencias

Las dependencias apuntan **hacia abajo**, jamás hacia arriba:

```
PRESENTACIÓN  →  SERVICIO  →  REPOSITORIO  →  MODELO
        (todas pueden conocer al Modelo)
```

- Presentación conoce al servicio. ✅
- Servicio conoce al repositorio. ✅
- El servicio **NO sabe que existe el `Scanner`**: si mañana la UI es web o Swing, el servicio ni se entera. ❌ Scanner en servicio
- El repositorio **no valida reglas de negocio**: solo persiste lo que el servicio le manda.

Si una flecha sube, la arquitectura está rota.

### Paso 3 — DTO: objetos de transporte

Un **DTO** (*Data Transfer Object*) es un objeto diseñado para **viajar entre capas**, no para vivir en el dominio. ¿Por qué no mandar la entidad directo? Porque exponer la entidad **acopla** capas (la amarra a los detalles internos de otra): si agregás un campo interno a `Producto`, todo lo que la consume se rompe; y le regalás a la presentación poder mutar tu modelo.

```java
// Entidad: vive en el Modelo, identidad mutable
class Producto { private int id; ... }

// DTO: transporte inmutable — los records (módulo 11) fueron hechos para esto
record ProductoDto(int id, String nombre, double precio) {
    static ProductoDto desde(Producto p) { ... }  // helper de mapeo
}
```

Regla práctica: entidad adentro de la lógica/datos, DTO en el borde hacia la presentación.

### Paso 4 — El viaje de una petición, línea por línea

Usuario elige opción `1` (alta de producto). Sigamos el dato por todas las capas:

1. `ControllerConsolaYMain` → el `Scanner` lee `"1"` y entra al caso `ALTA` del menú.
2. Controller pide nombre y precio, y **delega**: `servicio.crear(nombre, precio)`.
3. `ServicioProductos.crear()` valida: nombre no vacío → si falla, lanza `IllegalArgumentException("El nombre no puede estar vacío.")`.
4. Valida `precio > 0`; valida contra duplicados consultando `repositorio.existeNombre(nombre)`.
5. Todo OK → construye `new Producto(...)` y llama `repositorio.guardar(producto)`; el repo asigna id y devuelve la entidad guardada.
6. El servicio devuelve el `Producto` al controller, que lo convierte en `ProductoDto` para mostrarlo.
7. La respuesta **sube** por el mismo camino: servicio → controller → pantalla: `✔ Producto creado: [3] Mate $2500.0`.

Nadie saltó pisos, nadie imprimió desde la cocina.

## ¿Dónde se usa?

- Todo backend HTTP (Spring, Quarkus, Micronaut): controller / service / repository.
- Apps Android (Activity / ViewModel / Repository).
- Desktop y CLI serios: cualquier app que supere "un archivo".
- Microservicios: cada servicio repite internamente esta estructura.

## ¿Cuándo usarlo y cuándo NO?

- **SÍ:** cualquier aplicación de más de un archivo, algo que crezca, algo que se testea, algo con datos que van a cambiar de almacenamiento algún día (y van a cambiar).
- **NO:** scripts de una sola vez, ejercicios de sintaxis, prototipos desechables. Meter cuatro capas en un script de 30 líneas es **sobre-ingeniería**: la arquitectura se paga sola cuando el sistema cambia, no antes.

## Ejemplo práctico

En [`ejemplos/`](ejemplos/) tenés un **CRUD completo de productos** partido en 4 archivos, uno por responsabilidad:

| Archivo | Capa | Contenido |
|---------|------|-----------|
| `ModeloYDto.java` | Modelo | `Producto` + record `ProductoDto` con helper de mapeo |
| `RepositorioProductos.java` | Datos | Interfaz + implementación en memoria (`HashMap`) |
| `ServicioProductos.java` | Lógica | Validaciones y reglas: nombre, precio, duplicados |
| `ControllerConsolaYMain.java` | Presentación | Menú de consola + `main` que conecta todo |

Para correrlo (importante: primero compilá **todo junto**, porque las clases se usan entre sí):

```bash
cd ejemplos
javac *.java
java ControllerConsolaYMain    # ojo: SIN la extensión .java
```

> **Gotcha interesante:** `java ControllerConsolaYMain.java` (lanzador de fuente única) **no funciona** en este ejemplo: para resolver dependencias busca archivos llamados igual que la clase (`Producto` → `Producto.java`), y acá agrupamos varias clases por capa con nombres temáticos. Compilá primero y ejecutá el bytecode. Si un día un módulo te exige un archivo por clase, ya sabés por qué es la convención profesional.

## Buenas prácticas

- **Una responsabilidad por clase, una clase por capa.** Si el nombre de tu clase tiene "Y" (`ServicioYReportes`), sospechá.
- **Interfaz en el límite del repositorio.** Ya hiciste esto con `Repositorio<T>` genérico (módulo 17): el servicio depende de la interfaz, nunca de la implementación concreta.
- **El servicio es dueño de transacciones y validaciones.** Las reglas viven en UN lugar.
- **Controllers finos:** leer input, delegar, mostrar resultado. Cero `if` de negocio.
- **DTOs en el borde:** records inmutables para viajar hacia presentación.

## Errores comunes

- **Controlador gordo con SQL adentro:** el clásico anti-patrón; el controller deja de ser presentación.
- **Servicio que devuelve tipos leaky:** devolver algo tipo `ResultSet` (o un `Map` crudo) *filtra* detalles de persistencia hacia arriba. Devolvé entidades o DTOs.
- **Saltearse capas "solo esta vez":** el controller llama al repo directo porque "era rápido". Después son cinco veces, y la arquitectura muere.
- **Dependencias bidireccionales:** si el repositorio conoce al servicio, hay un ciclo y nada se puede probar ni cambiar aislado.

## Resumen express

- Capas = separación por **responsabilidad**: presentación → servicio → repositorio → modelo.
- Dependencias **solo hacia abajo**; el modelo es conocido por todos pero no conoce a nadie.
- **Servicio** = reglas de negocio; **repositorio** = persistencia detrás de una interfaz; **controller fino**.
- **DTO** = objeto de transporte (record) que evita acoplar capas vía entidades.
- Cambiar una capa no debe tocar las demás: esa es LA prueba de que lo hiciste bien.

## Ejercicios

1. **¿De qué capa es esta regla?** Dadas 8 sentencias ("el email debe tener @", "el listado se imprime en columnas", "el SQL usa WHERE"), clasificá cada una en su capa y justificá.
2. **Service que valida y Controller que delega:** mové todas las validaciones de un CRUD "todo-en-main" a un `ServicioX`, dejando el controller sin un solo `if` de negocio.
3. **Repositorio con interfaz:** extraé una interfaz `RepositorioClientes` con dos implementaciones (memoria y lista) y switchéalas cambiando UNA línea del `main`.
4. **DTO separado de entidad:** convertí `Cliente` en record `ClienteDto` con helper `desde(...)`, y hacé que la presentación solo reciba DTOs.
5. **Desafío — CRUD completo por capas:** inventario de libros con alta/baja/listado/búsqueda, las 4 capas, interfaz de repositorio, validaciones en servicio y DTO hacia presentación. Sin reglas fuera de su capa.

## Para profundizar

- *Patterns of Enterprise Application Architecture* — Martin Fowler (capas, Repository, DTO).
- Documentación de Spring: anotaciones `@Service`, `@Repository`, `@Transactional`.
- Clean Architecture (Robert C. Martin) — el siguiente escalón: dependencias apuntando al dominio.

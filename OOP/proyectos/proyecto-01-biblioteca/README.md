# Biblioteca Comunitaria — Proyecto Integrador N°1

Sistema de gestión de biblioteca que integra **todos los módulos del curso de OOP** en un solo programa: catálogo polimórfico de ítems, socios con límites, préstamos con reglas de negocio y un historial inmutable de movimientos. El objetivo no es "que compile": es que cada línea tenga una decisión de diseño que puedas defender en voz alta.

## Quick path

1. Compilá y corré la demo guiada: `javac *.java && java Main`
2. Leé los mensajes `[OK]` y `[RECHAZADO]`: cada rechazo es una excepción propia capturada.
3. Jugá con el menú: `javac *.java && java Main interactivo`
4. Auto-evaluarte con la rúbrica del final: si no podés explicar el POR QUÉ, volvé al módulo.

## Cómo correrlo

```bash
javac *.java && java Main                # demo guiada no interactiva
javac *.java && java Main interactivo    # menú con Scanner
```

Sin argumentos corre la demo completa (11 secciones, termina sola). Con `interactivo` arranca el mismo gestor pero sembrado con datos de ejemplo y un menú por consola para prestar, devolver, listar e informar.

## Arquitectura: clases y colaboraciones

```text
                        <<interface>>
                         Prestable
                            ▲ implements
                            │
                 ┌──────────┴───────────┐
                 │  ItemBiblioteca      │  <<abstract>>
                 │  - codigo            │  estado = EstadoItem
                 │  - titulo, anio      │  categoria = CategoriaItem
                 │  + estaDisponibleParaPrestamo() <<abstract>>
                 │  + descripcionDetallada()       <<abstract>>
                 └──────────┬───────────┘
              ┌─────────────┼─────────────┐
        ┌─────┴────┐  ┌─────┴─────┐  ┌────┴────┐
        │  Libro   │  │  Revista  │  │   DVD   │   cada una define SU regla
        │ paginas  │  │ edicion   │  │ minutos │   (la edición del año de una
        └──────────┘  └───────────┘  └─────────┘   revista NO sale de sala)

    GestorBiblioteca ──usa──▶ <<interface>> RepositorioGenerico<T, K>
         │                              ▲ implements
         │                              │
         ├── repositorioItems           RepositorioEnMemoria<T,K>
         │     : RepositorioGenerico    (backend: HashMap<K,T> + lock simple)
         ├── repositorioSocios
         └── historial: List<RegistroPrestamo>   (records inmutables)

    Socio ◀──prestamosActivos──▶ GestorBiblioteca
      (límite: MAX_PRESTAMOS_ACTIVOS = 3)

    Jerarquía de excepciones propias (checked):
        Exception
          └── ExcepcionBiblioteca (base común, abstracta)
                ├── ItemNoDisponibleException
                ├── SocioConLimiteAlcanzadoException
                └── ItemInexistenteException
```

**Flujo de un préstamo:** `Main → GestorBiblioteca.prestar(codigo, socioId)` → busca ítem y socio en sus repositorios (`ItemInexistenteException` si falta) → valida disponibilidad del ítem (`estaDisponibleParaPrestamo()`, polimórfico) y cupo del socio → registra el movimiento como `RegistroPrestamo` en el historial. Cualquier falla lanza una excepción de la jerarquía propia, que `Main` captura y traduce a un mensaje amable.

## Mapa de archivos

| Archivo | Rol |
|---|---|
| `ItemBiblioteca.java` | Clase abstracta madre del catálogo; implementa `Prestable` |
| `Libro.java` / `Revista.java` / `DVD.java` | Subclases concretas; cada una define su regla de préstamo y descripción |
| `Prestable.java` | Interfaz del comportamiento prestable |
| `EstadoItem.java` / `CategoriaItem.java` | Enums de estado y categoría (con datos propios) |
| `RegistroPrestamo.java` | Record inmutable de movimientos + enum anidado `Accion` |
| `RepositorioGenerico.java` / `RepositorioEnMemoria.java` | Contrato genérico `<T,K>` y su implementación con `HashMap` |
| `GestorBiblioteca.java` | Fachada de negocio: préstamos, devoluciones, historial, listados ordenados |
| `Socio.java` | Socio con cupo de préstamos activos |
| `ExcepcionesBiblioteca.java` | Jerarquía de excepciones checked del dominio |
| `Main.java` | Demo guiada (sin args) y menú interactivo (`interactivo`) |

## Concept checklist — feature → módulo del curso

| Feature concreta en el código | Módulo | Concepto |
|---|---|---|
| `Libro`, `Revista`, `DVD` extienden `ItemBiblioteca` y reutilizan codigo/título/estado | 06 | Herencia |
| Mismo llamado `item.prestarA(socio)` ejecuta la regla de cada subclase; `descripcionDetallada()` imprime distinto según tipo | 07 | Polimorfismo (overriding) |
| `ItemBiblioteca` es abstracta: no existe "un ítem" suelto, solo tipos concretos; declara métodos abstractos sin implementación | 08 | Abstracción |
| `interface Prestable { prestarA, devolver }` implementada por la clase madre | 09 | Interfaces |
| `EstadoItem` (DISPONIBLE/PRESTADO/EN_REPARACION) y `CategoriaItem` con datos propios (días máximos de préstamo por categoría) | 10 | Enums con comportamiento |
| `record RegistroPrestamo(itemTitulo, socioNombre, fecha, accion)` con enum anidado `Accion`; inmutable, con `toString` propio | 11 | Records |
| `GestorBiblioteca` compone dos repositorios vía su interfaz: no conoce HashMap, solo el contrato | 12 | Relaciones y composición |
| `try/catch` en `Main` captura `ItemNoDisponibleException`, `SocioConLimiteAlcanzadoException`, `ItemInexistenteException` y muestra mensajes amables | 14 | Excepciones propias (checked) |
| `historial: List<RegistroPrestamo>` en el gestor, expuesto como copia inmutable con `List.copyOf` | 15 | ArrayList |
| `RepositorioEnMemoria` guarda todo en `HashMap<K,T>`: lookup por código en O(1) | 16 | HashMap |
| `interface RepositorioGenerico<T, K>` + `RepositorioEnMemoria<T,K>`: un solo repo genérico para ítems y socios | 17 | Generics |

## Decisiones de diseño (las que tenés que poder defender)

- **¿Por qué checked exceptions?** Porque "el socio ya llegó a su límite" o "el ítem está prestado" no son bugs: son resultados esperados del dominio que quien llama debe manejar. Un `RuntimeException` dejaría pasar esos casos silenciosamente.
- **¿Por qué el gestor depende de la interfaz `RepositorioGenerico` y no de `RepositorioEnMemoria`?** Para poder cambiar el backend (archivo, BD) sin tocar el gestor: inversión de dependencias en miniatura.
- **¿Por qué la validación de "sale de sala" vive en cada subclase y no en un `if` del gestor?** Si mañana agregás `Audiolibro`, el gestor no se entera: la regla viaja con el tipo. Eso es el principio abierto/cerrado.
- **¿Por qué record para el historial?** Los movimientos son hechos pasados que nunca cambian; la inmutabilidad evita que alguien "edite la historia" y te da equals/hashCode gratis.
- **¿Por qué `List.copyOf(historial)` en el getter?** Devolver la lista interna permitiría a cualquiera mutarla desde afuera: fuga de encapsulamiento.

## Rúbrica de auto-evaluación

Antes de dar el proyecto por cerrado, respondé EN VOZ ALTA:

- [ ] ¿Podés explicar por qué `ItemBiblioteca` es abstracta y qué rompería si fuera concreta?
- [ ] ¿Podés señalar la línea exacta donde actúa el polimorfismo en la demo?
- [ ] ¿Sabés qué gana `RegistroPrestamo` por ser record y no clase?
- [ ] ¿Podés explicar la firma `RepositorioGenerico<T, K>` y qué representa cada parámetro?
- [ ] ¿Podés justificar por qué tus excepciones son checked y dónde exactamente se capturan?
- [ ] ¿Podés trazar el flujo completo de `prestar` desde `Main` hasta el historial?
- [ ] ¿Podés decir qué cambiaría si el límite de préstamos dependiera de la categoría del socio?

Si dudaste en alguna, ese es tu módulo pendiente: volvé a la clase correspondiente y rehacé el ejercicio.

## Extensiones propuestas

1. **Multas por atraso:** agregá `fechaVencimiento` al préstamo (usando los días máximos de `CategoriaItem`) y calculá multa al devolver. Ejercita fechas + reglas por categoría.
2. **Búsqueda por categoría con Streams** (teaser del mundo funcional):

```java
itemsOrdenadosPorTitulo().stream()
    .filter(i -> i.getCategoria() == CategoriaItem.DIVULGACION)
    .forEach(System.out::println);
```

3. **Reservas:** cola de socios esperando un ítem prestado (`Queue<Socio>` por ítem).
4. **Persistencia:** implementá `RepositorioGenerico` sobre archivo CSV y enchufalo al gestor sin tocarlo.

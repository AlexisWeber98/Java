# Módulo 21 · JPA e Hibernate

> **Objetos adentro, filas afuera: el traductor automático.**
> Vos pensás en objetos; la base de datos piensa en tablas y filas. JPA es el contrato que traduce de un mundo al otro sin que escribas ni una línea de SQL para el 90% de tu trabajo diario.

---

## ¿Qué es un ORM y qué es JPA?

Tres conceptos que se confunden todo el tiempo y hay que separar:

- **ORM** (*Object-Relational Mapping*): la **idea/técnica** de mapear automáticamente clases ↔ tablas y objetos ↔ filas. Guardás un objeto y aparece una fila; leés una fila y obtenés un objeto.
- **JPA** (*Jakarta Persistence API*): la **especificación**. Un conjunto de interfaces (`EntityManager`, `@Entity`, `@Id`, JPQL...) que define *cómo* debe comportarse cualquier ORM en Java. JPA por sí sola no ejecuta nada.
- **Hibernate**: la **implementación**. Es quien hace el trabajo sucio: genera el SQL, maneja las conexiones, sincroniza objetos y filas. Existen otras implementaciones (EclipseLink, OpenJPA), pero Hibernate domina el mercado.

Analogía rápida: JPA es el plano eléctrico de la casa; Hibernate es el electricista que lo ejecuta.

En el [módulo 20](../20-jdbc-base-de-datos/README.md) vimos JDBC: abrís conexión, escribís SQL a mano, leés el `ResultSet` columna por columna y armás el objeto manualmente. Eso es exactamente lo que un ORM te elimina.

## ¿Por qué existe?

Porque el mapeo manual es una fábrica de código repetitivo:

```java
// El camino JDBC (módulo 20): TODO manual
PreparedStatement ps = conn.prepareStatement("SELECT id, nombre, precio FROM productos WHERE id = ?");
ps.setLong(1, id);
ResultSet rs = ps.executeQuery();
Producto p = null;
if (rs.next()) {
    p = new Producto(rs.getString("nombre"), rs.getBigDecimal("precio"));
    // y si agregás una columna, tocas acá... y en el INSERT... y en el UPDATE...
}
```

Con JPA eso entero se reduce a `em.find(Producto.class, id)`. Y encima recibís gratis dos superpoderes que con JDBC tendrías que programar a mano:

- **Identidad**: dentro de una misma unidad de trabajo, la fila 1 siempre es *el mismo objeto* Java.
- **Dirty tracking**: cambiás un campo del objeto y al confirmar la transacción Hibernate genera el `UPDATE` solo. Sin escribirlo.

## ¿Quién lo usa?

Prácticamente todos los backends serios de Java:

- **Spring Boot** lo trae de fábrica: su starter de datos usa JPA con Hibernate por defecto.
- **Quarkus** usa Hibernate como motor de persistencia (con Panache arriba).
- Cualquier sistema corporativo con dominio rico: bancos, ERPs, e-commerce, sistemas hospitalarios.

Si vas a trabajar como desarrollador Java backend, esto no es opcional.

## ¿Cómo funciona?

Vamos paso a paso con los **archivos reales de este módulo**, que podés abrir junto a esta lectura.

### Paso 1 · La configuración: `persistence.xml`

Ubicado en `src/main/resources/META-INF/persistence.xml`. Es el punto de arranque: define una **unidad de persistencia** con nombre (`demoPU`) que agrupa conexión, entidades y estrategia de esquema:

| Propiedad | Qué hace |
|---|---|
| `jakarta.persistence.jdbc.url` | Dónde está la base: acá, H2 en archivo (`./basedemo`) |
| `jakarta.persistence.jdbc.user/password` | Credenciales |
| `hibernate.dialect` | El "dialecto" SQL del motor (H2, PostgreSQL, MySQL hablan distinto) |
| `hibernate.hbm2ddl.auto=drop-and-create` | En cada arranque borra y recrea las tablas según tus entidades. Ideal para aprender, **prohibido** en producción |
| `hibernate.show_sql=true` | Imprime el SQL real que genera Hibernate: miralo siempre |

### Paso 2 · La entidad: `modelo/Producto.java`

Las reglas mínimas para que una clase sea entidad JPA:

```java
@Entity
@Table(name = "productos")          // nombre real de la tabla (si no, usaría "Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private BigDecimal precio;      // BigDecimal mapea a numeric: perfecto para dinero

    protected Producto() { }        // OBLIGATORIO: constructor sin argumentos
}
```

- `@Entity`: "esta clase se persiste". `@Id`: cuál atributo es la clave primaria (**obligatorio**: sin `@Id` no arranca).
- Constructor sin argumentos: Hibernate crea instancias reflectivamente, necesita ese camino.
- Tipos soportados: primitivos, wrappers, `String`, fechas (`LocalDate`/`LocalDateTime`) y sí, `BigDecimal`.

Sobre `@GeneratedValue` (quién asigna el id):

| Estrategia | Comportamiento |
|---|---|
| `IDENTITY` | Columna autoincremental del motor. El INSERT sale inmediato en el `persist()` (es la de este módulo) |
| `SEQUENCE` | Usa sequences de la base (ideal en PostgreSQL) |
| `AUTO` | Delega la elección en el proveedor |

Y `@Column` / `@Table` sirven para **renombrar**: si tu atributo se llama `precioUnitarioFinal` pero la columna debe llamarse `precio_final`, es `@Column(name = "precio_final")`. Tu Java queda idiomatico y tu esquema, convencional.

### Paso 3 · El ciclo con `EntityManager` (ver `Main.java`)

El flujo completo de la demo:

```java
EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU"); // UNA vez por app

EntityManager em = emf.createEntityManager();        // uno por unidad de trabajo
em.getTransaction().begin();

em.persist(teclado);                                 // ALTA
Producto p = em.find(Producto.class, teclado.getId()); // LECTURA por clave primaria
p.setPrecio(nuevoPrecio);                            // UPDATE implícito (dirty checking)
em.remove(monitor);                                  // BAJA

em.getTransaction().commit();                        // acá salen UPDATE y DELETE
em.close();
```

Analogía de oficina: el `EntityManagerFactory` es **montar la oficina entera** (carísimo, una sola vez por aplicación); cada `EntityManager` es un **empleado con su carpeta de trabajo** (barato, se abre para una tarea y se cierra al terminar).

Cada operación en una línea:

- **`persist(entidad)`** → registra un objeto nuevo para guardarse.
- **`find(Entidad.class, id)`** → trae por clave primaria (o devuelve `null`; no lanza excepción).
- **`merge(entidad)`** → re-adjunta un objeto *detached* (que salió de un contexto ya cerrado) creando una copia gestionada. Regla simple: `persist` para nuevos, `merge` para reconectar.
- **`remove(entidad)`** → marca para borrado al hacer commit.

### Paso 4 · El *persistence context*: el corazón de JPA

Entre `begin()` y `commit()` existe una zona de gestión donde Hibernate lleva registro de todo lo que cargaste. Analogía de cocina: es la **mesada del cocinero**. Mientras tus ingredientes están en la mesada, el cocinero los mira ojo por ojo y sabe qué cambió cada uno; cuando levanta la mesada (`close()`), deja de seguirlos: eso es un objeto *detached*. Dos consecuencias prácticas que vas a ver corriendo `Main`:

1. **Identity map**: si hacés `find()` dos veces por el mismo id, obtienes *el mismo objeto* (`==`). Una fila ↔ un objeto dentro del contexto. Por eso en la demo el `find()` no dispara SQL nuevo: ya lo tenía en memoria.
2. **Dirty checking**: al hacer `commit()`, Hibernate compara cada objeto gestionado contra su foto original. Si cambió algo, genera el `UPDATE` solo. En la demo modificamos el precio con un simple setter y el `UPDATE` apareció solo en el commit.

Esto explica también por qué **modificar sin llamar a ningún update funciona** y por qué los cambios en objetos gestionados se guardan "solos".

### Paso 5 · Transacciones

Toda escritura vive dentro de una transacción: `begin()` ... `commit()`, o `rollback()` si algo falla. Es all-or-nothing: o salen todos los cambios o no sale ninguno. En la demo, el `UPDATE` del dirty checking y el `DELETE` del `remove` salen **juntos** recién en el `commit()`.

### Paso 6 · Consultas JPQL

JPQL consulta **objetos y atributos**, no tablas y columnas. Hibernate lo traduce a SQL del dialecto correspondiente:

```java
List<Producto> caros = em.createQuery(
        "select p from Producto p where p.precio > :precioMinimo order by p.precio desc",
        Producto.class)
    .setParameter("precioMinimo", new BigDecimal("10000"))   // parámetro con nombre: nunca concatenar
    .getResultList();
```

Fijate: `from Producto p` habla de la **clase**, `p.precio` del **atributo**. Los `:parámetros` con nombre evitan SQL injection y son la forma canónica de filtrar.

### Paso 7 · Relaciones (adelanto)

Los `@ManyToOne` / `@OneToMany` mapean exactamente las relaciones que modelaste en el [módulo 12](../12-relaciones-entre-clases/README.md): asociación, composición, colecciones. Un adelanto de cómo se ve:

```java
@Entity
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne                      // muchos pedidos -> un cliente
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido") // un pedido -> muchos ítems
    private List<ItemPedido> items;
}
```

Acá solo lo nombramos: el trabajo profundo (fetch types, cascadas, N+1) merece su propio proyecto y vive en el proyecto 02.

### Teaser: Spring Data JPA

¿Te pareció boilerplate el `createEntityManagerFactory` + transacciones manuales? Bien: en Spring Data ese archivo desaparece y las consultas se reducen a **interfaces**:

```java
public interface ProductoRepository extends JpaRepository<Producto, Long> { }
// y tenés save(), findById(), findAll(), delete()... gratis.
```

Pero ojo: Spring Data es azúcar sobre **esto que acabás de aprender**. Si no entendés el persistence context, vas a sufrir igual con la capa de arriba.

## ¿Dónde se usa?

Backends REST, sistemas transaccionales, microservicios con dominio de negocio, aplicaciones desktop con base local (como esta demo con H2). Todo lugar donde tu código habla en objetos y los datos viven en una base relacional.

## ¿Cuándo usarlo y cuándo NO?

Seamos honestos, porque el fanatismo tecnológico no sirve:

- ✅ **Sí**: aplicaciones CRUD-heavy, dominios con muchas entidades y relaciones, equipos que valoran mantenibilidad y portabilidad entre motores.
- ⚠️ **Pensalo dos veces**: reportes masivos, batchs de millones de filas, queries analíticas complejas. Ahí el ORM estorba: SQL crudo vía JDBC (módulo 20) o `JdbcTemplate` suele ser más directo y más rápido. Hibernate no reemplaza a SQL: lo complementa.

## Ejemplo práctico

La demo completa (persistir 3 → find → listar JPQL → update por dirty checking → remove → estado final) está en `src/main/java/Main.java`. Para correrla:

```bash
export JAVA_HOME=/usr/lib/jvm/java-25-openjdk
cd OOP/21-jpa-hibernate
mvn -q compile && mvn -q dependency:build-classpath -Dmdep.outputFile=/tmp/cp21.txt
java -cp "target/classes:$(cat /tmp/cp21.txt)" Main
```

Mirá la salida con atención: cada `[ETIQUETA]` corresponde a un paso del ciclo CRUD, y entre medio aparecen los `Hibernate:` seguidos del SQL real generado. Esa es la magia al desnudo.

Este es además **el primer módulo con estructura Maven real** (`pom.xml`, `src/main/java`, `src/main/resources`): las dependencias (`hibernate-core`, `h2`) se declaran una vez y Maven resuelve el classpath.

## Buenas prácticas

1. **Un `EntityManagerFactory` por aplicación**: crearlo es caro (lee config, construye metadatos). Crearlo en cada request es un bug clásico.
2. **`EntityManager` barato y corto**: uno por unidad de trabajo (un request, un caso de uso). Abrir, usar, cerrar.
3. **Transacciones cortas**: abrí tarde, cerrá temprano. No hagas cálculos pesados ni I/O de red dentro de una transacción.
4. **Nunca records como entidades**: las entities necesitan constructor sin argumentos y campos mutables (el dirty checking depende de eso). Los [records del módulo 11](../11-records/README.md) son inmutables y sin no-arg: perfectos como DTOs, imposibles como `@Entity`.
5. **Parámetros con nombre siempre** (`:nombre`), jamás concatenar strings en JPQL (lo mismo que los `?` del `PreparedStatement` del módulo 20: misma defensa, otra sintaxis).

## Errores comunes

- **`NoClassDefFoundError` / clase no encontrada al correr**: casi siempre es el classpath. No inventes comandos: usá los de este README tal cual (`java -cp "target/classes:$(cat /tmp/cp21.txt)" ...`).
- **Olvidar el `@Id`** (o el constructor sin argumentos): Hibernate rechaza la entidad al bootstrapear con un mensaje explícito. Leé el error: dice exactamente qué regla rompiste.
- **Entidad detached**: si usás un objeto después de cerrar su `EntityManager`, quedó *detached*. Cambiarle campos no actualiza nada en la base. Para reconectarlo: `merge()` en un nuevo contexto (no le pases `persist()`: intentarías insertar un duplicado).
- **Lazy loading fuera de transacción**: cuando lleguen las relaciones (proyecto 02), acceder a una colección perezosa con la sesión ya cerrada tira `LazyInitializationException`. Regla por ahora: trabajá con tus datos *dentro* de la transacción.

## Resumen express

- **ORM**: técnica que mapea objetos ↔ filas automáticamente.
- **JPA**: especificación (interfaces + anotaciones). **Hibernate**: implementación.
- Config en `persistence.xml`; entidad = `@Entity` + `@Id` + constructor no-arg.
- Ciclo: `persist` / `find` / `merge` / `remove`, envueltos en `begin`/`commit`.
- Persistence context: identity map (una fila = un objeto) y dirty checking (UPDATE automático).
- JPQL: consultas orientadas a objetos con `:parámetros`.
- Corrías SQL a mano en JDBC (módulo 20); ahora modelás objetos y el SQL sale solo.

## Ejercicios

Todos compilan y corren con **los mismos comandos del ejemplo práctico**, cambiando solo el nombre de la clase. Los enunciados están en `src/main/java/ejercicios/` (con TODOs guía) y las resoluciones en `src/main/java/soluciones/`. Intentá resolverlos antes de mirar.

| # | Ejercicio | Starter | Solución |
|---|---|---|---|
| 1 | Persistir y encontrar | `ejercicios.Ejercicio1App` | `soluciones.Solucion1App` |
| 2 | Actualizar con dirty checking | `ejercicios.Ejercicio2App` | `soluciones.Solucion2App` |
| 3 | Eliminar seguro | `ejercicios.Ejercicio3App` | `soluciones.Solucion3App` |
| 4 | Consultas JPQL con filtro | `ejercicios.Ejercicio4App` | `soluciones.Solucion4App` |
| 5 | Desafío: alta-baja-modificación + reporte final | `ejercicios.Ejercicio5App` | `soluciones.Solucion5App` |

Para correr cualquiera (ejemplo con el ejercicio 1):

```bash
mvn -q compile && java -cp "target/classes:$(cat /tmp/cp21.txt)" ejercicios.Ejercicio1App
```

Nota: como la unidad usa `drop-and-create`, cada corrida arranca con la tabla vacía y fresca. Cada ejercicio es autónomo: crea sus propios datos.

## Para profundizar

- Documentación oficial de Hibernate ORM 6.6: https://hibernate.org/orm/documentation/
- Especificación Jakarta Persistence: https://jakarta.ee/specifications/persistence/
- Guía de H2 Database: https://h2database.com/html/main.html
- Proyecto 02 del curso: relaciones entre entidades con JPA (fetch, cascadas, N+1).
- Cuando domines este módulo: Spring Data JPA (el próximo salto natural).

# Proyecto 02 — Inventario con JPA/Hibernate

Proyecto integrador (capstone) del curso de POO: un CRUD de inventario por capas
que persiste en una base H2 en archivo usando JPA/Hibernate. Acá se juntan casi
todos los conceptos del curso en una aplicación real, con separación clara de
responsabilidades.

## Objetivo

- Construir una aplicación **en capas** (estilo módulo 22): presentación → negocio → persistencia.
- Practicar **JPA/Hibernate** (módulo 21) sobre una base **H2 en archivo**: entidades, JPQL, transacciones.
- Aplicar reglas de negocio en un único lugar (el servicio) con excepciones propias.
- Exponer datos a la capa de presentación mediante **records DTO**, nunca entidades.

## Arquitectura: flujo entre capas

```
┌──────────────────────────────┐
│  ConsolaController           │  Presentación: Scanner, menú, lectura robusta.
│  (controlador)               │  Solo dialoga con el usuario. Conoce DTOs.
└──────────────┬───────────────┘
               │  llama casos de uso
┌──────────────▼───────────────┐
│  InventarioService           │  Negocio: valida reglas y es el ÚNICO dueño
│  (servicio)                  │  de begin/commit/rollback. Devuelve DTOs.
└──────────────┬───────────────┘
               │  depende del CONTRATO, no de la implementación
┌──────────────▼───────────────┐
│  ProductoRepositorio (I)     │  Puerto de persistencia (interfaz).
│   └─ ProductoJpaRepository   │  Adaptador: EntityManager + JPQL.
└──────────────┬───────────────┘
               │  JDBC / SQL generado por Hibernate
┌──────────────▼───────────────┐
│  H2 (archivo local)          │  ./data/inventario.mv.db
└──────────────────────────────┘
```

### ¿Dónde cortaría la arquitectura hexagonal? (módulo 23)

Este diseño es el estilo **en capas del módulo 22**. El paso a hexagonal sería:

1. **Puerto de salida**: `ProductoRepositorio` ya es una interfaz; movería su
   definición al núcleo (`dominio/puertos`) junto con las entidades, para que el
   dominio no dependa ni siquiera del paquete `repositorio`.
2. **Adaptador de salida**: `ProductoJpaRepository` pasaría a ser un adaptador
   externo que implementa ese puerto; Hibernate queda fuera del corazón.
3. **Puerto de entrada**: definiría `InventarioUseCases` como interfaz; tanto
   `ConsolaController` como la prueba de humo serían *adaptadores de entrada*
   que lo consumen.
4. La flecha de dependencias siempre apunta **hacia adentro**: dominio no
   conoce framework ni consola ni base.

## Conceptos del curso aplicados

| Concepto | Módulo | Dónde aparece acá |
|---|---|---|
| Clases, encapsulamiento, constructores | 2-4 | `modelo.Producto` |
| Enums con estado propio | 10 | `modelo.Categoria` (con descripción) |
| Records y mapeo estático | 11 | `dto.ProductoDto` + `ProductoDto.desde()` |
| `BigDecimal` para dinero (nunca `double`) | 13 | precio y cálculo del valor total |
| Excepciones propias, try/catch/finally | 14 | `ValidacionException`, rollback en el servicio |
| Colecciones y Streams | 15-16 | listados, `map(ProductoDto::desde).toList()` |
| Transacciones begin/commit/rollback | 20-21 | plantilla `enTransaccion` del servicio |
| Entidades JPA, JPQL, dirty checking | 21 | `modelo.Producto`, consultas en el repositorio |
| Separación en capas | 22 | toda la estructura de paquetes |

Nota sobre dinero: el precio usa `BigDecimal` con `precision=12, scale=2` y las
comparaciones se hacen con `compareTo`, nunca con `equals` (que distingue escala)
ni con `double`.

## Cómo construirlo y ejecutarlo

Requisitos: JDK 25 y Maven 3.9. Ejecutá desde la raíz del proyecto:

```bash
export JAVA_HOME=/usr/lib/jvm/java-25-openjdk
mvn -q clean package
mvn -q dependency:build-classpath -Dmdep.outputFile=cp.txt
java -cp "target/classes:$(cat cp.txt)" humo.PruebaDeHumo     # prueba automática
java -cp "target/classes:$(cat cp.txt)" util.AppMain           # demo guiada
java -cp "target/classes:$(cat cp.txt)" util.AppMain interactivo
```

- `humo.PruebaDeHumo`: flujo completo automático con verificaciones (16 checks),
  imprime `RESULTADO FINAL: PASS` o `FAIL` y sale con código 0/1.
- `util.AppMain` (sin argumentos): corre la misma demo guiada, no interactiva,
  mostrando cada operación de CRUD con sus salidas.
- `util.AppMain interactivo`: abre el menú de consola (alta, listar, buscar,
  modificar precio/stock, eliminar, reportes).

## Base de datos

- Motor: **H2 en archivo**, URL `jdbc:h2:file:./data/inventario`
  (crea `./data/inventario.mv.db` relativo al directorio desde donde ejecutes).
- Estrategia de esquema: `drop-and-create`. Cada arranque **borra y recrea**
  las tablas: perfecto para aprender, prohibido en producción (ahí va `validate`
  o migraciones). Los archivos generados quedan cubiertos por el `.gitignore`
  (`*.db`, `*.mv.db`, `*.trace.db`).

## Estructura de paquetes

```
src/main/java/
├── modelo/       Producto (@Entity), Categoria (enum)
├── dto/          ProductoDto (record) + mapper estático
├── repositorio/  ProductoRepositorio (interfaz) + ProductoJpaRepository
├── servicio/     InventarioService + ValidacionException + EntidadNoEncontradaException
├── controlador/  ConsolaController (menú de consola)
├── util/         JpaUtil (singleton EMF) + AppMain (cableado)
└── humo/         PruebaDeHumo (main independiente end-to-end)

src/main/resources/META-INF/persistence.xml   unidad "inventarioPU"
```

## Reglas de negocio implementadas

- Nombre obligatorio, mínimo 3 caracteres (se recortan espacios).
- Precio mayor que cero.
- Stock mayor o igual a cero.
- Sin duplicados por nombre (comparación insensible a mayúsculas).
- Reporte de valor total: `SUM(precio * stock)` vía JPQL.
- Reporte de stock bajo: productos con stock `< 5` (`UMBRAL_STOCK_BAJO`).

## Ideas de extensión

1. **Categorías como entidad**: convertir el enum en `@Entity` con relación
   `@ManyToOne` (preview del módulo 21 avanzado): tabla `categorias`, clave
   foránea, y navegación `producto.getCategoria().getNombre()`.
2. **Paginación**: `setFirstResult/setMaxResults` en el listado para manejar
   inventarios grandes.
3. **Export CSV**: volcar el listado a un archivo desde el menú (practicar
   `java.io` y formateo).
4. **Tests unitarios reales**: extraer las reglas a una clase pura y testearla
   sin base de datos.

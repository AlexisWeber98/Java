# Módulo 20 · JDBC: Base de Datos

> Tu programa hasta acá vive en RAM: cuando el proceso termina, todo desaparece.
> JDBC es el puente oficial de Java hacia bases de datos: los datos sobreviven
> al apagado.

## ¿Qué es JDBC?

**JDBC** (*Java Database Connectivity*) es la API estándar de Java para hablar
con bases de datos relacionales: abrir conexiones, enviar SQL y leer resultados.

Analogía de oficina: tu aplicación es un empleado que necesita papeles del
archivo central (la base). **La conexión** es el teléfono directo con el
archivo; **el driver** es el traductor que convierte tus pedidos al dialecto
exacto de ese archivo en particular (H2, PostgreSQL y MySQL "hablan" SQL, pero
con acentos distintos); **el `ResultSet`** es la persona del archivo leyéndote
los papeles **de a uno por vez**, en orden.

Tres piezas, siempre las mismas:

- **Driver**: el traductor específico por motor (acá usamos H2, un `.jar`).
- **`Connection`**: la línea abierta con la base. Se abre, se usa, se cierra.
- **SQL + `ResultSet`**: lo que pedís y las filas que te devuelven.

Esto ya lo viste en el módulo 9: JDBC es *el qué* (interfaces: `Connection`,
`Statement`, `ResultSet`) y cada driver es *el cómo* (implementaciones
concretas). Polimorfismo de manual, ahora con una base detrás.

## ¿Por qué existe?

Sin base de datos, cada reinicio borra tus listas y mapas. Con una base:

- Los datos **sobreviven** al proceso (y a los cortes de luz).
- Varios programas comparten los mismos datos sin copias desincronizadas.
- Consultás millones de filas con índices en milisegundos: cosas que un
  `HashMap` en memoria no puede pagarte.

## ¿Quién lo usa?

Todo backend serio pasa por SQL en algún punto:

- **Spring Boot, Quarkus, microservicios**: debajo de JPA (módulo 21) hay JDBC corriendo.
- **Reportes y batchs**: cargas masivas nocturnas, cierres contables.
- **Herramientas**: cualquier IDE o cliente de DB usa drivers JDBC adentro.

## ¿Cómo funciona?

El flujo canónico son cuatro pasos: **conectar → preparar → ejecutar → cerrar**.

### 1. Conectar: la URL le dice a Java dónde y con quién

```java
// jdbc:h2:mem = H2 en memoria: nace y muere con el proceso. Perfecta para aprender.
try (Connection conexion = DriverManager.getConnection("jdbc:h2:mem:demo", "sa", "")) {
    // todo lo que hacés acá adentro usa esta conexión...
} // ...y el try-with-resources (módulo 14) la cierra SOLO, incluso si hay excepción.
```

`DriverManager` lee el prefijo (`jdbc:h2:`, `jdbc:postgresql:`...) y elige el
driver traductor correspondiente. Vos nunca instanciás el driver: eso es
abstracción trabajando a tu favor.

### 2. Ejecutar SQL: dos herramientas

```java
// Statement: SQL fijo, escrito entero. Solo para DDL (CREATE TABLE...).
conexion.createStatement().execute("CREATE TABLE productos (...)");

// PreparedStatement: formulario con huecos "?" que rellenás aparte.
PreparedStatement ps = conexion.prepareStatement(
        "SELECT nombre, precio FROM productos WHERE precio < ?");
ps.setBigDecimal(1, limite);  // el valor viaja SEPARADO del texto SQL
```

**Regla del curso: `PreparedStatement` siempre que haya datos del usuario.**
Concatenar strings para armar SQL es la vulnerabilidad #1 de la historia web
(SQL injection): si el usuario tipea `' OR '1'='1`, tu consulta cambia de
significado. Con `?`, el valor entra como dato, jamás como código. En
[`ejemplos/PreparedStatementSeguro.java`](ejemplos/PreparedStatementSeguro.java)
ves ambos lados comparados.

### 3. Leer resultados: fila por fila

```java
ResultSet rs = ps.executeQuery();          // SELECT → devuelve filas
while (rs.next()) {                        // next() avanza; false cuando se acaba
    System.out.println(rs.getString("nombre") + " $" + rs.getBigDecimal("precio"));
}
```

Como la persona del archivo: no te lee la pila entera de golpe; te da una hoja,
pedís la siguiente, y así hasta el final.

### 4. Escribir: INSERT / UPDATE / DELETE

Igual pero con `executeUpdate()` (devuelve cuántas filas tocaron):

```java
PreparedStatement ps = conexion.prepareStatement(
        "INSERT INTO productos (nombre, precio) VALUES (?, ?)");
ps.setString(1, "Mate");
ps.setBigDecimal(2, new BigDecimal("2500"));
int filas = ps.executeUpdate();            // filas == 1
```

### 5. Transacciones: todo o nada

Una **transacción** agrupa operaciones bajo la regla *all-or-nothing*: o se
aplican TODAS, o no se aplica NINGUNA. Caso real: transferir $1500 son DOS
operaciones (debitar a Ana, acreditar a Bruno). Si el sistema muere entre
medias, sin transacción el dinero se evapora. Con transacción, el `rollback()`
deshace los cambios parciales y el saldo queda como estaba.

```java
conexion.setAutoCommit(false);   // "no apliques nada todavía"
// ... débito y crédito ...
conexion.commit();               // recién acá quedan visibles juntas
// o ante cualquier error:       conexion.rollback();
```

Lo viste en acción en [`ejemplos/DaoCuentasConTransaccion.java`](ejemplos/DaoCuentasConTransaccion.java):
la transferencia inválida fuerza rollback y **nada cambia**. Corrélo y mirá.

### 6. DAO: JDBC detrás de una interfaz

Ya sabés la jugada del módulo 9 (y la vas a re-ver en el módulo 22): definís
una interfaz `CuentaDao` con métodos de negocio (`buscarPorId`, `crear`) y el
detallado JDBC queda encerrado en `CuentaDaoH2`. El resto del programa pide
cuentas sin saber que existe SQL. Ese patrón es el famoso **DAO**
(*Data Access Object*) y es el puente natural hacia repositorios y capas.

## ¿Dónde se usa?

Backends que persisten en PostgreSQL/MySQL/Oracle, migraciones de datos,
reportes donde el ORM estorba, herramientas de administración, y como motor
invisible debajo de Hibernate (módulo 21).

## ¿Cuándo NO conviene (a mano)?

JDBC puro es el nivel correcto para **aprender**, y sigue siendo la herramienta
elegida para SQL fino y batchs. Pero para CRUD diario, escribir mapeo manual
fila-a-objeto por cada entidad es repetitivo: ahí aparece JPA/Hibernate
(módulo 21), que automatiza exactamente esto. No lo saltees: quien no entendió
JDBC depura ORMs a oscuras.

## Ejemplo práctico

En [`ejemplos/`](ejemplos/) tenés tres demos progresivas (H2 ya está en `lib/h2.jar`):

1. **`PrimeraConexionYTabla.java`** — conectar, `CREATE TABLE`, insertar 3 filas, leerlas.
2. **`PreparedStatementSeguro.java`** — concatenación peligrosa vs parámetros seguros.
3. **`DaoCuentasConTransaccion.java`** — DAO + transferencia atómica con rollback en vivo.

```bash
cd OOP/20-jdbc-base-de-datos
java -cp lib/h2.jar ejemplos/PrimeraConexionYTabla.java
java -cp lib/h2.jar ejemplos/PreparedStatementSeguro.java
java -cp lib/h2.jar ejemplos/DaoCuentasConTransaccion.java
```

Compilá, corré, y después rompé cosas: borrá la tabla antes del INSERT, pasale
un string malicioso a la versión insegura, y mirá qué explota.

## Buenas prácticas

- **Try-with-resources siempre**: `Connection`, `PreparedStatement` y `ResultSet`
  son recursos que hay que cerrar; el bloque `try (...)` lo garantiza.
- **`PreparedStatement` ante cualquier dato externo**: cero concatenación.
- **`BigDecimal` para dinero** (módulo 11): `double` redondea mal y en finanzas
  eso es un despido.
- **Transacciones cortas**: abrí tarde, confirmá temprano.
- **DAO detrás de interfaz**: el resto del código nunca ve un `SQLException`.

## Errores comunes

| Error | Síntoma |
|---|---|
| Olvidar el driver en el classpath | `No suitable driver found for jdbc:...` |
| Concatenar input del usuario en SQL | SQL injection: la consulta cambia de significado |
| No cerrar conexiones | La base se queda sin conexiones libres ("connection leak") |
| Olvidar `setAutoCommit(false)` | Cada UPDATE se aplica solo: rollback no deshace nada |
| Leer columna después de `next()` en `false` | `SQLException`: pedís hojas de una pila terminada |

## Resumen express

- JDBC = API estándar de Java para SQL; el **driver** es el traductor por motor.
- Flujo: `Connection` → `PreparedStatement` → `executeQuery()`/`executeUpdate()` → cerrar.
- `ResultSet` se recorre con `next()`, fila por fila.
- Transacción = all-or-nothing: `commit()` confirma, `rollback()` descarta.
- Datos del usuario → SIEMPRE parámetros `?`, jamás concatenar.
- Encapsulá el detalle en un **DAO** con interfaz (módulos 9 y 22).

## Ejercicios

Los enunciados con TODOs guía están en [`ejercicios/`](ejercicios/) y las
resoluciones comentadas en [`soluciones/`](soluciones/). Intentá resolverlos
antes de mirar.

| # | Ejercicio | Starter | Solución |
|---|---|---|---|
| 1 | Primera tabla | `ejercicios/Ejercicio1PrimeraTabla.java` | `soluciones/Solucion1PrimeraTabla.java` |
| 2 | Consulta parametrizada | `ejercicios/Ejercicio2ConsultaParametrizada.java` | `soluciones/Solucion2ConsultaParametrizada.java` |
| 3 | CRUD de productos | `ejercicios/Ejercicio3CrudProductos.java` | `soluciones/Solucion3CrudProductos.java` |
| 4 | Transferencia atómica | `ejercicios/Ejercicio4TransferenciaAtomica.java` | `soluciones/Solucion4TransferenciaAtomica.java` |
| 5 | Desafío: mini-DAO completo | `ejercicios/Ejercicio5DesafioMiniDaoCompleto.java` | `soluciones/Solucion5DesafioMiniDaoCompleto.java` |

Se corren igual que los ejemplos: `java -cp lib/h2.jar ejercicios/Nombre.java`.

## Para profundizar

- [Tutorial oficial de Oracle — JDBC](https://docs.oracle.com/javase/tutorial/jdbc/)
- [Documentación de H2 Database](https://h2database.com/html/main.html)
- Módulo 21: JPA/Hibernate, la capa que automatiza este mapeo.

---

*Antes de seguir: corré los tres ejemplos. Ver un rollback dejar los saldos intactos enseña más que diez párrafos.*

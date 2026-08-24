# Módulo 11 — Records: datos que se declaran solos

¿Cansado de escribir cuarenta líneas para modelar un punto con dos coordenadas? Bienvenido al **record**: le decís al compilador *qué datos* tiene tu tipo y él te escribe el constructor, los accesores, `equals`, `hashCode` y `toString`. Vos declarás la forma; Java hace el trabajo repetitivo.

## Quick path

1. Leé "¿Qué es un record?" para entender la idea en una frase.
2. Mirá el antes/después en "¿Por qué existen?".
3. Corré los ejemplos: `java ejemplos/IgualdadPorValor.java`.

---

## ¿Qué es un record?

Un **record** es un portador de datos **inmutable en superficie**, introducido como feature estándar en **Java 16** (preview desde el 14). En **una sola línea** declarás sus componentes y el compilador genera automáticamente:

- campos `private final` por cada componente,
- un **constructor canónico** que los asigna todos,
- **accesores** con el nombre del componente (`x()`, no `getX()`),
- `equals()`, `hashCode()` y `toString()` consistentes entre sí.

La analogía: un record es un **formulario impreso**. Lo llenás una vez, queda firmado, y nadie puede tachar ni corregir nada después. Si necesitás otro dato distinto... imprimís otro formulario. No se edita: se reemplaza.

```java
record Punto(int x, int y) {}
```

Esa línea equivale a una clase completa de decenas de líneas. No es azúcar "porque sí": es reconocer que muchísimas clases son *solo datos*, y que ese patrón merecía soporte del lenguaje.

## ¿Por qué existen?

**Motivo 1: matar el código repetitivo (boilerplate) de los DTOs.** Un *DTO* (Data Transfer Object, objeto de transferencia de datos) es una clase sin comportamiento propio cuyo único trabajo es llevar datos de un lado a otro — por ejemplo, del servidor a la pantalla. La clase tradicional equivalente exige campo + getter + constructor + `equals` + `hashCode` + `toString`, cada uno escrito a mano y cada uno una oportunidad de errar (¿te acordás de generar `equals` sin `hashCode`?). El record condensa todo eso:

```java
// Clase tradicional (~40 líneas)          // Record (1 línea)
public final class Dinero {                record Dinero(int centavos, String moneda) {}
    private final int centavos;
    private final String moneda;

    public Dinero(int centavos, String moneda) {
        this.centavos = centavos;
        this.moneda = moneda;
    }
    public int getCentavos() { return centavos; }
    public String getMoneda() { return moneda; }

    @Override public boolean equals(Object o) { /* ... */ }
    @Override public int hashCode() { /* ... */ }
    @Override public String toString() { /* ... */ }
}
```

**Motivo 2: la inmutabilidad previene categorías enteras de bugs.** Cuando un objeto puede mutar y además lo comparten varias partes del programa (**estado mutable compartido**), aparecen errores imposibles de rastrear: alguien cambia un valor "de costado" y otro componente falla tres métodos más adelante. Un record garantiza por contrato que *nadie* va a cambiar esos valores debajo de tus pies. Podés pasarlos a cualquier hilo, guardarlos donde quieras: son seguros.

## ¿Quién lo usa?

- **APIs modernas del JDK**: muchos métodos nuevos devuelven records internamente; `Map.Entry` fue el precedente conceptual (un par clave-valor).
- **Spring Boot**: DTOs de entrada/salida, proyecciones de consultas, eventos de dominio.
- **Valores de dominio**: coordenadas, montos, rangos de fechas, colores, códigos postales con ciudad.
- **Pares y tuplas ligeras** cuando necesitás devolver dos cosas relacionadas sin crear jerarquías.

## ¿Cómo funciona?

### Sintaxis básica

```java
record Punto(int x, int y) {}

var p = new Punto(3, 4);
System.out.println(p.x());      // 3  ← ¡sin el "get"!
System.out.println(p);          // Punto[x=3, y=4]
```

### Accesores: `x()`, NO `getX()`

Los accesores se llaman **igual que el componente**. No es estilo JavaBeans (`getX()`), y eso es deliberado: un record no promete seguir esa convención. Si mezclás con herramientas que exigen getters clásicos (algunos frameworks viejos, JSP/EL antiguo), vas a sentir el roce — lo vemos en Errores comunes.

### `equals`: compara VALORES, no referencias

En una clase común, `==` y `equals` heredados comparan **referencias** ("¿son el mismo objeto?"). El compilador genera `equals` para que dos records con **componentes iguales sean iguales**:

```java
var a = new Punto(3, 4);
var b = new Punto(3, 4);
a == b        // false: objetos distintos en memoria
a.equals(b)   // true : mismos valores
```

Esto es lo que hacés a mano con clases escribiendo `equals` campo por campo. Acá sale gratis y **consistente**.

### `hashCode`: consistente con equals

El contrato es ley: si dos objetos son `equals`, su `hashCode` debe coincidir. El generado cumple esto automáticamente — fundamental para usar records como claves de `HashMap` o dentro de `HashSet`.

### Constructor compacto: validación

Si querés validar o normalizar **sin repetir la lista de parámetros**, usá el **constructor compacto**: escribís solo las reglas y la asignación de campos sigue siendo automática.

```java
record Rango(int minimo, int maximo) {
    Rango {                                   // constructor compacto
        if (minimo > maximo) {
            throw new IllegalArgumentException(
                "minimo (" + minimo + ") > maximo (" + maximo + ")");
        }
    }
}
```

Al salir del bloque sin excepción, los campos quedan asignados solos. Invariante protegido, cero repetición.

### Constructores normales y métodos propios

Un record también admite constructores alternativos (que delegan al canónico con `this(...)`) y métodos de instancia, típicamente **derivados** de los componentes:

```java
record Rectangulo(int ancho, int alto) {
    Rectangulo {                              // validación
        if (ancho <= 0 || alto <= 0) throw new IllegalArgumentException("dimensiones positivas");
    }
    Rectangulo cuadradoDe(int lado) {         // constructor alternativo
        return new Rectangulo(lado, lado);
    }
    int area() { return ancho * alto; }       // método derivado
}
```

### Límites: qué NO podés hacer con un record

| Restricción | Motivo |
|---|---|
| Es implícitamente `final`: no se puede extender | Su valor está en ser una forma de datos cerrada y predecible |
| Los componentes son `final`: no hay setters | Inmutabilidad por diseño; "cambiar" = crear otro record |
| Puede implementar interfaces, pero no extender clases | Herencia única cerrada; polimorfismo vía interfaces |
| Todos los componentes participan en `equals/hashCode` | No podés excluir campos de la igualdad como en una clase |

**Ojo con la inmutabilidad superficial:** el record congela las *referencias*, no el contenido. Si un componente es un array o una `List` mutable, sus elementos siguen siendo modificables:

```java
record Pedido(List<String> items) {}

var items = new ArrayList<>(List.of("café"));
var pedido = new Pedido(items);
items.add("té");                    // ¡el record "vio" el cambio!
```

Para inmutabilidad real, guardá copias defensivas en el constructor compacto (`List.copyOf(items)`).

### Sealed classes: el hermano moderno

Las **sealed classes** complementan a los records: mientras el record cierra *los datos* de un tipo, `sealed` cierra *la jerarquía* de tipos, permitiendo listar exactamente qué clases pueden implementarla o extenderla. Juntas habilitan modelar dominios cerrados (ej.: `interface Forma permits Circulo, Rectangulo`) donde el compilador conoce todas las variantes — la base de los *pattern matching* exhaustivos (le preguntás al compilador qué tipo concreto es cada objeto y él verifica que cubriste todos los casos posibles). Las veremos a fondo en su propio módulo.

## ¿Dónde se usa?

- **Capa de transporte**: los datos que entran y salen de tu API (request/response: petición y respuesta HTTP), mensajes entre servicios.
- **Resultados intermedios**: lo que devuelve un cálculo antes de persistirse.
- **Claves compuestas**: `(usuarioId, cursoId)` como clave de un `Map`.
- **Configuración y opciones**: paquetes de parámetros inmutables que pasás entre capas.

## ¿Cuándo usarlo y cuándo NO?

**Regla de decisión:**

> Si el tipo es un **valor puro o datos en tránsito** (dos instancias iguales deben ser intercambiables) → **record, SIEMPRE**.
> Si el tipo tiene **identidad propia y ciclo de vida** (su estado cambia y su identidad sobrevive a los cambios) → **clase tradicional**.

| Situación | ¿Record? | Por qué |
|---|---|---|
| DTO de API, resultado de query, par coordenado | ✅ Sí | Valor puro; igualdad por componentes es lo correcto |
| Entidad de base de datos con JPA/Hibernate | ❌ No | JPA necesita setters/campos mutables, proxy dinámico y `final` en clase rompe lazy loading |
| Objeto con identidad (`Usuario` con id y estado cambiante) | ❌ No | Identidad ≠ valores; el estado muta durante su vida |
| Componente de UI acumulando estado | ❌ No | Necesitás mutabilidad controlada, no inmutabilidad |

Regla extra dura: **las entidades JPA no pueden ser records** — Hibernate requiere constructor sin argumentos y campos mutables; un record viola ambas cosas.

## Ejemplo práctico

Mirá `ejemplos/RecordsConValidacion.java`: un `Producto` con precio validado en el constructor compacto y un método derivado `conDescuento()` que devuelve un **nuevo** record. Después corré `ejemplos/IgualdadPorValor.java` y mirá cómo `==` y `equals` cuentan historias distintas según uses record o clase.

## Buenas prácticas

- **Validá en el constructor compacto**: es tu única puerta de entrada; si algo entra, quedó validado.
- **Pocos componentes** (idealmente ≤ 4): si necesitás más, probablemente haya un concepto intermedio escondido (¡extraelo!).
- **Nombrá por sustantivo de dominio**: `DireccionPostal`, `TasaCambio`, no `Datos1`.
- **Copiá colecciones entrantes** (`List.copyOf`) para inmutabilidad profunda.
- Para "modificar", ofrecé métodos `withX()` / `conX()` que devuelvan un nuevo record.

## Errores comunes

1. **Esperar setters.** `pedido.setItems(...)` no existe ni va a existir: creá un nuevo record con los valores actualizados.
2. **Usar records para entidades JPA.** Hibernate necesita mutabilidad y constructor vacío; el record se lo niega. Entidad = clase.
3. **Mutuar un componente `List` y creer que el record protege.** La inmutabilidad es superficial: si guardaste una lista mutable, afuera puede cambiar. Usá copias defensivas.
4. **Confundir `x()` con `getX()`** al integrar con herramientas JavaBeans: los accesores de record no siguen esa convención y algunos frameworks antiguos no los encuentran.

## Resumen express

- `record Nombre(tipo campo1, tipo campo2) {}` declara datos + constructor + accesores + `equals/hashCode/toString` en una línea.
- Accesores sin prefijo `get`; igualdad **por valor**, no por referencia.
- Constructor compacto = lugar natural para validar.
- Implícitamente `final`; componentes `final`; inmutabilidad **superficial**.
- Para valores y datos en tránsito: sí. Para entidades con identidad y ciclo de vida: no.

## Ejercicios

Practicá en `ejercicios/` (cuando estén disponibles):

1. **Tu primer record** — convertí una clase de datos a record y observá qué gana gratis.
2. **Validación con constructor compacto** — rechazá valores inválidos en la puerta de entrada.
3. **Métodos derivados** — calculá valores a partir de los componentes sin romper la inmutabilidad.
4. **Records en una colección** — usá records como claves de un `Map` gracias a `equals`/`hashCode` automáticos.
5. **Desafío: ¿record o clase?** — dado un escenario, decidí y defendé tu elección.

## Para profundizar

- Documentación oficial: *Records* en Java SE (dev.java/learn/records).
- JEP 395: la especificación original de records.
- JEP 409: sealed classes, el compañero de jerarquías cerradas.
- Módulos anteriores: 05-encapsulamiento (getters/setters que el record reemplaza), 09-interfaces (records implementando contratos).

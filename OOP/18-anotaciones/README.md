# 18 · Anotaciones — etiquetas para el código

Las anotaciones son **metadata que otros leen**: etiquetas que pegás sobre clases,
métodos o campos para declarar algo *acerca* del código, sin escribir lógica extra.
En este módulo vas a entender quién las lee, cómo crear las tuyas y por qué son
el motor invisible de frameworks como Spring o JPA.

## Requisitos previos

- Módulos 06 (herencia) y 09 (interfaces): para entender `@Override`.
- Nociones básicas de reflexión (`Class`, `Method`): las usamos en la segunda mitad.

## ¿Qué es una annotation?

Una annotation es como una **nota adhesiva pegada al código**:

```java
@Override
public String toString() { ... }
```

Tres ideas clave:

1. Se aplican sobre clases, métodos, campos, parámetros y hasta sobre otras anotaciones.
2. Llevan **metadata** (información *sobre* el código, no lógica del negocio).
3. Por sí solas **no ejecutan nada**. La nota no hace la tarea: alguien tiene que leerla.

> Analogía: dejás un post-it en la heladera que dice "regar las plantas". El papel
> no riega nada. Si nadie lee el post-it, las plantas se secan igual.

## ¿Por qué existen?

Antes de las anotaciones (Java 5, 2004), configurar frameworks significaba
escribir XML interminable:

```xml
<bean id="servicio" class="com.ejemplo.ServicioEmail"/>
```

La promesa de las anotaciones es la **configuración declarativa**: en lugar de
*conectar código*, *etiquetás* y una herramienta actúa. Menos boilerplate, y la
configuración vive junto al código que describe.

## ¿Quién las usa?

Esta es la sección más importante del módulo. Hay tres lectores principales:

### 1. El compilador

| Annotation | Qué hace |
|---|---|
| `@Override` | Verifica que realmente estés sobrescribiendo un método del padre. Caza typos. |
| `@Deprecated` | Marca API obsoleta; el compilador emite warnings a quien la use. |
| `@SuppressWarnings("x")` | Silencia un warning específico, de forma acotada. |

Con `@Override`, si te equivocás y escribís `hacerRuido()` cuando el padre tiene
`hacerSonido()`, obtenés **error de compilación** en vez de un bug silencioso.

### 2. Los frameworks de testing

Si alguna vez escribiste `@Test` en JUnit, ya usaste anotaciones: el runner de
JUnit escanea tus métodos, encuentra los etiquetados y los ejecuta.

### 3. Frameworks vía reflection en runtime

Aquí vive la industria:

- **Spring**: `@Component`, `@Autowired`, `@Service` → detecta y conecta componentes.
- **JPA / Hibernate**: `@Entity`, `@Id`, `@Column` → convierte clases en tablas (¡lo vemos en el módulo 21!).
- **Jackson**: `@JsonProperty` → controla cómo tu objeto se serializa a JSON.

Todos funcionan igual: **leen las etiquetas con reflexión** mientras tu programa corre.

## ¿Cómo funciona?

Paso a paso, del uso a la creación:

### Paso 1 — Usar las integradas correctamente

Ya lo hacés: `@Override` arriba del método, `@Deprecated` arriba de lo obsoleto.

### Paso 2 — Declarar la tuya propia

```java
@interface MiEtiqueta {
    String valor() default "";
}
```

Se declara con `@interface`. Los "atributos" son métodos sin cuerpo;
`default` da un valor opcional.

### Paso 3 — Decirle a Java dónde vive la etiqueta (meta-anotaciones)

```java
@Retention(RetentionPolicy.RUNTIME)   // ¿hasta cuándo sobrevive?
@Target(ElementType.METHOD)           // ¿dónde se puede pegar?
@interface MiEtiqueta { }
```

| Meta-anotación | Valores | Significado |
|---|---|---|
| `@Retention` | `SOURCE` | Solo existe en el código fuente; el compilador la descarta. |
| | `CLASS` | Llega al `.class` pero la JVM no la carga (default). |
| | `RUNTIME` | Legible con reflexión en ejecución. |
| `@Target` | `TYPE`, `METHOD`, `FIELD`, ... | Dónde se permite aplicar. |

**Regla de oro**: si querés leerla con reflexión, `Retention` debe ser `RUNTIME`.
Sin eso, tu etiqueta desaparece antes de que corra el programa.

### Paso 4 — Leerla con reflexión

Si "el framework detecta mis clases" te suena a magia, respirá: **no hay magia, es solo leer etiquetas**. *Reflexión* es el nombre técnico de algo humilde: preguntarle al código por sí mismo mientras corre. Cuando Spring arranca y encuentra tus componentes, todo lo que hace es esto:

1. Recorre las clases del proyecto (el famoso escaneo inicial).
2. Le pregunta a cada clase: «¿tenés la etiqueta `@Component`?» → eso es `getAnnotation(...)`.
3. Si la respuesta es `null`, sigue de largo: sin etiqueta no hay reacción.
4. Si la etiqueta está, la registra en su lista y después decide crear y conectar ese objeto.

Un cartero que lee los sobres antes de repartirlos. En Java, la pregunta se escribe así:

```java
MiEtiqueta etiqueta = clase.getAnnotation(MiEtiqueta.class);
if (etiqueta != null) {
    System.out.println(etiqueta.valor());
}
```

### Paso 5 — El patrón escáner (lo que hacen los frameworks)

Recorrer métodos, preguntar por la etiqueta, reaccionar:

```java
for (Method metodo : clase.getDeclaredMethods()) {
    if (metodo.isAnnotationPresent(MiEtiqueta.class)) {
        // ¡reaccioná!: invocarlo, registrar, validar...
    }
}
```

Esto, en 15 líneas, es el corazón de Spring, JUnit y JPA. Sin misterio:
**loop + reflexión + reacción**.

## ¿Dónde se usa?

- Marcar validaciones (`@NotNull`, `@Min`) que un validador interpreta.
- Definir tareas programadas con responsables y prioridades propias.
- Documentar contratos: qué método reemplaza a cuál, qué está en desuso.
- Testing: describir casos, timeouts, fixtures.

## ¿Cuándo NO?

- **No inventes anotaciones para lo que resuelve un método, una constante o un enum.**
  Si la única lectora va a ser tu propio `if`, no necesitás etiqueta: necesitás código normal.
- **Ojo con el costo de la reflexión**: escanear clases en runtime es más caro que
  llamar métodos directo. En rutas calientes de rendimiento, medí antes.
- No la uses para lógica condicional compleja: una anotación describe, no decide.

## Ejemplo práctico

En [`ejemplos/`](ejemplos/):

1. `AnotacionesDelCompilador.java` — `@Override` cazando typos, `@Deprecated`
   con su reemplazo documentado, `@SuppressWarnings` bien acotado.
2. `MiPrimeraAnotacion.java` — tu primera etiqueta propia: `@RutinaImportante`.
3. `EscaneandoAnotaciones.java` — el mini-framework: escanea, ordena por prioridad
   y ejecuta los métodos etiquetados.

```bash
cd ejemplos
java AnotacionesDelCompilador.java   # compilalo con -Xlint:deprecation para ver el warning
java MiPrimeraAnotacion.java
java EscaneandoAnotaciones.java
```

## Buenas prácticas

- **Nombrala como adjetivo o concepto**: `@RutinaImportante`, `@EnDesuso`,
  `@SoloLectura`. Debería poder leerse en voz alta sobre el elemento.
- **Seteá siempre `@Retention` y `@Target` explícitos**, aunque el default exista:
  comunican intención y evitan el clásico olvido del `RUNTIME`.
- **Documentá el procesador junto a la etiqueta**: una anotación sin lector es un
  post-it en idioma inventado. Un comentario apuntando al escáner salva vidas.
- Mantené los atributos simples (String, int, boolean, enums, Class, arrays de estos).

## Errores comunes

1. **Olvidar `RUNTIME` en `@Retention`** → la reflexión devuelve `null` y pensás
   que hay un bug en tu escáner, cuando la etiqueta nunca llegó a runtime.
2. **Esperar que la anotación "haga algo" sola** → sin procesador (compilador,
   framework o tu escáner), es decoración costosa.
3. **`@SuppressWarnings` como estilo de vida** → silenciar warnings generales
   esconde problemas reales. Acotal al mínimo bloque posible y nombrá el warning exacto.

## Resumen express

- Annotations = **metadata pegada al código**; no ejecutan nada por sí solas.
- Las leen tres: **compilador**, **runners de test** y **frameworks vía reflection**.
- Crearlas: `@interface` + `@Retention(RUNTIME)` + `@Target(...)`.
- Leerlas: `getAnnotation(...)` dentro de un loop → **patrón escáner**.
- Ese loop de 15 líneas es, literalmente, cómo arranca un mini-framework.

## Ejercicios

1. **Override detective** — Rompés a propósito un `@Override` con un typo y
   observás cómo el compilador te lo impide. Después lo arreglás.
2. **Deprecar con elegancia** — Marcá un método como `@Deprecated` con javadoc
   que apunte a su reemplazo, usalo desde **otra clase (en otro archivo)** y
   compilá con `-Xlint:deprecation` para ver el warning aparecer. Pista: si lo
   usás dentro de la misma clase, Java no te avisa.
3. **Tu propia etiqueta** — Creá `@TareaPendiente(conResponsable)` y aplicala
   sobre dos métodos de una clase.
4. **Escáner de etiquetas** — Escribí el loop con reflexión que encuentre todos
   los métodos `@TareaPendiente` e imprima sus responsables.
5. **Desafío mini-validador** — Creá `@Longitud(min, max)` para campos String y
   un validador que recorra los campos de un objeto con reflexión y avise cuáles
   incumplen. Bonus: lanzá una excepción propia cuando fallan.

## Para profundizar

- `java.lang.annotation` en la documentación oficial: todas las meta-anotaciones.
- `AnnotatedElement` (la interfaz que implementan `Class`, `Method`, `Field`).
- Anotaciones repetibles (`@Repeatable`) y anotaciones de tipo (`ElementType.TYPE_USE`).
- Procesadores de anotaciones en tiempo de compilación (APT): otra forma de "leer",
  antes de que corra el programa.

---

**Lo que sigue**: con anotaciones en tu caja de herramientas, los próximos
módulos de frameworks y persistencia (JPA, módulo 21) van a revelar todos sus trucos.

# Módulo 04 — Constructores

Todo objeto nace alguna vez. El constructor es el parto: el momento exacto en que decidís qué estado tiene tu objeto desde el primer segundo. Si lo escribís bien, es imposible tener un objeto "medio roto" dando vueltas por tu programa.

## ¿Qué es un constructor?

Un **constructor** es un método especial cuya única misión es **inicializar el estado de un objeto** en el instante en que se crea con `new`.

Sus reglas, sin excepciones:

- Se llama **exactamente igual que la clase** (mismas mayúsculas y minúsculas).
- **No tiene tipo de retorno**, ni siquiera `void`. Si le ponés uno, deja de ser constructor y pasa a ser un método común con nombre sospechoso.
- Se ejecuta **una sola vez**, automáticamente, justo después de reservar memoria para el objeto.
- Podés escribir varios, mientras difieran en parámetros (sobrecarga).

```java
class Persona {
    String nombre;

    Persona(String nombre) {   // constructor: mismo nombre, cero retorno
        this.nombre = nombre;
    }
}
```

## ¿Por qué existen?

Sin constructores, los objetos nacen con valores por defecto: referencias en `null`, números en `0`, booleanos en `false`. Eso significa objetos **a medio construir** que cualquier línea de tu programa puede usar antes de tiempo.

El constructor existe para garantizar una sola cosa: **si el objeto existe, es válido**. Nace listo para trabajar o no nace. Esa garantía vale oro: te ahorra mil chequeos defensivos desparramados por todo el código.

## ¿Quién lo usa?

- La JVM, cuando ejecutás `new`.
- Vos, cada vez que necesitás un objeto con datos concretos.
- Todos los frameworks serios: Spring, Jakarta EE, Jackson... todos instancian tus clases llamando constructores (a veces por reflexión).
- Los contenedores de **inyección de dependencias**: su trabajo favorito es crear objetos pasándoles sus dependencias justamente por constructor.

## ¿Cómo funciona?

Veamos la mecánica completa, paso a paso. Cada fragmento está en `ejemplos/`.

### 1. El constructor por defecto (y LA TRAMPA)

Si no escribís ningún constructor, Java genera uno vacío por vos: el **constructor por defecto**. Por eso durante módulos anteriores pudiste hacer `new Perro()` sin definir nada.

```java
class Gato {
    String nombre;          // sin constructor escrito...
}                            // ...pero new Gato() compila igual

Gato g = new Gato();         // el compilador inventó el constructor
```

**LA TRAMPA**: ese constructor automático vive solo mientras no escribas ninguno. En cuanto declarás uno con parámetros, el default **desaparece**:

```java
class Gato {
    String nombre;
    Gato(String nombre) { this.nombre = nombre; }
}

Gato g = new Gato();   // ERROR DE COMPILACIÓN: ya no existe
```

No fue eliminado por nadie: nunca existió como código tuyo, era un regalo condicional del compilador.

### 2. Constructor parametrizado

La forma más común: recibís los datos iniciales y los asignás a los campos.

```java
Persona(String nombre, int edad) {
    this.nombre = nombre;
    this.edad = edad;
}
```

Ojo con `this.` : sin él, `nombre = nombre` asignaría el parámetro a sí mismo (el parámetro **oculta** al campo). Con `this.nombre` decís explícitamente "el campo del objeto que estoy construyendo".

### 3. Sobrecarga: varias formas de nacer

Podés ofrecer más de un constructor, cada uno con distinta lista de parámetros. Es dar comodidad a quien usa tu clase:

```java
Producto(String nombre)                     // mínimo indispensable
Producto(String nombre, double precio)      // con precio
Producto(String nombre, double precio, int stock)   // completo
```

### 4. Encadenar con this(...): DRY entre constructores

Cuando tres constructores repiten las mismas asignaciones, tenés lógica duplicada. La solución: que los "chicos" deleguen en el "grande" con `this(...)`.

```java
Producto(String nombre) {
    this(nombre, 0.0);              // delega, no duplica
}

Producto(String nombre, double precio) {
    this(nombre, precio, 0);
}

Producto(String nombre, double precio, int stock) {
    this.nombre = nombre;           // el canónico: única fuente de verdad
    this.precio = precio;
    this.stock = stock;
}
```

Reglas de `this(...)`:

- Debe ser **la primera sentencia** del constructor.
- Solo puede llamarse una vez por constructor.
- Con esto, la inicialización real vive en UN solo lugar.

### 5. Orden de inicialización

Parece magia, pero tiene orden fijo:

1. Los campos se crean con sus valores por defecto (`null`, `0`, `false`).
2. Los **inicializadores de campo** (`int dias = 7;`) se ejecutan, en orden de declaración.
3. Recién entonces corre el cuerpo del constructor (después de que `this(...)` termine con su destino).

```java
class Temporizador {
    int base = 10;          // 2º: inicializador de campo
    int limite;

    Temporizador() {
        limite = base * 6;  // 3º: cuerpo; acá base ya vale 10
    }
}
```

### 6. Validación al nacer

Como el constructor es la puerta de entrada al mundo, es el lugar perfecto para rechazar basura **antes** de que exista el objeto:

```java
Cuenta(double saldoInicial) {
    if (saldoInicial < 0) {
        throw new IllegalArgumentException("El saldo no puede ser negativo");
    }
    this.saldo = saldoInicial;
}
```

Si la validación falla, `new` lanza la excepción y **no obtenés ninguna referencia**: no hay objeto inválido que perseguir.

## ¿Dónde se usa?

- Literalmente **cada vez que algo instancia un objeto**: `new ArrayList<>()`, `new Scanner(System.in)`...
- Frameworks web: crean controladores, servicios y DTOs por constructor.
- Contenedores de DI (Spring, Quarkus, Micronaut): elige el constructor, resuelve argumentos, invoca. Constructor con parámetros claros = dependencias explícitas y testeables.
- Serialización/deserialización (Jackson, Gson): necesitan reconstruir objetos, y muchos mecanismos apoyan constructores.

## ¿Cuándo usarlo y cuándo NO?

Usalo para: asignar campos, calcular valores derivados simples, validar invariantes, encadenar defaults.

**NO lo uses para trabajo pesado**: leer archivos, abrir conexiones, llamar APIs, esperar red, imprimir menús interactivos. Un constructor debe ser **barato y predecible**. Razones:

- No podés elegir *cuándo* se ejecuta: se dispara solo con `new`, y eso hace tests lentos y frágiles.
- Un constructor que falla a mitad de IO es un infierno de depuración.
- Difícil de testear: querés construir el objeto y luego inyectarle colaboradores, no pelear con el disco duro dentro del `new`.

Si necesitás IO, dejala fuera: construí el objeto liviano y dale un método que haga el trabajo.

## Ejemplo práctico

En `ejemplos/` tenés dos archivos listos para correr:

- `ConstructoresBasicos.java`: default automático vs parametrizado, y demostración en vivo de LA TRAMPA (el uso comentado que compila antes de escribir el parametrizado y rompe después).
- `ConstructoresSobrecargadosYThis.java`: `Producto` con tres constructores encadenados vía `this(...)`, mostrando estados finales y la moraleja de la única fuente de verdad.

Corrélos así:

```bash
cd OOP/04-constructores/ejemplos
java ConstructoresBasicos.java
java ConstructoresSobrecargadosYThis.java
```

## Buenas prácticas

1. **Un constructor canónico**: elegí el más completo, poné ahí TODA la asignación (y validación), y hacé que los demás deleguen con `this(...)`. Una sola fuente de verdad.
2. **Validá invariantes temprano**: si algo es inválido, que el objeto ni nazca.
3. Parámetros opcionales: resolvélos con sobrecarga o con el patrón telescópico (de menor a mayor aridad), siempre delegando. Si la cantidad de combinaciones explota, considerá un builder (tema futuro).
4. Mantenelos baratos: nada de IO, red ni lógica de negocio pesada.
5. Asigná SIEMPRE con `this.campo = parametro` cuando los nombres coincidan.

## Errores comunes

| Error | Qué pasa |
|-------|----------|
| Ponerle `void` al constructor | `void Persona() {}` NO es constructor: es un método común. El objeto sigue usando el default (¡y la trampa te muerde!) |
| Olvidar `this.` | `nombre = nombre;` autoasigna el parámetro: el campo queda `null` |
| Duplicar lógica entre constructores | Tres constructores copiando las mismas líneas = tres lugares para equivocarse. Usá `this(...)` |
| Trabajo pesado adentro | Leer archivos o conectar a BD en el constructor: código lento e intesteable |
| Esperar que el default siga ahí | Escribiste un constructor con parámetros y `new MiClase()` dejó de compilar |

## Resumen express

- Constructor = método especial, nombre de la clase, **sin retorno**.
- Inicializa estado; garantiza objetos válidos desde el nacimiento.
- Default automático **solo si no escribís ninguno** (LA TRAMPA).
- Sobrecarga = varias formas de construir; `this(...)` = delegación, primera sentencia.
- Orden: campos → inicializadores → cuerpo del constructor.
- Validación en el constructor = rechazar basura antes de que exista el objeto.
- Barato y simple adentro; el trabajo pesado va en métodos.

## Ejercicios

1. **Constructor básico de Persona** — Clase `Persona` con `nombre` y `edad`; constructor parametrizado que asigne ambos. Instanciala y mostrá el estado.
2. **Tres constructores de Producto** — `Producto` con constructores para (nombre), (nombre, precio) y (nombre, precio, stock). Creá objetos con cada variante.
3. **Encadenar con this()** — Refactorizá el ejercicio anterior para que los dos primeros constructores deleguen en el canónico con `this(...)`.
4. **Validación en constructor** — Agregale a `Producto` la regla "precio no negativo": lanzá `IllegalArgumentException` si se incumple y probá el caso inválido.
5. **Desafío FechaSimple** — Clase `FechaSimple` con día/mes/año: constructor canónico con validación (mes 1-12, día 1-31), constructor por defecto que use `this(...)` con una fecha fija, y método `descripcion()`.

## Para profundizar

- Bloques de inicialización de instancia (`{ ... }`) y bloques `static`.
- Constructores y herencia: `super(...)` y por qué Java inserta esa llamada si vos no la escribís.
- Records (JDK 16+): el constructor canónico generado y la validación compacta.
- Patrón Builder frente a constructores telescópicos cuando los parámetros se multiplican.
- Inyección de dependencias por constructor: por qué los frameworks lo prefieren.

# Módulo 02 — Clases y Objetos en profundidad

En el módulo anterior escribiste tu primera clase y tu primer objeto. Ahora abrimos la caja negra: vas a entender **técnicamente** qué es una clase, cómo funciona `new`, qué es una **referencia**, por qué existe `null` y qué hace el *garbage collector*. La POO deja de ser magia y pasa a ser mecánica entendible.

**Ruta rápida:** leé esta teoría (20 min) → ejecutá los tres ejemplos de `ejemplos/` → resolvé los ejercicios del final.

## ¿Qué es una clase, técnicamente?

Técnicamente, una clase es tres cosas a la vez:

1. **Una plantilla**: describe cómo serán los objetos de ese tipo.
2. **Un tipo**: al declararla creás un nuevo tipo de dato usable en variables, parámetros y retornos, igual que `int`.
3. **Un molde, no la cosa**: la clase `Reloj` no marca la hora; cada *instancia* sí.

Anatomía mínima:

```java
public class Reloj {

    // ESTADO: campos (fields) — lo que el objeto SABE
    int hora;
    int minutos;
    String modelo;

    // COMPORTAMIENTO: métodos — lo que el objeto HACE
    void avanzarMinuto() {
        minutos++;
        if (minutos == 60) {
            minutos = 0;
            hora = (hora + 1) % 24;
        }
    }

    public static void main(String[] args) {  // OPCIONAL: solo si la clase se ejecuta sola
        Reloj despertador = new Reloj();       // acá recién nace un objeto
    }
}
```

El `main` NO es parte del rol de plantilla: está solo para probar con `java Reloj.java`; muchas clases de dominio ni lo tienen. Y al escribir `Reloj relojDePared;` estás usando `Reloj` **como tipo**: crear tipos es la superpoder de la POO.

## ¿Por qué separar estado y comportamiento?

Son responsabilidades distintas que cambian por razones distintas:

| Aspecto | Estado (campos) | Comportamiento (métodos) |
|---|---|---|
| Pregunta que responde | ¿qué sé? | ¿qué sé hacer? |
| Vive | dentro de cada objeto | definido en la clase |
| Cambia | en tiempo de ejecución | solo si editás código |
| Ejemplo (`CuentaBancaria`) | `saldo` | `depositar()`, `extraer()` |

Con variables sueltas y lógica suelta, cada cambio dependería de que "alguien" actualice todo en orden. Encapsulados en una clase, **el objeto se administra solo**: nadie toca `saldo`; le piden a la cuenta que se deposite a sí misma.

## ¿Quién lo usa?

Todo el ecosistema Java: `String`, `Scanner`, `ArrayList` son clases de la librería estándar. Cada vez que instanciás algo con `new`, aplicás exactamente lo de este módulo.

## ¿Cómo funciona? Paso a paso

### Paso 1 — Declarar la clase

```java
class Producto {
    String nombre;
    double precio;
    int stock;

    void reducirStock(int cantidad) {
        stock -= cantidad;
    }
}
```

Esto solo **define** el molde. Todavía no hay ningún producto en memoria.

### Paso 2 — Instanciar con `new`

```java
Producto teclado = new Producto();
teclado.nombre = "Teclado K380";
teclado.precio = 45999.0;
teclado.stock = 12;
```

`new Producto()` reserva memoria, inicializa los campos con valores por defecto (`0`, `0.0`, `false`, `null`) y recién ahí el objeto existe.

### Paso 3 — ¿Qué devuelve `new`? Una referencia

Acá está el concepto más importante del módulo: **`new` NO devuelve el objeto; devuelve una referencia al objeto.**

Analogía de la caja de zapatos: el objeto es una caja en un depósito gigante (el *heap*); la variable es una **etiqueta con la dirección del depósito**, no la caja. Al escribir `teclado.precio`, Java sigue esa dirección hasta la caja. Consecuencia directa: dos variables pueden apuntar **a la misma caja** (lo ves en `ReferenciasYMemoriaDemo.java`).

### Paso 4 — Primitivos vs objetos

| | Primitivos (`int`, `double`, `char`, `boolean`...) | Objetos (`Producto`, `String`...) |
|---|---|---|
| Qué guarda la variable | **el valor** directamente | **una referencia** al objeto |
| Asignación `b = a` | copia el valor | copia la referencia (¡no el objeto!) |
| Puede ser `null` | no | sí |

```java
Producto p1 = new Producto();
Producto p2 = p1;   // ¡NO se copió el producto! Dos etiquetas, UNA caja
p2.stock = 0;       // p1 también ve stock == 0
```

### Paso 5 — Memoria: stack y heap, simple

- **Stack (pila)**: ordenada y veloz; viven las **variables locales**. Para primitivos, el valor entero; para objetos, solo la **referencia**.
- **Heap (montón)**: grande y flexible; viven **los objetos** con todos sus campos.

```text
STACK                          HEAP
┌───────────────────┐          ┌──────────────────────────────┐
│ teclado ──────► #A7 │ ──────► │ Objeto Producto @A7           │
│ precioIva = 21.0    │         │   nombre="Teclado K380"       │
│ (primitivo, acá)    │         │   precio=45999.0  stock=12    │
└───────────────────┘          └──────────────────────────────┘
```

Variable en el stack, objeto en el heap, y la variable contiene la dirección que los conecta: ese es todo el misterio.

### Paso 6 — `null`: la referencia que no apunta a nada

Una variable de tipo objeto puede estar vacía: sin caja asignada. Ese "vacío" se llama `null`.

```java
Producto encontrado = null;             // la etiqueta existe, no señala nada
System.out.println(encontrado.nombre);  // 💥 NullPointerException
```

El famoso **NullPointerException** (NPE) ocurre al pedirle algo a una referencia `null`: Java sigue la dirección buscando la caja... y la dirección es "ninguna". Primera defensa:

```java
if (encontrado != null) System.out.println(encontrado.nombre);
else System.out.println("No se encontró el producto.");
```

Regla mental: **antes de usar un punto sobre una variable, preguntate si podría ser null.**

### Paso 7 — Instancias independientes

Cada `new` crea un objeto **nuevo, con estado propio**:

```java
Producto teclado = new Producto();
Producto monitor = new Producto();
teclado.stock = 12;
monitor.stock = 3;
// teclado.stock sigue siendo 12: son cajas distintas
```

Los métodos también actúan por instancia: `teclado.reducirStock(2)` afecta solo al teclado.

### Paso 8 — Primer contacto con `this`

Si un **parámetro** se llama igual que un **campo**, Java usa el más cercano (el parámetro). Para referirte al campo del objeto actual, usá `this`:

```java
void renombrar(String nombre) {   // parámetro "nombre"
    this.nombre = nombre;         // this.nombre = el CAMPO; nombre = el PARÁMETRO
}
```

Leelo así: `this` significa "**este objeto, el que recibió el mensaje**". Lo profundizamos pronto; por ahora, usalo para desambiguar.

### Paso 9 — Ciclo de vida de un objeto

1. **Nace**: con `new`, en el heap.
2. **Vive**: mientras exista al menos una referencia accesible hacia él.
3. **Muere**: cuando ninguna referencia lo alcanza. Ahí entra el **garbage collector** (GC), un proceso automático de la JVM que reclama esa memoria. **No hay destructor que llamar**: vos no administrás memoria; si hacés `temporal = null` o la variable sale de alcance, el objeto queda inalcanzable y el GC lo limpia *cuando decide*, no cuando vos querés.

Ojo: eso significa que la recuperación de memoria está garantizada *eventualmente*, pero no podés forzarla ni sabés cuándo ocurre.

### Paso 10 — Convenciones de nombres

| Elemento | Convención | Ejemplo |
|---|---|---|
| Clases | `PascalCase`, sustantivo | `CuentaBancaria`, `Producto` |
| Métodos | `camelCase`, verbo | `calcularTotal()`, `renombrar()` |
| Campos | `camelCase`, sustantivo | `saldo`, `precioUnitario` |
| Constantes | `UPPER_SNAKE_CASE` | `IVA`, `DIAS_HABILES` (en serio pronto, con `static final`) |

Seguir convenciones no es esteticismo: es comunicación entre colegas.

## ¿Dónde se usa?

Modelado de dominio (`Factura`, `Pasajero`, `Turno`), librería estándar (`Random`, `LocalDate`) y frameworks como Spring o Android: todo gira alrededor de clases bien diseñadas.

## ¿Cuándo usarlo y cuándo NO?

**Usalo cuando:** hay datos + comportamiento que pertenecen juntos (cohesión); necesitás múltiples instancias con estado independiente; querés crear un **tipo** con nombre significativo para tu dominio.

**Evitalo (por ahora) cuando:** es un puñado de utilidades sin estado (eso suele resolverse con métodos `static`, tema posterior); un primitivo alcanza (modelar `Edad` como clase para un `int` es sobreingeniería inicial); estés aprendiendo (clases chicas y claras antes que jerarquías grandiosas).

## Ejemplo práctico

En `ejemplos/` tenés tres demos progresivas:

1. **`EstadoVsComportamiento.java`** — un objeto cuyo estado evoluciona mediante sus propios métodos.
2. **`ReferenciasYMemoriaDemo.java`** — aliasing: dos variables, un mismo objeto; contraste con primitivos.
3. **`NullSeguro.java`** — uso defensivo de `null` y NPE controlada.

Ejecutal desde esa carpeta con `java NombreDelArchivo.java`.

## Buenas prácticas

- **Inicializá el estado con sentido**: un objeto recién creado no debería dejar el programa en un estado absurdo (stock negativo, saldo indefinido).
- **Evitá métodos que devuelven `null`** cuando puedas: devolvé un valor vacío razonable o documentá claramente cuándo puede venir `null`. Menos NPEs para todos.
- **Clases chicas y enfocadas**: una clase = una responsabilidad. Si necesitás llamarla `GestorAdminSistemaHelper`, hay un diseño confuso escondido ahí.
- **Usá `this` ante cualquier ambigüedad**, aunque sea redundante: comunica intención.

## Errores comunes

| Error | Por qué pasa | Cómo evitarlo |
|---|---|---|
| NPE por olvidar instanciar | `Producto p; p.nombre = "x";` — `p` es `null` | Siempre `new` antes de usar (o verificá contra `null`) |
| Confundir copiar referencia con copiar objeto | `Producto b = a;` parece duplicar el objeto | Caja de zapatos: dos etiquetas, una sola caja |
| Creer que `==` compara contenido | `a == b` compara **direcciones**, no campos | Para contenido: comparación campo a campo (luego veremos `equals`) |
| Sombrear campos sin `this` | Parámetro homónimo: la asignación no llega al campo | `this.campo = parametro` |

## Resumen express

- Una clase es plantilla **y** tipo; define campos (estado) y métodos (comportamiento).
- `new` crea el objeto en el heap y devuelve una **referencia**; variable primitiva guarda valor, variable objeto guarda dirección.
- Stack: variables y referencias. Heap: objetos.
- `null` = referencia sin destino; desreferenciarla lanza `NullPointerException`.
- `this` distingue el campo del parámetro cuando comparten nombre.
- Un objeto muere cuando nadie lo referencia; el garbage collector limpia. No hay destructores.
- Clases `PascalCase`, miembros `camelCase`, constantes `UPPER_SNAKE_CASE`.

## Ejercicios

Resolvelos en `ejercicios/` (después compará con `soluciones/`):

1. **CuentaBancaria básica** — clase con `titular` y `saldo`; `depositar(monto)` y `extraer(monto)` que validen fondos.
2. **Estados independientes** — dos cuentas de la misma clase; operá sobre una y demostrá que la otra no cambia.
3. **Experimento de referencias** — dado un código con aliasing, **predecí la salida ANTES de ejecutar**; después corrélo y contrastá.
4. **Defensa contra null** — método que busca un producto por nombre y puede devolver `null`; escribiló y consumilo sin que reviente.
5. **Desafío Libro y Socio** — modelá `Libro` y `Socio` con préstamo y devolución; el estado de uno depende del otro.

## Para profundizar

- Oracle: [Tutorial de clases y objetos](https://docs.oracle.com/javase/tutorial/java/concepts/)
- Módulo siguiente: **03-metodos** — parámetros, retornos, sobrecarga y `this` en serio.

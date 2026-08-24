# 07 · Polimorfismo

> **El hook:** escribís `animal.hacerSonido()` una sola vez... y cada animal responde a su manera. Un mismo mensaje, respuestas distintas. Esa es la magia que veníamos persiguiendo desde herencia.

## La idea antes del vocabulario

Pensá en los botones de apagar de tu casa. El de la tele, el del ventilador, el de la radio: apretás **el mismo botón** en todos, y cada aparato responde a su manera. La tele se pone negra, el ventilador frena poco a poco, la radio se calla. Vos hiciste un solo gesto; la respuesta dependió de qué aparato estaba conectado.

Otra versión: en la veterinaria, el veterinario le dice *"hacé tu sonido"* a cada animal de la sala de espera. Al perro le responde un guau, al gato un miau, al loro una frase entera. Nadie tuvo que preguntar "¿vos sos perro o gato?" antes de hablarle: le habló igual a todos, y cada uno respondió como sabe.

Eso es todo el polimorfismo: **mismo mensaje, respuestas distintas**. Ahora sí, veamos cómo se llama técnicamente.

## ¿Qué es el polimorfismo?

Polimorfismo significa "muchas formas": **una misma llamada de método produce comportamientos distintos según el objeto real que recibe el mensaje**.

La pieza clave se llama *dynamic dispatch* (despacho dinámico): **el método que se ejecuta depende del tipo REAL del objeto en tiempo de ejecución, no del tipo de la variable** que lo referencia.

```java
Animal a = new Perro();
a.hacerSonido(); // ¿guau o sonido genérico? → "Guau guau"
```

- La variable `a` es de tipo `Animal` (el tipo declarado).
- El objeto en memoria es un `Perro` (el tipo real).
- En tiempo de ejecución, Java mira el tipo **real** y ejecuta `Perro.hacerSonido()`.

## ¿Por qué existe?

Para que escribas código **una vez, contra el tipo general**, y funcione con todo lo que exista hoy y mañana:

- **Escribir contra lo general:** un bucle sobre `Animal[]` sirve para perros, gatos y cualquier subclase futura.
- **Abierto a extensión sin modificación:** agregás una subclase nueva (¡un `Pato`!) y el bucle que despacha sonidos **no cambia ni una línea**.
- Menos duplicación: cada clase define su versión del comportamiento; nadie repite `if`s por todos lados.

## ¿Quién lo usa?

Literalmente todo el ecosistema Java:

| Lugar | Polimorfismo invisible |
|---|---|
| Frameworks (callbacks, listeners) | Te dan una interfaz base; ellos llaman TU implementación |
| `Collections.sort(lista)` | Llama `compareTo()` polimórficamente de cada elemento `Comparable` |
| `System.out.println(x)` | Llama `x.toString()`, cada clase responde con su representación |

**En tu primer trabajo vas a ver esto en**: frameworks que llaman métodos que *vos* sobrescribiste (un controller web, un listener de clicks), en `toString()` redefinido para que los logs sean legibles, y en tests donde intercambiás variantes de un mismo comportamiento. El patrón "yo escribo el bucle general, cada objeto aporta su variante" está en todas partes.

## ¿Cómo funciona? Paso a paso

### 1. Tipo de la variable ≠ tipo del objeto

```java
Animal a = new Perro(); // variable: Animal | objeto: Perro
```

### 2. Upcasting: siempre seguro

Asignar un objeto de la subclase a una variable de la superclase ("subir" en la jerarquía) **nunca falla**: todo `Perro` ES un `Animal`, garantizado. No requiere cast explícito.

```java
Animal a = new Perro();        // implícito
Animal b = (Animal) new Gato(); // explícito, innecesario pero válido
```

### 3. Dynamic dispatch: cómo decide Java

Cuando llamás `a.hacerSonido()`:

1. El compilador verifica que `Animal` declare ese método (si no, ni compila).
2. En runtime, JVM consulta el **tipo real** del objeto (`Perro`).
3. Ejecuta la versión más específica: arranca por `Perro`; si ahí no estuviera redefinido, sube hacia el padre hasta encontrarla.

> **Deep dive opcional (saltealo tranquilo):** por dentro, cada clase lleva una tabla con sus métodos (la idea se llama *vtable*): si `Perro` sobrescribió el método, su entrada apunta al código de `Perro`. Es el detalle técnico detrás del paso 3; no lo necesitás para usar polimorfismo. Alcanza con recordar: **manda siempre el tipo real**.

**Analogía:** llamás al mismo número de teléfono de la empresa central (el mensaje). Quién atiende depende de a qué sucursal esté conectada esa línea (el objeto real). El número es uno; la persona que responde, distinta.

### 4. El límite: métodos exclusivos de la subclase

A través de una variable `Animal` solo podés llamar lo que `Animal` declara:

```java
Animal a = new Perro();
a.ladrar(); // ❌ ERROR DE COMPILACIÓN: Animal no declara ladrar()
```

### 5. Downcasting: bajar, con cuidado

Volver de `Animal` a `Perro` requiere cast explícito y puede fallar con `ClassCastException`. Verificá primero con `instanceof` + **pattern matching** (Java 16+):

```java
if (a instanceof Perro perro) {
    perro.ladrar(); // 'perro' ya está convertido, sin cast manual
}
```

Si usás Java anterior a 16 sería `if (a instanceof Perro) { ((Perro) a).ladrar(); }`.

### Sobrescritura vs sobrecarga

| Aspecto | Sobrescritura (@Override) | Sobrecarga |
|---|---|---|
| Firma | **Igual** (nombre + parámetros) | Mismo nombre, **parámetros distintos** |
| Cuerpo | Nuevo cuerpo en la subclase | Nuevo cuerpo en la misma clase (o subclase) |
| Se resuelve en | **Tiempo de ejecución** (tipo real del objeto) | **Tiempo de compilación** (tipos de los argumentos) |
| Herencia | Requiere relación padre-hijo | No la requiere |
| Anotación | `@Override` recomendado | No aplica |

## ¿Dónde se usa?

- Colecciones heterogéneas: `List<Forma>` con círculos, rectángulos y triángulos procesadas juntas.
- Estrategias intercambiables: distintos `MedioDePago` detrás de una interfaz común.
- Plugins y frameworks: definen contratos base; vos aportás las variantes.
- Tests: dobles de prueba (fakes) que reemplazan dependencias reales.

## ¿Cuándo usarlo y cuándo NO?

**Sí:** cuando tenés una cadena larga de `if/else if` preguntando por tipos (`if (x instanceof Perro) ... else if (x instanceof Gato) ...`). Eso es polimorfismo pidiendo a gritos ser aplicado: movés cada rama a un método sobrescrito y la cadena desaparece.

**No:** si no hay un comportamiento variante real, no inventes jerarquías solo "para usar polimorfismo". Una jerarquía forzada cuesta más que los `if`s honestos.

## Ejemplo práctico

Mirá `ejemplos/DespachoDinamico.java`: un zoológico (`Animal[]`) donde cada animal responde a `hacerSonido()` a su manera. Agregar un `Pato` nuevo requeriría crear su archivo y nada más: el bucle ni se entera.

## Buenas prácticas

- **Programá contra el tipo general** (`Animal`, `Forma`, `MedioDePago`), no contra clases concretas.
- **Usá siempre `@Override`**: el compilador te avisa si la firma no coincide exactamente.
- **Minimizá el downcasting**: si necesitás muchos `instanceof`, tu diseño está gritando por un método polimórfico faltante en la base.
- Mantené los métodos sobrescritos con la **misma semántica** que promete la superclase (principio de sustitución).

## Errores comunes

1. **Esperar que el tipo de la variable elija el método.** No: manda el tipo real del objeto en runtime.
2. **`ClassCastException` por downcastear sin verificar.** Usá `instanceof` con pattern matching antes.
3. **Confundir resolución de sobrecarga.** Los parámetros se eligen **en compilación**, mirando los tipos declarados de los argumentos — no el tipo real en runtime.
4. **Creer que los campos son polimórficos.** Los atributos NO participan del dynamic dispatch: se resuelven por el tipo de la variable.

```java
class Animal    { String nombre = "animal"; }
class Perro extends Animal { String nombre = "perro"; }

Animal a = new Perro();
System.out.println(a.nombre); // "animal" ← ¡por el tipo de la VARIABLE!
```

## Resumen express

- Un mensaje, muchas respuestas: el método se elige por el **tipo real** en runtime (*dynamic dispatch*).
- Upcasting siempre seguro; downcasting solo tras `instanceof` (mejor con pattern matching).
- Sobrescritura = runtime, misma firma. Sobrecarga = compilación, firma distinta.
- Los campos nunca son polimórficos.
- Sirve para extender sistemas sin modificarlos: código escrito una vez contra la clase base.

## Ejercicios

1. **Polimorfismo básico con figuras** — Jerarquía `Forma` con `calcularArea()`; lista mixta y suma total.
2. **Procesar pagos sin if-else** — `MedioDePago` con variantes tarjeta/efectivo/transferencia despachadas polimórficamente.
3. **instanceof y comportamiento exclusivo** — Upcastear aves, invocar el vuelo solo donde corresponde usando pattern matching.
4. **Refactor: matar el if-else gigante** — Convertir una cadena de `instanceof` en un diseño polimórfico limpio.
5. **Desafío nómina polimórfica** — Empleados asalariados y por hora cobrando vía `calcularSueldo()` en un único bucle.

## Para profundizar

- Clases abstractas e interfaces (módulo siguiente): contratos sin estado.
- `Object.toString()` / `equals()`: polimorfismo que ya usás sin darte cuenta.
- Principios SOLID, en particular *Open/Closed* y *Liskov Substitution*.
- Documentación oficial: [Herencia y polimorfismo](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html)

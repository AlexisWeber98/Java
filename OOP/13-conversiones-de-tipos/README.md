# Módulo 13 · Conversiones de tipos

Todo dato viaja con pasaporte: conversiones seguras y peligrosas. En Java ningún valor cambia de tipo por arte de magia: o el compilador autoriza un viaje seguro, o vos firmás con el *cast* y te hacés cargo de lo que se pierda en el camino. Este módulo te enseña a distinguir cuál es cuál antes de que un `double` te corrompa un precio o un `parseInt` tumbe tu app con input de usuario.

## ¿Qué es una conversión de tipos?

Es transformar un valor de un tipo a otro: de `int` a `double`, de `Integer` a `int`, de `"42"` a `42`. Hay tres familias bien distintas:

| Familia | Ejemplo | Riesgo |
|---------|---------|--------|
| **Widening** (ensanchamiento) | `int` → `double` | Ninguno: siempre entra |
| **Narrowing** (estrechamiento) | `double` → `int` | Pérdida silenciosa |
| **Boxing / parsing** | `String` ↔ número | Excepciones en tiempo de ejecución |

La regla mental: **ir hacia un tipo más chico o más frágil siempre cobra peaje**.

## ¿Por qué importa?

Dos desastres clásicos, ambos silenciosos hasta que duelen:

1. **Precisión perdida que corrompe plata**: si guardás un precio en `float` o truncás `(int)` un total con decimales, los centavos desaparecen sin error ni warning. Una caja que calcula mal no avisa: factura mal.
2. **Parsing que tira la app**: `Integer.parseInt("hola")` lanza `NumberFormatException`. Con input de usuario (lo que tipea la persona que usa tu programa) eso no es "si pasa", es "cuándo pasa". Sin try/catch, tu programa muere en la línea exacta donde el usuario escribió cualquier cosa.

## ¿Quién lo usa?

- **Sistemas financieros**: convierten entre representaciones enteras y decimales todo el día (y por eso odian el `double` para dinero).
- **APIs y parsing de entrada**: todo request HTTP llega como texto; convertir a números es la primera operación del backend.
- **Videojuegos**: posiciones en `double`, píxeles en `int`; castean constantemente.
- **Colecciones genéricas**: `List<Integer>` obliga a autoboxear cada `int` que metés.

## ¿Cómo funciona?

### Paso 1 · Primitivos: ensanchamiento automático

La cadena segura, cada tipo "entra" en el siguiente:

```
byte → short → int → long → float → double
         ↘ char ↗
```

```java
int entero = 130;
long grande = entero;      // automático: long aguanta todo int
double decimal = grande;   // automático: cero pérdida
```

El compilador lo hace solo porque **nunca hay pérdida**: todo valor de `int` tiene lugar garantizado en `double`.

### Paso 2 · Primitivos: estrechamiento explícito con pérdida

Al revés necesitás cast `(tipo)` y el compilador te dice "firmaste": recorta o desborda **sin avisar**.

```java
double precio = 9.99;
int entero = (int) precio;        // 9 → trunca, NO redondea

int enorme = 300;
byte chico = (byte) enorme;       // 44 → overflow: wraparound (da la vuelta, como el odómetro del auto)
// byte va de -128 a 127: 300 - 256 = 44. Los bits que no entran, se cortan.
```

Fijate: `(int) 9.99` da `9`, no `10`. El cast **trunca** siempre. Si querés redondear, usá `Math.round()`.

### Paso 3 · La dualidad numérica de `char`

Un `char` es un número disfrazado de letra (su código Unicode):

```java
char letra = 'A';
int codigo = letra;          // 65, automático (char cabe en int)
char siguiente = (char) (letra + 1);  // 'B', necesita cast
```

Ojo: `'A' + 1` es aritmética (`66`), no concatenación de texto.

### Paso 4 · Objetos: upcast gratis, downcast vigilado

Ya lo viste en el módulo 07 con herencia: subir en la jerarquía es libre; bajar exige cast y verificación.

```java
Object figura = new Circulo();            // upcast: automático
if (figura instanceof Circulo circulo) {  // downcast seguro: instanceof comprueba Y convierte (pattern matching)
    circulo.dibujar();
}
```

Sin el `instanceof`, un cast fallido lanza `ClassCastException` en runtime.

### Paso 5 · Autoboxing: `Integer` ↔ `int` y SUS trampas

Java convierte solo entre primitivos y sus envoltorios (wrappers):

```java
Integer caja = 5;    // autoboxing: int → Integer
int pelado = caja;   // unboxing: Integer → int
```

**Trampa 1 — el `==` miente con enteros grandes.** Java cachea `Integer` entre `-128` y `127`, así que dos cajas chicas son el mismo objeto... pero afuera del caché son objetos distintos:

```java
Integer a = 127, b = 127;
System.out.println(a == b);     // true  (mismo objeto del caché)

Integer x = 128, y = 128;
System.out.println(x == y);     // false (!) objetos distintos
System.out.println(x.equals(y)); // true ← SIEMPRE equals para wrappers
```

**Trampa 2 — unboxing de `null` reventá con `NullPointerException`:**

```java
Integer valorNulo = null;
// int rompio = valorNulo;  // NPE: Java intenta valorNulo.intValue() sobre null
```

### Paso 6 · Strings ↔ números: parsear con red de seguridad

Todo texto de usuario llega como `String`. Convertirlo puede fallar:

```java
int edad = Integer.parseInt("42");      // ok
double altura = Double.parseDouble("1.75"); // ok
// Integer.parseInt("cuarenta y dos");  // NumberFormatException 💥
```

**Receta segura** — helper reutilizable con try/catch:

```java
static Integer parsearEnteroSeguro(String texto) {
    try {
        return Integer.parseInt(texto.trim());
    } catch (NumberFormatException e) {
        return null; // o un default, según tu caso
    }
}
```

**Camino inverso**, de número a texto:

```java
String s1 = String.valueOf(42);   // forma explícita
String s2 = "" + 42;              // concatenación: funciona, menos clara
String precioFormateado = String.format("$%.2f", 9.5); // "$9.50" (preview módulo 15)
```

## ¿Dónde se usa?

- **Lectura de archivos/JSON/CSV**: todo llega como texto y hay que convertirlo.
- **Cálculos mixtos**: promedios (`int` → `double`), índices (`double` → `int`).
- **Interfaces gráficas y juegos**: coordenadas decimales que terminan en píxeles enteros.
- **Bases de datos**: mapear columnas numéricas a tipos Java distintos.

## ¿Cuándo NO convertir?

- **Si podés elegir bien el tipo desde el inicio, elegilo.** Si un dato tiene decimales, declaralo `double` de entrada; no lo guardes `int` para castear después.
- **Evitá cadenas de dobles casts** (`(int) (double) (long) x`): casi siempre indican que el diseño de tipos está mal.
- **Nunca uses `double` para dinero.** Sus errores de redondeo binario corrompen totales. Existe `BigDecimal` para eso — lo vemos en profundidad más adelante.
- No castees "para callar al compilador": si necesitás un cast raro, primero preguntate si el tipo original era el equivocado.

## Ejemplo práctico

Un mini procesador de entrada que combina todo: parsing seguro, conversión y formateo.

```java
public class ProcesadorEntrada {
    public static void main(String[] args) {
        String[] entradas = { "25", " 30 ", "abc" };
        for (String entrada : entradas) {
            Integer edad = parsearEnteroSeguro(entrada);
            if (edad == null) {
                System.out.println("Entrada inválida: \"" + entrada + "\"");
            } else {
                double edadPromediada = edad; // widening gratis
                System.out.printf("Edad válida: %d (%.1f como double)%n", edad, edadPromediada);
            }
        }
    }

    static Integer parsearEnteroSeguro(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
```

Los ejemplos completos están en [`ejemplos/`](ejemplos/): ejecutalos con `java ejemplos/ConversionesPrimitivos.java` desde esta carpeta.

## Buenas prácticas

- Dejá que el compilador ensanche solo (`int` → `double`); castea únicamente cuando estrechás.
- Antes de un cast estrecho, preguntate: ¿puedo perder datos? ¿el rango entra?
- Usá `instanceof Tipo variable` (pattern matching) para todo downcast.
- Compará wrappers con `.equals()`, nunca con `==`.
- Validá todo input con try/catch antes de parsearlo.
- Para redondear usá `Math.round()`, no `(int)`.

## Errores comunes

- **`==` en boxed types**: funciona con 127 y falla con 128. El caché te acostumbra mal; `.equals()` siempre.
- **Esperar redondeo de un cast**: `(int) 9.99` es `9`. El cast trunca; `Math.round(9.99)` sí da `10`.
- **`parseInt` sin try/catch**: un espacio o una letra del usuario y tu app muere.
- **Asumir que `char` + algo concatena**: `'A' + 1` suma códigos (da `66`), no `"A1"`.
- **Olvidar el unboxing implícito**: operar `Integer` nulo como `int` lanza NPE lejos de donde pusiste el `null`.

## Resumen express

| Conversión | Sintaxis | Riesgo |
|------------|----------|--------|
| Widening primitivo | Automática | Ninguno |
| Narrowing primitivo | `(tipo) valor` | Pérdida silenciosa |
| Upcast de objetos | Automático | Ninguno |
| Downcast de objetos | `instanceof` + cast | `ClassCastException` |
| Boxing / unboxing | Automático | NPE con `null`, `==` mentiroso |
| String → número | `parseInt` / `parseDouble` | `NumberFormatException` |
| Número → String | `String.valueOf` | Ninguno |

## Ejercicios

1. **Escalera de conversiones** — Recorré `byte → short → int → long → float → double` imprimiendo cada paso; después intentá volver con casts y anotá qué cambia.
2. **Pérdida silenciosa** — Tomá `9.99` y `300`, aplicá narrowing a `int` y `byte`, y explicá en un comentario por qué obtenés `9` y `44`.
3. **Parsing a prueba de usuarios** — Escribí `parsearDoubleSeguro` con try/catch que acepte `"1.75"` y sobreviva a `"hola"`.
4. **La trampa del autoboxing** — Reproducí el misterio de `127 == 127` vs `128 == 128` y corregilo con `.equals()`.
5. **Desafío procesador de entrada** — Combiná todo: leé un array de strings mixtas, convertí las válidas y reportá las inválidas sin que el programa muera.

## Para profundizar

- [Conversión primitiva en la especificación del lenguaje (JLS)](https://docs.oracle.com/javase/specs/jls/se25/html/jls-5.html#jls-5.1.2) y el caché de `Integer` entre `-128` y `127`.
- Clase `BigDecimal` para aritmética monetaria exacta.
- Módulos vecinos: 07 (herencia y upcasting), 14 (formato de salida).

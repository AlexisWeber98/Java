# 03 · Métodos: dale comportamiento a tus objetos

Hasta ahora tus objetos *saben* cosas (atributos). En este módulo van a aprender a **hacer** cosas: los métodos son los verbos de la POO, y dominarlos es la diferencia entre un programa espagueti y uno que se entiende a primera vista.

> **Requisito**: haber trabajado el módulo 02 (clases, objetos y referencias). Acá vamos a usar todo eso.

---

## ¿Qué es un método?

Un método es un **bloque de código con nombre** que vive dentro de una clase y realiza una tarea. Le das un nombre, le pasás datos de entrada (parámetros) y opcionalmente te devuelve un resultado.

```java
public class Calculadora {
    double sumar(double a, double b) {   // firma: nombre + lista de parámetros
        return a + b;                    // tipo de retorno: double
    }
}
```

### Anatomía

| Pieza | Ejemplo | Qué hace |
|-------|---------|----------|
| Modificadores | `public` | Quién puede llamarlo |
| Tipo de retorno | `double` | Qué devuelve (o `void`) |
| Nombre | `sumar` | Cómo se lo invoca |
| Parámetros | `(double a, double b)` | Datos de entrada |
| Cuerpo | `{ return a + b; }` | La tarea en sí |

La **firma** del método es solo el **nombre + la lista de parámetros** (en ese orden y con esos tipos). El tipo de retorno NO forma parte de la firma — esto va a importar cuando hablemos de sobrecarga.

Si no devuelve nada, usás `void`. Y para **llamar** a un método desde un objeto:

```java
Calculadora calc = new Calculadora();
double total = calc.sumar(3.5, 2.0);  // el resultado queda en total
```

---

## ¿Por qué existen?

1. **Reutilización**: escribís la lógica una vez y la llamás mil veces. Si mañana hay que corregir algo, lo corregís en UN lugar.
2. **Nombres con intención**: `pedido.calcularTotal()` cuenta la historia del negocio; veinte líneas sueltas dentro del `main` no.
3. **Responsabilidad única**: cada método hace UNA cosa y la hace bien. Eso hace el código testeable, legible y fácil de cambiar.

Pensalo como una receta: en vez de repetir "cortar, cocinar, condimentar" cada vez, definís el paso "preparar salsa" y lo citás cuando lo necesitás.

## ¿Quién lo usa?

- **Los objetos entre sí**: un `Pedido` le pide al `Cliente` su dirección (`cliente.getCiudad()`).
- **Vos, desde el `main`**: orquestás el flujo llamando métodos.
- **La biblioteca estándar**: cada vez que escribís `"hola".length()` o `Math.round(3.7)` estás usando métodos que otro escribió por vos.

---

## ¿Cómo funciona?

### Paso 1 — Definir y llamar

Primero se define dentro de la clase; después se llama sobre un objeto (o sobre la clase, si es `static`). La llamada ejecuta el cuerpo y vuelve al punto exacto donde estaba el llamador.

### Paso 2 — El valor de retorno

Con `return` entregás un resultado y el método termina ahí mismo. El tipo devuelto tiene que coincidir con el declarado. Un método `void` también puede tener `return;` para cortar antes de tiempo.

```java
boolean esPar(int n) {
    if (n % 2 != 0) return false;   // salida anticipada
    return true;
}
```

### Paso 3 — Parámetros

Los parámetros son variables locales que se inicializan con los argumentos de la llamada. Podés tener ninguno, uno o varios separados por coma. Los nombres de parámetros pueden coincidir con atributos: usá `this.nombre` para distinguir el atributo del parámetro.

### Paso 4 — Sobrecarga (*overloading*)

Varios métodos pueden compartir nombre si sus **firmas** difieren en número o tipos de parámetros:

```java
int sumar(int a, int b)            { ... }
double sumar(double a, double b)   { ... }
int sumar(int a, int b, int c)     { ... }
```

El compilador elige cuál ejecutar mirando **los argumentos que pasás**, no el tipo de retorno. Por eso dos métodos que difieren SOLO en el tipo de retorno no compilan: la firma sería idéntica y el compilador no tiene forma de decidir.

### Paso 5 — Varargs: cantidad flexible de parámetros

Cuando un método acepta "cero o más valores del mismo tipo", usá varargs:

```java
double promedio(double... notas) { ... }
promedio(8);  promedio(7, 9, 10);   // ambas llamadas válidas
```

Adentro, `notas` es simplemente un arreglo. Útil para `sumar`, `promedio`, `maximo`... No abuses: si siempre recibís exactamente dos valores, mejor dos parámetros normales.

### Paso 6 — Métodos `static` vs métodos de instancia

- **De instancia**: se llaman sobre un objeto y pueden leer/modificar sus atributos. Es el caso por defecto: `miCuenta.depositar(500)`.
- **Static**: pertenecen a la clase, no a ningún objeto. Se llaman con el nombre de la clase y NO tocan estado de instancia: `Math.round(3.7)`, `Calculadora.estatica.sumar(2, 3)`.

Regla mental: si el método necesita los datos del objeto → instancia. Si es una utilidad pura (mismas entradas → misma salida, sin atributos) → `static`.

### Paso 7 — Paso por valor: qué pasa con tus argumentos

Java SIEMPRE pasa por valor. Lo que pasa depende de qué pasás:

- **Primitivos** (`int`, `double`, `boolean`...): se copia el valor. Cambiar el parámetro adentro NO afecta a la variable del llamador.
- **Objetos**: se copia **la referencia**. El objeto es único, así que mutarlo adentro (ej.: `cuenta.saldo = ...`) SÍ se ve afuera. Pero si **reasignás** el parámetro (`parametro = new Cuenta()`), solo cambiás tu copia local: el llamador sigue apuntando al objeto original.

Esta distinción explica el 90% de los bugs raros con objetos. Mirá `PasoPorValorDemo.java` antes de seguir.

---

## ¿Dónde se usa?

- **Constructores** (`new Cliente(...)`) son casos especiales de inicialización.
- **Getters/setters** encapsulan atributos.
- **Lógica de negocio**: `calcularDescuento()`, `validarStock()`.
- **Utilidades compartidas**: conversores, formateadores, helpers matemáticos.
- **`main`**: el punto de entrada es... ¡un método static!

## ¿Cuándo usarlo y cuándo NO?

**Sí**: cuando repetís lógica, cuando un bloque del `main` crece más de unas líneas, cuando podés nombrar un concepto del dominio.

**No / cuidado**:

- No sobrecargues cuando las variantes significan cosas distintas: si `registrar(nombre)` crea un usuario y `registrar(monto)` registra un pago, la sobrecarga confunde — usá nombres diferentes (`crearUsuario`, `registrarPago`).
- No hagas `static` algo que depende del estado del objeto: perdés polimorfismo y acoplás el código a la clase.
- No extraigas un método que necesita diez parámetros para funcionar: probablemente estés mezclando responsabilidades.

## Ejemplo práctico

En [`ejemplos/`](ejemplos/) tenés cuatro archivos independientes, pensados para leerse en orden:

| Archivo | Qué demuestra |
|---------|---------------|
| `AnatomiaDeUnMetodo.java` | Las piezas de un método, comentadas una por una |
| `SobrecargaCalculadora.java` | Tres `sumar(...)` y cómo elige el compilador |
| `StaticVsInstancia.java` | Utilidades static vs comportamiento de instancia |
| `PasoPorValorDemo.java` | Primitivos copiados, objetos mutables, referencias reasignadas |

Corré cualquiera sin compilar a mano:

```bash
java ejemplos/SobrecargaCalculadora.java
```

## Buenas prácticas

- **Usá verbos**: `calcularTotal`, `enviarEmail`, `esValido`. Los `get/es/hay` cuentan el estado del objeto sin exponer el atributo.
- **Una responsabilidad por método**: si necesitás un "y" para describirlo, dividilo.
- **Evitá parámetros booleanos** que cambian el comportamiento (`procesar(true)` no dice nada): preferí dos métodos bien nombrados.
- **Evitá efectos sorpresa**: un método llamado `calcularX` que además modifica atributos rompe la confianza del lector.
- **Métodos chicos**: si no entra en una pantalla, dividí. Los comentarios casi nunca arreglan un método largo.

## Errores comunes

1. **Olvidar el `return`**: error de compilación ("missing return statement"). Revisá también los caminos con `if`.
2. **Recursión infinita**: un método que se llama a sí mismo sin caso base desborda la pila (`StackOverflowError`). Mencionamos recursión acá porque el mecanismo es el mismo: cada llamada apila un marco nuevo.
3. **Esperar que un primitivo cambie afuera**: `intentarSumarDiez(edad)` deja `edad` intacta. El primitivo se copió.
4. **Sobrecarga ambigua**: `sumar(int, long)` vs `sumar(long, int)` con la llamada `sumar(3, 3)` obliga al compilador a ampliar tipos; si dos firmas empatan, no compila.
5. **Confundir reasignar con mutar**: `parametro = new X();` dentro del método no cambia la variable del llamador.

## Resumen express

- Un método = modificador + tipo de retorno + nombre + parámetros + cuerpo.
- La firma es nombre + parámetros; el retorno no participa.
- Sobrecarga = mismo nombre, firmas distintas; elige el compilador por tus argumentos.
- Varargs (`tipo...`) para cantidades variables del mismo tipo.
- `static` para utilidades sin estado; instancia para comportamiento que usa atributos.
- Todo pasa por valor: primitivos por copia de valor, objetos por copia de referencia.

## Ejercicios

1. **Métodos con retorno** — Creá `doble(n)` que devuelva el doble de un entero y `esPar(n)` que diga si es par. Probá ambos desde `main` e imprimí resultados.
2. **Calculadora de 4 operaciones** — Una clase con `sumar`, `restar`, `multiplicar` y `dividir`; en la división, validá el divisor cero y decidí qué devolver (¿0? ¿mensaje? documentá tu decisión).
3. **Sobrecarga de `area()`** — `area(double lado)` para el cuadrado, `area(double base, double altura)` para el rectángulo y `area(double radio, boolean esCirculo)` para el círculo. Llamá las tres desde `main`.
4. **Promedio con varargs** — `promedio(double... notas)` que devuelva el promedio; probá con 0, 2 y 5 notas. ¿Qué pasa si te pasan ninguna? Manejalo.
5. **Desafío: contador static vs instancia** — Una clase con un contador de instancias creado (`static`) y otro contador de llamadas por objeto (instancia). Creá tres objetos, llamá métodos varias veces y explicá los números finales en un comentario.

## Para profundizar

- *Overloading resolution* en el [Tutorial oficial de Oracle](https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html).
- JLS §15.12: cómo resuelve el compilador las llamadas sobrecargadas (para cuando quieras el nivel experto).
- Próximo módulo: **encapsulamiento** — getters, setters y por qué los atributos públicos son una trampa.

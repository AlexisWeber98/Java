# Módulo 09 · Interfaces: "prometo saber hacer esto"

Cerramos la Parte II del curso con la pieza que faltaba del polimorfismo. Una interfaz es
**un contrato de capacidad**: quien lo firma declara *"yo sé hacer esto"* sin revelar cómo,
y quien lo usa puede invocarlo confiando en esa promesa. Hoy vas a aprender a definir
contratos, a firmarlos con `implements` y a desacoplar el QUÉ del CÓMO.

> **La idea en una frase:** la herencia responde "qué ES" (`ES-UN`); la interfaz responde
> "qué SABE HACER" (`PUEDE-HACER`).

## Requisitos

- Haber completado los módulos 06 a 08 (herencia, polimorfismo, clases abstractas).
- JDK 25 instalado. Cada ejemplo corre standalone: `java NombreArchivo.java`.

## Quick path

1. Leé "¿Qué es una interfaz?" para fijar el modelo mental del contrato.
2. Corré los tres ejemplos de `ejemplos/`, uno por vez, leyendo su salida.
3. Resolvé los 5 ejercicios del final, en orden.

---

## ¿Qué es una interfaz?

Una interfaz es un tipo que declara **qué se puede hacer** con sus implementadores, sin
decir cómo se hace. Es un contrato puro de capacidad:

```java
interface Pagable {
    boolean cobrar(double monto);   // sin cuerpo: solo la promesa
}
```

Reglas del contrato:

| Regla | Detalle |
|-------|---------|
| Métodos | Son implícitamente `public abstract` (salvo `default` y `static`) |
| Campos | Solo pueden ser constantes `public static final` (usá con moderación) |
| Instancias | No se puede hacer `new` de una interfaz directamente |
| Múltiples contratos | Una clase puede `implements` varias interfaces a la vez |

**¿Por qué Java permite muchas interfaces pero no herencia múltiple de clases?**
Porque las interfaces no traen estado (ni campos de instancia ni constructores). Dos
interfaces no pueden chocar por "quién inicializa qué atributo"; en el peor caso, dos
`default` con la misma firma obligan a resolver la ambigüedad explícitamente. Con estado
duplicado, en cambio, el conflicto sería invisible y letal. Por eso: muchas capacidades,
una sola cadena de estado.

Ojo con la distinción conceptual: si tu clase **es algo**, usás herencia (`Pato extends
Ave`). Si tu clase **sabe hacer algo**, usás interfaz (`Pato implements Volador`).

## ¿Por qué existen?

El propósito central es **desacoplar el QUÉ del CÓMO**: el código cliente pide una
capacidad y le da exactamente igual quién la cumpla.

La analogía del enchufe: el tomacorriente define el contrato (forma, voltaje). Podés
conectar una lámpara, un ventilador o un cargador; cualquier aparato que respete el
contrato funciona. Si mañana cambiás la lámpara, el enchufe no se entera. Así se siente
intercambiar implementaciones sin tocar código cliente.

Beneficio concreto: podés escribir `procesarCobro(Pagable p)` hoy con tarjeta y efectivo,
y mañana agregar MercadoPago **sin modificar una línea** del proceso de cobro.

## ¿Quién lo usa?

Las interfaces están en todos lados del día a día, aunque no las notices:

- `Comparable<T>` / `Comparator<T>`: ordenar colecciones.
- `Runnable`: pasar comportamiento a un hilo.
- `List`, `Set`, `Map`: ¡son interfaces! Por eso escribís `List<String> nombres = new ArrayList<>();`
  y podrías cambiar a `LinkedList` sin tocar el resto.

Si ya hiciste `for (String s : lista)` usando una variable declarada como `List`, venís
programando contra interfaces desde el módulo de colecciones.

**En tu primer trabajo vas a ver esto en**: repositorios declarados como interfaz
(`RepositorioUsuarios` con una implementación en memoria y otra en base de datos), en
`Comparable` cuando te pidan ordenar una lista por un criterio de negocio, y en cada API
que te pide implementar una interfaz para engancharte: listeners, handlers, callbacks.

## ¿Cómo funciona? Paso a paso

**1. Declarar la interfaz** (el contrato):

```java
interface Volador {
    void volar();
}
```

**2. Firmarla con `implements`** (y cumplir TODO lo prometido):

```java
class Pato implements Volador {
    @Override
    public void volar() {           // public obligatorio: no podés bajar visibilidad
        System.out.println("El pato vuela bajo");
    }
}
```

Si no implementás todos los métodos, la clase debe declararse `abstract`.

**3. Usar el TIPO interfaz** como variable o parámetro (ahí vive el polimorfismo):

```java
static void despegar(Volador v) { v.volar(); }
Volador candidato = new Pato();  // vale cualquier implementación
```

**4. Métodos `default` (Java 8+)**: métodos con cuerpo dentro de la interfaz. Nacieron
para la **evolución segura de APIs**: cuando Java 8 quiso agregar `forEach` a
`Iterable`, miles de clases ya lo implementaban. En vez de romper todo, agregaron el
método con una implementación por defecto: las clases viejas siguieron compilando, y cada
implementación puede sobrescribirlo si quiere otro comportamiento:

```java
interface Audible {
    void sonar();

    default void silenciar() {
        System.out.println("(silencio)");
    }
}

class Parlante implements Audible {
    @Override public void sonar() { System.out.println("¡piii!"); }

    @Override
    public void silenciar() {       // sobrescribir es opcional
        Audible.super.silenciar();  // puedo reutilizar la versión del contrato
        System.out.println("Parlante en mute");
    }
}
```

**5. Métodos `static` y `private` en interfaces (breve)**: desde Java 8 una interfaz
puede tener métodos `static` (utilidades del propio contrato, se llaman como
`Interfaz.metodo()`) y desde Java 9 también `private`, útiles para que varios `default`
compartan código sin exponerlo.

**6. Constantes en interfaces**: todo campo es `public static final`. Existen, pero
usálas con moderación: hoy preferimos `enum` o clases de constantes para ese rol.

### Tabla de decisión: ¿interfaz o clase abstracta?

| Tu situación | Elegí |
|---|---|
| ¿Solo querés prometer una capacidad ("esto sabe volar") sin guardar ningún dato? | **Interfaz** |
| ¿Necesitás guardar estado compartido entre subclases (campos, constructor común) y ya tenés código para no repetir? | **Clase abstracta** |
| ¿Te sirven las dos cosas a la vez? | **Combiná**: interfaz `Volador` + clase abstracta `AveVoladora` que la implementa |

No compiten: la interfaz define la capacidad pública; la clase abstracta puede servir de
base reutilizable para un grupo de implementadores.

## ¿Dónde se usa?

- APIs de bibliotecas y frameworks (definen contratos que vos cumplís).
- Colecciones (`List`, `Set`, `Map`, `Queue`).
- Callbacks y tareas (`Runnable`, `Callable`).
- Orden natural y comparadores (`Comparable`, `Comparator`).
- Testing: dobles de prueba que implementan el mismo contrato que la dependencia real.

## ¿Cuándo usarlo y cuándo NO?

Usalo cuando haya (o preveas razonablemente) **varias implementaciones** de una misma
capacidad, o cuando quieras aislar un módulo de otro.

Nota pragmática: no crees interfaces de una sola implementación "por si acaso", sin una
necesidad real de intercambio. Esa ceremonia extra agrega indirección gratis. Empezá con
la clase concreta; cuando aparezca el segundo caso de uso, extraé la interfaz. Refactorizar
barato es mejor que especular caro.

## Ejemplo práctico

En `ejemplos/ContratoPagable.java`: `Pagable` promete `cobrar(monto)`;
`TarjetaCredito` valida límite, `PagoEfectivo` siempre acepta. El método
`procesarCobro(Pagable p, double monto)` sirve para ambos y para cualquier pago futuro.
Corrélo con `java ContratoPagable.java`.

## Buenas prácticas

- **Programá contra la interfaz** en firmas de métodos y campos: `void imprimir(List<String>)`,
  no `void imprimir(ArrayList<String>)`.
- **Contratos chicos y enfocados**: mejor tres interfaces de dos métodos que una de veinte
  (principio de segregación de interfaces, ISP). Que nadie firme un contrato con métodos que
  nunca va a necesitar.
- **Nombrá por capacidad**: adjetivos en `-able`/`-ible` (`Pagable`, `Comparable`) o sustantivos
  de rol (`Volador`, `Nadador`).

## Errores comunes

- **Olvidar el `public` al implementar**: el método queda package-private (menos visible que
  el contrato) y el compilador lo rechaza: "cannot reduce visibility". El `@Override` te salva.
- **Esperar campos de instancia en una interfaz**: no hay estado. Todo campo es constante
  `public static final`. Si necesitás estado, eso huele a clase abstracta.
- **Confundir herencia de `default` con herencia de clase**: un `default` aporta comportamiento
  pero jamás estado ni constructores; y si dos interfaces traen el mismo `default`, debés
  resolver el conflicto sobrescribiendo explícitamente.
- **Interfaces "cocina de restaurante"** (giant kitchen-sink): contratos gigantes que obligan a
  implementar de todo. Segregalos por capacidad.

## Resumen express

- Interfaz = contrato de capacidad (`PUEDE-HACER`); herencia = identidad (`ES-UN`).
- Métodos: implícitos `public abstract`; `default` y `static` traen cuerpo; campos: solo constantes.
- `implements` múltiples interfaces: legal porque no arrastran estado.
- Usá el tipo interfaz en variables y parámetros: ahí está el desacople.
- Interfaz (capacidad sin estado) vs. clase abstracta (estado + implementación parcial). Se combinan.

## Ejercicios

Resolvelos en orden; cada uno sube un escalón:

1. **Tu primer contrato**: creá `Sonoro` con `sonar()` e implementala en `Campana` y `Perro`.
2. **Un pato, tres habilidades**: `Pato` que implementa `Volador`, `Nadador` y `Caminante`;
   probalo guardándolo en variables de cada tipo de interfaz.
3. **default: usarlo y sobrescribirlo**: interfaz con un método `default`; una clase que lo
   usa tal cual y otra que lo sobrescribe llamando a `Interfaz.super.metodo()`.
4. **Programar contra la interfaz**: método `procesar(Trabajable t)` que funcione con al menos
   dos implementaciones distintas, sin tocar el método al agregar la segunda.
5. **Desafío e-commerce con tres contratos**: modelá `Pagable`, `Enviabile` y `Descontable`
   aplicados a productos/carritos de una tienda.

## Para profundizar

- Tutorial oficial de Oracle: [interfaces](https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html)
  y [default methods](https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html).
- Principio de segregación de interfaces (la "I" de SOLID).
- Interfaces funcionales y expresiones lambda (Java moderno): puerta de entrada al próximo bloque.

[← Módulo anterior](../08-clases-abstractas/) · [Inicio del curso](../README.md)

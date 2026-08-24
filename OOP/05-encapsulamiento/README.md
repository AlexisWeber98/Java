# Módulo 05 — Encapsulamiento

> Hasta acá sabés crear clases, constructores y métodos. Ahora viene la pregunta que separa un juguete de un sistema serio: **quién puede tocar los datos de tus objetos, y bajo qué reglas**. El encapsulamiento es la primera píldora del paradigma orientado a objetos, y es literalmente una cápsula: adentro protegés la medicina, afuera dejás solo la interfaz clara.

## ¿Qué es el encapsulamiento?

Es **ocultar el estado interno de un objeto y exponer en su lugar una API controlada**.

Los campos se vuelven `private`: nadie desde afuera puede leerlos ni escribirlos directamente. Lo que el mundo ve son métodos públicos (`depositar`, `extraer`, getters y setters) que deciden **cómo** se accede a esos datos.

La analogía clásica es la **cápsula de una pastilla**: la medicina (el estado interno) está protegida por una cubierta; vos no la abrís con las manos, la tragás. La cápsula define la única forma correcta de interactuar con lo que hay adentro. Si mañana el laboratorio cambia la fórmula interna, la cápsula sigue siendo una cápsula: tu forma de tomarla no cambia.

En Java esto se traduce en dos decisiones:

1. Campos `private` → el estado ya no es manipulable desde afuera.
2. Métodos `public` → la única puerta de entrada, con las reglas del negocio adentro.

## ¿Por qué existe?

Porque los **campos públicos permiten que cualquier código rompa las invariantes** de tu clase.

Mirá este desastre esperando a ocurrir:

```java
public class CuentaRota {
    public double saldo;      // cualquiera puede escribir esto
    public String nombreTitular;
}

// En algún lugar del programa, con la mejor intención:
CuentaRota cuenta = new CuentaRota();
cuenta.saldo = -9999;                    // ¿un saldo negativo "porque sí"?
cuenta.nombreTitular = "";               // ¿una persona sin nombre?
cuenta.nombreTitular = null;             // ahora todo código que lo usa puede romperse
```

Nada te avisa. Nada valida. La corrupción viaja silenciosa por el programa hasta explotar tres capas más abajo, donde el stack trace ya no tiene relación con la línea que causó el problema. Con encapsulamiento, ese mismo intento **no compila**: el compilador te frena en la puerta, no en producción.

## ¿Quién lo usa?

Todo codebase Java serio. Y más importante: **los frameworks están construidos sobre esta convención**:

- **Jackson** serializa/deserializa JSON leyendo `getNombre()` / escribiendo `setNombre()`.
- **JPA/Hibernate** mapea tablas a objetos accediendo a propiedades.
- **Spring**, **Lombok**, builders, validadores… todos asumen getters/setters estándar.

Si tu clase expone campos públicos, estás peleando contra el ecosistema entero.

## ¿Cómo funciona?

### 1. Los modificadores de acceso

Java ofrece cuatro niveles, ordenados de más restrictivo a más abierto:

| Modificador | Misma clase | Mismo paquete | Subclase | Todo el mundo |
|-------------|:-----------:|:-------------:|:--------:|:-------------:|
| `private`   | ✅          | ❌            | ❌       | ❌            |
| *(default)* | ✅          | ✅            | ❌       | ❌            |
| `protected` | ✅          | ✅            | ✅       | ❌            |
| `public`    | ✅          | ✅            | ✅       | ✅            |

*(default)* significa "sin escribir nada". Para campos, la regla práctica es simple: **`private` por defecto**, siempre.

### 2. Campos privados + accesores públicos

```java
public class Cuenta {
    private String titular;   // el estado vive detrás del muro
    private double saldo;

    public String getTitular() { return titular; }

    public void setTitular(String titular) {
        this.titular = titular;
    }
}
```

### 3. Validación dentro del setters

El setter es el **punto único de control**. Ahí se rechaza la basura:

```java
public void setTitular(String titular) {
    if (titular == null || titular.isBlank()) {
        throw new IllegalArgumentException("El titular es obligatorio");
    }
    this.titular = titular;
}
```

Ahora es **imposible** tener una cuenta sin titular. No "difícil": imposible.

### 4. Propiedades de solo lectura

Getter sin setter → el valor se consulta pero nadie externo lo cambia:

```java
public String getTitular() { return titular; }
// sin setTitular(): el titular solo cambia por operaciones legítimas
```

Ideal para datos inmutables después de crear el objeto (por ejemplo, el número de cuenta).

### 5. Getters calculados

Un getter puede **derivar** un valor en vez de devolver un campo almacenado:

```java
public boolean isSobregirada() {
    return saldo < 0;
}
```

No existe ningún campo `sobregirada`: se calcula al vuelo y nunca puede quedar desincronizado con `saldo`.

### 6. El concepto de invariante

Una **invariante** es una regla que debe cumplirse *siempre*, en todos los estados posibles del objeto. Ejemplos: "el saldo nunca es negativo", "el titular nunca es vacío".

Antes del encapsulamiento, cada línea de código cliente era responsable de respetarlas (y tarde o temprano alguna fallaba). Después, la invariante se defiende en **un único punto** — constructor y métodos — y el resto del programa puede confiar ciegamente. Eso es menos código repetido y menos bugs.

```java
public void extraer(double monto) {
    if (monto <= 0) {
        throw new IllegalArgumentException("El monto debe ser positivo");
    }
    if (monto > saldo) {
        throw new IllegalStateException("Saldo insuficiente");
    }
    saldo -= monto;
}
```

### 7. La convención JavaBeans

Java tiene un estándar de nombres para propiedades que los frameworks esperan al pie de la letra:

| Tipo         | Convención              | Ejemplo              |
|--------------|-------------------------|----------------------|
| Getter       | `get` + Campo           | `getSaldo()`         |
| Getter booleano | `is` + Campo         | `isActivo()`         |
| Setter       | `set` + Campo           | `setActivo(true)`    |

Si llamás al getter `obtenerSaldo()` en vez de `getSaldo()`, Jackson no lo va a ver, JPA tampoco. La convención **es** la API invisible sobre la que funciona el ecosistema.

## ¿Dónde se usa?

- **Modelo de dominio**: entidades como `Cuenta`, `Pedido`, `Paciente` — donde viven las reglas del negocio.
- **APIs y librerías**: lo que publicás para otros, porque no podés cambiarlo sin romperlos.
- **Integración con frameworks**: DTOs, entidades JPA, configuraciones Spring.
- En general: cualquier clase cuyo estado, si queda corrupto, produce bugs.

## ¿Cuándo usarlo y cuándo NO?

El encapsulamiento es una herramienta, no un ritual:

- **Datos tontos** (sin comportamiento ni invariantes): usá **records** — los vemos a fondo en el módulo 11. Un record es inmutable por diseño y te ahorra getters/setters manuales.
- **DTOs de transferencia pura**: si viajan entre capas sin reglas, un record o un objeto simple alcanza.
- **Setters sin sentido**: si `setX(x)` no valida ni protege ninguna invariante, pensá si esa propiedad debería existir así. Escribir setter "porque siempre se escribe" genera clases infladas sin valor.
- **Prototipos desechables**: para tirar código en cinco minutos, no hace falta ceremonia. Pero si sobrevive a la semana, encapsulalo.

La pregunta guía: **¿este campo tiene reglas?** Si la respuesta es sí, encapúlalo. Si es no, quizás ni necesites una clase tradicional.

## Ejemplo práctico

En [`ejemplos/`](ejemplos/) tenés el antes-y-después completo:

1. **`CampoPublicoPeligroso.java`** — un campo `saldo` público que termina en `-9999` por código aparentemente inocente, con informe de daños incluido.
2. **`CuentaEncapsulada.java`** — exactamente el mismo escenario, pero con campos privados y validaciones: la corrupción se vuelve imposible y ves ambas morales lado a lado.

Correlos así:

```bash
java ejemplos/CampoPublicoPeligroso.java
java ejemplos/CuentaEncapsulada.java
```

## Buenas prácticas

- **Campos `private` por defecto.** Ampliás visibilidad solo con una razón concreta, nunca al revés.
- **Validá en el punto único de entrada.** Constructor y métodos mutadores son los guardianes; nadie más repite la validación.
- **Exponé la superficie mínima.** Cada método público es un compromiso para siempre: si dudás, no lo agregues.
- **Preferí métodos con comportamiento sobre getters + lógica afuera.** Antes de escribir `if (cuenta.getSaldo() >= monto) { ... }` en el cliente, preguntate si la cuenta misma no debería ofrecer `extraer(monto)`. Las reglas del saldo pertenecen a la clase saldo, no dispersas por todo el programa.

## Errores comunes

- **Setter que solo asigna.** `setSaldo(double s) { this.saldo = s; }` valida cero; es un campo público disfrazado, con pasos extra.
- **Exponer estado mutable por el getter.** Devolver la lista interna permite que el cliente la modifique sin pasar por tus reglas. Solución definitiva: copias defensivas, en el módulo de colecciones.
- **Clase anémica.** Catorce getters, catorce setters, cero comportamiento. Toda la lógica vive afuera manipulando datos pelados: encapsulamiento cosmético, orientación a objetos ausente.
- **`protected` como "casi público".** Protege también hacia subclases y paquete; usarlo "por las dudas" abre puertas que después nadie cierra. Para campos: `private`.

## Resumen express

- Encapsulamiento = estado privado + API pública controlada.
- Existe porque los invariantes rotos son bugs silenciosos; con `private`, el compilador te frena.
- Setters validan, getters exponen (y pueden calcular), los métodos con comportamiento son la mejor interfaz.
- Convención JavaBeans (`getX`/`setX`/`isX`): los frameworks dependen de ella.
- Records para datos tontos; encapsulamiento serio para datos con reglas.

## Ejercicios

1. **Encapsular clase herida** — tomá una clase con campos públicos y convertila a privada + accesores sin romper su uso básico.
2. **Setter con validación** — escribí un setter que rechace valores inválidos lanzando `IllegalArgumentException`.
3. **Getter calculado** — implementá una propiedad que se derive de otras, sin campo propio.
4. **Clase con invariantes** — diseñá una clase cuyas reglas sean imposibles de violar desde afuera.
5. **Diseño encapsulado justificado** — modelá un caso real y defendé en comentarios qué exponés, qué ocultás y por qué.

Los consignas detalladas están en el README de ejercicios del curso; resolvé primero leyendo el módulo, no mirando soluciones.

## Para profundizar

- *Effective Java* (Joshua Bloch) — Ítem 25 y 15-16: minimizar accesibilidad e inmutabilidad.
- Documentación oficial de Java: [controlling access to members](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html).
- Artículo clásico sobre clases anémicas vs. modelo de dominio rico (Fowler, *Anemic Domain Model*).
- Módulo 11 (records) y el módulo de colecciones (copias defensivas) completan esta historia.

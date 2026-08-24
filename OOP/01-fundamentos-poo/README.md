# 01 · Fundamentos de POO

> **La idea del módulo en una frase:** la Programación Orientada a Objetos (POO) es una forma de organizar programas alrededor de *cosas del problema* —objetos— que guardan datos y saben hacer cosas, en lugar de solo instrucciones y variables sueltas.

Este módulo no te va a enseñar sintaxis nueva: te va a enseñar **a mirar distinto**. Al terminarlo vas a poder explicar qué es un objeto, qué es una clase, por qué la POO escala mejor que el código estructurado, y nombrar los 4 pilares sabiendo dónde se profundiza cada uno.

## Ruta rápida

1. Leé este README sin apuro.
2. Corré los 3 programas de `ejemplos/` y mirá cómo resuelven *el mismo problema* de dos formas distintas.
3. Hacé los 5 ejercicios de `ejercicios/` **sin mirar las soluciones**.
4. Compará tu trabajo con `soluciones/` y seguí al [módulo 02](../02-clases-y-objetos/).

---

## ¿Qué es la Programación Orientada a Objetos?

La POO es un **paradigma**: una manera de encarar la construcción de programas. En vez de escribir una lista larga de instrucciones que comparten variables sueltas, **modelás el programa como un conjunto de objetos que colaboran entre sí**.

Cada objeto combina dos cosas:

- **Estado (datos):** lo que el objeto *sabe*. Una mascota tiene nombre y edad.
- **Comportamiento:** lo que el objeto *hace*. Una mascota sabe presentarse, comer, dormir.

**Analogía:** pensá en un molde de galletitas. El molde define la forma (es la **clase**); cada galletita que horneás es un objeto concreto (una **instancia**) con su propio sabor y color. Un solo molde, infinitas galletitas independientes entre sí.

La traducción mundo real → código queda así:

| Mundo real | En tu programa |
|------------|----------------|
| "Un perro" como concepto | Clase `Perro` |
| Tu perro Luna | Objeto (instancia) de `Perro` |
| Su nombre, raza, edad | Atributos |
| Ladrar, comer, vacunarse | Métodos |

## ¿Por qué existe?

Porque los programas chicos funcionan bien con código estructurado, pero **los programas reales crecen**, y ahí el estilo estructurado empieza a doler. Mirá el mismo problema resuelto de las dos maneras.

**El problema:** controlar el stock de un producto en un almacén.

**Versión estructurada:** datos sueltos + funciones que los reciben y devuelven.

```java
// Los datos viven separados de las operaciones
int stockArroz = 30;
int stockFideos = 15;

// Las funciones operan sobre esos datos
static int vender(int stock, int unidades) {
    return stock - unidades;
}
```

Funciona... hasta que querés agregar validaciones, un segundo atributo (precio), un tercer producto. Entonces aparecen los problemas:

- **Datos y comportamiento separados:** cada función debe recordar qué variable tocar; nada impide mezclar el stock del arroz con el de los fideos.
- **Duplicación:** cada producto nuevo son más variables copiadas y pegadas.
- **Nadie protege los datos:** cualquier línea del programa puede modificar cualquier variable.

**Versión con objetos:** los datos y sus operaciones viven juntos dentro de un molde.

```java
class Producto {
    String nombre;
    int stock;

    void vender(int unidades) { ... }
}

Producto arroz = new Producto();
Producto fideos = new Producto(); // gratis: reutilizamos el molde
```

Ahora cada producto lleva su propio estado, las reglas están escritas **una sola vez**, y agregar productos no cuesta nada extra. Eso es lo que la POO compra: **orden y escalabilidad**.

Aclaración honesta: lo estructurado no es "malo". Para un script corto es perfecto. La POO brilla cuando el sistema crece, cambia y lo mantiene gente distinta a la que lo escribió.

## ¿Quién lo usa?

Prácticamente todo el ecosistema Java (y también muchos otros lenguajes):

| Quién | Qué modela con objetos |
|-------|------------------------|
| El propio JDK | `"texto".length()`, `ArrayList`, `Scanner`: casi todo en Java es un objeto |
| Spring Boot | Backends web: entidades, servicios, controladores |
| Android SDK | Cada pantalla, botón o lista de una app es un objeto |
| Videojuegos | Minecraft (Java Edition): cada bloque, mob e ítem es un objeto |
| Sistemas bancarios y ERPs | Cuentas, clientes, transferencias, facturas |

## ¿Cómo funciona?

Pasar de un enunciado real a código con objetos sigue siempre los mismos pasos. Usemos una veterinaria como ejemplo.

**Paso 1 — Identificá los objetos.** Buscá los sustantivos importantes del problema: mascota, turno, veterinario.

**Paso 2 — Definí la clase (el molde).** Para cada concepto, preguntate qué *datos* tiene (atributos) y qué *acciones* hace (métodos):

```java
class Mascota {
    String nombre;   // atributo: dato que cada objeto guarda
    String especie;

    void vacunar() { // método: acción que cada objeto ejecuta
        System.out.println(nombre + " fue vacunada.");
    }
}
```

**Paso 3 — Creá instancias con `new`.**

```java
Mascota luna = new Mascota();
luna.nombre = "Luna";

Mascota rocky = new Mascota();
rocky.nombre = "Rocky";
```

**Paso 4 — Hacelas colaborar.** Los objetos se comunican llamando métodos, y pueden referenciarse entre sí (un `Turno` conoce a su `Mascota`).

```java
Turno turno = new Turno();
turno.paciente = luna;
turno.confirmar();
```

Ese flujo —*observar el mundo → definir clases → instanciar → colaborar*— es el corazón de todo diseño orientado a objetos.

### Los 4 pilares (vista panorámica)

Estos son los conceptos que sostienen toda la POO. Acá solo los nombramos para que sepas que existen; cada uno tiene su propio módulo más adelante.

- **Encapsulamiento:** esconder los detalles internos de un objeto y controlar quién puede modificar su estado, para que nadie lo "rompa" desde afuera. *(Se profundiza en el módulo 05.)*
- **Herencia:** crear clases nuevas a partir de clases existentes, reutilizando atributos y comportamiento (`Perro es un Animal`). *(Se profundiza en el módulo 06.)*
- **Polimorfismo:** tratar objetos distintos de manera uniforme y que cada uno responda "a su manera" al mismo mensaje. *(Se profundiza en el módulo 07.)*
- **Abstracción:** quedarse solo con los detalles relevantes del mundo real y descartar el resto; es la habilidad de decidir *qué* modelar. Se materializa con clases abstractas e interfaces. *(Se profundiza en los módulos 08 y 09.)*

### Recap de la ruta del curso

Este módulo es la puerta de entrada de la **Parte I — Fundamentos**. El mapa completo con los 22 módulos está en el [README del curso](../README.md). Lo que viene:

1. Módulo 02 — clases y objetos en detalle (estado, memoria, referencias).
2. Módulo 03 — métodos a fondo.
3. Módulo 04 — constructores.
4. Módulo 05 — encapsulamiento (el primer pilar).
5. Módulos 06 a 09 — herencia, polimorfismo, abstracción e interfaces (Parte II).

## ¿Dónde se usa?

Casos reales donde este modo de pensar está detrás del sistema:

| Dominio | Objetos típicos |
|---------|-----------------|
| E-commerce | `Producto`, `Carrito`, `Pedido`, `Cliente` |
| Banca online | `CuentaBancaria`, `Transferencia`, `Tarjeta` |
| App de turnos médicos | `Paciente`, `Medico`, `Turno` |
| Streaming musical | `Cancion`, `Playlist`, `Usuario` |
| Videojuegos | `Personaje`, `Enemigo`, `Inventario` |

Fijate el patrón: en todos los casos hay *cosas* con datos propios y acciones propias. Cuando el dominio se explica así, la POO encaja naturalmente.

## ¿Cuándo usarlo y cuándo NO?

| Usalo cuando... | No hace falta cuando... |
|-----------------|--------------------------|
| El problema tiene varias "entidades" con estado propio (cuentas, productos, turnos). | Es un script corto y lineal (leer algo, calcular, mostrar). |
| El mismo concepto aparece muchas veces (muchos productos, muchos clientes). | Es un cálculo matemático aislado: una función alcanza. |
| El programa va a crecer o cambiar con el tiempo. | Es una transformación simple de datos (recorrer una lista y filtrar). |
| Trabajás con otras personas y necesitás límites claros. | Estás prototipando una idea desechable. |

**Alternativas:** para scripts chicos alcanza el estilo estructurado; para pipelines de datos suele rendir más el estilo funcional. Y un matiz importante: Java te obliga a escribir *todo* dentro de alguna clase (es una exigencia del lenguaje), pero eso no significa que todo deba ser un "objeto rico": las utilidades simples pueden ser métodos `static` (lo vemos en el módulo 03).

## Ejemplo práctico

Corré los tres ejemplos desde la carpeta `ejemplos/`:

```bash
cd OOP/01-fundamentos-poo/ejemplos

java Ejemplo1ProceduralVsObjetos.java   # el mismo problema: estructurado vs POO
java Ejemplo2MiPrimeraClase.java        # tu primer molde y tus primeros objetos
java Ejemplo3DelMundoAlCodigo.java      # de una escena real (veterinaria) al código
```

Qué observar:

1. **Ejemplo 1** resuelve el control de stock dos veces. Fijate cuánto código habría que duplicar en la versión estructurada para agregar un segundo producto, y cuánto cuesta en la versión con objetos (una línea).
2. **Ejemplo 2** muestra el ciclo completo: definir clase → crear instancias → cambiar su estado → usar sus métodos. Dos personas, un mismo molde.
3. **Ejemplo 3** recorre los pasos del diseño: leer una escena real, elegir objetos, atributos y métodos, y hacer que colaboren.

Consejo: rompélos. Cambiá valores, agregá atributos nuevos, borrá líneas y mirá qué error da. Así es como se aprende de verdad.

## Buenas prácticas

- **Nombrá las clases con sustantivos del dominio** en PascalCase: `CuentaBancaria`, `TurnoVeterinaria`.
- **Nombrá los métodos con verbos** en camelCase: `vacunar()`, `confirmarTurno()`.
- **Una responsabilidad por clase.** Si no sabés qué nombre ponerle, probablemente haga demasiado.
- **Diseñá desde el mundo real hacia el código**, nunca al revés: primero la historia, después las clases.
- **Iterá el diseño.** La primera versión de tus clases casi nunca es la definitiva, y está bien.
- Comentá el *porqué* de las decisiones, no lo que ya dice el código.

## Errores comunes

1. **Confundir la clase con el objeto.** La clase es el plano; el objeto es cada casa construida. Modificar el plano no cambia las casas ya hechas. Si tu programa "no hace nada", preguntate: ¿definí la clase pero nunca creé instancias?
2. **Querer aplicar herencia desde el día uno.** Todavía no la necesitás: primero dominá clases, objetos, métodos y estado (módulos 01 a 05). Herencia prematura = diseños frágiles.
3. **La clase "todo en uno"** que maneja clientes, productos, facturas y reporte. Dividí por concepto: cada entidad merece su propia clase.
4. **Copiar y pegar en vez de crear un molde.** Si escribís dos veces casi el mismo bloque para dos "cosas" parecidas, lo que querés es una clase con dos instancias.

## Resumen express

- POO = organizar el programa como **objetos** que combinan **estado** (atributos) y **comportamiento** (métodos).
- **Clase** = molde / plano. **Objeto** = instancia concreta creada a partir del molde.
- Código estructurado: datos y funciones separados. POO: datos y funciones **juntos** por concepto.
- Con `new` fabricás instancias; cada instancia tiene estado independiente.
- Los 4 pilares: encapsulamiento (05), herencia (06), polimorfismo (07), abstracción (08-09).
- Método de diseño: sustantivos → clases y atributos; verbos → métodos.

## Ejercicios

Los encontrás en `ejercicios/`, cada uno con su enunciado, requisitos y pistas dentro del archivo. Las soluciones comentadas están en `soluciones/` (miralas solo después de intentar).

1. **Detective de objetos** — leé la descripción de un kiosco e identificá clases, atributos y métodos.
2. **Tu primera clase** — definí una clase `Libro` con 2 campos y 1 método, y usala desde el `main`.
3. **Un molde, muchos objetos** — modelá productos de un almacén y comprobá que cada instancia vive su vida propia.
4. **Refactor mental** — analizá un fragmento de código estructurado "espagueti" y explicá cómo lo mejorarías con objetos.
5. **Diseñá tu propio mundo** — mini desafío libre: elegí un dominio, diseñá sus clases y contá una historia con ellas.

## Para profundizar

- Oracle Java Tutorials — *Object-Oriented Programming Concepts*: <https://docs.oracle.com/javase/tutorial/java/concepts/>
- Oracle Java Tutorials — *Classes and Objects*: <https://docs.oracle.com/javase/tutorial/java/javaOO/index.html>
- dev.java — Learn Java, sección OOP: <https://dev.java/learn/oop/>
- Mapa completo del curso y próximos módulos: [../README.md](../README.md)

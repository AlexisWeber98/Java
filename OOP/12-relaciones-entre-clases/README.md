# 12 · Relaciones entre clases

Las clases no viven solas: se conocen, se contienen, se usan. Un `Pedido` tiene líneas, un `Equipo` tiene jugadores, un `Cliente` conoce a su asesor y un `Cajero` *usa* una impresora sin guardarla. Todo eso son **relaciones**, y modelarlas bien es lo que separa un diseño sólido de una bola de nieve de objetos huérfanos.

## Quick path

1. Leé la tabla de decisión de "¿Cuándo usar cada una?".
2. Corré los ejemplos en `ejemplos/` con `java ejemplos/Nombre.java`.
3. Hacé los 5 ejercicios del final.

---

## ¿Qué son las relaciones?

Cuando dos objetos colaboran, entre ellos existe una relación con **dirección** (quién conoce a quién) e **intensidad** (qué pasa si uno deja de existir). Hay cuatro formas principales de conectarlos:

| Relación | Idea central | Fuerza del vínculo |
|---|---|---|
| **Dependencia** | A *usa* a B de forma transitoria (parámetro, variable local) | La más débil |
| **Asociación** | A *conoce* a B a largo plazo (campo) | Vínculo duradero, vidas independientes |
| **Agregación** | A *tiene* B, pero B sobrevive si A desaparece | HAS-A flexible |
| **Composición** | A *posee* B y B muere con A | HAS-A exclusiva, la más fuerte |

*(HAS-A es jerga clásica de diseño que significa "tiene un": un equipo *tiene un* grupo de jugadores, una casa *tiene* habitaciones.)*

## ¿Por qué importa modelarlas?

Elegir mal la relación = elegir mal el **ciclo de vida**. Y el ciclo de vida mal resuelto trae problemas reales:

- **Fugas de memoria**: guardás objetos que nadie más necesita porque alguien quedó "colgado" de ellos.
- **Objetos huérfanos o zombis**: partes que siguen vivas cuando su todo ya no existe (o al revés).
- **Diseño frágil**: si cada clase conoce a todas, cambiar una rompe diez.

La calidad de un diseño orientado a objetos vive acá, en *cómo* se conectan las piezas — no solo en cómo están escritas por dentro.

## ¿Quién lo usa?

- **Todo modelo de dominio**: facturas, reservas, catálogos, torneos. Si hay entidades, hay relaciones.
- **UML**: los diagramas de clase dibujan exactamente estas cuatro relaciones (flechas y rombos).
- **JPA / Hibernate** (módulo 21, spoiler): anotaciones como `@ManyToOne`, `@OneToMany`, `@OneToOne` son literalmente estas relaciones, traducidas a tablas. Lo que aprendés acá después lo vas a escribir como anotación.

## ¿Cómo funciona?

De la más débil a la más fuerte:

### 1. Dependencia — "la uso ahora y me olvido"

Una clase depende de otra cuando la recibe como parámetro o la crea localmente para hacer algo puntual. No guarda ninguna referencia: si mañana no la necesitás más, no queda rastro.

```java
class ReporteService {
    void exportar(Pedido pedido) {   // usa al Pedido, no lo guarda
        System.out.println("Exportando " + pedido.numero());
    }
}
```

Es el vínculo más débil: en UML se dibuja con **flecha punteada**.

### 2. Asociación — "nos conocemos hace tiempo"

Dos objetos se conocen de forma duradera mediante un campo, pero cada uno tiene su propio ciclo de vida. El clásico: `Cliente` conoce a su `Asesor`. Si el asesor cambia de trabajo, el cliente sigue existiendo; si el cliente se va, el asesor tampoco muere.

```java
class Cliente {
    private final String nombre;
    private Asesor asesor;           // asociación: referencia duradera

    void asignarAsesor(Asesor nuevo) { this.asesor = nuevo; }
}
```

En UML: **flecha simple** (línea continua). Por defecto buscá asociaciones **unidireccionales**: que el cliente conozca al asesor, no necesariamente al revés.

### 3. Agregación — "tengo una parte que puede vivir sola"

Un todo contiene partes, pero las partes **existen antes y sobreviven después** del todo. Un `Equipo` tiene `Jugador`es: los jugadores llegan de otro equipo o quedan disponibles si el equipo se disuelve. Borrar el equipo no borra a los jugadores.

```java
class Equipo {
    private final String nombre;
    private final List<Jugador> jugadores = new ArrayList<>();

    void fichar(Jugador jugador) { jugadores.add(jugador); } // llega de afuera
    void disolver() { jugadores.clear(); }                   // ellos siguen vivos
}
```

En UML: **rombo vacío** (contorno) del lado del todo. Clave: la parte **no se crea adentro**, se recibe.

### 4. Composición — "mis partes mueren conmigo"

El todo crea sus partes internamente y jamás las comparte. Si el todo desaparece, las partes desaparecen con él. Una `Casa` tiene `Habitacion`es creadas dentro de la propia construcción; un `Pedido` crea sus `LineaPedido` al agregarse. No tiene sentido una habitación flotando sin casa ni una línea sin pedido.

```java
class Casa {
    private final List<Habitacion> habitaciones = new ArrayList<>();

    Casa() {                                   // la casa CREA sus habitaciones
        habitaciones.add(new Habitacion("cocina", 12));
        habitaciones.add(new Habitacion("dormitorio", 18));
    }
    double superficieTotal() { /* suma interna */ }
}
```

En UML: **rombo lleno** del lado del todo. Regla de oro: la parte se crea adentro y nunca sale.

### Multiplicidad

Toda relación además dice **cuántos**: `1 a 1` (una casa, un titular), `1 a N` (un pedido, varias líneas), `N a M` (estudiantes y cursos, por ambos lados). En código, la multiplicidad la expresás con campos simples (`Asesor`) o colecciones (`List<Jugador>`).

### Mini cheat sheet UML

| Relación | Símbolo UML | En Java |
|---|---|---|
| Dependencia | `- - - -▶` punteada | Parámetro / variable local |
| Asociación | `—————▶` continua | Campo |
| Agregación | `◇—————` rombo vacío en el todo | Colección de objetos recibidos de afuera |
| Composición | `◆—————` rombo lleno en el todo | Parte creada dentro, nunca compartida |

## ¿Dónde se usa?

- **Backend y dominio**: servicios que dependen de repositorios (dependencia), entidades que se referencian (asociación), agregados con partes (composición — de ahí viene el nombre "aggregate" en DDD, *Domain-Driven Design*, un enfoque para diseñar sistemas hablando el idioma del negocio).
- **UI y componentes**: una ventana agrega paneles reutilizables pero compone sus subcomponentes propios.
- **Modelado previo al código**: diagramas UML en diseño, y luego JPA/Hibernate en persistencia.

## ¿Cuándo usar cada una?

Preguntate, en orden:

| Pregunta | Si es... | Relación |
|---|---|---|
| ¿La uso solo dentro de un método y la descarto? | sí | **Dependencia** |
| ¿Solo necesito conocerla a largo plazo, sin "tenerla"? | sí | **Asociación** |
| ¿La tengo, pero llegó de afuera y sigue viva si yo muero? | sí | **Agregación** |
| ¿La tengo, nació adentro mío y muere conmigo? | sí | **Composición** |

Test rápido para HAS-A: *¿esta parte necesita al todo para existir?* Sí → composición. No → agregación. *¿Quién crea la parte?* El todo → composición. Alguien externo → agregación.

## Ejemplo práctico

Mirá `ejemplos/`:

- `DependenciaYAsociacion.java` — contraste entre usar un objeto transitoriamente y conocerlo a largo plazo.
- `AgregacionEquipoJugador.java` — el equipo se disuelve, los jugadores siguen vivos.
- `ComposicionCasaHabitacion.java` — la casa crea sus habitaciones; nadie más las toca.

Corré cualquiera con:

```bash
java ejemplos/AgregacionEquipoJugador.java
```

## Buenas prácticas

- **Preferí composición sobre herencia** cuando querés reutilizar comportamiento: componer piezas es más flexible que heredar de una base rígida (el famoso *"favor composition over inheritance"* vuelve acá, con otro ángulo).
- **Direcciones claras**: empezá con relaciones unidireccionales. Agregá la vuelta solo si realmente el otro lado necesita conocer.
- **Inicializá internamente las partes compuestas**: la composición pierde sentido si las partes llegan de afuera.
- **Dependencias primero**: si algo puede ser parámetro en vez de campo, hacelo parámetro. Menos estado = menos acoplamiento.

## Errores comunes

- **Exponer la lista interna compuesta**: devolver `habitaciones` directamente rompe la composición — cualquiera puede meter o sacar partes. Devolvé copias (`List.copyOf(...)`) u operaciones específicas.
- **Todo bidireccional**: si `A` conoce a `B` y `B` conoce a `A` "por las dudas", generás caos de actualización (¿quién sincroniza?) y ciclos raros. Bidireccional solo con necesidad real.
- **Confundir agregación con asociación en code review**: ambas usan campos, pero la agregación implica un rol "todo-parte". Preguntate si semánticamente el objeto *pertenece a* una colección mayor.
- **Composición por accidente**: crear la parte adentro pero luego pasarla a otros objetos = ya no es composición. O la encapsulás de verdad, o era agregación.

## Resumen express

| Relación | Ciclo de vida de la parte | Se crea adentro del todo | UML |
|---|---|---|---|
| Dependencia | Transitoria | — | Flecha punteada |
| Asociación | Independiente | No | Flecha continua |
| Agregación | Sobrevive al todo | No | Rombo vacío |
| Composición | Muere con el todo | Sí | Rombo lleno |

## Ejercicios

1. **Asociación simple** — Modelo un `Cliente` que conoce a su `Asesor` (nombre y legajo). Permito cambiar de asesor y muestro que ambos existen independientemente.
2. **Agregación universidad–profesores** — Una `Universidad` tiene profesores que trabajan en varias universidades. Al cerrar una universidad, los profesores siguen contratados en la otra.
3. **Composición pedido–líneas** — Un `Pedido` crea sus `LineaPedido` internamente con producto y cantidad. Calculo total; pruebo que no haya forma de agregar una línea desde afuera.
4. **Dependencia por parámetro** — Un `ValidadorDePedidos` que recibe pedidos por parámetro y valida reglas, sin guardar ninguno. Muestro que no queda acoplado a ningún pedido.
5. **Desafío biblioteca** — Modelo una `Biblioteca` usando las cuatro relaciones: dependencia con un servicio de impresión, asociación con el bibliotecario, agregación con socios y composición con estantes/salas.

## Para profundizar

- Diagramas UML de clases: flechas, multiplicidades y navegabilidad.
- DDD: qué es un "aggregate" y por qué la composición define sus fronteras.
- Módulo 21 (persistencia): cómo `@OneToMany` / `@ManyToOne` traducen estas relaciones a tablas.

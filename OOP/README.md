# 🎓 Curso de Programación Orientada a Objetos con Java

Curso completo, progresivo y 100% práctico. Cada tema responde las preguntas clave: **qué es, por qué existe, quién lo usa, cómo funciona, dónde se usa, cuándo usarlo y cuándo NO**, con ejemplos ejecutables, **5 ejercicios por tema** y sus soluciones.

> 🧑‍🏫 **Filosofía didáctica del curso:** acá se explica como te lo contaría un profe al lado tuyo. Cada concepto arranca con una **analogía de la vida cotidiana**, muestra un **caso de uso real** (dónde te lo vas a encontrar trabajando) y recién después baja al detalle técnico. Si un término suena raro, va a estar explicado en palabras simples **antes** de usarlo. Preferimos ser redundantes y bajar todo a tierra antes que dejar a alguien atrás: lo técnico llega cuando ya entendiste la idea.

---

## 🗺️ Mapa del curso

### Parte I — Fundamentos
| # | Módulo | Qué vas a aprender |
|---|--------|--------------------|
| 01 | [fundamentos-poo](01-fundamentos-poo/) | Qué es la POO, objetos vs código estructurado, los 4 pilares en vista panorámica |
| 02 | [clases-y-objetos](02-clases-y-objetos/) | Anatomía de una clase, estado y comportamiento, `new`, referencias y memoria |
| 03 | [metodos](03-metodos/) | Firma, parámetros, retorno, sobrecarga, varargs, `static` vs instancia |
| 04 | [constructores](04-constructores/) | Constructores, sobrecarga, `this()`, orden de inicialización |
| 05 | [encapsulamiento](05-encapsulamiento/) | Modificadores de acceso, getters/setters, invariantes, validación |

### Parte II — Los pilares en profundidad
| # | Módulo | Qué vas a aprender |
|---|--------|--------------------|
| 06 | [herencia](06-herencia/) | `extends`, `super`, redefinición, `@Override`, la clase `Object` |
| 07 | [polimorfismo](07-polimorfismo/) | Despacho dinámico, upcasting, `instanceof`, sobrescritura vs sobrecarga |
| 08 | [clases-abstractas](08-clases-abstractas/) | Clases y métodos abstractos, plantillas comunes, cuándo usarlas |
| 09 | [interfaces](09-interfaces/) | Contratos, `implements`, herencia múltiple de tipo, métodos `default` |

### Parte III — El lenguaje en detalle
| # | Módulo | Qué vas a aprender |
|---|--------|--------------------|
| 10 | [enums](10-enums/) | Enums simples y avanzados, campos, constructores, `switch` sobre enums |
| 11 | [records](11-records/) | Records, constructor compacto, cuándo sí y cuándo no, sealed classes |
| 12 | [relaciones-entre-clases](12-relaciones-entre-clases/) | Asociación, agregación, composición, dependencia |
| 13 | [conversiones-de-tipos](13-conversiones-de-tipos/) | Casting primitivos y objetos, autoboxing, `String ↔ número`, parsing seguro |
| 14 | [excepciones](14-excepciones/) | `try/catch/finally`, checked vs unchecked, excepciones propias, try-with-resources |

### Parte IV — Colecciones y genéricos
| # | Módulo | Qué vas a aprender |
|---|--------|--------------------|
| 15 | [colecciones-listas](15-colecciones-listas/) | Framework Collections, `ArrayList`, `LinkedList`, iteración y ordenamiento |
| 16 | [mapas-pilas-colas](16-mapas-pilas-colas/) | `HashMap` (+ `equals`/`hashCode`), `Stack`, colas, `Deque` |
| 17 | [generics](17-generics/) | Tipos genéricos, clases y métodos genéricos, bounded types, wildcards |

### Parte V — Temas avanzados y persistencia
| # | Módulo | Qué vas a aprender |
|---|--------|--------------------|
| 18 | [anotaciones](18-anotaciones/) | Qué son las annotations, built-ins, retención, cómo las usan los frameworks |
| 19 | [hilos-y-concurrencia](19-hilos-y-concurrencia/) | Threads, ciclo de vida, condiciones de carrera, `synchronized`, ExecutorService |
| 20 | [jdbc-base-de-datos](20-jdbc-base-de-datos/) | Conexión a BD, `PreparedStatement`, ResultSet, patrón DAO |
| 21 | [jpa-hibernate](21-jpa-hibernate/) | ORM, entidades, anotaciones JPA, `EntityManager`, transacciones |

### Parte VI — Arquitectura profesional
| # | Módulo | Qué vas a aprender |
|---|--------|--------------------|
| 22 | [arquitectura-capas-crud](22-arquitectura-capas-crud/) | Capas Controller → Service → Repository, DTOs, flujo CRUD completo |
| 23 | [arquitectura-hexagonal](23-arquitectura-hexagonal/) | Dominio al centro, puertos y adaptadores, la regla de dependencias, cuándo vale la pena |

> 🏆 **Proyecto integrador final**: [`proyectos/proyecto-02-inventario-jpa`](proyectos/proyecto-02-inventario-jpa/) — CRUD por capas con JPA + base de datos real.

---

## 🧭 Cómo estudiar cada módulo

Seguí siempre el mismo ciclo:

1. **Leé** el `README.md` del módulo sin apuro. Es la teoría.
2. **Corré** los archivos de `ejemplos/`. Cambialos, rompélos, volvé a correrlos. Así se aprende.
3. **Hacé los 5 ejercicios** de `ejercicios/` SIN mirar las soluciones. En serio. El esfuerzo de intentar es lo que fija el conocimiento.
4. **Compará** tu solución con `soluciones/`. No es un examen: si tu versión funciona y es clara, también es válida.
5. Si algo no cerró, volvé al punto 1 antes de seguir al próximo módulo.

## ✅ Requisitos

- **JDK 17 o superior** (recomendado 21+; este curso fue verificado con JDK 25).
- Un editor cualquiera (IntelliJ IDEA Community, VS Code, o simplemente terminal).
- Nada más: hasta el módulo 20 todo usa **solo la librería estándar de Java**.

## ▶️ Cómo ejecutar los archivos

Cada archivo está pensado para correrse de forma independiente, sin compilar nada más:

```bash
cd OOP/02-clases-y-objetos/ejemplos
java EstadoVsComportamiento.java
```

Desde Java 11+, `java Archivo.java` compila y ejecuta en un paso. Los archivos no usan `package` a propósito: cero fricción.

## 📁 Estructura estándar de cada módulo

```
NN-nombre-del-modulo/
├── README.md        ← teoría completa (leelo primero)
├── ejemplos/        ← 2 a 4 programas cortos y corribles
├── ejercicios/      ← Ejercicio1.java ... Ejercicio5.java (con enunciado y TODOs)
└── soluciones/      ← las mismas consignas, resueltas y comentadas
```

Dentro de cada ejercicio, el enunciado está como comentario al inicio del archivo y el código base tiene marcas `// TODO:` que te dicen exactamente qué implementar.

## 💡 Regla de oro

No avances de módulo si no pudiste hacer al menos los ejercicios 1 a 3 solo/a. La POO se aprende **haciendo**, no leyendo. Los proyectos integradores son la meta: ahí todo lo anterior se conecta.

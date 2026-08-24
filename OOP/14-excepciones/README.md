# Módulo 14 — Excepciones

Cuando algo se rompe, Java no se calla: **lanza una excepción**. Ese objeto es el grito de alarma del programa, y en este módulo vas a aprender a escucharlo, atraparlo y responder como profesional.

## ¿Qué es una excepción?

Una excepción es un **objeto** que describe la falla: qué pasó, con qué mensaje y en qué punto del código. Cuando ocurre un error, se *lanza* (`throw`) ese objeto y este **viaja hacia arriba por la pila de llamadas** (la fila de métodos que se fueron llamando entre sí y siguen abiertos en este momento), método a método, buscando a alguien que quiera ocuparse de él.

Pensá en una **alarma de incendio**: suena en un piso (el método donde falló todo) y va subiendo piso por piso hasta que alguien la atiende (un `catch`). Si nadie la atiende... el edificio entero se desaloja: el programa termina.

## ¿Por qué existen?

Antes de las excepciones, los programas avisaban errores devolviendo valores especiales: `-1`, `null`, `0`. ¿El problema? Tres graves:

| Con códigos de error (`return -1`)          | Con excepciones                                    |
|---------------------------------------------|----------------------------------------------------|
| Se pueden **ignorar** sin que el compilador avise | Es **imposible ignorarlas** si son checked: el compilador te obliga |
| No llevan contexto: ¿por qué `-1`?           | Llevan **mensaje, causa y stack trace** completos   |
| Mezclan camino feliz y manejo de errores     | **Separan** el flujo normal del manejo de fallos    |

*(Stack trace: el reporte que imprime Java cuando algo explota, con la línea exacta del error y el camino completo de llamadas que llevó hasta ella.)*

La excepción convierte "algo salió mal" en "acá está exactamente qué salió mal, dónde y por qué".

## ¿Quién lo usa?

Todo el ecosistema Java. `Integer.parseInt` lanza `NumberFormatException` si le pasás `"hola"`. La I/O (entrada/salida: leer archivos, hablar por red) lanza `IOException` si el archivo no existe. Spring, Hibernate, las librerías de red: todas comunican fallas así. Vos también lo vas a hacer para tus reglas de negocio, como veremos con `SaldoInsuficienteException`.

## La jerarquía: Throwable manda

Toda excepción desciende de `java.lang.Throwable`:

```
Throwable
├── Error                  ← problemas de la JVM. NO los atrapes
│      └── OutOfMemoryError, StackOverflowError
└── Exception              ← condiciones que SÍ podés manejar
       ├── IOException, SQLException        ← CHECKED (obligatorias)
       └── RuntimeException                 ← UNCHECKED
              ├── NullPointerException
              ├── IllegalArgumentException
              └── ArithmeticException
```

- **Error**: fallas de la máquina virtual (sin memoria, stack overflow). No hay nada que puedas hacer razonablemente; dejar que el programa muera es lo correcto.
- **Exception checked** (ej. `IOException`): el compilador **te obliga** a declararla o atraparla. Son fallas externas que debés anticipar: archivo faltante, red caída.
- **RuntimeException unchecked** (ej. `NullPointerException`): bugs de programación. El compilador no exige nada porque la solución es corregir el código, no atrapar más.

### Tabla de decisión: ¿qué lanzo?

| Situación | Lanzá | Ejemplo |
|-----------|-------|---------|
| El que llama violó una precondición (argumento inválido) | `IllegalArgumentException` | edad negativa |
| Un objeto llegó en estado nulo donde no correspondía | `NullPointerException` | dependencia no inicializada |
| Regla de negocio incumplida, recuperable | excepción checked propia | `SaldoInsuficienteException` |
| Falla externa esperable (I/O, red) | checked estándar o propia | `IOException` |

## ¿Cómo funciona?

### Anatomía de try / catch

```java
try {
    // código vigilado: acá puede explotar algo
    int resultado = dividir(10, 0);
} catch (ArithmeticException e) {
    // plan B: solo entra si se lanzó esa excepción
    System.out.println("No se puede dividir por cero: " + e.getMessage());
}
// el flujo sigue normalmente desde acá
```

### Múltiples catch: el orden importa

Podés poner varios `catch`, pero van de **lo más específico a lo más general**. Si ponés la superclase primero, los catch siguientes son inalcanzables y **no compila**:

```java
try {
    leerArchivo("datos.txt");
} catch (FileNotFoundException e) {   // subclase primero ✔
    System.out.println("No existe el archivo");
} catch (IOException e) {             // superclase después ✔
    System.out.println("Problema de lectura: " + e.getMessage());
}
// catch (IOException e) arriba de FileNotFoundException → ERROR de compilación
```

### finally: siempre corre

El bloque `finally` se ejecuta **siempre**, haya o no excepción, incluso si hay `return`. Ideal para liberar recursos. Nota moderna: para cerrar recursos preferí **try-with-resources** (más abajo); `finally` queda para limpieza que no sea auto-cerrable.

```java
try {
    procesar();
} finally {
    System.out.println("Esto corre pase lo que pase");
}
```

### throw vs throws: no los confundas

| Palabra | Qué hace | Dónde vive |
|---------|----------|------------|
| `throw` | **Lanza un objeto** concreto, ahora mismo | Dentro del cuerpo del método |
| `throws` | **Declara la posibilidad** de lanzar | En la firma, después de los paréntesis |

```java
public void retirar(double monto) throws SaldoInsuficienteException { // declara
    if (monto <= 0) {
        throw new IllegalArgumentException("Monto inválido");         // lanza
    }
}
```

En la firma podés declarar varios tipos separados por coma: `throws IOException, SaldoInsuficienteException`.

### Excepciones propias

Para reglas de negocio, creá tu propia clase:

```java
public class SaldoInsuficienteException extends Exception {
    private final double montoFaltante;

    public SaldoInsuficienteException(String mensaje, double montoFaltante) {
        super(mensaje);
        this.montoFaltante = montoFaltante;
    }

    public double getMontoFaltante() { return montoFaltante; }
}
```

Regla simple: `extends Exception` (checked) para reglas de negocio que el que llama debe manejar; `extends RuntimeException` para indicar bug o uso incorrecto de una API. Y sumarle **datos propios** (como `montoFaltante`) convierte el mensaje genérico en información accionable.

### Encadenamiento: traducir sin perder la causa

A veces querés convertir una excepción técnica en una de dominio, **conservando el origen**:

```java
try {
    guardarEnBaseDeDatos(cliente);
} catch (SQLException e) {
    throw new PersistenciaFallidaException("No se pudo guardar el cliente", e);
}
// después: e.getCause() devuelve el SQLException original
```

Ese segundo parámetro `e` encadena la causa. Si la perdés re-lanzando sin causa, quemás el mapa del tesoro: nadie va a poder diagnosticar el problema real.

### try-with-resources: cierre garantizado

Si un recurso implementa `AutoCloseable`, declaralo entre paréntesis del `try`: Java lo cierra **solo**, aun si vuela una excepción.

```java
class RecursoSimulado implements AutoCloseable {
    public void usar() { System.out.println("Usando el recurso"); }
    @Override
    public void close() { System.out.println("Recurso cerrado"); }
}

try (RecursoSimulado recurso = new RecursoSimulado()) {
    recurso.usar();
    // aunque acá haya un throw, close() se ejecuta igual
}
```

## Anti-patrones

- **Tragar la excepción**: `catch (Exception e) {}` vacío. El error desaparece silenciosamente y el bug vive para siempre.
- **Atrapar `Exception` a lo broad**: mata la precisión; atrapá lo específico.
- **Excepciones como control de flujo**: usar try/catch en vez de un `if` para lógica normal es caro y confuso.
- **Log-and-rethrow**: registrar la excepción Y relanzarla duplica logs y ensucia el diagnóstico. Elegí una.

## ¿Dónde se usa?

En todos los bordes de tu sistema: validación de entrada, acceso a archivos y red, operaciones de base de datos, parsing de datos externos, y reglas de negocio (saldo insuficiente, cupo agotado, usuario duplicado).

## ¿Cuándo usarlo y cuándo NO?

Las excepciones son para **condiciones excepcionales**, no para branching normal. Que el usuario tipee mal la opción del menú y tenga que volver a elegir **no es excepcional**: es el flujo esperado, resolvélo con un `while` y un `if`. Pero que el archivo de configuración no exista sí lo es: no tenés forma de continuar sanamente.

Preguntate: *"¿esto debería pasarle a un usuario usando bien el sistema?"* Si la respuesta es no → excepción. Si es sí → lógica normal.

## Ejemplo práctico

Mirá `ejemplos/PrimerTryCatch.java` (primeros pasos con try/catch), `ejemplos/ExcepcionPropiaDeNegocio.java` (regla de negocio con datos propias) y `ejemplos/RecursosYTryWithResources.java` (cierre garantizado). Cada uno corre standalone con `java NombreDelArchivo.java`.

## Buenas prácticas

- **Fail fast** (fallar temprano): validá temprano y lanzá apenas detectás el problema.
- **Mensajes con datos**: "Saldo insuficiente" ayuda poco; "Faltan $250,50 para cubrir el retiro de $1500" ayuda mucho.
- **Preservá la causa**: usá el constructor de encadenamiento `new Ex(msg, causa)`.
- **Catch específicos**: atrapá el tipo más preciso posible.
- **Cerrá recursos automáticamente**: try-with-resources siempre que pueda existir un `close()`.

## Errores comunes

- `catch` vacío: esconde bugs para siempre. Si no sabés qué hacer, al menos registrá el error (loguealo) antes de relanzarlo.
- Atrapar `Throwable` o `Error`: estás secuestrando fallas de la JVM que debés dejar morir.
- Re-lanzar sin causa: rompés el stack trace original y el debugging se vuelve arqueología.
- Validar argumentos con excepciones cuando el diseño (tipos, constructores, `Optional`) podía evitar la situación.

## Resumen express

| Concepto | Clave |
|----------|-------|
| Excepción | Objeto que describe una falla y viaja por la pila |
| `throw` vs `throws` | Uno lanza el objeto; otro declara la posibilidad en la firma |
| Checked vs unchecked | Obligatorio manejar (negocio/externas) vs bug de programación |
| Orden de catch | Subclase antes que superclase, o no compila |
| `finally` | Corre siempre; para recursos usá try-with-resources |
| Encadenamiento | `new MiExcepcion("contexto", causa)` + `getCause()` |
| Custom | `extends Exception` negocio · `extends RuntimeException` bugs |

## Ejercicios

1. **División segura**: pedí dos números, manejá división por cero e input no numérico sin que muera el programa.
2. **El orden de los catch importa**: escribí un catch que no compila por orden incorrecto y corregilo.
3. **Tu primera excepción de negocio**: creá `EdadInvalidaException` y usala en un validador.
4. **Traducir y relanzar con causa**: convertí un `NumberFormatException` en una excepción propia preservando la causa.
5. **Desafío cajero con recurso auto-cerrable**: combiná `SaldoInsuficienteException` con try-with-resources sobre un registro de operaciones.

## Para profundizar

- Documentación oficial: [Lesson: Exceptions](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- [Try-with-resources](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)
- Effective Java (Joshua Bloch), ítems sobre excepciones: usar checked para condiciones recuperables, unchecked para errores de programación.

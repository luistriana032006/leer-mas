# Preguntas sobre el proyecto

## Pregunta 1: ¿Por qué es necesario añadir ModelMapperConfig.java y qué contiene ese archivo?

### Respuesta:

ModelMapper es una **librería externa** (no es parte de Spring). Spring no sabe automáticamente cómo crear un objeto `ModelMapper` para inyectarlo en tus clases.

### La solución:
Crear un archivo de **configuración** que le diga a Spring:
1. "Crea un objeto ModelMapper"
2. "Configúralo de esta manera específica"
3. "Guárdalo como un Bean para que pueda ser inyectado"

---

### ¿Qué contiene ModelMapperConfig.java?

```java
package com.devsenior.luistriana.leer_mas.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // = Le dice a Spring: "Esta clase contiene configuraciones"
public class ModelMapperConfig {

    @Bean  // = Le dice a Spring: "Crea este objeto y guárdalo en el contenedor"
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // ™ CONFIGURACIÓN CLAVE PARA PATCH
        mapper.getConfiguration()
            .setSkipNullEnabled(true)  //  Ignora campos null (clave para PATCH)
            .setMatchingStrategy(MatchingStrategies.STRICT); //  Solo mapea campos con nombres exactos

        return mapper;
    }
}
```

---

### Desglose línea por línea:

#### 1. `@Configuration`
```java
@Configuration
public class ModelMapperConfig {
```
**¿Qué hace?**
- Le dice a Spring: "Esta clase es especial, contiene **beans de configuración**"
- Spring escanea esta clase al arrancar la aplicación

**Analogía:**
Es como decirle a Spring: "Oye, en este archivo tengo recetas para crear objetos que vas a necesitar"

---

#### 2. `@Bean`
```java
@Bean
public ModelMapper modelMapper() {
```
**¿Qué hace?**
- Crea un objeto `ModelMapper`
- Lo **registra en el contenedor de Spring** (Application Context)
- Ahora puede ser **inyectado** en cualquier clase que lo necesite

**Analogía:**
- Sin `@Bean`: "Tengo un objeto ModelMapper en mi cajón, pero nadie más puede usarlo"
- Con `@Bean`: "Pongo el ModelMapper en un estante público donde todos pueden tomarlo"

---

#### 3. `setSkipNullEnabled(true)` P MUY IMPORTANTE
```java
mapper.getConfiguration().setSkipNullEnabled(true)
```
**¿Qué hace?**
- Le dice a ModelMapper: **"NO copies campos que sean null"**
- Esto es **CRÍTICO** para que el PATCH funcione correctamente

**Ejemplo práctico:**

**SIN `setSkipNullEnabled(true)`:**
```java
// Libro existente:
Libro existente = {isbn: "ABC", titulo: "Java", precio: 25.0}

// Actualización parcial (solo cambiar título):
Libro actualizado = {titulo: "Python", precio: null}

modelMapper.map(actualizado, existente);

// Resultado: L MALO
existente = {isbn: "ABC", titulo: "Python", precio: null}
// ¡Se perdió el precio!
```

**CON `setSkipNullEnabled(true)`:**
```java
// Libro existente:
Libro existente = {isbn: "ABC", titulo: "Java", precio: 25.0}

// Actualización parcial:
Libro actualizado = {titulo: "Python", precio: null}

modelMapper.map(actualizado, existente);

// Resultado:  BUENO
existente = {isbn: "ABC", titulo: "Python", precio: 25.0}
// Solo cambió el título, el precio se conservó
```

---

#### 4. `setMatchingStrategy(MatchingStrategies.STRICT)`
```java
.setMatchingStrategy(MatchingStrategies.STRICT)
```
**¿Qué hace?**
- Le dice a ModelMapper: **"Solo mapea campos que tengan exactamente el mismo nombre"**

**Estrategias disponibles:**

| Estrategia | Comportamiento | Ejemplo |
|------------|----------------|---------|
| `LOOSE` | Mapea campos similares | `userAge` ’ `age`  |
| `STANDARD` | Requiere coincidencia razonable | `userAge` ’ `age`   |
| `STRICT` | Solo nombres EXACTOS | `age` ’ `age`  / `userAge` ’ `age` L |

**Por qué usar STRICT:**
- Evita mapeos accidentales
- Más seguro para tu código
- Previene bugs difíciles de detectar

---

### ¿Qué pasaría SIN este archivo?

**Sin ModelMapperConfig.java:**

```java
@Service
public class LibreriaImpl implements Libreria {
    private final ModelMapper modelMapper;

    public LibreriaImpl(ModelMapper modelMapper) {  // L ERROR
        // Spring dice: "No sé qué es ModelMapper, no puedo inyectarlo"
        this.modelMapper = modelMapper;
    }
}
```

**Error que verías:**
```
***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 0 of constructor in LibreriaImpl required a bean of type
'org.modelmapper.ModelMapper' that could not be found.

Action:

Consider defining a bean of type 'org.modelmapper.ModelMapper' in your configuration.
```

---

### Flujo completo de cómo funciona:

```
1. Spring arranca la aplicación
   “
2. Spring encuentra @Configuration en ModelMapperConfig
   “
3. Spring ejecuta el método con @Bean
   “
4. Se crea un ModelMapper configurado con skipNull=true
   “
5. Spring guarda ese ModelMapper en su "contenedor" (Application Context)
   “
6. LibreriaImpl dice: "Necesito un ModelMapper"
   “
7. Spring dice: "¡Tengo uno! Te lo inyecto"
   “
8. LibreriaImpl puede usar modelMapper.map() 
```

---

### ModelMapper funciona SIN base de datos

ModelMapper es una librería que trabaja **puramente en memoria** con objetos Java (POJOs). No tiene ninguna relación con bases de datos.

**¿Qué hace ModelMapper?**

Copia valores de un objeto a otro EN MEMORIA:

```java
// Tienes dos objetos Java en memoria:
Libro objetoA = {titulo: "Python", precio: null, isbn: null};
Libro objetoB = {titulo: "Java", precio: 25.0, isbn: "ABC123"};

// ModelMapper copia campos no-null de A a B:
modelMapper.map(objetoA, objetoB);

// Resultado:
objetoB = {titulo: "Python", precio: 25.0, isbn: "ABC123"};
// Solo cambió el título, el resto se conservó
```

**Todo esto pasa en la RAM de tu computadora**, sin tocar ninguna base de datos.

---

### Resumen:

**¿Por qué necesitamos el archivo?**
- Para que Spring sepa cómo crear un `ModelMapper`
- Para configurarlo correctamente (skipNull para PATCH)
- Para poder inyectarlo en cualquier clase

**¿Qué hace el archivo?**
1. `@Configuration` ’ "Aquí hay configuraciones"
2. `@Bean` ’ "Crea este objeto y guárdalo"
3. `setSkipNullEnabled(true)` ’ "No copies nulls" (clave para PATCH)
4. `setMatchingStrategy(STRICT)` ’ "Solo nombres exactos"

---

## Pregunta 2: Si tengo múltiples implementaciones, ¿por qué no simplemente inyecto ambas en el constructor y uso la que necesite?

### Respuesta:

**¡Excelente pregunta! SÍ se puede hacer eso**, y es perfectamente válido en ciertos casos.

---

## SÍ, puedes inyectar múltiples implementaciones

```java
@RestController
public class LibreriaController {
    private final Libreria libreriaMemoria;
    private final Libreria libreriaDatabase;

    //  Inyectas ambas
    public LibreriaController(
        @Qualifier("memory") Libreria libreriaMemoria,
        @Qualifier("database") Libreria libreriaDatabase
    ) {
        this.libreriaMemoria = libreriaMemoria;
        this.libreriaDatabase = libreriaDatabase;
    }

    // Usas una u otra según el endpoint
    @GetMapping("/libros/memory")
    public List<Libro> getLibrosFromMemory() {
        return libreriaMemoria.todosLosLibros();  //  Usa la de memoria
    }

    @GetMapping("/libros/database")
    public List<Libro> getLibrosFromDatabase() {
        return libreriaDatabase.todosLosLibros();  //  Usa la de DB
    }

    @GetMapping("/libros")
    public List<Libro> getLibros() {
        // Puedes decidir cuál usar según alguna lógica
        if (algunaCondicion) {
            return libreriaDatabase.todosLosLibros();
        } else {
            return libreriaMemoria.todosLosLibros();
        }
    }
}
```

**Esto funciona perfectamente.** Pero la pregunta es: **¿Cuándo tiene sentido hacer esto?**

---

## Escenarios donde SÍ tiene sentido tener ambas:

### Caso 1: Cache + Persistencia

```java
@RestController
public class LibreriaController {
    private final Libreria cache;      // Rápido
    private final Libreria database;   // Persistente

    public LibreriaController(
        @Qualifier("cache") Libreria cache,
        @Qualifier("database") Libreria database
    ) {
        this.cache = cache;
        this.database = database;
    }

    @GetMapping("/libros/{isbn}")
    public Libro getLibro(@PathVariable String isbn) {
        // 1. Intenta obtener del cache (rápido)
        try {
            return cache.buscarLibroPorIsbn(isbn);
        } catch (RuntimeException e) {
            // 2. Si no está en cache, busca en DB
            Libro libro = database.buscarLibroPorIsbn(isbn);
            // 3. Guarda en cache para la próxima vez
            cache.agregarLibro(libro);
            return libro;
        }
    }
}
```

**Tiene sentido porque:**
- Ambas tienen roles diferentes (velocidad vs persistencia)
- Usas ambas en el mismo flujo

---

### Caso 2: Comparación o Migración

```java
@RestController
public class LibreriaController {
    private final Libreria oldSystem;
    private final Libreria newSystem;

    public LibreriaController(
        @Qualifier("legacy") Libreria oldSystem,
        @Qualifier("modern") Libreria newSystem
    ) {
        this.oldSystem = oldSystem;
        this.newSystem = newSystem;
    }

    @GetMapping("/libros/compare")
    public Map<String, Object> compareImplementations() {
        List<Libro> librosViejo = oldSystem.todosLosLibros();
        List<Libro> librosNuevo = newSystem.todosLosLibros();

        return Map.of(
            "oldCount", librosViejo.size(),
            "newCount", librosNuevo.size(),
            "match", librosViejo.equals(librosNuevo)
        );
    }
}
```

**Tiene sentido porque:**
- Estás migrando de un sistema a otro
- Necesitas comparar resultados
- Temporalmente corres ambos en paralelo

---

### Caso 3: Multi-tenancy (Múltiples clientes)

```java
@RestController
public class LibreriaController {
    private final Libreria libreriaPremium;
    private final Libreria libreriaFree;

    @GetMapping("/libros")
    public List<Libro> getLibros(@RequestHeader("User-Type") String userType) {
        if ("premium".equals(userType)) {
            return libreriaPremium.todosLosLibros();  // Más funciones
        } else {
            return libreriaFree.todosLosLibros();     // Básico
        }
    }
}
```

---

## Escenarios donde NO tiene sentido tener ambas:

### Caso 1: Solo usas una a la vez (redundante)

```java
@RestController
public class LibreriaController {
    private final Libreria libreriaMemoria;   // L Solo usas una
    private final Libreria libreriaDatabase;  // L La otra está inactiva

    @GetMapping("/libros")
    public List<Libro> getLibros() {
        // Siempre usas la de database
        return libreriaDatabase.todosLosLibros();
        // libreriaMemoria nunca se usa ’ desperdicio
    }
}
```

**Problema:**
- Spring crea ambos beans (consume memoria)
- Solo usas uno
- El otro está ahí "ocupando espacio"

**Mejor solución:** Usa `@Primary` o `@Qualifier` y solo inyecta el que necesitas

---

### Caso 2: Ambas hacen lo mismo (confusión)

```java
@Service
@Qualifier("impl1")
public class LibreriaImpl1 implements Libreria {
    // Implementación A
}

@Service
@Qualifier("impl2")
public class LibreriaImpl2 implements Libreria {
    // Implementación B que hace LO MISMO que A
}

@RestController
public class LibreriaController {
    private final Libreria impl1;
    private final Libreria impl2;

    @GetMapping("/libros")
    public List<Libro> getLibros() {
        // S ¿Cuál usar? Ambas hacen lo mismo
        return impl1.todosLosLibros();  // ¿Por qué no impl2?
    }
}
```

**Problema:**
- Confusión: "¿Por qué tengo dos si hacen lo mismo?"
- Mantenibilidad: Si cambias una, ¿cambias la otra?
- Decisión arbitraria: No hay razón clara para elegir una u otra

**Mejor solución:** Ten solo una implementación o usa `@Primary`

---

## La pregunta clave: ¿Por qué tendrías dos implementaciones?

### Razones VÁLIDAS:

1. **Diferentes fuentes de datos**
   - Una en memoria (rápida, temporal)
   - Otra en BD (persistente)

2. **Diferentes niveles de servicio**
   - Implementación básica (gratis)
   - Implementación premium (pago)

3. **Cache + Persistencia**
   - Cache para velocidad
   - DB para datos permanentes

4. **Migración**
   - Sistema viejo (legacy)
   - Sistema nuevo (durante transición)

5. **Testing**
   - Mock/Fake para tests
   - Real para producción

---

### Razones NO VÁLIDAS (anti-patterns):

1. **"Por si acaso"**
   - No las usas activamente
   - Solo está ahí "por las dudas"

2. **Dos versiones de lo mismo**
   - Ambas hacen exactamente lo mismo
   - No hay criterio para elegir una

3. **Falta de decisión**
   - No sabes cuál usar
   - Inyectas ambas para decidir después

---

## Soluciones cuando tienes múltiples implementaciones:

### Solución 1: `@Primary` (Marca la predeterminada)

```java
@Service
@Primary  //  "Usa esta por defecto si no especificas cuál"
public class LibreriaDatabaseImpl implements Libreria {
    // ...
}

@Service
public class LibreriaInMemoryImpl implements Libreria {
    // ...
}

// Controller
@RestController
public class LibreriaController {
    private final Libreria libreria;

    public LibreriaController(Libreria libreria) {  //  Inyecta la @Primary
        this.libreria = libreria;  // Será LibreriaDatabaseImpl
    }
}
```

**¿Cuándo usar `@Primary`?**
- Cuando tienes una implementación "principal" que usarás el 90% del tiempo
- Las otras implementaciones son para casos especiales

---

### Solución 2: `@Qualifier` (Especifica cuál quieres)

```java
@Service
@Qualifier("database")  //  Le pones un nombre
public class LibreriaDatabaseImpl implements Libreria {
    // ...
}

@Service
@Qualifier("memory")  //  Le pones otro nombre
public class LibreriaInMemoryImpl implements Libreria {
    // ...
}

// Controller - Opción A: Inyectar la de base de datos
@RestController
public class LibreriaController {
    private final Libreria libreria;

    public LibreriaController(@Qualifier("database") Libreria libreria) {  //  Específico
        this.libreria = libreria;  // Será LibreriaDatabaseImpl
    }
}

// Controller - Opción B: Inyectar ambas
@RestController
public class LibreriaController {
    private final Libreria libreriaDB;
    private final Libreria libreriaMemoria;

    public LibreriaController(
        @Qualifier("database") Libreria libreriaDB,
        @Qualifier("memory") Libreria libreriaMemoria
    ) {
        this.libreriaDB = libreriaDB;
        this.libreriaMemoria = libreriaMemoria;
    }

    @GetMapping("/libros")
    public List<Libro> getLibros() {
        // Podrías decidir cuál usar según alguna lógica
        return libreriaDB.todosLosLibros();
    }
}
```

**¿Cuándo usar `@Qualifier`?**
- Cuando necesitas usar implementaciones específicas en diferentes lugares
- Cuando no hay una implementación "predeterminada"
- Cuando quieres inyectar múltiples implementaciones en la misma clase

---

## Comparación visual:

```
                                                
   UNA IMPLEMENTACIÓN (Tu caso actual)         
                                                $
                                                
  Interface: Libreria                           
       “                                        
  Implementación: LibreriaImpl (@Service)       
       “                                        
  Spring: "Solo hay una, la inyecto"           
       “                                        
  Controller recibe: Libreria libreria          
        NO necesitas @Qualifier               
                                                
                                                
```

```
                                                
   MÚLTIPLES IMPLEMENTACIONES                   
                                                $
                                                
  Interface: Libreria                           
       “                    “                   
  LibreriaInMemory      LibreriaDatabase        
   (@Service)             (@Service)            
       “                    “                   
  Spring: "¡Hay dos! ¿Cuál quieres?"           
       “                                        
  Opciones:                                     
    1. @Primary en una                          
    2. @Qualifier("nombre")                     
    3. Inyectar ambas si las usas activamente   
                                                
                                                
```

---

## Regla de oro:

> **"Solo inyecta lo que vas a usar activamente"**

### Preguntas para decidir:

1. **¿Usas ambas implementaciones en el mismo flujo?**
   -  Sí ’ Tiene sentido tener ambas
   - L No ’ Solo inyecta la que uses

2. **¿Tienen roles claramente diferentes?**
   -  Sí (cache vs DB, free vs premium) ’ Tiene sentido
   - L No (ambas hacen lo mismo) ’ Solo una

3. **¿Podrías lograr lo mismo con una sola?**
   -  Sí ’ Usa solo una
   - L No ’ Inyecta las que necesites

---

## Resumen:

 **Sí puedes inyectar múltiples implementaciones:**
```java
public LibreriaController(
    @Qualifier("impl1") Libreria impl1,
    @Qualifier("impl2") Libreria impl2
) {
    // Válido y funciona
}
```

 **Tiene sentido cuando:**
- Cada una tiene un rol diferente (cache + DB)
- Las usas ambas activamente
- Complementan funcionalidades

L **NO tiene sentido cuando:**
- Solo usas una (la otra está "de adorno")
- Ambas hacen exactamente lo mismo
- No hay razón clara para tener ambas

<¯ **Mejor práctica:**
- Por defecto: Inyecta solo la que necesitas
- Usa `@Primary` para marcar la predeterminada
- Solo inyecta múltiples cuando tengan roles distintos y activos

**Conclusión:** Sí se puede inyectar múltiples implementaciones, pero hay que tener un buen motivo para hacerlo. De lo contrario, es mejor mantener las cosas simples y solo inyectar lo que realmente vas a usar. =€

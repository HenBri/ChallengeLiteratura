# 📚 LiteraAlura — Catálogo de Libros

> Aplicación de consola desarrollada con **Spring Boot** que permite buscar, registrar y explorar libros a través de la API pública de [Gutendex](https://gutendex.com/), con persistencia de datos en una base de datos relacional.

---

## 🗂️ Tabla de Contenidos

- [Descripción](#-descripción)
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Arquitectura del Proyecto](#-arquitectura-del-proyecto)
- [Requisitos Previos](#-requisitos-previos)
- [Configuración e Instalación](#-configuración-e-instalación)
- [Uso](#-uso)
- [Estructura de Clases](#-estructura-de-clases)
- [API Externa](#-api-externa)

---

## 📖 Descripción

**LiteraAlura** es un catálogo interactivo de libros por línea de comandos. Permite al usuario buscar títulos de libros a través de la API de Gutendex (basada en el Proyecto Gutenberg), guardarlos localmente junto con la información de sus autores, y realizar consultas sobre el catálogo almacenado.

---

## ✨ Características

- 🔍 **Búsqueda de libros** por título usando la API de Gutendex
- 💾 **Persistencia automática** de libros y autores en base de datos
- 📋 **Listado completo** de libros y autores registrados
- 🌍 **Filtrado por idioma** (por ejemplo: `en`, `es`, `fr`)
- 🧓 **Consulta de autores vivos** en un año determinado
- 🚫 **Prevención de duplicados** al registrar un libro ya existente

---

## 🛠️ Tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17+ | Lenguaje principal |
| Spring Boot | 3.x | Framework base |
| Spring Data JPA | 3.x | Persistencia y ORM |
| Hibernate | 6.x | Implementación JPA |
| PostgreSQL / H2 | — | Base de datos |
| Jackson | 2.x | Deserialización JSON |
| Lombok | 1.18+ | Reducción de boilerplate |
| Gutendex API | — | Fuente de datos de libros |

---

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas estándar de Spring Boot:

```
pe.challenge.literatura/
│
├── LibraryApplication.java          # Punto de entrada (CommandLineRunner)
├── Principal.java                   # Controlador de menú interactivo
│
├── model/
│   ├── Autor.java                   # Entidad JPA: Autor
│   └── Libro.java                   # Entidad JPA: Libro
│
├── dtos/
│   ├── DatosAutor.java              # DTO para deserializar autor desde API
│   ├── DatosBook.java               # DTO para deserializar libro desde API
│   └── DatosRespuesta.java          # DTO para deserializar respuesta raíz
│
├── repositories/
│   ├── AutorRepository.java         # Repositorio CrudRepository para Autor
│   └── BookRepository.java          # Repositorio CrudRepository para Libro
│
└── service/
    ├── BookService.java             # Lógica de negocio principal
    └── ConsumoApiService.java       # Cliente HTTP para consumir Gutendex
```

---

## ✅ Requisitos Previos

Asegúrate de tener instalado:

- **Java 17** o superior
- **Maven 3.8+**
- **PostgreSQL** (u otra base de datos compatible configurada en `application.properties`)
- Conexión a internet para consumir la API de Gutendex

---

## ⚙️ Configuración e Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/literalura.git
cd literalura
```

### 2. Configurar la base de datos

Edita el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

### 3. Compilar y ejecutar

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🚀 Uso

Al iniciar la aplicación, se muestra el siguiente menú interactivo:

```
1 - Buscar libro por título
2 - Listar libros
3 - Listar autores
4 - Listar libros por idioma
5 - Autores vivos en un año
0 - Salir
```

### Ejemplos de uso

**Buscar un libro:**
```
Seleccione opción: 1
Ingrese título: Don Quixote

📖 ----- LIBRO ENCONTRADO -----
Título    : Don Quixote
Autor     : Cervantes Saavedra, Miguel de
Idioma    : es
Descargas : 18432
-------------------------------
Libro guardado correctamente.
```

**Listar por idioma:**
```
Seleccione opción: 4
Ingrese idioma (ej: en, es): es

--- Libros en idioma 'es' ---
- Don Quixote (Autor: Cervantes Saavedra, Miguel de)
----------------------------------
```

**Autores vivos en un año:**
```
Seleccione opción: 5
Ingrese año: 1850

--- Autores vivos en el año 1850 ---
- Dickens, Charles (1812 - 1870)
--------------------------------------
```

---

## 📐 Estructura de Clases

### Entidades

**`Autor`** — Representa un autor almacenado en la base de datos.
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | Identificador autoincremental |
| nombre | String | Nombre completo del autor |
| nacimiento | Integer | Año de nacimiento |
| fallecimiento | Integer | Año de fallecimiento (puede ser nulo) |
| libros | List\<Libro\> | Relación uno-a-muchos con libros |

**`Libro`** — Representa un libro almacenado en la base de datos.
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | Identificador autoincremental |
| titulo | String | Título del libro |
| idioma | String | Código de idioma (ej: `en`, `es`) |
| descargas | Double | Número de descargas en Gutendex |
| autor | Autor | Relación muchos-a-uno con autor |

---

## 🌐 API Externa

Este proyecto consume la API pública de **[Gutendex](https://gutendex.com/)**, un índice web del Proyecto Gutenberg.

**Endpoint utilizado:**
```
GET https://gutendex.com/books/?search={titulo}
```

**Ejemplo de respuesta:**
```json
{
  "results": [
    {
      "title": "Pride and Prejudice",
      "authors": [{ "name": "Austen, Jane", "birth_year": 1775, "death_year": 1817 }],
      "languages": ["en"],
      "download_count": 53000
    }
  ]
}
```

---

## 👤 Henry Aspeti

Desarrollado como parte del **Challenge LiteraAlura** de [Alura Latam](https://www.aluracursos.com/) — ONE (Oracle Next Education).

---

# Challenge Literatura - Alura

Este proyecto es una aplicación de consola desarrollada en Java con Spring Boot que permite interactuar con la API de [Gutendex](https://gutendex.com/) para buscar libros y gestionar un catálogo personal almacenado en una base de datos PostgreSQL.

## 🚀 Funcionalidades

- **Buscar libro por título:** Consulta la API de Gutendex, recupera la información del libro y su autor, y lo registra en la base de datos local.
- **Listar libros registrados:** Muestra todos los libros que han sido guardados previamente.
- **Listar autores registrados:** Muestra una lista de todos los autores cuyos libros han sido registrados.
- **Listar autores vivos en un año determinado:** Filtra los autores registrados basándose en un año específico para verificar quiénes estaban vivos en ese entonces.

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL**
- **HttpClient** (para solicitudes a la API)
- **Jackson** (para el manejo de JSON)
- **Maven** (gestión de dependencias)

## 📋 Requisitos Previos

- Tener instalado **Java 17** o superior.
- Tener instalado **Maven**.
- Una instancia de **PostgreSQL** corriendo localmente.

## ⚙️ Configuración

1. Clona el repositorio.
2. Configura las credenciales de tu base de datos en el archivo `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literatura
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   ```
3. Asegúrate de que la base de datos `literatura` exista en tu servidor PostgreSQL.

## 📂 Arquitectura

El proyecto sigue una arquitectura por capas para mantener el código organizado y escalable:
- **Client:** Maneja las peticiones HTTP externas.
- **DTO:** Objetos de transferencia de datos para la API.
- **Model:** Entidades JPA representativas de la base de datos.
- **Repository:** Interfaces para el acceso a datos.
- **Service:** Lógica de negocio de la aplicación.
- **Menu:** Interfaz de usuario por consola.

---
Desarrollado como parte del Challenge de Literatura de Alura Latam.


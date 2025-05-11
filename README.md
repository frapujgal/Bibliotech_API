# 📚 Bibliotech_API

API RESTful para la gestión de una biblioteca, desarrollada en Java utilizando el framework Spring Boot. Este proyecto permite administrar libros, usuarios y préstamos, proporcionando endpoints para operaciones CRUD y consultas específicas.

## 🚀 Características
- Gestión de libros: Alta, baja, modificación y consulta de libros.

- Gestión de usuarios: Registro, actualización y eliminación de usuarios.

- Gestión de préstamos: Registro de préstamos y devoluciones de libros.

- Consultas avanzadas: Búsqueda de libros por título, autor o categoría.

## 🛠️ Tecnologías utilizadas
- Java

- Spring Boot

- Maven

- Hibernate

## 📁 Rutas API
### **Libros**
- **GET /api/books**: Obtiene una lista de todos los libros.
- **GET /api/books/{id}**: Obtiene un libro específico por su ID
- **POST /api/books**: Crea un nuevo libro.
- **DELETE /api/books/{id}**: Elimina un libro.
- **GET /api/books/available**: Obtiene una lista de los libros actualmente disponibles.
- **GET /api/books/unavailable**: Obtiene una lista de los libros actualmente prestados.

### **Usuarios**
- **GET /api/users**: Obtiene una lista de todos los usuarios.
- **GET /api/users/{id}**: Obtiene un usuario específico por su ID
- **POST /api/users**: Crea un nuevo usuario.
- **PATCH /api/users/{id}**: Actualiza un usuario existente.
- **DELETE /api/users/{id}**: Elimina un usuario.
- **GET /api/users/login**: Comprueba credenciales de usuario.

### **Préstamos**
- **GET /api/loans**: Obtiene una lista de todos los préstamos.
- **GET /api/loans/{id}**: Obtiene un préstamo específico
- **POST /api/loans**: Crea un nuevo préstamo.
- **PUT /api/loans/{id}/return**: Actualiza un préstamo existente.
- **DELETE /prestamos/{id}**: Elimina un préstamo.
- **GET /api/loans/user/{userId}**: Obtiene los préstamos de un usuario específico
- **GET /api/loans/book/{id}**: Obtiene los préstamos de un libro específico
- **GET /api/loans/last{bookId}**: Obtiene el último préstamo de un usuario específico

### **Comentarios**
- **GET /api/comments**: Obtiene una lista de todos los comentarios.
- **GET /api/comments/{id}**: Obtiene un comentario específico.
- **POST /api/comments**: Crea un nuevo comentario.

## 📂 Estructura del proyecto
```
Bibliotech_API/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bibliotech/
│   │   │           ├── controller/
│   │   │           ├── model/
│   │   │           ├── repository/
│   │   │           └── service/
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
└── README.md
```

## 🙌 Créditos
Aplicación desarrollada por Francisco Pujol Gallego como proyecto de fin de ciclo del Grado Superior de Desarrollo de Aplicaciones Multiplataforma.
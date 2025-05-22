Perfecto. Aquí tienes un ejemplo de README estructurado y profesional para tu proyecto, que puedes adaptar según tus necesidades. Lo redacté con base en todo lo que has implementado:

---

## 📌 SIGEPRO – Sistema de Gestión de Proyectos

SIGEPRO es una aplicación web desarrollada con **Spring Boot** y **Thymeleaf** que permite gestionar proyectos, tareas, usuarios, notificaciones, reportes PDF y más.

---

### 🧰 Tecnologías utilizadas

* Java 17
* Spring Boot
* Spring MVC
* Spring Security + JWT
* Spring Data JPA (Hibernate)
* Thymeleaf
* Bootstrap
* MySQL
* iText (para generación de PDFs)

---

### 🔧 Funcionalidades principales

* **Gestión de proyectos**: creación, edición, asignación de usuarios.
* **Gestión de tareas**: asignar responsables, cambiar estados, comentar.
* **Sistema de notificaciones**: para eventos clave como asignación de tareas, comentarios, etc.
* **Generación de reportes en PDF**: por proyecto.
* **Carga y descarga de archivos**: imágenes y documentos adjuntos en tareas.
* **Autenticación y autorización** con JWT y cookies.
* **Panel de usuario**: perfil, configuración y cambio de contraseña.
* **Campanita de notificaciones** dinámica en el navbar.

---

### ▶️ Instalación y ejecución local

1. Clona el repositorio:

```bash
git clone https://github.com/tuusuario/sigepro.git
cd sigepro
```

2. Configura tu archivo `application.properties` o `.yml`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sigepro
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

jwt.secret=unaClaveSecreta123
file.upload-dir=uploads
```

3. Crea la base de datos `sigepro` en MySQL utilizando el archivo sql.
```
schema.sql
```

4. Ejecuta la aplicación:

```bash
./mvnw spring-boot:run
```

5. Accede desde el navegador:

```
http://localhost:8081
```

---

### 📂 Estructura del proyecto

* `controllers/`: Controladores web
* `services/`: Lógica de negocio
* `models/`: Entidades JPA
* `repositories/`: Repositorios de datos
* `templates/`: Vistas Thymeleaf
* `static/`: Archivos estáticos (CSS, JS, imágenes)
* `util/`: Funciones auxiliares (subida de archivos, generación de PDF)

---

### 🧪 Datos de prueba

Puedes poblar tu base de datos con datos SQL haciendo uso del archivo **data.sql**.

---

### 💡 Pendientes o ideas a futuro

* Mejorar el diseño responsive.
* Implementar filtros por proyecto/estado/usuario.
* Notificaciones en tiempo real con WebSockets.
* CRUD completo de usuarios por roles administrativos.

---

### 🤝 Autor

Desarrollado por ***Isaac Celis Vargas*** ara diplomado Java DGTIC.

---

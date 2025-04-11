Drop database if exists sigepro;
CREATE database sigepro;
use sigepro;

-- Tabla de Roles
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla de Usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    rolId INT NOT NULL,
    CONSTRAINT fk_usuarios_roles FOREIGN KEY (rolId) REFERENCES roles(id) ON DELETE CASCADE
);

-- Tabla de Proyectos
CREATE TABLE proyectos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    creadorId INT NOT NULL,
    CONSTRAINT fk_proyectos_creador FOREIGN KEY (creadorId) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Tabla de Tareas
CREATE TABLE tareas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proyectoId INT NOT NULL,
    usuarioId INT NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    estado ENUM('Pendiente', 'En_progreso', 'Completada','Cancelada') DEFAULT 'Pendiente',
    fechaLimite DATE,
    CONSTRAINT fk_tareas_proyectos FOREIGN KEY (proyectoId) REFERENCES proyectos(id) ON DELETE CASCADE,
    CONSTRAINT fk_tareas_usuarios FOREIGN KEY (usuarioId) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Tabla de Comentarios
CREATE TABLE comentarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tareaId INT NOT NULL,
    usuarioId INT NOT NULL,
    comentario TEXT NOT NULL,
    archivoUrl TEXT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comentarios_tareas FOREIGN KEY (tareaId) REFERENCES tareas(id) ON DELETE CASCADE,
    CONSTRAINT fk_comentarios_usuarios FOREIGN KEY (usuarioId) REFERENCES usuarios(id) ON DELETE CASCADE
);



-- Tabla de Notificaciones
CREATE TABLE notificaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuarioId INT NOT NULL,
    tipo ENUM('Tarea', 'Comentario', 'Otro') NOT NULL,
    contenido TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    leido BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_notificaciones_usuarios FOREIGN KEY (usuarioId) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Tabla de Reportes
CREATE TABLE reportes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proyectoId INT NOT NULL,
    tipo ENUM('General', 'Tareas', 'Error', 'Mejora', 'Otro') NOT NULL,
    contenido TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reportes_proyectos FOREIGN KEY (proyectoId) REFERENCES proyectos(id) ON DELETE CASCADE
);

-- Tabla de Historial de Cambios
CREATE TABLE historial_cambios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tareaId INT NOT NULL,
    usuarioId INT NOT NULL,
    accion VARCHAR(255) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_historial_tareas FOREIGN KEY (tareaId) REFERENCES tareas(id) ON DELETE CASCADE,
    CONSTRAINT fk_historial_usuarios FOREIGN KEY (usuarioId) REFERENCES usuarios(id) ON DELETE CASCADE
);

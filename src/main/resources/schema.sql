Drop database if exists sigepro;
CREATE database sigepro;
use sigepro;

-- Tabla de Roles
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre ENUM('Administrador', 'Lider', 'Miembro','Invitado') DEFAULT 'Invitado'
);

CREATE TABLE equipo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL
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

CREATE TABLE usuario_equipo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuarioId INT,
    equipoId INT,
    FOREIGN KEY (usuarioId) REFERENCES usuarios(id),
    FOREIGN KEY (equipoId) REFERENCES equipo(id)
);

-- Tabla de Proyectos
CREATE TABLE proyectos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    creadorId INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_proyectos_creador FOREIGN KEY (creadorId) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Tabla de Tareas
CREATE TABLE tareas (
    prioridad ENUM('baja', 'media', 'alta', 'crítica') DEFAULT 'media',
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

-- Tabla de invitaciones
CREATE TABLE invitacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    token VARCHAR(100) NOT NULL,
    fecha_invitacion TIMESTAMP NOT NULL,
    aceptada BOOLEAN DEFAULT FALSE,
    rolId INT,
    FOREIGN KEY (rolId) REFERENCES roles(id)
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

CREATE TABLE archivos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tareaId INT,
    comentarioId INT,
    url TEXT,
    tipo VARCHAR(50),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (tareaId) REFERENCES tareas(id),
    FOREIGN KEY (comentarioId) REFERENCES comentarios(id)
);

-- Tabla de Notificaciones
CREATE TABLE notificaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuarioId INT NOT NULL,
    idTarea INT,
    contenido TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    leido BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_notificaciones_tareas FOREIGN KEY (idTarea) REFERENCES tareas(id),
    CONSTRAINT fk_notificaciones_usuarios FOREIGN KEY (usuarioId) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Tabla de Reportes
CREATE TABLE reportes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proyectoId INT NOT NULL,
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


-- Tabla para invitaciones de nuevos usuarios
CREATE TABLE invitaciones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



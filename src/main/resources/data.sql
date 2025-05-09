-- Insertar Roles
INSERT INTO roles (nombre) VALUES
('Administrador'),
('Lider'),
('Miembro'),
('Invitado');

-- Insertar Usuarios
INSERT INTO usuarios (nombre, email, contraseña, rolId) VALUES
('Juan Pérez', 'juan.perez@example.com', 'pass123', 1),
('Ana García', 'ana.garcia@example.com', 'pass456', 2),
('Carlos Rodríguez', 'carlos.rodriguez@example.com', 'pass789', 3),
('Laura Martínez', 'laura.martinez@example.com', 'passabc', 2),
('Pedro Sánchez', 'pedro.sanchez@example.com', 'passdef', 4);

-- Insertar Proyectos
INSERT INTO proyectos (nombre, descripcion, creadorId) VALUES
('Sistema de Gestión de Clientes', 'Desarrollo de un sistema para gestionar la información de los clientes.', 1),
('Aplicación Móvil de E-commerce', 'Creación de una aplicación móvil para la venta de productos en línea.', 2),
('Plataforma de Aprendizaje en Línea', 'Desarrollo de una plataforma para cursos y capacitaciones online.', 1),
('Nuevo Sitio Web Corporativo', 'Diseño y desarrollo del nuevo sitio web de la empresa.', 3),
('Herramienta de Análisis de Datos', 'Desarrollo de una herramienta para el análisis de grandes volúmenes de datos.', 2);

-- Insertar Tareas
INSERT INTO tareas (proyectoId, usuarioId, nombre, descripcion, estado, fechaLimite) VALUES
(1, 2, 'Diseñar la interfaz de usuario', 'Diseñar la interfaz de usuario para el módulo de clientes.', 'En_progreso', '2025-04-20'),
(1, 4, 'Implementar la autenticación', 'Implementar el sistema de autenticación de usuarios.', 'Pendiente', '2025-04-25'),
(2, 3, 'Desarrollar el carrito de compras', 'Desarrollar la funcionalidad del carrito de compras en la app.', 'Completada', '2025-04-15'),
(2, 2, 'Integrar pasarela de pago', 'Integrar una pasarela de pago segura en la aplicación.', 'En_progreso', '2025-04-22'),
(3, 1, 'Crear la estructura de cursos', 'Definir la estructura y el contenido de los cursos en la plataforma.', 'Pendiente', '2025-04-28'),
(3, 4, 'Implementar el sistema de inscripciones', 'Desarrollar el sistema para que los usuarios se inscriban en los cursos.', 'En_progreso', '2025-04-24'),
(4, 5, 'Diseñar la página de inicio', 'Diseñar la página principal del nuevo sitio web.', 'Completada', '2025-04-10'),
(4, 2, 'Desarrollar el formulario de contacto', 'Desarrollar el formulario para que los visitantes puedan contactar a la empresa.', 'Cancelada', '2025-04-18'),
(5, 3, 'Implementar la conexión a la base de datos', 'Establecer la conexión con la base de datos para recuperar los datos.', 'En_progreso', '2025-04-21'),
(5, 1, 'Desarrollar los algoritmos de análisis', 'Desarrollar los algoritmos para realizar el análisis de datos.', 'Pendiente', '2025-04-27'),
(1, 1, 'Refactorizar módulo de informes', 'Mejorar la eficiencia y legibilidad del código del módulo de informes', 'En_progreso', '2025-05-01'),
(2, 4, 'Corregir error en el módulo de pagos', 'Resolver un problema que impide procesar algunos pagos correctamente', 'Completada', '2025-04-12');

-- Insertar Comentarios
INSERT INTO comentarios (tareaId, usuarioId, comentario, archivoUrl) VALUES
(1, 2, 'El diseño de la interfaz está casi terminado.', 'diseno_interfaz.pdf'),
(1, 1, 'Revisar los colores y la tipografía.', 'estilos.css'),
(3, 3, 'El carrito de compras funciona correctamente.', 'carrito.java'),
(3, 2, 'Asegurarse de que sea compatible con diferentes navegadores.', 'compatibilidad.js'),
(5, 1, 'Los algoritmos de análisis están en desarrollo.', 'algoritmos.py'),
(5, 3, 'Probar el rendimiento con grandes conjuntos de datos.', 'rendimiento.txt'),
(11, 1, 'Se mejoró la eficiencia del código', 'refactorizacion.txt'),
(12, 4, 'Se corrigió el error y se realizaron pruebas', 'correccion_error.txt');

-- Insertar Notificaciones
INSERT INTO notificaciones (usuarioId, tipo, contenido) VALUES
(2, 'Tarea', 'Se te ha asignado la tarea "Diseñar la interfaz de usuario".'),
(3, 'Comentario', 'Hay un nuevo comentario en la tarea "Desarrollar el carrito de compras".'),
(1, 'Tarea', 'La tarea "Crear la estructura de cursos" ha sido creada.'),
(4, 'Tarea', 'Se actualizó la tarea "Integrar pasarela de pago".'),
(1, 'Otro', 'Recordatorio: Reunión de seguimiento del proyecto el viernes.'),
(2, 'Comentario', 'Nuevo comentario en la tarea "Diseñar la interfaz de usuario".'),
(4, 'Tarea', 'Se te ha asignado la tarea "Corregir error en el módulo de pagos".');

-- Insertar Reportes
INSERT INTO reportes (proyectoId, tipo, contenido) VALUES
(1, 'General', 'Informe general del progreso del proyecto.'),
(2, 'Tareas', 'Informe de estado de las tareas del proyecto.'),
(3, 'Error', 'Informe de errores encontrados en la plataforma.'),
(4, 'Mejora', 'Propuestas de mejora para el sitio web.'),
(5, 'Otro', 'Informe de análisis de datos preliminar.'),
(1, 'Tareas', 'Reporte de tareas completadas y pendientes.'),
(2, 'Error', 'Reporte de errores en la pasarela de pago.');

-- Insertar Historial de Cambios
INSERT INTO historial_cambios (tareaId, usuarioId, accion) VALUES
(1, 2, 'Se inició el diseño de la interfaz de usuario.'),
(3, 3, 'Se completó el desarrollo del carrito de compras.'),
(1, 4, 'Se actualizó el estado de la tarea a "En_progreso".'),
(2, 2, 'Se asignó la tarea a Ana García.'),
(5, 1, 'Se comenzó el desarrollo de los algoritmos de análisis.'),
(11, 1, 'Se refactorizó el módulo de informes.'),
(12, 4, 'Se corrigió el error en el módulo de pagos.');




-- ====================================================================================

-- Insert para la tabla roles
INSERT INTO roles (nombre) VALUES
('Administrador'),
('Lider'),
('Miembro'),
('Invitado');

-- Insert para la tabla equipo
INSERT INTO equipo (nombre) VALUES
('Equipo de Desarrollo'),
('Equipo de Diseño'),
('Equipo de Marketing');

-- Insert para la tabla usuarios
INSERT INTO usuarios (nombre, email, contraseña, rolId, equipoId) VALUES
('Alice Smith', 'alice.smith@example.com', 'contraseña123', 1, 1),
('Bob Johnson', 'bob.johnson@example.com', 'segura456', 2, 1),
('Charlie Brown', 'charlie.brown@example.com', 'mypass', 3, 2),
('Diana Lee', 'diana.lee@example.com', 'password', 3, 1),
('Eve Davis', 'eve.davis@example.com', 'claveunica', 2, 3),
('Frank Miller', 'frank.miller@example.com', 'otraclave', 3, 2),
('Grace Wilson', 'grace.wilson@example.com', 'micontra', 4, NULL);

-- Insert para la tabla proyectos
INSERT INTO proyectos (nombre, descripcion, creadorId) VALUES
('Desarrollo de Nueva Plataforma', 'Creación de una plataforma web innovadora.', 1),
('Rediseño de Sitio Web', 'Actualización completa del diseño y la funcionalidad del sitio web existente.', 2),
('Campaña de Marketing Digital', 'Implementación de estrategias de marketing online para aumentar la visibilidad.', 5);

-- Insert para la tabla tareas
INSERT INTO tareas (proyectoId, usuarioId, nombre, descripcion, estado, fechaLimite) VALUES
(1, 2, 'Implementar Autenticación de Usuarios', 'Desarrollar el sistema de login y registro.', 'En_progreso', '2025-05-15'),
(1, 1, 'Diseñar la Base de Datos', 'Crear el modelo de datos para la nueva plataforma.', 'Completada', '2025-05-10'),
(2, 3, 'Maquetación de la Página Principal', 'Desarrollar el HTML y CSS para la home page.', 'Pendiente', '2025-05-20'),
(2, 4, 'Optimización de Imágenes', 'Comprimir y optimizar las imágenes del sitio web.', 'En_progreso', '2025-05-18'),
(3, 5, 'Creación de Contenido para Redes Sociales', 'Generar publicaciones atractivas para las diferentes plataformas.', 'Pendiente', '2025-05-25'),
(1, 4, 'Pruebas Unitarias de API', 'Escribir y ejecutar pruebas para la API.', 'Completada', '2025-05-12');

-- Insert para la tabla invitacion
INSERT INTO invitacion (email, token, fecha_invitacion) VALUES
('invitado1@example.com', 'token123abc', CURRENT_TIMESTAMP),
('nuevo.usuario@example.org', 'securetoken456', '2025-05-08 10:00:00');

-- Insert para la tabla comentarios
INSERT INTO comentarios (tareaId, usuarioId, comentario, archivoUrl) VALUES
(1, 2, 'Avances en la implementación de la autenticación.', NULL),
(1, 1, 'El diseño de la base de datos está finalizado.', 'diagrama_db.pdf'),
(2, 3, 'La maquetación inicial está lista para revisión.', NULL),
(3, 5, 'Borradores del contenido para redes sociales adjuntos.', 'contenido_rrss.zip');

-- Insert para la tabla notificaciones
INSERT INTO notificaciones (usuarioId, tipo, contenido) VALUES
(2, 'Tarea', 'Se te ha asignado la tarea: Implementar Autenticación de Usuarios.'),
(1, 'Comentario', 'Nuevo comentario en la tarea: Diseñar la Base de Datos.'),
(3, 'Tarea', 'La fecha límite para la tarea: Maquetación de la Página Principal se acerca.');

-- Insert para la tabla reportes
INSERT INTO reportes (proyectoId, tipo, contenido) VALUES
(1, 'General', 'Informe general del progreso del proyecto de la nueva plataforma.'),
(2, 'Tareas', 'Listado de tareas completadas y pendientes del rediseño del sitio web.'),
(1, 'Error', 'Se encontraron errores durante las pruebas de la API.');

-- Insert para la tabla historial_cambios
INSERT INTO historial_cambios (tareaId, usuarioId, accion) VALUES
(1, 2, 'Inició la implementación de la autenticación de usuarios.'),
(1, 1, 'Finalizó el diseño de la base de datos.'),
(2, 3, 'Comenzó la maquetación de la página principal.'),
(1, 2, 'Actualizó el estado de la tarea a "En_progreso".');
-- Insertar Roles
INSERT INTO roles (nombre) VALUES
('Administrador'),
('Desarrollador'),
('Tester'),
('Analista');

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
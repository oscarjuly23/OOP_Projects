## Descripción:
Minder es una aplicación de escritorio diseñada para permitir que los estudiantes se conecten con otros estudiantes para realizar prácticas y trabajos académicos. Los usuarios pueden registrarse en la aplicación proporcionando información personal, incluyendo nombre, edad, correo electrónico, contraseña y la opción de obtener una cuenta Premium. Los datos de registro deben ser precisos, con requisitos específicos para el correo electrónico y la contraseña.
###
Una vez registrados con éxito, los usuarios deben completar su perfil la primera vez que inician sesión. Esto incluye seleccionar una imagen de perfil, escribir una descripción y elegir su lenguaje de programación (C o Java), todos los cuales deben completarse estrictamente.
###
El programa sigue una arquitectura cliente-servidor, lo que significa que la información de registro del usuario se envía al servidor para su autorización. Lo mismo ocurre con el inicio de sesión. Si los datos son incorrectos, se muestra un mensaje de error.
## Funcionalidades:
- Registro de Usuarios: Los usuarios pueden registrarse en la aplicación proporcionando información personal, incluyendo nombre, edad, correo electrónico y contraseña.
- Requisitos de Registro: Los datos de registro deben cumplir con requisitos específicos, como un correo electrónico válido y una contraseña que contenga al menos una minúscula, una mayúscula, un carácter numérico y tenga una longitud mínima de 8 caracteres. También se requiere que los usuarios sean mayores de edad.
- Completar Perfil: La primera vez que los usuarios inician sesión, deben completar su perfil, incluyendo la selección de una imagen de perfil, una descripción y la elección de su lenguaje de programación preferido.
- Lista de Usuarios: Los usuarios pueden ver una lista de perfiles de otros usuarios registrados, pero solo se mostrarán perfiles de usuarios que compartan el mismo lenguaje de programación.
- Aceptar y Rechazar Usuarios: Los usuarios pueden aceptar o rechazar otros perfiles, lo que lleva a un "match" si dos usuarios se aceptan mutuamente.
- Usuarios Premium: Los usuarios Premium tienen prioridad en la lista de usuarios.
- Chat en Tiempo Real: Una vez que dos usuarios hacen "match", pueden comenzar a chatear en tiempo real.
- Estadísticas del Servidor: El servidor muestra gráficos de la evolución de los "matchs" en la aplicación y una tabla con el TOP 5 de usuarios con más "matchs".
## Diseño de la Interfaz Gráfica
### Servidor:
La interfaz del servidor incluye una sección de gráficas que muestra la evolución de los "matchs" en diferentes períodos de tiempo y una sección que muestra el TOP 5 de usuarios con más "matchs".
### Cliente: 
El cliente tiene varias ventanas, incluyendo la ventana de inicio de sesión, registro, perfil, lista de chats y ventana de chat. Cada una de estas ventanas incluye elementos como campos de entrada, botones y ventanas emergentes para notificaciones.
## Metodología de Desarrollo:
El desarrollo del proyecto se dividió en tres fases: comprensión de los requisitos, diseño y codificación.
- En la fase de diseño, se trabajó en la estructura del proyecto, incluyendo un diagrama de clases (UML).
- La codificación se realizó en Java, con el uso de MySQL para la base de datos.
- Se utilizó IntelliJ IDEA como entorno de desarrollo y XAMPP para MySQL.
- La gestión de proyectos se realizó con JIRA, el control de versiones con GIT y BitBucket.
- Se utilizó la colaboración en tiempo real a través de Collaborate y Discord para la comunicación del equipo.

## Descripción
El proyecto consiste en desarrollar una aplicación de TMB que realice varias de las funcionalidades de la aplicación original, utilizando los datos de la API oficial de TMB. 
- Al ejecutar la aplicación, se te pedirá que ingreses tu nombre, correo electrónico y año de nacimiento.
- Luego, tendrás acceso a un menú con varias opciones, incluyendo la gestión de localizaciones, historial de búsquedas, planificación de rutas y consulta del tiempo de espera del autobús.
- Sigue las instrucciones en pantalla para utilizar las diversas funcionalidades.

## Funcionalidades:
1. Gestión de Usuario: Administrar localizaciones, ver historial de búsquedas de localizaciones, gestionar rutas, ver paradas y estaciones preferidas, buscar estaciones de metro inauguradas en el año de nacimiento del usuario.
2. Buscar Localizaciones: Buscar y guardar localizaciones.
3. Planificar Rutas: Encontrar la ruta más rápida entre un origen y un destino, considerando parámetros personalizados.
4. Tiempo de Espera de Autobuses: Consultar el tiempo de espera de autobuses en una parada específica.
5. Salir: Cerrar la aplicación.

## Estructura:
El proyecto está organizado en tres paquetes: "Api" (comunicación con la API de TMB), "Model" (almacenamiento de datos) y "Logica" (lógica principal de la aplicación). Se implementaron características adicionales, como validación de datos y capacidad de guardar información de usuarios en un archivo JSON (incompleto).


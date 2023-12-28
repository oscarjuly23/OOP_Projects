# LSubjects
Programa LSubjects - Projecte OOP

## Descripción
El proyecto LSubject tiene como objetivo ayudar a los estudiantes a encontrar las asignaturas que están cursando en el año actual. En el contexto de Diseño y Programación Orientada a Objetos (DPOO), se ha desarrollado una interfaz gráfica que permite a los estudiantes ver una lista de asignaturas preferidas que pueden personalizar a su gusto.

## Funcionalidades:
### Ventana de inicio de Sessión
- Los usuarios deben ingresar sus credenciales y se verifica si coinciden con los registros almacenados en el archivo LSubjects.json.
- En caso de credenciales incorrectas, se muestra un mensaje de error utilizando un JOptionPane.
### Ventana de Asignaturas
- Una vez que los usuarios inician sesión con credenciales válidas, se oculta la ventana de inicio de sesión y se abre la ventana de asignaturas.
- La ventana de asignaturas se divide en dos secciones: la parte superior muestra las asignaturas favoritas, mientras que la parte inferior muestra todas las asignaturas disponibles.
- Cada asignatura se presenta con un identificador, que es de color amarillo para asignaturas optativas y verde para asignaturas obligatorias, junto con el nombre, los créditos y un botón que permite agregar o eliminar las asignaturas de la sección de favoritas.
- Al hacer clic en el botón, la asignatura se mueve a la sección de favoritas y el botón cambia de "Añadir a favoritas" a "Eliminar de favoritas".
- Si se hace clic en "Eliminar de favoritas", la asignatura se elimina de la sección de favoritas y el botón vuelve a cambiar a "Añadir a favoritas".
- Ambas secciones permiten desplazarse ya que pueden contener un número ilimitado de asignaturas.
##
El proyecto sigue un patrón MVC (Modelo-Vista-Controlador).
La ejecución del programa finaliza cuando se cierra cualquiera de las dos ventanas.
Este proyecto es una herramienta útil para que los estudiantes gestionen y personalicen sus asignaturas de manera efectiva y eficiente en el contexto de la asignatura de DPOO.

##
<p align="center">
  <img width="460" height="300" src="https://github.com/oscarjuly23/OOP_Projects/assets/39187459/3a188511-61f0-459f-a259-f47071b68c45">
</p>

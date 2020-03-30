Nombre del grupo: G3
Integrantes: José Ruiz Alba, Vicente Lopez Vazquez, Antonio Corredera Lavado, Alberto Cordon Arevalo
Cantidad de historias de usuario % del total: 90%

Hemos realizado los test de findAll pese a que no debiamos hacerlos ya que su implementacion viene de base y son muy simples.

Tuvimos un incidente al intentar realizar una busqueda poniendo el campo en blanco, tal como se hace en el test de owner, nos dice que debe redirigir a excepcion, cuando deberia de llevarnos a la lista de citas filtradas

El test de show causa no funciona debido a que necesitamos saber el usuario conectado para ver si es un owner y asi ocultar los botones de editar y borrar.

El test positivo de crear una nueva donacion no funciona, porque al buscar una causa nos devuelve null.

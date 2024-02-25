# Tarea2-Alquiler-de-vehiculos-con-PrincipiosPOO

Se debe desarrollar el mismo problema de la "Unidad 1: Tarea - POO", pero aplicando los principios de POO como: encapsulamiento, ocultamiento, polimorfismo, herencia, abstracción, modularidad. Y realizar el manejo de excepciones/errores.

Software de Alquiler de vehículos Java

Base de datos
En el archivo BD.sql se encuentran los scripts de creacion de la base de datos.

El proyecto esta en java, con JDK17 y la libreria mysql-connector-j-8.2.0.jar

#Problema:

Se debe desarrollar una aplicación que permita calcular los precios de alquiler de una empresa de alquiler de vehículos.

Cada vehículo se identifica por medio de la matricula.

La empresa alquila distintos tipos de vehículos, tanto para transporte de personas como de carga. En la actualidad los vehículos alquilados por la empresa son: coches, microbuses, furgonetas de carga y camiones.

El precio de alquiler de cualquier vehículo tiene un componente base que depende de los días de alquiler a razón de $50 COP x día.

En el caso de alquiler de un coche, al precio base se le suma la cantidad de $1.5 COP x día.

El precio de alquiler de microbuses es igual que el de los coches, salvo que se le añade una cantidad de $2 COP, independientemente de los días de alquiler.

El precio de los vehículos de carga es el precio base más $20 COP x PMA (Peso Máximo Autorizado en toneladas).

Además, en el caso de los camiones, al precio se suma un fijo de $40 COP, independientemente de los días de alquiler.

La interacción del empleado con la aplicación deberá realizarse a través de una interfaz gráfica basada en menú.

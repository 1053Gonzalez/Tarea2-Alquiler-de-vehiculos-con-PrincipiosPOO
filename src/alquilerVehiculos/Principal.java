package alquilerVehiculos;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //mostramos el menu
        System.out.println("\n___===== Bienvenido a Alquiler de vehiculos AlquilaYA =====___\n");

        while (true) {
            System.out.println("Seleccione una opcion:\n");
            System.out.println("1. Alquilar Vehiculo");
            System.out.println("2. Gestionar Vehiculos");
            System.out.println("0. Salir\n");

            try {
                System.out.print("Seleccione la opción deseada (0-2): ");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 0:
                        System.out.println("Gracias por usar nuestro servicio. ¡Hasta luego!");
                        System.exit(0);
                    case 1:
                        System.out.println("");
                        AlquilarVehiculo.alquilarVehiculo();
                        break;
                    case 2:

                        System.out.println("\n___===== Menu de Gestion de vehiculos =====___\n");

                        while (true) {
                            System.out.println("Seleccione una opcion:\n");
                            System.out.println("1. Listar vehiculos");
                            System.out.println("2. Agregar vehiculos");
                            System.out.println("3. Editar estado del vehiculo");
                            System.out.println("4. Eliminar Vehiculo");
                            System.out.println("0. Regresar\n");

                            try {
                                System.out.print("Seleccione la opción deseada (0-4): ");
                                int opcion2 = scanner.nextInt();

                                switch (opcion2) {
                                    case 0://Regresamos al menu inicial
                                        main(args);
                                        System.out.println("Regresando al menu anterior\n");
                                        break;
                                    case 1://Usamos la clase para listar vehiculos
                                        listarVehiculos();
                                        break;
                                    case 2://Usamos la clase para agregar vehiculos
                                        agregarVehiculo();
                                        break;
                                    case 3://Usamos la clase para listar vehiculos y para editar el estado de uno de estos
                                        listarVehiculos();
                                        System.out.println("");
                                        editarEstadoVehiculo();
                                        break;
                                    case 4://Usamos la clase para listar vehiculos y para eliminar uno de estos
                                        listarVehiculos();
                                        System.out.println("");
                                        eliminarVehiculo();
                                        break;
                                    default://en caso de una opcion no valida
                                        System.out.println("Opción no válida. Por favor, seleccione 0 o 4.");
                                }
                            } catch (InputMismatchException e) {//control de excepciones del menu de gestion de vehiculos
                                System.out.println("Error: Ingrese un número entero válido.");
                                scanner.nextLine(); // Limpiar el buffer del scanner
                            }
                        }
                }
            } catch (InputMismatchException e) {//control de excepciones del menu principal
                System.out.println("Error: Ingrese un número entero válido.");
                scanner.nextLine(); // Limpiar el buffer del scanner
            }
        }
    }

    //metodo para listar los vehiculos recuperados de la clase conexionBD en un formato de lista
    public static void listarVehiculos() {
        List<Vehiculo> vehiculos = ConexionBD.listarVehiculos();

        if (vehiculos.isEmpty()) {//si la lista de vehiculos esta vacia mostramos el mensaje
            System.out.println("No hay vehiculos registrados.");
        } else {//si hay elementos en la lista los mostramos con un formato adecuado para su facil lectura
            System.out.println("\nLista de Vehiculos:\n");
            System.out.printf("%-15s%-25s%-15s%-15s%-15s%-15s\n", "Num Placa", "Tipo", "Marca", "Modelo", "Estado", "pma\n");
            for (Vehiculo vehiculo : vehiculos) {
                System.out.printf("%-15s%-25s%-15s%-15s%-15s%-15s\n",
                        vehiculo.getNumeroPlaca(), vehiculo.getTipo(), vehiculo.getMarca(),
                        vehiculo.getModelo(), vehiculo.getEstado(), vehiculo.getpma());
            }
            System.out.println("");
        }
    }
    //metodo para agregar 1 vehiculo
    private static void agregarVehiculo() {
        //intanciamos variable para manejar el pma
        int pma = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nAgregar Nuevo Vehículo");
        System.out.print("Ingrese el número de placa: ");
        String numeroPlaca = scanner.nextLine();

        // Verificamos si el vehículo ya existe usando el numero de placa
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            if (ConexionBD.existeVehiculo(conexion, numeroPlaca)) {
                System.out.println("¡Error! Ya existe un vehículo con esa placa.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del vehículo: " + e.getMessage());
            return;
        }
        //mostramos menu de seleccion del tipo de vehiculo hasta que se seleccione una opcion valida
        String tipo = "";

        do {
            System.out.print("\nSeleccione el tipo de vehiculo:\n1. coche\n2. microbus\n3. furgoneta de carga\n4. camion \n0. Regresar\n");
            tipo = scanner.nextLine().toLowerCase();

            switch (tipo) {
                case "1", "coche" -> {
                    System.out.println("Ha seleccionado 'coche'\n");
                    tipo = "coche";
                }
                case "2", "microbus" -> {
                    System.out.println("Ha seleccionado 'microbus'\n");
                    tipo = "microbus";
                }
                case "3", "furgoneta de carga" -> {
                    System.out.println("Ha seleccionado 'furgoneta de carga'\n");
                    tipo = "furgoneta de carga";
                }
                case "4", "camion" -> {
                    System.out.println("Ha seleccionado 'camion'\n");
                    tipo = "camion";
                }
                case "0" -> {
                    System.out.println("Regresando al menu anterior\n");
                    return;
                }
                default ->
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.\n");
            }

        } while (!tipo.equals("coche") && !tipo.equals("microbus") && !tipo.equals("furgoneta de carga") && !tipo.equals("camion"));

        //Almacenamos los valores en las variables:
        System.out.print("Ingrese la marca del vehículo: ");
        String marca = scanner.nextLine();

        System.out.print("Ingrese el modelo del vehículo: ");
        String modelo = scanner.nextLine();
        
        //mostramos menu seleccionable para el estado del vehiculo hasta que se seleccione una opcion valida
        String estado = "";
        do {
            System.out.print("\nSeleccione el estado del vehiculo:\n1. disponible\n2. alquilado\n3. no disponible\n");
            estado = scanner.next().toLowerCase();

            switch (estado) {
                case "1":
                case "disponible":
                    System.out.println("El estado del vehículo ha sido cambiado a 'disponible'\n");
                    estado = "disponible";
                    break;
                case "2":
                case "alquilado":
                    System.out.println("El estado del vehículo ha sido cambiado a 'alquilado'\n");
                    estado = "alquilado";
                    break;
                case "3":
                case "no disponible":
                    System.out.println("El estado del vehículo ha sido cambiado a 'no disponible'\n");
                    estado = "no disponible";
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.\n");
                    break;
            }

        } while (!estado.equals(
                "disponible") && !estado.equals("alquilado") && !estado.equals("no disponible"));
        
        //condicional para almacenar el pma en caso de que el vehiculo sea furgoneta de carga o camion
        if (tipo.equals("furgoneta de carga")) {
            System.out.print("Ingrese la cantidad de toneladas que se cargaran al vehículo: ");
            pma = scanner.nextInt();

        } else if (tipo.equals("camion")) {
            System.out.print("Ingrese la cantidad de toneladas que se cargaran al vehículo: ");
            pma = scanner.nextInt();
        } else {

        }
        //enviamos los datos al metodo insertarvehiculo en la clase ConexionBD, quien los registrara en la base de datos
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            Vehiculo nuevoVehiculo = new Vehiculo(numeroPlaca, tipo, marca, modelo, estado, pma);
            ConexionBD.insertarVehiculo(conexion, nuevoVehiculo);
            System.out.println("Vehículo agregado correctamente.\n");
        } catch (SQLException e) {
            System.out.println("Error al agregar el vehículo: " + e.getMessage());
        }
    }
   
    //Metodo para agregar editar el estado de un vehiculo por placa
    private static void editarEstadoVehiculo() {
        Scanner scanner = new Scanner(System.in);
        
        //Solicitamos el numero de placa y lo almacenamos en la variable
        System.out.print("Ingrese el numero de placa del vehiculo: ");
        String numeroPlaca = scanner.next();

        //Verificamos si el vehículo existe
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            if (!ConexionBD.existeVehiculo(conexion, numeroPlaca)) {
                System.out.println("¡Error! No existe un vehículo con esa placa.\n");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del vehículo: " + e.getMessage());
            return;
        }

        String nuevoEstado = "";
        do {
            System.out.print("\nSeleccione el nuevo estado del vehiculo:\n1. disponible\n2. alquilado\n3. no disponible\n");
            nuevoEstado = scanner.next().toLowerCase();

            switch (nuevoEstado) {
                case "1":
                case "disponible":
                    System.out.println("El estado del vehículo ha sido cambiado a 'disponible'\n");
                    nuevoEstado = "disponible";
                    break;
                case "2":
                case "alquilado":
                    System.out.println("El estado del vehículo ha sido cambiado a 'alquilado'\n");
                    nuevoEstado = "alquilado";
                    break;
                case "3":
                case "no disponible":
                    System.out.println("El estado del vehículo ha sido cambiado a 'no disponible'\n");
                    nuevoEstado = "no disponible";
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.\n");
                    break;
            }

        } while (!nuevoEstado.equals("disponible") && !nuevoEstado.equals("alquilado") && !nuevoEstado.equals("no disponible"));

        try (Connection conexion = ConexionBD.obtenerConexion()) {
            ConexionBD.editarEstadoVehiculo(conexion, numeroPlaca, nuevoEstado);
            System.out.println("Estado del vehículo actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar el estado del vehículo: " + e.getMessage());
        }
    }

    private static void eliminarVehiculo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el numero de placa del vehiculo a eliminar: ");
        String numeroPlaca = scanner.next();

        // Verificar si el vehículo existe
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            if (!ConexionBD.existeVehiculo(conexion, numeroPlaca)) {
                System.out.println("¡Error! No existe un vehículo con esa placa.\n");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del vehículo: " + e.getMessage());
            return;
        }

        try (Connection conexion = ConexionBD.obtenerConexion()) {
            ConexionBD.eliminarVehiculo(conexion, numeroPlaca);
            System.out.println("Vehículo eliminado correctamente.\n");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el vehículo: " + e.getMessage());
        }
    }

}

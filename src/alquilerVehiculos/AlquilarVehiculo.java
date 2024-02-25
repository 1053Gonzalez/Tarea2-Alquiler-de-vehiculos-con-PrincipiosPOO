package alquilerVehiculos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

// Aplicamos el principio de Herencia y Polimorfismo (hereda de ConexionBD)

/*La clase AlquilarVehiculo hereda de la clase ConexionBD. 
Esto significa que AlquilarVehiculo tiene acceso a los métodos y propiedades de ConexionBD y puede extender
su funcionalidad. La herencia facilita la reutilización de código y la creación de una jerarquía de clases.*/

public class AlquilarVehiculo extends ConexionBD {

    public static void alquilarVehiculo() {

        Date diaActual = new Date();
        Scanner scanner = new Scanner(System.in);

        // Mostrar tipos de vehículos disponibles
        System.out.print("Ingrese el número correspondiente al tipo de vehículo: ");

        String tipoSeleccionado = "";

        //Repetimos el menu seleccionable hasta que se elija una opcion valida.
        do {
            System.out.print("\nSeleccione el tipo de vehiculo:\n1. coche\n2. microbus\n3. furgoneta de carga\n4. camion \n0. Regresar\n");
            tipoSeleccionado = scanner.nextLine().toLowerCase();

            switch (tipoSeleccionado) {
                case "1", "coche" -> {
                    System.out.println("Ha seleccionado 'coche'\n");
                    tipoSeleccionado = "coche";
                }
                case "2", "microbus" -> {
                    System.out.println("Ha seleccionado 'microbus'\n");
                    tipoSeleccionado = "microbus";
                }
                case "3", "furgoneta de carga" -> {
                    System.out.println("Ha seleccionado 'furgoneta de carga'\n");
                    tipoSeleccionado = "furgoneta de carga";
                }
                case "4", "camion" -> {
                    System.out.println("Ha seleccionado 'camion'\n");
                    tipoSeleccionado = "camion";
                }
                case "0" -> {
                    System.out.println("Regresando al menu anterior\n");
                    return;
                }
                default ->
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.\n");
            }

        } while (!tipoSeleccionado.equals(
                "coche") && !tipoSeleccionado.equals("microbus") && !tipoSeleccionado.equals("furgoneta de carga") && !tipoSeleccionado.equals("camion"));

        // Obtenetemos la lista de vehículos segun el tipo seleccionado
        List<Vehiculo> vehiculos = obtenerVehiculosPorTipo(tipoSeleccionado);

        // Mostramos la lista de vehículos disponibles segun el tipo
        System.out.println("\nLista de Vehículos Disponibles:\n");
        System.out.printf("%-15s%-25s%-15s%-15s%-15s%-15s\n", "Num Placa", "Tipo", "Marca", "Modelo", "Estado", "pma\n");
        for (Vehiculo vehiculo : vehiculos) {
            System.out.printf("%-15s%-25s%-15s%-15s%-15s%-15s\n",
                    vehiculo.getNumeroPlaca(), vehiculo.getTipo(), vehiculo.getMarca(),
                    vehiculo.getModelo(), vehiculo.getEstado(), vehiculo.getpma());
        }

        // Solicitamos el número de placa del vehículo a alquilar
        System.out.print("\nIngrese el número de placa del vehículo que desea alquilar: ");
        String numeroPlaca = scanner.next();

        // Verificamos si el vehículo está disponible
        if (!vehiculoDisponible(numeroPlaca)) {
            System.out.println("¡Error! El vehículo seleccionado no está disponible para alquilar.");
            return;
        }

        // Ingresamos los datos del cliente y alquiler
        System.out.print("Ingrese el nombre del cliente: ");
        String nombreCliente = scanner.next();

        System.out.print("Ingrese el documento del cliente: ");
        String documentoCliente = scanner.next();

        System.out.print("Ingrese la cantidad de días del alquiler: ");
        int cantidadDias = scanner.nextInt();
        
        // Calculamos la fecha de entrega (día actual + cantidad de días)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(diaActual);
        calendar.add(Calendar.DAY_OF_YEAR, cantidadDias);
        java.sql.Date fechaEntrega = new java.sql.Date(calendar.getTimeInMillis());

        //System.out.println("Fecha de entrega: " + fechaEntrega);
        // Calculamos el total a pagar (precio varía según el tipo de vehículo)
        double totalPagar = calcularTotalPagar(numeroPlaca, cantidadDias);

        // Almacenamos los datos del alquiler en la base de datos
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            ConexionBD.registrarAlquiler(conexion, numeroPlaca, nombreCliente, documentoCliente, cantidadDias, totalPagar, fechaEntrega);
            ConexionBD.editarEstadoVehiculo(conexion, numeroPlaca, "alquilado");
            System.out.println("Alquiler registrado correctamente.\n\n\n");
        } catch (SQLException e) {
            System.out.println("Error al registrar el alquiler: " + e.getMessage());
        }
    }

    //listamos nuevamente los vehiculos por tipo para validar que quede alquilado
    //Aplicamos principio de Modularidad (métodos más pequeños y especializados)
    
    /*el principio de modularidad al dividir la lógica en métodos más pequeños y especializados. 
    Cada método realiza una tarea específica, lo que facilita la comprensión del código y su mantenimiento.*/
    
    private static List<Vehiculo> obtenerVehiculosPorTipo(String tipo) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            return ConexionBD.obtenerVehiculosPorTipo(conexion, tipo);
        } catch (SQLException e) {
            System.out.println("Error al obtener vehículos por tipo: " + e.getMessage());
            return List.of();  // Retorna una lista vacía en caso de error
        }
    }

    private static boolean vehiculoDisponible(String numeroPlaca) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            return ConexionBD.estadoVehiculoDisponible(conexion, numeroPlaca);
        } catch (SQLException e) {
            System.out.println("Error al verificar disponibilidad del vehículo: " + e.getMessage());
            return false;
        }
    }

    private static double calcularTotalPagar(String numeroPlaca, int cantidadDias) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            return ConexionBD.calcularPrecioAlquiler(conexion, numeroPlaca, cantidadDias);
        } catch (SQLException e) {
            System.out.println("Error al calcular el total a pagar: " + e.getMessage());
            return 0.0;
        }
    }
}

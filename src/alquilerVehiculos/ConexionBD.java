package alquilerVehiculos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConexionBD {
    //Establecemos las constantes para los datos de conexion a la base de datos y la tabla
    //Aplicamos el principio de Encapsulamiento y Ocultamiento (constantes privadas)***

    private static final String URL = "jdbc:mysql://localhost:3306/alquiler_vehiculos";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    
    //Metodo para validar la conexión a la base de datos
    //Método para obtener la conexión (Ocultamiento de detalles de conexión)**
    /*(Ocultamiento de detalles de conexión): El objetivo principal de este método es ocultar los 
    detalles específicos de cómo se establece la conexión a la base de datos. 
    Los detalles, como la URL, el usuario y la contraseña, están encapsulados en la clase y no son expuestos externamente. 
    Esto sigue el principio de ocultamiento (o encapsulamiento) y mejora la modularidad del código, 
    ya que los cambios en la conexión no afectarán a otras partes del programa.*/
    
    public static Connection obtenerConexion() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo establecer la conexión a la base de datos.");
        }
    }
    
    //Metodo para validar si existe un vehiculo por numero de placa
    public static boolean existeVehiculo(Connection conexion, String numeroPlaca) throws SQLException {
        String consulta = "SELECT * FROM vehiculos WHERE numero_placa = ?";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, numeroPlaca);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
    
    //Metodo para insertar un vehiculo.
    public static void insertarVehiculo(Connection conexion, Vehiculo vehiculo) throws SQLException {
        String consulta = "INSERT INTO vehiculos (numero_placa, tipo, marca, modelo, estado, pma) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, vehiculo.getNumeroPlaca());
            statement.setString(2, vehiculo.getTipo());
            statement.setString(3, vehiculo.getMarca());
            statement.setString(4, vehiculo.getModelo());
            statement.setString(5, vehiculo.getEstado());
            statement.setInt(6, vehiculo.getpma());
            statement.executeUpdate();
        }
    }
    
    // Aplicamos el principio de Modularidad (métodos más pequeños y especializados)
    //Metodo para listar todos los vehiculos.
    public static List<Vehiculo> listarVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<>();

        try (Connection conexion = obtenerConexion()) {
            String consulta = "SELECT * FROM vehiculos";
            try (PreparedStatement statement = conexion.prepareStatement(consulta); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String numeroPlaca = resultSet.getString("numero_placa");
                    String tipo = resultSet.getString("tipo");
                    String marca = resultSet.getString("marca");
                    String modelo = resultSet.getString("modelo");
                    String estado = resultSet.getString("estado");
                    int pma = resultSet.getInt("pma");

                    Vehiculo vehiculo = new Vehiculo(numeroPlaca, tipo, marca, modelo, estado, pma);
                    vehiculos.add(vehiculo);

                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de vehiculos: " + e.getMessage());
        }

        return vehiculos;
    }

    //Metodo para editar el estado de un vehiculo.
    public static void editarEstadoVehiculo(Connection conexion, String numeroPlaca, String nuevoEstado) throws SQLException {
        String consulta = "UPDATE vehiculos SET estado = ? WHERE numero_placa = ?";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, nuevoEstado);
            statement.setString(2, numeroPlaca);
            statement.executeUpdate();
        }
    }

    //Metodo para eliminar un vehiculo.
    public static void eliminarVehiculo(Connection conexion, String numeroPlaca) throws SQLException {
        String consulta = "DELETE FROM vehiculos WHERE numero_placa = ?";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, numeroPlaca);
            statement.executeUpdate();
        }
    }

    //Metodo para validar si el estado de un vehiculo es "disponible".
    public static boolean estadoVehiculoDisponible(Connection conexion, String numeroPlaca) throws SQLException {
        String consulta = "SELECT estado FROM vehiculos WHERE numero_placa = ?";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, numeroPlaca);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String estado = resultSet.getString("estado");
                    return estado.equalsIgnoreCase("disponible");
                } else {
                    throw new SQLException("No se encontró el estado del vehículo para la placa proporcionada.");
                }
            }
        }
    }

    //Metodo para obtener los vehiculos de un mismo tipo.
    public static List<Vehiculo> obtenerVehiculosPorTipo(Connection conexion, String tipo) throws SQLException {
        String consulta = "SELECT * FROM vehiculos WHERE tipo = ?";
        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, tipo);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Vehiculo> vehiculos = new ArrayList<>();
                while (resultSet.next()) {
                    String numeroPlaca = resultSet.getString("numero_placa");
                    String tipoVehiculo = resultSet.getString("tipo");
                    String marca = resultSet.getString("marca");
                    String modelo = resultSet.getString("modelo");
                    String estado = resultSet.getString("estado");
                    int pma = resultSet.getInt("pma");

                    vehiculos.add(new Vehiculo(numeroPlaca, tipoVehiculo, marca, modelo, estado, pma));
                }
                return vehiculos;
            }
        }
    }
    
    //Metodo para Calcular el precio de alquiler de un vehiculo.
    public static double calcularPrecioAlquiler(Connection conexion, String numeroPlaca, int cantidadDias) throws SQLException {
        String consulta = "SELECT tipo, pma FROM vehiculos WHERE numero_placa = ?";

        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
            statement.setString(1, numeroPlaca);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String tipo = resultSet.getString("tipo");
                    double pma = resultSet.getDouble("pma");

                    // Constantes
                    final double PRECIO_BASE = 50 * cantidadDias;
                    final double ADICIONAL_COCHES = 1.5 * cantidadDias;
                    final double ADICIONAL_MICROBUSES = 2;
                    final double ADICIONAL_CARGA = 20 * pma;
                    final double ADICIONAL_CAMIONES = ADICIONAL_CARGA + 40;

                    switch (tipo.toLowerCase()) {
                        case "coche":
                            return PRECIO_BASE + ADICIONAL_COCHES;
                        case "microbus":
                            return PRECIO_BASE + ADICIONAL_MICROBUSES;
                        case "furgoneta de carga":
                            return PRECIO_BASE + ADICIONAL_CARGA;
                        case "camion":
                            return PRECIO_BASE + ADICIONAL_CAMIONES;
                        default:
                            // Tipo de vehículo no reconocido
                            throw new SQLException("Tipo de vehículo no reconocido para calcular el precio de alquiler.");
                    }
                } else {
                    throw new SQLException("No se encontró el vehículo para calcular el precio de alquiler.");
                }
            }
        }
    }
    
    //Metodo para registrar un alquiler de un vehiculo.
    public static void registrarAlquiler(Connection conexion, String numeroPlaca, String nombreCliente,
            String documentoCliente, int cantidadDias, double totalPagar, Date fechaEntrega) throws SQLException {
        String insertarAlquiler = "INSERT INTO alquileres (numero_placa, nombre_cliente, documento_cliente, "
                + "cantidad_dias, total_pagar, fecha_entrega) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conexion.prepareStatement(insertarAlquiler)) {
            statement.setString(1, numeroPlaca);
            statement.setString(2, nombreCliente);
            statement.setString(3, documentoCliente);
            statement.setInt(4, cantidadDias);
            statement.setDouble(5, totalPagar);
            statement.setDate(6, (java.sql.Date) fechaEntrega);
            
            //Mostrar Resumen
            System.out.println("\nResumen del Alquiler\n\n");
            System.out.println("Número de Placa: \t" + numeroPlaca);
            System.out.println("Nombre del Cliente: \t" + nombreCliente);
            System.out.println("Fecha de Entrega: \t" + fechaEntrega);
            System.out.println("Cantidad de Días: \t" + cantidadDias);
            System.out.println("Total a Pagar: \t\t" + totalPagar + " COP\n");

            statement.executeUpdate();
        }
    }

}

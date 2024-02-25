package alquilerVehiculos;

//Aplicamos el principio de Abstracción (representación de un vehículo)*

/*"Principio de Abstracción (representación de un vehículo)" sugiere que la clase Vehiculo
es una abstracción que representa un vehículo. La abstracción es la simplificación de la realidad, 
centrando la atención en los aspectos relevantes y dejando de lado los detalles innecesarios. 
En este caso, Vehiculo encapsula las características esenciales de un vehículo de la vida real.*/

public class Vehiculo {
    
    // Principio de Encapsulamiento (atributos privados)*
    /*La clase Vehiculo utiliza el principio de encapsulamiento al declarar sus atributos 
    (numeroPlaca, tipo, marca, modelo, estado, pma) como privados. 
    Esto significa que estos atributos no son accesibles directamente desde fuera de la clase.*/
    
    private String numeroPlaca;
    private String tipo;
    private String marca;
    private String modelo;
    private String estado;
    private int pma;
    
    //clase constructora de objetos vehiculo.

    public Vehiculo(String numeroPlaca, String tipo, String marca, String modelo, String estado, int pma) {
        this.numeroPlaca = numeroPlaca;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
        this.pma = pma;
    }

    //Principio de Encapsulamiento (métodos getter y setter)
    /*Se proporcionan métodos públicos (getters y setters) para acceder y modificar estos atributos de manera controlada.
    Esto permite que la clase controle cómo se accede y se modifica su estado interno.*/

    
    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getpma() {
        return pma;
    }

    public void setpma(int pma) {
        this.pma = pma;
    }
}

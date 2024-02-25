--Crear la base de datos:

CREATE DATABASE `alquiler_vehiculos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;


--Crear Tabla vehiculos

CREATE TABLE `vehiculos` (
  `numero_placa` varchar(20) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `estado` varchar(20) NOT NULL,
  `pma` double DEFAULT NULL,
  PRIMARY KEY (`numero_placa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--Crear Tabla alquileres
CREATE TABLE alquileres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_placa VARCHAR(15) NOT NULL,
    nombre_cliente VARCHAR(100) NOT NULL,
    documento_cliente VARCHAR(20) NOT NULL,
    cantidad_dias INT NOT NULL,
    fecha_entrega DATE NOT NULL,
    total_pagar DOUBLE NOT NULL,
    FOREIGN KEY (numero_placa) REFERENCES vehiculos(numero_placa)
);


-- Insertar Microbuses
INSERT INTO vehiculos (numero_placa, tipo, marca, modelo, estado, pma) VALUES
("MIC001", "Microbus", "Toyota", "2020", "disponible", 0),
("MIC002", "Microbus", "Ford", "2018", "disponible", 0),
("MIC003", "Microbus", "Volkswagen", "2019", "disponible", 0),
("MIC004", "Microbus", "Mercedes-Benz", "2017", "disponible", 0),
("MIC005", "Microbus", "Hyundai", "2022", "disponible", 0);

-- Insertar Furgonetas de carga
INSERT INTO vehiculos (numero_placa, tipo, marca, modelo, estado, pma) VALUES
("FUR001", "Furgoneta de carga", "Renault", "2015", "disponible", 1.5),
("FUR002", "Furgoneta de carga", "Iveco", "2019", "disponible", 2.0),
("FUR003", "Furgoneta de carga", "Peugeot", "2016", "disponible", 1.8),
("FUR004", "Furgoneta de carga", "Fiat", "2017", "disponible", 1.7),
("FUR005", "Furgoneta de carga", "Citroen", "2018", "disponible", 1.6);

-- Insertar Camiones
INSERT INTO vehiculos (numero_placa, tipo, marca, modelo, estado, pma) VALUES
("CAM001", "Camion", "Volvo", "2015", "disponible", 3.5),
("CAM002", "Camion", "Scania", "2018", "disponible", 4.0),
("CAM003", "Camion", "Mercedes-Benz", "2017", "disponible", 3.8),
("CAM004", "Camion", "MAN", "2016", "disponible", 3.2),
("CAM005", "Camion", "DAF", "2019", "disponible", 3.0);
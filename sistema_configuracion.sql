CREATE DATABASE sistema_configuracion;

USE sistema_configuracion;

CREATE TABLE configuracion(
id INT PRIMARY KEY AUTO_INCREMENT,
nombre_empresa VARCHAR(100) NOT NULL,
ruta_logo TEXT NOT NULL,
idioma VARCHAR(50) NOT NULL,
zona_horaria VARCHAR(100) NOT NULL,
vencimiento_tickets INT NOT NULL,
nivel1 VARCHAR(50),
nivel2 VARCHAR(50),
nivel3 VARCHAR(50));
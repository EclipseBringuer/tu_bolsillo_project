-- Uso de la bd
USE tu_bolsillo_db;

-- Tabla que representa roles de usuario
CREATE TABLE `role`(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(120) NOT NULL UNIQUE
);

-- Tabla que representa los usuario del sistema
CREATE TABLE `user`(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(150) NOT NULL,
email VARCHAR(150) NOT NULL UNIQUE,
`password` CHAR(60) NOT NULL, -- Contraseña hasheada
created_at TIMESTAMP NOT NULL,
updated_at TIMESTAMP NOT NULL,
deleted_at TIMESTAMP NULL
);

-- Tabla que relaciona roles con usuarios
CREATE TABLE user_role(
role_id BIGINT,
user_id BIGINT,
FOREIGN KEY (role_id) REFERENCES `role`(id) ON DELETE CASCADE,
FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE,
PRIMARY KEY (role_id, user_id)
);

-- Tabla de refresh token basados en UUID
CREATE TABLE refresh_token(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_id BIGINT NOT NULL,
token CHAR(36) NOT NULL UNIQUE,
expiry_date TIMESTAMP NOT NULL,
FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE
);

-- Tabla de categorías de transacciones
CREATE TABLE category(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_id BIGINT NOT NULL,
`name` VARCHAR(100) NOT NULL,
`type` ENUM('INCOME', 'EXPENSE') NOT NULL,
UNIQUE (user_id, `name`),
FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE
);

-- Tabla de transacciones
CREATE TABLE `transaction`(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_id BIGINT NOT NULL,
category_id BIGINT NOT NULL,
amount DECIMAL(10,2) NOT NULL,
`date` DATE NOT NULL,
`description` VARCHAR(255),
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT chk_amount CHECK(amount > 0),
FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE,
FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

-- Índices
CREATE INDEX idx_transaction_user_id ON `transaction`(user_id);
CREATE INDEX idx_transaction_category_id ON `transaction`(category_id);
CREATE INDEX idx_transaction_date ON `transaction`(`date`);

-- Datos iniciales para roles
INSERT INTO `role`(`name`) VALUES ("USER"), ("ADMIN");

-- Inserción de usuario administrador
INSERT INTO `user`(first_name, last_name, email, `password`, created_at, updated_at) VALUES
("Gabriel", "Rincón López", "gabrielrl2004@gmail.com", "$2a$12$SoHnhXiNQWRtjb/KGf90VubROVrW3T/jPBoILleD6oQ0PWxiASRLS", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserción de los roles en los usuarios administradores
INSERT INTO user_role(role_id, user_id) VALUES 
(1, 1), (2, 1);
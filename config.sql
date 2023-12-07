CREATE TABLE IF NOT EXISTS tipo_clientes (     id_tipo INT AUTO_INCREMENT,     nome_tipo VARCHAR(255) NOT NULL,     PRIMARY KEY (id_tipo) );
CREATE TABLE IF NOT EXISTS grupo_clientes (     id_grupo INT AUTO_INCREMENT,     nome_grupo VARCHAR(255) NOT NULL,     cpf_raiz VARCHAR(255),     tipo_grupo TINYINT NOT NULL,     PRIMARY KEY (id_grupo) );
CREATE TABLE IF NOT EXISTS clientes (     id_cliente INT AUTO_INCREMENT,     nome_cliente VARCHAR(255) NOT NULL,     cpf_cliente VARCHAR(255),     tipo_cliente TINYINT NOT NULL,     id_grupo_cliente INT NOT NULL,     PRIMARY KEY (id_cliente) );
CREATE TABLE IF NOT EXISTS tipo_grupos (     id_tipo_grupo INT AUTO_INCREMENT,     nome VARCHAR(255) NOT NULL,     PRIMARY KEY (id_tipo_grupo) );
CREATE TABLE usuarios (id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, salt VARCHAR(255) NOT NULL);

CREATE DATABASE IF NOT EXISTS Rubrica;
use Rubrica;

CREATE TABLE persone (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cognome VARCHAR(100),
    indirizzo VARCHAR(200),
    telefono VARCHAR(30),
    eta INT
);

CREATE TABLE utenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL
);

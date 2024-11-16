CREATE DATABASE IF NOT EXISTS colegioHorizonte;
USE colegioHorizonte;

CREATE TABLE alumno (
	dni VARCHAR(8) PRIMARY KEY,
    nombreYApellido VARCHAR(255),
    curso VARCHAR(15)
);

CREATE TABLE cupon (
	idCupon VARCHAR(12) PRIMARY KEY,
    dniAlumno VARCHAR(8),
	idPago INT,
    FOREIGN KEY (dniAlumno) REFERENCES alumno(dni)
    ON DELETE CASCADE
);

INSERT INTO alumno(dni, nombreYApellido, curso)
VALUES
	('12345678', 'Francisco Ruiz', 'Primero'),
    ('23456789', 'Martin Alegre', 'Segundo'),
    ('34567890', 'Federico Peretti', 'Tercero'),
    ('45678901', 'Mark Hintermeister', 'Cuarto'),
    ('56789012', 'Grusha Kharbanda', 'Quinto');

INSERT INTO cupon (idCupon, dniAlumno, idPago)
VALUES
	('OCT001', '12345678', NULL),
    ('OCT002', '23456789', 00000001),
    ('OCT003', '34567890', 00000002),
    ('OCT004', '45678901', NULL),
    ('OCT005', '56789012', 00000003);

-- Consulta para validar si un alumno se encuentra ya cargado en el sistema
SELECT *
FROM alumno
WHERE dni = '12345678';

-- Consulta para identificar si un pago específico ya fue realizado
SELECT *
FROM cupon
WHERE idCupon = 'OCT001';


-- Consulta para identificar todos los alumnos con deudas.
SELECT 
	alumno.nombreYApellido AS 'Alumno',
    cupon.idCupon AS 'Cupon Adeudado'
FROM
	alumno
INNER JOIN 
	cupon
ON
	alumno.dni = cupon.dniAlumno
WHERE
	idPago IS NULL;


-- Consulta para identificar si un alumno específico posee deudas
SELECT 
	alumno.nombreYApellido AS 'Alumno',
    cupon.idCupon AS 'Cupon Adeudado'
FROM
	alumno
INNER JOIN 
	cupon
ON
	alumno.dni = cupon.dniAlumno
WHERE
	idPago IS NULL
AND
	dni = '12345678';  

-- Se borran los datos de las tablas y se valida
DELETE FROM alumno
WHERE dni = '12345678';
SELECT * FROM alumno
WHERE dni = '12345678';

DELETE FROM cupon
WHERE idCupon = 'OCT002';
SELECT * FROM cupon
WHERE idCupon = 'OCT002';

SET FOREIGN_KEY_CHECKS=1;
-- DROP TABLE Date_cadre_didactice;
-- DROP TABLE Disciplina_de_studii;
-- DROP TABLE date_studenti;
--
-- DELETE FROM Date_cadre_didactice;
-- DELETE FROM Disciplina_de_studii;
-- DELETE FROM date_studenti;

---- Table 1: Date cadre didactice
-- CREATE TABLE date_cadre_didactice (
--                                       ID INT PRIMARY KEY AUTO_INCREMENT,
--                                       nume VARCHAR(255) NOT NULL,
--                                       prenume VARCHAR(255) NOT NULL,
--                                       email VARCHAR(255) UNIQUE,
--                                       grad_didactic ENUM('asist', 'sef_lucr', 'conf', 'prof') DEFAULT NULL,
--                                       tip_asociere ENUM('titular', 'asociat', 'extern') NOT NULL,
--                                       afiliere VARCHAR(255) DEFAULT NULL
-- );
--
-- -- Table 2: Disciplina de studii
-- CREATE TABLE disciplina_de_studii (
--                                       COD VARCHAR(255) PRIMARY KEY,
--                                       ID_titular INT,
--                                       nume_disciplina VARCHAR(255) NOT NULL,
--                                       an_studiu INT NOT NULL,
--                                       tip_disciplina ENUM('impusa', 'optionala', 'liber_aleasa') NOT NULL,
--                                       categorie_disciplina ENUM('domeniu', 'specialitate', 'adiacenta') NOT NULL,
--                                       tip_examinare ENUM('examen', 'colocviu') NOT NULL,
--                                       FOREIGN KEY (ID_titular) REFERENCES Date_cadre_didactice(ID)
-- );
--
-- -- Table 3: Date studenti
-- CREATE TABLE date_studenti (
--                                ID INT PRIMARY KEY AUTO_INCREMENT,
--                                nume VARCHAR(255) NOT NULL,
--                                prenume VARCHAR(255) NOT NULL,
--                                email VARCHAR(255) UNIQUE,
--                                ciclu_studii ENUM('licenta', 'master') NOT NULL,
--                                an_studiu INT NOT NULL,
--                                grupa INT NOT NULL
-- );


-- DELETE FROM utilizatori;
-- DROP TABLE utilizatori;

-- CREATE TABLE utilizatori (
--                              id INT AUTO_INCREMENT PRIMARY KEY,
--                              email VARCHAR(255) UNIQUE NOT NULL,
--                              parola VARCHAR(255) NOT NULL,
--                              rol ENUM('ADMIN', 'PROFESOR', 'STUDENT') NOT NULL
-- );

-- INSERT INTO utilizatori (email, parola, rol)
-- VALUES
--     ('admin@example.com', '$2a$10$WppTo4XLFCj974d9pBKH.OkksRzfoTbmb5VIaDJcFKYzGw1gRBJlC', 'ADMIN'),
--     ('prof1@example.com', '$2a$10$nYEYMM9H..zVms5cQ.UcV.A6kOV9xWCbWkWex01OGKNCAXFqLNjbu', 'PROFESOR'),
--     ('prof2@example.com', '$2a$10$ubwH59UlTYt3l0XQnhpaz.L3Ao65vWBZkLDF/pjXCvLF8iNrN2aie', 'PROFESOR'),
--     ('student1@example.com', '$2a$10$3wI/G8Gf0AQdTkjuvR/z5.6riUtR7nOHvhm8vy1kctVltdLlGzdhS', 'STUDENT'),
--     ('student2@example.com', '$2a$10$30eM4gT4dqsZnocJ.j3lteuImq2qDg1RkLIZv/RQapxGy/bFxnJGm', 'STUDENT');

-- Data for date_cadre_didactice table
-- INSERT INTO date_cadre_didactice (nume, prenume, email, grad_didactic, tip_asociere, afiliere) VALUES
--                                                                                                    ('Popescu', 'Ion', 'ion.popescu@university.edu', 'prof', 'titular', 'Departamentul de Matematica'),
--                                                                                                    ('Ionescu', 'Maria', 'maria.ionescu@university.edu', 'conf', 'titular', 'Departamentul de Informatica'),
--                                                                                                    ('Dumitrescu', 'Gheorghe', 'gheorghe.dumitrescu@university.edu', 'sef_lucr', 'asociat', 'Departamentul de Istorie'),
--                                                                                                    ('Constantinescu', 'Andreea', 'andreea.constantinescu@university.edu', 'asist', 'extern', 'Departamentul de Economie'),
--                                                                                                    ('Radulescu', 'Mihai', 'mihai.radulescu@university.edu', 'prof', 'titular', 'Departamentul de Fizica');

-- Data for disciplina_de_studii table
-- INSERT INTO disciplina_de_studii (COD, ID_titular, nume_disciplina, an_studiu, tip_disciplina, categorie_disciplina, tip_examinare) VALUES
--                                                                                                                                         ('MAT101', 1, 'Matematica I', 1, 'impusa', 'domeniu', 'examen'),
--                                                                                                                                         ('CS201', 2, 'Introducere in Programare', 1, 'impusa', 'specialitate', 'colocviu'),
--                                                                                                                                         ('HIST301', 3, 'Istoria Romaniei', 3, 'optionala', 'adiacenta', 'examen'),
--                                                                                                                                         ('ECON401', 4, 'Macroeconomie', 4, 'liber_aleasa', 'specialitate', 'examen'),
--                                                                                                                                         ('PHYS150', 5, 'Fizica Generala', 1, 'impusa', 'domeniu', 'colocviu');

-- Data for date_studenti table
-- INSERT INTO date_studenti (nume, prenume, email, ciclu_studii, an_studiu, grupa) VALUES
--                                                                                      ('Popov', 'Mihai', 'mihai.popov@student.university.edu', 'LICENTA', 1, 101),
--                                                                                      ('Ionita', 'Ana', 'ana.ionita@student.university.edu', 'MASTER', 2, 201),
--                                                                                      ('Diaconu', 'Andrei', 'andrei.diaconu@student.university.edu', 'LICENTA', 3, 301),
--                                                                                      ('Marinescu', 'Cristina', 'cristina.marinescu@student.university.edu', 'LICENTA', 2, 202),
--                                                                                      ('Vasile', 'Radu', 'radu.vasile@student.university.edu', 'MASTER', 1, 101);

# DROP IF EXIST & CREATE SCHEMA iQuad Academy
DROP SCHEMA IF EXISTS iQuad_Academy_TEST;
CREATE SCHEMA iQuad_Academy_TEST;

# SELECT SCHEMA iQuad Academy
USE iQuad_Academy_TEST;

# SET GLOBAL TIME ZONE
SET @@global.time_zone = '+00:00';

# CREATE DEPARTMENT TABLE
CREATE TABLE department
(
    depID   int AUTO_INCREMENT
        PRIMARY KEY,
    depName varchar(24) NOT NULL,
    depCode int         NOT NULL,
    CONSTRAINT department_depCode_uindex
        UNIQUE (depCode),
    CONSTRAINT department_depName_uindex
        UNIQUE (depName)
);


# INSERT DATA INTO DEPARTMENT
INSERT INTO department (depName, depCode)
VALUES ('PHYSICS', '1001'),
       ('CHEMISTRY', '1002'),
       ('BIOLOGY', '1003'),
       ('MATHEMATICS', '1004'),
       ('CSE', '1005');


# CREATE STUDENT TABLE
CREATE TABLE student
(
    studentID int AUTO_INCREMENT,
    firstName varchar(55) NOT NULL,
    lastName  varchar(55) NOT NULL,
    dob       date        NULL,
    depID     int         NOT NULL,
    CONSTRAINT student_pk
        PRIMARY KEY (studentID),
    CONSTRAINT student_department_depID_fk
        FOREIGN KEY (depID) REFERENCES department (depID)
);

# INSERT DATA INTO STUDENT
INSERT INTO student (firstName, lastName, dob, depID)
VALUES ('Nazmul', 'Hassan', '1999-01-02', 5),
       ('Jubaer', 'Ahmed', '1998-06-05', 4),
       ('Georgi', 'Facello', '1990-12-31', 2),
       ('Bezalel', 'Simmel', '1993-8-13', 1),
       ('Parto', 'Bamford', '1999-12-16', 1),
       ('Chirstian', 'Koblick', '1980-10-30', 3),
       ('Kyoichi', 'Maliniak', '2000-12-1', 5),
       ('Anneke', 'Preusig', '2000-01-01', 4),
       ('Tzvetan', 'Zielinski', '2003-03-25', 4),
       ('Saniya', 'Kalloufi', '1993-05-16', 3),
       ('Sumant', 'Peac', '1997-12-05', 2),
       ('Duangkaew', 'Piveteau', '1986-12-12', 1);

# CREATE TEACHERS TABLE
CREATE TABLE teachers
(
    teachersID int AUTO_INCREMENT,
    firstName  varchar(55) NOT NULL,
    lastName   varchar(55) NOT NULL,
    CONSTRAINT teachers_pk
        PRIMARY KEY (teachersID)
);

# INSERT DATA INTO TEACHERS
INSERT INTO teachers (firstName, lastName)
VALUES ('Jeson', 'K'),
       ('Alon', 'G'),
       ('Aaron', 'K'),
       ('Fahima', 'G');

# CREATE MODULE TABLE
CREATE TABLE module
(
    modID   int AUTO_INCREMENT,
    modName varchar(55) NOT NULL,
    modCode int         NOT NULL,
    depID   int         NOT NULL,
    CONSTRAINT module_pk
        PRIMARY KEY (modID),
    CONSTRAINT module_department_depID_fk
        FOREIGN KEY (depID) REFERENCES department (depID)
);

# INSERT DATA INTO MODULE
INSERT INTO module (modName, modCode, depID)
VALUES ('PHYSICS 1', '101', 1),
       ('PHYSICS 2', '102', 1),
       ('PHYSICS 3', '103', 1),
       ('PHYSICS 4', '104', 1),
       ('CHEMISTRY 1', '201', 2),
       ('CHEMISTRY 2', '202', 2),
       ('CHEMISTRY 3', '203', 2),
       ('CHEMISTRY 4', '204', 2),
       ('BIOLOGY 1', '301', 3),
       ('BIOLOGY 2', '302', 3),
       ('BIOLOGY 3', '303', 3),
       ('MATHEMATICS 1', '401', 4),
       ('MATHEMATICS 2', '402', 4),
       ('MATHEMATICS 3', '403', 4),
       ('CSE 1', '501', 5),
       ('CSE 2', '502', 5),
       ('CSE 3', '503', 5);

# CREATE CLASSS TABLE
CREATE TABLE classs
(
    classsID   int AUTO_INCREMENT,
    teachersID int NOT NULL,
    modID      int NOT NULL,
    CONSTRAINT classs_pk
        PRIMARY KEY (classsID),
    CONSTRAINT classs_module_modID_fk
        FOREIGN KEY (modID) REFERENCES iquad_academy_test.module (modID),
    CONSTRAINT classs_teachers_teachersID_fk
        FOREIGN KEY (teachersID) REFERENCES iquad_academy_test.teachers (teachersID)
);

# INSERT DATA INTO CLASSES
INSERT INTO classs (teachersID, modID)
VALUES (1, 5),
       (1, 2),
       (1, 1),
       (2, 1),
       (2, 3),
       (2, 4),
       (2, 5),
       (3, 5),
       (3, 4),
       (3, 2),
       (3, 1),
       (4, 1),
       (4, 5),
       (4, 3),
       (4, 4);


# CREATE ROSTER TABLE
    CREATE TABLE roster
(
    rosterID  INT AUTO_INCREMENT,
    classsID  INT NOT NULL,
    studentID INT NOT NULL,
    CONSTRAINT roster_pk
        PRIMARY KEY (rosterID),
    CONSTRAINT roster_classs_classsID_fk
        FOREIGN KEY (classsID) REFERENCES classs (classsID),
    CONSTRAINT roster_student_studentID_fk
        FOREIGN KEY (studentID) REFERENCES student (studentID)
);

# INSERT DATA INTO ROSTER
INSERT INTO roster (classsID, studentID)
VALUES (1, 2),
       (1, 3),
       (1, 6),
       (1, 8),
       (1, 11),
       (1, 12),
       (2, 5),
       (2, 6),
       (2, 8),
       (2, 11),
       (2, 12),
       (2, 3),
       (3, 3),
       (3, 1),
       (3, 2),
       (3, 8),
       (3, 9),
       (3, 7),
       (4, 7),
       (4, 6),
       (4, 4),
       (4, 3),
       (4, 9),
       (4, 12),
       (5, 12),
       (5, 11),
       (5, 10),
       (5, 9),
       (5, 8),
       (6, 7),
       (6, 6),
       (6, 5),
       (6, 4),
       (7, 4),
       (7, 1),
       (7, 5),
       (8, 4),
       (8, 1),
       (8, 3),
       (9, 3),
       (9, 9),
       (9, 6),
       (9, 4),
       (10, 1),
       (10, 10),
       (10, 11),
       (10, 12),
       (11, 1),
       (11, 12),
       (11, 11),
       (12, 11),
       (12, 12),
       (12, 8),
       (12, 7),
       (13, 7),
       (13, 6),
       (13, 5),
       (13, 4),
       (14, 3),
       (14, 4),
       (14, 1),
       (14, 8),
       (15, 8),
       (15, 9),
       (15, 10),
       (15, 12),
       (15, 11);


# DROP IF EXIST & CREATE SCHEMA iQuad Academy
DROP SCHEMA IF EXISTS iQuad_Academy;
CREATE SCHEMA iQuad_Academy;

# SELECT SCHEMA iQuad Academy
USE iQuad_Academy;

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

# CREATE TEACHERS TABLE
CREATE TABLE teachers
(
    teachersID int AUTO_INCREMENT,
    firstName  varchar(55) NOT NULL,
    lastName   varchar(55) NOT NULL,
    CONSTRAINT teachers_pk
        PRIMARY KEY (teachersID)
);

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

# CREATE CLASSS TABLE
CREATE TABLE classs
(
    classsID   int AUTO_INCREMENT,
    teachersID int NOT NULL,
    modID      int NOT NULL,
    CONSTRAINT classs_pk
        PRIMARY KEY (classsID),
    CONSTRAINT classs_module_modID_fk
        FOREIGN KEY (modID) REFERENCES iquad_academy.module (modID),
    CONSTRAINT classs_teachers_teachersID_fk
        FOREIGN KEY (teachersID) REFERENCES iquad_academy.teachers (teachersID)
);

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
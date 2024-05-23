-- Drop existing tables
DROP TABLE Medical_Records CASCADE CONSTRAINTS;
DROP TABLE Appointments CASCADE CONSTRAINTS;
DROP TABLE Patients CASCADE CONSTRAINTS;
DROP TABLE Secretaire CASCADE CONSTRAINTS;
DROP TABLE Doctors CASCADE CONSTRAINTS;

-- Create sequences
CREATE SEQUENCE doctors_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

CREATE SEQUENCE secretaire_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

CREATE SEQUENCE patients_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

CREATE SEQUENCE appointments_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

CREATE SEQUENCE medical_records_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

-- Create tables
CREATE TABLE Doctors (
    doctor_id NUMBER,
    name VARCHAR2(100),
    specialty VARCHAR2(100),
    email VARCHAR2(30) UNIQUE,
    password VARCHAR2(255),
    n_tel NUMBER(10),
    PRIMARY KEY (doctor_id)
);

CREATE TABLE Secretaire (
    secretaire_id NUMBER,
    name VARCHAR2(100),
    email VARCHAR2(30) UNIQUE,
    password VARCHAR2(255),
    n_tel NUMBER(10),
    PRIMARY KEY (secretaire_id)
);

CREATE TABLE Patients (
    patient_id NUMBER,
    name VARCHAR2(100),
    phone VARCHAR2(50),
    date_de_naissance DATE,
    weight NUMBER,
    sex VARCHAR2(1) CHECK (sex IN ('f', 'm')),
    height NUMBER,
    medical_history CLOB,
    surgical_history CLOB,
    PRIMARY KEY (patient_id)
);

CREATE TABLE Appointments (
    appointment_id NUMBER,
    patient_id NUMBER,
    doctor_id NUMBER,
    appointment_time TIMESTAMP,
    PRIMARY KEY (appointment_id),
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id)
);

CREATE TABLE Medical_Records (
    record_id NUMBER,
    patient_id NUMBER,
    visit_date DATE,
    symptoms CLOB,
    diagnosis CLOB,
    treatment CLOB,
    remarque CLOB,
    PRIMARY KEY (record_id),
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id)
);

-- Create triggers
CREATE OR REPLACE TRIGGER doctors_bir
BEFORE INSERT ON Doctors
FOR EACH ROW
BEGIN
    SELECT doctors_seq.NEXTVAL
    INTO :new.doctor_id
    FROM dual;
END;
/

CREATE OR REPLACE TRIGGER secretaire_bir
BEFORE INSERT ON Secretaire
FOR EACH ROW
BEGIN
    SELECT secretaire_seq.NEXTVAL
    INTO :new.secretaire_id
    FROM dual;
END;
/

CREATE OR REPLACE TRIGGER patients_bir
BEFORE INSERT ON Patients
FOR EACH ROW
BEGIN
    SELECT patients_seq.NEXTVAL
    INTO :new.patient_id
    FROM dual;
END;
/

CREATE OR REPLACE TRIGGER appointments_bir
BEFORE INSERT ON Appointments
FOR EACH ROW
BEGIN
    SELECT appointments_seq.NEXTVAL
    INTO :new.appointment_id
    FROM dual;
END;
/

CREATE OR REPLACE TRIGGER medical_records_bir
BEFORE INSERT ON Medical_Records
FOR EACH ROW
BEGIN
    SELECT medical_records_seq.NEXTVAL
    INTO :new.record_id
    FROM dual;
END;
/

-- Insert example data into Doctors table
INSERT INTO Doctors (name, specialty, email, password, n_tel) VALUES 
('Dr. John Smith', 'Cardiology', 'john.smith@hospital.com', 'hashed_password_123', 1234567890);
INSERT INTO Doctors (name, specialty, email, password, n_tel) VALUES
('Dr. Emily Johnson', 'Neurology', 'emily.johnson@hospital.com', 'hashed_password_456', 2345678901);
INSERT INTO Doctors (name, specialty, email, password, n_tel) VALUES
('Dr. Robert Brown', 'Pediatrics', 'robert.brown@hospital.com', 'hashed_password_789', 3456789012);
INSERT INTO Doctors (name, specialty, email, password, n_tel) VALUES
('Dr. Sarah Davis', 'Dermatology', 'sarah.davis@hospital.com', 'hashed_password_101', 4567890123);
INSERT INTO Doctors (name, specialty, email, password, n_tel) VALUES
('Dr. William Wilson', 'Orthopedics', 'william.wilson@hospital.com', 'hashed_password_202', 5678901234);
INSERT INTO Doctors (name, specialty, email, password, n_tel) VALUES
('Dr. John Doe', 'Cardiology', 'john.doe@example.com', 'password123', 1234567890);
INSERT INTO Doctors (name, specialty, email, password, n_tel) VALUES
('Dr. Jane Smith', 'Neurology', 'jane.smith@example.com', 'password456', 1234567891);
INSERT INTO Doctors (name, specialty, email, password, n_tel) VALUES
('Dr. Emily Johnson', 'Dermatology', 'emily.johnson@example.com', 'password789', 1234567892);
INSERT INTO Doctors (name, specialty, email, password, n_tel) VALUES
('Dr. Michael Brown', 'Pediatrics', 'michael.brown@example.com', 'password101', 1234567893);
INSERT INTO Doctors (name, specialty, email, password, n_tel) VALUES
('Dr. Sarah Davis', 'Orthopedics', 'sarah.davis@example.com', 'password202', 1234567894);

-- Insert example data into Secretaire table
INSERT INTO Secretaire (name, email, password, n_tel) VALUES 
('Alice Walker', 'alice.walker@hospital.com', 'hashed_password_303', 6789012345);
INSERT INTO Secretaire (name, email, password, n_tel) VALUES 
('Mark Harris', 'mark.harris@hospital.com', 'hashed_password_404', 7890123456);
INSERT INTO Secretaire (name, email, password, n_tel) VALUES 
('Susan Clark', 'susan.clark@hospital.com', 'hashed_password_505', 8901234567);
INSERT INTO Secretaire (name, email, password, n_tel) VALUES 
('James Lewis', 'james.lewis@hospital.com', 'hashed_password_606', 9012345678);
INSERT INTO Secretaire (name, email, password, n_tel) VALUES 
('Laura Robinson', 'laura.robinson@hospital.com', 'hashed_password_707', 1234567891);

-- Insert example data into Patients table
INSERT INTO Patients (name, phone) VALUES ('Alice Walker', '111-222-3333');
INSERT INTO Patients (name, phone) VALUES ('Bob Harris', '222-333-4444');
INSERT INTO Patients (name, phone) VALUES ('Charlie Brown', '333-444-5555');
INSERT INTO Patients (name, phone) VALUES ('Diana Prince', '444-555-6666');
INSERT INTO Patients (name, phone) VALUES ('Edward Norton', '555-666-7777');


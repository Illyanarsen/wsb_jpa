-- Dodanie adresów
INSERT INTO ADDRESS (city, address_Line1, address_Line2, postal_Code) VALUES
                                                                           ('Wroclaw', 'ul. Kwiatowa 5', NULL, '50-500'),
                                                                           ('Warsaw', 'ul. Prosta 10', NULL, '00-800');
-- Dodanie pacjentów
INSERT INTO PATIENT (first_Name, last_Name, telephone_Number, email, patient_Number, date_Of_Birth, address_id) VALUES
    ('Jan', 'Kowalski', '123456789', 'jan.kowalski@gmail.com', 'P001', '1985-04-20', 1);

-- Dodanie lekarzy
INSERT INTO DOCTOR (first_Name, last_Name, telephone_Number, email, doctor_Number, specialization, address_id) VALUES
    ('Anna', 'Nowak', '987654321', 'anna.nowak@gmail.com', 'D001', 'SURGEON', 2);

-- Dodanie wizyt
INSERT INTO VISIT (description, time, doctor_id, patient_id) VALUES
    ('Konsultacja chirurgiczna', '2025-04-15 10:00:00', 1, 1);

-- Dodanie zabiegów
INSERT INTO MEDICAL_TREATMENT (description, type, visit_id) VALUES
    ('Badanie USG brzucha', 'USG', 1);

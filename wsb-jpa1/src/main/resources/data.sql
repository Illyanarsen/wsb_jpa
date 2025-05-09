-- Dodanie adresów
INSERT INTO ADDRESS (city, address_Line1, address_Line2, postal_Code) VALUES
                                                                           ('Wroclaw', 'ul. Kwiatowa 5', NULL, '50-500'),
                                                                           ('Warsaw', 'ul. Prosta 10', NULL, '00-800'),
                                                                           ('Krakow', 'ul. Sloneczna 15', 'apt. 3', '30-100'),
                                                                           ('Gdansk', 'ul. Morska 22', NULL, '80-200'),
                                                                           ('Poznan', 'ul. Lesna 8', 'floor 2', '60-500'),
                                                                           ('Szczecin', 'ul. Portowa 12', NULL, '70-300'),
                                                                           ('Lodz', 'ul. Przemyslowa 33', 'apt. 5', '90-400');
-- Dodanie pacjentów
INSERT INTO PATIENT (first_Name, last_Name, telephone_Number, email, patient_Number, date_Of_Birth, address_id) VALUES
    ('Jan', 'Kowalski', '123456789', 'jan.kowalski@gmail.com', 'P001', '1985-04-20', 1),
    ('Maria', 'Wisniewska', '234567890', 'maria.wisniewska@example.com', 'P002', '1990-07-12', 3),
    ('Piotr', 'Nowakowski', '345678901', 'piotr.nowakowski@example.com', 'P003', '1978-11-30', 4),
    ('Agnieszka', 'Lewandowska', '456789012', 'agnieszka.lew@example.com', 'P004', '1982-03-25', 5),
    ('Krzysztof', 'Dabrowski', '567890123', 'k.dabrowski@example.com', 'P005', '1995-09-18', 6),
    ('Ewa', 'Wojcik', '678901234', 'ewa.wojcik@example.com', 'P006', '1972-12-05', 7);

-- Dodanie lekarzy
INSERT INTO DOCTOR (first_Name, last_Name, telephone_Number, email, doctor_Number, specialization, address_id) VALUES
    ('Anna', 'Nowak', '987654321', 'anna.nowak@gmail.com', 'D001', 'SURGEON', 2),
    ('Marek', 'Kowalczyk', '876543210', 'marek.kowalczyk@example.com', 'D002', 'GP', 3),
    ('Tomasz', 'Zielinski', '765432109', 't.zielinski@example.com', 'D003', 'DERMATOLOGIST', 4),
    ('Barbara', 'Szymanska', '654321098', 'b.szymanska@example.com', 'D004', 'OCULIST', 5),
    ('Grzegorz', 'Wozniak', '543210987', 'grzegorz.wozniak@example.com', 'D005', 'ORTHOPEDIST', 6),
    ('Katarzyna', 'Kaminska', '432109876', 'k.kaminska@example.com', 'D006', 'CARDIOLOGIST', 7);

-- Dodanie wizyt
INSERT INTO VISIT (description, time, doctor_id, patient_id) VALUES
    ('Konsultacja chirurgiczna', '2025-04-15 10:00:00', 1, 1),
    ('Badanie kontrolne', '2025-05-10 14:30:00', 2, 2),
    ('Konsultacja dermatologiczna', '2025-05-12 09:15:00', 3, 3),
    ('Badanie wzroku', '2025-05-15 11:00:00', 4, 4),
    ('Konsultacja ortopedyczna', '2025-05-18 16:45:00', 5, 5),
    ('EKG serca', '2025-05-20 13:20:00', 6, 6),
    ('Badanie ogólne', '2025-05-22 10:30:00', 2, 1),
    ('Kontrola po zabiegu', '2025-05-25 15:00:00', 1, 3);

-- Dodanie zabiegów
INSERT INTO MEDICAL_TREATMENT (description, type, visit_id) VALUES
    ('Badanie USG brzucha', 'USG', 1),
    ('Badanie krwi', 'LAB_TEST', 2),
    ('Test alergiczny', 'ALLERGY_TEST', 3),
    ('Badanie ostrości wzroku', 'VISION_TEST', 4),
    ('RTG kolana', 'RTG', 5),
    ('EKG spoczynkowe', 'EKG', 6),
    ('Pomiar ciśnienia', 'BLOOD_PRESSURE', 7),
    ('USG stawu barkowego', 'USG', 8);

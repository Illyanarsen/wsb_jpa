package com.jpacourse.service.impl;
import com.jpacourse.persistance.dao.Dao;
import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.Specialization;
import com.jpacourse.service.PatientService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.jpacourse.dto.PatientTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PatientServiceImplTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private VisitDao visitDao;

    @PersistenceContext
    private EntityManager em;

    private PatientEntity testPatient;
    private VisitEntity testVisit;
    private DoctorEntity testDoctor;
    private Long doctorId;

    @BeforeEach
    void setUp() {
        // Create and save Address
        AddressEntity patientAddress = new AddressEntity();
        patientAddress.setCity("Wroclaw");
        patientAddress.setAddressLine1("ul.Jana Pawla 2");
        patientAddress.setPostalCode("50-001");

        // Create and save Doctor
        testDoctor = new DoctorEntity();
        testDoctor.setFirstName("Dr. Smith");
        testDoctor.setLastName("Johnson");
        testDoctor.setSpecialization(Specialization.DERMATOLOGIST);
        testDoctor.setEmail("dr.smith@clinic.com");
        testDoctor.setAddress(patientAddress);
        testDoctor.setDoctorNumber("abc");
        testDoctor.setTelephoneNumber("123456789");
        doctorDao.save(testDoctor);

        // Create and save Patient with Address
        testPatient = new PatientEntity();
        testPatient.setFirstName("Jan");
        testPatient.setLastName("Kowalski");
        testPatient.setTelephoneNumber("123456789");
        testPatient.setEmail("jan.kowalski@example.com");
        testPatient.setPatientNumber("P10001");
        testPatient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        testPatient.setAddress(patientAddress);
        testPatient.setIsInsured(true);
        patientDao.save(testPatient);

        // Create and save Visit
        testVisit = new VisitEntity();
        testVisit.setTime(LocalDateTime.now());
        testVisit.setDescription("Regular checkup");
        testVisit.setDoctor(testDoctor);
        testPatient.addVisit(testVisit);
        visitDao.save(testVisit);

        em.flush();
    }

    @Test
    public void testShouldFindPatientById() {
        // given
        Long existingPatientId = 1L; // Assuming this ID exists in your test data

        // when
        PatientTO patientTO = patientService.findById(existingPatientId);

        // then
        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getFirstName()).isEqualTo("Jan"); // Replace with actual expected value
        assertThat(patientTO.getLastName()).isEqualTo("Kowalski"); // Replace with actual expected value
    }

    @Test
    public void testShouldNotFindNonExistingPatient() {
        // given
        Long nonExistingPatientId = 999L;

        // when
        PatientTO patientTO = patientService.findById(nonExistingPatientId);

        // then
        assertThat(patientTO).isNull();
    }

    @Test
    public void testShouldReturnPatientWithAddress() {
        // given
        Long patientIdWithAddress = 1L; // Assuming this patient has an address

        // when
        PatientTO patientTO = patientService.findById(patientIdWithAddress);

        // then
        assertThat(patientTO.getAddress()).isNotNull();
        assertThat(patientTO.getAddress().getCity()).isEqualTo("Wroclaw"); // Replace with actual expected value
    }

    @Test
    void deletePatient_ShouldRemovePatientAndVisits_ButNotDoctor() {
        // Given
        Long patientId = testPatient.getId();
        Long visitId = testVisit.getId();
        Long doctorId = testVisit.getId();

        // When
        patientService.deletePatientWithCascade(patientId);

        // Then - Using repository methods
        assertThat(patientDao.findOne(patientId)).isNull();
        assertThat(visitDao.findOne(visitId)).isNull();
        assertThat(doctorDao.findOne(doctorId)).isNotNull();
    }



    }
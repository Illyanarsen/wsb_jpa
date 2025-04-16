package com.jpacourse.service.impl;
import com.jpacourse.dto.VisitTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class PatientServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImplTest.class);

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
    private VisitEntity testVisit2;
    private DoctorEntity testDoctor;
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
        patientDao.save(testPatient);


        // Create and save Visit
        testVisit2 = new VisitEntity();
        testVisit2.setTime(LocalDateTime.now());
        testVisit2.setDescription("Regular checkup");
        testVisit2.setDoctor(testDoctor);
        testPatient.addVisit(testVisit2);
        visitDao.save(testVisit2);
        patientDao.save(testPatient);

        em.flush();
    }

    @Test
    public void testShouldFindPatientById() {
        // given
        Long existingPatientId = testPatient.getId();

        // Clear persistence context to ensure fresh query
        em.clear();
        log.debug("===== BEFORE PATIENT FETCH =====");

        // when
        PatientTO patientTO = patientService.findById(existingPatientId);

        log.debug("===== AFTER PATIENT FETCH =====");

        // then
        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getFirstName()).isEqualTo("Jan");
        assertThat(patientTO.getLastName()).isEqualTo("Kowalski");
    }

    @Test
    public void testShouldReturnAllVisits(){
        // Given
        Long existingPatientId = testPatient.getId();

        PatientTO patientTO = patientService.findById(existingPatientId);
        List<Long> visitIds = patientTO.getVisits().stream()
                .map(VisitTO::getId)
                .toList();

        // Then
        assertThat(patientTO).isNotNull();
        assertThat(visitIds).contains(testVisit.getId());
        assertThat(visitIds).contains(testVisit2.getId());
        assertThat(patientTO.getVisits()).hasSize(2);

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
        Long patientIdWithAddress = testPatient.getId();

        // when
        PatientTO patientTO = patientService.findById(patientIdWithAddress);

        // then
        assertThat(patientTO.getAddress()).isNotNull();
        assertThat(patientTO.getAddress().getCity()).isEqualTo("Wroclaw");
    }

    @Test
    void deletePatient_ShouldRemovePatientAndVisits_ButNotDoctor() {
        // Given
        Long patientId = testPatient.getId();
        Long visitId = testVisit.getId();
        Long doctorId = testDoctor.getId();

        // When
        patientService.deletePatientWithCascade(patientId);

        // Then
        assertThat(patientDao.findOne(patientId)).isNull();
        assertThat(visitDao.findOne(visitId)).isNull();
        assertThat(doctorDao.findOne(doctorId)).isNotNull();
    }



    }
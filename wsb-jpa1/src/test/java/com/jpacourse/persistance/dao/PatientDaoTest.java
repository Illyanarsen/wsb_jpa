package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.AddressEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.Specialization;
import com.jpacourse.service.PatientService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientDaoTest {

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
        ;

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
        testPatient.addVisit(testVisit);
        visitDao.save(testVisit);
        patientDao.save(testPatient);

        em.flush();
    }

    @Test
    @Transactional
    public void testShouldFindByLastName(){
        // given
        // when
        List<PatientEntity> patientEntity = patientDao.findByLastName("Kowalski");
        // then
        assertThat(patientEntity).isNotNull();
    }

    @Test
    @Transactional
    public void testShouldFindMoreThanX(){
        // given

        // when
        List<PatientEntity> patients = patientDao.MoreVisitsThan(1);
        // then
        assertThat(patients).isNotNull();
    }

    @Test
    @Transactional
    public void testShouldFindAdultPatients(){
        // given
        // when
        List<PatientEntity> patients = patientDao.findAdultPatients();
        // then
        assertThat(patients).isNotNull();
    }

}

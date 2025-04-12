package com.jpacourse.service.impl;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.jpacourse.dto.PatientTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PatientServiceImplTest {

        @Autowired
        private PatientService patientService;

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
    }
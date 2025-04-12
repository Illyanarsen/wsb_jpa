package com.jpacourse.persistance.dao.impl;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public PatientEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String description) {
        PatientEntity patient = findOne(patientId);
        if (patient == null) {
            throw new EntityNotFoundException("Patient not found with id: " + patientId);
        }

        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);
        if (doctor == null) {
            throw new EntityNotFoundException("Doctor not found with id: " + doctorId);
        }

        VisitEntity newVisit = new VisitEntity();
        newVisit.setTime(visitDate);
        newVisit.setDescription(description);
        newVisit.setDoctor(doctor);

        // Add visit to patient's collection
        patient.addVisit(newVisit);

        // This will cascade the persist operation to the new visit
        return entityManager.merge(patient);
    }

}

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
import java.util.Collections;
import java.util.List;

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

    @Override
    public List<PatientEntity> findByLastName(String pPatientLastName) {
        return entityManager.createQuery(" select patient from PatientEntity patient " +
                " where patient.lastName like :param1" , PatientEntity.class)
                .setParameter("param1", "%"+pPatientLastName+"%").getResultList();
    }

    @Override
    public List<VisitEntity> findPatientVisits(Long pPatientId) {
        PatientEntity patient = entityManager.find(PatientEntity.class, pPatientId);
        if (patient != null) {
            return patient.getVisits(); // Returns the list of visits
        }
        return Collections.emptyList(); // Return empty list if patient not found
    }

    @Override
    public List<PatientEntity> MoreVisitsThan(Integer minVisits) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p " +
                                "JOIN p.visits v " +  // Join with visits
                                "GROUP BY p.id " +     // Group by patient
                                "HAVING COUNT(v) > :minVisits", // Filter by visit count
                        PatientEntity.class)
                .setParameter("minVisits", minVisits)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findAdultPatients() {
        LocalDate adultDate = LocalDate.now().minusYears(18);
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p WHERE p.dateOfBirth <= :adultDate",
                        PatientEntity.class)
                .setParameter("adultDate", adultDate)
                .getResultList();
    }



}

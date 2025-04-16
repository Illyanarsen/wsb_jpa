package com.jpacourse.persistance.dao;

        import com.jpacourse.persistance.entity.PatientEntity;
        import com.jpacourse.persistance.entity.VisitEntity;

        import java.time.LocalDateTime;
        import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {
    PatientEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String description);
    List<PatientEntity> findByLastName(String patientLastName);
    List<VisitEntity> findPatientVisits(Long patientId);
    List<PatientEntity> MoreVisitsThan(Integer minVisits);
    public List<PatientEntity> findAdultPatients();
    boolean exists(Long id);         // Add this for existence checks
    void delete(Long id);            // Your existing delete


}

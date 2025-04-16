package com.jpacourse.service.impl;
import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientDao patientDao;
    private final DoctorDao doctorDao;
    private final EntityManager entityManager;


    @Autowired
    public PatientServiceImpl(PatientDao patientDao,
                              DoctorDao doctorDao,
                              EntityManager entityManager) {
        this.patientDao = patientDao;
        this.doctorDao = doctorDao;
        this.entityManager = entityManager;
    }

    @Override
    public PatientTO findById(Long id) {
        final PatientEntity entity = patientDao.findOne(id);
        return PatientMapper.mapToTO(entity);
    }

    public void deletePatientWithCascade(Long patientId) {
        PatientEntity patient = patientDao.findOne(patientId);
        if (patient == null) {
            throw new EntityNotFoundException(patientId);
        }
        patientDao.delete(patientId);

        // Explicit flush to ensure operation completes
        entityManager.flush();
    }
}

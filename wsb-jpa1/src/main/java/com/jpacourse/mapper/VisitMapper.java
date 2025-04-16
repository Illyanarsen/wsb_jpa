package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.VisitEntity;

public class VisitMapper {
    public static VisitTO mapToTO(VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }

        VisitTO visitTO = new VisitTO();
        visitTO.setId(visitEntity.getId());
        visitTO.setDescription(visitEntity.getDescription());
        visitTO.setTime(visitEntity.getTime());

        // Use DoctorMapper to convert the associated doctor
        visitTO.setDoctor(DoctorMapper.mapToTO(visitEntity.getDoctor()));

        return visitTO;
    }

    public static VisitEntity mapToEntity(VisitTO visitTO) {
        if (visitTO == null) {
            return null;
        }

        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setId(visitTO.getId());
        visitEntity.setDescription(visitTO.getDescription());
        visitEntity.setTime(visitTO.getTime());

        // Use DoctorMapper to convert back to entity
        if (visitTO.getDoctor() != null) {
            visitEntity.setDoctor(DoctorMapper.mapToEntity(visitTO.getDoctor()));
        }

        return visitEntity;
    }
}

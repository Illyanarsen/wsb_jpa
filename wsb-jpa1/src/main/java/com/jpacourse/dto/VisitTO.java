package com.jpacourse.dto;

import com.jpacourse.persistance.entity.DoctorEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class VisitTO {
    private Long id;

    private String description;

    private LocalDateTime time;

    private DoctorEntity doctor;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

}

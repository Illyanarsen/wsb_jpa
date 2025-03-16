package com.jpacourse.persistance.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private String description;

	@Column(nullable = false)
	private LocalDateTime time;

	// Dwustronna relacja: Doctor jest właścicielem relacji
	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false) // Klucz obcy do DOCTOR
	private DoctorEntity doctor;

	// Jednostronna relacja
	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private PatientEntity patient;

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



}

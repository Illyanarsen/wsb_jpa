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



}

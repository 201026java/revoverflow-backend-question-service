package com.revature.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "questions")
@Data @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @ToString
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// Changed to wrapper class on line 23 so that it could hold a null/0 value
	@Column(name = "accepted_id")
	private Integer acceptedId;

	@NotBlank(message = "Title requires a string value")
	private String title;

	@NotBlank(message = "Content requires a string value")
	@Column(columnDefinition = "TEXT")
	private String content;

	// add the not null check in the service layer
	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@Column(name = "edit_date")
	private LocalDateTime editDate;
	
	@Column(name = "question_type")
	private String questionType;
	
	@Column(name = "location")
	private String location;

	private boolean status;

	// add the not null check in the service layer
	@Column(name = "user_id")
	private int userID;

}

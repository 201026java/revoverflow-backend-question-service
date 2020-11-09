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

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Data @Getter @Setter @NoArgsConstructor
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
	private String content;

	// add the not null check in the service layer
	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@Column(name = "edit_date")
	private LocalDateTime editDate;
	
    @Enumerated(EnumType.STRING)
	private QuestionType questionType;
	
	@Column(name = "location")
	private String location;

	private boolean status;

	// add the not null check in the service layer
	@Column(name = "user_id")
	private int userID;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acceptedId == null) ? 0 : acceptedId.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((editDate == null) ? 0 : editDate.hashCode());
		result = prime * result + id;
		result = prime * result + (status ? 1231 : 1237);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((questionType == null) ? 0 : questionType.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + userID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (acceptedId == null) {
			if (other.acceptedId != null)
				return false;
		} else if (!acceptedId.equals(other.acceptedId))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (editDate == null) {
			if (other.editDate != null)
				return false;
		} else if (!editDate.equals(other.editDate))
			return false;
		if (questionType == null) {
			if (other.questionType != null)
				return false;
		} else if (!questionType.equals(other.questionType))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (id != other.id)
			return false;
		if (status != other.status)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}

	public Question(int id, Integer acceptedId, @NotBlank(message = "Title requires a string value") String title,
			@NotBlank(message = "Content requires a string value") String content, LocalDateTime creationDate,
			LocalDateTime editDate, QuestionType questionType, String location, boolean status, int userID) {
		super();
		this.id = id;
		this.acceptedId = acceptedId;
		this.title = title;
		this.content = content;
		this.creationDate = creationDate;
		this.editDate = editDate;
		this.questionType = questionType;
		this.location = location;
		this.status = status;
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", acceptedId=" + acceptedId + ", title=" + title + ", content=" + content
				+ ", creationDate=" + creationDate + ", editDate=" + editDate + ", questionType=" + questionType + 
				", location=" + location + ", status=" + status + ", userID=" + userID + "]";
	}
	
}

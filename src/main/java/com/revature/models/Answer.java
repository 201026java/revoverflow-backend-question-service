package com.revature.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Answer {

	private int id;
	private int userId;
	private int questionId;
	private String content;
	private LocalDateTime creationDate;
	private LocalDateTime editDate;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((editDate == null) ? 0 : editDate.hashCode());
		result = prime * result + id;
		result = prime * result + questionId;
		result = prime * result + userId;
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
		Answer other = (Answer) obj;
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
		if (id != other.id)
			return false;
		if (questionId != other.questionId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", userId=" + userId + ", questionId=" + questionId + ", content=" + content
				+ ", creationDate=" + creationDate + ", editDate=" + editDate + "]";
	}

	public Answer(int id, int userId, int questionId,
			@NotBlank(message = "Content must have a string value") String content, LocalDateTime creationDate,
			LocalDateTime editDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.questionId = questionId;
		this.content = content;
		this.creationDate = creationDate;
		this.editDate = editDate;
	}

}

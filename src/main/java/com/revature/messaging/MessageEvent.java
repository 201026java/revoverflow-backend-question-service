package com.revature.messaging;

import java.time.LocalDateTime;

import com.revature.models.Answer;
import com.revature.models.Question;
import com.revature.models.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class MessageEvent {

	private User user;
	private Question question;
	private Answer answer;
	private Operation operation;
	private LocalDateTime timestamp;
	
	public MessageEvent(User user, Question question, Answer answer, Operation operation) {
		this.user = user;
		this.question = question;
		this.answer = answer;
		this.operation = operation;
		this.timestamp = LocalDateTime.now();
	}
	
	public MessageEvent(Question question, Answer answer, Operation operation) {
		this.question = question;
		this.answer = answer;
		this.operation = operation;
		this.timestamp = LocalDateTime.now();
	}
	
	public MessageEvent(User user, Answer answer, Operation operation) {
		this.user = user;
		this.answer = answer;
		this.operation = operation;
		this.timestamp = LocalDateTime.now();
	}
	
	public MessageEvent(User user, Question question, Operation operation) {
		this.user = user;
		this.question = question;
		this.operation = operation;
		this.timestamp = LocalDateTime.now();
	}
	
	public MessageEvent(User user, Operation operation) {
		this.user = user;
		this.operation = operation;
		this.timestamp = LocalDateTime.now();
	}
	
	public MessageEvent(Question question, Operation operation) {
		this.question = question;
		this.operation = operation;
		this.timestamp = LocalDateTime.now();
	}
	
	public MessageEvent(Answer answer, Operation operation) {
		this.answer = answer;
		this.operation = operation;
		this.timestamp = LocalDateTime.now();
	}
	
	public MessageEvent(Operation operation) {
		this.operation = operation;
		this.timestamp = LocalDateTime.now();
	}
}

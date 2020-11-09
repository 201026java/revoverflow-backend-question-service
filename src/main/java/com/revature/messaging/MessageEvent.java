package com.revature.messaging;

import com.revature.models.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Getter
public class MessageEvent {

	private Question question;
	private Operation operation;
	
}

package com.revature.messaging;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.revature.services.QuestionService;

@Service
public class MessageService {
	
	private static Set<Integer> eventCache = new HashSet<>();
	
	@Autowired
	private QuestionService questionService;

	@Autowired
	private KafkaTemplate<String, MessageEvent> kt;
	
	public void triggerEvent(MessageEvent event) {
		eventCache.add(event.hashCode());
		
		kt.send("question", event);
	}
	
	@KafkaListener(topics = "question")
	public void processMessageEvent(MessageEvent event) {
		if(event.getQuestion() == null || event.getOperation() == null) {
			return;
		}
		if(eventCache.contains(event.hashCode())) {
			eventCache.remove(event.hashCode());
			return;
		}
		
		System.out.println("___________________________________________________");
		System.out.println("        I GOT A FISH");
		System.out.println(event);
		System.out.println("___________________________________________________");
		
		switch(event.getOperation()) {
		case CREATE:
			this.questionService.save(event.getQuestion());
			break;
		case UPDATE_AA:
			this.questionService.updateQuestionAcceptedAnswerId(event.getQuestion());
			break;
		case UPDATE_STATUS:
			//set to 0 points - do not add points again for every service. points are added from the original service
			this.questionService.updateQuestionStatus(event.getQuestion(), 0);
			break;
		}
	}
}

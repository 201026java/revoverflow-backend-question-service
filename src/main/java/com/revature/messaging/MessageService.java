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
	
	
	/** @Author Mark Alsip 
	 * messaging is used to update parallel h2 databases of the
	 * same service. this will send a notification to add an
	 * answer to that service's h2
	 * @param pass in a MessageEvent, it'll contain a question and the http method
	 */
	public void triggerEvent(MessageEvent event) {
		eventCache.add(event.hashCode());
		
		kt.send("question", event);
	}
	
	
	/** @Author Mark Alsip 
	 * if a message is received add that message to the h2
	 * I GOT A FISH is just used to log the message that was received. 
	 * also i was thinking about fish when i wrote that. 
	 * it's not necessary but i like it.
	 * don't delete it.
	 * please.
	 * 
	 * @param event is received by message
	 */
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

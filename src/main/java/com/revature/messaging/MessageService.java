package com.revature.messaging;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.revature.repositories.QuestionRepository;


public class MessageService {
	
	private static Set<Integer> eventCache = new HashSet<>();
	
	@Autowired
	private QuestionRepository questionDao;

	@Autowired
	private KafkaTemplate<String, MessageEvent> kt;
	
	public void triggerQuestionEvent(MessageEvent event) {
		eventCache.add(event.hashCode());
		
		if(event.getOperation() == Operation.DELETE) {
			kt.send("question", event);
		}
		
		kt.send("question", event);
	}
	
	@KafkaListener(topics = "question")
	public void processMessageEvent(MessageEvent event) {
		if(eventCache.contains(event.hashCode())) {
			eventCache.remove(event.hashCode());
			return;
		}
		
		//Flashcard card = event.getFlashcard();
		
		switch(event.getOperation()) {
		case CREATE:
			//this.questionDao.save(card);
			break;
		case UPDATE:
			//this.questionDao.save(card);
			break;
		case DELETE:
			//this.questionDao.deleteById(card.getId());
			break;
		}
	}
}

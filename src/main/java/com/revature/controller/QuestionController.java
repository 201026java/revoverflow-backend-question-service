package com.revature.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.messaging.MessageEvent;
import com.revature.messaging.MessageService;
import com.revature.messaging.Operation;
import com.revature.models.Question;
import com.revature.services.QuestionService;


@RestController
@RequestMapping("/question")
@CrossOrigin(
		origins = { "http://localhost:8500" }, 
		methods = { RequestMethod.GET, RequestMethod.PUT, 
					RequestMethod.PATCH, RequestMethod.POST },
		allowedHeaders = { "content-type" }
	)
public class QuestionController {

	@Autowired
	QuestionService questionService;
	
	@Autowired
	MessageService messageService;

	public static int m = 0;
	
	/**	 *@author ken 
	 * get all the questions*/
	@GetMapping
	public Page<Question> getAllQuestions(Pageable pageable)
	{
		return questionService.getAllQuestions(pageable);
	}

	/**
	 * @param status = true/false
	 * get all the questions by the status of the question
	 */
	/**@author ken*/
	@GetMapping("/status/{status}")
	public Page<Question> getAllQuestionsByStatus(Pageable pageable, @PathVariable boolean status)
	{
		return questionService.getAllQuestionsByStatus(pageable, status);
	}

	/**@author ken*/
	/** get all the questions by user id
	 * @param pageable
	 * @param id = the id of the user
	 * @return
	 */
	@GetMapping("/user/{id}")
	public Page<Question> getAllQuestionsByUserId(Pageable pageable, @PathVariable int id)
	{
		return questionService.getAllQuestionsByUserId(pageable, id);
	}

	/** @Author James Walls */
	/** Adds new questions and updates existing ones. */
	@PostMapping
	public Question saveQuestion(@Valid @RequestBody Question question) {
		messageService.triggerEvent(new MessageEvent(question, Operation.CREATE));
		return questionService.save(question);
	}

	/** 
	 * @author Hugh Thornhill 
	 * @return This is the updateQuestionAcceptedAnswerId endpoint which updates the
	 * acceptedId to the answer that is deemed the most acceptable.
	 */
	@PutMapping
	public Question updateQuestionAcceptedAnswerId(@RequestBody Question question) {
		messageService.triggerEvent(new MessageEvent(question, Operation.UPDATE_AA));
		return questionService.updateQuestionAcceptedAnswerId(question);
	}

	/** 
	 * @author Hugh Thornhill 
	 * @return This is the updateStatus endpoint which updates the question status and 
	 * awards 20 points to the user who answered the question.
	 */
	@PutMapping("/status")
	public Question updateStatus(@RequestBody Question question) {
		messageService.triggerEvent(new MessageEvent(question, Operation.UPDATE_STATUS));
		return questionService.updateQuestionStatus(question, 20);
	}

	/** @Author Natasha Poser 
	 * @return the is the GetQuestionById end-point. It retrieves a question by it's ID*/
	@GetMapping("/id/{id}")
	public Question getQuestionById(@PathVariable int id) {
		return questionService.findById(id);
	}
	
	/** get all the questions by filter data
	 * @param questionType = defines the question type (Revature or Location)
	 * @param location = specific location if questionType is Location
	 * @param id = the id of the user, or 0 if not specified
	 * @return
	 */
	@GetMapping("/filter")
	public Page<Question> getAllQuestionsByQuestionType(Pageable pageable, @RequestParam String questionType, @RequestParam String location, @RequestParam int id)
	{	
		return questionService.getAllQuestionsByFilter(pageable, questionType, location, id);
	}

	@GetMapping("/unconfirmed/filter")
	public Page<Question> getAllUnconfirmedQuestionsByQuestionType(Pageable pageable, @RequestParam String questionType, @RequestParam String location, @RequestParam int id)
	{	
		return questionService.getAllUnconfirmedQuestionsByFilter(pageable, questionType, location, id);
	}

} 
package com.revature.controller;


import java.util.List;
import java.util.Collection;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		origins = { "*" }, 
		methods = { RequestMethod.GET, RequestMethod.PUT, 
					RequestMethod.PATCH, RequestMethod.POST },
		allowedHeaders = { "*" }
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
	@PreAuthorize("hasAuthority('USER')")
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
	@PreAuthorize("hasAuthority('USER')")
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
	@PreAuthorize("hasAuthority('USER')")
	public Page<Question> getAllQuestionsByUserId(Pageable pageable, @PathVariable int id)
	{
		return questionService.getAllQuestionsByUserId(pageable, id);
	}

	/** @Author James Walls */
	/** Adds new questions and updates existing ones. */
	@PostMapping
	@PreAuthorize("hasAuthority('USER')")
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
	@PreAuthorize("hasAuthority('ADMIN')")
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
	@PreAuthorize("hasAuthority('USER')")
	public Question updateStatus(@RequestBody Question question) {
		messageService.triggerEvent(new MessageEvent(question, Operation.UPDATE_STATUS));
		return questionService.updateQuestionStatus(question, 20);
	}

	/** @Author Natasha Poser 
	 * @return the is the GetQuestionById end-point. It retrieves a question by it's ID*/
	@GetMapping("/id/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public Question getQuestionById(@PathVariable int id) {
		return questionService.findById(id);
	}
	
	/** @Author Mark Alsip
	 * get all the questions by filter data
	 * Example URL: /filter?questionType=Revature&location=none&id=0
	 * @param questionType = defines the question type (Revature or Location). Required.
	 * @param location = specific location if questionType is Location. ignored if questionType is Revature.
	 * @param id = the id of the user, or 0 if not specified.
	 * @return
	 */
	@GetMapping("/filter")
	public Page<Question> getAllQuestionsByFilter(Pageable pageable, @RequestParam String questionType, @RequestParam String location, @RequestParam int id){	
		return questionService.getAllQuestionsByFilter(pageable, questionType, location, id);
	}

	/** @Author Mark Alsip
	 * same as /filter, but only returns records with "status = false"
	 * status refers to whether a question is confirmed or not
	 */
	@GetMapping("/unconfirmed/filter")
	public Page<Question> getAllUnconfirmedQuestionsByFilter(Pageable pageable, @RequestParam String questionType, @RequestParam String location, @RequestParam int id){	
		return questionService.getAllUnconfirmedQuestionsByFilter(pageable, questionType, location, id);
	}
	
	/** @Author Mark Alsip
	 * same as /filter, but returns a list instead of a page
	 * this was necessary because openfeign doesn't like to
	 * pass pages or pageables. something about encoding or
	 * configuration or whatever. so this is specifically for
	 * the openfeign link in the answer service.
	 */
	@GetMapping("/non-paged/filter")
	public List<Question> getAllNonPagedQuestionsByFilter(@RequestParam String questionType, @RequestParam String location, @RequestParam int id){	
		return questionService.getAllNonPagedQuestionsByFilter(questionType, location, id);
	}

} 

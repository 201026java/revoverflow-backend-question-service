package com.revature.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.revature.models.Answer;

@FeignClient(name = "RevOverflow-AnswerService")
@RequestMapping("/answer")
public interface AnswerClient {

	
	/** @Author Mark Alsip
	 * Used to fetch answers from the answer service
	 * @param id is the id of the answer. 
	 */
	@GetMapping("/id/{id}")
	public Answer getAnswerById(@RequestParam Integer id);
	
}

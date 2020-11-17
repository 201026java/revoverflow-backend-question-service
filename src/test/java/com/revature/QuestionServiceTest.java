package com.revature;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Question;
//import com.revature.repositories.AnswerRepository;
import com.revature.repositories.QuestionRepository;
import com.revature.services.QuestionService;
//import com.revature.services.RSSService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTest {
	@Autowired
	QuestionService questionService;
	
	@MockBean
	QuestionRepository questionRepository;
	
//	@MockBean
//	AnswerRepository answerRepository;
//	
//	@MockBean
//	RSSService rssService;
	
	/** @author Hugh Thornhill */
	@Test
	public void getAllQuestionsTest() throws Exception {
		Question question = new Question(1, 0, "title","content", LocalDateTime.MIN, LocalDateTime.MIN, null, "New York", true, 1);
		List<Question> questions = new ArrayList<>();
		questions.add(question);
		Page<Question> pageResult = new PageImpl<>(questions);
		when(questionRepository.findAll(Mockito.any(Pageable.class))).thenReturn((pageResult));
		Page<Question> result = questionService.getAllQuestions(PageRequest.of(1, 5));
		assertThat(pageResult).contains(question);
		assertEquals(pageResult, result);
	}
	
	/** @author Hugh Thornhill */
	@Test
	public void getAllQuestionsByUserID() throws Exception {
		Question question = new Question(1, 0, "title","content", LocalDateTime.MIN, LocalDateTime.MIN, null, "New York", true, 1);
		List<Question> questions = new ArrayList<>();
		questions.add(question);
		Page<Question> pageResult = new PageImpl<Question>(questions);
		when(questionRepository.getAllQuestionsByUserID(Mockito.any(Pageable.class), Mockito.anyInt()))
				.thenReturn((pageResult));
		Page<Question> result = questionService.getAllQuestionsByUserId(PageRequest.of(1, 5), 1);
		assertThat(result).contains(question);
	}
	
	/** @author ken */
	@Test
	public void getAllQuestionsByID() throws Exception {
		Question question = new Question(1, 0, "title","content", LocalDateTime.MIN, LocalDateTime.MIN, null, "New York", true, 1);
		when(questionRepository.findById( Mockito.anyInt()))
		.thenReturn(Optional.of(question));
		Question result = questionService.findById(1);
		assertEquals(result, question);
	}
	
	/** @author James */
	@Test
	public void updateQuestion() throws Exception {
		Question question = new Question(1, 0, "title","content", LocalDateTime.MIN, LocalDateTime.MIN, null, "New York", true, 1);
	
		when(questionRepository.save(Mockito.any(Question.class)))
		.thenReturn(question);
		Question result = questionService.save(question);
		assertEquals(result, question);
	}
	
	/**@author Bukadiri Trawally*/
	@Test
	public void updateQuestionAcceptedAnswerId_will_return_question() throws Exception {
		Question q = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, null, "New York", false, 1);
		Question q1 = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, null, "New York", true, 1);
		Mockito.when(questionRepository.save(q)).thenReturn(q1);
		
		Question q2 = questionService.updateQuestionAcceptedAnswerId(q);
		//System.out.println(q2);
		
		assertEquals(q1,q2);
	}
	
	/**@author Bukadiri Trawally*/
	@Test(expected = HttpClientErrorException.class)
	public void updateQuestionAcceptedAnswerId_will_return_bad_request() {
		//Intentional send question with id = 0
		Question q2 = new Question(0,1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, null, "New York", true, 1);
		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(null);
		
		Question q3 = questionService.updateQuestionAcceptedAnswerId(q2);
		assertNotEquals(q2, q3);
		
	}
	
	/**@author Bukadiri Trawally*/
	@Test(expected = HttpClientErrorException.class)
	public void updateQuestionStatus_will_return_bad_request() {
		//Intentional send question with id = 0
		Question q2 = new Question(0,1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, null, "New York", true, 1);
		Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(null);
		
		Question q3 = questionService.updateQuestionStatus(q2, 0);
		
	}
}

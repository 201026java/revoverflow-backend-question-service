package com.revature.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{

	/**@author ken*/
	Page<Question> getAllQuestionsByUserID(Pageable pageable, int id);

	/**@author ken*/
	@Query("FROM Question s WHERE :status = s.status")
	Page<Question> getQuestionsByStatus(Pageable pageable, boolean status);

	
	/** @Author Mark Alsip
	 * Accesses specific data as explained in the service and controller.
	 * Specifically for the filter.
	 */
	@Query("FROM Question s WHERE question_type = :questionType")
	Page<Question> getAllQuestionsByQuestionType(Pageable pageable, String questionType);
	
	@Query("FROM Question s WHERE question_type = :questionType AND user_id = :id")
	Page<Question> getAllQuestionsByQuestionTypeAndUserId(Pageable pageable, String questionType, int id);
	
	@Query("FROM Question s WHERE location = :location")
	Page<Question> getAllQuestionsByLocation(Pageable pageable, String location);
	
	@Query("FROM Question s WHERE location = :location AND user_id = :id")
	Page<Question> getAllQuestionsByLocationAndUserId(Pageable pageable, String location, int id);
	
	
	/** @Author Mark Alsip
	 * Accesses specific data as explained in the service and controller.
	 * Specifically for the filter where unconfirmed is needed.
	 */
	@Query("FROM Question s WHERE question_type = :questionType AND status = false")
	Page<Question> getAllUnconfirmedQuestionsByQuestionType(Pageable pageable, String questionType);
	
	@Query("FROM Question s WHERE question_type = :questionType AND user_id = :id AND status = false")
	Page<Question> getAllUnconfirmedQuestionsByQuestionTypeAndUserId(Pageable pageable, String questionType, int id);
	
	@Query("FROM Question s WHERE location = :location AND status = false")
	Page<Question> getAllUnconfirmedQuestionsByLocation(Pageable pageable, String location);
	
	@Query("FROM Question s WHERE location = :location AND user_id = :id AND status = false")
	Page<Question> getAllUnconfirmedQuestionsByLocationAndUserId(Pageable pageable, String location, int id);
	
	
	/** @Author Mark Alsip
	 * Accesses specific data as explained in the service and controller.
	 * Specifically for the openfeign endpoint for the answer service.
	 */
	@Query("FROM Question s WHERE question_type = :questionType")
	List<Question> getAllNonPagedQuestionsByQuestionType(String questionType);
	
	@Query("FROM Question s WHERE question_type = :questionType AND user_id = :id")
	List<Question> getAllNonPagedQuestionsByQuestionTypeAndUserId(String questionType, int id);
	
	@Query("FROM Question s WHERE location = :location")
	List<Question> getAllNonPagedQuestionsByLocation(String location);
	
	@Query("FROM Question s WHERE location = :location AND user_id = :id")
	List<Question> getAllNonPagedQuestionsByLocationAndUserId(String location, int id);
}
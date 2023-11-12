package com.example.questionnaire.rep;

import com.example.questionnaire.models.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
}

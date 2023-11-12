package com.example.questionnaire.rep;

import com.example.questionnaire.models.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}

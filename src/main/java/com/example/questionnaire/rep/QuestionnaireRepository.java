package com.example.questionnaire.rep;

import com.example.questionnaire.models.Questionnaire;
import org.springframework.data.repository.CrudRepository;

public interface QuestionnaireRepository extends CrudRepository<Questionnaire, Long> {

}

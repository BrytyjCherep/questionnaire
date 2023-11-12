package com.example.questionnaire.controllers;

import com.example.questionnaire.models.Answer;
import com.example.questionnaire.models.Question;
import com.example.questionnaire.models.Questionnaire;
import com.example.questionnaire.rep.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    private Questionnaire TempQuestionnaire = new Questionnaire();
    private Question TempQuestion = new Question();

    @GetMapping("/questionnaires/add")
    public String questionnaireAdd(Model model) {
        model.addAttribute("title", "Страница добавления анкет");
        model.addAttribute("questionnaire", TempQuestionnaire);
        return "questionnaireAdd";
    }

    @PostMapping("/questionnaires/add")
    public String questionnairePostAdd(@RequestParam String title,
                                       Model model) {
        TempQuestionnaire.setTitle(title);
        questionnaireRepository.save(TempQuestionnaire);
        TempQuestionnaire = new Questionnaire();
        return "redirect:/";
    }

    @GetMapping("/questionnaires/addQuestion")
    public String questionnaireAddQuestion(Model model) {
        model.addAttribute("question", TempQuestion);
        return "questionAdd";
    }

    @PostMapping("/questionnaires/addQuestion")
    public String questionnaireAddQuestionPost(@RequestParam String questionText,
                                               @RequestParam int rightAnswerIdx,
                                               Model model) {
        TempQuestion.setText(questionText);
        TempQuestion.setRightAnswerIdx(rightAnswerIdx);
        TempQuestionnaire.getQuestions().add(TempQuestion);
        TempQuestion = new Question();
        return "redirect:/questionnaires/add";
    }

    @PostMapping("/questionnaires/addAnswer")
    public String questionnaireAddAnswerPost(@RequestParam String answerText, Model model) {
        Answer answer = new Answer(answerText);
        TempQuestion.getAnswers().add(answer);
        return "redirect:/questionnaires/addQuestion";
    }

    @GetMapping("/questionnaires/delete")
    public String questionnaireDelete(Model model) {
        Iterable<Questionnaire> questionnaires = questionnaireRepository.findAll();
        model.addAttribute("questionnaires", questionnaires);
        return "questionnaireDelete";
    }

    @PostMapping("/questionnaires/delete")
    public String questionnaireDeleteById(@RequestParam Long id, Model model) {
        questionnaireRepository.deleteById(id);
        return "redirect:/questionnaires/delete";
    }
}

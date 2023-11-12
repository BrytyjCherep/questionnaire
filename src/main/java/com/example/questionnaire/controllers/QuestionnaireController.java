package com.example.questionnaire.controllers;

import com.example.questionnaire.models.Answer;
import com.example.questionnaire.models.Question;
import com.example.questionnaire.models.Questionnaire;
import com.example.questionnaire.models.Role;
import com.example.questionnaire.rep.QuestionRepository;
import com.example.questionnaire.rep.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class QuestionnaireController {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private QuestionRepository questionRepository;
    private List<Long> list = new ArrayList<>();
    private Questionnaire TempQuestionnaire = new Questionnaire();
    private Question TempQuestion = new Question();
    private int score = 0;

    @GetMapping("/")
    public String questionnaire(Model model) {
        Iterable<Questionnaire> questionnaires = questionnaireRepository.findAll();
        model.addAttribute("questionnaires", questionnaires);
        score = 0;
        list.clear();
        return "questionnaires";

    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("user", user);
        score = 0;
        list.clear();
        if (user.getUsername().equals("admin")){
            return "adminProfile";
        }
        else{
            return "profile";
        }

    }

    @GetMapping("/questionnaires/{id}")
    public String questionnaireDetails(@PathVariable(value = "id") long id,
                                       Model model) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
        Questionnaire q = questionnaire.get();
        model.addAttribute("questionnaire", q);
        model.addAttribute("score", score);
        return "questionnaire-details";
    }

    @GetMapping("/questionnaires/like/{id}")
    public String questionnaireLike(@PathVariable(value = "id") long id,
                                       Model model) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
        Questionnaire q = questionnaire.get();
        q.incRating();
        questionnaireRepository.deleteById(id);
        questionnaireRepository.save(q);

        return "redirect:/";
    }

    @PostMapping("/questionnaires/checkAnswer")
    public String checkAnswer(@RequestParam Long questionId,
                              @RequestParam int rightAnswer,
                              @RequestParam Long questionnaireId,
                              Model model) {
        Question question = questionRepository.findById(questionId).get();
        if((rightAnswer == question.getRightAnswerIdx()) && (!list.contains(questionId))){
            score++;
            list.add(questionId);
        }

        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(questionnaireId);
        Questionnaire q = questionnaire.get();

        model.addAttribute("questionnaire", q);
        model.addAttribute("score", score);
        return "questionnaire-details";
    }

    @GetMapping("/questionnaires/checkAnswer")
    public String checkAnswerGet(Model model) {
        return "redirect:/";
    }


}

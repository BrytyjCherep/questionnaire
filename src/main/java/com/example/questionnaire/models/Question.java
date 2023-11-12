package com.example.questionnaire.models;

import jakarta.persistence.*;
import lombok.Data;


import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "question")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private int rightAnswerIdx;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private List<Answer> answers = new LinkedList<>();


    public Question(){

    }
    public Question(String text, int rightAnswerIdx, List<Answer> answers) {
        this.text = text;
        this.rightAnswerIdx = rightAnswerIdx;
        this.answers = answers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRightAnswerIdx() {
        return rightAnswerIdx;
    }

    public void setRightAnswerIdx(int rightAnswerIdx) {
        this.rightAnswerIdx = rightAnswerIdx;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}

package com.example.questionnaire.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "answer")
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Answer() {
    }

    public Answer(String text) {
        this.text = text;
    }
}

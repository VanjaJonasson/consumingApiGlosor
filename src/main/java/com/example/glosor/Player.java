package com.example.glosor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Transient
    private List<String> answers = new ArrayList<>();
    @Transient
    private int num;
    @Transient
    private int numCorrect;
    @Transient
    private int numWrong;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
        this.num = 0;
    }

    public void clearAnswers() {
        answers.clear();
    }

    public void addToWrongAnswers(String s) {
        answers.add(s);
    }

    public void saveAnswers(List<String> a) {
        this.answers = a;
    }

    public List getAnswers()   {
        return answers;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public void setNumCorrect(int numCorrect) {
        this.numCorrect = numCorrect;
    }

    public int getNumWrong() {
        return numWrong;
    }

    public void setNumWrong(int numWrong) {
        this.numWrong = numWrong;
    }

    public void increaseNum() {
        num++;
    }

    public void increaseNumCorrect() {
        numCorrect++;
    }

    public void increaseNumWrong() {
        numWrong++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

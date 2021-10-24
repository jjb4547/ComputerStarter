package com.example.computerstarter;

import java.util.ArrayList;

public class User {
    public String username;
    public String email;
    public int age;
    public String name;
    public ArrayList quiz;
    public User(String name, String email, int age, String username){
        this.age = age;
        this.email = email;
        this.name = name;
        this.username = username;
    }
    public User(){}
    public void setQuiz(ArrayList<String> quiz){
        this.quiz = quiz;
    }

    public ArrayList getQuiz() {
        return quiz;
    }
}

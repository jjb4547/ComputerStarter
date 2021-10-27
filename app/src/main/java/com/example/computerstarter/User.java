package com.example.computerstarter;

import java.util.ArrayList;

public class User {
    private String username;
    private String email;
    private int age;
    private String name;
    private ArrayList quiz;
    private String documentId;
    public User(String name, String email, int age, String username){
        this.age = age;
        this.email = email;
        this.name = name;
        this.username = username;
    }
    public User(){}
    public String getDocumentId(){ return documentId; }

    public int getAge() { return age; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public void setAge(int age) { this.age = age; }

    public void setDocumentId(String documentId) { this.documentId = documentId; }

    public void setEmail(String email) { this.email = email; }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setQuiz(ArrayList quiz) { this.quiz = quiz; }

    public ArrayList getQuiz() {
        return quiz;
    }

}

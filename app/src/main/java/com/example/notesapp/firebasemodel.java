package com.example.notesapp;

import com.google.firebase.database.PropertyName;

public class firebasemodel {
    private static String title;
    private static String content;

    public firebasemodel() {
        // Default constructor required for Firestore
    }
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public firebasemodel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter and setter for title

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter for content



    public void setContent(String content) {
        this.content = content;
    }
}

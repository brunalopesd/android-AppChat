package com.brunaLopes.appchat.servidor;

public class Usuario {
    private static final Usuario instance = new Usuario();

    private String userId;
    private String currentContact;


    public static Usuario getInstance() {
        return instance;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCurrentContact() {
        return currentContact;
    }

    public void setCurrentContact(String currentContact) {
        this.currentContact = currentContact;
    }
}

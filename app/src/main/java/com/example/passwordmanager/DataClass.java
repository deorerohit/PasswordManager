package com.example.passwordmanager;

public class DataClass {

    private long id;
    private String title;
    private String username;
    private String password;

    public DataClass(String title, String username, String password) {
        this.title = title;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}

package com.dbexercise.domain;

public class User {
    private String id;
    private String name;
    private String password;

    // 생성자1
    public User() {
    }

    // 생성자2
    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    // getter
    public String getId() {
        return id;
    }

    // getter
    public String getName() {
        return name;
    }

    // getter
    public String getPassword() {
        return password;
    }
}

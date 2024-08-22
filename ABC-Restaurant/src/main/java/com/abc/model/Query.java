package com.abc.model;

public class Query {
    private int id;
    private Integer userId; // Nullable, used for logged-in users
    private String name;     // Nullable, used for external users
    private String email;    // Nullable, used for external users
    private String query;
    private String response;

    public Query() {}

    public Query(int id, Integer userId, String name, String email, String query, String response) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.query = query;
        this.response = response;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

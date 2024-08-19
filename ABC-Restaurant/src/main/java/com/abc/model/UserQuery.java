package com.abc.model;

public class UserQuery {
    private int id;
    private int userId; // foreign key from User
    private String query;
    private String response;

    public UserQuery() {}

    public UserQuery(int id, int userId, String query, String response) {
        this.id = id;
        this.userId = userId;
        this.query = query;
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

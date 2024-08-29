package com.abc.model;

public class Query {
    private int id;
    private Integer userId;
    private String query;
    private String response;

    public Query() {}

    public Query(int id, Integer userId, String query, String response) {
        this.id = id;
        this.userId = userId;
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

package com.abc.model;

public class ExternalQuery {
    private int id;
    private String name;
    private String email;
    private String query;

    public ExternalQuery() {}

    public ExternalQuery(int id, String name, String email, String query) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.query = query;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

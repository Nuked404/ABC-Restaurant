package com.abc.service;

import com.abc.dao.QueryDAO;
import com.abc.model.Query;

import java.util.List;

public class QueryService {
    private static volatile QueryService instance;
    private QueryDAO queryDAO;

    private QueryService() {
        this.queryDAO = new QueryDAO();
    }

    public static QueryService getInstance() {
        if (instance == null) {
            synchronized (QueryService.class) {
                if (instance == null) {
                    instance = new QueryService();
                }
            }
        }
        return instance;
    }

    public void addQuery(Query query) {
        queryDAO.addQuery(query);
    }

    public List<Query> getQueriesByUserId(int userId) {
        return queryDAO.getQueriesByUserId(userId);
    }

    public List<Query> getAllQueries() {
        return queryDAO.getAllQueries();
    }
    
    public Query getQueryById(int id) {
        return queryDAO.getQueryById(id);
    }

    public List<Query> getUnrespondedQueries() {
        return queryDAO.getUnrespondedQueries();
    }

    // Updated method to use query ID and response
    public void updateQueryResponse(int queryId, String response) {
        queryDAO.updateResponse(queryId,response); // Admin Side
    }
    
    public void updateQuery(int queryId, String query) {
        queryDAO.updateQuery(queryId,query); // User side
    }

    public void deleteQuery(int id) {
        queryDAO.deleteQuery(id);
    }
}

package com.abc.service;

import com.abc.dao.BranchDAO;
import com.abc.model.Branch;

import java.sql.SQLException;
import java.util.List;

public class BranchService {

    private static BranchService instance;
    private BranchDAO branchDAO;

    private BranchService() {
        this.branchDAO = new BranchDAO();
    }

    public static BranchService getInstance() {
        if (instance == null) {
            synchronized (BranchService.class) {
                if (instance == null) {
                    instance = new BranchService();
                }
            }
        }
        return instance;
    }

    // Add a new branch
    public void addBranch(Branch branch) {
        branchDAO.addBranch(branch);
    }

    // Retrieve a branch by ID
    public Branch getBranchById(int id) {
        return branchDAO.getBranchById(id);
    }

    // Retrieve all branches
    public List<Branch> getAllBranches() throws SQLException {
        return branchDAO.getAllBranches();
    }

    // Update an existing branch
    public void updateBranch(Branch branch) {
        branchDAO.updateBranch(branch);
    }

    // Delete a branch by ID
    public void deleteBranch(int id) {
        branchDAO.deleteBranch(id);
    }
}

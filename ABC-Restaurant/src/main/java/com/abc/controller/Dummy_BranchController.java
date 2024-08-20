package com.abc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.model.Branch;
import com.abc.service.BranchService;

@WebServlet("/branch")
public class Dummy_BranchController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private BranchService branchService;

    @Override
    public void init() throws ServletException {
        branchService = BranchService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.equals("list")) {
            listBranches(request, response);
        } else if (action.equals("add")) {
            showAddForm(request, response);
        } else if (action.equals("edit")) {
            showEditForm(request, response);
        } else if (action.equals("delete")) {
            deleteBranch(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("add")) {
            addBranch(request, response);
        } else if (action.equals("update")) {
            updateBranch(request, response);
        }
    }

    private void listBranches(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Branch> branchList = new ArrayList<>();
        try {
            branchList = branchService.getAllBranches();
            request.setAttribute("branches", branchList);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("WEB-INF/view/listBranches.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/addBranch.jsp").forward(request, response);
    }

    private void addBranch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String location = request.getParameter("location");
        Branch branch = new Branch();
        branch.setLocation(location);
        branchService.addBranch(branch);
        //response.sendRedirect("branch?action=list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Branch branch = branchService.getBranchById(id);
        request.setAttribute("branch", branch);
        request.getRequestDispatcher("WEB-INF/view/editBranch.jsp").forward(request, response);
    }

    private void updateBranch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String location = request.getParameter("location");
        Branch branch = new Branch();
        branch.setId(id);
        branch.setLocation(location);
        branchService.updateBranch(branch);
        response.sendRedirect("branch?action=list");
    }

    private void deleteBranch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        branchService.deleteBranch(id);
        response.sendRedirect("branch?action=list");
    }
}

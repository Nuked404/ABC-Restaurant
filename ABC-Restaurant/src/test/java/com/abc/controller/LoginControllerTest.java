package com.abc.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abc.enums.UserRole;
import com.abc.service.UserService;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
    	//resetSingleton();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoGet_WithLogoutAction() throws ServletException, IOException {
        // Mocking request parameters and session
        when(request.getParameter("Logout")).thenReturn("true");
        when(request.getSession(false)).thenReturn(session);

        // Mock RequestDispatcher
        RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("WEB-INF/view/login.jsp")).thenReturn(mockDispatcher);

        // Execute the method under test
        loginController.doGet(request, response);

        // Verify interactions
        verify(userService).logoutUser(session);
        verify(request).getRequestDispatcher("WEB-INF/view/login.jsp");
        verify(mockDispatcher).forward(request, response);
    }


    @Test
    void testDoPost_SuccessfulLogin_AdminRole() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("admin@example.com");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getSession(false)).thenReturn(session);
        when(userService.authenticateUser(session, "admin@example.com", "password")).thenReturn(true);
        when(userService.hasRole(session, UserRole.ADMIN)).thenReturn(true);

        loginController.doPost(request, response);

        verify(response).sendRedirect("DashboardReport");
    }

    @Test
    void testDoPost_SuccessfulLogin_StaffRole() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("staff@example.com");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getSession(false)).thenReturn(session);
        when(userService.authenticateUser(session, "staff@example.com", "password")).thenReturn(true);
        lenient().when(userService.hasRole(session, UserRole.STAFF)).thenReturn(true);

        loginController.doPost(request, response);

        verify(response).sendRedirect("DashboardMenu");
    }

    @Test
    void testDoPost_SuccessfulLogin_CustomerRole() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("customer@example.com");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getSession(false)).thenReturn(session);
        when(userService.authenticateUser(session, "customer@example.com", "password")).thenReturn(true);
        lenient().when(userService.hasRole(session, UserRole.CUSTOMER)).thenReturn(true);

        loginController.doPost(request, response);

        verify(response).sendRedirect("index.jsp");
    }

    @Test
    void testDoPost_FailedLogin() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("user@example.com");
        when(request.getParameter("password")).thenReturn("wrongpassword");
        when(request.getSession(false)).thenReturn(session);
        when(userService.authenticateUser(session, "user@example.com", "wrongpassword")).thenReturn(false);

        loginController.doPost(request, response);

        verify(response).sendRedirect("Login?error=true");
    }

}

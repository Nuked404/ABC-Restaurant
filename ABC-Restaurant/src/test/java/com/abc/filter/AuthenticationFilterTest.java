package com.abc.filter;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.enums.UserRole;
import com.abc.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthenticationFilterTest {

    @InjectMocks
    private AuthenticationFilter authenticationFilter;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private FilterChain chain;
    
    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void testDoFilter_PublicPage() throws IOException, ServletException {
        // Arrange
        when(request.getRequestURI()).thenReturn("/contextPath/index.jsp");
        when(request.getContextPath()).thenReturn("/contextPath");

        // Act
        authenticationFilter.doFilter(request, response, chain);

        // Assert
        verify(chain, times(1)).doFilter(request, response);  // Public pages should allow the request
    }

    @Test
    void testDoFilter_LoggedInAsAdmin() throws IOException, ServletException {
        // Arrange
        when(request.getRequestURI()).thenReturn("/contextPath/Dashboard");
        when(request.getContextPath()).thenReturn("/contextPath");
        when(request.getSession(false)).thenReturn(session);
        when(userService.isLoggedIn(session)).thenReturn(true);
        when(userService.hasRole(session, UserRole.ADMIN)).thenReturn(true);

        // Act
        authenticationFilter.doFilter(request, response, chain);

        // Assert
        verify(chain, times(1)).doFilter(request, response);  // Admin should have access to the dashboard
    }

    @Test
    void testDoFilter_LoggedInAsEmployee_NoAdminAccess() throws IOException, ServletException {
        // Arrange
        when(request.getRequestURI()).thenReturn("/contextPath/DashboardEmployee");
        when(request.getContextPath()).thenReturn("/contextPath");
        when(request.getSession(false)).thenReturn(session);
        when(userService.isLoggedIn(session)).thenReturn(true);
        when(userService.hasRole(session, UserRole.STAFF)).thenReturn(true);

        // Mock the request dispatcher to avoid NullPointerException
        when(request.getRequestDispatcher("WEB-INF/view/accessdenied.jsp")).thenReturn(requestDispatcher);

        // Act
        authenticationFilter.doFilter(request, response, chain);

        // Assert
        verify(requestDispatcher, times(1)).forward(request, response); // Ensure the request is forwarded to access denied
        verify(chain, never()).doFilter(request, response);  // The request should not proceed further
    }

    @Test
    void testDoFilter_LoggedInButNotAuthorizedForAdminPage() throws IOException, ServletException {
        // Arrange
        when(request.getRequestURI()).thenReturn("/contextPath/DashboardEmployee");
        when(request.getContextPath()).thenReturn("/contextPath");
        when(request.getSession(false)).thenReturn(session);
        when(userService.isLoggedIn(session)).thenReturn(true);
        when(userService.hasRole(session, UserRole.CUSTOMER)).thenReturn(true);

        // Mock the request dispatcher
        when(request.getRequestDispatcher("WEB-INF/view/accessdenied.jsp")).thenReturn(mock(javax.servlet.RequestDispatcher.class));

        // Act
        authenticationFilter.doFilter(request, response, chain);

        // Assert
        verify(request, times(1)).getRequestDispatcher("WEB-INF/view/accessdenied.jsp");
        verify(request.getRequestDispatcher("WEB-INF/view/accessdenied.jsp"), times(1)).forward(request, response);
    }

    @Test
    void testDoFilter_NotLoggedIn_AccessDenied() throws IOException, ServletException {
        // Arrange
        when(request.getRequestURI()).thenReturn("/contextPath/Dashboard");
        when(request.getContextPath()).thenReturn("/contextPath");
        when(request.getSession(false)).thenReturn(null);  // No session

        // Mock the request dispatcher for access denied
        when(request.getRequestDispatcher("WEB-INF/view/accessdenied.jsp")).thenReturn(mock(javax.servlet.RequestDispatcher.class));

        // Act
        authenticationFilter.doFilter(request, response, chain);

        // Assert
        verify(request, times(1)).getRequestDispatcher("WEB-INF/view/accessdenied.jsp");
        verify(request.getRequestDispatcher("WEB-INF/view/accessdenied.jsp"), times(1)).forward(request, response);
    }
}

package com.abc.dao;

import com.abc.model.Query;
import com.abc.util.DBConnectionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QueryDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private QueryDAO queryDAO;

    private static MockedStatic<DBConnectionFactory> mockedDBConnectionFactory;

    @BeforeAll
    static void setUpStatic() {
        // Setup static mocking once for the entire test class
        mockedDBConnectionFactory = mockStatic(DBConnectionFactory.class);
    }

    @AfterAll
    static void tearDownStatic() {
        // Deregister the static mock after all tests are done
        mockedDBConnectionFactory.close();
    }

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        queryDAO = new QueryDAO();

        // Set up the mock connection for each test
        when(DBConnectionFactory.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void testAddQuery() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        Query query = new Query(1, 1, "Test Query", null);
        queryDAO.addQuery(query);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setString(2, "Test Query");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetQueriesByUserId() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("user_id")).thenReturn(1);
        when(mockResultSet.getString("query")).thenReturn("Test Query");
        when(mockResultSet.getString("response")).thenReturn("Test Response");

        List<Query> queries = queryDAO.getQueriesByUserId(1);

        assertNotNull(queries);
        assertEquals(1, queries.size());
        Query query = queries.get(0);
        assertEquals(1, query.getId());
        assertEquals(1, query.getUserId());
        assertEquals("Test Query", query.getQuery());
        assertEquals("Test Response", query.getResponse());
    }

    @Test
    void testGetAllQueries() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getObject("user_id", Integer.class)).thenReturn(1);
        when(mockResultSet.getString("query")).thenReturn("Test Query");
        when(mockResultSet.getString("response")).thenReturn("Test Response");

        List<Query> queries = queryDAO.getAllQueries();

        assertNotNull(queries);
        assertEquals(1, queries.size());
        Query query = queries.get(0);
        assertEquals(1, query.getId());
        assertEquals(1, query.getUserId());
        assertEquals("Test Query", query.getQuery());
        assertEquals("Test Response", query.getResponse());
    }

    @Test
    void testGetQueryById() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getObject("user_id", Integer.class)).thenReturn(1);
        when(mockResultSet.getString("query")).thenReturn("Test Query");
        when(mockResultSet.getString("response")).thenReturn("Test Response");

        Query query = queryDAO.getQueryById(1);

        assertNotNull(query);
        assertEquals(1, query.getId());
        assertEquals(1, query.getUserId());
        assertEquals("Test Query", query.getQuery());
        assertEquals("Test Response", query.getResponse());
    }

    @Test
    void testUpdateQuery() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        queryDAO.updateQuery(1, "Updated Query");

        verify(mockPreparedStatement).setString(1, "Updated Query");
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testUpdateResponse() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        queryDAO.updateResponse(1, "Updated Response");

        verify(mockPreparedStatement).setString(1, "Updated Response");
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetUnrespondedQueries() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getObject("user_id", Integer.class)).thenReturn(1);
        when(mockResultSet.getString("query")).thenReturn("Test Query");
        when(mockResultSet.getString("response")).thenReturn("");

        List<Query> queries = queryDAO.getUnrespondedQueries();

        assertNotNull(queries);
        assertEquals(1, queries.size());
        Query query = queries.get(0);
        assertEquals(1, query.getId());
        assertEquals(1, query.getUserId());
        assertEquals("Test Query", query.getQuery());
        assertEquals("", query.getResponse());
    }

    @Test
    void testDeleteQuery() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        queryDAO.deleteQuery(1);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }
}

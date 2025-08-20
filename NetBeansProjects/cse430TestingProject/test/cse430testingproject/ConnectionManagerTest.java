package cse430testingproject;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionManagerTest {

    private Connection conn;

    @Before
    public void setUp() {
        conn = ConnectionManager.getConnection();
    }

    @After
    public void tearDown() {
        ConnectionManager.CloseConnection();
        conn = null;
    }

    // Valid connection should not be null
    @Test
    public void testGetConnection_NotNull() {
        assertNotNull("Connection should not be null if DB is configured correctly", conn);
    }

    // ConnectionManager should return the same instance (Singleton)
    @Test
    public void testGetConnection_SameInstance() {
        Connection conn2 = ConnectionManager.getConnection();
        assertSame("Both calls should return the same connection instance", conn, conn2);
    }

    // Closing connection should make it unusable
    @Test
    public void testCloseConnection() {
        assertNotNull(conn);
        ConnectionManager.CloseConnection();
        try {
            conn.createStatement();
            fail("Using a closed connection should throw SQLException");
        } catch (SQLException e) {
            // expected
        }
    }

    // Multiple close calls should not break anything
    @Test
    public void testCloseConnectionMultipleTimes() {
        ConnectionManager.CloseConnection();
        ConnectionManager.CloseConnection(); // Should not throw error
        assertTrue("Closing multiple times should not cause exception", true);
    }

    // Corner case: Invalid properties should not crash the system
    @Test
    public void testConnectionInvalidProperties() {
        System.setProperty("mysql.url", "jdbc:mysql://invalid:3306/fake_db");
        Connection badConn = ConnectionManager.getConnection();
        assertNotNull("Even with invalid properties, should handle gracefully", badConn);
    }
}

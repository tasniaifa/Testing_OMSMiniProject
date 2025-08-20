package cse430testingproject;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Map.Entry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tasniafarinifa
 */
public class PurchaseOrderDAOTest {

    private PurchaseOrderDAO dao;

    public PurchaseOrderDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        dao = new PurchaseOrderDAO();
    }

    @After
    public void tearDown() {
    }

    /** Test of getCon method, of class PurchaseOrderDAO. */
    // TC_01: Check if connection is not null
    @Test
    public void testGetCon_NotNull() {
        PurchaseOrderDAO dao = new PurchaseOrderDAO();
        Connection con = dao.getCon();
        assertNotNull("Connection should not be null if DB is connected properly.", con);
    }

    // TC_02: Check if the connection is valid (Optional, may throw SQLException)
    @Test
    public void testGetCon_IsValid() throws Exception {
        PurchaseOrderDAO dao = new PurchaseOrderDAO();
        Connection con = dao.getCon();
        // con.isValid(timeout) might throw SQLException, so throws Exception declared
        assertTrue("Connection should be valid.", con.isValid(2));
    }

    // TC_03: Ensure multiple DAO instances share same connection (singleton behavior)
    @Test
    public void testGetCon_SameConnectionInstance() {
        PurchaseOrderDAO dao1 = new PurchaseOrderDAO();
        PurchaseOrderDAO dao2 = new PurchaseOrderDAO();
        assertEquals("Same connection instance expected across DAO objects.", dao1.getCon(), dao2.getCon());
    }

    // TC_04: Check if connection is closed manually (simulating a bad use case)
    @Test
    public void testGetCon_ClosedConnection() throws Exception {
        PurchaseOrderDAO dao = new PurchaseOrderDAO();
        Connection con = dao.getCon();
        con.close(); // forcefully closing connection
        assertTrue("Connection should be closed now.", con.isClosed());
    }

    /** Test of getLastInsertID method, of class PurchaseOrderDAO. */
    // TC_01: After adding a PurchaseOrder → expect > 0
    @Test
    public void testGetLastInsertID_AfterAdd() {
        // Create a dummy PurchaseOrder and insert it
        PurchaseOrder p = new PurchaseOrder();
        p.setCustomerNo(1);
        p.setOrderDate(LocalDate.now().minusDays(1));
        p.setShipDate(LocalDate.now());
        p.setShipped(false);

        dao.addPurchaseOrder(p);
        int lastId = dao.getLastInsertID();
        assertTrue("After insert, ID should be > 0", lastId > 0);
    }

    // TC_03: Connection closed beforehand → expect -1 (catch(SQLException))
    @Test
    public void testGetLastInsertID_ClosedConnection() throws Exception {
        Connection con = dao.getCon();
        con.close(); // Connection closed manually
        int result = dao.getLastInsertID();
        assertEquals("Closed connection should return -1", -1, result);
    }

    // TC_04: Concurrent calls in multiple threads → expect no exceptions, IDs >= 0
    @Test
    public void testGetLastInsertID_MultiThreaded() throws InterruptedException {
        Runnable task = () -> {
            int id = dao.getLastInsertID();
            assertTrue("Thread should get a non-negative ID", id >= 0);
        };
        Thread t1 = new Thread(task), t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    /** Test of addPurchaseOrder method, of class PurchaseOrderDAO. */
    // TC_01: Add valid PurchaseOrder - expect no exception and data inserted
    @Test
    public void testAddPurchaseOrder_Valid() {
        PurchaseOrder p = new PurchaseOrder();
        p.setCustomerNo(1);
        p.setOrderDate(LocalDate.now().minusDays(2));
        p.setShipDate(LocalDate.now().plusDays(3));
        p.setShipped(false);

        try {
            dao.addPurchaseOrder(p);
        } catch (Exception e) {
            fail("Exception should not be thrown for valid purchase order: " + e.getMessage());
        }
    }

    // TC_02: Passing null should throw NullPointerException
    @Test(expected = NullPointerException.class)
    public void testAddPurchaseOrder_NullShouldThrow() {
        dao.addPurchaseOrder(null);
    }

    // TC_03: Add PurchaseOrder with invalid data (e.g. negative customerNo)
    @Test
    public void testAddPurchaseOrder_InvalidData() {
        PurchaseOrder p = new PurchaseOrder();
        p.setCustomerNo(-5); // Invalid customer number
        p.setOrderDate(LocalDate.now());
        p.setShipDate(LocalDate.now());
        p.setShipped(true);

        try {
            dao.addPurchaseOrder(p);
        } catch (Exception e) {
            fail("Exception should not be thrown even if data is invalid: " + e.getMessage());
        }
    }

    // TC_04: Add PurchaseOrder with shipDate before orderDate (logical invalid)
    @Test
    public void testAddPurchaseOrder_ShipDateBeforeOrderDate() {
        PurchaseOrder p = new PurchaseOrder();
        p.setCustomerNo(1);
        p.setOrderDate(LocalDate.now());
        p.setShipDate(LocalDate.now().minusDays(1)); // Ship date before order date
        p.setShipped(false);

        try {
            dao.addPurchaseOrder(p);
        } catch (Exception e) {
            fail("Exception should not be thrown even if shipDate < orderDate: " + e.getMessage());
        }
    }

    /** Test of getPurchaseOrderByCustomerNo method, of class PurchaseOrderDAO. */
    // TC_01: Valid customerNo with existing orders - expect non-empty list
    @Test
    public void testGetPurchaseOrderByCustomerNo_ValidExisting() {
        int customerNo = 103; // Assumes this customer exists with orders in DB
        List<PurchaseOrder> result = dao.getPurchaseOrderByCustomerNo(customerNo);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        for (PurchaseOrder po : result) {
            assertEquals(customerNo, po.getCustomerNo());
        }
    }

    // TC_02: Valid customerNo with no orders - expect empty list
    @Test
    public void testGetPurchaseOrderByCustomerNo_ValidNoOrders() {
        int customerNo = 99999; // Assumes no orders for this customer in DB
        List<PurchaseOrder> result = dao.getPurchaseOrderByCustomerNo(customerNo);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // TC_03: Invalid customerNo (e.g., negative) - expect empty list
    @Test
    public void testGetPurchaseOrderByCustomerNo_InvalidCustomerNo() {
        int customerNo = -10;
        List<PurchaseOrder> result = dao.getPurchaseOrderByCustomerNo(customerNo);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // TC_04: customerNo = 0 (edge case) - expect empty list
    @Test
    public void testGetPurchaseOrderByCustomerNo_ZeroCustomerNo() {
        int customerNo = 0;
        List<PurchaseOrder> result = dao.getPurchaseOrderByCustomerNo(customerNo);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /** Test of getPurchaseOrderByFromAndToDate method, of class PurchaseOrderDAO. */
    @Test(expected = NullPointerException.class)
    public void testGetPurchaseOrderByFromAndToDate() {
        System.out.println("getPurchaseOrderByFromAndToDate with null dates");
        LocalDate fromDate = null;
        LocalDate toDate = null;
        PurchaseOrderDAO instance = new PurchaseOrderDAO();
        instance.getPurchaseOrderByFromAndToDate(fromDate, toDate);
    }

    // TC_01: Valid date range with expected existing orders
    @Test
    public void testGetPurchaseOrderByFromAndToDate_ValidRangeWithOrders() {
        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2023, 12, 31);
        List<PurchaseOrder> result = dao.getPurchaseOrderByFromAndToDate(fromDate, toDate);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        for (PurchaseOrder po : result) {
            assertTrue(!po.getOrderDate().isBefore(fromDate) && !po.getOrderDate().isAfter(toDate));
        }
    }

    // TC_02: Valid date range but no orders expected
    @Test
    public void testGetPurchaseOrderByFromAndToDate_ValidRangeNoOrders() {
        LocalDate fromDate = LocalDate.of(1900, 1, 1);
        LocalDate toDate = LocalDate.of(1900, 12, 31);
        List<PurchaseOrder> result = dao.getPurchaseOrderByFromAndToDate(fromDate, toDate);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // TC_03: Single day range (fromDate == toDate)
    @Test
    public void testGetPurchaseOrderByFromAndToDate_SingleDayRange() {
        LocalDate date = LocalDate.of(2023, 5, 15);
        List<PurchaseOrder> result = dao.getPurchaseOrderByFromAndToDate(date, date);
        assertNotNull(result);
        for (PurchaseOrder po : result) {
            assertEquals(date, po.getOrderDate());
        }
    }

    // TC_04: Invalid range (fromDate after toDate)
    @Test
    public void testGetPurchaseOrderByFromAndToDate_InvalidRange() {
        LocalDate fromDate = LocalDate.of(2023, 12, 31);
        LocalDate toDate = LocalDate.of(2023, 1, 1);
        List<PurchaseOrder> result = dao.getPurchaseOrderByFromAndToDate(fromDate, toDate);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /** Test of getPurchaseOrderByDate method, of class PurchaseOrderDAO. */
    // TC_01: date = null, expect NullPointerException
    @Test(expected = NullPointerException.class)
    public void testGetPurchaseOrderByDate_NullDate() {
        LocalDate date = null;
        int customerNo = 1;
        PurchaseOrderDAO dao = new PurchaseOrderDAO();
        dao.getPurchaseOrderByDate(date, customerNo);
    }

    // TC_02: Valid date and customerNo, expect list of purchase orders (may be empty)
    @Test
    public void testGetPurchaseOrderByDate_ValidDateAndCustomer() {
        LocalDate date = LocalDate.of(2023, 8, 1);
        int customerNo = 101; // Assume this customerNo exists in DB
        PurchaseOrderDAO dao = new PurchaseOrderDAO();
        List<PurchaseOrder> result = dao.getPurchaseOrderByDate(date, customerNo);
        assertNotNull(result);
    }

    // TC_03: Valid inputs but no orders, expect empty list
    @Test
    public void testGetPurchaseOrderByDate_NoOrders() {
        LocalDate date = LocalDate.of(1900, 1, 1);
        int customerNo = 9999; // Assume no such customer or no orders
        PurchaseOrderDAO dao = new PurchaseOrderDAO();
        List<PurchaseOrder> result = dao.getPurchaseOrderByDate(date, customerNo);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /** Test of getDelayedOrders method, of class PurchaseOrderDAO. */
    // TC_01: Expect list with delayed orders (ship_date < today and is_shipped=0)
    @Test
    public void testGetDelayedOrders_WithDelayedOrders() {
        PurchaseOrderDAO dao = new PurchaseOrderDAO();
        List<PurchaseOrder> result = dao.getDelayedOrders();
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    // TC_02: No delayed orders in DB, expect empty list
    @Test
    public void testGetDelayedOrders_NoDelayedOrders() {
        PurchaseOrderDAO dao = new PurchaseOrderDAO();
        List<PurchaseOrder> result = dao.getDelayedOrders();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /** Test of shipOrder method, of class PurchaseOrderDAO. */
    // TC_01: Valid order number, not shipped yet
    @Test
    public void testShipOrder_ValidNotShipped() {
        int poNumber = 1020;
        dao.shipOrder(poNumber);
    }

    // TC_02: Order already shipped
    @Test
    public void testShipOrder_AlreadyShipped() {
        int poNumber = 1050;
        dao.shipOrder(poNumber);
    }

    // TC_03: Invalid order number (doesn't exist)
    @Test
    public void testShipOrder_InvalidOrderNumber() {
        int poNumber = 9999;
        dao.shipOrder(poNumber);
    }

    // TC_04: Negative order number
    @Test
    public void testShipOrder_NegativeOrderNumber() {
        int poNumber = -1;
        dao.shipOrder(poNumber);
    }

    // TC_05: Zero as order number
    @Test
    public void testShipOrder_ZeroOrderNumber() {
        int poNumber = 0;
        dao.shipOrder(poNumber);
    }

    // TC_06: Simulate SQLException (corner case)
    @Test
    public void testShipOrder_SQLException() {
        try {
            int poNumber = 1020;
            dao.shipOrder(poNumber);
        } catch (Exception e) {
            fail("SQLException should be caught inside shipOrder, not thrown out.");
        }
    }

    /** Test of getTotalShippedOrderMonthly method, of class PurchaseOrderDAO. */
    // TC_01: Valid year with shipped orders present
    @Test
    public void testGetTotalShippedOrderMonthly_ValidYearWithData() {
        int year = 2020;
        List<Entry<Integer, Integer>> result = dao.getTotalShippedOrderMonthly(year);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // TC_02: Valid year with no shipped orders
    @Test
    public void testGetTotalShippedOrderMonthly_ValidYearNoData() {
        int year = 1900;
        List<Entry<Integer, Integer>> result = dao.getTotalShippedOrderMonthly(year);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // TC_03: Invalid year (negative)
    @Test
    public void testGetTotalShippedOrderMonthly_InvalidYear() {
        int year = -1;
        List<Entry<Integer, Integer>> result = dao.getTotalShippedOrderMonthly(year);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // TC_04: Year zero edge case
    @Test
    public void testGetTotalShippedOrderMonthly_YearZero() {
        int year = 0;
        List<Entry<Integer, Integer>> result = dao.getTotalShippedOrderMonthly(year);
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }

    // TC_05: Large future year (no data expected)
    @Test
    public void testGetTotalShippedOrderMonthly_FutureYear() {
        int year = 3000;
        List<Entry<Integer, Integer>> result = dao.getTotalShippedOrderMonthly(year);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /** Test of getTotalAmountCollectedMonthly method, of class PurchaseOrderDAO. */
    // TC_01: Valid year with order data
    @Test
    public void testGetTotalAmountCollectedMonthly_ValidYearWithData() {
        int year = 2025;
        List<Entry<Integer, Double>> result = dao.getTotalAmountCollectedMonthly(year);
        assertNotNull(result);
    }

    // TC_02: Valid year but no orders found
    @Test
    public void testGetTotalAmountCollectedMonthly_ValidYearNoData() {
        int year = 1900;
        List<Entry<Integer, Double>> result = dao.getTotalAmountCollectedMonthly(year);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // TC_03: Invalid year (negative)
    @Test
    public void testGetTotalAmountCollectedMonthly_InvalidYear() {
        int year = -1;
        List<Entry<Integer, Double>> result = dao.getTotalAmountCollectedMonthly(year);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // TC_04: Year zero
    @Test
    public void testGetTotalAmountCollectedMonthly_YearZero() {
        int year = 0;
        List<Entry<Integer, Double>> result = dao.getTotalAmountCollectedMonthly(year);
        assertNotNull(result);
    }

    // TC_05: Future year, no data expected
    @Test
    public void testGetTotalAmountCollectedMonthly_FutureYear() {
        int year = 3000;
        List<Entry<Integer, Double>> result = dao.getTotalAmountCollectedMonthly(year);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /** Test of getAllPurchaseOrders method, of class PurchaseOrderDAO. */
    // TC_01: Fetch all purchase orders when DB has data
    @Test
    public void testGetAllPurchaseOrders_WithData() {
        List<PurchaseOrder> result = dao.getAllPurchaseOrders();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}

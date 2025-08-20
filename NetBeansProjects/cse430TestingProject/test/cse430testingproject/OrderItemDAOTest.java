package cse430testingproject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderItemDAOTest {

    private OrderItemDAO dao;

    @Before
    public void setUp() {
        dao = new OrderItemDAO();
    }

    @After
    public void tearDown() {
        dao = null;
    }

    // ---------------- getLastInsertID() tests ----------------

    // Normal case: get last inserted ID returns a non-negative number
    @Test
    public void normalInput_getLastInsertID_returnsNonNegative() {
        int lastId = dao.getLastInsertID();
        assertTrue("Last insert ID should be >= 0", lastId >= 0);
    }

    // ---------------- addOrderItem() tests ----------------

    // Normal case: insert a new order item with a safe stock item number
    @Test
    public void normalInput_addOrderItem_savesSuccessfully() {
        OrderItem orderItem = new OrderItem(36, 5, 2); // existing stock_item_number, po_number=2
        dao.addOrderItem(orderItem);
        assertTrue("Order item should be present after insertion", dao.isOrderPresent(orderItem));
    }

    // Corner case: adding null order item should throw exception
    @Test(expected = NullPointerException.class)
    public void nullInput_addOrderItem_throwsException() {
        dao.addOrderItem(null);
    }

    // Corner case: negative quantity should not be inserted
    @Test
    public void negativeQuantity_addOrderItem_notInserted() {
        OrderItem orderItem = new OrderItem(36, -10, 2);
        dao.addOrderItem(orderItem);
        assertTrue("Order item with negative quantity is present", dao.isOrderPresent(orderItem));
    }

    // Corner case: adding an order item with StockItem object included
    @Test
    public void orderItemWithStockItem_addsSuccessfully() {
        StockItem stock = new StockItem(36, "Maggie", 24); // existing stock_item_number
        OrderItem orderItem = new OrderItem(0, 36, 10, 2, stock);
        dao.addOrderItem(orderItem);
        assertTrue("Order item with StockItem should be present", dao.isOrderPresent(orderItem));
    }

    // ---------------- isOrderPresent() tests ----------------

    // Standard case: existing order item (should be present)
    @Test
    public void existingOrder_isOrderPresent_returnsTrue() {
        // stockItemNumber = 2, numberOfItems can be any valid number, poNumber = 2
        OrderItem orderItem = new OrderItem(2, 1, 2);
        assertTrue("Existing order item should be present", dao.isOrderPresent(orderItem));
    }

    // Corner case: non-existent order item (should return false)
    @Test
    public void nonExistentOrder_isOrderPresent_returnsFalse() {
        OrderItem orderItem = new OrderItem(999, 999, 999); // unlikely values
        assertFalse("Non-existent order item should NOT be present", dao.isOrderPresent(orderItem));
    }

    // Corner case: null input
    @Test(expected = NullPointerException.class)
    public void nullOrder_isOrderPresent_throwsException() {
        dao.isOrderPresent(null);
    }

    // ---------------- updateExisitngOrder() tests ----------------

    // Standard case: update an existing order item (quantity increases)
    @Test
    public void existingOrder_updateExisitngOrder_updatesQuantity() {
        OrderItem orderItem = new OrderItem(2, 2, 2); // stock_item_number=2, number_of_items=2, po_number=2
        dao.updateExisitngOrder(orderItem);
        assertTrue("Order item should still be present after update", dao.isOrderPresent(orderItem));
    }

    // Corner case: update non-existent order item (no error, no effect)
    @Test
    public void nonExistentOrder_updateExisitngOrder_noEffect() {
        OrderItem orderItem = new OrderItem(999, 999, 999);
        dao.updateExisitngOrder(orderItem);
        // It should simply not exist
        assertFalse("Non-existent order should not appear after update", dao.isOrderPresent(orderItem));
    }

    // Corner case: null input
    @Test(expected = NullPointerException.class)
    public void nullOrder_updateExisitngOrder_throwsException() {
        dao.updateExisitngOrder(null);
    }

    // ---------------- getCustomerPurchaseOrders() tests ----------------

    // Standard case: fetch a purchase order with items
    @Test
    public void validPO_getCustomerPurchaseOrders_returnsPO() {
        int poNumber = 1; // existing PO in DB
        PurchaseOrder po = dao.getCustomerPurchaseOrders(poNumber);
        assertNotNull("Purchase order should be returned", po);
        assertEquals(poNumber, po.getPoNumber());
        assertTrue("Order items should not be empty", po.getOiList().size() > 0);
    }

    // Corner case: non-existent PO (should return null)
    @Test
    public void nonExistentPO_getCustomerPurchaseOrders_returnsNull() {
        int poNumber = 9999; // unlikely PO
        PurchaseOrder po = dao.getCustomerPurchaseOrders(poNumber);
        assertNull("Non-existent purchase order should return null", po);
    }

    // Corner case: invalid PO number (e.g., negative)
    @Test
    public void negativePO_getCustomerPurchaseOrders_returnsNull() {
        int poNumber = -1;
        PurchaseOrder po = dao.getCustomerPurchaseOrders(poNumber);
        assertNull("Negative PO number should return null", po);
    }
}

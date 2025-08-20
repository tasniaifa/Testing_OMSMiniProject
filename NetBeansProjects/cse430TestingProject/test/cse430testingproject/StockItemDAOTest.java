package cse430testingproject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class StockItemDAOTest {

    StockItemDAO stockItemDAO;
    StockItem testItem;

    @Before
    public void setUp() {
        stockItemDAO = new StockItemDAO();
        testItem = new StockItem(1, "Pen", 10.5, Unit.PIECE, 100);
        stockItemDAO.addStockItem(testItem); // make sure it exists in DB
    }

    @After
    public void tearDown() {
        System.out.println("Tearing down...");
        stockItemDAO.updateStockQuantity(testItem.getItemNumber(), 0);
        stockItemDAO = null;
        testItem = null;
    }

    @Test // Check database connection
    public void testGetCon() {
        System.out.println("getCon");
        assertNotNull(stockItemDAO.getCon());
    }

    @Test // Add a valid stock item
    public void testAddStockItem() {
        System.out.println("addStockItem");
        StockItem newItem = new StockItem();
        newItem.setItemDescription("Notebook");
        newItem.setItemPrice(45.5);
        newItem.setUnit(Unit.NUMBERS);
        newItem.setQuantity(20);

        stockItemDAO.addStockItem(newItem);

        List<StockItem> items = stockItemDAO.getAllStockItems();
        boolean found = items.stream().anyMatch(item
                -> "Notebook".equals(item.getItemDescription()) && item.getItemPrice() == 45.5
        );
        assertTrue(found);
    }

    @Test // Get all stock items
    public void testGetAllStockItems() {
        System.out.println("getAllStockItems");
        List<StockItem> items = stockItemDAO.getAllStockItems();
        assertNotNull(items);
        assertFalse(items.isEmpty());
    }

    @Test // Get quantity of an existing item
    public void testGetQuantity() {
        System.out.println("getQuantity");
        int quantity = stockItemDAO.getQuantity(testItem.getItemNumber());
        assertEquals(testItem.getQuantity(), quantity);
    }

    @Test // Update stock quantity
    public void testUpdateStockQuantity() {
        System.out.println("updateStockQuantity");
        int newQuantity = 50;
        stockItemDAO.updateStockQuantity(testItem.getItemNumber(), newQuantity);

        int updatedQuantity = stockItemDAO.getQuantity(testItem.getItemNumber());
        assertEquals(newQuantity, updatedQuantity);
    }

    //CORNER CASES

    @Test // Add stock item with zero quantity
    public void testAddStockItemWithZeroQuantity() {
        StockItem zeroItem = new StockItem();
        zeroItem.setItemDescription("ZeroQtyItem");
        zeroItem.setItemPrice(5.0);
        zeroItem.setUnit(Unit.NUMBERS);
        zeroItem.setQuantity(0);

        stockItemDAO.addStockItem(zeroItem);
        int qty = stockItemDAO.getQuantity(zeroItem.getItemNumber());
        assertEquals(0, qty);
    }

    @Test // Add stock item with negative price (should fail)
    public void testAddStockItemWithNegativePrice() {
        StockItem invalidItem = new StockItem();
        invalidItem.setItemDescription("InvalidPriceItem");
        invalidItem.setItemPrice(-10.0);
        invalidItem.setUnit(Unit.NUMBERS);
        invalidItem.setQuantity(10);

        try {
            stockItemDAO.addStockItem(invalidItem);
            fail("Expected IllegalArgumentException for negative price");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test // Update stock quantity to negative (should fail)
    public void testUpdateStockQuantityToNegative() {
        try {
            stockItemDAO.updateStockQuantity(testItem.getItemNumber(), -5);
            fail("Expected IllegalArgumentException for negative quantity");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test // Get quantity for invalid item number
    public void testGetQuantityForInvalidItemNumber() {
        int invalidItemNumber = -9999;
        int qty = stockItemDAO.getQuantity(invalidItemNumber);
        assertEquals(0, qty);
    }

    @Test // Add stock item with null description (should fail)
    public void testAddStockItemWithNullDescription() {
        StockItem nullDescItem = new StockItem();
        nullDescItem.setItemDescription(null);
        nullDescItem.setItemPrice(20.0);
        nullDescItem.setUnit(Unit.NUMBERS);
        nullDescItem.setQuantity(5);

        try {
            stockItemDAO.addStockItem(nullDescItem);
            fail("Expected IllegalArgumentException for null description");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

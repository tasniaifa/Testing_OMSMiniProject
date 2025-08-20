package cse430testingproject;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class StockItemTest {

    StockItem item;

    @Before
    public void setUp() {
        System.out.println("Setting up...");
        item = new StockItem();
    }

    @After
    public void tearDown() {
        System.out.println("Tearing down...");
        item = null;
    }

    @Test // Default constructor values
    public void testDefaultConstructor() {
        assertEquals(0, item.getItemNumber());
        assertEquals("", item.getItemDescription());
        assertEquals(0.0, item.getItemPrice(), 0.0001); // delta required for doubles
        assertEquals(0, item.getQuantity());
        assertNull(item.getUnit());
    }

    @Test // Constructor with all fields
    public void testParameterizedConstructorWithAllFields() {
        StockItem newItem = new StockItem(1, "Pen", 10.5, Unit.PIECE, 100);
        assertEquals(1, newItem.getItemNumber());
        assertEquals("Pen", newItem.getItemDescription());
        assertEquals(10.5, newItem.getItemPrice(), 0.0001);
        assertEquals(Unit.PIECE, newItem.getUnit());
        assertEquals(100, newItem.getQuantity());
    }

    @Test // Constructor without unit and quantity
    public void testParameterizedConstructorWithoutUnitAndQuantity() {
        StockItem newItem = new StockItem(2, "Notebook", 30.0);
        assertEquals(2, newItem.getItemNumber());
        assertEquals("Notebook", newItem.getItemDescription());
        assertEquals(30.0, newItem.getItemPrice(), 0.0001);
        assertNull(newItem.getUnit());
        assertEquals(0, newItem.getQuantity());
    }

    @Test
    public void testSetAndGetItemNumber() {
        item.setItemNumber(5);
        assertEquals(5, item.getItemNumber());
    }

    @Test
    public void testSetAndGetItemDescription() {
        item.setItemDescription("Eraser");
        assertEquals("Eraser", item.getItemDescription());
    }

    @Test
    public void testSetAndGetItemPrice() {
        item.setItemPrice(15.75);
        assertEquals(15.75, item.getItemPrice(), 0.0001);
    }

    @Test
    public void testSetAndGetUnit() {
        item.setUnit(Unit.PIECE);
        assertEquals(Unit.PIECE, item.getUnit());
    }

    @Test
    public void testSetAndGetQuantity() {
        item.setQuantity(500);
        assertEquals(500, item.getQuantity());
    }

    // CORNER CASES

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeItemNumber() {
        item.setItemNumber(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativePrice() {
        item.setItemPrice(-50.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeQuantity() {
        item.setQuantity(-10);
    }

    @Test
    public void testSetLargeQuantity() {
        item.setQuantity(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, item.getQuantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullDescription() {
        item.setItemDescription(null);
    }

    @Test
    public void testSetEmptyDescription() {
        item.setItemDescription("");
        assertEquals("", item.getItemDescription());
    }
}

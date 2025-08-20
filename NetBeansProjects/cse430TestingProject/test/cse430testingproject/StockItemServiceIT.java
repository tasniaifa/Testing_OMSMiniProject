package cse430testingproject;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class StockItemServiceIT {

    private StockItemService stockItemService;

    @Before
    public void setUp() {
        stockItemService = new StockItemService();
    }

    // Helper method to check if an item exists by fields
    private boolean existsInDB(StockItem item, List<StockItem> items) {
        for (StockItem s : items) {
            if (s.getItemNumber() == item.getItemNumber() &&
                s.getItemDescription().equals(item.getItemDescription()) &&
                s.getItemPrice() == item.getItemPrice() &&
                s.getUnit() == item.getUnit() &&
                s.getQuantity() == item.getQuantity()) {
                return true;
            }
        }
        return false;
    }

    // Normal case
    @Test
    public void normalInput_addStockItem_savesSuccessfully() {
        StockItem item = new StockItem(3, "Apple", 50, Unit.KG, 37);
        stockItemService.addStockItem(item);
        List<StockItem> items = stockItemService.getAllStockItems();
        assertTrue("Stock item should exist in database", existsInDB(item, items));
    }

    // Negative price
    @Test
    public void negativePrice_addStockItem_notInserted() {
        StockItem item = new StockItem(2, "Orange", -5.0, Unit.PIECE, 5);
        stockItemService.addStockItem(item);
        List<StockItem> items = stockItemService.getAllStockItems();
        assertFalse("Stock item with negative price should NOT be added", existsInDB(item, items));
    }

    // Negative quantity
    @Test
    public void negativeQuantity_addStockItem_notInserted() {
        StockItem item = new StockItem(3, "Banana", 5.0, Unit.PIECE, -10);
        stockItemService.addStockItem(item);
        List<StockItem> items = stockItemService.getAllStockItems();
        assertFalse("Stock item with negative quantity should NOT be added", existsInDB(item, items));
    }

    // Zero price
    @Test
    public void zeroPrice_addStockItem_savesSuccessfully() {
        StockItem item = new StockItem(5, "Grapes", 0.0, Unit.KG, 10);
        stockItemService.addStockItem(item);
        List<StockItem> items = stockItemService.getAllStockItems();
        assertFalse("Stock item with zero price should not be added", existsInDB(item, items));
    }

    // Zero quantity
    @Test
    public void zeroQuantity_addStockItem_savesSuccessfully() {
        StockItem item = new StockItem(6, "Pineapple", 10.0, Unit.PIECE, 0);
        stockItemService.addStockItem(item);
        List<StockItem> items = stockItemService.getAllStockItems();
        assertFalse("Stock item with zero quantity should not be added", existsInDB(item, items));
    }
}

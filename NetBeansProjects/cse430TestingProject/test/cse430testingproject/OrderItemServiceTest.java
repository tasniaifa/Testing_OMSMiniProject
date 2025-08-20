package cse430testingproject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class OrderItemServiceTest {
    
    public OrderItemServiceTest() {
    }
    
    OrderItemService testService;
    OrderItem testOrder;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testOrder = new OrderItem(5, 3, 13);
        testService = new OrderItemService();
        testService.addOrderItem(testOrder);
    }
    
    @After
    public void tearDown() {
        testOrder = null;
        testService = null;
    }

    /**
     * Test of addOrderItem method, of class OrderItemService.
     */
    @Test
    public void testAddOrderItem() {
        System.out.println("addOrderItem");
        
        testService.addOrderItem(testOrder);
        
    }

    /**
     * Test of generateBillForCustomerPurchaseOrder method, of class OrderItemService.
     */
    @Test
    public void testGenerateBillForCustomerPurchaseOrder() throws IOException {
        System.out.println("generateBillForCustomerPurchaseOrder");

        int customerNo = 174;
        int purchaseOrderNumber = 13;

        // Call the method (generates the file)
        testService.generateBillForCustomerPurchaseOrder(customerNo, purchaseOrderNumber);

        // Locate the generated file
        File billFile = new File("bills/" + customerNo + "/" + purchaseOrderNumber + ".txt");
        assertTrue("Bill file should be generated", billFile.exists());

        // Read the content
        String billContent = new String(Files.readAllBytes(billFile.toPath()));

        System.out.println("Generated Bill Content:\n" + billContent);

        // Example expected part (adjust according to your real data)
        String expectedHeader = "Invoice for Customer Number : " + customerNo;
        assertTrue("Bill should contain invoice header", billContent.contains(expectedHeader));

        String expectedPONumber = "Purchase Order Number : " + purchaseOrderNumber;
        assertTrue("Bill should contain purchase order number", billContent.contains(expectedPONumber));

        // Also assert specific customer fields or totals
        assertTrue("Bill should contain Total Sum section", billContent.contains("Total Sum"));
    }
    
}

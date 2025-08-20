package cse430testingproject;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
public class PurchaseOrderServiceIT {
    
    public PurchaseOrderServiceIT() {
    }
    
    PurchaseOrderService testService;
    PurchaseOrder testOrder;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testService = new PurchaseOrderService();
        testOrder = new PurchaseOrder(101, LocalDate.of(2025, 8, 9));
        testService.addPurchaseOrder(testOrder);
    }
    
    @After
    public void tearDown() {
        testService = null;
        testOrder = null;
    }

    
    @Test
    public void testGetPurchaseOrder() {
        System.out.println("getPurchaseOrder");
        int customerNo = 172;
        String expResult = "[PurchaseOrder [poNumber=6, customerNo=172, orderDate=2021-07-16, shipDate=2021-08-02, shipped=true]]";
        List<PurchaseOrder> result = testService.getPurchaseOrder(customerNo);
        assertEquals(expResult, result.toString());
        
    }

    @Test
    public void testAddPurchaseOrder() {
        System.out.println("addPurchaseOrder");
        
        int expResult = 0;
        int result = testService.addPurchaseOrder(testOrder);
        assertEquals(expResult, result);
        
    }

    @Test
    public void testGetPurchaseOrderBetweenDates() {
        System.out.println("getPurchaseOrderBetweenDates");
        LocalDate fromDate = LocalDate.of(2021, 7, 19);
        LocalDate toDate = LocalDate.of(2021, 7, 15);
        String expResult = "[PurchaseOrder [poNumber=6, customerNo=172, orderDate=2021-07-16, shipDate=2021-08-02, shipped=true]]";
        List<PurchaseOrder> result = testService.getPurchaseOrderBetweenDates(fromDate, toDate);
        assertEquals(expResult, result.toString());
        
    }

    @Test
    public void testGetPurchaseOrderByDate_LocalDate() {
        System.out.println("getPurchaseOrderByDate");
        LocalDate date = LocalDate.of(2021, 7, 16);
        String expResult = "[PurchaseOrder [poNumber=6, customerNo=172, orderDate=2021-07-16, shipDate=2021-08-02, shipped=true]]";
        List<PurchaseOrder> result = testService.getPurchaseOrderByDate(date);
        assertEquals(expResult, result.toString());
        
    }

    @Test
    public void testGetPurchaseOrderByDate_LocalDate_int() {
        System.out.println("getPurchaseOrderByDate");
        LocalDate date = LocalDate.of(2022, 10, 26);
        int customerNo = 174;
        String expResult = "[PurchaseOrder [poNumber=13, customerNo=174, orderDate=2022-10-26, shipDate=2025-08-20, shipped=true]]";
        List<PurchaseOrder> result = testService.getPurchaseOrderByDate(date, customerNo);
        assertEquals(expResult, result.toString());
        System.out.println(result);
        
    }

    @Test
    public void testGetDelayedOrders() {
        System.out.println("getDelayedOrders");
        String expResult = "[PurchaseOrder [poNumber=2, customerNo=104, orderDate=2020-07-03, shipDate=2020-07-11, shipped=false], PurchaseOrder [poNumber=3, customerNo=103, orderDate=2020-09-25, shipDate=2020-09-29, shipped=false], PurchaseOrder [poNumber=13, customerNo=174, orderDate=2022-10-26, shipDate=2022-10-30, shipped=false], PurchaseOrder [poNumber=24, customerNo=108, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false], PurchaseOrder [poNumber=25, customerNo=108, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false], PurchaseOrder [poNumber=26, customerNo=108, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false], PurchaseOrder [poNumber=27, customerNo=104, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false], PurchaseOrder [poNumber=28, customerNo=104, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false], PurchaseOrder [poNumber=29, customerNo=103, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false]]";
        List<PurchaseOrder> result = testService.getDelayedOrders();
        assertEquals(expResult, result.toString());
    }

    @Test
    public void testShipOrderOf() {
        System.out.println("shipOrderOf");
        int poNumber = 13;
        
        testService.shipOrderOf(poNumber);
        
    }

    @Test
    public void testGetTotalShippedOrderMonthly() {
        System.out.println("getTotalShippedOrderMonthly");
        int year = 2020;
        
        List<Map.Entry<Integer, Integer>> result = testService.getTotalShippedOrderMonthly(year);
        System.out.println(result);
        
    }

    @Test
    public void testGetTotalAmountCollectedMonthly() {
        System.out.println("getTotalAmountCollectedMonthly");
        int year = 2030;
        
        List<Map.Entry<Integer, Integer>> result = testService.getTotalShippedOrderMonthly(year);
        System.out.println(result);
        
    }

    @Test
    public void testGetAllPurchaseOrders() {
        System.out.println("getAllPurchaseOrders");
        String expResult = "[PurchaseOrder [poNumber=1, customerNo=103, orderDate=2020-07-11, shipDate=2020-10-03, shipped=true], PurchaseOrder [poNumber=2, customerNo=104, orderDate=2020-07-03, shipDate=2020-07-11, shipped=false], PurchaseOrder [poNumber=3, customerNo=103, orderDate=2020-09-25, shipDate=2020-09-29, shipped=false], PurchaseOrder [poNumber=6, customerNo=172, orderDate=2021-07-16, shipDate=2021-08-02, shipped=true], PurchaseOrder [poNumber=10, customerNo=103, orderDate=2020-08-24, shipDate=2020-09-29, shipped=true], PurchaseOrder [poNumber=13, customerNo=174, orderDate=2022-10-26, shipDate=2025-08-16, shipped=true], PurchaseOrder [poNumber=24, customerNo=108, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false], PurchaseOrder [poNumber=25, customerNo=108, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false], PurchaseOrder [poNumber=26, customerNo=108, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false], PurchaseOrder [poNumber=27, customerNo=104, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false], PurchaseOrder [poNumber=28, customerNo=104, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false], PurchaseOrder [poNumber=29, customerNo=103, orderDate=2020-10-03, shipDate=2020-10-07, shipped=false]]";
        List<PurchaseOrder> result = testService.getAllPurchaseOrders();
        assertEquals(expResult, result.toString());
    }
    
}

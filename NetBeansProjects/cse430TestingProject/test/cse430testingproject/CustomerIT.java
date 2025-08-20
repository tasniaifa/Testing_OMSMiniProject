package cse430testingproject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
public class CustomerIT {
    
    public CustomerIT() {
    }
     Customer testCustomer;
    
    @BeforeClass
    public static void setUpClass() {
        // Runs once before all tests
    }
    
    @AfterClass
    public static void tearDownClass() {
        // Runs once after all tests
    }
    
    @Before
    public void setUp() {
        testCustomer = new Customer("Swabirah Iffat", "02 988 6723", "880 1352 785904", "880 1912 458970", "08 Niketan", "Gulshan 1", "Dhaka", "1212");
    }
    
    @After
    public void tearDown() {
        testCustomer = null;
    }

    @Test
    public void testGetPoList() {
        System.out.println("getPoList");
        List<PurchaseOrder> expResult = new ArrayList<>();
        expResult.add(new PurchaseOrder(101, LocalDate.of(2025, 8, 9)));
        expResult.add(new PurchaseOrder(102, LocalDate.of(2025, 8, 10)));

        testCustomer.setPoList(expResult);
        
        List<PurchaseOrder> result = testCustomer.getPoList();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetPoList() {
        System.out.println("setPoList");
        List<PurchaseOrder> poList = new ArrayList<>();
        poList.add(new PurchaseOrder(110, LocalDate.of(2025, 6, 14)));
        
        testCustomer.setPoList(poList);
        
        List<PurchaseOrder> result = testCustomer.getPoList();
        assertEquals(poList, result);
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "Customer [customerNo=0, name=Swabirah Iffat, homePhone=02 988 6723, cellPhone=880 1352 785904, workPhone=880 1912 458970, street=08 Niketan, city=Gulshan 1, state=Dhaka, zip=1212]";
        String result = testCustomer.toString();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCustomerNo() {
        System.out.println("getCustomerNo");
        int expResult = 0;
        int result = testCustomer.getCustomerNo();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetCustomerNo() {
        System.out.println("setCustomerNo");
        int customerNo = 10;
        testCustomer.setCustomerNo(customerNo);
        assertEquals(10, testCustomer.getCustomerNo());
    }

    @Test
    public void testGetName() {
        System.out.println("getName");
        assertEquals("Swabirah Iffat", testCustomer.getName());
    }

    @Test
    public void testSetName() {
        System.out.println("setName");
        testCustomer.setName("Samia Rahman");
        assertEquals("Samia Rahman", testCustomer.getName());
    }

    @Test
    public void testGetHomePhone() {
        System.out.println("getHomePhone");
        assertEquals("02 988 6723", testCustomer.getHomePhone());
    }

    @Test
    public void testSetHomePhone() {
        System.out.println("setHomePhone");
        testCustomer.setHomePhone("02 344 0912");
        assertEquals("02 344 0912", testCustomer.getHomePhone());
    }

    @Test
    public void testGetCellPhone() {
        System.out.println("getCellPhone");
        assertEquals("880 1352 785904", testCustomer.getCellPhone());
    }

    @Test
    public void testSetCellPhone() {
        System.out.println("setCellPhone");
        testCustomer.setCellPhone("880 1625 221199");
        assertEquals("880 1625 221199", testCustomer.getCellPhone());
    }

    @Test
    public void testGetWorkPhone() {
        System.out.println("getWorkPhone");
        assertEquals("880 1912 458970", testCustomer.getWorkPhone());
    }

    @Test
    public void testSetWorkPhone() {
        System.out.println("setWorkPhone");
        testCustomer.setWorkPhone("880 1512 785328");
        assertEquals("880 1512 785328", testCustomer.getWorkPhone());
    }

    @Test
    public void testGetStreet() {
        System.out.println("getStreet");
        assertEquals("08 Niketan", testCustomer.getStreet());
    }

    @Test
    public void testSetStreet() {
        System.out.println("setStreet");
        testCustomer.setStreet("12 A");
        assertEquals("12 A", testCustomer.getStreet());
    }

    @Test
    public void testGetCity() {
        System.out.println("getCity");
        assertEquals("Gulshan 1", testCustomer.getCity());
    }

    @Test
    public void testSetCity() {
        System.out.println("setCity");
        testCustomer.setCity("Aftabnagar");
        assertEquals("Aftabnagar", testCustomer.getCity());
    }

    @Test
    public void testGetState() {
        System.out.println("getState");
        assertEquals("Dhaka", testCustomer.getState());
    }

    @Test
    public void testSetState() {
        System.out.println("setState");
        testCustomer.setState("Sylhet");
        assertEquals("Sylhet", testCustomer.getState());
    }

    @Test
    public void testGetZip() {
        System.out.println("getZip");
        assertEquals("1212", testCustomer.getZip());
    }

    @Test
    public void testSetZip() {
        System.out.println("setZip");
        testCustomer.setZip("7800");
        assertEquals("7800", testCustomer.getZip());
    }
}
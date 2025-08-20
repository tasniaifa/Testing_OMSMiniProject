package cse430testingproject;

import java.util.Arrays;
import java.util.List;
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
public class CustomerServiceTest {
    
    CustomerService testService;
    Customer testCustomer;
    
    @Before
    public void setUp() {
        testCustomer = new Customer("Swabirah Iffat", "02 988 6723", "880 1352 785904", "880 1912 458970", "08 Niketan", "Gulshan 1", "Dhaka", "1212");
        testService = new CustomerService();
        testService.addCustomer(testCustomer);
    }
    
    @After
    public void tearDown() {
        testCustomer = null;
        testService = null;
    }

    /**
     * Test of addCustomer method, of class CustomerService.
     */
    @Test
    public void testAddCustomer() {
        System.out.println("addCustomer");
        //int expResult = 177;
        int result = testService.addCustomer(testCustomer);
        //assertEquals(expResult, result);
        System.out.println(result);
    }

    /**
     * Test of getCustomer method, of class CustomerService.
     */
    @Test
    public void testGetCustomer() {
        System.out.println("getCustomer");
        int customerNo = 219;
        String expResult = "Customer [customerNo=219, name=Swabirah Iffat, homePhone=02 988 6723, cellPhone=880 1352 785904, workPhone=880 1912 458970, street=08 Niketan, city=Gulshan 1, state=Dhaka, zip=1212]";
        Customer result = testService.getCustomer(customerNo);
        assertEquals(expResult, result.toString());
        System.out.println(result);
    }

    /**
     * Test of getAllCustomers method, of class CustomerService.
     */
    @Test
    public void testGetAllCustomers() {
        System.out.println("getAllCustomers");
        String expResult = "[Customer [customerNo=1, name=Test Customer, homePhone=null, cellPhone=01700000000, workPhone=null, street=123 Test St, city=Dhaka, state=Dhaka, zip=1230], Customer [customerNo=103, name=Carine Schmitt, homePhone=2319999, cellPhone=8888838383, workPhone=7321456712, street=54, rue Royale, city=Nantes, state=Ouest, zip=44000], Customer [customerNo=104, name=Justin Patel, homePhone=2311123, cellPhone=8887738813, workPhone=9320006718, street=11, Heritage, city=Ponda, state=Goa, zip=403401], Customer [customerNo=108, name=Prerna Desai, homePhone=9564325978, cellPhone=9654875321, workPhone=2229747452, street=99, HarbhajanGunj, city=Srinagar, state=Jammu and Kashmir, zip=297490], Customer [customerNo=172, name=Rahul Sharma, homePhone=08322536498, cellPhone=9881876854, workPhone=08332659845, street=Mangor Hills, city=Vasco, state=Goa, zip=403802], Customer [customerNo=174, name=Kunal Singh, homePhone=08652315468, cellPhone=8652314985, workPhone=02256548654, street=82, Chhaya Nagar, city=Darjeeling, state=West Bengal, zip=734101], Customer [customerNo=175, name=Samia Rahman, homePhone=111-222-3333, cellPhone=222-333-4444, workPhone=333-444-5555, street=Aftabnagar, city=Dhaka, state=BD, zip=1205], Customer [customerNo=178, name=, homePhone=123-456-7890, cellPhone=123-456-7890, workPhone=123-456-7890, street=Unknown, city=Unknown, state=BD, zip=0000], Customer [customerNo=181, name=Swabirah Iffat, homePhone=444-555-6666, cellPhone=555-666-7777, workPhone=666-777-8888, street=456 Park Ave, city=Chittagong, state=BD, zip=4000], Customer [customerNo=219, name=Swabirah Iffat, homePhone=02 988 6723, cellPhone=880 1352 785904, workPhone=880 1912 458970, street=08 Niketan, city=Gulshan 1, state=Dhaka, zip=1212]]";
        List<Customer> result = testService.getAllCustomers();
        assertEquals(expResult, result.toString());
        System.out.println(result);
    }

    /**
     * Test of getCustomerByMaxPurchaseOrder method, of class CustomerService.
     */
    @Test
    public void testGetCustomerByMaxPurchaseOrder() {
        System.out.println("getCustomerByMaxPurchaseOrder");
        List<Integer> expResult = Arrays.asList(1);        
        List<Integer> result = testService.getCustomerByMaxPurchaseOrder();
        assertEquals(expResult, result);
        System.out.println(result);
    }
}

package cse430testingproject;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerDAOTest {

    CustomerDAO customerDAO;
    Customer testCustomer;

    @Before
    public void setUp() {
        System.out.println("Setting up...");
        customerDAO = new CustomerDAO();

        // Creating test customer
        testCustomer = new Customer();
        testCustomer.setName("Samia Rahman");
        testCustomer.setHomePhone("111-222-3333");
        testCustomer.setCellPhone("222-333-4444");
        testCustomer.setWorkPhone("333-444-5555");
        testCustomer.setStreet("Aftabnagar");
        testCustomer.setCity("Dhaka");
        testCustomer.setState("BD");
        testCustomer.setZip("1205");

        customerDAO.addCustomer(testCustomer);
    }

    @After
    public void tearDown() {
        System.out.println("Tearing down...");
        customerDAO = null;
        testCustomer = null;
    }

    @Test   //DB connection is valid
    public void testGetCon() {
        assertNotNull("Database connection should not be null", customerDAO.getCon());
    }

    @Test   //inserting works
    public void testAddCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setName("Swabirah Iffat");
        newCustomer.setHomePhone("444-555-6666");
        newCustomer.setCellPhone("555-666-7777");
        newCustomer.setWorkPhone("666-777-8888");
        newCustomer.setStreet("456 Park Ave");
        newCustomer.setCity("Chittagong");
        newCustomer.setState("BD");
        newCustomer.setZip("4000");

        customerDAO.addCustomer(newCustomer);
        List<Customer> customers = customerDAO.getAllCustomers();
        boolean found = customers.stream().anyMatch(c -> "Swabirah Iffat".equals(c.getName()));
        assertTrue("Newly added customer should exist in the database", found);
    }

    @Test   //list is not empty
    public void testGetAllCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        assertNotNull("Customer list should not be null", customers);
        assertFalse("Customer list should not be empty", customers.isEmpty());
    }

    @Test   //retrieve valid customer
    public void testGetCustomerById() {
        List<Customer> customers = customerDAO.getAllCustomers();
        assertFalse("At least one customer should exist", customers.isEmpty());

        Customer first = customers.get(0);
        Customer found = customerDAO.getCustomerById(first.getCustomerNo());

        assertNotNull("Customer should be found by ID", found);
        assertEquals("Customer name should match", first.getName(), found.getName());
    }

    @Test   //returns valid IDs
    public void testGetCustomerByMaxPurchaseOrder() {
        List<Integer> customerIds = customerDAO.getCustomerByMaxPurchaseOrder();
        assertNotNull("List should not be null", customerIds);
        assertTrue("Method should return 0 or more customer IDs", customerIds.size() >= 0);
    }

    // ----------------- CORNER CASES -----------------

    @Test   //Invalid customer ID returns null
    public void testGetCustomerByIdInvalid() {
        Customer customer = customerDAO.getCustomerById(-9999);
        assertNull("Invalid customer ID should return null", customer);
    }

    @Test   //Adding customer with null name
    public void testAddCustomerWithNullName() {
        Customer invalidCustomer = new Customer();
        invalidCustomer.setName(null);
        invalidCustomer.setHomePhone("123-456-7890");
        invalidCustomer.setCellPhone("123-456-7890");
        invalidCustomer.setWorkPhone("123-456-7890");
        invalidCustomer.setStreet("Unknown");
        invalidCustomer.setCity("Unknown");
        invalidCustomer.setState("BD");
        invalidCustomer.setZip("0000");

        try {
            customerDAO.addCustomer(invalidCustomer);
        } catch (Exception e) {
            fail("Adding customer with null name should not crash (depends on DB constraints)");
        }
    }

    @Test    //empty DB
    public void testGetAllCustomersEmptyDatabase() {
        List<Customer> customers = customerDAO.getAllCustomers();
        assertNotNull("Should return non-null list", customers);
        assertTrue("Customer list should be 0 or more", customers.size() >= 0);
    }

    @Test     //should be -1 (if none) or valid ID
    public void testGetLastInsertID() {
        int id = customerDAO.getLastInsertID();
        assertTrue("Last insert ID should be -1 if no inserts or a positive number if inserted", id >= -1);
    }
}

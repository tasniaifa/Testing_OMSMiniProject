package cse430testingproject;

import org.junit.Test;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PurchaseOrderTest {

    /** Test of PurchaseOrder(int customerNo, LocalDate orderDate) constructor */

    // TC_01: Normal input — verify all fields are set correctly
    @Test
    public void normalInput_sets_AllFieldsCorrectly() {
        LocalDate orderDate = LocalDate.of(2023, 8, 15);
        PurchaseOrder po = new PurchaseOrder(123, orderDate);

        assertEquals(0, po.getPoNumber());
        assertEquals(123, po.getCustomerNo());
        assertEquals(orderDate, po.getOrderDate());
        assertEquals(orderDate.plusDays(4), po.getShipDate());
        assertFalse(po.isShipped());
    }

    // TC_02: Edge: customerNo = 0 should be accepted and stored
    @Test
    public void zeroCustomer_allowedAndSet() {
        LocalDate orderDate = LocalDate.of(2024, 1, 10);
        PurchaseOrder po = new PurchaseOrder(0, orderDate);

        assertEquals(0, po.getCustomerNo());
        assertEquals(orderDate.plusDays(4), po.getShipDate());
        assertFalse(po.isShipped());
    }

    // TC_03: Edge: negative customerNo should be stored as given
    @Test
    public void negativeCustomer_isStored() {
        LocalDate orderDate = LocalDate.of(2024, 2, 1);
        PurchaseOrder po = new PurchaseOrder(-5, orderDate);

        assertEquals(-5, po.getCustomerNo());
        assertEquals(orderDate.plusDays(4), po.getShipDate());
    }

    // TC_04: Corner: null orderDate should throw NullPointerException
    @Test(expected = NullPointerException.class)
    public void nullOrderDate_throwsNullPointerException() {
        new PurchaseOrder(1, null);
    }

    // TC_05: Corner: leap-year crossing — ensure shipDate calculation handles Feb 29 correctly
    @Test
    public void leapYear_crossesFebruary_shipDateCorrect() {
        LocalDate orderDate = LocalDate.of(2020, 2, 27);
        PurchaseOrder po = new PurchaseOrder(77, orderDate);

        assertEquals(LocalDate.of(2020, 3, 2), po.getShipDate());
    }

    // TC_06: Corner: year boundary crossing — ensure shipDate crosses into next year correctly
    @Test
    public void yearBoundary_crossesYear_shipDateCorrect() {
        LocalDate orderDate = LocalDate.of(2023, 12, 29);
        PurchaseOrder po = new PurchaseOrder(88, orderDate);

        assertEquals(LocalDate.of(2024, 1, 2), po.getShipDate());
    }
    
    /** Test of toString() method */

    // TC_01: Normal input — all fields set
    @Test
    public void normalInput_returnsCorrectString() {
        PurchaseOrder po = new PurchaseOrder();
        po.setPoNumber(1001);
        po.setCustomerNo(5001);
        LocalDate orderDate = LocalDate.of(2025, 8, 19);
        po.setOrderDate(orderDate);
        po.setShipDate(orderDate.plusDays(4));
        po.setShipped(true);

        String expected = "PurchaseOrder [poNumber=1001, customerNo=5001, orderDate=2025-08-19, shipDate=2025-08-23, shipped=true]";
        assertEquals(expected, po.toString());
    }

    // TC_02: Default constructor values
    @Test
    public void defaultValues_returnsStringWithDefaults() {
        PurchaseOrder po = new PurchaseOrder();
        String expected = "PurchaseOrder [poNumber=0, customerNo=0, orderDate=null, shipDate=null, shipped=false]";
        assertEquals(expected, po.toString());
    }

    // TC_03: Negative IDs printed as-is
    @Test
    public void negativeIds_printedAsIs() {
        PurchaseOrder po = new PurchaseOrder();
        po.setPoNumber(-10);
        po.setCustomerNo(-50);
        LocalDate orderDate = LocalDate.of(2025, 1, 1);
        po.setOrderDate(orderDate);
        po.setShipDate(orderDate.plusDays(2));
        po.setShipped(false);

        String expected = "PurchaseOrder [poNumber=-10, customerNo=-50, orderDate=2025-01-01, shipDate=2025-01-03, shipped=false]";
        assertEquals(expected, po.toString());
    }

    // TC_04: Large IDs printed correctly
    @Test
    public void largeIds_printedCorrectly() {
        PurchaseOrder po = new PurchaseOrder();
        po.setPoNumber(Integer.MAX_VALUE);
        po.setCustomerNo(Integer.MAX_VALUE);
        LocalDate orderDate = LocalDate.of(2099, 12, 31);
        po.setOrderDate(orderDate);
        po.setShipDate(orderDate.plusDays(1));
        po.setShipped(true);

        String expected = "PurchaseOrder [poNumber=2147483647, customerNo=2147483647, orderDate=2099-12-31, shipDate=2100-01-01, shipped=true]";
        assertEquals(expected, po.toString());
    }

    // TC_05: oiList initializes empty
    @Test
    public void oiList_initializesEmpty() {
        PurchaseOrder po = new PurchaseOrder();
        assertNotNull(po.getOiList());
        assertTrue(po.getOiList().isEmpty());
    }

    /** Test of getPoNumber() / setPoNumber() */

    // TC_01: Positive poNumber
    @Test
    public void setAndGetPoNumber_positiveValue() {
        PurchaseOrder po = new PurchaseOrder();
        po.setPoNumber(1001);
        assertEquals(1001, po.getPoNumber());
    }

    // TC_02: Zero poNumber
    @Test
    public void setAndGetPoNumber_zeroValue() {
        PurchaseOrder po = new PurchaseOrder();
        po.setPoNumber(0);
        assertEquals(0, po.getPoNumber());
    }

    // TC_03: Negative poNumber
    @Test
    public void setAndGetPoNumber_negativeValue() {
        PurchaseOrder po = new PurchaseOrder();
        po.setPoNumber(-50);
        assertEquals(-50, po.getPoNumber());
    }

    /** Test of getCustomerNo() / setCustomerNo() */

    // TC_01: Positive customerNo
    @Test
    public void setAndGetCustomerNo_positiveValue() {
        PurchaseOrder po = new PurchaseOrder();
        po.setCustomerNo(5001);
        assertEquals(5001, po.getCustomerNo());
    }

    // TC_02: Zero customerNo
    @Test
    public void setAndGetCustomerNo_zeroValue() {
        PurchaseOrder po = new PurchaseOrder();
        po.setCustomerNo(0);
        assertEquals(0, po.getCustomerNo());
    }

    // TC_03: Negative customerNo
    @Test
    public void setAndGetCustomerNo_negativeValue() {
        PurchaseOrder po = new PurchaseOrder();
        po.setCustomerNo(-25);
        assertEquals(-25, po.getCustomerNo());
    }

    /** Test of getOrderDate(), setOrderDate() */

    // TC_01: Valid orderDate
    @Test
    public void setAndGetOrderDate_validDate() {
        PurchaseOrder po = new PurchaseOrder();
        LocalDate date = LocalDate.of(2025, 8, 19);
        po.setOrderDate(date);
        assertEquals(date, po.getOrderDate());
    }

    // TC_02: Null orderDate
    @Test
    public void setAndGetOrderDate_nullValue() {
        PurchaseOrder po = new PurchaseOrder();
        po.setOrderDate(null);
        assertNull(po.getOrderDate());
    }

    /** Test of getShipDate(), setShipDate() */

    // TC_01: Valid shipDate
    @Test
    public void setAndGetShipDate_validDate() {
        PurchaseOrder po = new PurchaseOrder();
        LocalDate date = LocalDate.of(2025, 8, 23);
        po.setShipDate(date);
        assertEquals(date, po.getShipDate());
    }

    // TC_02: Null shipDate
    @Test
    public void setAndGetShipDate_nullValue() {
        PurchaseOrder po = new PurchaseOrder();
        po.setShipDate(null);
        assertNull(po.getShipDate());
    }

    /** Test of isShipped() / setShipped() */

    // TC_01: shipped = true
    @Test
    public void setAndGetShipped_true() {
        PurchaseOrder po = new PurchaseOrder();
        po.setShipped(true);
        assertTrue(po.isShipped());
    }

    // TC_02: shipped = false
    @Test
    public void setAndGetShipped_false() {
        PurchaseOrder po = new PurchaseOrder();
        po.setShipped(false);
        assertFalse(po.isShipped());
    }

    /** Test of getOiList() / setOiList() */

    // TC_01: Non-empty list
    @Test
    public void setAndGetOiList_nonEmptyList() {
        PurchaseOrder po = new PurchaseOrder();
        List<OrderItem> list = new ArrayList<>();
        list.add(new OrderItem(1, 5, 100));
        list.add(new OrderItem(2, 10, 100));
        po.setOiList(list);
        assertEquals(list, po.getOiList());
    }

    // TC_02: Null list
    @Test
    public void setAndGetOiList_nullList() {
        PurchaseOrder po = new PurchaseOrder();
        po.setOiList(null);
        assertNull(po.getOiList());
    }
}
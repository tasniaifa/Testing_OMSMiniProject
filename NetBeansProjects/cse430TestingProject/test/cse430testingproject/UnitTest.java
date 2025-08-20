package cse430testingproject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author tasniafarinifa
 * JUnit test class for Unit enum
 */
public class UnitTest {

    public UnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /** Test of values() method */

    // TC_01: Check that values() returns all enum constants
    @Test
    public void testValues_ReturnsAllConstants() {
        Unit[] expected = {Unit.KG, Unit.DOZEN, Unit.GALLONS, Unit.NUMBERS, Unit.GRAMS};
        Unit[] actual = Unit.values();
        assertArrayEquals("values() should return all enum constants", expected, actual);
    }

    // TC_02: Check length of values() matches enum count
    @Test
    public void testValues_LengthMatchesEnumCount() {
        Unit[] actual = Unit.values();
        int expectedLength = 5;  // Known number of enum constants
        assertEquals("values() length should match number of enum constants", expectedLength, actual.length);
    }

    // TC_03: Check that array returned is a new copy (not same reference)
    @Test
    public void testValues_ReturnsNewArrayInstance() {
        Unit[] firstCall = Unit.values();
        Unit[] secondCall = Unit.values();
        assertNotSame("values() should return a new array instance on each call", firstCall, secondCall);
    }

    /** Test of valueOf(String name) method */

    // TC_01: Valid enum name should return correct enum
    @Test
    public void testValueOf_ValidName() {
        Unit expected = Unit.KG;
        Unit actual = Unit.valueOf("KG");
        assertEquals(expected, actual);
    }

    // TC_02: Invalid enum name should throw IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testValueOf_InvalidName_ThrowsException() {
        Unit.valueOf("INVALID_NAME");
    }

    // TC_03: Empty string should throw IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void testValueOf_EmptyString_ThrowsException() {
        Unit.valueOf("");
    }

    /** Test of setValue(int value) method */

    // TC_01: Set positive value and verify getValue()
    @Test
    public void testSetValue_Positive() {
        Unit unit = Unit.KG;
        int value = 10;
        unit.setValue(value);
        assertEquals(10, unit.getValue());
    }

    // TC_02: Set zero value and verify getValue()
    @Test
    public void testSetValue_Zero() {
        Unit unit = Unit.DOZEN;
        int value = 0;
        unit.setValue(value);
        assertEquals(0, unit.getValue());
    }

    // TC_03: Set negative value and verify getValue()
    @Test
    public void testSetValue_Negative() {
        Unit unit = Unit.GALLONS;
        int value = -5;
        unit.setValue(value);
        assertEquals(-5, unit.getValue());
    }

    /** Test of getValue() method */

    // TC_01: Get value of enum after setting positive value
    @Test
    public void testGetValue_DefaultValue() {
        Unit unit = Unit.KG; 
        int expected = 10; // set in previous test
        int actual = unit.getValue();
        assertEquals(expected, actual);
    }

    // TC_02: Get value of enum with custom value
    @Test
    public void testGetValue_CustomValue() {
        Unit unit = Unit.GRAMS; 
        int expected = 250; // custom value defined in enum
        int actual = unit.getValue();
        assertEquals(expected, actual);
    }
}

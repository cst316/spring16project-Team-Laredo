package net.sf.memoranda.test;

import net.sf.memoranda.date.CalendarDate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CalendarDateTest {

    private CalendarDate cd1;
    private CalendarDate cd2;
    private CalendarDate cd3;

    @Before
    public void setUp() throws Exception {
        cd1 = new CalendarDate(10, 2, 2016);
        cd2 = new CalendarDate(11, 2, 2016);
        cd3 = new CalendarDate(10, 2, 2016);
    }

    @Test
    public void equalsTest() {
        assertTrue(cd1.equals(cd3));
        assertFalse(cd1.equals(cd2));
    }

    @Test
    public void beforeTest() {
        assertTrue(cd1.before(cd2));
        assertFalse(cd2.before(cd1)); //Pass with assertFalse
    }

    @Test
    public void afterTest() {
        assertFalse(cd1.after(cd2));
        assertTrue(cd2.after(cd1));
    }

}

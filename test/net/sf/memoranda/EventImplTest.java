package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by adam on 2/8/16.
 */
public class EventImplTest {
    private static Event emptyDuration = null;
    private static Event negativeDuration = null;
    private static Event zeroDuration = null;
    private static Event oneDuration = null;
    private static Event manyDuration = null;

    @org.junit.Before
    public void setUp() throws Exception {
        emptyDuration = EventsManager.createEvent(new CalendarDate(), 10, 0, 0, 0, "");
        negativeDuration = EventsManager.createEvent(new CalendarDate(), 10, 0, 9, 0, "");
        zeroDuration = EventsManager.createEvent(new CalendarDate(), 10, 0, 10, 0, "");
        oneDuration = EventsManager.createEvent(new CalendarDate(), 10, 0, 10, 1, "");
        manyDuration = EventsManager.createEvent(new CalendarDate(), 10, 0, 18, 33, "");
    }

    @org.junit.After
    public void tearDown() throws Exception {
        emptyDuration = null;
        negativeDuration = null;
        zeroDuration = null;
        oneDuration = null;
        manyDuration = null;
    }

    @org.junit.Test
    public void testGetStartHour() throws Exception {
        assertEquals(10, emptyDuration.getStartHour());
        assertEquals(10, negativeDuration.getStartHour());
        assertEquals(10, zeroDuration.getStartHour());
        assertEquals(10, oneDuration.getStartHour());
        assertEquals(10, manyDuration.getStartHour());
    }

    @org.junit.Test
    public void testGetStartMinute() throws Exception {
        assertEquals(0, emptyDuration.getStartMinute());
        assertEquals(0, negativeDuration.getStartMinute());
        assertEquals(0, zeroDuration.getStartMinute());
        assertEquals(0, oneDuration.getStartMinute());
        assertEquals(0, manyDuration.getStartMinute());
    }

    @org.junit.Test
    public void testGetEndHour() throws Exception {
        assertEquals(0, emptyDuration.getEndHour());
        assertEquals(9, negativeDuration.getEndHour());
        assertEquals(10, zeroDuration.getEndHour());
        assertEquals(10, oneDuration.getEndHour());
        assertEquals(18, manyDuration.getEndHour());
    }

    @org.junit.Test
    public void testGetEndMinute() throws Exception {
        assertEquals(0, emptyDuration.getEndMinute());
        assertEquals(0, negativeDuration.getEndMinute());
        assertEquals(0, zeroDuration.getEndMinute());
        assertEquals(1, oneDuration.getEndMinute());
        assertEquals(33, manyDuration.getEndMinute());
    }

    @org.junit.Test
    public void testGetDurationString() throws Exception {
        assertEquals("", emptyDuration.getDurationString());
        assertEquals("", negativeDuration.getDurationString());
        assertEquals("", zeroDuration.getDurationString());
        assertEquals("0:01", oneDuration.getDurationString());
        assertEquals("8:33", manyDuration.getDurationString());
    }

    @org.junit.Test
    public void testGetDurationMinutes() throws Exception {
        assertEquals(0, emptyDuration.getDurationMinutes());
        assertEquals(0, negativeDuration.getDurationMinutes());
        assertEquals(0, zeroDuration.getDurationMinutes());
        assertEquals(1, oneDuration.getDurationMinutes());
        assertEquals(513, manyDuration.getDurationMinutes());
    }
}

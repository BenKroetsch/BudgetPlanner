package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    private Event e1;
    private Date time;

    @BeforeEach
    public void setUp() {
        e1 = new Event("Testing event 1");
        time = Calendar.getInstance().getTime();
    }

    /** Cannot test time of event reliably
     */
    @Test
    public void EventConstructorTest() {
        assertEquals(e1.getDescription(), "Testing event 1");
    }

    @Test
    public void EventToString() {
        assertEquals(e1.toString(), time.toString() + "\n" + e1.getDescription());
    }
}

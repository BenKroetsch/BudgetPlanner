package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventLogTest {
    private EventLog eventLog;
    private Event e1;
    private Event e2;
    private Event e3;

    @BeforeEach
    public void setUp(){
        e1 = new Event("Testing event 1");
        e2 = new Event("Testing event 2");
        e3 = new Event("Testing event 3");
        eventLog = EventLog.getInstance();
    }

    @Test
    public void eventLogTest(){
        eventLog.logEvent(e1);
        eventLog.logEvent(e2);
        eventLog.logEvent(e3);
        ArrayList<Event> listEvent = new ArrayList<>();
        for (Event event: eventLog.getInstance()) {
            listEvent.add(event);
        }
        assertTrue(listEvent.contains(e1));
        assertTrue(listEvent.contains(e2));
        assertTrue(listEvent.contains(e3));

    }
}

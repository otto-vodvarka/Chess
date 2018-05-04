package model.chess;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DurationTest {

    private Duration duration;

    @Before
    public void setUp(){
        duration = new Duration(40);
    }

    @Test
    public void decrease() throws Exception {
        assertEquals(40, duration.getSeconds());

        duration.decrease();

        assertEquals(39, duration.getSeconds());

        duration.decrease();
        duration.decrease();
        duration.decrease();

        assertEquals(36, duration.getSeconds());
    }

    @Test
    public void testToString() throws Exception {
        String expected = "00:40";

        assertEquals(expected, duration.toString());

        Duration duration2 = new Duration(518);
        String expected2 = "08:38";

        assertEquals(expected2, duration2.toString());
    }

}
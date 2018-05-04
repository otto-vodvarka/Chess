package model.chess;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {

    @Test
    public void equals() throws Exception {
        Coordinate coord1 = new Coordinate(3,5);
        Coordinate coord2 = new Coordinate(3,5);
        Coordinate coord3 = new Coordinate(5,3);

        assertEquals(coord1, coord2);
        assertNotEquals(coord1, coord3);
    }

}
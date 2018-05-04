package model.chess;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveTest {

    @Test
    public void isAtBoard() throws Exception {
        Move move1 = new Move(new Board(), new Coordinate(1,2),new Coordinate(1,1));
        Move move2 = new Move(new Board(), new Coordinate(10,5),new Coordinate(3,4));
        Move move3 = new Move(new Board(), new Coordinate(5,1),new Coordinate(1,8));
        Move move4 = new Move(new Board(), new Coordinate(9,2),new Coordinate(10,10));

        assertTrue(move1.isAtBoard());
        assertFalse(move2.isAtBoard());
        assertFalse(move3.isAtBoard());
        assertFalse(move4.isAtBoard());
    }

    @Test
    public void invertMove() throws Exception {
        Move move1 = new Move(new Board(), new Coordinate(1,2),new Coordinate(1,1));
        Move move2 = new Move(new Board(), new Coordinate(1,1),new Coordinate(1,2));
        assertEquals(move2, move1.invertMove());
    }

    @Test
    public void getCastlingRookMove() throws Exception {
        Move castling = new Move(new Board(), new Coordinate(4,7),new Coordinate(6,7));
        Move rookMove = new Move(new Board(), new Coordinate(7,7), new Coordinate(5,7));

        assertEquals(rookMove, castling.getCastlingRookMove(Color.WHITE));

        Move castling2 = new Move(new Board(), new Coordinate(4,7),new Coordinate(2,3));

        assertNull(castling2.getCastlingRookMove(Color.WHITE));
    }

    @Test
    public void equals() throws Exception {
        Move move1 = new Move(new Board(), new Coordinate(3,2),new Coordinate(4,1));
        Move move2 = new Move(new Board(), new Coordinate(3,2),new Coordinate(4,1));
        Move move3 = new Move(new Board(), new Coordinate(1,2),new Coordinate(1,7));

        assertEquals(move1, move2);
        assertNotEquals(move1, move3);
    }

}
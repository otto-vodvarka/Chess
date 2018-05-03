/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import java.util.List;
import model.pieces.Piece;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ottovodvarka
 */
public class BoardTest {
    
    private Board board;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        board = new Board();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of moveTo method, of class Board.
     */
    @Test
    public void testMoveTo_Move() {
        assertNotNull(board.getPieceAt(new Coordinate(3, 6)));
        
        Move move = new Move(board, new Coordinate(3, 6), new Coordinate(3, 4));
        board.moveTo(move);
        
        Piece pieceStart = board.getPieceAt(new Coordinate(3, 6));
        Piece pieceEnd = board.getPieceAt(new Coordinate(3, 4));
        
        assertNull(pieceStart);
        assertNotNull(pieceEnd);
    }

    /**
     * Test of moveTo method, of class Board.
     */
    @Test
    public void testMoveTo_Piece_Coordinate() {
        System.out.println("moveTo");
        Piece piece = null;
        Coordinate coord = null;
        Board instance = new Board();
        instance.moveTo(piece, coord);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPiece method, of class Board.
     */
    @Test
    public void testAddPiece() {
        System.out.println("addPiece");
        Piece piece = null;
        Coordinate coord = null;
        Board instance = new Board();
        instance.addPiece(piece, coord);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removePiece method, of class Board.
     */
    @Test
    public void testRemovePiece() {
        System.out.println("removePiece");
        Piece piece = null;
        Board instance = new Board();
        instance.removePiece(piece);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removePieceAt method, of class Board.
     */
    @Test
    public void testRemovePieceAt() {
        System.out.println("removePieceAt");
        Coordinate coord = null;
        Board instance = new Board();
        instance.removePieceAt(coord);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasPiece method, of class Board.
     */
    @Test
    public void testHasPiece() {
        System.out.println("hasPiece");
        Coordinate coord = null;
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.hasPiece(coord);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumberOfPieces method, of class Board.
     */
    @Test
    public void testGetNumberOfPieces() {
        System.out.println("getNumberOfPieces");
        Board instance = new Board();
        int expResult = 0;
        int result = instance.getNumberOfPieces();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isOnRow method, of class Board.
     */
    @Test
    public void testIsOnRow() {
        System.out.println("isOnRow");
        Piece piece = null;
        int row = 0;
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.isOnRow(piece, row);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isInCheck method, of class Board.
     */
    @Test
    public void testIsInCheck() {
        System.out.println("isInCheck");
        Color color = null;
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.isInCheck(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isStalemate method, of class Board.
     */
    @Test
    public void testIsStalemate() {
        System.out.println("isStalemate");
        Color color = null;
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.isStalemate(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isCheckMate method, of class Board.
     */
    @Test
    public void testIsCheckMate() {
        System.out.println("isCheckMate");
        Color color = null;
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.isCheckMate(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isInCheckAfterThisMove method, of class Board.
     */
    @Test
    public void testIsInCheckAfterThisMove() {
        System.out.println("isInCheckAfterThisMove");
        Move move = null;
        Color color = null;
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.isInCheckAfterThisMove(move, color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllAvailableMovesByColor method, of class Board.
     */
    @Test
    public void testGetAllAvailableMovesByColor() {
        System.out.println("getAllAvailableMovesByColor");
        Color color = null;
        Board instance = new Board();
        List<Move> expResult = null;
        List<Move> result = instance.getAllAvailableMovesByColor(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllLegalMovesByColor method, of class Board.
     */
    @Test
    public void testGetAllLegalMovesByColor() {
        System.out.println("getAllLegalMovesByColor");
        Color color = null;
        Board instance = new Board();
        List<Move> expResult = null;
        List<Move> result = instance.getAllLegalMovesByColor(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findPiece method, of class Board.
     */
    @Test
    public void testFindPiece() {
        System.out.println("findPiece");
        Piece piece = null;
        Board instance = new Board();
        Coordinate expResult = null;
        Coordinate result = instance.findPiece(piece);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findKing method, of class Board.
     */
    @Test
    public void testFindKing() {
        System.out.println("findKing");
        Color color = null;
        Board instance = new Board();
        Coordinate expResult = null;
        Coordinate result = instance.findKing(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBishop method, of class Board.
     */
    @Test
    public void testGetBishop() {
        System.out.println("getBishop");
        Color color = null;
        Board instance = new Board();
        Piece expResult = null;
        Piece result = instance.getBishop(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllPiecesbyColor method, of class Board.
     */
    @Test
    public void testGetAllPiecesbyColor() {
        System.out.println("getAllPiecesbyColor");
        Color color = null;
        Board instance = new Board();
        List<Piece> expResult = null;
        List<Piece> result = instance.getAllPiecesbyColor(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPieceAt method, of class Board.
     */
    @Test
    public void testGetPieceAt() {
        System.out.println("getPieceAt");
        Coordinate coord = null;
        Board instance = new Board();
        Piece expResult = null;
        Piece result = instance.getPieceAt(coord);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpot method, of class Board.
     */
    @Test
    public void testGetSpot() {
        System.out.println("getSpot");
        Coordinate coord = null;
        Board instance = new Board();
        Spot expResult = null;
        Spot result = instance.getSpot(coord);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastMove method, of class Board.
     */
    @Test
    public void testGetLastMove() {
        System.out.println("getLastMove");
        Board instance = new Board();
        Move expResult = null;
        Move result = instance.getLastMove();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of IsCastlingAvailable method, of class Board.
     */
    @Test
    public void testIsCastlingAvailable() {
        System.out.println("IsCastlingAvailable");
        Move move = null;
        Color color = null;
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.IsCastlingAvailable(move, color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import java.util.ArrayList;
import java.util.List;

import model.pieces.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author ottovodvarka
 */
public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board();
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
        Piece piece = board.getPieceAt(new Coordinate(3, 6));
        board.moveTo(piece, new Coordinate(3, 4));

        Piece pieceStart = board.getPieceAt(new Coordinate(3, 6));
        Piece pieceEnd = board.getPieceAt(new Coordinate(3, 4));

        assertNull(pieceStart);
        assertNotNull(pieceEnd);
    }

    /**
     * Test of addPiece method, of class Board.
     */
    @Test
    public void testAddPiece() {
        Piece piece = new Queen(Color.WHITE);
        Coordinate coord = new Coordinate(5, 5);

        board.addPiece(piece, coord);

        assertEquals(piece, board.getPieceAt(new Coordinate(5, 5)));
    }

    /**
     * Test of removePiece method, of class Board.
     */
    @Test
    public void testRemovePiece() {
        assertNotNull(board.getPieceAt(new Coordinate(3, 6)));

        Piece piece = board.getPieceAt(new Coordinate(3, 6));
        board.removePiece(piece);

        assertNull(board.getPieceAt(new Coordinate(3, 6)));
    }

    /**
     * Test of removePieceAt method, of class Board.
     */
    @Test
    public void testRemovePieceAt() {
        assertNotNull(board.getPieceAt(new Coordinate(3, 6)));

        board.removePieceAt(new Coordinate(3, 6));

        assertNull(board.getPieceAt(new Coordinate(3, 6)));
    }

    /**
     * Test of hasPiece method, of class Board.
     */
    @Test
    public void testHasPiece() {
        assertFalse(board.hasPiece(new Coordinate(3, 5)));
        assertTrue(board.hasPiece(new Coordinate(3, 6)));
    }

    /**
     * Test of getNumberOfPieces method, of class Board.
     */
    @Test
    public void testGetNumberOfPieces() {
        assertEquals(32, board.getNumberOfPieces());

        board.removePieceAt(new Coordinate(0, 0));
        board.removePieceAt(new Coordinate(7, 7));

        assertEquals(30, board.getNumberOfPieces());
    }

    /**
     * Test of isOnRow method, of class Board.
     */
    @Test
    public void testIsOnRow() {
        Piece piece = board.getPieceAt(new Coordinate(3, 6));

        assertTrue(board.isOnRow(piece, 6));
        assertFalse(board.isOnRow(piece, 2));
    }

    /**
     * Test of isInCheck method, of class Board.
     */
    @Test
    public void testIsInCheck() {
        assertFalse(board.isInCheck(Color.BLACK));

        board.moveTo(new Move(board, new Coordinate(5, 7), new Coordinate(7, 3)));
        board.removePieceAt(new Coordinate(5, 1));

        assertTrue(board.isInCheck(Color.BLACK));
    }

    /**
     * Test of isStalemate method, of class Board.
     */
    @Test
    public void testIsStalemate() {
        assertFalse(board.isStalemate(Color.BLACK));

        board = new Board(new Spot[Board.BOARD_SIZE][Board.BOARD_SIZE]);

        Piece whiteKing = new King(Color.WHITE);
        Piece blackKing = new King(Color.BLACK);
        Piece whiteQueen = new Queen(Color.WHITE);

        board.addPiece(whiteKing, new Coordinate(5, 1));
        board.addPiece(whiteQueen, new Coordinate(6, 2));
        board.addPiece(blackKing, new Coordinate(7, 0));

        assertTrue(board.isStalemate(Color.BLACK));
    }

    /**
     * Test of isCheckMate method, of class Board.
     */
    @Test
    public void testIsCheckMate() {
        assertFalse(board.isCheckMate(Color.BLACK));

        board = new Board(new Spot[Board.BOARD_SIZE][Board.BOARD_SIZE]);

        Piece whiteKing = new King(Color.WHITE);
        Piece whiteQueen = new Queen(Color.WHITE);
        Piece whiteRook = new Rook(Color.WHITE);
        Piece blackKing = new King(Color.BLACK);

        board.addPiece(whiteKing, new Coordinate(7, 7));
        board.addPiece(whiteQueen, new Coordinate(1, 1));
        board.addPiece(whiteRook, new Coordinate(0, 0));
        board.addPiece(blackKing, new Coordinate(6, 0));

        assertTrue(board.isCheckMate(Color.BLACK));
        assertFalse(board.isCheckMate(Color.WHITE));

    }

    /**
     * Test of isInCheckAfterThisMove method, of class Board.
     */
    @Test
    public void testIsInCheckAfterThisMove() {
        Move move1 = new Move(board, new Coordinate(5, 7), new Coordinate(7,7));
        assertFalse(board.isInCheckAfterThisMove(move1, Color.BLACK));

        Move move2 = new Move(board, new Coordinate(5, 7), new Coordinate(5, 1));
        assertTrue(board.isInCheckAfterThisMove(move2, Color.BLACK));
    }

    /**
     * Test of findPiece method, of class Board.
     */
    @Test
    public void testFindPiece() {
       Piece piece = new Pawn(Color.WHITE);

       assertNull(board.findPiece(piece));

       board.addPiece(piece, new Coordinate(4,4));

       assertEquals(new Coordinate(4,4), board.findPiece(piece));
    }

    /**
     * Test of findKing method, of class Board.
     */
    @Test
    public void testFindKing() {
        assertEquals(new Coordinate(4,0), board.findKing(Color.BLACK));
        assertEquals(new Coordinate(4,7), board.findKing(Color.WHITE));
    }

    /**
     * Test of getBishop method, of class Board.
     */
    @Test
    public void testGetBishop() {
        board.removePieceAt(new Coordinate(5,0));
        assertEquals(board.getPieceAt(new Coordinate(2,0)),
                board.getBishop(Color.BLACK));
    }

    /**
     * Test of getAllPiecesbyColor method, of class Board.
     */
    @Test
    public void testGetAllPiecesbyColor() {
        board = new Board(new Spot[Board.BOARD_SIZE][Board.BOARD_SIZE]);

        Piece whiteKing = new King(Color.WHITE);
        Piece whiteQueen = new Queen(Color.WHITE);
        Piece whiteRook = new Rook(Color.WHITE);
        Piece blackKing = new King(Color.BLACK);

        List<Piece> whitePieces = new ArrayList<>();
        whitePieces.add(whiteRook);
        whitePieces.add(whiteQueen);
        whitePieces.add(whiteKing);

        List<Piece> blackPieces = new ArrayList<>();
        blackPieces.add(blackKing);

        board.addPiece(whiteKing, new Coordinate(7, 7));
        board.addPiece(whiteQueen, new Coordinate(1, 1));
        board.addPiece(whiteRook, new Coordinate(0, 0));
        board.addPiece(blackKing, new Coordinate(6, 0));

        assertEquals(whitePieces, board.getAllPiecesbyColor(Color.WHITE));
        assertEquals(blackPieces, board.getAllPiecesbyColor(Color.BLACK));
    }

    /**
     * Test of getPieceAt method, of class Board.
     */
    @Test
    public void testGetPieceAt() {
        Piece piece = new Pawn(Color.WHITE);
        board.addPiece(piece, new Coordinate(4,4));

        assertEquals(piece, board.getPieceAt(new Coordinate(4,4)));
        assertNull(board.getPieceAt(new Coordinate(4,5)));
    }

    /**
     * Test of isCastlingAvailable method, of class Board.
     */
    @Test
    public void testIsCastlingAvailable() {
        Move castling = new Move(board, new Coordinate(4,7),new Coordinate(6,7));

        assertFalse(board.isCastlingAvailable(castling, Color.WHITE));

        board.removePieceAt(new Coordinate(5,7));
        board.removePieceAt(new Coordinate(6,7));

        assertTrue(board.isCastlingAvailable(castling, Color.WHITE));
    }

}

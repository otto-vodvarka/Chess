/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.pieces;

import model.chess.Board;
import model.chess.Color;
import model.chess.Coordinate;
import model.chess.Move;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ottovodvarka
 */
public class Rook extends Piece {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean isMoveValid(Board board, Move move) {
        return isMovementValid(move)
                && !super.istargetPieceMine(board.getPiece(move.getEnd()))
                && !super.isPathBlocked(board, move);
    }

    private boolean isMovementValid(Move move) {
        int xDifference = Math.abs(move.getStartX() - move.getEndX());
        int yDifference = Math.abs(move.getStartY() - move.getEndY());
        if (xDifference * yDifference == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getTextRepresantation() {
        if (color == Color.WHITE) {
            return "r";
        }
        return "t";
    }

    @Override
    public List<Move> getAllAvailableMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        Coordinate myCoord = board.findPiece(this);

        //left
        for (int i = 1; i < Board.BOARD_SIZE; i++) {
            Move move = new Move(board, myCoord, new Coordinate(myCoord.getX() - i, myCoord.getY()));
            if (move.isAtBoard() && isMoveValid(board, move)) {
                moves.add(move);
            } else {
                break;
            }
        }

        //right
        for (int i = 1; i < Board.BOARD_SIZE; i++) {
            Move move = new Move(board, myCoord, new Coordinate(myCoord.getX() + i, myCoord.getY()));
            if (move.isAtBoard() && isMoveValid(board, move)) {
                moves.add(move);
            } else {
                break;
            }
        }

        //up
        for (int i = 1; i < Board.BOARD_SIZE; i++) {
            Move move = new Move(board, myCoord, new Coordinate(myCoord.getX(), myCoord.getY() - i));
            if (move.isAtBoard() && isMoveValid(board, move)) {
                moves.add(move);
            } else {
                break;
            }
        }

        //down
        for (int i = 1; i < Board.BOARD_SIZE; i++) {
            Move move = new Move(board, myCoord, new Coordinate(myCoord.getX(), myCoord.getY() + i));
            if (move.isAtBoard() && isMoveValid(board, move)) {
                moves.add(move);
            } else {
                break;
            }
        }
        return moves;

    }

}

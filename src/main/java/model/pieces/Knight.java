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
public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    public boolean isMoveValid(Board board, Move move) {
        return isMovementValid(move)
                && !super.istargetPieceMine(board.getPieceAt(move.getEnd()));
    }

    private boolean isMovementValid(Move move) {
        int xDifference = Math.abs(move.getStartX() - move.getEndX());
        int yDifference = Math.abs(move.getStartY() - move.getEndY());
        if (xDifference * yDifference == 2) {
            return true;
        }
        return false;
    }

    @Override
    public String getTextRepresantation() {
        if (color == Color.WHITE) {
            return "n";
        }
        return "m";
    }

    @Override
    public List<Move> getAllAvailableMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        Coordinate myCoord = board.findPiece(this);

        //square
        for (int i = -1; i <= 1; i++) {
            //up
            Move moveUp = new Move(board, myCoord, new Coordinate(myCoord.getX() + i, myCoord.getY() - 2));
            if (moveUp.isAtBoard() && isMoveValid(board, moveUp)) {
                moves.add(moveUp);
            }

            //down
            Move moveDown = new Move(board, myCoord, new Coordinate(myCoord.getX() + i, myCoord.getY() + 2));
            if (moveDown.isAtBoard() && isMoveValid(board, moveDown)) {
                moves.add(moveDown);
            }

            //left
            Move moveLeft = new Move(board, myCoord, new Coordinate(myCoord.getX() - 2, myCoord.getY() + i));
            if (moveLeft.isAtBoard() && isMoveValid(board, moveLeft)) {
                moves.add(moveLeft);
            }

            //right
            Move moveRight = new Move(board, myCoord, new Coordinate(myCoord.getX() + 2, myCoord.getY() + i));
            if (moveRight.isAtBoard() && isMoveValid(board, moveRight)) {
                moves.add(moveRight);
            }
        }
        return moves;
    }

}

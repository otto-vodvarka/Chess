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
public abstract class Piece {

    protected final Color color;
    protected boolean moved;

    public Piece(Color color) {
        this.color = color;
    }

    public abstract boolean isMoveValid(Board board, Move move);

    //All moves including moves that creates check
    public abstract List<Move> getAllAvailableMoves(Board board);

    public abstract String getTextRepresantation();

    //All moves excluding moves that creates check
    public List<Move> getAllLegalMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        for (Move move : getAllAvailableMoves(board)) {
            if (!board.isInCheckAfterThisMove(move, color)) {
                moves.add(move);
            }
        }
        return moves;
    }

    protected boolean istargetPieceMine(Piece target) {
        if (target == null) {
            return false;
        }
        return target.color == this.color;
    }

    protected boolean isPathBlocked(Board board, Move move) {
        int xDifference = move.getStartX() - move.getEndX();
        int yDifference = move.getStartY() - move.getEndY();

        for (int i = 1; i < Math.max(Math.abs(xDifference), Math.abs(yDifference)); i++) {
            Coordinate coord = new Coordinate(move.getStartX(), move.getStartY());

            if (xDifference != 0) {
                if (xDifference < 0) {
                    coord.setX(move.getStartX() + i);
                } else {
                    coord.setX(move.getStartX() - i);
                }
            }

            if (yDifference != 0) {
                if (yDifference < 0) {
                    coord.setY(move.getStartY() + i);
                } else {
                    coord.setY(move.getStartY() - i);
                }
            }

            if (board.hasPiece(coord)) {
                return true;
            }
        }
        return false;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

}

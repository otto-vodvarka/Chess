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
public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public boolean isMoveValid(Board board, Move move) {
        return isMovementValid(board, move)
                && !super.istargetPieceMine(board.getPieceAt(move.getEnd()))
                && !super.isPathBlocked(board, move);
    }

    private boolean isMovementValid(Board board, Move move) {
        if (color == Color.WHITE) {
            return whiteValidation(board, move);
        }
        return blackValidation(board, move);
    }

    private boolean blackValidation(Board board, Move move) {
        int xDifference = move.getStartX() - move.getEndX();
        int yDifference = move.getStartY() - move.getEndY();
        if (board.hasPiece(move.getEnd())) {
            if (yDifference == -1 && (xDifference == -1 || xDifference == 1)) {
                return true;
            }
        } else {
            if (move.isEnPassant()) {
                return true;
            }

            if (board.isOnRow(this, 1)) {
                if ((yDifference == -1 || yDifference == -2) && xDifference == 0) {
                    return true;
                }
            } else {
                if (yDifference == -1 && xDifference == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean whiteValidation(Board board, Move move) {
        int xDifference = move.getStartX() - move.getEndX();
        int yDifference = move.getStartY() - move.getEndY();
        if (board.hasPiece(move.getEnd())) {
            if (yDifference == 1 && (xDifference == -1 || xDifference == 1)) {
                return true;
            }
        } else {
            if (move.isEnPassant()) {
                return true;
            }

            if (board.isOnRow(this, 6)) {
                if ((yDifference == 1 || yDifference == 2) && xDifference == 0) {
                    return true;
                }
            } else {
                if (yDifference == 1 && xDifference == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getTextRepresantation() {
        if (color == Color.WHITE) {
            return "p";
        }
        return "o";
    }

    @Override
    public List<Move> getAllAvailableMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        Coordinate myCoord = board.findPiece(this);

        //one block forward
        for (int i = -1; i <= 1; i++) {
            //white
            Move moveWhite = new Move(board, myCoord, new Coordinate(myCoord.getX() + i, myCoord.getY() - 1));
            if (moveWhite.isAtBoard() && isMoveValid(board, moveWhite)) {
                moves.add(moveWhite);
            }

            //black
            Move moveBlack = new Move(board, myCoord, new Coordinate(myCoord.getX() + i, myCoord.getY() + 1));
            if (moveBlack.isAtBoard() && isMoveValid(board, moveBlack)) {
                moves.add(moveBlack);
            }
        }

        //two steps forward
        //white
        Move moveWhite = new Move(board, myCoord, new Coordinate(myCoord.getX(), myCoord.getY() - 2));
        if (moveWhite.isAtBoard() && isMoveValid(board, moveWhite)) {
            moves.add(moveWhite);
        }
        //black
        Move moveBlack = new Move(board, myCoord, new Coordinate(myCoord.getX(), myCoord.getY() + 2));
        if (moveBlack.isAtBoard() && isMoveValid(board, moveBlack)) {
            moves.add(moveBlack);
        }
        return moves;
    }

}

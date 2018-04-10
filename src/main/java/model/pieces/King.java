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
public class King extends Piece {

    public King(Color color) {
        super(color);
    }

    @Override
    public boolean isMoveValid(Board board, Move move) {
        if(isMovementValid(move)){
            if(!super.istargetPieceMine(board.getPiece(move.getEnd()))){
                return true;
            }
        }else{
            if(board.getSpot(move.getEnd()) != null && !board.getSpot(move.getEnd()).isOccupied()){
                if(move.isCastling()){
                    if (board.IsCastlingAvailable(move, color)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isMovementValid(Move move) {
        int xDifference = Math.abs(move.getStartX() - move.getEndX());
        int yDifference = Math.abs(move.getStartY() - move.getEndY());
        if (xDifference == 1 && (yDifference == 1 || yDifference == 0)) {
            return true;
        }
        if (yDifference == 1 && (xDifference == 1 || xDifference == 0)) {
            return true;
        }
        return false;
    }

    @Override
    public String getTextRepresantation() {
        if (color == Color.WHITE) {
            return "k";
        }
        return "l";
    }

    @Override
    public List<Move> getAllAvailableMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        Coordinate myCoord = board.findPiece(this);

        //upper lower line
        for (int i = -1; i <= 1; i++) {
            //upper
            Move moveUp = new Move(board, myCoord, new Coordinate(myCoord.getX() + i, myCoord.getY() - 1));
            if (moveUp.isAtBoard() && isMoveValid(board, moveUp)) {
                moves.add(moveUp);
            }

            //lower
            Move moveDown = new Move(board, myCoord, new Coordinate(myCoord.getX() + i, myCoord.getY() + 1));
            if (moveDown.isAtBoard() && isMoveValid(board, moveDown)) {
                moves.add(moveDown);
            }
        }

        //sides
        //left
        Move moveLeft = new Move(board, myCoord, new Coordinate(myCoord.getX() - 1, myCoord.getY()));
        if (moveLeft.isAtBoard() && isMoveValid(board, moveLeft)) {
            moves.add(moveLeft);
        }
        //right
        Move moveRight = new Move(board, myCoord, new Coordinate(myCoord.getX() + 1, myCoord.getY()));
        if (moveRight.isAtBoard() && isMoveValid(board, moveRight)) {
            moves.add(moveRight);
        }

        //castling
        if (color == Color.WHITE) {
            //white
            Move castlingLeft = new Move(board, myCoord, new Coordinate(myCoord.getX() - 2, 7));
            if (castlingLeft.isAtBoard() && isMoveValid(board, castlingLeft)) {
                moves.add(castlingLeft);
            }

            Move castlingRight = new Move(board, myCoord, new Coordinate(myCoord.getX() + 2, 7));
            if (castlingRight.isAtBoard() && isMoveValid(board, castlingRight)) {
                moves.add(castlingRight);
            }
        } else {
            //black
            Move castlingLeft = new Move(board, myCoord, new Coordinate(myCoord.getX() - 2, 0));
            if (castlingLeft.isAtBoard() && isMoveValid(board, castlingLeft)) {
                moves.add(castlingLeft);
            }

            Move castlingRight = new Move(board, myCoord, new Coordinate(myCoord.getX() + 2, 0));
            if (castlingRight.isAtBoard() && isMoveValid(board, castlingRight)) {
                moves.add(castlingRight);
            }
        }

        return moves;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import model.pieces.King;
import model.pieces.Piece;
import model.pieces.Rook;
import java.util.Objects;

/**
 *
 * @author ottovodvarka
 */
public class Move {

    private final Coordinate start;
    private final Coordinate end;

    private boolean castling;

    public Move(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public Move(Board board, Piece piece, Coordinate end) {
        this.start = board.findPiece(piece);
        this.end = end;
    }

    public int getStartX() {
        return start.getX();
    }

    public int getStartY() {
        return start.getY();
    }

    public int getEndX() {
        return end.getX();
    }

    public int getEndY() {
        return end.getY();
    }

    public void setStartX(int x) {
        start.setX(x);
    }

    public void setStartY(int y) {
        start.setY(y);
    }

    public void setEndX(int x) {
        end.setX(x);
    }

    public void setEndY(int y) {
        end.setY(y);
    }

    public Coordinate getEnd() {
        return end;
    }

    public Coordinate getStart() {
        return start;
    }

    public boolean isAtBoard() {
        return start.getX() >= 0 && start.getX() < Board.BOARD_SIZE
                && start.getY() >= 0 && start.getY() < Board.BOARD_SIZE
                && end.getX() >= 0 && end.getX() < Board.BOARD_SIZE
                && end.getY() >= 0 && end.getY() < Board.BOARD_SIZE;
    }

    public Move invertMove() {
        return new Move(end, start);
    }

    public boolean isCastling(Board board, Color color) {
        Piece king = board.getPiece(this.getStart());
        if (king == null || !(king instanceof King)) {
            return false;
        }
        if (king.hasMoved()) {
            return false;
        }
        if (color == Color.WHITE) {
            if (this.getStart().equals(new Coordinate(4, 7))) {

                if (this.getEnd().equals(new Coordinate(2, 7))) {
                    Piece rook = board.getPiece(new Coordinate(0, 7));
                    if (rook != null && rook instanceof Rook && !rook.hasMoved()) {
                        setCastling(true);
                    }
                }

                if (this.getEnd().equals(new Coordinate(6, 7))) {
                    Piece rook = board.getPiece(new Coordinate(0, 7));
                    if (rook != null && rook instanceof Rook && !rook.hasMoved()) {
                        setCastling(true);
                    }
                }

            }
        } else {

            if (this.getStart().equals(new Coordinate(4, 0))) {

                if (this.getEnd().equals(new Coordinate(2, 0))) {
                    Piece rook = board.getPiece(new Coordinate(0, 0));
                    if (rook != null && rook instanceof Rook && !rook.hasMoved()) {
                        setCastling(true);
                    }
                }

                if (this.getEnd().equals(new Coordinate(6, 0))) {
                    Piece rook = board.getPiece(new Coordinate(7, 0));
                    if (rook != null && rook instanceof Rook && !rook.hasMoved()) {
                        setCastling(true);
                    }
                }

            }
        }
        return castling;
    }

    public Move getCastlingRookMove(Color color) {
        Move moveRook = null;
        //white small castling
        if (color == Color.WHITE && this.getEnd().equals(new Coordinate(6, 7))) {
            moveRook = new Move(new Coordinate(7, 7), new Coordinate(5, 7));
        }
        //white big castling
        if (color == Color.WHITE && this.getEnd().equals(new Coordinate(2, 7))) {
            moveRook = new Move(new Coordinate(0, 7), new Coordinate(3, 7));
        }
        //black small castling
        if (color == Color.BLACK && this.getEnd().equals(new Coordinate(6, 0))) {
            moveRook = new Move(new Coordinate(7, 0), new Coordinate(5, 0));
        }
        //black big castling
        if (color == Color.BLACK && this.getEnd().equals(new Coordinate(2, 0))) {
            moveRook = new Move(new Coordinate(0, 0), new Coordinate(3, 0));
        }
        return moveRook;
    }

    public void setCastling(boolean castling) {
        this.castling = castling;
    }

    public boolean isCastling() {
        return castling;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.start);
        hash = 97 * hash + Objects.hashCode(this.end);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Move other = (Move) obj;
        if (!Objects.equals(this.start, other.start)) {
            return false;
        }
        if (!Objects.equals(this.end, other.end)) {
            return false;
        }
        return true;
    }

    

       
    

}

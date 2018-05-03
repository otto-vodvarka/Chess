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
import model.pieces.Pawn;

/**
 *
 * @author ottovodvarka
 */
public class Move {

    private final Board board;

    private final Coordinate start;
    private final Coordinate end;

    private MoveType moveType;

    /**
     *
     * @param board
     * @param start
     * @param end
     */
    public Move(Board board, Coordinate start, Coordinate end) {
        this.board = board;
        this.start = start;
        this.end = end;
        setMoveType(board, board.getPieceAt(start).getColor());
    }

    /**
     *
     * @param board
     * @param piece
     * @param end
     */
    public Move(Board board, Piece piece, Coordinate end) {
        this.board = board;
        this.start = board.findPiece(piece);
        this.end = end;
        setMoveType(board, piece.getColor());
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

    /**
     * 
     * @return True if both start nad end of move is on board
     */
    public boolean isAtBoard() {
        return start.getX() >= 0 && start.getX() < Board.BOARD_SIZE
                && start.getY() >= 0 && start.getY() < Board.BOARD_SIZE
                && end.getX() >= 0 && end.getX() < Board.BOARD_SIZE
                && end.getY() >= 0 && end.getY() < Board.BOARD_SIZE;
    }

    /**
     * Switch start and end of move
     * @return new Move
     */
    public Move invertMove() {
        return new Move(board, end, start);
    }

    private void setMoveType(Board board, Color color) {
        moveType = MoveType.REGULAR;
        setPawnJumpIfTrue(board);
        setCastlingIfTrue(board, color);
        setEnpassantIfTrue(board, color);
        setPromotionIfTrue(board, color);
    }

    private void setPawnJumpIfTrue(Board board) {
        Piece pawn = board.getPieceAt(this.getStart());
        if (pawn == null || !(pawn instanceof Pawn)) {
            return;
        }

        int xDifference = Math.abs(getStartX() - getEndX());
        int yDifference = Math.abs(getStartY() - getEndY());
        if (yDifference == 2 && xDifference == 0) {
            moveType = MoveType.PAWNJUMP;
        }
    }

    private void setCastlingIfTrue(Board board, Color color) {
        Piece king = board.getPieceAt(this.getStart());
        if (king == null || !(king instanceof King)) {
            return;
        }
        if (king.hasMoved()) {
            return;
        }
        if (color == Color.WHITE) {
            if (this.getStart().equals(new Coordinate(4, 7))) {

                if (this.getEnd().equals(new Coordinate(2, 7))) {
                    Piece rook = board.getPieceAt(new Coordinate(0, 7));
                    if (rook != null && rook instanceof Rook && !rook.hasMoved()) {
                        moveType = MoveType.CASTLING;
                        return;
                    }
                }

                if (this.getEnd().equals(new Coordinate(6, 7))) {
                    Piece rook = board.getPieceAt(new Coordinate(0, 7));
                    if (rook != null && rook instanceof Rook && !rook.hasMoved()) {
                        moveType = MoveType.CASTLING;
                        return;
                    }
                }

            }
        } else {

            if (this.getStart().equals(new Coordinate(4, 0))) {

                if (this.getEnd().equals(new Coordinate(2, 0))) {
                    Piece rook = board.getPieceAt(new Coordinate(0, 0));
                    if (rook != null && rook instanceof Rook && !rook.hasMoved()) {
                        moveType = MoveType.CASTLING;
                        return;
                    }
                }

                if (this.getEnd().equals(new Coordinate(6, 0))) {
                    Piece rook = board.getPieceAt(new Coordinate(7, 0));
                    if (rook != null && rook instanceof Rook && !rook.hasMoved()) {
                        moveType = MoveType.CASTLING;
                        return;
                    }
                }

            }
        }
    }

    private void setEnpassantIfTrue(Board board, Color color) {
        Piece pawn = board.getPieceAt(this.getStart());
        if (pawn == null || !(pawn instanceof Pawn)) {
            return;
        }
        if(board.getLastMove() == null){
            return;
        }
        if (!board.getLastMove().isPawnJump()) {
            return;
        }
        if (getEndX() != board.getLastMove().getEndX()) {
            return;
        }

        if (color == Color.WHITE) {
            if (getEndY() + 1 == board.getLastMove().getEndY()) {
                moveType = MoveType.ENPASSANT;
                return;
            }
        } else {
            if (getEndY() - 1 == board.getLastMove().getEndY()) {
                moveType = MoveType.ENPASSANT;
                return;
            }
        }
    }

    private void setPromotionIfTrue(Board board, Color color) {
        Piece pawn = board.getPieceAt(this.getStart());
        if (pawn == null || !(pawn instanceof Pawn)) {
            return;
        }
        if (color == Color.WHITE) {
            if (getEndY() == 0) {
                moveType = MoveType.PROMOTION;
                return;
            }
        } else {
            if (getEndY() == Board.BOARD_SIZE-1) {
                moveType = MoveType.PROMOTION;
                return;
            }
        }

    }

    /**
     *
     * @param color
     * @return move of rook when castling
     */
    public Move getCastlingRookMove(Color color) {
        Move moveRook = null;
        //white small castling
        if (color == Color.WHITE && this.getEnd().equals(new Coordinate(6, 7))) {
            moveRook = new Move(board, new Coordinate(7, 7), new Coordinate(5, 7));
        }
        //white big castling
        if (color == Color.WHITE && this.getEnd().equals(new Coordinate(2, 7))) {
            moveRook = new Move(board, new Coordinate(0, 7), new Coordinate(3, 7));
        }
        //black small castling
        if (color == Color.BLACK && this.getEnd().equals(new Coordinate(6, 0))) {
            moveRook = new Move(board, new Coordinate(7, 0), new Coordinate(5, 0));
        }
        //black big castling
        if (color == Color.BLACK && this.getEnd().equals(new Coordinate(2, 0))) {
            moveRook = new Move(board, new Coordinate(0, 0), new Coordinate(3, 0));
        }
        return moveRook;
    }

    /**
     *
     * @return
     */
    public boolean isPawnJump() {
        return moveType == MoveType.PAWNJUMP;
    }

    /**
     *
     * @return
     */
    public boolean isCastling() {
        return moveType == MoveType.CASTLING;
    }

    /**
     *
     * @return
     */
    public boolean isEnPassant() {
        return moveType == MoveType.ENPASSANT;
    }

    /**
     *
     * @return
     */
    public boolean isPromotion() {
        return moveType == MoveType.PROMOTION;
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

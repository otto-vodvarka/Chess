/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import model.pieces.Piece;
import model.pieces.King;
import model.pieces.Queen;
import model.pieces.Knight;
import model.pieces.Bishop;
import model.pieces.Rook;
import model.pieces.Pawn;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ottovodvarka
 */
public class Board {

    public static final int BOARD_SIZE = 8;
    
    private Spot[][] spots;
    private Move lastMove;

    public Board() {
        spots = new Spot[BOARD_SIZE][BOARD_SIZE];
        setupPieces();
    }

    public Board(Spot[][] spots) {
        this.spots = spots;
    }
    
    public void moveTo(Move move) {
        Piece piece = getPiece(move.getStart());
        if(move.isCastling()){
            castle(piece, move);
            return;
        }
        if(move.isEnPassant()){
            enPassant(piece, move);
            return;
        }
        if(move.isPromotion()){
            promotion(piece, move);
            return;
        }
        removePiece(move.getStart());
        addPiece(piece, move.getEnd());
        piece.setMoved(true);
        lastMove = move;
    }

    public void moveTo(Piece piece, Coordinate coord) {
        Move move = new Move(this, piece, coord);
        moveTo(move);
    }
    
    private void castle(Piece piece, Move move){
        Move rookMove = move.getCastlingRookMove(piece.getColor());
        Piece rook = getPiece(rookMove.getStart());
        removePiece(move.getStart());
        removePiece(rookMove.getStart());
        addPiece(piece, move.getEnd());
        addPiece(rook, rookMove.getEnd());
        piece.setMoved(true);
    }
    
    private void enPassant(Piece piece, Move move){
        removePiece(move.getStart());
        addPiece(piece, move.getEnd());
        if(piece.getColor() == Color.WHITE){
            removePiece(new Coordinate(move.getEndX(), move.getEndY()+1));
        }else{
            removePiece(new Coordinate(move.getEndX(), move.getEndY()-1));
        }
    }
    
    private void promotion(Piece piece, Move move){
        removePiece(move.getStart());
        addPiece(new Queen(piece.getColor()), move.getEnd());
    }

    private void makeImaginaryMove(Move move) {
        Piece piece = getPiece(move.getStart());
        removePiece(move.getStart());
        addPiece(piece, move.getEnd());
    }

    public void addPiece(Piece piece, Coordinate coord) {
        spots[coord.getY()][coord.getX()] = new Spot(piece);
    }

    public void removePiece(Piece piece) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (spots[i][j] != null && spots[i][j].getPiece() == piece) {
                    spots[i][j].removePiece();
                    return;
                }
            }
        }
    }

    public void removePiece(Coordinate coord) {
        int x = coord.getY();
        int y = coord.getX();
        if (spots[x][y] != null) {
            spots[x][y].removePiece();
        }
    }

    public Coordinate findPiece(Piece piece) {
        Coordinate coord = null;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (spots[i][j] != null && spots[i][j].getPiece() == piece) {
                    coord = new Coordinate(j, i);
                }
            }
        }
        return coord;
    }

    public Coordinate findKing(Color color) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (spots[i][j] != null && spots[i][j].getPiece() != null
                        && spots[i][j].getPiece().getColor() == color
                        && spots[i][j].getPiece() instanceof King) {
                    return new Coordinate(j, i);
                }
            }
        }
        return null;
    }

    public Piece getPiece(Coordinate coord) {
        Spot spot = spots[coord.getY()][coord.getX()];
        if (spot != null) {
            return spot.getPiece();
        }
        return null;
    }

    public boolean hasPiece(Coordinate coord) {
        if (getPiece(coord) == null) {
            return false;
        }
        return true;
    }

    public boolean isOnRow(Piece piece, int row) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (spots[row][i] != null && spots[row][i].getPiece() == piece) {
                return true;
            }
        }
        return false;
    }

    public boolean isInCheck(Color color) {
        Coordinate kingCoord = findKing(color);
        for (Move move : getAllAvailableMovesByColor(color.opposite())) {
            if (move.getEnd().equals(kingCoord)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCheckMate(Color color) {
        if (isInCheck(color) && getAllLegalMovesByColor(color).isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isInCheckAfterThisMove(Move move, Color color) {
        if(move.isCastling()){
            return false;
        }
        boolean check = false;
        Piece piece = getPiece(move.getEnd());
        makeImaginaryMove(move);
        if (isInCheck(color)) {
            check = true;
        }
        makeImaginaryMove(move.invertMove());
        addPiece(piece, move.getEnd());
        return check;
    }

    public List<Move> getAllAvailableMovesByColor(Color color) {
        List<Move> moves = new ArrayList<>();
        for (Piece piece : getAllPiecesbyColor(color)) {
            moves.addAll(piece.getAllAvailableMoves(this));
        }
        return moves;
    }

    public List<Move> getAllLegalMovesByColor(Color color) {
        List<Move> moves = new ArrayList<>();
        for (Piece piece : getAllPiecesbyColor(color)) {
            moves.addAll(piece.getAllLegalMoves(this));
        }
        return moves;
    }

    public List<Piece> getAllPiecesbyColor(Color color) {
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (spots[i][j] != null && spots[i][j].getPiece() != null
                        && spots[i][j].getPiece().getColor() == color) {
                    pieces.add(spots[i][j].getPiece());
                }
            }
        }
        return pieces;
    }

    public boolean IsCastlingAvailable(Move move, Color color) {
        //white small castling
        if(color == Color.WHITE && move.getEnd().equals(new Coordinate(6, 7))){
            Move move1 = new Move(this, move.getStart(), new Coordinate(move.getEndX()-1, move.getEndY()));
            Move move2 = new Move(this, move.getStart(), new Coordinate(move.getEndX(), move.getEndY()));
            if(!getSpot(move1.getEnd()).isOccupied() && !getSpot(move2.getEnd()).isOccupied()){
                if(!isInCheckAfterThisMove(move1, color) && !isInCheckAfterThisMove(move2, color)){
                    return true;
                }
            }            
        }
        //white big castling
        if(color == Color.WHITE && move.getEnd().equals(new Coordinate(2, 7))){
            Move move1 = new Move(this, move.getStart(), new Coordinate(move.getEndX()-1, move.getEndY()));
            Move move2 = new Move(this, move.getStart(), new Coordinate(move.getEndX(), move.getEndY()));
            Move move3 = new Move(this, move.getStart(), new Coordinate(move.getEndX()+1, move.getEndY()));
            if(!getSpot(move1.getEnd()).isOccupied() && !getSpot(move2.getEnd()).isOccupied() && !getSpot(move3.getEnd()).isOccupied()){
                if(!isInCheckAfterThisMove(move1, color) && !isInCheckAfterThisMove(move2, color)){
                    return true;
                }
            }
        }
        //black small castling
        if(color == Color.BLACK && move.getEnd().equals(new Coordinate(6, 0))){
            Move move1 = new Move(this, move.getStart(), new Coordinate(move.getEndX()-1, move.getEndY()));
            Move move2 = new Move(this, move.getStart(), new Coordinate(move.getEndX(), move.getEndY()));
            if(!getSpot(move1.getEnd()).isOccupied() && !getSpot(move2.getEnd()).isOccupied()){
                if(!isInCheckAfterThisMove(move1, color) && !isInCheckAfterThisMove(move2, color)){
                    return true;
                }
            }
        }
        //black big castling
        if(color == Color.BLACK && move.getEnd().equals(new Coordinate(2, 0))){
            Move move1 = new Move(this, move.getStart(), new Coordinate(move.getEndX()-1, move.getEndY()));
            Move move2 = new Move(this, move.getStart(), new Coordinate(move.getEndX(), move.getEndY()));
            Move move3 = new Move(this, move.getStart(), new Coordinate(move.getEndX()+1, move.getEndY()));
            if(!getSpot(move1.getEnd()).isOccupied() && !getSpot(move2.getEnd()).isOccupied() && !getSpot(move3.getEnd()).isOccupied()){
                if(!isInCheckAfterThisMove(move1, color) && !isInCheckAfterThisMove(move2, color)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Spot getSpot(Coordinate coord){
        return spots[coord.getY()][coord.getX()];
    }

    public Move getLastMove() {
        if(lastMove == null){
            return new Move(this, new Coordinate(0, 0), new Coordinate(0,0));
        }
        return lastMove;
    }

    private void setupPieces() {
        spots[0][0] = new Spot(new Rook(Color.BLACK));
        spots[0][1] = new Spot(new Knight(Color.BLACK));
        spots[0][2] = new Spot(new Bishop(Color.BLACK));
        spots[0][3] = new Spot(new Queen(Color.BLACK));
        spots[0][4] = new Spot(new King(Color.BLACK));
        spots[0][5] = new Spot(new Bishop(Color.BLACK));
        spots[0][6] = new Spot(new Knight(Color.BLACK));
        spots[0][7] = new Spot(new Rook(Color.BLACK));

        spots[1][0] = new Spot(new Pawn(Color.BLACK));
        spots[1][1] = new Spot(new Pawn(Color.BLACK));
        spots[1][2] = new Spot(new Pawn(Color.BLACK));
        spots[1][3] = new Spot(new Pawn(Color.BLACK));
        spots[1][4] = new Spot(new Pawn(Color.BLACK));
        spots[1][5] = new Spot(new Pawn(Color.BLACK));
        spots[1][6] = new Spot(new Pawn(Color.BLACK));
        spots[1][7] = new Spot(new Pawn(Color.BLACK));

        spots[7][0] = new Spot(new Rook(Color.WHITE));
        spots[7][1] = new Spot(new Knight(Color.WHITE));
        spots[7][2] = new Spot(new Bishop(Color.WHITE));
        spots[7][3] = new Spot(new Queen(Color.WHITE));
        spots[7][4] = new Spot(new King(Color.WHITE));
        spots[7][5] = new Spot(new Bishop(Color.WHITE));
        spots[7][6] = new Spot(new Knight(Color.WHITE));
        spots[7][7] = new Spot(new Rook(Color.WHITE));

        spots[6][0] = new Spot(new Pawn(Color.WHITE));
        spots[6][1] = new Spot(new Pawn(Color.WHITE));
        spots[6][2] = new Spot(new Pawn(Color.WHITE));
        spots[6][3] = new Spot(new Pawn(Color.WHITE));
        spots[6][4] = new Spot(new Pawn(Color.WHITE));
        spots[6][5] = new Spot(new Pawn(Color.WHITE));
        spots[6][6] = new Spot(new Pawn(Color.WHITE));
        spots[6][7] = new Spot(new Pawn(Color.WHITE));

    }

}

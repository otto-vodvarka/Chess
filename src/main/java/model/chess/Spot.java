/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import model.pieces.Piece;

/**
 *
 * @author ottovodvarka
 */
public class Spot {
    
    private Piece piece;

    /**
     *
     */
    public Spot() {
    }

    /**
     *
     * @param piece
     */
    public Spot(Piece piece) {
        this.piece = piece;
    }
    
    /**
     *
     * @param piece
     */
    public void setPiece(Piece piece){
        this.piece = piece;
    }
    
    /**
     *
     * @return True if place is occupied by piece
     */
    public boolean isOccupied(){
        return piece != null;
    }
    
    /**
     * Removes piece from the spot
     * @return piece
     */
    public Piece removePiece(){
        Piece tmp = this.piece;
        this.piece = null;
        return tmp;
    }
    
    public Piece getPiece() {
        return piece;
    }
    
}

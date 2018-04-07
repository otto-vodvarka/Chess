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

    public Spot() {
    }

    public Spot(Piece piece) {
        this.piece = piece;
    }
    
    public void setPiece(Piece piece){
        this.piece = piece;
    }
    
    public boolean isOccupied(){
        return piece != null;
    }
    
    public Piece removePiece(){
        Piece tmp = this.piece;
        this.piece = null;
        return tmp;
    }

    public Piece getPiece() {
        return piece;
    }
    
}

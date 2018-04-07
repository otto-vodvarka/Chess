/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.pieces.Piece;
import javafx.scene.control.Label;

/**
 *
 * @author ottovodvarka
 */
public class PieceView extends Label{
    
    private final Piece piece;

    public PieceView(Piece piece) {
        super(piece.getTextRepresantation());
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }    
    
}

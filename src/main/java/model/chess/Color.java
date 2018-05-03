package model.chess;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author ottovodvarka
 */
public enum Color {
    
    WHITE,BLACK;
    
    /**
     *
     * @return opposite color, for BLACK return WHITE and for WHITE return BLACK
     */
    public Color opposite() {
        if (this == WHITE) {
            return BLACK;
        }
        return WHITE;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

/**
 *
 * @author ottovodvarka
 */
public abstract class Player {
    
    protected String name;
    protected Color color;
    protected boolean inCheck;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setIsInCheck(boolean isInCheck) {
        this.inCheck = isInCheck;
    }

    public boolean isInCheck() {
        return inCheck;
    }
    
    
    
}

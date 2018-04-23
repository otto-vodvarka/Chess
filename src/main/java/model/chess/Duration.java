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
public class Duration {

    private int seconds;

    public Duration(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public void decrease() {
        seconds--;
    }

    @Override
    public String toString() {
        return seconds / 60 + " : " + seconds % 60;
    }

}

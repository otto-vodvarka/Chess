/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

/**
 *
 * @author ottovodvarka
 */
public class Duration {

    private int seconds;

    /**
     * 
     * @param seconds
     */
    public Duration(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    /**
     * Decrease countdown by 1 second
     */
    public void decrease() {
        seconds--;
    }

    @Override
    public String toString() {
        StringBuilder sbuf = new StringBuilder();
        Formatter fmt = new Formatter(sbuf);
        fmt.format("%02d:%02d", seconds / 60, seconds % 60);
        return sbuf.toString();
    }

}

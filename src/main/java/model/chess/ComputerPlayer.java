/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author ottovodvarka
 */
public class ComputerPlayer extends Player {

    public ComputerPlayer(String name, Color color) {
        super(name, color);
    }

    private Move generateMove(Board board) {
        List<Move> legalMoves = board.getAllLegalMovesByColor(this.color);
        Random random = new Random();
        return legalMoves.get(random.nextInt(legalMoves.size()));
    }

    @Override
    public void play(Game game) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Random r = new Random();
                    Thread.sleep(r.nextInt(2000)+1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ComputerPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        game.getBoard().moveTo(generateMove(game.getBoard()));
                    }
                });
            }
        });
        t.start();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import model.pieces.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ottovodvarka
 */
public class ChessLoader {

    /**
     * Load game from file
     * @param file file to be loaded
     * @return instance of game
     * @throws Exception
     */
    public Game loadGame(File file) throws Exception {

        String s = "";
        String nextLine = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            while ((nextLine = reader.readLine()) != null) {
                s += nextLine;
            }
        }
        JSONObject json = new JSONObject(s);
        Game game = loadGameFromJSON(json);

        return game;
    }

    private Game loadGameFromJSON(JSONObject json) {
        Board board = loadBoardFromJSON(json);
        Player player1 = loadPlayerFromJSON(json, "player1");
        Player player2 = loadPlayerFromJSON(json, "player2");
        Game game = new Game(board, player1, player2);
        Color color = json.getJSONObject("playerOnMove").getEnum(Color.class, "color");
        game.setPlayerOnMove(game.getPlayerByColor(color));
        return game;
    }

    private Board loadBoardFromJSON(JSONObject json) {
        Spot[][] spots = new Spot[Board.BOARD_SIZE][Board.BOARD_SIZE];

        JSONArray boardJSON = json.getJSONArray("board");

        for (Object object : boardJSON) {
            JSONObject pieceJSON = (JSONObject) object;

            int x = pieceJSON.getInt("x");
            int y = pieceJSON.getInt("y");
            String type = pieceJSON.getString("type");

            Piece piece = getPieceFromText(type);
            spots[x][y] = new Spot(piece);
        }

        return new Board(spots);
    }

    private Player loadPlayerFromJSON(JSONObject json, String key) {
        JSONObject playerJSON = json.getJSONObject(key);

        String name = playerJSON.getString("name");
        Color color = playerJSON.getEnum(Color.class, "color");
        String type = playerJSON.getString("type");

        Player player;
        if (type.equals("Human")) {
            player = new HumanPlayer(name, color);
        } else {
            player = new ComputerPlayer(name, color);
        }
        player.setTime(new Duration(playerJSON.getInt("time")));

        return player;
    }

    private Piece getPieceFromText(String text) {
        Piece piece = null;
        switch (text) {
            case "b":
                piece = new Bishop(Color.WHITE);
                break;
            case "v":
                piece = new Bishop(Color.BLACK);
                break;
            case "k":
                piece = new King(Color.WHITE);
                break;
            case "l":
                piece = new King(Color.BLACK);
                break;
            case "n":
                piece = new Knight(Color.WHITE);
                break;
            case "m":
                piece = new Knight(Color.BLACK);
                break;
            case "p":
                piece = new Pawn(Color.WHITE);
                break;
            case "o":
                piece = new Pawn(Color.BLACK);
                break;
            case "q":
                piece = new Queen(Color.WHITE);
                break;
            case "w":
                piece = new Queen(Color.BLACK);
                break;
            case "r":
                piece = new Rook(Color.WHITE);
                break;
            case "t":
                piece = new Rook(Color.BLACK);
                break;
        }
        return piece;
    }

}

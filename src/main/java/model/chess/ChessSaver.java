/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import model.pieces.Piece;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ottovodvarka
 */
public class ChessSaver {

    /**
     * Saves game to text file in JSON format
     * @param game game to be saved
     * @param file selected file
     * @throws IOException
     */
    public void save(Game game, File file) throws IOException {
        try (
                FileWriter out = new FileWriter(file)) {

            out.write(getGameInJSON(game).toString());
        }
    }

    private JSONObject getGameInJSON(Game game) {
        JSONObject gameJson = new JSONObject();

        JSONObject player1 = getPlayerInJSON(game.getPlayer1());
        JSONObject player2 = getPlayerInJSON(game.getPlayer2());
        JSONObject playerOnMove = new JSONObject();
        playerOnMove.put("color", game.getPlayerOnMove().getColor());

        JSONArray board = getBoardInJSON(game.getBoard());
        gameJson.put("player1", player1);
        gameJson.put("player2", player2);
        gameJson.put("playerOnMove", playerOnMove);
        gameJson.put("board", board);

        return gameJson;
    }

    private JSONObject getPlayerInJSON(Player player) {
        JSONObject playerJSON = new JSONObject();

        playerJSON.put("name", player.getName());
        playerJSON.put("time", player.getTime().getSeconds());
        playerJSON.put("color", player.getColor());

        String type;
        if (player instanceof HumanPlayer) {
            type = "Human";
        } else {
            type = "Computer";
        }

        playerJSON.put("type", type);

        return playerJSON;
    }

    private JSONArray getBoardInJSON(Board board) {
        JSONArray boardJSON = new JSONArray();

        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                Piece piece = board.getPieceAt(new Coordinate(i, j));

                if (piece != null) {
                    JSONObject pieceJSON = new JSONObject();
                    
                    pieceJSON.put("x", j);
                    pieceJSON.put("y", i);
                    pieceJSON.put("type", piece.getTextRepresantation());
                    
                    boardJSON.put(pieceJSON);

                }
            }
        }
        
        return boardJSON;
    }
}

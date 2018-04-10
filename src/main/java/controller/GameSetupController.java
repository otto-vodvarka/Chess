/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.chess.Board;
import model.chess.Color;
import model.chess.Game;
import model.chess.HumanPlayer;
import model.chess.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ottovodvarka
 */
public class GameSetupController implements Initializable {

    @FXML
    private Button newGameButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void startNewGame(MouseEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/chessFXML.fxml"));

            Parent root = loader.load();
            ChessFXMLController controller = loader.getController();
            controller.setupGame(createGame());

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/chess.css");
            stage.setScene(scene);

            stage.show();
            ((Stage) newGameButton.getScene().getWindow()).close();

        } catch (IOException ex) {
            Logger.getLogger(GameSetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Game createGame() {
        Player player1 = new HumanPlayer("Otto", Color.WHITE);
        Player player2 = new HumanPlayer("Tomáš", Color.BLACK);
        return new Game(new Board(), player1, player2);
    }

}

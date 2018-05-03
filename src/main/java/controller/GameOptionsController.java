/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
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
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.chess.AlertUtils;
import model.chess.ChessLoader;
import model.chess.Game;

/**
 * FXML Controller class
 *
 * @author ottovodvarka
 */
public class GameOptionsController implements Initializable {

    @FXML
    private Button newGameButton;
    @FXML
    private Button loadGameButton;
    @FXML
    private Button customSetupButton;
    @FXML
    private MenuItem mainMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onNewGameClicked(MouseEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/gameSetup.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
            ((Stage) newGameButton.getScene().getWindow()).close();

        } catch (IOException ex) {
            AlertUtils.showInfoDialog("Sorry, please contact us if you have this error frequently", "Error");
            Logger.getLogger(GameSetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onLoadGameClicked(MouseEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file");
        File file = fileChooser.showOpenDialog(newGameButton.getScene().getWindow());
        if (file == null) {
            return;
        }

        try {
            ChessLoader loader = new ChessLoader();
            Game game = loader.loadGame(file);
            startGame(game);

        } catch (Exception ex) {
            AlertUtils.showInfoDialog("Loading failed", "Error");
            Logger.getLogger(GameOptionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startGame(Game game) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/chessFXML.fxml"));

            Parent root = loader.load();
            ChessFXMLController controller = loader.getController();
            controller.setupGame(game);

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
            ((Stage) newGameButton.getScene().getWindow()).close();

        } catch (IOException ex) {
            AlertUtils.showInfoDialog("Sorry, please contact us if you have this error frequently", "Error");
            Logger.getLogger(GameSetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onCustonSetupClicked(MouseEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/customSetup.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
            ((Stage) newGameButton.getScene().getWindow()).close();

        } catch (IOException ex) {
            AlertUtils.showInfoDialog("Sorry, please contact us if you have this error frequently", "Error");
            Logger.getLogger(GameSetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goToMainMenu() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/start.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
            ((Stage) newGameButton.getScene().getWindow()).close();

        } catch (IOException ex) {
            AlertUtils.showInfoDialog("Sorry, please contact us if you have this error frequently", "Error");
            Logger.getLogger(GameSetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

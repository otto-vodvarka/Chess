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
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.chess.ComputerPlayer;

/**
 * FXML Controller class
 *
 * @author ottovodvarka
 */
public class GameSetupController implements Initializable {

    private Board board = new Board();
    
    @FXML
    private Button newGameButton;
    @FXML
    private ChoiceBox<String> player1ChoiceBox;
    @FXML
    private TextField player1EditText;
    @FXML
    private TextField player2EditText;
    @FXML
    private Label player1ColorLabel;
    @FXML
    private Label player2ColorLabel;
    @FXML
    private ChoiceBox<String> player2ChoiceBox;
    @FXML
    private ImageView arrowImageView;
    @FXML
    private MenuItem mainMenu;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        player1ChoiceBox.setItems(FXCollections.observableArrayList("Human Player", "Computer"));
        player1ChoiceBox.getSelectionModel().selectFirst();

        player2ChoiceBox.setItems(FXCollections.observableArrayList("Human Player", "Computer"));
        player2ChoiceBox.getSelectionModel().selectFirst();

        player1ColorLabel.setFont(Font.loadFont(ChessFXMLController.class.getResource("/fonts/CASEFONT.TTF").toExternalForm(), 30));
        player1ColorLabel.setText("r");

        player2ColorLabel.setFont(Font.loadFont(ChessFXMLController.class.getResource("/fonts/CASEFONT.TTF").toExternalForm(), 30));
        player2ColorLabel.setText("t");
    }

    @FXML
    private void startNewGame(MouseEvent event) {
        if (!isDataCorrect()) {
            return;
        }

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/chessFXML.fxml"));

            Parent root = loader.load();
            ChessFXMLController controller = loader.getController();
            controller.setupGame(createGame());

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
            ((Stage) newGameButton.getScene().getWindow()).close();

        } catch (IOException ex) {
            Logger.getLogger(GameSetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Game createGame() {
        Player playerWhite;
        Player playerBlack;

        if (player1ColorLabel.getText().equals("r")) {
            if (player1ChoiceBox.getValue().equals("Human Player")) {
                playerWhite = new HumanPlayer(player1EditText.getText(), Color.WHITE);
            } else {
                playerWhite = new ComputerPlayer(player1EditText.getText(), Color.WHITE);
            }
            if (player2ChoiceBox.getValue().equals("Human Player")) {
                playerBlack = new HumanPlayer(player2EditText.getText(), Color.BLACK);
            } else {
                playerBlack = new ComputerPlayer(player2EditText.getText(), Color.BLACK);
            }
        } else {
            if (player1ChoiceBox.getValue().equals("Human Player")) {
                playerWhite = new HumanPlayer(player1EditText.getText(), Color.BLACK);
            } else {
                playerWhite = new ComputerPlayer(player1EditText.getText(), Color.BLACK);
            }
            if (player2ChoiceBox.getValue().equals("Human Player")) {
                playerBlack = new HumanPlayer(player2EditText.getText(), Color.WHITE);
            } else {
                playerBlack = new ComputerPlayer(player2EditText.getText(), Color.WHITE);
            }
        }

        return new Game(board, playerWhite, playerBlack);
    }

    private boolean isDataCorrect() {
        if (player1ChoiceBox.getValue().equals("Computer") && player2ChoiceBox.getValue().equals("Computer")) {
            showInfoDialoag("At least one player has to be human");
            return false;
        }
        
        if (player1EditText.getText().isEmpty() || player2EditText.getText().isEmpty()){
            showInfoDialoag("Every player needs to have a name");
            return false;
        }
        
        if (player1EditText.getText().length() > 15 || player2EditText.getText().length() > 15){
            showInfoDialoag("Maximal length of name is 15 characters");
            return false;
        }
        
        return true;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @FXML
    private void switchColors(MouseEvent event) {
        if (player1ColorLabel.getText().equals("r")) {
            player1ColorLabel.setText("t");
            player2ColorLabel.setText("r");
        } else {
            player1ColorLabel.setText("r");
            player2ColorLabel.setText("t");
        }
    }

    private void showInfoDialoag(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Incorrect data");
        alert.setContentText(message);

        alert.showAndWait();
    }
    
    @FXML
    private void goToMainMenu(){
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
            Logger.getLogger(GameSetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

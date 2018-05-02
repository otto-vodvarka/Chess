package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.chess.AlertUtils;

/**
 *
 * @author ottovodvarka
 */
public class Chess extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/start.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (IOException ex) {
            AlertUtils.showInfoDialog("Sorry, please contact us if you have this error frequently", "Error");
            Logger.getLogger(Chess.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

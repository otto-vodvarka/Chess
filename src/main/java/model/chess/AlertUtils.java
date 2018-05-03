/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import javafx.scene.control.Alert;

/**
 *
 * @author ottovodvarka
 */
public class AlertUtils {
    
    /**
     *  Shows information dailog
     * @param message body of information
     * @param header
     */
    public static void showInfoDialog(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }
    
}

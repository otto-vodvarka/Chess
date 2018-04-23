/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.chess.Board;
import model.chess.Color;
import model.chess.Coordinate;
import model.chess.Game;
import model.chess.Move;
import model.pieces.Piece;
import view.PieceView;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.chess.ChessTimer;
import model.chess.ComputerPlayer;
import model.chess.Duration;
import model.pieces.King;

/**
 * FXML Controller class
 *
 * @author ottovodvarka
 */
public class ChessFXMLController implements Initializable, Observer {

    private Game game;
    
    private ChessTimer whiteTimer;
    private ChessTimer blackTimer;

    @FXML
    private GridPane board;
    @FXML
    private Label playerWhiteLabel;
    @FXML
    private Label playerBlackLabel;
    @FXML
    private Label blackTimerLabel;
    @FXML
    private Label whiteTimerLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setupGame(Game game) {
        this.game = game;
        game.addObserver(this);
        setupPlayers();
        game.startGame();
        startTimers();
        draw();
    }
    
    private void startTimers(){
        whiteTimerLabel.setText(new Duration(Game.DURATION).toString());
        whiteTimer = new ChessTimer(game, game.getPlayerByColor(Color.WHITE), whiteTimerLabel);
        whiteTimer.run();
        
        blackTimerLabel.setText(new Duration(Game.DURATION).toString());
        blackTimer = new ChessTimer(game, game.getPlayerByColor(Color.BLACK), blackTimerLabel);
        blackTimer.run();
    }
    
    private void stopTimers(){
        whiteTimer.stop();
        blackTimer.stop();
    }

    private void setupPlayers() {
        if (game.getPlayer1().getColor() == Color.WHITE) {
            playerWhiteLabel.setText(game.getPlayer1().getName());
            playerBlackLabel.setText(game.getPlayer2().getName());
        } else {
            playerWhiteLabel.setText(game.getPlayer2().getName());
            playerBlackLabel.setText(game.getPlayer1().getName());
        }
    }

    public void draw() {
        board.getChildren().clear();
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                Pane pane = setupPane(i, j);
                Piece piece = game.getBoard().getPieceAt(new Coordinate(i, j));
                if (piece != null) {
                    Label pieceLabel = getPieceLabel(piece);
                    pane.getChildren().add(pieceLabel);
                }
                board.add(pane, i, j);
            }
        }
    }

    private Pane setupPane(int x, int y) {
        Pane pane = new StackPane();
        Coordinate paneCoord = new Coordinate(x, y);
        if ((x + y) % 2 == 0) {
            pane.getStyleClass().add("lightSpot");
        } else {
            pane.getStyleClass().add("darkSpot");
        }
        if (game.isSomePieceSelected()) {
            if (game.getSelectedPieceCoordinates().equals(paneCoord) || isInAvailableMoves(paneCoord)) {
                pane.getStyleClass().add("selected");
            }
        }

        setupClickListener(pane);

        return pane;
    }

    private void setupClickListener(Pane pane) {
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(game.isCheckmate() || game.isStalemate() || game.getPlayerOnMove() instanceof ComputerPlayer) return;
                int x = GridPane.getColumnIndex(pane);
                int y = GridPane.getRowIndex(pane);
                Coordinate spotCoord = new Coordinate(x, y);
                if (containsPiece(pane)) {
                    //spot contains piece
                    if (!game.isSomePieceSelected()) {
                        //no piece is selected
                        if (hasCorrectColor(pane)) {
                            //piece has the right color
                            setCurrentPiece(pane);
                        }
                    } else {
                        //some piece is selected
                        if (containsSelectedPiece(pane)) {
                            //clicked on same piece as selected
                            nullSelectedPiece();
                        } else {
                            //clicked on another piece
                            if (game.isMoveAvailable(new Move(game.getBoard(), game.getSelectedPieceCoordinates(), spotCoord))) {
                                //validace jestli můžu figurku vyhodit
                                //vyhození
                                game.getBoard().moveTo(game.getSelectedPiece(), spotCoord);
                            }
                        }
                    }
                } else {
                    //spot doesnt contain piece
                    if (game.isSomePieceSelected()) {
                        if (game.isMoveAvailable(new Move(game.getBoard(), game.getSelectedPieceCoordinates(), spotCoord))) {
                            //validace tahu
                            game.getBoard().moveTo(game.getSelectedPiece(), new Coordinate(x, y));
                        }
                    }
                }

                draw();
            }
        });
    }

    private PieceView getPieceLabel(Piece piece) {
        PieceView view = new PieceView(piece);
        view.setFont(Font.loadFont(ChessFXMLController.class.getResource("/fonts/CASEFONT.TTF").toExternalForm(), 70));
        if (piece instanceof King && game.getPlayerByColor(piece.getColor()).isInCheck()) {
            view.setTextFill(Paint.valueOf("#ff0000"));
        }
        StackPane.setAlignment(view, Pos.CENTER);

        return view;
    }

    private void setCurrentPiece(Pane pane) {
        game.setSelectedPiece(((PieceView) pane.getChildren().get(0)).getPiece());
    }

    private void nullSelectedPiece() {
        game.unselectPiece();
    }

    private boolean isInAvailableMoves(Coordinate coord) {
        return game.isMoveAvailable(new Move(game.getBoard(), game.getSelectedPieceCoordinates(), coord));
    }

    private boolean hasCorrectColor(Pane pane) {
        return game.getPlayerOnMove().getColor() == ((PieceView) pane.getChildren().get(0)).getPiece().getColor();
    }

    private boolean containsSelectedPiece(Pane pane) {
        return game.isSelectedPiece(((PieceView) pane.getChildren().get(0)).getPiece());
    }

    private boolean containsPiece(Pane pane) {
        return !pane.getChildren().isEmpty();
    }

    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Game over");
        alert.setContentText(message);

        alert.showAndWait();
    }

    @Override
    public void update(Observable o, Object arg) {
        draw();
        if (game.isCheckmate()) {
            showInfoDialog(game.getWaitingPlayer().getName() + " wins!!!");
            stopTimers();
             
       }
        if (game.isStalemate()) {
            showInfoDialog("It is a stalemate!!!");
            stopTimers();
        }
        if(game.isOutOfTime()){
            if(game.getPlayer1().getTime().getSeconds() == 0){
                showInfoDialog(game.getPlayer1() + "wins!!!");
            }else{
                showInfoDialog(game.getPlayer2() + "wins!!!");
            }
        }
    }

}

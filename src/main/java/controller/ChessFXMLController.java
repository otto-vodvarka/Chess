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
import model.chess.Player;
import model.pieces.Piece;
import view.PieceView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author ottovodvarka
 */
public class ChessFXMLController implements Initializable {

    private Game game;
    private Piece currentPiece;
    private Coordinate currentPosition;
    private List<Move> availableMoves;

    //temporary
    private Player currentPlayer;
    private Player waitingPlayer;
    //temporary

    @FXML
    private GridPane board;
    @FXML
    private Label playerWhiteLabel;
    @FXML
    private Label playerBlackLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setupGame(Game game) {
        this.game = game;
        setupPlayers();
        draw();
    }

    private void setupPlayers() {
        if (game.getPlayer1().getColor() == Color.WHITE) {
            playerWhiteLabel.setText(game.getPlayer1().getName());
            playerBlackLabel.setText(game.getPlayer2().getName());
            currentPlayer = game.getPlayer1();
            waitingPlayer = game.getPlayer2();
        } else {
            playerWhiteLabel.setText(game.getPlayer2().getName());
            playerBlackLabel.setText(game.getPlayer1().getName());
            waitingPlayer = game.getPlayer1();
            currentPlayer = game.getPlayer2();
        }
    }

    private void switchPlayers() {
        Player tmp = currentPlayer;
        currentPlayer = waitingPlayer;
        waitingPlayer = tmp;
    }

    private void draw() {
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
        if (currentPiece != null) {
            if (currentPosition.equals(paneCoord) || isInAvailableMoves(paneCoord)) {
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
                int x = GridPane.getColumnIndex(pane);
                int y = GridPane.getRowIndex(pane);
                Coordinate spotCoord = new Coordinate(x, y);
                if (containsPiece(pane)) {
                    //spot contains piece
                    if (currentPiece == null) {
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
                            if (availableMoves.contains(new Move(game.getBoard(), currentPosition, spotCoord))) {
                                //validace jestli můžu figurku vyhodit
                                //vyhození
                                game.getBoard().moveTo(currentPiece, spotCoord);
                                nullSelectedPiece();
                                isInCheck(currentPlayer);
                                isCheckmated(currentPlayer);
                                switchPlayers();
                            }

                        }
                    }

                } else {
                    //spot doesnt contain piece
                    if (currentPiece != null) {
                        if (availableMoves.contains(new Move(game.getBoard(), currentPosition, spotCoord))) {
                            //validace tahu
                            game.getBoard().moveTo(currentPiece, new Coordinate(x, y));
                            nullSelectedPiece();
                            isInCheck(currentPlayer);
                            isCheckmated(currentPlayer);
                            switchPlayers();
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
        StackPane.setAlignment(view, Pos.CENTER);

        return view;
    }

    private void isInCheck(Player player) {
        if (game.getBoard().isInCheck(player.getColor().opposite())) {
            waitingPlayer.setIsInCheck(true);
            System.out.println("Player is in check");
        } else {
            waitingPlayer.setIsInCheck(false);
        }
    }

    private void isCheckmated(Player player) {
        if (waitingPlayer.isInCheck()) {
            if (game.getBoard().isCheckMate(player.getColor().opposite())) {
                System.out.println("CHECKMATE!!!");
            }
        }
    }

    private void setCurrentPiece(Pane pane) {
        currentPiece = ((PieceView) pane.getChildren().get(0)).getPiece();
        currentPosition = game.getBoard().findPiece(currentPiece);
        availableMoves = currentPiece.getAllLegalMoves(game.getBoard());
    }

    private void nullSelectedPiece() {
        currentPiece = null;
        currentPosition = null;
        availableMoves = null;
    }

    private boolean isInAvailableMoves(Coordinate coord) {
        for (Move move : availableMoves) {
            if (move.getEnd().equals(coord)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCorrectColor(Pane pane) {
        return currentPlayer.getColor() == ((PieceView) pane.getChildren().get(0)).getPiece().getColor();
    }

    private boolean containsSelectedPiece(Pane pane) {
        return currentPiece == ((PieceView) pane.getChildren().get(0)).getPiece();
    }

    private boolean containsPiece(Pane pane) {
        return !pane.getChildren().isEmpty();
    }

}

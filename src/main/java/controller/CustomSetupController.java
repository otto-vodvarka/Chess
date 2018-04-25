/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.chess.Board;
import model.chess.Color;
import model.chess.Coordinate;
import model.chess.Spot;
import model.pieces.Bishop;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.Queen;
import model.pieces.Rook;
import view.PieceView;

/**
 * FXML Controller class
 *
 * @author ottovodvarka
 */
public class CustomSetupController implements Initializable {

    private Board board;
    private Piece[][] pieces;

    private Coordinate draggedPieceCoord;

    @FXML
    private GridPane boardGridPane;
    @FXML
    private GridPane piecesGridPane;
    @FXML
    private Button resetButton;
    @FXML
    private Button startButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        board = new Board(new Spot[Board.BOARD_SIZE][Board.BOARD_SIZE]);
        pieces = new Piece[4][8];
        initPieces();
        draw();
    }

    public void draw() {
        drawBoard();
        drawPieces();
    }

    private void drawBoard() {
        boardGridPane.getChildren().clear();
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                Pane pane = setupPane(i, j);
                Piece piece = board.getPieceAt(new Coordinate(i, j));
                if (piece != null) {
                    Label pieceLabel = getPieceLabel(piece);
                    pane.getChildren().add(pieceLabel);
                }
                boardGridPane.add(pane, i, j);
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

        pane.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getGestureSource() != pane
                        && event.getDragboard().hasString()) {

                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        pane.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (board.getPieceAt(new Coordinate(x, y)) == null
                        && isAccordingToRules(pieces[draggedPieceCoord.getX()][draggedPieceCoord.getY()], new Coordinate(x, y))) {

                    pane.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.RED,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                }

                event.consume();
            }
        });

        pane.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                pane.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.TRANSPARENT,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                event.consume();
            }
        });

        pane.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {

                    if (board.getPieceAt(new Coordinate(x, y)) == null
                            && isAccordingToRules(
                                    pieces[draggedPieceCoord.getX()][draggedPieceCoord.getY()], new Coordinate(x, y))) {

                        board.addPiece(pieces[draggedPieceCoord.getX()][draggedPieceCoord.getY()], new Coordinate(x, y));
                        pieces[draggedPieceCoord.getX()][draggedPieceCoord.getY()] = null;

                        success = true;
                    }

                    event.setDropCompleted(success);

                    event.consume();

                    draw();
                }
            }
        });

        return pane;
    }

    private void drawPieces() {
        piecesGridPane.getChildren().clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                //Pane pane = setupPane(i, j);
                Piece piece = pieces[i][j];
                if (piece != null) {
                    PieceView pieceLabel = getPieceLabel(piece);
                    final Coordinate pieceCoord = new Coordinate(i, j);
                    pieceLabel.setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Dragboard db = pieceLabel.startDragAndDrop(TransferMode.MOVE);
                            ClipboardContent content = new ClipboardContent();
                            content.putString("aaa");
                            db.setContent(content);
                            draggedPieceCoord = pieceCoord;
                            event.consume();
                        }
                    });

                    piecesGridPane.add(pieceLabel, i, j);

                }

            }
        }
    }

    private PieceView getPieceLabel(Piece piece) {
        PieceView view = new PieceView(piece);
        view.setFont(Font.loadFont(ChessFXMLController.class.getResource("/fonts/CASEFONT.TTF").toExternalForm(), 70));

        StackPane.setAlignment(view, Pos.CENTER);

        return view;
    }

    private boolean isAccordingToRules(Piece piece, Coordinate coord) {
        if (piece instanceof Pawn) {
            if (coord.getY() == 0 || coord.getY() == 7) {
                return false;
            }
        }
        if (piece instanceof Bishop) {
            Piece otherBishop = board.getBishop(piece.getColor());
            if (otherBishop != null) {
                Coordinate otherBishopCoord = board.findPiece(otherBishop);
                if ((otherBishopCoord.getX() + otherBishopCoord.getY()) % 2 == 0) {
                    if ((coord.getX() + coord.getY()) % 2 == 0) {
                        return false;
                    }
                } else {
                    if ((coord.getX() + coord.getY()) % 2 == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @FXML
    private void onReset(ActionEvent event) {
        board = new Board(new Spot[Board.BOARD_SIZE][Board.BOARD_SIZE]);
        pieces = new Piece[4][8];
        initPieces();
        draw();
    }

    @FXML
    private void onGameStart(ActionEvent event) {
        if(!isBoardValid()) return;
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/gameSetup.fxml"));

            Parent root = loader.load();
            GameSetupController controller = loader.getController();
            controller.setBoard(board);

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
            ((Stage) boardGridPane.getScene().getWindow()).close();

        } catch (IOException ex) {
            Logger.getLogger(GameSetupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean isBoardValid(){
        if(board.isCheckMate(Color.WHITE) || board.isCheckMate(Color.BLACK)){
            showInfoDialoag("One of the players is chackmated");
            return false;
        }
        if(board.findKing(Color.WHITE) == null || board.findKing(Color.BLACK) == null){
            showInfoDialoag("Both king must be in the game");
            return false;
        }
        if(board.getNumberOfPieces() <= 2){
            showInfoDialoag("Kings are not good enough");
        }
        return true;
    }
    
    private void showInfoDialoag(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Incorrect data");
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void initPieces() {
        pieces[0][0] = new Rook(Color.BLACK);
        pieces[0][1] = new Knight(Color.BLACK);
        pieces[0][2] = new Bishop(Color.BLACK);
        pieces[0][3] = new Queen(Color.BLACK);
        pieces[0][4] = new King(Color.BLACK);
        pieces[0][5] = new Bishop(Color.BLACK);
        pieces[0][6] = new Knight(Color.BLACK);
        pieces[0][7] = new Rook(Color.BLACK);

        pieces[1][0] = new Pawn(Color.BLACK);
        pieces[1][1] = new Pawn(Color.BLACK);
        pieces[1][2] = new Pawn(Color.BLACK);
        pieces[1][3] = new Pawn(Color.BLACK);
        pieces[1][4] = new Pawn(Color.BLACK);
        pieces[1][5] = new Pawn(Color.BLACK);
        pieces[1][6] = new Pawn(Color.BLACK);
        pieces[1][7] = new Pawn(Color.BLACK);

        pieces[2][0] = new Rook(Color.WHITE);
        pieces[2][1] = new Knight(Color.WHITE);
        pieces[2][2] = new Bishop(Color.WHITE);
        pieces[2][3] = new Queen(Color.WHITE);
        pieces[2][4] = new King(Color.WHITE);
        pieces[2][5] = new Bishop(Color.WHITE);
        pieces[2][6] = new Knight(Color.WHITE);
        pieces[2][7] = new Rook(Color.WHITE);

        pieces[3][0] = new Pawn(Color.WHITE);
        pieces[3][1] = new Pawn(Color.WHITE);
        pieces[3][2] = new Pawn(Color.WHITE);
        pieces[3][3] = new Pawn(Color.WHITE);
        pieces[3][4] = new Pawn(Color.WHITE);
        pieces[3][5] = new Pawn(Color.WHITE);
        pieces[3][6] = new Pawn(Color.WHITE);
        pieces[3][7] = new Pawn(Color.WHITE);
    }

}

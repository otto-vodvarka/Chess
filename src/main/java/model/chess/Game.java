/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import model.pieces.Piece;

/**
 *
 * @author ottovodvarka
 */
public class Game extends Observable implements Observer {
    
    /**
     *  Seconds, that each player has
     */
    public static int DURATION = 900;

    private final Board board;
    private final Player player1;
    private final Player player2;

    private Player playerOnMove;

    private Piece selectedPiece;
    private Coordinate selectedPieceCoords;
    private List<Move> legalMoves;
    
    private boolean checkmate;
    private boolean stalemate;
    private boolean outOfTime;

    /**
     *
     * @param board
     * @param player1
     * @param player2
     */
    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        if (player1.getColor() == Color.WHITE) {
            playerOnMove = player1;
        } else {
            playerOnMove = player2;
        }
        board.addObserver(this);
    }

    /**
     * Start the game loop
     */
    public void startGame() {
        playerOnMove.play(this);
    }

    /**
     *
     * @param color
     * @return Player with that color
     */
    public Player getPlayerByColor(Color color) {
        if (player1.getColor() == color) {
            return player1;
        }
        return player2;
    }

    /**
     *
     * @param player
     * @return the opposite player
     */
    public Player getOtherPlayer(Player player) {
        if (player == player1) {
            return player2;
        }
        return player1;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setCheckmate(boolean checkmate) {
        this.checkmate = checkmate;
    }

    public boolean isCheckmate() {
        return checkmate;
    }

    public void setStalemate(boolean stalemate) {
        this.stalemate = stalemate;
    }

    public boolean isStalemate() {
        return stalemate;
    }

    public void setOutOfTime(boolean outOfTime) {
        this.outOfTime = outOfTime;
        setChanged();
        notifyObservers();
    }

    public boolean isOutOfTime() {
        return outOfTime;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    /**
     * Put piece to selected one and also its coordinates and retrieve all legal moves of that piece
     * @param selectedPiece
     */
    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
        this.selectedPieceCoords = board.findPiece(selectedPiece);
        this.legalMoves = board.getAllLegalMovesByColor(selectedPiece.getColor());
    }

    /**
     * Null the selected piece
     */
    public void unselectPiece() {
        this.selectedPiece = null;
        this.selectedPieceCoords = null;
        this.legalMoves = null;
    }

    /**
     *
     * @param piece
     * @return true if piece is selcted
     */
    public boolean isSelectedPiece(Piece piece) {
        return piece == selectedPiece;
    }

    public Coordinate getSelectedPieceCoordinates() {
        return selectedPieceCoords;
    }

    /**
     *
     * @return True if some piece is selected
     */
    public boolean isSomePieceSelected() {
        return selectedPiece != null;
    }

    public List<Move> getLegalMoves() {
        return legalMoves;
    }

    /**
     * Is move in list of legal moves
     * @param move
     * @return
     */
    public boolean isMoveAvailable(Move move) {
        return legalMoves.contains(move);
    }

    public Player getPlayerOnMove() {
        return playerOnMove;
    }

    public void setPlayerOnMove(Player playerOnMove) {
        this.playerOnMove = playerOnMove;
    }

    public Player getWaitingPlayer() {
        if (player1 == playerOnMove) {
            return player2;
        }
        return player1;
    }

    /**
     * Makes waiting player a player on move
     */
    public void switchPlayers() {
        if (playerOnMove == player1) {
            playerOnMove = player2;
        } else {
            playerOnMove = player1;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        unselectPiece();
        checkForSpecialSituation();
        switchPlayers();
        setChanged();
        notifyObservers();
        playerOnMove.play(this);
    }

    /**
     * Check for both players check, checkmate and stalemate
     */
    public void checkForSpecialSituation() {
        isInCheck(getWaitingPlayer());
        isInCheck(getPlayerOnMove());
        isCheckmated(getWaitingPlayer());
        isStalemated(getWaitingPlayer());
    }

    private void isInCheck(Player player) {
        if (board.isInCheck(player.getColor())) {
            player.setIsInCheck(true);
        } else {
            player.setIsInCheck(false);
        }
    }

    private void isCheckmated(Player player) {
        if (player.isInCheck()) {
            if (board.isCheckMate(player.getColor())) {
                setCheckmate(true);
            }
        }
    }
    
    private void isStalemated(Player player){
        if(board.isStalemate(player.getColor())){
            setStalemate(true);
        }
    }
}

import java.util.*;
/**
 * A player implementation that makes random moves on the game board.
 * Implements the Player interface by providing a playTurn method that randomly selects a valid empty cell.
 * This player does not employ any specific strategy and relies on random moves.
 * @author Yinon Kedem
 * @see Player
 */
class WhateverPlayer implements Player{
    /**
     * Makes a random move on the game board by selecting a valid empty cell.
     * The player continues making random moves until a valid empty cell is found.
     * @param board The game board on which the player makes a move.
     * @param mark The mark (X or O) associated with the player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        boolean correctInput = false;
        Random randomRowPosition = new Random();
        Random randomColPosition = new Random();
        int row = -1;
        int col = -1;
        while (!correctInput) {
            row = randomRowPosition.nextInt(board.getSize());
            col = randomColPosition.nextInt(board.getSize());
            if (board.getMark(row,col) == Mark.BLANK){
                correctInput = true;
            }
        }
        board.putMark(mark, row, col);
    }
}
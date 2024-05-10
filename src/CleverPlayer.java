/**
 * Represents a clever player in a two-player game.
 * The clever player tries to make the most strategic move by
 * selecting the first available blank position on the board.
 * Implements the Player interface for playing turns.
 * @author Yinon Kedem
 * @see Player
 * @see Board
 * @see Mark
 */
class CleverPlayer implements Player{
    /**
     * Plays a turn by selecting the first available blank position on
     * the board and placing the player's mark.
     * @param board The game board.
     * @param mark The mark associated with the player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        for (int row = 0; row < board.getSize(); row++){
            for (int col = 0; col < board.getSize(); col++){
                if (board.getMark(row, col) == Mark.BLANK){
                    board.putMark(mark, row, col);
                    return;
            }
        }
    }
}
}
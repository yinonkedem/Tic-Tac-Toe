/**
 * Represents a player with a strategic and adaptive playing style in a two-player game.
 * The GeniusPlayer is designed to make intelligent moves based on the current state of the game board.
 * Implements the Player interface for playing turns.
 * @author Yinon Kedem
 * @see Player
 * @see Board
 * @see Mark
 */
class GeniusPlayer implements Player {
    /**
     * Plays a turn by making strategic moves on the game board.
     * The GeniusPlayer adapts its strategy based on the current game state.
     * @param board The game board.
     * @param mark The mark associated with the player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int lastPlace = board.getSize() - 1;

        // If the top-left corner is already marked by the player, play like a CleverPlayer
        if(board.getMark(0, 0) == mark){
            playLikeClever(board, mark);
            return;
        }
        // Iterate through the board to make strategic moves
        for (int row = lastPlace; row >= 0; row--) {
            for (int col = lastPlace; col >= 0; col--) {
                // If the top-left corner is empty and the GeniusPlayer is playing against a random player,
                // strategically mark the top-left corner
                if (board.getMark(0, 0) == Mark.BLANK && isGeniusVsRandom(board, mark)){
                    board.putMark(mark, 0, 0);
                    return;
                }
                // Check if there's a need to block the opponent
                if (needToBlock(board, mark)){
                    return;
                }
                // If the current position is empty, make a strategic move
                if (board.getMark(row, col) == Mark.BLANK) {
                    board.putMark(mark, row, col);
                    return;
                }
            }
        }
    }
    /**
     * Checks if the GeniusPlayer is playing against a random player.
     * Determines this based on the number of non-empty cells on the board.
     * @param board The game board.
     * @param mark The mark associated with the player.
     * @return true if playing against a random player, false otherwise.
     */
    private boolean isGeniusVsRandom(Board board, Mark mark) {
        int cellCounter = 0;
        for (int row = 0; row < board.getSize(); row++){
            for (int col = 0; col < board.getSize(); col++){
                if (board.getMark(row, col) != Mark.BLANK && board.getMark(row, col) != mark){
                    cellCounter++;
                }
            }
        }
        return cellCounter == 1;
    }
    /**
     * Plays a turn similar to a CleverPlayer by selecting the first available blank position on the board.
     * @param board The game board.
     * @param mark The mark associated with the player.
     */
    private void playLikeClever(Board board, Mark mark) {
        for (int row = 0; row < board.getSize(); row++){
            for (int col = 0; col < board.getSize(); col++){
                if (board.getMark(row, col) == Mark.BLANK){
                    board.putMark(mark, row, col);
                    return;
                }
            }
        }
    }
    /**
     * Checks if there's a need to block the opponent from winning.
     * Determines this based on the current state of streaks on the game board.
     * @param board The game board.
     * @param mark The mark associated with the player.
     * @return true if a block move is needed, false otherwise.
     */
    private boolean needToBlock(Board board, Mark mark) {
        int streakCounter = 0;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getMark(row, col) == mark){
                    return false;
                }
                if (streakCounter == 2){
                    board.putMark(mark, row, col);
                    return true;
                }
                Mark curMark = board.getMark(row, col);
                if (curMark != Mark.BLANK && curMark != mark){
                    streakCounter++;
                }
            }
        }
        return false;
    }
}
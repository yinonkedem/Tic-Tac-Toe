/**
 * Represents a player in a two-player game.
 * Defines the method for playing a turn on the game board.
 * Implementing classes must provide an implementation for the playTurn method.
 * @author Yinon Kedem
 * @see Board
 * @see Mark
 */
interface Player{
    /**
     * Plays a turn on the game board for the specified player mark.
     * Implementing classes must define the behavior of playing a turn.
     * @param board The game board on which the turn is played.
     * @param mark The mark associated with the player (X or O).
     */
    void playTurn(Board board, Mark mark);
}
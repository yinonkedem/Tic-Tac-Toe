/**
 * Represents a renderer for displaying the state of a game board.
 * Defines a method for rendering the current state of the game board.
 * Implementing classes must provide an implementation for the renderBoard method.
 * @author Yinon Kedem
 * @see Board
 */
interface Renderer {
    /**
     * Renders the current state of the game board.
     * Implementing classes must define the behavior of rendering the board.
     * @param board The game board to be rendered.
     */
    void renderBoard(Board board);
}
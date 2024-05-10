/**
 * A renderer implementation that does not display any output.
 * Implements the Renderer interface by providing a no-operation (NOP) renderBoard method.
 * This renderer is useful when no visual representation of the game board is required.
 * @author Yinon Kedem
 * @see Renderer
 */
public class VoidRenderer implements Renderer{
    /**
     * Does not perform any rendering operation.
     * This method has no effect, as the purpose of the VoidRenderer is to provide no visual output.
     * @param board The game board to be rendered (unused in this implementation).
     */
    public void renderBoard(Board board){
        return; // No rendering operation; method does nothing.
    }
}
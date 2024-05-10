/**
 * Represents a human player in a two-player game.
 * Implements the Player interface for playing turns.
 * Allows the human player to input their moves through the console.
 * @author Yinon Kedem
 * @see Player
 * @see Board
 * @see Mark
 */
class HumanPlayer implements Player{

    /**
     * Constructs a HumanPlayer instance.
     * // (Optional) Include additional details about the constructor.
     */
    HumanPlayer(){}
    /**
     * Plays a turn by allowing the human player to input their move coordinates.
     * Checks for valid input, coordinates range, and empty cell before making the move.
     * @param board The game board.
     * @param mark The mark associated with the player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int userInput;
        String userMarkSymbol = stringTheMark(mark);
        System.out.println(Constants.playerRequestInputString(userMarkSymbol));
        boolean correctInput = false;
        while (!correctInput) {
            userInput = KeyboardInput.readInt(); // the user coordinates
            int row = userInput / 10;
            int col = userInput % 10;
            boolean inRange = InputInRange(row, col, board);
            if (!inRange) {
                System.out.println(Constants.INVALID_COORDINATE);
            }
            else if (inRange && !emptyCell(row, col, board)){
                System.out.println(Constants.OCCUPIED_COORDINATE);
            }
            else {
                board.putMark(mark, row, col);
                correctInput = true;
            }
        }
    }
    /**
     * Converts the Mark enum to a string representation.
     * @param mark The mark associated with the player.
     * @return The string representation of the mark ("X" or "O").
     */
    private String stringTheMark(Mark mark) {
        if (mark == Mark.X){
            return "X";
        }
        else {
            return "O";
        }
    }
    /**
     * Checks if the given input coordinates are within the valid range of the game board.
     * @param row The row index of the input coordinates.
     * @param col The column index of the input coordinates.
     * @param board The game board.
     * @return true if the coordinates are within the valid range, false otherwise.
     */
    public boolean InputInRange(int row, int col, Board board){
        return col >= 0 && col <= board.getSize() - 1 && row >= 0 && row <= board.getSize() - 1;
    }
    /**
     * Checks if the cell at the given coordinates on the game board is empty.
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param board The game board.
     * @return true if the cell is empty, false otherwise.
     */
    public boolean emptyCell(int row, int col, Board board){
        return board.getMark(row, col) == Mark.BLANK;
    }
}
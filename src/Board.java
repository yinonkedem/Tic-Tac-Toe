/**
 * Represents a game board for a two-player game.
 * The board consists of a grid of marks, and players can place their marks on the board.
 * @author Your Name
 * @see Mark
 */
class Board {
    /**
     * The default size of the board. It is a constant (static final).
     */
    public final static int DEFUALT_BOARD_SIZE = 4;
    private int Board_size = DEFUALT_BOARD_SIZE;
    private Mark[][] board_table;

    /**
     * Default constructor. Initializes the board with the default size.
     */
    Board(){
        this.board_table = new Mark[DEFUALT_BOARD_SIZE][DEFUALT_BOARD_SIZE];
        this.initBoard();
    }
    /**
     * Parameterized constructor. Initializes the board with the specified size.
     * @param size The size of the board.
     */
    Board(int size){
        this.Board_size = size;
        this.board_table = new Mark[size][size];
        this.initBoard();
    }
    /**
     * Initializes the board with blank marks.
     */
    void initBoard(){
        for (int row = 0; row < this.getSize(); row++){
            for (int col = 0; col < this.getSize(); col++){
                this.board_table[row][col] = Mark.BLANK;
            }
        }
    }
    /**
     * Gets the size of the board.
     * @return The size of the board.
     */
    int getSize(){
        return this.Board_size;
    }
    /**
     * Places a mark on the specified position of the board.
     * @param mark The mark to be placed.
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return true if the mark was successfully placed, false otherwise.
     */
    boolean putMark(Mark mark, int row, int col){
        Mark wanted_position_mark;
        if (!validPosition(row, col)){
            return false;
        }
        wanted_position_mark = this.board_table[row][col];
        if (wanted_position_mark != Mark.BLANK){
            return false;
        }
        this.board_table[row][col] = mark;
        return true;
    }
    /**
     * Checks if a given position is a valid position on the board.
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return true if the position is valid, false otherwise.
     */
    private boolean validPosition(int row, int col) {
        return col >= 0 && col <= this.getSize() - 1 && row >= 0 && row <= this.getSize() - 1;
    }
    /**
     * Gets the mark at the specified position on the board.
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return The mark at the specified position.
     */
    Mark getMark(int row, int col){
        return board_table[row][col];
    }
}

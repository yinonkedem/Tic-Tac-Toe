/**
 * Represents a two-player game with a game board, players, and a renderer.
 * The game can be played until there is a winner, a draw, or the game is manually stopped.
 * Implements the game logic, including checking for a win streak and a draw.
 * @author Yinon Kedem
 * @see Board
 * @see Player
 * @see Renderer
 */
class Game{

    private final static int DEFAULT_WIN_STREAK = 3;
    private final static int MINIMUM_WIN_STREAK = 2;
    int requireStreakToWin;
    private int numberOfCellsAreOccupied = 0;
    private final Board board;
    private final Player player1;
    private final Player player2;
    private final Renderer rendererOfTheGame;


    /**
     * Constructor for the game with default board size and win streak.
     * @param playerX The first player (X).
     * @param playerO The second player (O).
     * @param renderer The renderer for displaying the game.
     */
    Game(Player playerX, Player playerO, Renderer renderer){
        this.player1 = playerX;
        this.player2 = playerO;
        this.rendererOfTheGame = renderer;
        this.board = new Board(Board.DEFUALT_BOARD_SIZE);
        this.requireStreakToWin = DEFAULT_WIN_STREAK;
    }
    /**
     * Constructor for the game with custom board size and win streak.
     * @param playerX The first player (X).
     * @param playerO The second player (O).
     * @param size The size of the game board.
     * @param winStreak The required win streak to win the game.
     * @param renderer The renderer for displaying the game.
     */
    Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer){
        this.player1 = playerX;
        this.player2 = playerO;
        this.rendererOfTheGame = renderer;
        this.board = new Board(size);
        this.requireStreakToWin = size;
        if (winStreak < size && winStreak > MINIMUM_WIN_STREAK){
            this.requireStreakToWin = winStreak;
        }
    }
    /**
     * Gets the required win streak to win the game.
     * @return The required win streak.
     */
     int getWinStreak(){
         return this.requireStreakToWin;
     }
    /**
     * Gets the size of the game board.
     * @return The size of the game board.
     */
    int getBoardSize(){
        return this.board.getSize();
    }
    /**
     * Runs the game until there is a winner, a draw, or the game is manually stopped.
     * @return The mark of the winning player or BLANK for a draw.
     */
    Mark run(){
        Mark markToReturn = Mark.BLANK;
        boolean needToPrint = false;
        while (true) {
            if (checkDraw()) {
                break;
            }
            player1.playTurn(board, Mark.X);
            this.rendererOfTheGame.renderBoard(this.board);
            this.numberOfCellsAreOccupied++;
            if (checkWinStreak(Mark.X) != Mark.BLANK) {
                markToReturn = Mark.X;
                break;
            }
            if (checkDraw()) {
                break;
            }
            player2.playTurn(board, Mark.O);
            this.rendererOfTheGame.renderBoard(this.board);
            this.numberOfCellsAreOccupied++;
            if (checkWinStreak(Mark.O) != Mark.BLANK) {
                markToReturn = Mark.O;
                break;
            }
            if (checkDraw()) {
                break;
            }
        }
//        this.rendererOfTheGame.renderBoard(this.board);
        return markToReturn;
    }
    /**
     * Checks if the game is a draw.
     * @return true if the game is a draw, false otherwise.
     */
    private boolean checkDraw() {
        return numberOfCellsAreOccupied ==
                this.getBoardSize()*this.getBoardSize();
    }
    /**
     * Checks for a win streak on the game board for a given mark.
     * @param markToCheck The mark to check for a win streak.
     * @return The winning mark or BLANK if there is no winner.
     */
    private Mark checkWinStreak(Mark markToCheck) {
        for (int row = 0; row < this.getBoardSize(); row++){
            for (int col = 0; col < this.getBoardSize(); col++){
                if (board.getMark(row, col) == markToCheck){
                     if (findMaxStreak(row, col, this.board, markToCheck) == getWinStreak()){
                         return markToCheck;
                    }
                }
            }
        }
        return Mark.BLANK;
    }

    /**
     * Finds the maximum streak for a given position, mark, and direction on the game board.
     * Uses recursion and backtracking for each direction.
     * @param row The row index of the starting position.
     * @param col The column index of the starting position.
     * @param board The game board.
     * @param markToCheck The mark to check for a streak.
     * @return The size of the maximum streak in the given direction.
     */
    private int findMaxStreak(int row, int col, Board board, Mark markToCheck) {
        int curSizeStreak = 1;
        // check Cols streak
        if (validCell(row, col+1) && board.getMark(row, col+1) == markToCheck){
            curSizeStreak += findMaxStreakCols(row, col+1, board, markToCheck);
            if (curSizeStreak == this.getWinStreak()){
                return curSizeStreak;
            }
            curSizeStreak = 1;
        }
        // check Rows streak
        if (validCell(row+1, col) && board.getMark(row+1, col) == markToCheck){
            curSizeStreak += findMaxStreakRows(row+1, col, board, markToCheck);
            if (curSizeStreak == this.getWinStreak()){
                return curSizeStreak;
            }
            curSizeStreak = 1;
        }
        // check right Diagonal streak
        if (validCell(row+1, col+1) && board.getMark(row+1, col+1) == markToCheck){
            curSizeStreak += findMaxStreakRightDiagonal(row+1, col+1, board, markToCheck);
            if (curSizeStreak == this.getWinStreak()){
                return curSizeStreak;
            }
            curSizeStreak = 1;
        }
        // check left Diagonal streak
        if (validCell(row+1, col-1) && board.getMark(row+1, col-1) == markToCheck){
            curSizeStreak += findMaxStreakLeftDiagonal(row+1, col-1, board, markToCheck);
            if (curSizeStreak == this.getWinStreak()){
                return curSizeStreak;
            }
            curSizeStreak = 1;
        }
        return curSizeStreak;
    }

//    private int checkAllDirections(int row, int col, Board board, Mark markToCheck, String );

    // check Cols streak
    private int findMaxStreakCols(int row, int col, Board board, Mark markToCheck) {
        int curSizeStreak = 1;
        if (validCell(row, col+1) && board.getMark(row, col+1) == markToCheck){
            curSizeStreak += findMaxStreakCols(row, col+1, board, markToCheck);
        }
        return curSizeStreak;
    }
    // check Rows streak
    private int findMaxStreakRows(int row, int col, Board board, Mark markToCheck) {
        int curSizeStreak = 1;
        if (validCell(row+1, col) && board.getMark(row+1, col) == markToCheck){
            curSizeStreak += findMaxStreakRows(row+1, col, board, markToCheck);
        }
        return curSizeStreak;
    }
    // check right Diagonal streak
    private int findMaxStreakRightDiagonal(int row, int col, Board board, Mark markToCheck) {
        int curSizeStreak = 1;
        if (validCell(row+1, col+1) && board.getMark(row+1, col+1) == markToCheck){
            curSizeStreak += findMaxStreakRightDiagonal(row+1, col+1, board, markToCheck);
        }
        return curSizeStreak;
    }
    // check left Diagonal streak
    private int findMaxStreakLeftDiagonal(int row, int col, Board board, Mark markToCheck) {
        int curSizeStreak = 1;
        if (validCell(row+1, col-1) && board.getMark(row+1, col-1) == markToCheck){
            curSizeStreak += findMaxStreakLeftDiagonal(row+1, col-1, board, markToCheck);
        }
        return curSizeStreak;
    }
    /**
     * Checks if a given cell position is valid within the bounds of the game board.
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return true if the cell position is valid, false otherwise.
     */
    private boolean validCell(int row, int col) {
        return col >= 0 && col <= this.getBoardSize() - 1 && row >= 0 && row <= this.getBoardSize() - 1;
    }
}
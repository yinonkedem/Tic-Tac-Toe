/**
 * Represents a tournament between two players playing a specified number of rounds.
 * Manages the games, tracks player wins, and displays the final results.
 * The tournament uses a specified renderer and two player implementations.
 * Players and renderer are provided through command-line arguments.
 * @author Yinon Kedem
 * @see Player
 * @see Renderer
 * @see Game
 * @see PlayerFactory
 * @see RendererFactory
 */
class Tournament{
    private final int numberOfRounds;
    private final Renderer renderer;
    private Player[] players = new Player[] {null, null};
    private int[] playerWins = new int[2];
    private int ties = 0;

    /**
     * Constructs a Tournament instance with the specified number of rounds, renderer, and players.
     * @param rounds The number of rounds in the tournament.
     * @param renderer The renderer used to display the game board.
     * @param player1 The first player participating in the tournament.
     * @param player2 The second player participating in the tournament.
     */
    Tournament(int rounds, Renderer renderer, Player player1, Player player2){
        this.numberOfRounds = rounds;
        this.renderer = renderer;
        this.players[0] = player1;
        this.players[1] = player2;
    }
    /**
     * Entry point of the program to run the tournament based on command-line arguments.
     * Validates command-line inputs and initializes the tournament.
     * @param args Command-line arguments containing tournament details.
     */
    public static void main(String[] args) {
        boolean validInputs = validation(args[3], args[4], args[5]);

        if (validInputs) {
            Tournament newTournament = new Tournament(Integer.parseInt(args[0]),
                    RendererFactory.buildRenderer(args[3], Integer.parseInt(args[1])),
                    PlayerFactory.buildPlayer(args[4].toLowerCase()),
                    PlayerFactory.buildPlayer(args[5].toLowerCase()));
            newTournament.playTournament(Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]),
                    args[4], args[5]);
        }
    }
    /**
     * Validates the command-line inputs for renderer type and player names.
     * Displays error messages for invalid inputs.
     * @param rendererType The type of renderer specified in the command-line arguments.
     * @param player1Name The name of the first player specified in the command-line arguments.
     * @param player2Name The name of the second player specified in the command-line arguments.
     * @return true if the inputs are valid, false otherwise.
     */
    private static boolean validation(String rendererType, String player1Name, String player2Name) {
        String[] playerType = {"human", "clever", "whatever", "genius"};
        String[] rendererTypes = {"console", "none"};
        boolean validRenderer = false;
        for (String renderer : rendererTypes){
            if (rendererType.equalsIgnoreCase(renderer)) {
                validRenderer = true;
                break;
            }
        }
        if (!validRenderer){
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return false;
        }
        int validNames = 0;
        if (player1Name.equalsIgnoreCase(player2Name)){
            validNames++;
        }
        for (String player : playerType) {
            if (player.equalsIgnoreCase(player1Name) || player.equalsIgnoreCase(player2Name)) {
                validNames++;
            }
        }
        if (validNames != 2){
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return false;
        }

        return true;
    }
    /**
     * Plays the specified number of rounds in the tournament, tracking wins and ties.
     * Displays the final results after all rounds are played.
     * @param size The size of the game board for each round.
     * @param winStreak The required win streak for a player to win a round.
     * @param playerName1 The name of the first player participating in the tournament.
     * @param playerName2 The name of the second player participating in the tournament.
     */
    void playTournament(int size, int winStreak, String playerName1, String playerName2){
            for (int round = 0; round < this.numberOfRounds; round++){
                Game currentGame = new Game(this.players[round % this.players.length],
                        this.players[(round+1) % this.players.length], size,
                        winStreak, this.renderer);
                Mark curMark = currentGame.run();
                if (curMark == Mark.X){
                    this.playerWins[round % this.players.length] += 1;
                }
                else if (curMark == Mark.O) {
                    this.playerWins[(round+1) % this.players.length] += 1;
                }
                else {
                    this.ties += 1;
                }

            }
        printTheFinalScore(playerName1, playerName2);
    }
    /**
     * Displays the final results of the tournament, including player wins and ties.
     * @param playerName1 The name of the first player participating in the tournament.
     * @param playerName2 The name of the second player participating in the tournament.
     */
    private void printTheFinalScore(String playerName1, String playerName2) {
        System.out.println("######### Results #########\n" +
                "Player 1, " + playerName1 + " won: " + this.playerWins[0] + " rounds\n" +
                "Player 2, " + playerName2 + " won: " + this.playerWins[1] + " rounds\n" +
                "Ties: " + this.ties);
    }

}
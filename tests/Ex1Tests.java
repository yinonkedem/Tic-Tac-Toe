import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class Ex1Tests {
    @Nested
    public class RendererFactoryTest {
        @AfterEach
        public void tearDown() {
            KeyboardInput.resetBuffer();
        }

        @Test
        public void testBuildConsoleRenderer() {
            RendererFactory rendererFactory = new RendererFactory();
            Renderer renderer = rendererFactory.buildRenderer("console", 3);
            assertInstanceOf(ConsoleRenderer.class, renderer);
        }

        @Test
        public void testBuildVoidRenderer() {
            RendererFactory rendererFactory = new RendererFactory();
            Renderer renderer = rendererFactory.buildRenderer("none", 3);
            assertInstanceOf(VoidRenderer.class, renderer);
        }

        @Test
        public void testBuildInvalidRenderer() {
            RendererFactory rendererFactory = new RendererFactory();
            Renderer renderer = rendererFactory.buildRenderer("invalid", 3);
            assertNull(renderer);
        }

        @Test
        public void testBuildVoidRendererWithDifferentSize() {
            RendererFactory rendererFactory = new RendererFactory();
            Renderer renderer = rendererFactory.buildRenderer("none", 5);
            assertInstanceOf(VoidRenderer.class, renderer);
        }

        @Test
        public void testBuildInvalidRendererWithDifferentSize() {
            RendererFactory rendererFactory = new RendererFactory();
            Renderer renderer = rendererFactory.buildRenderer("invalid", 5);
            assertNull(renderer);
        }
    }

    @Nested
    public class PlayerFactoryTest {
        @AfterEach
        public void tearDown() {
            KeyboardInput.resetBuffer();
        }

        @Test
        public void testBuildHumanPlayer() {
            PlayerFactory playerFactory = new PlayerFactory();
            Player player = playerFactory.buildPlayer("human");
            assertInstanceOf(HumanPlayer.class, player);
        }

        @Test
        public void testBuildCleverPlayer() {
            PlayerFactory playerFactory = new PlayerFactory();
            Player player = playerFactory.buildPlayer("clever");
            assertInstanceOf(CleverPlayer.class, player);
        }

        @Test
        public void testBuildWhateverPlayer() {
            PlayerFactory playerFactory = new PlayerFactory();
            Player player = playerFactory.buildPlayer("whatever");
            assertInstanceOf(WhateverPlayer.class, player);
        }

        @Test
        public void testBuildGeniusPlayer() {
            PlayerFactory playerFactory = new PlayerFactory();
            Player player = playerFactory.buildPlayer("genius");
            assertInstanceOf(GeniusPlayer.class, player);
        }

        @Test
        public void testBuildInvalidPlayer() {
            PlayerFactory playerFactory = new PlayerFactory();
            Player player = playerFactory.buildPlayer("invalid");
            assertNull(player);
        }
    }

    @Nested
    public class BoardTest {
        @AfterEach
        public void tearDown() {
            KeyboardInput.resetBuffer();
        }

        @Test
        public void testDefaultConstructor() {
            Board board = new Board();
            assertEquals(4, board.getSize());
        }

        @Test
        public void testCustomConstructor() {
            int customSize = 5;
            Board board = new Board(customSize);
            assertEquals(customSize, board.getSize());
        }

        @Test
        public void testPutMark() {
            Board board = new Board();
            assertTrue(board.putMark(Mark.X, 0, 0));
            assertEquals(Mark.X, board.getMark(0, 0));
        }

        @Test
        public void testPutMarkInvalidRow() {
            Board board = new Board();
            assertFalse(board.putMark(Mark.X, -1, 0));
            assertFalse(board.putMark(Mark.X, 5, 0));
        }

        @Test
        public void testPutMarkInvalidColumn() {
            Board board = new Board();
            assertFalse(board.putMark(Mark.X, 0, -1));
            assertFalse(board.putMark(Mark.X, 0, 5));
        }

        @Test
        public void testPutMarkOccupiedCell() {
            Board board = new Board();
            assertTrue(board.putMark(Mark.X, 0, 0));
            assertFalse(board.putMark(Mark.O, 0, 0));
            assertEquals(Mark.X, board.getMark(0, 0));
        }
    }

    @Nested
    public class PlayerTest {
        @AfterEach
        public void tearDown() {
            KeyboardInput.resetBuffer();
        }

        @Test
        public void testCleverPlayerBehavior() {
            verifyPlacement(new CleverPlayer(), Mark.X);
            verifyPlacement(new CleverPlayer(), Mark.O);
        }

        @Nested
        public class TestHumanPlayerBehavior {
            @AfterEach
            public void tearDown() {
                KeyboardInput.resetBuffer();
            }

            private static final String INVALID_COORDINATE_ERR_MSG = "Invalid mark position, please choose a different" +
                    " position.\n" +
                    "Invalid coordinates, type again: ";

            private static final String OCCUPIED_COORDINATE_ERR_MSG = "Mark position is already occupied.\n" +
                    "Invalid coordinates, type again: ";

            @Test
            public void testHumanPlayerBehavior() {
                // Mocking user input for testing, first input "1", second "22"
                KeyboardInput.setBuffer("1\n22");
                verifyPlacement(new HumanPlayer(), Mark.X);
                verifyPlacement(new HumanPlayer(), Mark.O);
                // Reset System.in to its original state
            }

            @Test
            public void testInstructions() {
                ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outputStreamCaptor));
                KeyboardInput.setBuffer("01\n00");

                HumanPlayer humanPlayer = new HumanPlayer();
                Board board = new Board();

                humanPlayer.playTurn(board, Mark.X);
                assertEquals("Player X, type coordinates: \n", outputStreamCaptor.toString().replace("\r", ""));
                humanPlayer.playTurn(board, Mark.O);
                assertEquals("Player X, type coordinates: \nPlayer O, type coordinates: \n", outputStreamCaptor.toString().replace("\r", ""));
            }

            @Test
            public void testInvalidPosition() {
                KeyboardInput.setBuffer("55\n00");

                ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outputStreamCaptor));

                HumanPlayer humanPlayer = new HumanPlayer();
                Board board = new Board();

                humanPlayer.playTurn(board, Mark.X);

                assertEquals("Player X, type coordinates: \n" + INVALID_COORDINATE_ERR_MSG + "\n", outputStreamCaptor.toString().replace("\r", ""));
                assertEquals(Mark.X, board.getMark(0, 0));
            }

            @Test
            public void testOccupiedPosition() {
                KeyboardInput.setBuffer("00\n00\n01");
                ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outputStreamCaptor));

                HumanPlayer humanPlayer = new HumanPlayer();
                Board board = new Board();

                humanPlayer.playTurn(board, Mark.X);
                humanPlayer.playTurn(board, Mark.X);

                assertEquals("Player X, type coordinates: \n" + "Player X, type coordinates: \n" + OCCUPIED_COORDINATE_ERR_MSG + "\n", outputStreamCaptor.toString().replace("\r", ""));
                assertEquals(Mark.X, board.getMark(0, 0));
                assertEquals(Mark.X, board.getMark(0, 1));
            }
        }

        @Test
        public void testGeniusPlayerBehavior() {
            verifyPlacement(new GeniusPlayer(), Mark.X);
            verifyPlacement(new GeniusPlayer(), Mark.O);
        }

        @Test
        public void testWhateverPlayerBehavior() {
            verifyPlacement(new WhateverPlayer(), Mark.X);
            verifyPlacement(new WhateverPlayer(), Mark.O);
        }

        // Helper method to test the Player interface behavior for both 'X' and 'O'
        private void verifyPlacement(Player player, Mark mark) {
            // Create a board
            Board board = new Board();

            // Play a turn
            player.playTurn(board, mark);

            // Check the entire board for the player's mark
            int boardSize = board.getSize();
            boolean markFound = false;

            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    if (board.getMark(row, col) == mark) {
                        markFound = true;
                        break;
                    }
                }
                if (markFound) {
                    break;
                }
            }

            assertTrue(markFound, "Player did not place the mark on the board for mark: " + mark);
        }
    }

    @Nested
    public class GameTest {
        @AfterEach
        public void tearDown() {
            KeyboardInput.resetBuffer();
        }

        @Test
        public void testConstructors() {
            Player playerX = new WhateverPlayer();
            Player playerO = new CleverPlayer();
            Renderer renderer = new VoidRenderer();

            Game defaultSizesConstructor = new Game(playerX, playerO, renderer);
            Game withSizesConstructor = new Game(playerX, playerO, 5, 3, renderer);
            Game withInvalidWinStreakGreaterThanSize = new Game(playerX, playerO, 3, 5, renderer);
            Game withInvalidWinStreakBelowTwo = new Game(playerX, playerO, 7, 1, renderer);

            assertEquals(4, defaultSizesConstructor.getBoardSize());
            assertEquals(3, defaultSizesConstructor.getWinStreak());

            assertEquals(5, withSizesConstructor.getBoardSize());
            assertEquals(3, withSizesConstructor.getWinStreak());

            assertEquals(3, withInvalidWinStreakGreaterThanSize.getBoardSize());
            assertEquals(3, withInvalidWinStreakGreaterThanSize.getWinStreak());

            assertEquals(7, withInvalidWinStreakBelowTwo.getBoardSize());
            assertEquals(7, withInvalidWinStreakBelowTwo.getWinStreak());
        }

        @Test
        public void testRunGameWithXWin() {
            Player playerX = new GeniusPlayer();
            Player playerO = new CleverPlayer();
            Renderer renderer = new VoidRenderer();
            Game game = new Game(playerX, playerO, renderer);

            Mark winner = game.run();

            assertEquals(Mark.X, winner);
        }

        @Test
        public void testRunGameWithOWin() {
            Player playerX = new CleverPlayer();
            Player playerO = new GeniusPlayer();
            Renderer renderer = new VoidRenderer();
            Game game = new Game(playerX, playerO, renderer);

            Mark winner = game.run();

            assertEquals(Mark.O, winner);
        }

        @Test
        public void testRunGameWithTie() throws Exception {
            Player playerX = new HumanPlayer();
            Player playerO = new HumanPlayer();
            Renderer renderer = new VoidRenderer();
            Game game = new Game(playerX, playerO, 3, 3, renderer);

            KeyboardInput.setBuffer("00\n01\n02\n10\n11\n20\n12\n22\n21");

            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            Mark winner = game.run();

            assertEquals(Mark.BLANK, winner);

            assertEquals("Player X, type coordinates: \n" +
                    "Player O, type coordinates: \n" +
                    "Player X, type coordinates: \n" +
                    "Player O, type coordinates: \n" +
                    "Player X, type coordinates: \n" +
                    "Player O, type coordinates: \n" +
                    "Player X, type coordinates: \n" +
                    "Player O, type coordinates: \n" +
                    "Player X, type coordinates: \n", outputStreamCaptor.toString().replace("\r", ""));

            Field privateField = Game.class.getDeclaredField("board");
            privateField.setAccessible(true);
            Board board = (Board) privateField.get(game);
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    assertNotEquals(Mark.BLANK, board.getMark(row, col));
                }
            }
        }

        /**
         * This test verify that you first check win streak and after that for tie
         */
        @Test
        public void testRunGameWithWinInLastStep() throws Exception {
            Player playerX = new HumanPlayer();
            Player playerO = new HumanPlayer();
            Renderer renderer = new VoidRenderer();
            Game game = new Game(playerX, playerO, 3, 3, renderer);

            KeyboardInput.setBuffer("00\n01\n02\n10\n11\n12\n21\n20\n22");

            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            Mark winner = game.run();

            assertEquals(Mark.X, winner);

            // Make sure that all the turns were played and the board is full
            assertEquals("Player X, type coordinates: \n" +
                    "Player O, type coordinates: \n" +
                    "Player X, type coordinates: \n" +
                    "Player O, type coordinates: \n" +
                    "Player X, type coordinates: \n" +
                    "Player O, type coordinates: \n" +
                    "Player X, type coordinates: \n" +
                    "Player O, type coordinates: \n" +
                    "Player X, type coordinates: \n", outputStreamCaptor.toString().replace("\r", ""));

            Field privateField = Game.class.getDeclaredField("board");
            privateField.setAccessible(true);
            Board board = (Board) privateField.get(game);
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    assertNotEquals(Mark.BLANK, board.getMark(row, col));
                }
            }
        }

        @Test
        public void testRunGameWithWinRow() {
            Player playerX = new HumanPlayer();
            Player playerO = new HumanPlayer();
            Renderer renderer = new VoidRenderer();
            Game game = new Game(playerX, playerO, 3, 3, renderer);

            KeyboardInput.setBuffer("00\n10\n01\n11\n02");

            Mark winner = game.run();

            assertEquals(Mark.X, winner);
        }

        @Test
        public void testRunGameWithWinCol() {
            Player playerX = new HumanPlayer();
            Player playerO = new HumanPlayer();
            Renderer renderer = new VoidRenderer();
            Game game = new Game(playerX, playerO, 3, 3, renderer);

            KeyboardInput.setBuffer("00\n11\n01\n12\n02\n");

            Mark winner = game.run();

            assertEquals(Mark.X, winner);
        }

        @Test
        public void testRunGameWithWinDiagonal() {
            Player playerX = new HumanPlayer();
            Player playerO = new HumanPlayer();
            Renderer renderer = new VoidRenderer();
            Game game = new Game(playerX, playerO, 3, 3, renderer);

            KeyboardInput.setBuffer("00\n10\n11\n12\n22");

            Mark winner = game.run();

            assertEquals(Mark.X, winner);
        }

        @Test
        public void testRunGameWithWinInverseDiagonal() {
            Player playerX = new HumanPlayer();
            Player playerO = new HumanPlayer();
            Renderer renderer = new VoidRenderer();
            Game game = new Game(playerX, playerO, 3, 3, renderer);

            KeyboardInput.setBuffer("02\n10\n11\n12\n20");

            Mark winner = game.run();

            assertEquals(Mark.X, winner);
        }
    }

    @Nested
    public class SuccessRateTest {
        @AfterEach
        public void tearDown() {
            KeyboardInput.resetBuffer();
        }

        @Test
        public void testAll() {
            testBetweenTwoPlayers("genius", "clever");
            testBetweenTwoPlayers("genius", "whatever");
            testBetweenTwoPlayers("clever", "whatever");
        }

        private void testBetweenTwoPlayers(String player1, String player2) {
            for(int i = 4; i < 10; i++) {
                for(int j = 3; j <= i; j++) {
                    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
                    System.setOut(new PrintStream(outputStreamCaptor));
                    Tournament.main(new String[] {"10000", String.valueOf(i), String.valueOf(j), "none", player1, player2});
                    String winMessage = outputStreamCaptor.toString();
                    winMessage = winMessage.replaceAll("[^-?0-9]+", " ");

                    int player1Wins = Integer.parseInt(Arrays.asList(winMessage.trim().split(" ")).get(1));
                    assertTrue((double) player1Wins / 10000 >= 0.55);
                }
            }
        }
    }

    @Nested
    class TournamentTest {
        @AfterEach
        public void tearDown() {
            KeyboardInput.resetBuffer();
        }

        private final static String UNKNOWN_RENDERER_NAME = "Choose a renderer, and start again. \nPlease choose" +
                " one of the following [console, none]";

        private final static String UNKNOWN_PLAYER_NAME = "Choose a player, and start again.\nThe players: " +
                "[human, clever, whatever, genius]";

        @Test
        public void testInvalidRendererErrorMsg() {
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            Tournament.main(new String[] {"10000", "4", "4", "renderer", "human", "human"});

            assertEquals(UNKNOWN_RENDERER_NAME + "\n", outputStreamCaptor.toString().replace("\r", ""));
        }

        @Test
        public void testInvalidRendererPlayer1() {
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            Tournament.main(new String[] {"10000", "4", "4", "none", "humann", "human"});

            assertEquals(UNKNOWN_PLAYER_NAME + "\n", outputStreamCaptor.toString().replace("\r", ""));
        }

        @Test
        public void testInvalidRendererPlayer2() {
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            Tournament.main(new String[] {"10000", "4", "4", "none", "human", "humann"});

            assertEquals(UNKNOWN_PLAYER_NAME + "\n", outputStreamCaptor.toString().replace("\r", ""));
        }

        @Test
        public void testInvalidRendererAndPlayersErrorMsg() {
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            Tournament.main(new String[] {"10000", "4", "4", "renderer", "humaan", "humaan"});

            assertEquals(UNKNOWN_RENDERER_NAME + "\n", outputStreamCaptor.toString().replace("\r", ""));
        }

        @Test
        public void testWinMessage() {
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            Tournament.main(new String[] {"10000", "6", "3", "none", "genius", "clever"});

            assertEquals("######### Results #########\n" +
                    "Player 1, genius won: 10000 rounds\n" +
                    "Player 2, clever won: 0 rounds\n" +
                    "Ties: 0\n", outputStreamCaptor.toString().replace("\r", ""));
        }

        @Test
        public void testNoEmptyBoardPrintBeforeFirstTurn() {
            KeyboardInput.setBuffer("00\n11\n01\n22\n02");
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            Tournament.main(new String[] {"1", "3", "3", "console", "human", "human"});


            assertTrue(outputStreamCaptor.toString().replace("\r", "").startsWith("Player X, type coordinates: \n"));
        }

        @Test
        public void testPlayTournamentWithGeniusDefeatClever() throws Exception {
            for(int i = 4; i < 10; i++) {
                for (int j = 3; j <= i; j++) {
                    Player playerX = new GeniusPlayer();
                    Player playerO = new CleverPlayer();
                    Renderer renderer = new VoidRenderer();
                    Tournament tournament = new Tournament(10000, renderer, playerX, playerO);

                    tournament.playTournament(i, j, "genius", "clever");

                    Field privateField = Tournament.class.getDeclaredField("playersWins");
                    privateField.setAccessible(true);
                    int[] totalPoints = (int[]) privateField.get(tournament);

                    Field privateFieldTies = Tournament.class.getDeclaredField("ties");
                    privateFieldTies.setAccessible(true);
                    int ties = (int) privateFieldTies.get(tournament);

                    assertEquals(10000, totalPoints[0]);
                    assertEquals(0, totalPoints[1]);
                    assertEquals(0, ties);
                }
            }
        }
    }
}
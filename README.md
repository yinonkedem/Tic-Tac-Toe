# Tic-Tac-Toe Game in Java

## Description
This project is a Java implementation of the classic Tic-Tac-Toe game, playable via command line. It features a two-player mode **with an advanced backtracking mechanism for win detection**, offering a robust and fair gameplay experience.

## Features
- **Two-Player Mode:** Engage in a competitive game with another player.
- **Command Line Interface:** Play and interact directly from your terminal.
- **Backtracking Win Detection:** Employs a recursive technique to verify winning conditions dynamically across the board.

## Installation
Ensure Java is installed on your system before proceeding. Follow these steps to set up the game:

1. **Clone the repository:**
git clone https://github.com/yinonkedem/Tic-Tac-Toe.git


2. **Navigate to the project directory:**
cd Tic-Tac-Toe

## Usage
To start the game, compile the Java files and run the main class using the following command structure:

java Tournament [round count] [size] [win_streak] [render target: console/none] [first player: human/whatever/clever/genius] [second player: human/whatever/clever/genius]


### Command-Line Arguments
- **Round Count:** The number of rounds to play.
- **Size:** The dimension of the game board, which can be up to 9x9.
- **Win Streak:** The number of consecutive marks needed to win.
- **Render Target:** Choose 'console' for command-line output or 'none' for no visual output.
- **First Player & Second Player:** Specify the type of each player ('human', 'whatever', 'clever', 'genius').

**Example Command:**
To start a game with 5 rounds on a 5x5 board, requiring 4 marks in a row to win, output to console, with both players as human:
**java Tournament 5 5 4 console human human**

## Contact
For any inquiries or feedback, please contact:
- **Email:** [yinonked@gmail.com](mailto:yinonked@gmail.com)

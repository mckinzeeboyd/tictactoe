import java.util.Scanner;

public class TTTtest {
    // Name-constants to represent the seeds and cell contents
    public static final int EMPTY = 0; 
    public static final int X = 1;
    public static final int O = 2;

    // Name-constants to represent the various states of the game
    public static final int PLAYING = 0;
    public static final int DRAW = 1;
    public static final int X_WON = 2;
    public static final int O_WON = 3;

    // The game board and the game status
    public static final int ROWS = 3, COLS = 3; // number of rows and columns
    public static int[][] board = new int[ROWS][COLS]; // game board
    //  containing (EMPTY, X, O)
    public static int currentState;  // the current state of the game
    // (PLAYING, DRAW, X_WON, O_WON)
    public static int currentPlayer; // the current player (X or O)
    public static int currntRow, currentCol; // current seed's row and column

    public static Scanner in = new Scanner(System.in); // the input Scanner

    //print empty gameboard
    static char PLACEHOLDER = '.';
    static Scanner keyboard = new Scanner(System.in);

    public static void printWelcome()
    {
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("To play, enter a number for which box to play in.");
        System.out.println("1 2 3");
        System.out.println("4 5 6");
        System.out.println("7 8 9");
        System.out.println("You'll need a buddy to play with.  Ready to begin?  Here we go.");
    }

    public static void drawBoard(char[][] board)
    {
        System.out.println();
        for (int row = 0; row < 3; row++)
        {
            System.out.println(" " + board[row][0] + " | " + board[row][1] + " | " + board[row][2]);
        }
        System.out.println();
    }

    public static char[][] createEmptyBoard()
    {
        char[][] gameBoard = new char[3][3];
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                gameBoard[row][col] = PLACEHOLDER;
            }
        }
        return gameBoard;
    }

    public static boolean wantsToPlayAgain()
    {
        System.out.print("Would you like to play again?  ");
        keyboard.nextLine(); // skip leftover newline from the prior nextInt().
        String answer = keyboard.nextLine(); // get real input.
        return (answer.equals("y"));

    }

    /**the program starts here */
    public static void main(String[] args) {
        // Initialize the game-board and current status
        printWelcome();
        do{
            char [][] gameBoard = createEmptyBoard();
            drawBoard(gameBoard);

            initGame();

            int xWins = 0;
            int oWins = 0;
            int draws = 0;
            // Play the game once

            do {
                playerMove(currentPlayer); // update currentRow and currentCol
                updateGame(currentPlayer, currntRow, currentCol); // update currentState
                printBoard();
                // Print message if game-over
                if (currentState == X_WON) {
                    System.out.println("'X' won!");
                    
                } else if (currentState == O_WON) {
                    System.out.println("'O' won!");
                    
                } else if (currentState == DRAW) {
                    System.out.println("It's a Draw! ");
                    
                }
                currentPlayer = (currentPlayer == X) ? O : X;
                // Switch player
                
            } while (currentState == PLAYING); 
            
            // repeat if not game-over
            if (currentState == X_WON) {
                    
                    xWins++;
                } else if (currentState == O_WON) {
                    
                    oWins++;
                } else if (currentState == DRAW) {
                    
                    draws++;
                }
            System.out.println("Score: X=" + xWins + ", O=" + oWins + ", draws=" + draws);
        } while (wantsToPlayAgain());

        System.out.println("Thanks for playing!");
    }

    /** Initialize the game-board contents and the current states */
    public static void initGame() {
        int xWins = 0;
        int oWins = 0;
        int draws = 0;

        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = EMPTY;  // all cells empty
            }
        }
        currentState = PLAYING; // ready to play
        currentPlayer = X;  // cross plays first
    }

    /** Player with the "theSeed" makes one move, with input validation.
    Update global variables "currentRow" and "currentCol". */
    public static void playerMove(int theSeed) {
        boolean validInput = false;  // for input validation
        do {
            if (theSeed == X) {
                System.out.print("Player 'X', enter your move (1-9): ");
            } else {
                System.out.print("Player 'O', enter your move (1-9): ");
            }

            int position=keyboard.nextInt();
            int row = (position - 1) / 3;  // where to place token
            int col = (position - 1) % 3;

            if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == EMPTY) {
                currntRow = row;
                currentCol = col;
                board[currntRow][currentCol] = theSeed;  // update game-board content
                validInput = true;  // input okay, exit loop
            } else {
                System.out.println("This move at" + position + " is not valid. Try again...");
            }
        } while (!validInput);  // repeat until input is valid
    }

    /** Update the "currentState" after the player with "theSeed" has placed on
    (currentRow, currentCol). */
    public static void updateGame(int theSeed, int currentRow, int currentCol) {
        if (hasWon(theSeed, currentRow, currentCol)) {  // check if winning move
            currentState = (theSeed == X) ? X_WON : O_WON;
        } else if (isDraw()) {  // check for draw
            currentState = DRAW;
        }
        // Otherwise, no change to currentState (still PLAYING).
    }

    /** Return true if it is a draw (no more empty cells) */
    // TODO: Shall declare draw if no player can "possibly" win
    public static boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == EMPTY) {
                    return false;  // an empty cell found, not draw, exit
                }
            }
        }
        return true;  // no empty cell, it's a draw
    }

    /** Return true if the player with "theSeed" has won after placing (currentRow, currentCol) */
    public static boolean hasWon(int theSeed, int currentRow, int currentCol) {
        return (board[currentRow][0] == theSeed         // 3-in-the-row
            && board[currentRow][1] == theSeed
            && board[currentRow][2] == theSeed
            || board[0][currentCol] == theSeed      // 3-in-the-column
            && board[1][currentCol] == theSeed
            && board[2][currentCol] == theSeed
            || currentRow == currentCol            // 3-in-the-diagonal
            && board[0][0] == theSeed
            && board[1][1] == theSeed
            && board[2][2] == theSeed
            || currentRow + currentCol == 2  // 3-in-the-opposite-diagonal
            && board[0][2] == theSeed
            && board[1][1] == theSeed
            && board[2][0] == theSeed);
    }

    /** Print the game board */
    public static void printBoard() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                printCell(board[row][col]); // print each of the cells
                if (col != COLS - 1) {
                    System.out.print("|");   // print vertical partition
                }
            }
            System.out.println();
            if (row != ROWS - 1) {
                System.out.println("-----------"); // print horizontal partition
            }
        }
        System.out.println();
    }

    /** Print a cell with the specified "content" */
    public static void printCell(int content) {
        switch (content) {
            case EMPTY:  System.out.print("   "); break;
            case O: System.out.print(" O "); break;
            case X:  System.out.print(" X "); break;
        }
    }
}
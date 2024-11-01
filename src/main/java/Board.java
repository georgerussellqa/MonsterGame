import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Board {
    Random randGen = new Random();
    private int gameState = 0;

    private final Integer boardSize;
    private final Square[][] gameBoard;

    private int playerRow;
    private int playerCol;
    private int treasureRow;
    private int treasureCol;

    private final ArrayList<int[]> playerPath = new ArrayList<>();

    public Board() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter board size (size x size grid) >>> ");
        int boardSize = input.nextInt();

        System.out.print("Enter number of monsters to spawn >>> ");
        int numMonsters = input.nextInt();

        this.boardSize = boardSize;
        this.gameBoard = new Square[boardSize][boardSize];

        generateMonsters(numMonsters);
        generatePlayer();
        generateTreasure();

        playerPath.add(new int[] {playerRow, playerCol});
    }

    public void startGame() {
        Scanner input = new Scanner(System.in);
        String msg;
        System.out.println(this);

        do {
            System.out.print("Make a move (U, R, D, L) >>> ");
            String in = input.next();

            msg = this.movePlayer(in);
            System.out.println(this);
            System.out.println(msg);
        } while (this.getGameState() != 1 && this.getGameState() != -1);

        displayFinalGrid();

    }

    private void displayFinalGrid() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int finalI = i;
                int finalJ = j;

                // Display death symbol on monster that killed you
                if (playerRow == i && playerCol == j && this.gameState == -1) {
                    sb.append("☠\uFE0F");

                // Displays diamond if you find the treasure
                } else if (playerRow == i && playerCol == j && this.gameState == 1) {
                    sb.append("\uD83D\uDC8E");

                // Displays monster symbols
                } else if (gameBoard[i][j] instanceof Monster) {
                    sb.append("\uD83D\uDC79");

                // Displays treasure symbol
                } else if (gameBoard[i][j] instanceof Treasure) {
                    sb.append("\uD83D\uDC8E");

                // Display purple square on player's starting point
                } else if (playerPath.get(0)[0] == i && playerPath.get(0)[1] == j) {
                    sb.append("\uD83D\uDFEA");

                // Display path if you die in red
                } else if (playerPath.stream().anyMatch(o -> (o[0] == finalI && o[1] == finalJ)) && this.gameState == -1) {
                    sb.append("\uD83D\uDFE5");

                // Display path if you win in green
                } else if (playerPath.stream().anyMatch(o -> (o[0] == finalI && o[1] == finalJ))) {
                    sb.append("\uD83D\uDFE9");

                // Fill in board with white squares
                } else {
                    sb.append("⬜");
                }
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    public String movePlayer(String direction) {
        int code = makeMove(direction);

        if (code == 1) {
            return "You found the treasure!";
        } else if (code == -1) {
            return ((Monster)gameBoard[playerRow][playerCol]).getGreeting();
        }

        return "You are " + getDistanceFromTreasure() + " steps away from the treasure...";
    }

    private int makeMove(String direction) {
        int newRow = playerRow;
        int newCol = playerCol;

        if (direction.equalsIgnoreCase("u")) {
            newRow -= 1;
        } else if (direction.equalsIgnoreCase("r")) {
            newCol += 1;
        } else if (direction.equalsIgnoreCase("d")) {
            newRow += 1;
        } else if (direction.equalsIgnoreCase("l")) {
            newCol -= 1;
        } else {
            return 0;
        }

        if (newCol > boardSize-1 || newCol < 0 || newRow > boardSize-1 || newRow < 0) {
            return 0;
        }

        // If player lands on treasure, set game state to 1 (win)
        if (gameBoard[newRow][newCol] instanceof Treasure) {
            gameState = 1;
            playerRow = newRow;
            playerCol = newCol;
            return 1;

        // If player lands on a monster, set game state to -1 (loss)
        } else if (gameBoard[newRow][newCol] instanceof Monster) {
            gameState = -1;
            playerRow = newRow;
            playerCol = newCol;
            return -1;
        }

        // Move player instance
        gameBoard[newRow][newCol] = gameBoard[playerRow][playerCol];
        gameBoard[playerRow][playerCol] = null;


        // Add new player position, and add to their path, then return game state 0 (no change)
        playerRow = newRow;
        playerCol = newCol;
        playerPath.add(new int[] {newRow, newCol});

        return 0;

    }

    public int getDistanceFromTreasure() {
        return Math.abs(playerRow - treasureRow) + Math.abs(playerCol-treasureCol);
    }

    private void generateMonsters(Integer numMonsters) {
        for (int i = 0; i < numMonsters; i++) {
            do {
                int row = randGen.nextInt(boardSize);
                int col = randGen.nextInt(boardSize);

                if (isSquareEmpty(row, col)) {
                    gameBoard[row][col] = new Monster();
                    break;
                }
            } while (true);

        }
    }

    private void generatePlayer() {
        do {
            int row = randGen.nextInt(boardSize);
            int col = randGen.nextInt(boardSize);

            if (isSquareEmpty(row, col)) {
                gameBoard[row][col] = new Player();
                playerRow = row;
                playerCol = col;
                break;
            }
        } while (true);
    }

    private void generateTreasure() {
        do {
            int row = randGen.nextInt(boardSize);
            int col = randGen.nextInt(boardSize);

            if (isSquareEmpty(row, col)) {
                gameBoard[row][col] = new Treasure();
                treasureRow = row;
                treasureCol = col;
                break;
            }
        } while (true);
    }

    private boolean isSquareEmpty(int row, int col) {
        return !(gameBoard[row][col] instanceof Monster
                || gameBoard[row][col] instanceof Player
                || gameBoard[row][col] instanceof Treasure);
    }

    public int getGameState() {
        return gameState;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                sb.append(gameBoard[i][j] != null ? gameBoard[i][j]: "⬜");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

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

    private final ArrayList<int[]> path = new ArrayList<>();

    public Board(Integer size, Integer numMonsters) {
        this.boardSize = size;
        this.gameBoard = new Square[size][size];
        generateMonsters(numMonsters);
        generatePlayer();
        generateTreasure();
        path.add(new int[] {playerRow, playerCol});
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

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int finalI = i;
                int finalJ = j;
                System.out.print(
                        playerRow == i && playerCol == j && this.gameState == -1 ? "☠\uFE0F" :
                                playerRow == i && playerCol == j && this.gameState == 1 ? "\uD83D\uDC8E" :
                                        gameBoard[i][j] instanceof Monster ? "\uD83D\uDC79" :
                                                gameBoard[i][j] instanceof Treasure ? "\uD83D\uDC8E" :
                                                    path.get(0)[0] == j && path.get(0)[1] == i ? "\uD83D\uDFEA" :
                                                        path.stream().anyMatch(o -> (o[0] == finalI && o[1] == finalJ)) && this.gameState == -1 ? "\uD83D\uDFE5" :
                                                            path.stream().anyMatch(o -> (o[0] == finalI && o[1] == finalJ)) ? "\uD83D\uDFE9" : "⬜");
            }
            System.out.print("\n");
        }

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

        if (gameBoard[newRow][newCol] instanceof Treasure) {
            gameState = 1;
            playerRow = newRow;
            playerCol = newCol;
            return 1;
        } else if (gameBoard[newRow][newCol] instanceof Monster) {
            gameState = -1;
            playerRow = newRow;
            playerCol = newCol;
            return -1;
        }

        gameBoard[newRow][newCol] = gameBoard[playerRow][playerCol];
        gameBoard[playerRow][playerCol] = null;
        playerRow = newRow;
        playerCol = newCol;
        path.add(new int[] {newRow, newCol});

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

    @Override
    public String toString() {
        /*System.out.println("|-------------------|");*/
        for (int i = 0; i < boardSize; i++) {
           for (int j = 0; j < boardSize; j++) {
               System.out.print(gameBoard[i][j] != null ? gameBoard[i][j] /*+ " | "*/ : "⬜"/* | "*/);
           }
            System.out.print("\n");
           /*System.out.print("\n|-------------------|\n");*/
        }
       return "";
    }

    public int getGameState() {
        return gameState;
    }
}

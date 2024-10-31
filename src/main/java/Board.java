import java.util.Arrays;
import java.util.Random;

public class Board {
    Random randGen = new Random();
    private Integer boardSize;
    private Square[][] gameBoard;

    public Board(Integer size, Integer numMonsters) {
        this.boardSize = size;
        this.gameBoard = new Square[size][size];
        generateMonsters(numMonsters);
    }

    private void generateMonsters(Integer numMonsters) {
        for (int i = 0; i < numMonsters; i++) {
            do {
                int row = randGen.nextInt(boardSize);
                int col = randGen.nextInt(boardSize);

                if (gameBoard[row][col] instanceof Monster) {
                    continue;
                }

                gameBoard[row][col] = new Monster();
                break;
            } while (true);

        }
    }

    private void generatePlayer() {
        do {
            int row = randGen.nextInt(boardSize);
            int col = randGen.nextInt(boardSize);

            if (isSquareEmpty(row, col)) {
                gameBoard[row][col] = new Player();
                break;
            }
        } while (true);
    }

    private boolean isSquareEmpty(int row, int col) {
        return !(gameBoard[row][col] instanceof Monster);
    }

    @Override
    public String toString() {
       return Arrays.deepToString(gameBoard);
    }
}

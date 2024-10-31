import java.util.Scanner;

public class Main {
    private static String msg;
    static String option;
    public static void main(String[] args) {
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter board size >>> ");
            int boardSize = input.nextInt();
            System.out.print("\nEnter number of monsters >>> ");
            int numMonsters = input.nextInt();

            System.out.println();

            Board board = new Board(boardSize, numMonsters);
            board.startGame();

            System.out.print("Enter q to quit or anything else to play again >>> ");
            option = input.next();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        } while (!option.equalsIgnoreCase("q"));

    }
}
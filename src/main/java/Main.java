import java.util.Scanner;

public class Main {
    private static String msg;
    static String option;
    public static void main(String[] args) {
        do {
            Scanner input = new Scanner(System.in);

            Board board = new Board();
            board.startGame();

            System.out.print("Enter q to quit or anything else to play again >>> ");
            option = input.next();
        } while (!option.equalsIgnoreCase("q"));

    }
}
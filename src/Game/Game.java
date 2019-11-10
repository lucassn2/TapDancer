package Game;

import AI.AIMain;
import AI.Evaluator;
import Enumerators.COLOR;

import java.util.Scanner;

public class Game {

    Board board;
    Scanner input;
    COLOR computerColour;
    AIMain tapDancer;

    public void playGame() {
        // Initialize the game.
        board = new Board();
        input = new Scanner(System.in);
        board.setStandardBoard();

        // Initialize tapdancer.
        tapDancer = new AIMain(COLOR.BLACK);

        System.out.println("Which colour are you? (W/B)");
        String colour = input.next();
        if (colour.equals("W"))
            computerColour = COLOR.BLACK;
        else if (colour.equals("B"))
            computerColour = COLOR.WHITE;
        else {
            System.out.println("You gotta pick a colour, smartass.");
            System.exit(1);
        }

        // Initialize tapdancer.
        tapDancer = new AIMain(computerColour);

        COLOR currentColor = COLOR.WHITE;
        Move otherMove;
        while(true) {
            // Check turn.
            System.out.println(board.toString());
            if (computerColour == currentColor) {
                otherMove = tapDancer.makeMove(board);
                // TODO: Play TapDancer's move.
            } else {
                Move move = askPlayerMove();
                board.playMove(move);
            }

            currentColor = (currentColor == COLOR.WHITE) ? COLOR.BLACK : COLOR.WHITE;
        }

    }

    private Move askPlayerMove() {
        int x1;
        int y1;
        int x2;
        int y2;
        System.out.println("Input your move (From x, y, To x, y): ");
        x1 = input.nextInt();
        y1 = input.nextInt();
        x2 = input.nextInt();
        y2 = input.nextInt();
        return new Move(x1, y1, x2, y2, board.getPiece(x1, y1), board.getPiece(x2, y2), false);
    }

}

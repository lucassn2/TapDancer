package AI;

import Enumerators.COLOR;
import Enumerators.PIECETYPE;
import Game.Board;
import Game.Move;
import Game.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Evaluator {

    public int[][] pawnFieldValueBlack;
    public int[][] pawnFieldValueWhite;
    public static int[] pawnLine = {-2, 0, 3, 4, 5, 1, -2, -2};
    COLOR myColor;
    MoveGenerator generator;

    // TODO: Remove test main in evaluator.
    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator(COLOR.WHITE);
        Board testBoard = new Board();
        testBoard.setStandardBoard();
        System.out.println(testBoard.toString());
        System.out.println("Testing board: " + evaluator.evaluateBoard(testBoard));
    }

    public Evaluator(COLOR myColor) {
        this.myColor = myColor;
        pawnFieldValueBlack = new int[8][8];
        pawnFieldValueWhite = new int[8][8];
        setPawnFieldValue();
        generator = new MoveGenerator();
    }

    // Return evaluation. Add up value from own pieces, remove value for opponent pieces.
    public float evaluateBoard(Board board) {
        float evalSum = 0;
        float pieceValue;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                Piece piece = board.getPiece(x, y);
                if (piece == null) // We don't care about empty fields.
                    continue;
                // First we sum up pieces for a total value. This is the most basic evaluation function.
                System.out.println("Checking piece: " + piece);
                List<Move> moves = generator.getMoves(board, piece, x, y);
                switch(piece.type) {
                    case KING:
                        if (piece.color ==  myColor)
                            evalSum += 10000; // TODO: Add -100 per 'depths in' we are.
                        else
                            evalSum -= 10000;
                        break;
                    case QUEEN:
                        pieceValue = 900 + 1 * moves.size();
                        if (piece.color ==  myColor)
                            evalSum += pieceValue;
                        else
                            evalSum -= pieceValue;
                        break;
                    case ROOK:
                        pieceValue = 500 + 2 * moves.size();
                        if (piece.color ==  myColor)
                            evalSum += pieceValue;
                        else
                            evalSum -= pieceValue;
                        break;
                    case BISHOP:
                        pieceValue = 300 + 2 * moves.size();
                        if (piece.color ==  myColor)
                            evalSum += pieceValue;
                        else
                            evalSum -= pieceValue;
                        break;
                    case KNIGHT:
                        //distances from the 4 centers
                        List<Float> distances = new ArrayList<>();
                        float distance = (float) Math.sqrt(Math.pow(4-x, 2) + Math.pow(4-y, 2));
                        float distance2 = (float) Math.sqrt(Math.pow(4-x, 2) + Math.pow(5-y, 2));
                        float distance3 = (float) Math.sqrt(Math.pow(5-x, 2) + Math.pow(4-y, 2));
                        float distance4 = (float) Math.sqrt(Math.pow(5-x, 2) + Math.pow(5-y, 2));
                        distances.add(distance);
                        distances.add(distance2);
                        distances.add(distance3);
                        distances.add(distance4);
                        //return lowest distance
                        //System.out.print(300 + 3 * distances.indexOf(Collections.min(distances)));
                        pieceValue = 300 + 3 * distances.indexOf(Collections.min(distances));
                        if (piece.color ==  myColor)
                            evalSum += pieceValue;
                        else
                            evalSum -= pieceValue;
                        break;
                    case PAWN:
                        if (piece.color == COLOR.WHITE)
                            pieceValue = 100 + pawnFieldValueWhite[y-1][x-1];
                        else
                            pieceValue = 100 + pawnFieldValueBlack[y-1][x-1];
                        if (piece.color ==  myColor)
                            evalSum += pieceValue;
                        else
                            evalSum -= pieceValue;
                        break;
                    default:
                        break;
                }
                // TODO: Check for double-pawns
                // TODO: Check for fortresses
                // TODO: Check for threatened pieces
                // TODO: Check for endgame
                // TODO: Check for chess-mate-finale
                System.out.println("Eval: " + evalSum);
            }
        }
        return evalSum;
    }

    // Sets up all the pawn values. Should only be called in constructor.
    public void setPawnFieldValue() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0 || i == 7) {
                    pawnFieldValueBlack[i][j] = 0;
                    pawnFieldValueWhite[i][j] = 0;
                }
            }
        }

        pawnFieldValueWhite[1][0] = -2;
        pawnFieldValueWhite[1][1] = 0;
        pawnFieldValueWhite[1][2] = 3;
        pawnFieldValueWhite[1][3] = 4;
        pawnFieldValueWhite[1][4] = 5;
        pawnFieldValueWhite[1][5] = 1;
        pawnFieldValueWhite[1][6] = -2;
        pawnFieldValueWhite[1][7] = -2;
        pawnFieldValueWhite[2][0] = -4;
        pawnFieldValueWhite[2][1] = -1;
        pawnFieldValueWhite[2][2] = 4;
        pawnFieldValueWhite[2][3] = 5;
        pawnFieldValueWhite[2][4] = 7;
        pawnFieldValueWhite[2][5] = 1;
        pawnFieldValueWhite[2][6] = -4;
        pawnFieldValueWhite[2][7] = -4;
        pawnFieldValueWhite[3][0] = -4;
        pawnFieldValueWhite[3][1] = 0;
        pawnFieldValueWhite[3][2] = 6;
        pawnFieldValueWhite[3][3] = 8;
        pawnFieldValueWhite[3][4] = 10;
        pawnFieldValueWhite[3][5] = 2;
        pawnFieldValueWhite[3][6] = -4;
        pawnFieldValueWhite[3][7] = -4;
        pawnFieldValueWhite[4][0] = -3;
        pawnFieldValueWhite[4][1] = 2;
        pawnFieldValueWhite[4][2] = 10;
        pawnFieldValueWhite[4][3] = 12;
        pawnFieldValueWhite[4][4] = 15;
        pawnFieldValueWhite[4][5] = 5;
        pawnFieldValueWhite[4][6] = -3;
        pawnFieldValueWhite[4][7] = -2;
        pawnFieldValueWhite[5][0] = 8;
        pawnFieldValueWhite[5][1] = 14;
        pawnFieldValueWhite[5][2] = 23;
        pawnFieldValueWhite[5][3] = 26;
        pawnFieldValueWhite[5][4] = 29;
        pawnFieldValueWhite[5][5] = 17;
        pawnFieldValueWhite[5][6] = 8;
        pawnFieldValueWhite[5][7] = 8;
        pawnFieldValueWhite[6][0] = 23;
        pawnFieldValueWhite[6][1] = 30;
        pawnFieldValueWhite[6][2] = 42;
        pawnFieldValueWhite[6][3] = 44;
        pawnFieldValueWhite[6][4] = 48;
        pawnFieldValueWhite[6][5] = 34;
        pawnFieldValueWhite[6][6] = 23;
        pawnFieldValueWhite[6][7] = 23;

        pawnFieldValueBlack[6][7] = -2;
        pawnFieldValueBlack[6][6] = 0;
        pawnFieldValueBlack[6][5] = 3;
        pawnFieldValueBlack[6][4] = 4;
        pawnFieldValueBlack[6][3] = 5;
        pawnFieldValueBlack[6][2] = 1;
        pawnFieldValueBlack[6][1] = -2;
        pawnFieldValueBlack[6][0] = -2;
        pawnFieldValueBlack[5][7] = -4;
        pawnFieldValueBlack[5][6] = -1;
        pawnFieldValueBlack[5][5] = 4;
        pawnFieldValueBlack[5][4] = 5;
        pawnFieldValueBlack[5][3] = 7;
        pawnFieldValueBlack[5][2] = 1;
        pawnFieldValueBlack[5][1] = -4;
        pawnFieldValueBlack[5][0] = -4;
        pawnFieldValueBlack[4][7] = -4;
        pawnFieldValueBlack[4][6] = 0;
        pawnFieldValueBlack[4][5] = 6;
        pawnFieldValueBlack[4][4] = 8;
        pawnFieldValueBlack[4][3] = 10;
        pawnFieldValueBlack[4][2] = 2;
        pawnFieldValueBlack[4][1] = -4;
        pawnFieldValueBlack[4][0] = -4;
        pawnFieldValueBlack[3][7] = -3;
        pawnFieldValueBlack[3][6] = 2;
        pawnFieldValueBlack[3][5] = 10;
        pawnFieldValueBlack[3][4] = 12;
        pawnFieldValueBlack[3][3] = 15;
        pawnFieldValueBlack[3][2] = 5;
        pawnFieldValueBlack[3][1] = -3;
        pawnFieldValueBlack[3][0] = -2;
        pawnFieldValueBlack[2][7] = 8;
        pawnFieldValueBlack[2][6] = 14;
        pawnFieldValueBlack[2][5] = 23;
        pawnFieldValueBlack[2][4] = 26;
        pawnFieldValueBlack[2][3] = 29;
        pawnFieldValueBlack[2][2] = 17;
        pawnFieldValueBlack[2][1] = 8;
        pawnFieldValueBlack[2][0] = 8;
        pawnFieldValueBlack[1][7] = 23;
        pawnFieldValueBlack[1][6] = 30;
        pawnFieldValueBlack[1][5] = 42;
        pawnFieldValueBlack[1][4] = 44;
        pawnFieldValueBlack[1][3] = 48;
        pawnFieldValueBlack[1][2] = 34;
        pawnFieldValueBlack[1][1] = 23;
        pawnFieldValueBlack[1][0] = 23;
    }
}

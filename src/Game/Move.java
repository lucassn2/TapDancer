package Game;

public class Move implements Comparable<Move> {
    public int fromX, fromY;
    public int toX, toY;
    public Piece subject, target;
    public boolean special;
    public int moveValue; // Each move now has a value to determine where in the priorityqueue it should lie.
    // Value 10 = normal move.
    // Value 33 = Player move.
    // Value 50 = A move that beats another piece.
    // Value 100 = Castling
    // Value 500 = Best Move

    public Move(int fromX, int fromY, int toX, int toY, Piece subject, Piece target, boolean special, int moveValue) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.subject = subject;
        this.target = target;
        this.special = special;
    }

    public String toString() {
        String output = subject + " moved from " + fromX + "," + fromY + " to " + toX + "," + toY;
        if (target != null)
            output += " beating a " + target;
        return output;
    }

    @Override
    public int compareTo(Move move) {
        return moveValue - move.moveValue;
    }

}

package battleship.game;

public class Cell {
    private int i;
    private int j;

    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public Cell() {
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}

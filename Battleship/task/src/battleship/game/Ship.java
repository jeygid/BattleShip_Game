package battleship.game;

public enum Ship {
    AIRCRAFT_CARRIER(5, "Aircraft Carrier (5 cells)"),
    BATTLESHIP(4, "Battleship (4 cells)"),
    SUBMARINE(3, "Submarine (3 cells)"),
    CRUISER(3, "Cruiser (3 cells)"),
    DESTROYER(2, "Destroyer (2 cells)");

    private int size;
    private String description;

    Ship(int size, String description) {
        this.size = size;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getSize() {
        return size;
    }
}

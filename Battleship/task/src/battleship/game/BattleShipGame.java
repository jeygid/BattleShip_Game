package battleship.game;

import java.util.ArrayList;
import java.util.Scanner;

public class BattleShipGame {

    public static int size = 10;
    public static int[][] playerOneGameField = new int[size][size];
    public static int[][] playerTwoGameField = new int[size][size];

    public static ArrayList<ArrayList<Cell>> playerOneFleet = new ArrayList<>();
    public static ArrayList<ArrayList<Cell>> playerTwoFleet = new ArrayList<>();

    public static boolean isFinished = false;

    public static Scanner scanner = new Scanner(System.in);


    public static void initialize() {

        System.out.println("\nPlayer 1, place your ships on the game field");
        drawGameField(1, false);

        placeShip(1, Ship.AIRCRAFT_CARRIER);
        drawGameField(1, false);

        placeShip(1, Ship.BATTLESHIP);
        drawGameField(1, false);

        placeShip(1, Ship.SUBMARINE);
        drawGameField(1, false);

        placeShip(1, Ship.CRUISER);
        drawGameField(1, false);

        placeShip(1, Ship.DESTROYER);
        drawGameField(1, false);

        System.out.println("\nPress Enter and pass the move to another player");

        scanner.nextLine();

        System.out.println("\nPlayer 2, place your ships on the game field");
        drawGameField(2, false);

        placeShip(2, Ship.AIRCRAFT_CARRIER);
        drawGameField(2, false);

        placeShip(2, Ship.BATTLESHIP);
        drawGameField(2, false);

        placeShip(2, Ship.SUBMARINE);
        drawGameField(2, false);

        placeShip(2, Ship.CRUISER);
        drawGameField(2, false);

        placeShip(2, Ship.DESTROYER);
        drawGameField(2, false);
    }


    public static void startGame() {

        while (!isFinished) {

            System.out.println("\nPress Enter and pass the move to another player");
            scanner.nextLine();
            game(1);

            if (isFinished) break;

            System.out.println("\nPress Enter and pass the move to another player");
            scanner.nextLine();
            game(2);
        }
    }


    public static void game(int player) {

        int[][] attackedGameField = new int[size][size];
        ArrayList<ArrayList<Cell>> attackedFleet = new ArrayList<>();

        int attackedPlayer = 0;

        if (player == 1) {
            attackedFleet = playerTwoFleet;
            attackedGameField = playerTwoGameField;
            attackedPlayer = 2;
        } else if (player == 2) {
            attackedFleet = playerOneFleet;
            attackedGameField = playerOneGameField;
            attackedPlayer = 1;
        }

        String command = "";
        int aX;
        int aY;

        drawGameField(attackedPlayer, true);
        System.out.println("---------------------");
        drawGameField(player, false);

        System.out.println("\nPlayer " + player + ", it's your turn:");

        while (true) {

            command = scanner.nextLine();

            if (!command.matches("[A-Z][1-9][0]?")) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }

            aX = command.replaceAll("[0-9]+", "").charAt(0) - 65;
            aY = Integer.parseInt(command.replaceAll("[A-Z]+", "")) - 1;

            if (aX >= size || aY >= size) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }

            break;
        }

        if (attackedGameField[aX][aY] == 5) {
            attackedGameField[aX][aY] = 8;

            ArrayList<Cell> currentShip = new ArrayList<>();
            Cell currentCell = new Cell();

            for (ArrayList<Cell> ship : attackedFleet) {

                for (Cell cell : ship) {

                    Cell fleetCell = cell;

                    if (fleetCell.getI() == aX && fleetCell.getJ() == aY) {

                        currentShip = ship;
                        currentCell = cell;

                    }
                }
            }

            currentShip.remove(currentCell);

            if (currentShip.size() == 0) {

                attackedFleet.remove(currentShip);

                if (attackedFleet.size() == 0) {
                    System.out.println("\nYou sank the last ship. You won. Congratulations!");
                    isFinished = true;
                } else {
                    System.out.println("\nYou sank a ship!");
                }

            } else {

                System.out.println("\nYou hit a ship!");

            }

        } else if (attackedGameField[aX][aY] == 0) {
            attackedGameField[aX][aY] = 1;
            System.out.println("You missed");
        }


        drawGameField(attackedPlayer, true);
        System.out.println("---------------------");
        drawGameField(player, false);
    }


    public static boolean checkShipsAround(int player, ArrayList<Cell> shipCells) {

        int[][] gameField = new int[size][size];

        if (player == 1) {
            gameField = playerOneGameField;
        } else if (player == 2) {
            gameField = playerTwoGameField;
        }

        ArrayList<Cell> conflictedCells = new ArrayList<>();
        boolean check = false;

        for (Cell cell : shipCells) {

            int i = cell.getI();
            int j = cell.getJ();

            /* Left top corner */
            if (i == 0 && j == 0) {

                if (gameField[i][j + 1] == 5) conflictedCells.add(new Cell(i, j + 1));

                if (gameField[i + 1][j + 1] == 5) conflictedCells.add(new Cell(i + 1, j + 1));

                if (gameField[i + 1][j] == 5) conflictedCells.add(new Cell(i + 1, j));

                /* Right top corner */
            } else if (i == 0 && j == size - 1) {

                if (gameField[i][j - 1] == 5) conflictedCells.add(new Cell(i, j - 1));

                if (gameField[i + 1][j - 1] == 5) conflictedCells.add(new Cell(i + 1, j - 1));

                if (gameField[i + 1][j - 1] == 5) conflictedCells.add(new Cell(i + 1, j - 1));

                /* Left bottom corner */
            } else if (i == size - 1 && j == 0) {

                if (gameField[i - 1][j] == 5) conflictedCells.add(new Cell(i - 1, j));

                if (gameField[i - 1][j + 1] == 5) conflictedCells.add(new Cell(i - 1, j + 1));

                if (gameField[i][j + 1] == 5) conflictedCells.add(new Cell(i, j + 1));

                /* Right bottom corner */
            } else if (i == size - 1 && j == size - 1) {

                if (gameField[i][j - 1] == 5) conflictedCells.add(new Cell(i, j - 1));

                if (gameField[i - 1][j - 1] == 5) conflictedCells.add(new Cell(i - 1, j - 1));

                if (gameField[i - 1][j] == 5) conflictedCells.add(new Cell(i - 1, j));

                /* Top line cells */
            } else if (i == 0) {

                if (gameField[i][j - 1] == 5) conflictedCells.add(new Cell(i, j - 1));

                if (gameField[i][j + 1] == 5) conflictedCells.add(new Cell(i, j + 1));

                if (gameField[i + 1][j - 1] == 5) conflictedCells.add(new Cell(i + 1, j - 1));

                if (gameField[i + 1][j] == 5) conflictedCells.add(new Cell(i + 1, j));

                if (gameField[i + 1][j + 1] == 5) conflictedCells.add(new Cell(i + 1, j + 1));

                /* Left side line cells */
            } else if (j == 0) {

                if (gameField[i - 1][j] == 5) conflictedCells.add(new Cell(i - 1, j));

                if (gameField[i + 1][j] == 5) conflictedCells.add(new Cell(i + 1, j));

                if (gameField[i - 1][j + 1] == 5) conflictedCells.add(new Cell(i - 1, j + 1));

                if (gameField[i][j + 1] == 5) conflictedCells.add(new Cell(i, j + 1));

                if (gameField[i + 1][j + 1] == 5) conflictedCells.add(new Cell(i + 1, j + 1));

                /* Bottom line cells */
            } else if (i == size - 1) {

                if (gameField[i][j - 1] == 5) conflictedCells.add(new Cell(i, j - 1));

                if (gameField[i][j + 1] == 5) conflictedCells.add(new Cell(i, j + 1));

                if (gameField[i - 1][j - 1] == 5) conflictedCells.add(new Cell(i - 1, j - 1));

                if (gameField[i - 1][j] == 5) conflictedCells.add(new Cell(i - 1, j));

                if (gameField[i - 1][j + 1] == 5) conflictedCells.add(new Cell(i - 1, j + 1));

                /* Right side line cells */
            } else if (j == size - 1) {

                if (gameField[i - 1][j] == 5) conflictedCells.add(new Cell(i - 1, j));

                if (gameField[i + 1][j] == 5) conflictedCells.add(new Cell(i + 1, j));

                if (gameField[i - 1][j - 1] == 5) conflictedCells.add(new Cell(i - 1, j - 1));

                if (gameField[i][j - 1] == 5) conflictedCells.add(new Cell(i, j - 1));

                if (gameField[i + 1][j - 1] == 5) conflictedCells.add(new Cell(i + 1, j - 1));

                /* Regular cells */
            } else {

                if (gameField[i - 1][j - 1] == 5) conflictedCells.add(new Cell(i - 1, j - 1));

                if (gameField[i][j - 1] == 5) conflictedCells.add(new Cell(i, j - 1));

                if (gameField[i + 1][j - 1] == 5) conflictedCells.add(new Cell(i + 1, j - 1));

                if (gameField[i - 1][j] == 5) conflictedCells.add(new Cell(i - 1, j));

                if (gameField[i + 1][j] == 5) conflictedCells.add(new Cell(i + 1, j));

                if (gameField[i - 1][j + 1] == 5) conflictedCells.add(new Cell(i - 1, j + 1));

                if (gameField[i][j + 1] == 5) conflictedCells.add(new Cell(i, j + 1));

                if (gameField[i + 1][j + 1] == 5) conflictedCells.add(new Cell(i + 1, j + 1));

            }

        }

        for (Cell shipCell : shipCells) {
            if (conflictedCells.contains(shipCell)) {
                conflictedCells.remove(shipCell);
            }
        }

        if (conflictedCells.size() > 0) check = true;

        return check;
    }

    public static void drawGameField(int player, boolean fogOfWar) {

        int[][] gameField = new int[size][size];

        if (player == 1) {
            gameField = playerOneGameField;
        } else if (player == 2) {
            gameField = playerTwoGameField;
        }

        System.out.print("\n ");

        for (int k = 1; k <= gameField.length; k++) {
            System.out.print(" " + k);
        }

        System.out.println();

        char character = 'A';

        for (int i = 0; i < gameField.length; i++) {

            System.out.print(character);
            character++;

            for (int j = 0; j < gameField[0].length; j++) {

                if (gameField[i][j] == 0) {
                    System.out.print(" " + "~");
                } else if (gameField[i][j] == 5 && !fogOfWar) {
                    System.out.print(" " + "O");
                } else if (gameField[i][j] == 5 && fogOfWar) {
                    System.out.print(" " + "~");
                } else if (gameField[i][j] == 8) {
                    System.out.print(" " + "X");
                } else if (gameField[i][j] == 1) {
                    System.out.print(" " + "M");
                } else {
                    System.out.print(" " + "~");
                }

            }
            System.out.println();
        }
    }


    public static void placeShip(int player, Ship ship) {

        int[][] gameField = new int[size][size];
        ArrayList<ArrayList<Cell>> fleet = new ArrayList<>();

        if (player == 1) {
            gameField = playerOneGameField;
            fleet = playerOneFleet;
        } else if (player == 2) {
            gameField = playerTwoGameField;
            fleet = playerTwoFleet;
        }

        int length = 0;
        String description = "";

        switch (ship) {
            case AIRCRAFT_CARRIER:
                length = Ship.AIRCRAFT_CARRIER.getSize();
                description = Ship.AIRCRAFT_CARRIER.getDescription();
                break;
            case BATTLESHIP:
                length = Ship.BATTLESHIP.getSize();
                description = Ship.BATTLESHIP.getDescription();
                break;
            case SUBMARINE:
                length = Ship.SUBMARINE.getSize();
                description = Ship.SUBMARINE.getDescription();
                break;
            case CRUISER:
                length = Ship.CRUISER.getSize();
                description = Ship.CRUISER.getDescription();
                break;
            case DESTROYER:
                length = Ship.DESTROYER.getSize();
                description = Ship.DESTROYER.getDescription();
                break;
            default:
                break;

        }

        String command = "";

        System.out.println("\nEnter the coordinates of the " + description + ":");

        while (true) {

            command = scanner.nextLine();

            if (!command.matches("[A-J][1-9][0]?[ ][A-J][1-9][0]?")) {
                System.out.println("\nError! Wrong arguments! Try again:");
                continue;
            }

            String[] cells = command.split(" ");

            int aX = cells[0].replaceAll("[0-9]+", "").charAt(0) - 65;
            int aY = Integer.parseInt(cells[0].replaceAll("[A-Z]+", "")) - 1;
            int bX = cells[1].replaceAll("[0-9]+", "").charAt(0) - 65;
            int bY = Integer.parseInt(cells[1].replaceAll("[A-Z]+", "")) - 1;

            if (aX == bX && (aY - bY == length - 1 || bY - aY == length - 1)) {

                ArrayList<Cell> shipCells = new ArrayList<>();

                // Example: A5 A3 aY = 5 bY = 3
                if (aY - bY > 0) {

                    for (int i = 0; i < length; i++) {
                        shipCells.add(new Cell(aX, bY + i));
                    }

                    if (checkShipsAround(player, shipCells)) {
                        System.out.println("\nError! You placed it too close to another one. Try again:");
                        continue;
                    } else {

                        for (Cell cell : shipCells) {
                            gameField[cell.getI()][cell.getJ()] = 5;
                        }

                        fleet.add(shipCells);

                    }

                    // Example: A3 A5 aY = 3 bY = 5
                } else if (bY - aY > 0) {

                    for (int i = 0; i < length; i++) {
                        shipCells.add(new Cell(aX, aY + i));
                    }

                    if (checkShipsAround(player, shipCells)) {
                        System.out.println("\nError! Wrong ship location! Try again:");
                        continue;

                    } else {

                        for (Cell cell : shipCells) {
                            gameField[cell.getI()][cell.getJ()] = 5;
                        }

                        fleet.add(shipCells);
                    }
                }

                break;

            } else if (aY == bY && (aX - bX == length - 1 || bX - aX == length - 1)) {

                ArrayList<Cell> shipCells = new ArrayList<>();

                // Example: D1 A1 aX = 3 (ASCII 68 - 65) bX = 0 (ASCII 65 - 65)
                if (aX - bX > 0) {

                    for (int i = 0; i < length; i++) {
                        shipCells.add(new Cell(bX + i, aY));
                    }

                    if (checkShipsAround(player, shipCells)) {
                        System.out.println("\nError! You placed it too close to another one. Try again:");
                        continue;
                    } else {
                        for (Cell cell : shipCells) {
                            gameField[cell.getI()][cell.getJ()] = 5;
                        }

                        fleet.add(shipCells);

                    }

                    // Example: A1 D1 aX = 0 (ASCII 65 - 65) bX = 3 (ASCII 68 - 65)
                } else if (bX - aX > 0) {

                    for (int i = 0; i < length; i++) {
                        shipCells.add(new Cell(aX + i, aY));
                    }

                    if (checkShipsAround(player, shipCells)) {
                        System.out.println("\nError! You placed it too close to another one. Try again:");
                        continue;
                    } else {
                        for (Cell cell : shipCells) {
                            gameField[cell.getI()][cell.getJ()] = 5;
                        }

                        fleet.add(shipCells);

                    }
                }

                break;

            } else {
                System.out.println("\nError! Wrong ship location! Try again:");
                continue;
            }

        }
    }
}

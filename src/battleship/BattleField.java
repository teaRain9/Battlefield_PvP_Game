package battleship;

import java.util.ArrayList;
import java.util.List;

public class BattleField {
    private final Player playerA;

    private Ship currentShip;
    int counter = 0;
    List<Ship> shipList;

    private boolean hiddenMode;


    public BattleField() {
        this.playerA = new Player();
        this.shipList = new ArrayList<>();
        shipList.add(new Ship("Aircraft Carrier", 5));
        shipList.add(new Ship("Battleship", 4));
        shipList.add(new Ship("Submarine", 3));
        shipList.add(new Ship("Cruiser", 3));
        shipList.add(new Ship("Destroyer", 2));
        this.currentShip = shipList.get(counter);
        this.hiddenMode = false;
    }

    public void newCoordinate(Coordinate startPoint, Coordinate endPoint) throws BattleFieldException {
        if (extractCoordinates(startPoint, endPoint).size() != shipList.get(counter).getLength())
            throw new BattleFieldException("Error! Wrong length of the " + shipList.get(counter).getName() + "! Try again:");

        List<Coordinate> coordinates = extractCoordinates(startPoint, endPoint);
        List<Coordinate> closeCoordinates = extractCloseCoordinates(startPoint, endPoint);

        playerA.newCoordinate(coordinates, closeCoordinates);
        currentShip.addCoordinate(extractCoordinates(startPoint, endPoint));
        counter ++;
        if (counter == 5) {
            counter = 0;
        }
        currentShip = shipList.get(counter);
    }

    private List<Coordinate> extractCoordinates(Coordinate startPoint, Coordinate endPoint) throws BattleFieldException {
        List<Coordinate> outCoord = new ArrayList<>();
        if (startPoint.equals(endPoint)
                || (startPoint.getRow() != endPoint.getRow() && startPoint.getCol() != endPoint.getCol())) {
            throw new BattleFieldException("Error! Wrong ship location! Try again:");
        } else if (startPoint.getRow() > 'J' || startPoint.getRow() < 'A' || endPoint.getRow() > 'J' || endPoint.getRow() <'A') {
            throw new BattleFieldException("Error! Row coordinates should be from 'A' to 'J'! Try again:");
        } else if (startPoint.getCol() > 10 || startPoint.getCol() < 1 || endPoint.getCol() > 10 || endPoint.getCol() < 1) {
            throw new BattleFieldException("Error! Columns coordinates should be from 1 to 10! Try again:");
        } else {
            if (startPoint.getRow() == endPoint.getRow()) {
                for (int i = startPoint.getCol() ; i <= endPoint.getCol() ; i++ ) {
                    final Coordinate coordinate = new Coordinate(startPoint.getRow(), i);
                    outCoord.add(coordinate);
                }
            } else {
                for (char i = startPoint.getRow(); i <= endPoint.getRow() ; i++) {
                    final Coordinate coordinate = new Coordinate(i, startPoint.getCol());
                    outCoord.add(coordinate);
                }
            }
        }
        return outCoord;
    }

    private List<Coordinate> extractCloseCoordinates(Coordinate startPoint, Coordinate endPoint) {
        List<Coordinate> closeCoord = new ArrayList<>();

        if (startPoint.getRow() == endPoint.getRow()) {
            for (int i = startPoint.getCol()-1 ; i <= endPoint.getCol()+1 ; i++ ) {
                final Coordinate coordinate1 = new Coordinate(startPoint.getRow(), i);
                final Coordinate coordinate2 = new Coordinate((char) (startPoint.getRow()-1), i);
                final Coordinate coordinate3 = new Coordinate((char) (startPoint.getRow()+1), i);
                closeCoord.add(coordinate1);
                closeCoord.add(coordinate2);
                closeCoord.add(coordinate3);
            }
        } else {
            for (char i = (char) (startPoint.getRow()-1); i <= (char) (endPoint.getRow()+1) ; i++) {
                final Coordinate coordinate1 = new Coordinate(i, startPoint.getCol());
                final Coordinate coordinate2 = new Coordinate(i, startPoint.getCol()-1);
                final Coordinate coordinate3 = new Coordinate(i, startPoint.getCol()+1);
                closeCoord.add(coordinate1);
                closeCoord.add(coordinate2);
                closeCoord.add(coordinate3);
            }
        }
        return closeCoord;

    }


    public void print() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0 ; i < 10 ; i++){
            char row = (char) (i + 65);
            System.out.print(row + " ");
            for (int j = 1 ; j <= 10 ; j++){
                Coordinate coordinate = new Coordinate(row, j);
                if (playerA.hasCoordinate(coordinate)){
                    if (playerA.hit(coordinate)){
                        System.out.print("X ");
                    } else {
                        System.out.print(hiddenMode ? "~ " : "O ");
                    }
                } else {
                   if (playerA.missed(coordinate)) {
                       System.out.print("M ");
                   } else {
                        System.out.print("~ ");
                   }
                }

            }
            System.out.println();

        }
    }

    public void takeShot(Coordinate shot) throws BattleFieldException{
        if (shot.getRow() > 'J' || shot.getRow() < 'A' || shot.getCol() > 10 || shot.getCol() < 1) {
            throw new BattleFieldException("Error! You entered the wrong coordinates! Try again:");
        }
        playerA.shotAnl(shot);
        print();
        if (playerA.hasCoordinate(shot)) {
            boolean sankShip = false;
            for (Ship ship : shipList) {
                if (ship.hasCoordinate(shot)){
                    ship.removeCoordinate(shot);
                }
                if (ship.getShipLocation().isEmpty()) {
                    sankShip = true;
                }
            }
            shipList.removeIf(n -> n.getShipLocation().isEmpty());

            if (sankShip) {
                if (gameOver()) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                } else {
                    System.out.println("You sank a ship!");
                }
            } else {
                System.out.println("You hit a ship!");
            }
        } else {
            System.out.println("You missed!");
        }
    }

    public String shipName() {
        return currentShip.getName();
    }

    public int shipLength() {
        return currentShip.getLength();
    }



    public Boolean endShips() {
        if (playerA.coordinatesSize() == 17) {
            return true;
        } else {
            return false;
        }
    }

    public void setHiddenMode(boolean hiddenMode) {
        this.hiddenMode = hiddenMode;
    }

    public boolean gameOver(){

        return shipList.size() == 0 ? true : false;
    }
}

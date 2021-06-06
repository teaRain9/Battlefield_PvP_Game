package battleship;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Coordinate> coordinates;
    private final List<Coordinate> reservedCoordinates;
    private final List<Coordinate> hitShots;
    private final List<Coordinate> missedShots;


    public Player() {
        this.coordinates = new ArrayList<>();
        this.reservedCoordinates = new ArrayList<>();
        this.hitShots = new ArrayList<>();
        this.missedShots = new ArrayList<>();
    }

    public void newCoordinate(List<Coordinate> addCoordinates, List<Coordinate> closeCoordinates) throws BattleFieldException {
        for (Coordinate item : addCoordinates) {
            if (coordinates.contains(item)){
                throw new BattleFieldException("Error! The spot is already occupied. Try again:");
            } else if (reservedCoordinates.contains(item)) {
                throw new BattleFieldException("Error! You placed it too close to another one. Try again:");
            }
        }
        coordinates.addAll(addCoordinates);
        reservedCoordinates.addAll(closeCoordinates);
    }

    public void shotAnl(Coordinate shot) {
        if (coordinates.contains(shot)) {
            hitShots.add(shot);
        } else {
            missedShots.add(shot);
        }
    }

    public Boolean hasCoordinate (Coordinate coordinate) {
        return coordinates.contains(coordinate);
    }
    public Boolean hit (Coordinate coordinate) {
        return hitShots.contains(coordinate);
    }

    public Boolean missed (Coordinate coordinate) {
        return missedShots.contains(coordinate);
    }





    public int coordinatesSize() {
        return coordinates.size();
    }


}

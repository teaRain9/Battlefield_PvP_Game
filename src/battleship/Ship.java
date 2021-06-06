package battleship;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final String name;
    private final int length;
    private final List<Coordinate> shipLocation;

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.shipLocation = new ArrayList<>();
    }

    public void addCoordinate(List<Coordinate> coordinates) {
        shipLocation.addAll(coordinates);
    }

    public boolean hasCoordinate(Coordinate coordinate){
        return shipLocation.contains(coordinate);
    }

    public void removeCoordinate(Coordinate coordinate){
        shipLocation.remove(coordinate);
    }

    public List<Coordinate> getShipLocation() {
        return shipLocation;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }
}

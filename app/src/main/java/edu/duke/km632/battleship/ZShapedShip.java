package edu.duke.km632.battleship;

import java.util.HashMap;

/**
 * This class extends the BasicShip and implements
 * ships that are Z shaped (aka Carriers)
 */

public class ZShapedShip<T> extends BasicShip<T> {
  private final String name;


  /**
   * Constructs the Z shaped ship/Carrier  by creating Coordinate object for each coordinate fo
   * the ship
   */ 


  public ZShapedShip(String name, Coordinate upperLeft, Character orientation, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, orientation), myDisplayInfo, enemyDisplayInfo);
    this.name = name;

  }

  public ZShapedShip(String name, Coordinate upperLeft, Character orientation, T data, T onHit) {
    this(name, upperLeft, orientation, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));

  }
  /**
   * @return "Carrier" as string
   */

  public String getName() {
    return name;
  }


  
  static HashMap<Integer, Coordinate> makeCoords(Coordinate upperLeft, Character orientation) {
    HashMap<Integer, Coordinate> myCoords = new HashMap<Integer, Coordinate>();

    if (orientation == 'U') {
      myCoords.put(0, new Coordinate(upperLeft.getRow(), upperLeft.getColumn()));
      myCoords.put(1, new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
      myCoords.put(2, new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn()));
      myCoords.put(3, new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn() + 1));
      myCoords.put(4, new Coordinate(upperLeft.getRow() + 3, upperLeft.getColumn()));
      myCoords.put(5, new Coordinate(upperLeft.getRow() + 3, upperLeft.getColumn() + 1));
      myCoords.put(6, new Coordinate(upperLeft.getRow() + 4, upperLeft.getColumn() + 1));

    }
    if (orientation == 'R') {
      myCoords.put(4, new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
      myCoords.put(2, new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 2));
      myCoords.put(1, new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 3));
      myCoords.put(0, new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 4));
      myCoords.put(6, new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
      myCoords.put(5, new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
      myCoords.put(3, new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 2));

    }
    if (orientation == 'D') {
      myCoords.put(6, new Coordinate(upperLeft.getRow(), upperLeft.getColumn()));
      myCoords.put(5, new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
      myCoords.put(3, new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn()));
      myCoords.put(4, new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
      myCoords.put(2, new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn() + 1));
      myCoords.put(1, new Coordinate(upperLeft.getRow() + 3, upperLeft.getColumn() + 1));
      myCoords.put(0, new Coordinate(upperLeft.getRow() + 4, upperLeft.getColumn() + 1));

    }
    if (orientation == 'L') {
      myCoords.put(3, new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 2));
      myCoords.put(5, new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 3));
      myCoords.put(6, new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 4));
      myCoords.put(0, new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
      myCoords.put(1, new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
      myCoords.put(2, new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 2));
      myCoords.put(4, new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 3));

    }

    return myCoords;

  }

}

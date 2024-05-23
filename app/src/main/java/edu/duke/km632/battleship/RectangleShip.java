package edu.duke.km632.battleship;

import java.util.HashMap;

/**
 * This class extends the BasicShp and implements
 * ships that are rectangular
 */

public class RectangleShip<T> extends BasicShip<T> {

  private final String name;

  /**
   * Constructs the rectangular ship by creating Coordinate object for each coordinate fo
   * the ship
   * 
   * @param name is the type of the ship; upperLeft is the left top tip of the
   *             ship; width is the width of the ship;
   *             height is the height of the ship, myDisplayInfo is the display of
   *             of the board at a location
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo);
    this.name = name;


  }
 public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
                new SimpleShipDisplayInfo<T>(null, data));

 }

  // this constructor is used for testing purposes
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
  }

  /**
   * @return name of the ship as string ex: "Submarine", "Destroyer" 
   */
  public String getName() {
    return name;
  }

  static HashMap<Integer, Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashMap<Integer, Coordinate> myCoords = new HashMap<>();
    int key = 0;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j <  height; j++) {
        Coordinate c = new Coordinate (upperLeft.getRow() + j, upperLeft.getColumn() + i);
        key ++;
        myCoords.put(key, c);
      }
    }
    return myCoords;

  }

}

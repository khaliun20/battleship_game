package edu.duke.km632.battleship;

import java.util.HashMap;

/**
 * This class extends the BasicShip and implements
 * ships that are T shaped (aka Battleships)
 */

public class TShapedShip<T> extends BasicShip<T> {

  private final String name;


  /**
   * Constructs the T shaped ship/Battleship by creating Coordinate object for each coordinate fo
   * the ship
   */ 

  public TShapedShip(String name, Coordinate upperLeft, Character orientation, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, orientation), myDisplayInfo, enemyDisplayInfo);
    this.name = name;



  }
  public TShapedShip(String name, Coordinate upperLeft, Character orientation, T data, T onHit) {
        this(name, upperLeft, orientation, new SimpleShipDisplayInfo<T>(data, onHit),
             new SimpleShipDisplayInfo<T>(null, data));


    }
  /**
   *@return "Battleship" as string
   */
  public String getName() {
    return name;
  }
  
  static HashMap<Integer, Coordinate> makeCoords(Coordinate upperLeft, Character orientation) {
    HashMap<Integer, Coordinate> myCoords = new HashMap<Integer,Coordinate>();

    if(orientation == 'U'){
      myCoords.put(0, new Coordinate(upperLeft.getRow(), upperLeft.getColumn()+1));
      myCoords.put(1, new Coordinate(upperLeft.getRow()+1, upperLeft.getColumn()));
      myCoords.put(2, new Coordinate(upperLeft.getRow()+1, upperLeft.getColumn()+1));
      myCoords.put(3, new Coordinate(upperLeft.getRow()+1, upperLeft.getColumn()+2));
    }
    if(orientation == 'R'){
      myCoords.put(1, new Coordinate(upperLeft.getRow(), upperLeft.getColumn()));
      myCoords.put(2, new Coordinate(upperLeft.getRow()+1, upperLeft.getColumn()));
      myCoords.put(0, new Coordinate(upperLeft.getRow()+1, upperLeft.getColumn()+1));
      myCoords.put(3, new Coordinate(upperLeft.getRow()+2, upperLeft.getColumn()));

    }
     if(orientation == 'D'){
       myCoords.put(3,new Coordinate(upperLeft.getRow(), upperLeft.getColumn()));
       myCoords.put(2, new Coordinate(upperLeft.getRow(), upperLeft.getColumn()+1));
       myCoords.put(1, new Coordinate(upperLeft.getRow(), upperLeft.getColumn()+2));
       myCoords.put(0, new Coordinate(upperLeft.getRow()+1, upperLeft.getColumn()+1));

     }
     if(orientation == 'L'){
       myCoords.put(3, new Coordinate(upperLeft.getRow(), upperLeft.getColumn()+1));
       myCoords.put(0, new Coordinate(upperLeft.getRow()+1, upperLeft.getColumn()));
       myCoords.put(2, new Coordinate(upperLeft.getRow()+1, upperLeft.getColumn()+1));
       myCoords.put(1, new Coordinate(upperLeft.getRow()+2, upperLeft.getColumn()+1));

     }
 
    return myCoords;

  }

}

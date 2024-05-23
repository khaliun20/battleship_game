package edu.duke.km632.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to make ships on the board
 */

public abstract class BasicShip<T> implements Ship<T> {
  protected HashMap<Coordinate, Boolean> myPieces;// this is where I am going to keep my current ships locations
  protected ShipDisplayInfo<T> myDisplayInfo; // this is my ships's view from my perspective
  protected ShipDisplayInfo<T> enemyDisplayInfo;// this is my ships's view from enemy's perspective
  protected HashMap<Integer, Coordinate> myPiecesWithID; // this is where I am going to keep the current ships and its
                                                         // coordinates ID.
  // protected ArrayList<Integer> hitCoordsID;
  protected HashMap<Integer, Boolean> hitCoords = new HashMap<>();

  /**
   * Constructs a ship based on given coordinates
   */
  public BasicShip(HashMap<Integer, Coordinate> where, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
    this.myPiecesWithID = where;

    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where.values()) {
      myPieces.put(c, false);
    }
    hitCoords = new HashMap<>();

  }

  /**
   * @param where is the cooridinate to see if the coordinate is empty
   * @return false if the coordinate is empty true otherwise
   */

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    for (Coordinate c : myPieces.keySet()) {
      if (where.equals(c)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the entire ship is hit
   * 
   * @return true if the ship sunk false otherwise
   */
  @Override
  public boolean isSunk() {
    for (Coordinate c : myPieces.keySet()) {
      checkCoordinateInThisShip(c);
      if (!myPieces.get(c)) {
        return false;
      }
    }
    return true;
  }

  protected void checkCoordinateInThisShip(Coordinate c) {
    if (!myPieces.containsKey(c)) {
      throw new IllegalArgumentException("Coordinate is not part of the ship: " + c);
    }
  }

  /**
   * Records ship coordinate hit in myPieces.
   * Also add the hit coordinates id to the hitCoordsID array list.
   * 
   * @param where is the coordinate you want to record a hit
   */
  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
    for (HashMap.Entry<Integer, Coordinate> entry : myPiecesWithID.entrySet()) {
      if (entry.getValue().equals(where)) {
        hitCoords.put(entry.getKey(), true);
      }
    }

  }

  /**
   * @param where is the coordinate which you wan to check if there is a hit
   * @return false if not a hit; true if the coordinate has a hit
   */
  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  /**
   * Displsys coordinate content
   * 
   * @param where is the coordinate that you want display the content of
   * @return content of the coordinate
   */
  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    checkCoordinateInThisShip(where);
    // I am trying to display my coordinates. Check if I am hit, if I am hit, then
    // show *. if not show the ship letter.
    if (myShip) {
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    }
    // I am trying to displays my coordinates to the enemy. If I am hit, then return
    // the letter. If not show null.

    return enemyDisplayInfo.getInfo(where, wasHitAt(where));// true/false
    // return hitCoords.get(where);

  }

  /**
   * @return all the coordinates of a single ship as iterable
   */
  @Override
  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

  /**
   * Return the coordinate ids of the ones coordinates that are hit
   */
  @Override
  public Iterable<Integer> getHitPieceID() {
    ArrayList<Integer> idList = new ArrayList<>();
    for (Integer id: hitCoords.keySet()) {
      idList.add(id);
    }
    return idList;

  }

  /**
   * @param hitPieceID is the list of hit coordinates id of another ship
   */
  @Override
  public void updateHit(Iterable<Integer> hitPieceID) {
    for (Integer id : hitPieceID) {
      Coordinate c = myPiecesWithID.get(id);
      myPieces.put(c, true);
      hitCoords.put(id, false);

    }

  }


  @Override
  public Boolean wasHitOnMovedShip (Coordinate where){
   Integer id= null;
    for (HashMap.Entry<Integer, Coordinate> entry : myPiecesWithID.entrySet()) {
      if (entry.getValue().equals(where)) {
        id = entry.getKey();
        break;
      }
  
    }
    for (Map.Entry<Integer, Boolean> hit : hitCoords.entrySet()) {
      if (hit.getKey().equals(id)) {        
            return hit.getValue();
        }
    }
    return false;
  }
 
  

}

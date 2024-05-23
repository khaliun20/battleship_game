package edu.duke.km632.battleship;

import java.util.Iterator;

/**
 * This class represents an object that moves a ship
 */ 
public class ShipMover {
  private final Ship<Character> oldShip;
  private final Ship<Character> newShip; 

  public ShipMover( Ship<Character> oldShip, Ship<Character> newShip){
    this.oldShip = oldShip;
    this.newShip = newShip;
   }

  /**
   * Let new ship to have same hit spot as the old ship
   */
  public void updateHitStatus(){
    Iterable<Integer> hitCoordsID = oldShip.getHitPieceID();
    newShip.updateHit(hitCoordsID);
  }


  /**
   * Remove the old ship and add the new ship to the board
   */
  public String moveShip(Board<Character> myBoard){
    // add the oldShip to the myOldShips
    myBoard.addOldShip(oldShip);
    Iterable<Ship<Character>> myShips = myBoard.getMyShips();
    Iterator<Ship<Character>> iterator = myShips.iterator();
    while (iterator.hasNext()) {
        Ship<Character> ship = iterator.next();
        if (ship.equals(oldShip)) {
            iterator.remove();
            break;
        }
    }

   String validPlacementStatus =  myBoard.validatePlacement(newShip);
   if(validPlacementStatus!=null){
     myBoard.tryAddShip(oldShip);
     return validPlacementStatus;
   }
    
    this.updateHitStatus();
    return null;
  }

}

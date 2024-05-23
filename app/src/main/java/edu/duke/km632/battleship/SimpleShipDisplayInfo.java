package edu.duke.km632.battleship;

/**
 * This class implements the ship content display
 */

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private final T myData;//ship's first letter
  private final T onHit; // * 

  public SimpleShipDisplayInfo (T myData, T onHit){
    this.myData= myData;
    this.onHit = onHit;
  }

  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if(hit){
      return onHit;
    }
    return myData;
  }

}

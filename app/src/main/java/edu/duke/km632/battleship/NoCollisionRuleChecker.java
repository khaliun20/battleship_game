package edu.duke.km632.battleship;

/**
 * This class checks if we can place ship on the board
 * without having to collide with another ship
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
  protected NoCollisionRuleChecker( PlacementRuleChecker<T> next){
    super(next);
  }

  /**
   * @return true if ship can be added without collision
   */
  @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for(Coordinate c: theShip.getCoordinates()){
      if( theBoard.whatIsAtForSelf(c) != null){
        return "That placement is invalid: the ship overlaps another ship.\n";
      }
    }
    return null;

  }

}

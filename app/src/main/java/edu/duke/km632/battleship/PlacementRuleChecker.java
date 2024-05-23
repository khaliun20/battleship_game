package edu.duke.km632.battleship;

/**
 * This class sets the rules for the board
 * It is both a is-a and has-a class
 */
public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;
  // more stuff

  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  /**
   * Subclass of this abstract class implement its own rule, checkMyRule based
   * on the method checkPlacement
   */
  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

  public String checkPlacement(Ship<T> theShip, Board<T> theBoard) {
    // if we fail our own rule: stop the placement is not legal
    String errorMessage = checkMyRule(theShip, theBoard);
    if (errorMessage !=null) {
      return errorMessage;
    }
    // other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard);
    }
    // if there are no more rules, then the placement is legal
    return null;
  }

}

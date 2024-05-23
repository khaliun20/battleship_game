package edu.duke.km632.battleship;

public interface Board<T> {
  public int getWidth();
  public int getHeight();

  /**
   * Adds a ship to the board after checking boundary and collision rules
   * 
   * @return true if ship is added false if not added
   * @param toAdd is the ship to add to the Board
   */

  public String tryAddShip(Ship<T> toAdd);
  /**
   * My board's view I show to myself
   */
  
  public T whatIsAtForSelf(Coordinate where);

  /**
   * My board's view  I show to the enemy
   */
  public T whatIsAtForEnemy(Coordinate where);
  /**
   * Attack enemy board at a coordinate and record it if missed in enemyMisses
   * Think of it as this board got fired at.
   * 
   * @param coordinate to attack
   * @return ship if attack on target
   */

  public Ship<T> fireAt(Coordinate c);
  /**
   * Checks is all the ships are sunk
   * 
   * @return false is not all sunk true otherwise
   */

  public boolean allSunk();
 /**
   * @param sonarSearch is the sonar object of which you use to do the search with
   * @return the result of sonaring telling what types of ships found during sonar
   *         search is returned as one long string
   */
 
  public String  doSonarSearch(Sonar<Coordinate> sonarSearch);
 /**
   * Given a coordinate find a ship it belongs to
   * @return the ship
   */
 
  public Ship<T> getShip(Coordinate c);

  /**
   * Get all the ships on a board as iterable
   */
  public Iterable <Ship<T>> getMyShips();
  /**
   * Add the moved ship to historical board records
   */

  public void addOldShip(Ship<T> toAdd);
 /**
   * Checks if the placement is valid meaning there enough room on the board
   * for this ship
   * @param toAdd is the ship you want to add to the board
   */
 
  public String validatePlacement(Ship<T> toAdd);

}

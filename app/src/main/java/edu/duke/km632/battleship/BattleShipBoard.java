package edu.duke.km632.battleship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private final HashSet<Coordinate> enemyMisses;
  final ArrayList<Ship<T>> myOldShips;
  final T missInfo;

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  /**
   * Constructs a BattleShiBoard with the specified width
   * and height
   * 
   * @param width  is the width of the newly constructed board
   * @param height is the height of the newly constructed board
   *               throws @link IllegalArgumentException if the width or height
   *               are
   *               less than or equal to 0
   * 
   */

  public BattleShipBoard(int width, int height, PlacementRuleChecker<T> placementChecker, T missInfo) {
    if (width <= 0) {
      throw new IllegalArgumentException("Battleship Board's width must be positive");
    }
    if (height <= 0) {
      throw new IllegalArgumentException("Battleship Board's height muse be positive");
    }

    this.height = height;
    this.width = width;
    this.myShips = new ArrayList<>();
    this.placementChecker = placementChecker;
    this.enemyMisses = new HashSet<>();
    this.missInfo = missInfo;
    this.myOldShips = new ArrayList<>();

  }

  public BattleShipBoard(int width, int height, T missInfo) {
    this(width, height, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null)), missInfo);
  }

  /**
   * Checks is all the ships are sunk
   * 
   * @return false is not all sunk true otherwise
   */
  @Override
  public boolean allSunk() {
    for (Ship<T> s : myShips) {
      if (!s.isSunk()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Adds a ship to the board after checking boundary and collision rules
   * 
   * @return true if ship is added false if not added
   * @param toAdd is the ship to add to the Board
   */
  @Override
  public String tryAddShip(Ship<T> toAdd) {
    String status = this.placementChecker.checkPlacement(toAdd, this);
    if (status == null) {
      myShips.add(toAdd);
      return null;
    }
    return status;
  }

  /**
   * Checks if the placement is valid meaning there enough room on the board
   * for this ship
   * 
   * @param toAdd is the ship you want to add to the board
   */
  @Override
  public String validatePlacement(Ship<T> toAdd) {

    String status = this.placementChecker.checkPlacement(toAdd, this);
    if (status == null) {
      return null;
    }
    return status;

  }

  /**
   * Add the moved ship to historical board records
   */
  @Override
  public void addOldShip(Ship<T> toAdd) {
    myOldShips.add(toAdd);
  }

  /**
   * My board's view I show to myself
   */

  @Override
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  /**
   * What I show to the enemy
   */
  @Override
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  /**
   * Ckecks what is in the given coordinate
   * 
   * @param where is the coordinate of the shipl isSelf is true if checking for
   *              self
   * @return the ship letter if self, if enemy return the ship letter if hit, X is
   *         missed or null no attack has taken place on that coordinate
   */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {

      if (isSelf && s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
      // Show my current ship hits to the enemy
      // if (!isSelf && s.occupiesCoordinates(where)) {

      if (!isSelf && s.wasHitOnMovedShip(where)) {

        return s.getDisplayInfoAt(where, isSelf);

      }
      // Show X to the enemy if they misfired
      if (!isSelf && enemyMisses.contains(where)) {
        return missInfo;
      }

      // if the same board spot that is previously marked as hit
      // then return the s.getDisplayInfoAt
    }
    // show my oldShips hits to the enemy
    for (Ship<T> oldShip : myOldShips) {
      if (!isSelf && oldShip.occupiesCoordinates(where) && oldShip.wasHitOnMovedShip(where)) {
        return oldShip.getDisplayInfoAt(where, isSelf);
      }

    }
    return null;

  }

  /**
   * Attack enemy board at a coordinate and record it if missed in enemyMisses
   * Think of it as this board got fired at.
   * 
   * @param coordinate to attack
   * @return ship if attack on target
   */
  @Override
  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> s : myShips) {
      boolean hitShip = s.occupiesCoordinates(c);
      if (hitShip) {
        s.recordHitAt(c);
        return s;
      }

    }
    enemyMisses.add(c);
    return null;

  }

  /**
   * Given a coordinate find a ship it belongs to
   * 
   * @return the ship
   */
  @Override
  public Ship<T> getShip(Coordinate c) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        return s;
      }
    }
    return null;
  }
  /**
   * Get all the ships on a board as iterable
   */

  @Override
  public Iterable<Ship<T>> getMyShips() {
    return myShips;
  }

  /**
   * @param sonarSearch is the sonar object of which you use to do the search with
   * @return the result of sonaring telling what types of ships found during sonar
   *         search is returned as one long string
   */
  @Override
  public String doSonarSearch(Sonar<Coordinate> sonarSearch) {
    LinkedHashMap<String, Integer> inSonar = new LinkedHashMap<>();
    inSonar.put("Submarines", 0);
    inSonar.put("Destroyers", 0);
    inSonar.put("Battleships", 0);
    inSonar.put("Carriers", 0);

    for (Coordinate coord : sonarSearch.getSonarCoordinates()) {
      // find the coordinate in in myShips see what ship isit
      Ship<T> ship = this.getShip(coord);
      if (ship != null) {
        if (ship.getName() == "Battleship") {
          inSonar.put("Battleships", inSonar.get("Battleships") + 1);
        }
        if (ship.getName() == "Carrier") {
          inSonar.put("Carriers", inSonar.get("Carriers") + 1);
        }
        if (ship.getName() == "Submarine") {
          inSonar.put("Submarines", inSonar.get("Submarines") + 1);
        }
        if (ship.getName() == "Destroyer") {
          inSonar.put("Destroyers", inSonar.get("Destroyers") + 1);
        }

      }

    }

    StringBuilder sonarResult = new StringBuilder();
    for (String shipType : inSonar.keySet()) {
      int squares = inSonar.get(shipType);
      sonarResult.append(shipType).append(" occupy ").append(squares).append(" square");
      if (squares != 1) {
        sonarResult.append("s");
      }
      sonarResult.append("\n");
    }
    return sonarResult.toString();

  }

}

package edu.duke.km632.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * This class extends the TextPlayer and represents the computer playing the
 * game
 */
public class ComputerPlayer extends TextPlayer {

  private int attackCounter;
  public final ArrayList<Placement> myShips;
  public final ArrayList<String> shipNames;
  public final ArrayList<Character> attackChoices;
  public final ArrayList<Coordinate> attackCoords;
  private int shipID;

  /**
   * COnstructs the player object and initialize the fields needed to play the
   * game
   */
  public ComputerPlayer(String playerName, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> shipFactory) {
    super(playerName, theBoard, inputSource, out, shipFactory);
    this.attackCounter = 0;
    this.myShips = new ArrayList<>();
    this.shipNames = new ArrayList<>();
    this.shipID = 0;
    this.attackChoices = new ArrayList<>();
    this.attackCoords = new ArrayList<>();
    addAttackCoords();

  }

  private void addShipNames() {
    // TODO: Add all the ships here once done IN ORDER
    shipNames.add("Submarine");
    shipNames.add("Submarine");
    shipNames.add("Destroyer");
    shipNames.add("Destroyer");
    shipNames.add("Destroyer");
    shipNames.add("Battleship");
    shipNames.add("Battleship");
    shipNames.add("Battleship");
    shipNames.add("Carrier");
    shipNames.add("Carrier");
    

  }

  /**
   * Computer adds all the ships to the board
   */

  @Override
  public void doPlacementPhase() throws IOException {
    addShipNames();
    String[] placements = "A0V\nA1V\nA2V\nA3V\nA4V\nA7D\nC0U\nE1D\nD5R\nG5R".split("\n");
    for (String placementString : placements) {
      myShips.add(new Placement(placementString));
    }
    for (int i = 0; i < placements.length; i++) {
      doOnePlacement(shipNames.get(i), shipCreationFns.get(placements[i]));
    }
    doOverLoadedPlacement();
    out.println("Player " + getTextPlayer() + " is done placing the ships");

  }

  @Override
  public String doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    return null;
  }

  private void doOverLoadedPlacement() {
    for (int i = 0; i < myShips.size(); i++) {
      Ship<Character> s = shipFactory.makeNewShip(shipNames.get(i), myShips.get(i));
      theBoard.tryAddShip(s);
    }
  }
 /**
   * Computer fires at enemy's board looping through all the coordinates on board
   */
 
  @Override
  public void playOneTurnPhase(Board<Character> enemyBoard, BoardTextView enemyTextView, String enemyName)
      throws IOException {

    performAttackActionUntilValid(enemyBoard);

  }

  @Override
  public void performAttackActionUntilValid(Board<Character> enemyBoard) throws IOException {
    Ship<Character> ship = enemyBoard.fireAt(attackCoords.get(attackCounter));

    if (ship == null) {
      out.println("Player " + getTextPlayer() + " missed when trying to hit your ship");
    } else {

      out.println("Player " + getTextPlayer() + " hit your ship " + ship.getName() + " at "
          + attackCoords.get(attackCounter) + ".");

    }
    attackCounter++;

  }

  private void addAttackCoords() {

    for (int i = 0; i < theBoard.getWidth(); i++) {
      for (int j = 0; j < theBoard.getHeight(); j++) {
        Coordinate c = new Coordinate(i, j);
        attackCoords.add(c);
      }

    }

  }

}

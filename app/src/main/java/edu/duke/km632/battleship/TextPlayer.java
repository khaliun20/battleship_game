
package edu.duke.km632.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

/**
 * This class implements a Player interface and represents
 * a battleshp game player
 */
public class TextPlayer implements Player {

  protected final Board<Character> theBoard;
  private final BoardTextView view;
  private final BufferedReader inputReader;
  protected final PrintStream out;
  protected final AbstractShipFactory<Character> shipFactory;
  private final String textPlayer;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  private int sonarLeft;
  private int moveLeft;
  private String dash;

  public TextPlayer(String textPlayer, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> shipFactory) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    // Create character reader
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = shipFactory;
    this.textPlayer = textPlayer;
    shipsToPlace = new ArrayList<>();
    shipCreationFns = new HashMap<>();
    setupShipCreationMap();
    setupShipCreationList();
    sonarLeft = 3;
    moveLeft = 3;
    dash = "---------------------------------------------------------------------------\n";
  }

  /**
   * @return the name of the player as String
   */
  @Override
  public String getTextPlayer() {
    return textPlayer;
  }

  /**
   * Check if player is lost meaning all thier ship are sunk
   * 
   * @return true is if lost
   */
  @Override
  public boolean isLost() {
    if (theBoard.allSunk()) {
      return true;
    }
    return false;
  }

  @Override
  public void winGame() {
    this.out.println(dash + "Congratulations! Player " + textPlayer + ". You have won the game!\n" + dash);
  }

  @Override
  public Board<Character> getBoard() {
    return theBoard;
  }

  @Override
  public BoardTextView getView() {
    return view;
  }

  /**
   * Reads ship placement input from the players
   * 
   * @param prompt is a question as string asking the player to place a ship
   * @return placement object
   */
  @Override
  public Placement readPlacement(String prompt) throws IOException {
    out.println(dash + prompt + "\n" + dash);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  /**
   * Adds one ship on the board and displays the board after the placement
   */
  @Override
  public String doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    try {
      Placement p = readPlacement("Player " + textPlayer + " where do you want to place a " + shipName + "?");
      Ship<Character> s = createFn.apply(p);
      String errorAdding = theBoard.tryAddShip(s);
      if (errorAdding != null) {
        return errorAdding;
      }
      out.print(dash);
      out.print("Current ocean:\n");
      out.print(view.displayMyOwnBoard());
      out.print(dash);
      out.print("\n");
    } catch (IllegalArgumentException e) {
      return "That placement is invalid: it does not have the correct format.\n";
    }
    return null;
  }

  /**
   * This lets player plays all the ships on the board.
   * if the user doesn't provide valid placement, this keeps the player in a
   * while loop until they provide a valud placement.
   */
  @Override
  public void doPlacementPhase() throws IOException {
    this.out.print(dash);
    this.out.print(view.displayMyOwnBoard());
    this.out.println(dash);
    String initialPrompt = dash +
        "Player " + textPlayer + " : you are going to place the following ships (which are all\n" +
        "rectangular). For each ship, type the coordinate of the upper left\n" +
        "side of the ship, followed by either H (for horizontal) or V (for\n" +
        "vertical).  For example M4H would place a ship horizontally starting\n" +
        "at M4 and going to the right.  You have\n\n" + "2 \"Submarines\" ships that are 1x2\n"
        + "3 \"Destroyers\" that are 1x3\n" + "3 \"Battleships\" that are 1x4\n" + "2 \"Carriers\" that are 1x6\n"
        + dash;

    this.out.println(initialPrompt);
    for (String ship : shipsToPlace) {
      String addStatus = doOnePlacement(ship, shipCreationFns.get(ship));
      // as long as user don't provide valid placement, keep asking
      while (addStatus != null) {
        out.println(dash + addStatus + "\n" + dash);
        addStatus = doOnePlacement(ship, shipCreationFns.get(ship));
      }
    }

  }

  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));

  }

  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));

  }

  /**
   * Read players coordinate input
   */
  @Override
  public Coordinate readCoordinate(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Coordinate(s);
  }

    /**
     * @param coord is the coordinate which you want to check if it exist on a board
     * @return null if coord is valid and return string indicating invalidity if the
     *         coord is not valid
     */
  
  private String validateCoordinate(Coordinate coord) {
    int coordRow = coord.getRow();
    int coordCol = coord.getColumn();
    if (coordRow < 0 || coordRow > theBoard.getHeight() - 1 || coordCol < 0 || coordCol > theBoard.getWidth() - 1) {
      //      throw new IllegalArgumentException("That cooridnate is not a valid coordinate");
      return  "That cooridnate is not a valid coordinate";
    }

    return null;
  }

  /**
   * For valid attack coordinate, this method attacks the enemy board and prints
   * out to the player what kind of ship it has hit.
   * 
   * @return string "Attack complete" is returned if the attack action took place
   *         without any exceptions. If
   *         during attack exception occurs, it return a string indicating that
   *         exception occured
   */
  @Override
  public String playOneAttack(Board<Character> enemyBoard) throws IOException {
    String prompt = dash + "Player " + textPlayer + ", where would you like to attack?\n" + dash;
    try {
      Coordinate firedAt = readCoordinate(prompt);
      String coordValidarionStatus = validateCoordinate(firedAt);

      if (coordValidarionStatus != null) {
        return "That is not a valid coordinate. Please try again!\n";
      }

      enemyBoard.fireAt(firedAt);
      Character hitContent = enemyBoard.whatIsAtForEnemy(firedAt);

      if (hitContent == 's') {
        out.println(dash + "You hit a submarine!\n" + dash);
      } else if (hitContent == 'd') {
        out.println(dash + "You hit a destroyer!\n" + dash);
      } else if (hitContent == 'b') {
        out.println(dash + "You hit a battleship!\n" + dash);
      } else if (hitContent == 'c') {
        out.println(dash + "You hit a carrier!\n" + dash);
      } else {
        out.println(dash + "You missed!\n" + dash);
      }
    } catch (IllegalArgumentException e) {
      return "That coordinate is invalid: it does not have the correct format.\n";
    }
    return "Attack complete";

  }

  /**
   * As long as the player doesn't attack valid coordinate, this method keeps
   * asking the player to attack again.
   */
  @Override
  public void performAttackActionUntilValid(Board<Character> enemyBoard) throws IOException {
    String attackCoordStatus = playOneAttack(enemyBoard);
    while (!attackCoordStatus.equals("Attack complete")) {
      this.out.println(dash + attackCoordStatus + dash);
      attackCoordStatus = playOneAttack(enemyBoard);
    }
  }

  /**
   * This lets the player do a sonar search on enemy's board
   * It does it by initializing a diomand sonar search area as an object that
   * tries to
   * see if enemy has placed any ships that overlaps with this object as
   * coordinates
   */
  @Override
  public String playOneSonar(Board<Character> enemyBoard) throws IOException {
    String prompt = dash + "Player " + textPlayer + ", where would you like to place the sonar center?\n" + dash;
    try {
      Coordinate sonarCenter = readCoordinate(prompt);
      String coordValidarionStatus = validateCoordinate(sonarCenter);
      if (coordValidarionStatus != null) {
        return "That is not a valid coordinate. Please try again!\n";
      }

      Sonar<Coordinate> sonarSearch = new DiomandSonar<Coordinate>(sonarCenter);
      sonarSearch.buildSonarShape();
      String sonarResult = enemyBoard.doSonarSearch(sonarSearch);
      out.println(dash + sonarResult + dash);
    } catch (IllegalArgumentException e) {
      return "That is not a valid sonar center. Please provide the sonar center in valid format\n";
    }
    return "Sonar complete";
  }

  /**
   * This lets the player keep playing sonar action as long as it doesn't
   * provide a valid sonar action argument (sonar search center as coordinate)
   * 
   * @param enemyBoard is the enemy's board
   */

  public void performSonarActionUntilValid(Board<Character> enemyBoard) throws IOException {
    String sonarCoordStatus = playOneSonar(enemyBoard);
    while (!"Sonar complete".equals(sonarCoordStatus)){
      this.out.println(dash + sonarCoordStatus + dash);
      sonarCoordStatus = playOneSonar(enemyBoard);
    }

  }

  /**
   * Lets player pick a old ship and move the ship to new location
   */
  @Override
  public String moveShipOnce() throws IOException {
    String prompt = dash + "Player " + textPlayer + ", which ship would you like to move?\n" + dash;
    try {
      Coordinate shipCoord = readCoordinate(prompt);
      String coordValidarionStatus = validateCoordinate(shipCoord);
      if (coordValidarionStatus != null) {
        return "That is not a valid coordinate. Please try again!\n";
      }

      Ship<Character> oldShip = theBoard.getShip(shipCoord);

      if (oldShip == null) {
        return "That is not a valid ship piece. Please provide a valid coordinate\n";
      }
      String shipName = oldShip.getName();
      Placement newLoc = readPlacement("Please enter where you want to move the ship to.");
      Ship<Character> newShip = shipFactory.makeNewShip(shipName, newLoc);
      ShipMover mover = new ShipMover(oldShip, newShip);
      String moveStatus = mover.moveShip(theBoard);
      if (moveStatus != null) {
        return moveStatus;
      }
      String errorAdding = theBoard.tryAddShip(newShip);
      out.println("Ship successfully moved");

    } catch (IllegalArgumentException e) {
      return "That is not a valid ship piece or valid new placement. Please provide a valid argument\n";
    }
    return "Move complete";

  }

  /**
   * While loops that lets the keep playing the move action
   * as long as the player doesn't provide a valid move action arguments
   * (placement of the new ship and cooridnate of the old ship)
   */


  public void performMoveActionUntilValid() throws IOException {
    String moveShipStatus = moveShipOnce();
    while (!moveShipStatus.equals("Move complete")) {
      this.out.println(dash + moveShipStatus + dash);
      moveShipStatus = moveShipOnce();
    }

  }

  /**
   * Responsible for one turn player plays.
   * Displays the board and asks the player to choose among attack, move, sonar.
   * Depending on the choice, it calls on respective methods.
   * 
   * @param enemyBoard    - enemy's board
   * @param evemyTextView = enemy's board view from my perspective
   * @param enemyName     is player's name (A or B)
   */
  @Override
  public void playOneTurnPhase(Board<Character> enemyBoard, BoardTextView enemyTextView, String enemyName)
      throws IOException {
    printPlayerTurnPrompt();
    printMyAndEnemyBoard(enemyTextView, enemyName);
    char option = chooseActionPhase();
    handleOption(option, enemyBoard);
  }

  private void printPlayerTurnPrompt() {
    String prompt = dash + "Player " + textPlayer + " 's turn:\n";
    this.out.print(prompt);
  }

  private void printMyAndEnemyBoard(BoardTextView enemyTextView, String enemyName) {
    String enemyInfo = "Player " + enemyName + "'s ocean";
    this.out.print(view.displayMyBoardWithEnemyNextToIt(enemyTextView, "Your ocean", enemyInfo));
    this.out.println(dash);
  }

  private char chooseActionPhase() throws IOException {
    char option;
    do {
      option = chooseOneActionUntilValid();
    } while (!isValidOption(option));
    return option;
  }

    /**
   * As long as the player doesn't choose valid option, this method keeps asking
   * the player to make a choice again.
   * 
   * @return character representing the choice
   */
  private char chooseOneActionUntilValid() throws IOException {
    String prompt2 = "Player " + textPlayer + ", what would you like to do?\n";

    String prompt = dash +
        "Possible actions for Player " + textPlayer + ":\n\n" +
        "F Fire at a square\n" +
        "M Move a ship to another square\n" +
        "S Sonar scan\n" + dash;

    out.println(prompt);

    String actionStatus = chooseOneAction(prompt2);
    while (actionStatus.length() != 1) {
      out.println(dash + actionStatus + dash);
      actionStatus = chooseOneAction(prompt2);
    }
    return actionStatus.charAt(0);
  }


  private String chooseOneAction(String prompt) throws IOException {
    try {
      Action option = readAction(prompt);
      return option.getAction().toString();
    } catch (IllegalArgumentException e) {
      return ("That is not a valid option. Action must be either F, M, or S\n");
    }
  }

  /**
   * Read players chosen action (attack, move, sonar)
   */
  @Override
  public Action readAction(String prompt) throws IOException {
    out.println(dash + prompt + dash);
    String c = inputReader.readLine();
    return new Action(c);

  }

  private boolean isValidOption(char option) {
    if ((option == 'S' && sonarLeft == 0 && moveLeft != 0)) {
      out.println(dash + "No more sonar option left. You have used up all 3. Please pick either 'M' or 'F.'\n" + dash);
      return false;
    }
    if (option == 'M' && moveLeft == 0 && sonarLeft != 0) {
      out.println(dash + "No more move option left. You have used up all 3. Please pick either 'S' or 'F'.\n" + dash);
      return false;
    }
    if ((option == 'M' || option == 'S') && moveLeft == 0 && sonarLeft == 0) {
      out.println(dash + "No more move or sonar option left. You can only do 'F'\n"  + dash);
      return false;
    }

    return true;
  }

  private void handleOption(char option, Board<Character> enemyBoard) throws IOException {
    switch (option) {
        case 'F':
            performAttackActionUntilValid(enemyBoard);
            break;
        case 'S':
            performSonarActionUntilValid(enemyBoard);
            sonarLeft--;
            break;
        case 'M':
            performMoveActionUntilValid();
            moveLeft--;
            break;
    }
  }
}


package edu.duke.km632.battleship;

import java.io.IOException;
import java.util.function.Function;

public interface Player {
  /**
   *@return the name of the player as String
   */
  public String getTextPlayer();
  /**
   * Check if player is lost meaning all thier ship are sunk
   * 
   * @return true is if lost
   */

  public boolean isLost();
  /**
   * If wins, print it out
   */
  public void winGame();
  /**
   * @return the board
   */
  public Board<Character> getBoard();
  /**
   * @return the board view
   */
  public BoardTextView getView();
  /**
   * Reads ship placement input from the players
   * 
   * @param prompt is a question as string asking the player to place a ship
   * @return placement object
   */

  public Placement readPlacement(String prompt)throws IOException;
  /**
   * Adds one ship on the board and displays the board after the placement
   */

  public String doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn)throws IOException;
  /**
   * This lets player plays all the ships on the board. 
   * if the user doesn't provide valid placement, this keeps the player in a 
   * while loop until they provide a valud placement.
   */

  public void doPlacementPhase()throws IOException;
    /**
   * Read players coordinate input
   */ 

  public Coordinate readCoordinate(String prompt)throws IOException;
  /**
   * Read players chosen action (attack, move, sonar)
   */

  public Action readAction(String prompt)throws IOException;
  /**
   * For valid attack coordinate, this method attacks the enemy board and prints
   * out to the player what kind of ship it has hit.
   * 
   * @return string "Attack complete" is returned if the attack action took place
   *         without any exceptions. If
   *         during attack exception occurs, it return a string indicating that
   *         exception occured
   */

  public String playOneAttack(Board<Character> enemyBoard)throws IOException;
  /**
   * As long as the player doesn't attack valid coordinate, this method keeps
   * asking the player to attack again.
   */

  public void performAttackActionUntilValid(Board<Character> enemyBoard)throws IOException;
  /**
   * This lets the player do a sonar search on enemy's board
   * It does it by initializing a diomand sonar search area as an object that tries to 
   * see if enemy has placed any ships that overlaps with this object as coordinates
   */
 
  public String playOneSonar(Board<Character> enemyBoard)throws IOException;
 /**
   * Lets player pick a old ship and move the ship to new location
   */
 
  public String moveShipOnce()throws IOException;
 /**
   * Responsible for one turn player plays. 
   * Displays the board and asks the player to choose among attack, move, sonar.
   * Depending on the choice, it calls on respective methods.
   * @param enemyBoard - enemy's board
   * @param evemyTextView = enemy's board view from my perspective
   * @param enemyName is player's name (A or B)
   */
 
  public void playOneTurnPhase(Board<Character> enemyBoard, BoardTextView enemyTextView, String enemyName)throws IOException;

}

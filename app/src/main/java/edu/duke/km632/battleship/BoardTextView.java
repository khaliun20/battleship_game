package edu.duke.km632.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 */

public class BoardTextView {
  /**
   * Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   */

  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  public String displayMyOwnBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
  }

  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }

  /**
   * Setups the board with its row and column titles
   * 
   * @return string representation of the baord
   */
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
    StringBuilder ans = new StringBuilder("");
    String letter = "A";
    String sep = "|";
    ans.append(makeHeader());
    for (int row = 0; row < toDisplay.getHeight(); row++) {
      ans.append(letter);
      // for loop if there is ship show th ship if not then show space

      ans.append(" ");
      for (int col = 0; col < toDisplay.getWidth(); col++) {
        if (col != 0) {
          ans.append(sep);
        }
        Character ship = getSquareFn.apply(new Coordinate(row, col));
        if (ship != null) {
          ans.append(ship);
        } else {
          ans.append(" ");
        }
      }

      ans.append(" ");
      ans.append(letter);
      ans.append("\n");
      letter = String.valueOf((char) (letter.charAt(0) + 1));
    }

    ans.append(makeHeader());

    return ans.toString();
  }

  String makeHeader() {
    StringBuilder ans = new StringBuilder("  ");
    String sep = "";
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";

    }
    ans.append("\n");

    return ans.toString();
  }
  /**
   * This method is used to display my board along with what I know about the enemy's board. 
   * @param enemyView is the enemy's board text view 
   * @param myHeader is text saying "You board: " 
   * @param enemyHeader is text saying "Player B's board: "
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    StringBuilder buildStr = new StringBuilder();
    int initialOffset = 5;
    int headerOffset = (this.toDisplay.getWidth() * 2) + 22;
    int boardOffset = (this.toDisplay.getWidth() * 2) + 18;
    buildStr = addSpace(buildStr, initialOffset);
    buildStr.append(myHeader);
    buildStr = addSpace(buildStr, headerOffset);
    buildStr.append(enemyHeader);
    buildStr.append("\n");
    String[] myLines = this.displayMyOwnBoard().split("\n");
    // each board has its own view and the enemey view of my board
    // enemyView.displayEnemyBoard() is the representation of the enemy's board from my perspective
    String[] enemyLines = enemyView.displayEnemyBoard().split("\n");

    for (int i = 0; i < myLines.length; i++) {
      buildStr.append(myLines[i]);
      buildStr = addSpace(buildStr, boardOffset);
      if (i == 0) {
        buildStr.append("  ");
      }

      if (i == myLines.length - 1) {
        buildStr.append("  ");
      }
      buildStr.append(enemyLines[i]);

      buildStr.append("\n");
    }

    return buildStr.toString();
  }

  private StringBuilder addSpace(StringBuilder builder, int space) {
    for (int i = 0; i < space; i++) {
      builder.append(' ');
    }
    return builder;

  }
}

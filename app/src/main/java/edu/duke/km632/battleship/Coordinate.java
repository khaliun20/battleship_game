package edu.duke.km632.battleship;

public class Coordinate {
  private final int row;
  private final int column;

  /**
   * Constructs coordinate plane
   * 
   * @param row is the row and column is the column
   */

  public Coordinate(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Constructs a coordinate point
   * 
   * @param descr is the string representation of the coordiante Ex: A2
   * @throws IllegalArgumentException is the string descr is not 2 characters
   *                                  where first char is between A-Z and second
   *                                  char is between 0-9
   */
  public Coordinate(String descr) {
    if (descr.length() != 2) {
      throw new IllegalArgumentException("Coordinate must be a value that is 1 letter and 1 number");
    }
    char tempRowLetter = descr.toUpperCase().charAt(0); // "A", "B"
    char tempColLetter = descr.charAt(1);// "1", "2"
    int rowLetter = tempRowLetter - 'A';// B-A = 1
    int colLetter = tempColLetter - '0'; // "1" -> 1

    if (tempRowLetter < 'A' || tempRowLetter > 'Z') {
      throw new IllegalArgumentException(
          "Row must be a letter between A-Z. You provided  " + tempRowLetter + ". Provide corret value");
    }

    if (tempColLetter < '0' || tempColLetter > '9') {
      throw new IllegalArgumentException(
          "Column must be a digit between 0-9. You provided " + tempColLetter + ". Provde correct value");
    }
    this.column = colLetter;
    this.row = rowLetter;

  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  @Override
  public String toString() {
    return "(" + row + "," + column + ")";
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}

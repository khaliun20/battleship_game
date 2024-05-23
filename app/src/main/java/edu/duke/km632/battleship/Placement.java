package edu.duke.km632.battleship;

/**
 * This class is used to place a ship on the board
 */
public class Placement {
  final Coordinate where;
  final char orientation;

  /**
   * @param where is the starting point of the ship( top left tip of the ship);
   *              orientation is either 'H'(horizontal) or 'V' (vertical). This
   *              indicates the orientation of the ship on the board
   */
  public Placement(Coordinate where, char orientation) {
    this.where = where;
    this.orientation = orientation;

  }
  /** 
   *Constructs placements based on string description 
   * @param descr is the string describing the ship head and its orientation 
   * ex:descr = "A4H" means horizontal ship with a head at A4
     */
  public Placement(String descr) {
    if(descr.length()!=3){
      throw new IllegalArgumentException("Placement must consist of 3 letters. You provided "+ descr+ ". Provide correct value");
    }
    Coordinate c = new Coordinate(descr.substring(0, 2));
    char o = descr.toUpperCase().charAt(2);
    this.where = c;
    this.orientation = o;
   
  
  }

  public Coordinate getWhere() {
    return where;
  }

  public char getOrientation() {
    return orientation;
  }

  @Override
  public String toString() {
    return "(" + where.getRow() + "," + where.getColumn() + "," + orientation + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement p = (Placement) o;
      return where.getRow() == p.where.getRow() && where.getColumn() == p.where.getColumn()
          && orientation == p.orientation;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

}

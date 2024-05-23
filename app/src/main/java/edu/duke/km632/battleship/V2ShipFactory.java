package edu.duke.km632.battleship;

/**
 * This class is used to make new ships
 * for version2 of the battleship game
 */

public class V2ShipFactory implements AbstractShipFactory {

  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {

    Character orientation = where.getOrientation();
    if (orientation != 'H' && orientation != 'V') {
      throw new IllegalArgumentException("Invalid Orientation provided.It must be either H or V");
    }
    int width = w;
    int height = h;
    if (orientation == 'H') {
      width = h;
      height = w;
    }
    RectangleShip<Character> newShip = new RectangleShip<Character>(name, where.getWhere(), width, height, letter, '*');
    return newShip;

  }

  protected Ship<Character> createTZShapedShip(Placement where, char letter, String name) {
    Character orientation = where.getOrientation();
    if (orientation != 'U' && orientation != 'D' && orientation != 'R' && orientation != 'L') {
      throw new IllegalArgumentException(
          "Invalid Orientation provided. It must be one of the following" + ": R, L, U, D");
    }

    Ship<Character> newShip;
    if (name == "Battleship") {
      newShip = new TShapedShip<Character>(name, where.getWhere(), orientation, letter, '*');
    } else {
      newShip = new ZShapedShip<Character>(name, where.getWhere(), orientation, letter, '*');
    }

    return newShip;

  }
  /**
   * Given a ship name and placement, create a ship
   */
  
  @Override
  public Ship<Character> makeNewShip(String shipName, Placement where) {

    Ship<Character> newShip = null;
    switch (shipName) {
      case "Battleship":
        newShip = makeBattleship(where);
        break;
      case "Carrier":
        newShip = makeCarrier(where);
        break;
      case "Destroyer":
        newShip = makeDestroyer(where);
        break;
      case "Submarine":
        newShip = makeSubmarine(where);
        break;
    }
    return newShip;
  }
/**
 package edu.duke.km632.battleship;

public class V2ShipFactory {

}
  * These 4 makeXXX implements making of different kinds of ships.
   * 
   * @param where is the Placement containing the orientation and the Coordinate
   *              fo the top left tip of the ship
   */
 
  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createShip(where, 1, 2, 's', "Submarine");

  }

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createTZShapedShip(where, 'b', "Battleship");
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createTZShapedShip(where, 'c', "Carrier");
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createShip(where, 1, 3, 'd', "Destroyer");
  }

}

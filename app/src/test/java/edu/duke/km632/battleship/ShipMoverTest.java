package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ShipMoverTest {

  @Test
  public void test_ShipMoverConst() {

    Board<Character> b1 = new BattleShipBoard<Character>(5, 5, 'X');
    TShapedShip<Character> shipL3 = new TShapedShip<Character>("Battleship", new Coordinate(3, 1), 'U', 'b', '*');
    b1.tryAddShip(shipL3);

    TShapedShip<Character> shipL1 = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'L', 'b', '*');
    b1.tryAddShip(shipL1);

    /*
     * "  0|1|2|3|4 \n" +
     * "A  |b| | |  A\n" +
     * "B b|b| | |  B\n" +
     * "C  |b| | |  C\n" +
     * "D  | |b| |  D\n" +
     * "E  |b|b|b|  E\n" +
     * "  0|1|2|3\n";
     */

    b1.fireAt(new Coordinate("a1"));
    /*
     * "  0|1|2|3|4 \n" +
     * "A  |*| | |  A\n" +
     * "B b|b| | |  B\n" +
     * "C  |b| | |  C\n" +
     * "D  | |b| |  D\n" +
     * "E  |b|b|b|  E\n" +
     * "  0|1|2|3\n";
     */

    TShapedShip<Character> shipL2 = new TShapedShip<Character>("Battleship", new Coordinate(0, 2), 'R', 'b', '*');

    ShipMover mover = new ShipMover(shipL1, shipL2);

    mover.moveShip(b1);
    b1.tryAddShip(shipL2);
    assertEquals(Character.valueOf(shipL2.getDisplayInfoAt(new Coordinate("a2"), true)), 'b');
    assertEquals(Character.valueOf(shipL2.getDisplayInfoAt(new Coordinate("b2"), true)), 'b');
    assertEquals(Character.valueOf(shipL2.getDisplayInfoAt(new Coordinate("c2"), true)), '*');
    assertEquals(Character.valueOf(shipL2.getDisplayInfoAt(new Coordinate("b3"), true)), 'b');
    // tests if board has successfully removed oldl ship
    Iterable<Ship<Character>> myShips = b1.getMyShips();
    ArrayList<Ship<Character>> expectedMyShips = new ArrayList<>();
    expectedMyShips.add(shipL3);
    expectedMyShips.add(shipL2);
    Iterable<Ship<Character>> myShipsIt = arrayToIterable(expectedMyShips);
    assertTrue(iterablesEqual(myShipsIt, myShips));
    ArrayList<Ship<Character>> expectedMyShips1 = new ArrayList<>();
    expectedMyShips1.add(shipL1);
    expectedMyShips1.add(shipL2);
    Iterable<Ship<Character>> myShipsIt1 = arrayToIterable(expectedMyShips1);
    assertFalse(iterablesEqual(myShipsIt1, myShips));

  }

  private Iterable<Ship<Character>> arrayToIterable(ArrayList<Ship<Character>> newList) {
    return newList;
  }

  private <T> boolean iterablesEqual(Iterable<T> iterable1, Iterable<T> iterable2) {
    Iterator<T> iterator1 = iterable1.iterator();
    Iterator<T> iterator2 = iterable2.iterator();

    while (iterator1.hasNext() && iterator2.hasNext()) {
      T element1 = iterator1.next();
      T element2 = iterator2.next();

      if (!element1.equals(element2)) {
        return false; // Elements at corresponding positions are not equal
      }
    }

    // If one iterable has more elements than the other, they are not equal
    return !iterator1.hasNext() && !iterator2.hasNext();
  }

  @Test
  public void test_ShipMoverConstError() {

    Board<Character> b1 = new BattleShipBoard<Character>(5, 5, 'X');
    TShapedShip<Character> shipL1 = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'L', 'b', '*');
    TShapedShip<Character> shipL2 = new TShapedShip<Character>("Battleship", new Coordinate(0, 2), 'R', 'b', '*');
    ShipMover mover = new ShipMover(shipL1, shipL2);
    assertNull(mover.moveShip(b1));
    b1.tryAddShip(shipL2);
  }
@Test
public void validPlacementStatus(){
   Board<Character> b1 = new BattleShipBoard<Character>(5, 5, 'X');
    TShapedShip<Character> shipL1 = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'L', 'b', '*');
    b1.tryAddShip(shipL1);
    TShapedShip<Character> shipL2 = new TShapedShip<Character>("Battleship", new Coordinate(4, 7), 'R', 'b', '*');
    ShipMover mover = new ShipMover(shipL1, shipL2);
    assertEquals(mover.moveShip(b1),  "That placement is invalid: the ship goes off the right of the board.\n");

 

}
}

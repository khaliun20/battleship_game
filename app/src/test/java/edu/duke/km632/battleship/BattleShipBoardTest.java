package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());

  }

  @Test
  public void test_invalid_board_dimentions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 10, 'X'));

  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b1, T[][] expect) {
    for (int i = 0; i < b1.getHeight(); i++) {
      for (int j = 0; j < b1.getWidth(); j++) {
        assertEquals(expect[i][j], b1.whatIsAtForSelf(new Coordinate(i, j)));
      }

    }
  }

  // places "s" everywhere on the board
  private void test_whatIsit_tryAddShip(int h, int w, boolean empty) {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    Character[][] expectation = new Character[h][w];
    for (int i = 0; i < b1.getHeight(); i++) {
      for (int j = 0; j < b1.getWidth(); j++) {

        if (empty == true) {

          expectation[i][j] = null;
        } else {
          b1.tryAddShip(new RectangleShip<Character>(new Coordinate(i, j), 's', '*'));
          expectation[i][j] = 's';
        }
      }
    }
    checkWhatIsAtBoard(b1, expectation);
  }

  @Test
  public void test_empty_filled_board() {

    test_whatIsit_tryAddShip(2, 2, true); // test empty board
    test_whatIsit_tryAddShip(3, 3, false); // test nonempty board
    test_whatIsit_tryAddShip(4, 5, false);
    test_whatIsit_tryAddShip(2, 10, true);

  }

  @Test
  public void test_addingShipWithRules() {

    Board<Character> b1 = new BattleShipBoard<Character>(10, 10, 'X');// checkers are initialized in the construction of
                                                                      // the
    // board
    AbstractShipFactory<Character> f = new V1ShipFactory();

    // Place ships before testing
    Ship<Character> ship1 = f.makeSubmarine(new Placement("A2V"));
    Ship<Character> ship2 = f.makeSubmarine(new Placement("D2V"));
    Ship<Character> ship3 = f.makeCarrier(new Placement("B4H"));
    Ship<Character> ship4 = f.makeDestroyer(new Placement("C8V"));

    String added1 = b1.tryAddShip(ship1);
    String added2 = b1.tryAddShip(ship2);
    String added3 = b1.tryAddShip(ship3);
    String added4 = b1.tryAddShip(ship4);

    // Place ships causing collision
    Ship<Character> ship5 = f.makeSubmarine(new Placement("E8V"));
    Ship<Character> ship6 = f.makeSubmarine(new Placement("D2H"));

    String add5 = b1.tryAddShip(ship5);
    String add6 = b1.tryAddShip(ship6);

    assertEquals(add5, "That placement is invalid: the ship overlaps another ship.\n");
    assertEquals(add6, "That placement is invalid: the ship overlaps another ship.\n");

  }

  @Test
  public void test_fireAt() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 10, 'X');// checkers are initialized in the construction of
                                                                      // the
    AbstractShipFactory<Character> f = new V1ShipFactory();

    Ship<Character> ship1 = f.makeSubmarine(new Placement("A2V"));
    Ship<Character> ship2 = f.makeSubmarine(new Placement("D2V"));
    Ship<Character> ship3 = f.makeCarrier(new Placement("B4H"));
    Ship<Character> ship4 = f.makeDestroyer(new Placement("C8V"));
    String status1 = b1.tryAddShip(ship1);
    String status2 = b1.tryAddShip(ship2);
    String status3 = b1.tryAddShip(ship3);
    String status4 = b1.tryAddShip(ship4);

    Ship<Character> isAttacked1 = b1.fireAt(new Coordinate("A2"));
    assertSame(isAttacked1, ship1);
    assertNotSame(isAttacked1, ship2);
    assertNotSame(isAttacked1, ship3);
    assertNotSame(isAttacked1, ship4);
    assertFalse(isAttacked1.isSunk());
    Ship<Character> isAttacked2 = b1.fireAt(new Coordinate("B2"));
    assertTrue(isAttacked2.isSunk());
    assertNull(b1.fireAt(new Coordinate("B3")));
    assertNull(b1.fireAt(new Coordinate("C2")));

    Ship<Character> isAttacked3 = b1.fireAt(new Coordinate("C8"));
    assertSame(isAttacked3, ship4);
    assertNotSame(isAttacked3, ship3);
    assertFalse(isAttacked3.isSunk());
    Ship<Character> isAttacked4 = b1.fireAt(new Coordinate("D8"));
    assertSame(isAttacked4, ship4);
    assertFalse(isAttacked4.isSunk());
    Ship<Character> isAttacked5 = b1.fireAt(new Coordinate("E8"));
    assertSame(isAttacked5, ship4);
    assertTrue(isAttacked5.isSunk());

  }

  @Test
  public void test_allSunk() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 10, 'X');// checkers are initialized in the construction of
                                                                      // the
    AbstractShipFactory<Character> f = new V1ShipFactory();

    Ship<Character> ship1 = f.makeSubmarine(new Placement("A2V"));
    Ship<Character> ship2 = f.makeSubmarine(new Placement("D2V"));
    Ship<Character> ship4 = f.makeDestroyer(new Placement("C8V"));
    String status1 = b1.tryAddShip(ship1);
    String status2 = b1.tryAddShip(ship2);
    String status4 = b1.tryAddShip(ship4);

    Ship<Character> isAttacked1 = b1.fireAt(new Coordinate("A2"));
    Ship<Character> isAttacked2 = b1.fireAt(new Coordinate("B2"));
    Ship<Character> isAttacked3 = b1.fireAt(new Coordinate("D2"));
    Ship<Character> isAttacked4 = b1.fireAt(new Coordinate("E2"));
    assertFalse(b1.allSunk());
    Ship<Character> isAttacked5 = b1.fireAt(new Coordinate("C8"));
    Ship<Character> isAttacked6 = b1.fireAt(new Coordinate("d8"));
    Ship<Character> isAttacked7 = b1.fireAt(new Coordinate("e8"));
    assertTrue(b1.allSunk());

  }

  @Test
  public void test_doSonarSearch() {
    AbstractShipFactory<Character> f = new V1ShipFactory();
    Sonar<Coordinate> testSonar = new DiomandSonar<Coordinate>(new Coordinate(0, 3));
    testSonar.buildSonarShape();
    String testResult = "Submarines occupy 1 square\n" + "Destroyers occupy 2 squares\n"
        + "Battleships occupy 4 squares\n" + "Carriers occupy 3 squares\n";

    Board<Character> b = new BattleShipBoard<>(10, 10, 'X');
    Ship<Character> ship1 = f.makeSubmarine(new Placement("A6H"));
    Ship<Character> ship2 = f.makeDestroyer(new Placement("A1V"));
    Ship<Character> ship3 = f.makeCarrier(new Placement("A2V"));
    Ship<Character> ship4 = f.makeBattleship(new Placement("a3v"));
    b.tryAddShip(ship1);
    b.tryAddShip(ship2);
    b.tryAddShip(ship3);
    b.tryAddShip(ship4);

    Board<Character> b1 = new BattleShipBoard<>(10, 10, 'X');
    Ship<Character> ship5 = f.makeSubmarine(new Placement("H0V"));
    Ship<Character> ship6 = f.makeDestroyer(new Placement("h1V"));
    Ship<Character> ship7 = f.makeCarrier(new Placement("e2V"));
    Ship<Character> ship8 = f.makeBattleship(new Placement("g3v"));
    b1.tryAddShip(ship5);
    b1.tryAddShip(ship6);
    b1.tryAddShip(ship7);
    b1.tryAddShip(ship8);

    // b1 attacks b
    b.fireAt(new Coordinate("a0"));
    b.fireAt(new Coordinate("a1"));
    b.fireAt(new Coordinate("a2"));
    // b attacks b1
    b1.fireAt(new Coordinate("e2"));
    b1.fireAt(new Coordinate("d2"));
    b1.fireAt(new Coordinate("f4"));
    // b1 is doing Sonar on b's board when there is no hit ship
    String result = b.doSonarSearch(testSonar);
    assertEquals(result, testResult);

    // b1 is doing Sonar on b's board after hitting one of the ships
    assertEquals(b.whatIsAtForEnemy(new Coordinate("a0")), 'X');
    assertEquals(b.whatIsAtForEnemy(new Coordinate("a1")), 'd');
    b.fireAt(new Coordinate("b1"));
    String result1 = b.doSonarSearch(testSonar);
    assertEquals(testResult, result1);
  }

  @Test
  public void test_doSonarSearchAfterMove() {
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Sonar<Coordinate> testSonar = new DiomandSonar<Coordinate>(new Coordinate("i0"));
    testSonar.buildSonarShape();
    String testResult = "Submarines occupy 2 squares\n" + "Destroyers occupy 0 squares\n"
        + "Battleships occupy 0 squares\n" + "Carriers occupy 0 squares\n";

    Board<Character> b = new BattleShipBoard<>(10, 20, 'X');
    Ship<Character> ship1 = f.makeSubmarine(new Placement("A0v"));
    Ship<Character> ship2 = f.makeDestroyer(new Placement("A1V"));
    Ship<Character> ship3 = f.makeCarrier(new Placement("d3d"));
    Ship<Character> ship4 = f.makeBattleship(new Placement("f3l"));
    b.tryAddShip(ship1);
    b.tryAddShip(ship2);
    b.tryAddShip(ship3);
    b.tryAddShip(ship4);

    Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
    Ship<Character> ship5 = f.makeSubmarine(new Placement("a3v"));
    Ship<Character> ship6 = f.makeDestroyer(new Placement("a4v"));
    Ship<Character> ship7 = f.makeCarrier(new Placement("d3d"));
    Ship<Character> ship8 = f.makeBattleship(new Placement("f3l"));
    b1.tryAddShip(ship5);
    b1.tryAddShip(ship6);
    b1.tryAddShip(ship7);
    b1.tryAddShip(ship8);

    // b1 attacks b
    b.fireAt(new Coordinate("a0"));

    // b attacks b1
    b1.fireAt(new Coordinate("a0"));
    Ship<Character> ship9 = f.makeSubmarine(new Placement("i0h"));

    ShipMover mover = new ShipMover(ship1, ship9);
    mover.moveShip(b);
    b.tryAddShip(ship9);
    b.fireAt(new Coordinate("i0"));

    // b1 is doing Sonar on b's board when there is no hit ship
    String result = b.doSonarSearch(testSonar);
    assertEquals(result, testResult);

  }

  @Test
  public void test_getShip() {
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Board<Character> b = new BattleShipBoard<>(10, 20, 'X');
    Ship<Character> ship1 = f.makeSubmarine(new Placement("A6H"));
    Ship<Character> ship2 = f.makeDestroyer(new Placement("A1V"));
    Ship<Character> ship3 = f.makeCarrier(new Placement("D5D"));
    Ship<Character> ship4 = f.makeBattleship(new Placement("M3L"));
    b.tryAddShip(ship1);
    b.tryAddShip(ship2);
    b.tryAddShip(ship3);
    b.tryAddShip(ship4);
    assertNull(b.getShip(new Coordinate("b6")));
    assertEquals(b.getShip(new Coordinate("A7")), ship1);
    assertEquals(b.getShip(new Coordinate("d5")), ship3);
    assertNull(b.getShip(new Coordinate("d6")));
    assertEquals(b.getShip(new Coordinate("e5")), ship3);
    assertEquals(b.getShip(new Coordinate("N3")), ship4);
    assertNull(b.getShip(new Coordinate("m3")));
    assertNull(b.getShip(new Coordinate("A2")));
  }

  @Test
  public void test_getShips() {
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Board<Character> b = new BattleShipBoard<>(10, 20, 'X');
    Ship<Character> ship1 = f.makeSubmarine(new Placement("A6H"));
    Ship<Character> ship2 = f.makeDestroyer(new Placement("A1V"));
    Ship<Character> ship3 = f.makeCarrier(new Placement("D5D"));
    Ship<Character> ship4 = f.makeBattleship(new Placement("M3L"));
    b.tryAddShip(ship1);
    b.tryAddShip(ship2);
    b.tryAddShip(ship3);
    b.tryAddShip(ship4);
    ArrayList<Ship<Character>> newList = new ArrayList<>();
    newList.add(ship1);
    newList.add(ship2);
    newList.add(ship3);
    newList.add(ship4);
    Iterable<Ship<Character>> expectedAll = arrayToIterable(newList);
    Iterable<Ship<Character>> actualAll = b.getMyShips();

    assertTrue(iterablesEqual(expectedAll, actualAll));

  }

  @Test
  public void test_getShipsNotEqal() {
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Board<Character> b = new BattleShipBoard<>(10, 20, 'X');
    Ship<Character> ship1 = f.makeSubmarine(new Placement("A6H"));
    Ship<Character> ship2 = f.makeDestroyer(new Placement("A1V"));
    Ship<Character> ship3 = f.makeCarrier(new Placement("D5D"));
    Ship<Character> ship4 = f.makeBattleship(new Placement("M3L"));
    b.tryAddShip(ship1);
    b.tryAddShip(ship3);
    b.tryAddShip(ship4);
    ArrayList<Ship<Character>> newList = new ArrayList<>();
    newList.add(ship1);
    newList.add(ship2);
    newList.add(ship3);
    Iterable<Ship<Character>> expectedAll = arrayToIterable(newList);
    Iterable<Ship<Character>> actualAll = b.getMyShips();

    assertFalse(iterablesEqual(expectedAll, actualAll));

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
  public void test_validatePlacement() {
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Board<Character> b = new BattleShipBoard<>(1, 2, 'X');
    Ship<Character> ship1 = f.makeSubmarine(new Placement("A6H"));
    assertEquals(b.validatePlacement(ship1), "That placement is invalid: the ship goes off the right of the board.\n");
  }

  @Test
  public void test_whatIsAtOnOldShips() {
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Board<Character> b = new BattleShipBoard<>(10, 20, 'X');
    Ship<Character> ship1 = f.makeSubmarine(new Placement("A0V"));
    b.tryAddShip(ship1);
    b.fireAt(new Coordinate("a0"));
    Ship<Character> ship2 = f.makeSubmarine(new Placement("A1V"));
    ShipMover mover = new ShipMover(ship1, ship2);
    mover.moveShip(b);
    b.tryAddShip(ship2);
    Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
    Ship<Character> ship3 = f.makeSubmarine(new Placement("d3h"));
    b1.tryAddShip(ship3);
    char expected = 's';
    Character expect = expected;
    assertEquals(b.whatIsAtForEnemy(new Coordinate("a0")), expect);
    assertNotEquals(b.whatIsAtForEnemy(new Coordinate("a1")), expect);

    Ship<Character> ship4 = f.makeSubmarine(new Placement("A2V"));
    ShipMover mover1 = new ShipMover(ship2, ship4);
    mover1.moveShip(b);
    b.tryAddShip(ship4);
    b.fireAt(new Coordinate("b2"));
    assertEquals(b.whatIsAtForEnemy(new Coordinate("b2")), expect);
    assertEquals(b.whatIsAtForEnemy(new Coordinate("a0")), expect);
    assertNull(b.whatIsAtForEnemy(new Coordinate("a1")));
    assertNull(b.whatIsAtForEnemy(new Coordinate("b0")));
    assertNull(b.whatIsAtForEnemy(new Coordinate("b1")));
    assertNull(b.whatIsAtForEnemy(new Coordinate("a2")));

    Ship<Character> ship5 = f.makeSubmarine(new Placement("A5V"));
    ShipMover mover2 = new ShipMover(ship4, ship5);
    mover2.moveShip(b);
    b.tryAddShip(ship5);
    assertEquals(b.whatIsAtForEnemy(new Coordinate("b2")), expect);
    assertEquals(b.whatIsAtForEnemy(new Coordinate("a0")), expect);
    assertNull(b.whatIsAtForEnemy(new Coordinate("a1")));
    assertNull(b.whatIsAtForEnemy(new Coordinate("b0")));
    assertNull(b.whatIsAtForEnemy(new Coordinate("b1")));
    assertNull(b.whatIsAtForEnemy(new Coordinate("a2")));
    char expected1 = '*';
    Character expectStar = expected1;
    assertEquals(b.whatIsAtForSelf(new Coordinate("a5")), expectStar);
    assertEquals(b.whatIsAtForSelf(new Coordinate("b5")), expectStar);

  }

}

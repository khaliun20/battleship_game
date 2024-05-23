package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {

  String top = "That placement is invalid: the ship goes off the top of the board.\n";
  String bottom = "That placement is invalid: the ship goes off the bottom of the board.\n";
  String left = "That placement is invalid: the ship goes off the left of the board.\n";
  String right = "That placement is invalid: the ship goes off the right of the board.\n";

  InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
  Board<Character> b1 = new BattleShipBoard<Character>(5, 5, checker, 'X');
  AbstractShipFactory<Character> f = new V1ShipFactory();

  @Test
  public void test_shipOutOfBound() {

    Ship<Character> ship1 = f.makeCarrier(new Placement("A2V"));
    String error1 = checker.checkPlacement(ship1, b1);
    assertEquals(bottom, error1);

    Ship<Character> ship2 = f.makeCarrier(new Placement("A4H"));
    String error2 = checker.checkPlacement(ship2, b1);
    assertEquals(right, error2);

    Ship<Character> ship3 = f.makeCarrier(new Placement((new Coordinate(-1,1)),'H'));
    String error3 = checker.checkPlacement(ship3, b1);
    assertEquals(top, error3);

    Ship<Character> ship4 = f.makeCarrier(new Placement((new Coordinate(1,-1)),'V'));
    String error4 = checker.checkPlacement(ship4, b1);
    assertEquals(left, error4);

  }

  @Test
  public void test_shipInBound() {

    Ship<Character> ship1 = f.makeSubmarine(new Placement("A2V"));

    assertNull(checker.checkPlacement(ship1, b1) );// submarine is 1x2
    }
}

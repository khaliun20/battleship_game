package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {

  NoCollisionRuleChecker<Character> childChecker = new NoCollisionRuleChecker<>(null);
  InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(childChecker);

  Board<Character> b1 = new BattleShipBoard<Character>(10, 10, checker, 'X');
  AbstractShipFactory<Character> f = new V1ShipFactory();

  @Test
  public void test_checkCollision() {

    // Ships placed without collision
    Ship<Character> ship1 = f.makeSubmarine(new Placement("A2V"));
    Ship<Character> ship2 = f.makeSubmarine(new Placement("D2V"));
    Ship<Character> ship3 = f.makeCarrier(new Placement("B4H"));
    Ship<Character> ship4 = f.makeDestroyer(new Placement("C8V"));

    String added1 = b1.tryAddShip(ship1);
    String added2 = b1.tryAddShip(ship2);
    String added3 = b1.tryAddShip(ship3);
    String added4 = b1.tryAddShip(ship4);

    // Place ships to collide
    Ship<Character> ship5 = f.makeSubmarine(new Placement("E8V"));
    Ship<Character> ship6 = f.makeSubmarine(new Placement("D2H"));
    Ship<Character> ship7 = f.makeSubmarine(new Placement("B9V"));
    Ship<Character> ship8 = f.makeSubmarine(new Placement("A6V"));

    String error =   "That placement is invalid: the ship overlaps another ship.\n";
    String error1 = checker.checkPlacement(ship5, b1);
    String error2 = checker.checkPlacement(ship6, b1);
    String error3 = checker.checkPlacement(ship7, b1);
    String error4 = checker.checkPlacement(ship8, b1);

    assertEquals(error, error1);
    assertEquals(error, error2);
    assertEquals(error, error3);
    assertEquals(error, error4);

    // Place ships without collision
    Ship<Character> ship9 = f.makeSubmarine(new Placement("G8V"));
    Ship<Character> ship10 = f.makeSubmarine(new Placement("A3V"));
    Ship<Character> ship11 = f.makeSubmarine(new Placement("C5V"));
    Ship<Character> ship12 = f.makeSubmarine(new Placement("D1V"));

    assertNull(checker.checkPlacement(ship9, b1));
    assertNull(checker.checkPlacement(ship10, b1));
    assertNull(checker.checkPlacement(ship11, b1));
    assertNull(checker.checkPlacement(ship12, b1));

  }

}

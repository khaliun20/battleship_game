package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_placement_const() {
    Coordinate c1 = new Coordinate("B3");
    Coordinate c2 = new Coordinate("B3");
    Placement pls1 = new Placement(c1, 'v');
    Placement pls2 = new Placement(c2, 'V');
    assertEquals(c1, c2);
    assertEquals(pls1.getWhere(), c1);
    assertEquals(pls1.getOrientation(), 'v');
    assertEquals(pls2.getOrientation(), 'V');

  }

  @Test
  public void test_placement_string() {
    Placement pls1 = new Placement("B3V");
    Placement pls2 = new Placement("B3v");
    Placement pls3 = new Placement("C3V");
    BattleShipBoard c1 = new BattleShipBoard(10, 10, 'X');
    assertEquals(pls1, pls2);
    assertNotEquals(pls1, pls3);
    assertNotEquals(c1, ("13,13"));
    

  }

  @Test
  public void test_hash_tostring() {
    Placement pls1 = new Placement("C3v");
    Placement pls2 = new Placement("C3V");
    Placement pls3 = new Placement("B2v");
    Placement pls4 = new Placement("B2d");
    assertEquals(pls1.hashCode(), pls2.hashCode());
    assertNotEquals(pls1.hashCode(), pls3.hashCode());
    assertNotEquals(pls3.hashCode(), pls4.hashCode());
    assertTrue(pls1.equals(pls2));
    assertFalse(pls1.equals(pls3));
    Coordinate c = new Coordinate ("B2");
    assertFalse(pls1.equals(c));
  }

  @Test
  public void illgealArgument(){
    assertThrows(IllegalArgumentException.class, ()-> new Placement("a0b "));
    assertThrows(IllegalArgumentException.class, ()-> new Placement(" a0b"));
    assertThrows(IllegalArgumentException.class, ()-> new Placement("a 0b"));
    assertThrows(IllegalArgumentException.class, ()-> new Placement("a0bb"));

  }
}

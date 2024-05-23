package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CoordinateTest {
  @Test
  public void test_coordinates() {
    Coordinate c = new Coordinate (10,10);
    assertEquals(c.getColumn(), 10);
    assertEquals(c.getRow(), 10);

  }
  @Test
  public void test_equals(){
    Coordinate c1 = new Coordinate (10,10);
    Coordinate c2 = new Coordinate (11,11);
    Coordinate c3 = new Coordinate (12,12);
    Coordinate c4 = new Coordinate (13,13);
    Coordinate c5 = new Coordinate (10,10);
    BattleShipBoard b1 = new BattleShipBoard(5,10, 'X');
    assertNotEquals(c1, b1); // test different types are equals
    assertEquals(c1, c1);
    assertEquals(c2,c2);
    assertEquals(c1, c5); // test different objects from same class with same value
    assertNotEquals (c1, c2);
    assertNotEquals (c3, c4);
    assertNotEquals(c1, "(14,14)");
    
  }
  @Test
  public void test_hashCode_and_toString(){
    Coordinate c1 = new Coordinate (1,1);
    Coordinate c2 = new Coordinate (1,1);
    Coordinate c3 = new Coordinate (1,2);
    assertEquals(c1.hashCode(), c2.hashCode());
    assertNotEquals(c1.hashCode(), c3.hashCode());
    assertNotEquals (c2.hashCode(), c3.hashCode());
    assertEquals(c1.toString(), c2.toString());
    assertNotEquals(c1.toString(), c3.toString());
    assertNotEquals(c2.toString(), c3.toString());

  }

  @Test
  void test_string_constructor_valid_cases() {
    Coordinate c1 = new Coordinate("B3");
    assertEquals(1, c1.getRow());
    assertEquals(3, c1.getColumn());
    Coordinate c2 = new Coordinate("D5");
    assertEquals(3, c2.getRow());
    assertEquals(5, c2.getColumn());
    Coordinate c3 = new Coordinate("A9");
    assertEquals(0, c3.getRow());
    assertEquals(9, c3.getColumn());
    Coordinate c4 = new Coordinate("Z0");
    assertEquals(25, c4.getRow());
    assertEquals(0, c4.getColumn());

  }
  @Test
  public void test_string_constructor_error_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("00"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("AA"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("@0"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("[0"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("A:"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("A/"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("A"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("A12"));
  }

}

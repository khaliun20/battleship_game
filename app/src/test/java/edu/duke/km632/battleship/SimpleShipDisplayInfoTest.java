package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_SimpleShipDisplayInfo() {
    SimpleShipDisplayInfo d = new SimpleShipDisplayInfo (0,1);
    assertEquals(1, d.getInfo(new Coordinate (1,2), true));
    assertEquals(0, d.getInfo(new Coordinate (1,2), false));
    assertNotEquals(1, d.getInfo(new Coordinate (1,2), false));
    assertNotEquals(0, d.getInfo(new Coordinate (1,2), true));
    
  }

}

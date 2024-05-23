package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  @Test
  public void test_createShip() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    Ship<Character> dst = f.makeDestroyer(v1_2);
    checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));

    V1ShipFactory f3 = new V1ShipFactory();
    Placement v1_3 = new Placement(new Coordinate(2, 2), 'H');
    Ship<Character> dst3 = f3.makeCarrier(v1_3);
    checkShip(dst3, "Carrier", 'c', new Coordinate(2, 2), new Coordinate(2, 3), new Coordinate(2, 4),
        new Coordinate(2, 5), new Coordinate(2, 6), new Coordinate(2, 7));

    V1ShipFactory f4 = new V1ShipFactory();
    Placement v1_4 = new Placement(new Coordinate(2, 2), 'H');
    Ship<Character> dst4 = f4.makeBattleship(v1_4);
    checkShip(dst4, "Battleship", 'b', new Coordinate(2, 2), new Coordinate(2, 3), new Coordinate(2, 4),
        new Coordinate(2, 5));

    V1ShipFactory f5 = new V1ShipFactory();
    Placement v1_5 = new Placement(new Coordinate(2, 2), 'H');
    Ship<Character> dst5 = f5.makeSubmarine(v1_5);
    checkShip(dst5, "Submarine", 's', new Coordinate(2, 2), new Coordinate(2, 3));

  }

  private void checkShip(Ship<Character> testShip, String expectedName,
      char expectedLetter, Coordinate... expectedLocs) {

    assertEquals(testShip.getName(), expectedName);
    for (Coordinate c : expectedLocs) {
      assertTrue(testShip.occupiesCoordinates(c));
      assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
      assertNotEquals(expectedLetter, testShip.getDisplayInfoAt(c, false));
      
    }

  }

  @Test
  public void test_invalidArguments(){
    V1ShipFactory f5 = new V1ShipFactory();
    Placement v1_5 = new Placement(new Coordinate(2, 2), 'M');
    assertThrows(IllegalArgumentException.class,()-> f5.makeSubmarine(v1_5));
    
    
  }
 @Test
 public void test_makeNewShip(){
    V1ShipFactory f = new V1ShipFactory();
    Ship<Character> ship = f.makeNewShip("Battleship", new Placement("a4h"));
 Ship<Character> ship1 = f.makeNewShip("Carrier", new Placement("a4h"));
 Ship<Character> ship2 = f.makeNewShip("Submarine", new Placement("a4v"));
 Ship<Character> ship3 = f.makeNewShip("Destroyer", new Placement("a2h"));

 assertEquals(ship1.getName(), "Carrier");
 assertEquals(ship.getName(), "Battleship");
 assertEquals(ship2.getName(), "Submarine");
 assertEquals(ship3.getName(), "Destroyer");
 
 }
}

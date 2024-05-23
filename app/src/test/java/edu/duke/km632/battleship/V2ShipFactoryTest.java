package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
  @Test
  public void test_createShip() {
    //battleship 
    V2ShipFactory f = new V2ShipFactory();
    Placement v2_1 = new Placement(new Coordinate(1, 1), 'R');
    Ship<Character> ship1 = f.makeBattleship(v2_1);
    checkShip(ship1, "Battleship", 'b', new Coordinate(1,1), new Coordinate(2,1), new Coordinate (2,2), new Coordinate (3,1));
    // Submarine
    Placement v2_2 = new Placement(new Coordinate(1, 1), 'H');
    Ship<Character> ship2 = f.makeSubmarine(v2_2);
    checkShip(ship2, "Submarine", 's', new Coordinate(1,1), new Coordinate(1,2));
    //Carrier
    Placement v2_3 = new Placement(new Coordinate(1, 1), 'R');
    Ship<Character> ship3 = f.makeCarrier(v2_3);
    checkShip(ship3, "Carrier", 'c', new Coordinate(1,2), new Coordinate(1,3), new Coordinate(1,4), new Coordinate(1,5), new Coordinate(2,1), new Coordinate (2,2), new Coordinate (2,3));
    // Submarine
    Placement v2_4 = new Placement(new Coordinate(1, 1), 'V');
    Ship<Character> ship4 = f.makeDestroyer(v2_4);
    checkShip(ship4, "Destroyer", 'd', new Coordinate(1,1), new Coordinate(2,1), new Coordinate(3,1));

  }

 private void checkShip(Ship<Character> testShip, String expectedName,char expectedLetter, Coordinate... expectedLocs) {

    assertEquals(testShip.getName(), expectedName);
    for (Coordinate c : expectedLocs) {
      assertTrue(testShip.occupiesCoordinates(c));
      assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
      assertNotEquals(expectedLetter, testShip.getDisplayInfoAt(c, false));
      
    }
 }

  @Test
  public void test_invalidArguments(){
    V2ShipFactory f = new V2ShipFactory();
    Placement v2_1 = new Placement(new Coordinate (2,2), 'H');
    assertThrows(IllegalArgumentException.class, ()->f.makeCarrier(v2_1));
    assertThrows(IllegalArgumentException.class, ()->f.makeBattleship(v2_1));
    Placement v2_2 = new Placement(new Coordinate (2,2), 'U');
    assertThrows(IllegalArgumentException.class, ()->f.makeSubmarine(v2_2));
    assertThrows(IllegalArgumentException.class, ()->f.makeDestroyer(v2_2));
  
  }


 @Test
 public void test_makeNewShip(){
    V2ShipFactory f = new V2ShipFactory();
    Ship<Character> ship = f.makeNewShip("Battleship", new Placement("a4d"));
 Ship<Character> ship1 = f.makeNewShip("Carrier", new Placement("a4u"));
 Ship<Character> ship2 = f.makeNewShip("Submarine", new Placement("a4v"));
 Ship<Character> ship3 = f.makeNewShip("Destroyer", new Placement("a2h"));

 assertEquals(ship1.getName(), "Carrier");
 assertEquals(ship.getName(), "Battleship");
 assertEquals(ship2.getName(), "Submarine");
 assertEquals(ship3.getName(), "Destroyer");
 

 


 }


}

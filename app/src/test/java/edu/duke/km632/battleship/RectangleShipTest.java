package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {

  @Test
  public void test_rectangleShip_construcy() {
    Coordinate upperLeft = new Coordinate(1, 2);
    RectangleShip<Character> ship = new RectangleShip<Character>("submarine",upperLeft, 2, 2,'s','*');
    assertTrue(ship.occupiesCoordinates(new Coordinate(1,3)));
    assertTrue(ship.occupiesCoordinates(new Coordinate (2,2)));
    assertTrue(ship.occupiesCoordinates(new Coordinate (2,3)));
    assertFalse(ship.occupiesCoordinates(new Coordinate(1,6)));
    assertFalse(ship.occupiesCoordinates(new Coordinate (2,4)));
    assertFalse(ship.occupiesCoordinates(new Coordinate (3,4)));
    
  }

  @Test
  public void test_recordHit_wasHitAt(){
    RectangleShip<Character> ship = new RectangleShip<Character>("submarine",new Coordinate(1,2), 2, 2, 's','*');
    Coordinate c = new Coordinate(1,2);
    ship.recordHitAt(c);
    assertTrue(ship.wasHitAt(c));
    assertFalse(ship.wasHitAt(new Coordinate(2,2)));
    
  }

  @Test
  public void test_invalid_ship_hit(){
    RectangleShip<Character> ship = new RectangleShip<Character>("submarine",new Coordinate(1,2), 2, 2, 's','*');
    assertThrows(IllegalArgumentException.class, ()-> ship.recordHitAt(new Coordinate (3,1)));
    assertThrows(IllegalArgumentException.class, ()-> ship.recordHitAt(new Coordinate (1,4)));
    assertThrows(IllegalArgumentException.class, ()-> ship.recordHitAt(new Coordinate (3,4)));
    assertThrows(IllegalArgumentException.class, ()-> ship.recordHitAt(new Coordinate (0,2)));
    assertThrows(IllegalArgumentException.class, ()-> ship.recordHitAt(new Coordinate (2,1))); 
  }

  @Test
  public void test_isSunk(){
    RectangleShip<Character> ship = new RectangleShip<Character>("submarine",new Coordinate(4,5), 1, 2, 's','*');
    Coordinate c1 = new Coordinate(4,5);
    Coordinate c3 = new Coordinate(5,5);
    ship.recordHitAt(c1);
    ship.recordHitAt(c3);
    assertTrue(ship.isSunk());
    assertEquals("submarine", ship.getName());
    assertNotEquals("", ship.getName());

  }

  @Test
  public void test_notSunk(){
    RectangleShip<Character> ship = new RectangleShip<Character>("submarine",new Coordinate(4,5), 2, 2, 's','*');
    Coordinate c1 = new Coordinate(4,5);
    Coordinate c2 = new Coordinate(4,6);
    Coordinate c3 = new Coordinate(5,5);

    ship.recordHitAt(c1);
       ship.recordHitAt(c2);
    ship.recordHitAt(c3);
   

    

    assertFalse(ship.isSunk());
    
  }

  @Test
  public void test_allShipPoints(){
    AbstractShipFactory<Character> f = new V1ShipFactory();
    Ship<Character> ship = f.makeSubmarine(new Placement ("A3H"));
    Coordinate c1 = new Coordinate(0,3);
    Coordinate c2 = new Coordinate(0,4);
    HashSet<Coordinate> testSet= new HashSet<>();
    testSet.add(c1);
    testSet.add(c2);
    assertTrue(testSet.equals(ship.getCoordinates()));
    
    AbstractShipFactory<Character> f1 = new V1ShipFactory();
    Ship<Character> ship1 = f1.makeSubmarine(new Placement ("A3H"));
    Coordinate c3 = new Coordinate(1,3);
    Coordinate c4 = new Coordinate(1,4);
    HashSet<Coordinate> testSet1= new HashSet<>();
    testSet.add(c3);
    testSet.add(c4);
    assertFalse(testSet1.equals(ship1.getCoordinates()));
    
  }
@Test
public void test_wasHitOnMovedShip(){
 Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
  AbstractShipFactory<Character> f = new V1ShipFactory();
  Ship<Character> ship = f.makeSubmarine(new Placement ("A3H"));
  b1.tryAddShip(ship);
  Coordinate c = new Coordinate ("a3");
  b1.fireAt(c);
  assertTrue(ship.wasHitOnMovedShip(c));
  assertFalse(ship.wasHitOnMovedShip(new Coordinate("a4")));
  
}
  
}

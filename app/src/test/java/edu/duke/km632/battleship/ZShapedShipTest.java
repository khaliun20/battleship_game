package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ZShapedShipTest {
  @Test
  public void test_Constructor() {

    ZShapedShip<Character> shipU = new ZShapedShip<Character>("Carrier", new Coordinate(0, 0), 'U', 'c', '*');
    ZShapedShip<Character> shipD = new ZShapedShip<Character>("Carrier", new Coordinate(0, 0), 'D', 'c', '*');
    ZShapedShip<Character> shipR = new ZShapedShip<Character>("Carrier", new Coordinate(0, 0), 'R', 'c', '*');
    ZShapedShip<Character> shipL = new ZShapedShip<Character>("Carrier", new Coordinate(0, 0), 'L', 'c', '*');
  }

  @Test
  public void test_getName() {
    ZShapedShip<Character> shipU = new ZShapedShip<Character>("Carrier", new Coordinate(0, 0), 'U', 'c', '*');
    assertEquals(shipU.getName(), "Carrier");
  }

  @Test
  public void test_makeCoordsU() {
    HashSet<Coordinate> coordSet1 = new HashSet<>();
    coordSet1.add(new Coordinate(0,0));
    coordSet1.add(new Coordinate(1,0));
    coordSet1.add(new Coordinate(2,0));
    coordSet1.add(new Coordinate(3,0));
    coordSet1.add(new Coordinate(2,1));
    coordSet1.add(new Coordinate(3,1));
    coordSet1.add(new Coordinate(4,1));

    ZShapedShip<Character> ship1 = new ZShapedShip<Character>("Carrier", new Coordinate(0,0), 'U','c','*');
    HashSet<Coordinate> result1 = new HashSet<>();
    for (Coordinate coord: ship1.getCoordinates()){
      result1.add(coord);
    }
    assertTrue(coordSet1.equals(result1));

  }

  @Test
  public void test_makeCoordsD() {
    HashSet<Coordinate> coordSet1 = new HashSet<>();
    coordSet1.add(new Coordinate(0,0));
    coordSet1.add(new Coordinate(1,0));
    coordSet1.add(new Coordinate(2,0));
    coordSet1.add(new Coordinate(1,1));
    coordSet1.add(new Coordinate(2,1));
    coordSet1.add(new Coordinate(3,1));
    coordSet1.add(new Coordinate(4,1));

    ZShapedShip<Character> ship1 = new ZShapedShip<Character>("Carrier", new Coordinate(0,0), 'D','c','*');
    HashSet<Coordinate> result1 = new HashSet<>();
    for (Coordinate coord: ship1.getCoordinates()){
      result1.add(coord);
    }
    assertTrue(coordSet1.equals(result1));


  }

  @Test
  public void test_makeCoordsR() {
    HashSet<Coordinate> coordSet1 = new HashSet<>();
    coordSet1.add(new Coordinate(0,1));
    coordSet1.add(new Coordinate(0,2));
    coordSet1.add(new Coordinate(0,3));
    coordSet1.add(new Coordinate(0,4));
    coordSet1.add(new Coordinate(1,0));
    coordSet1.add(new Coordinate(1,1));
    coordSet1.add(new Coordinate(1,2));

    ZShapedShip<Character> ship1 = new ZShapedShip<Character>("Carrier", new Coordinate(0,0), 'R','c','*');
    HashSet<Coordinate> result1 = new HashSet<>();
    for (Coordinate coord: ship1.getCoordinates()){
      result1.add(coord);
    }
    assertTrue(coordSet1.equals(result1));

  }

  @Test
  public void test_makeCoordsL() {
    HashSet<Coordinate> coordSet1 = new HashSet<>();
    coordSet1.add(new Coordinate(0,2));
    coordSet1.add(new Coordinate(0,3));
    coordSet1.add(new Coordinate(0,4));
    coordSet1.add(new Coordinate(1,0));
    coordSet1.add(new Coordinate(1,1));
    coordSet1.add(new Coordinate(1,2));
    coordSet1.add(new Coordinate(1,3));

    ZShapedShip<Character> ship1 = new ZShapedShip<Character>("Carrier", new Coordinate(0,0), 'L','c','*');
    HashSet<Coordinate> result1 = new HashSet<>();
    for (Coordinate coord: ship1.getCoordinates()){
      result1.add(coord);
    }
    assertTrue(coordSet1.equals(result1));

  }
}

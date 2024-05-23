package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TShapedShipTest {

  @Test
  // Test the correct result of construction
  public void test_construction() {
    TShapedShip<Character> shipU = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'U', 'b', '*');
    TShapedShip<Character> shipD = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'D', 'b', '*');
    TShapedShip<Character> shipR = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'R', 'b', '*');
    TShapedShip<Character> shipL = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'L', 'b', '*');

  }

  @Test
  public void test_getName() {
    TShapedShip<Character> shipU = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'U', 'b', '*');
    assertEquals(shipU.getName(), "Battleship");
  }

  @Test
  public void test_makeCoordsForL() {

    // test passes
    HashSet<Coordinate> coordSet1 = new HashSet<>();
    coordSet1.add(new Coordinate("N3"));
    coordSet1.add(new Coordinate("m4"));
    coordSet1.add(new Coordinate("n4"));
    coordSet1.add(new Coordinate("o4"));

    TShapedShip<Character> shipL1 = new TShapedShip<Character>("Battleship", new Coordinate("m3"), 'L', 'b', '*');

    HashSet<Coordinate> result1 = new HashSet<>();
    for (Coordinate coord : shipL1.getCoordinates()) {
      result1.add(coord);
    }
    assertTrue(coordSet1.equals(result1));
    coordSet1.add(new Coordinate("m3"));
    assertFalse(coordSet1.equals(result1));
    // test fails
    HashSet<Coordinate> coordSet2 = new HashSet<>();

    coordSet1.add(new Coordinate(1, 0));
    coordSet1.add(new Coordinate(1, 1));
    coordSet1.add(new Coordinate(2, 1));

    TShapedShip<Character> shipL2 = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'L', 'b', '*');

    HashSet<Coordinate> result2 = new HashSet<>();
    for (Coordinate coord : shipL2.getCoordinates()) {
      result2.add(coord);
    }
    assertFalse(coordSet2.equals(result2));

  }

  @Test
  public void testMakeCoordsD(){
 // test passes
    HashSet<Coordinate> coordSet1 = new HashSet<>();
    coordSet1.add(new Coordinate(0, 0));
    coordSet1.add(new Coordinate(0, 1));
    coordSet1.add(new Coordinate(0, 2));
    coordSet1.add(new Coordinate(1, 1));

    TShapedShip<Character> shipL1 = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'D', 'b', '*');

    HashSet<Coordinate> result1 = new HashSet<>();
    for (Coordinate coord : shipL1.getCoordinates()) {
      result1.add(coord);
    }
    assertTrue(coordSet1.equals(result1));

  }
  @Test
  public void testMakeCoordsU(){
 // test passes
    HashSet<Coordinate> coordSet1 = new HashSet<>();
    coordSet1.add(new Coordinate(0, 1));
    coordSet1.add(new Coordinate(1, 0));
    coordSet1.add(new Coordinate(1, 1));
    coordSet1.add(new Coordinate(1, 2));

    TShapedShip<Character> shipL1 = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'U', 'b', '*');

    HashSet<Coordinate> result1 = new HashSet<>();
    for (Coordinate coord : shipL1.getCoordinates()) {
      result1.add(coord);
    }
    assertTrue(coordSet1.equals(result1));

  }
  @Test
  public void testMakeCoordsR(){
 // test passes
    HashSet<Coordinate> coordSet1 = new HashSet<>();
    coordSet1.add(new Coordinate(0, 0));
    coordSet1.add(new Coordinate(1, 0));
    coordSet1.add(new Coordinate(1, 1));
    coordSet1.add(new Coordinate(2, 0));

    TShapedShip<Character> shipL1 = new TShapedShip<Character>("Battleship", new Coordinate(0, 0), 'R', 'b', '*');

    HashSet<Coordinate> result1 = new HashSet<>();
    for (Coordinate coord : shipL1.getCoordinates()) {
      result1.add(coord);
    }
    assertTrue(coordSet1.equals(result1));

  }

 @Test
 public void test_getHitPieceID(){
  TShapedShip<Character> shipL1 = new TShapedShip<Character>("Battleship", new Coordinate(12, 3), 'L', 'b', '*');
  shipL1.recordHitAt(new Coordinate(12,4));
  shipL1.recordHitAt(new Coordinate("o4"));
  ArrayList<Integer> newIDList = new ArrayList<>();
  newIDList.add(1);
  newIDList.add(3);
  Iterable<Integer> newIterable =arrayToIterable(newIDList);
  assertTrue(iterablesEqual(newIterable, shipL1.getHitPieceID()));

 }

 @Test
 public void test_getHitPieceIDNotEqual(){
  TShapedShip<Character> shipL1 = new TShapedShip<Character>("Battleship", new Coordinate(12, 3), 'L', 'b', '*');
  shipL1.recordHitAt(new Coordinate(12,4));
  shipL1.recordHitAt(new Coordinate("o4"));
  ArrayList<Integer> newIDList = new ArrayList<>();
  newIDList.add(0);
  Iterable<Integer> newIterable =arrayToIterable(newIDList);
  assertFalse(iterablesEqual(newIterable, shipL1.getHitPieceID()));

 }

 @Test
 public void test_getHitPieceIDNotEqual1(){
  TShapedShip<Character> shipL1 = new TShapedShip<Character>("Battleship", new Coordinate(12, 3), 'L', 'b', '*');
  shipL1.recordHitAt(new Coordinate(12,4));
  shipL1.recordHitAt(new Coordinate("o4"));
  ArrayList<Integer> newIDList = new ArrayList<>();
  newIDList.add(0);
  newIDList.add(3);
  Iterable<Integer> newIterable =arrayToIterable(newIDList);
  assertFalse(iterablesEqual(newIterable, shipL1.getHitPieceID()));

 }


  
  private Iterable<Integer> arrayToIterable(ArrayList<Integer> newList) {
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
  public void test_updateHit(){

    //Create new ship
    TShapedShip<Character> shipL1 = new TShapedShip<Character>("Battleship", new Coordinate(12, 3), 'L', 'b', '*');
  ArrayList<Integer> newIDList = new ArrayList<>();
  newIDList.add(0);
  newIDList.add(2);
  Iterable<Integer> expected =arrayToIterable(newIDList); 
  // Give this new ship new hit list 
  //shipL1.updateHit(expected);

  

  

  }


}

package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class DiomandSonarTest {
  @Test
  public void test_construct() {
    Sonar<Coordinate> sonar = new DiomandSonar<Coordinate>(new Coordinate ("D3"));
    sonar.buildSonarShape();
    HashSet<Coordinate> actualSet = new HashSet<>();
    for(Coordinate coord : sonar.getSonarCoordinates()){
      actualSet.add(coord);
    }
    HashSet<Coordinate> testSet = new HashSet<>();
    testSet.add(new Coordinate("D2"));
    testSet.add(new Coordinate("D3"));
    testSet.add(new Coordinate("D1"));
    testSet.add(new Coordinate("D0"));
    testSet.add(new Coordinate("D4"));
    testSet.add(new Coordinate("D5"));
    testSet.add(new Coordinate("D6"));
    testSet.add(new Coordinate("C1"));
    testSet.add(new Coordinate("C2"));
    testSet.add(new Coordinate("c3"));
    testSet.add(new Coordinate("c4"));
    testSet.add(new Coordinate("C5"));
    testSet.add(new Coordinate("B2"));
    testSet.add(new Coordinate("B3"));
    testSet.add(new Coordinate("B4"));
    testSet.add(new Coordinate("A3"));
    testSet.add(new Coordinate("E1"));
    testSet.add(new Coordinate("E2"));
    testSet.add(new Coordinate("E3"));
    testSet.add(new Coordinate("E4"));
    testSet.add(new Coordinate("E5"));
    testSet.add(new Coordinate("F2"));
    testSet.add(new Coordinate("F3"));
    testSet.add(new Coordinate("F4"));
    testSet.add(new Coordinate("G3"));


    
    assertTrue(testSet.equals(actualSet));
  }

 @Test
 public void test_notEqual(){

    Sonar<Coordinate> sonar = new DiomandSonar<Coordinate>(new Coordinate ("D3"));
    sonar.buildSonarShape();
    HashSet<Coordinate> actualSet = new HashSet<>();
    for(Coordinate coord : sonar.getSonarCoordinates()){
      actualSet.add(coord);
    }
    HashSet<Coordinate> testSet = new HashSet<>();
    testSet.add(new Coordinate("D2"));
    testSet.add(new Coordinate("D3"));
    testSet.add(new Coordinate("D1"));
    testSet.add(new Coordinate("D0"));
    testSet.add(new Coordinate("D4"));
    testSet.add(new Coordinate("D5"));
    testSet.add(new Coordinate("D6"));
    testSet.add(new Coordinate("C1"));
    testSet.add(new Coordinate("C2"));
    testSet.add(new Coordinate("c3"));
    testSet.add(new Coordinate("c4"));
    testSet.add(new Coordinate("C5"));
    testSet.add(new Coordinate("B2"));
    testSet.add(new Coordinate("B3"));
    testSet.add(new Coordinate("B4"));
    testSet.add(new Coordinate("A3"));
    testSet.add(new Coordinate("E1"));
    testSet.add(new Coordinate("E2"));
    testSet.add(new Coordinate("E3"));
    testSet.add(new Coordinate("E4"));
    testSet.add(new Coordinate("E5"));
    testSet.add(new Coordinate("F2"));
    testSet.add(new Coordinate("F3"));
    testSet.add(new Coordinate("F4"));

    assertFalse(testSet.equals(actualSet));
 

 }

}

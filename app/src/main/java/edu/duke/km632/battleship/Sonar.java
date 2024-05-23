package edu.duke.km632.battleship;

public interface Sonar<T> {
 /**
   * Adds all the coordinates that make up the diomand shape to HashSet.
   */
 
  public void  buildSonarShape();
  /** 
   * Return sonar search coordinates as iterable
   */ 
  public Iterable<Coordinate> getSonarCoordinates();
  
}

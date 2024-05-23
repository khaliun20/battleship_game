package edu.duke.km632.battleship;

import java.util.HashSet;

/**
 * This class implements Sonar interface and builds diomand shaped sonar
 */
public class DiomandSonar<T> implements Sonar<T> {
  private final Coordinate center;
  private final HashSet<Coordinate> sonarCoordinates;

  /**
   * This constructs a solar searcher with center as the middle center of the
   * sonar
   */
  public DiomandSonar(Coordinate center) {
    this.center = center;
    this.sonarCoordinates = new HashSet<>();
  }

  /**
   * Adds all the coordinates that make up the diomand shape to HashSet.
   */
  @Override
  public void buildSonarShape() {
    int centerRow = center.getRow();
    int centerColumn = center.getColumn();
    sonarCoordinates.add(new Coordinate(centerRow, centerColumn));
    sonarCoordinates.add(new Coordinate(centerRow, centerColumn - 1));
    sonarCoordinates.add(new Coordinate(centerRow, centerColumn - 2));
    sonarCoordinates.add(new Coordinate(centerRow, centerColumn - 3));
    sonarCoordinates.add(new Coordinate(centerRow, centerColumn + 1));
    sonarCoordinates.add(new Coordinate(centerRow, centerColumn + 2));
    sonarCoordinates.add(new Coordinate(centerRow, centerColumn + 3));
    sonarCoordinates.add(new Coordinate(centerRow - 1, centerColumn));
    sonarCoordinates.add(new Coordinate(centerRow - 1, centerColumn - 1));
    sonarCoordinates.add(new Coordinate(centerRow - 1, centerColumn - 2));
    sonarCoordinates.add(new Coordinate(centerRow - 1, centerColumn + 1));
    sonarCoordinates.add(new Coordinate(centerRow - 1, centerColumn + 2));
    sonarCoordinates.add(new Coordinate(centerRow - 2, centerColumn));
    sonarCoordinates.add(new Coordinate(centerRow - 2, centerColumn - 1));
    sonarCoordinates.add(new Coordinate(centerRow - 2, centerColumn + 1));
    sonarCoordinates.add(new Coordinate(centerRow - 3, centerColumn));
    sonarCoordinates.add(new Coordinate(centerRow + 1, centerColumn));
    sonarCoordinates.add(new Coordinate(centerRow + 1, centerColumn - 1));
    sonarCoordinates.add(new Coordinate(centerRow + 1, centerColumn - 2));
    sonarCoordinates.add(new Coordinate(centerRow + 1, centerColumn + 1));
    sonarCoordinates.add(new Coordinate(centerRow + 1, centerColumn + 2));
    sonarCoordinates.add(new Coordinate(centerRow + 2, centerColumn));
    sonarCoordinates.add(new Coordinate(centerRow + 2, centerColumn - 1));
    sonarCoordinates.add(new Coordinate(centerRow + 2, centerColumn + 1));
    sonarCoordinates.add(new Coordinate(centerRow + 3, centerColumn));

  }

  /**
   * Return sonar search coordinates as iterable
   */

  @Override
  public Iterable<Coordinate> getSonarCoordinates() {
    return sonarCoordinates;

  }
}

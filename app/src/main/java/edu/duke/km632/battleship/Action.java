package edu.duke.km632.battleship;

import java.util.Objects;

/**
 * This class represents the actions player chooses (Attack, Move, Sonar)
 */
public class Action {

  private final Character action;

  /**
   * Constructs the object and checks if the player passed valid input
   */
  public Action(String action) {
    if (action.length() != 1) {
      throw new IllegalArgumentException("Action must be 1 letter. Either F, M or S");
    }

    char actionChar = action.toUpperCase().charAt(0);
    if (actionChar != 'F' && actionChar != 'M' && actionChar != 'S') {
      throw new IllegalArgumentException("Action must be 1 letter. Either F, M or S");

    }
    this.action = actionChar;

  }


    public Character getAction() {
    return action;
  }

  @Override
    public boolean equals(Object o) {
    if(o.getClass().equals(getClass())){
      Action a = (Action)o;
      return action == a.action;
    }
    return false;
  }

    @Override
    public int hashCode() {
      return Objects.hash(action);
    }

    @Override
    public String toString() {
      return action.toString();
    }


}

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.km632.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * @description This is the battleship game!
 */
public class App {
  private final Player player1;
  private final Player player2;

  /**
   * Conctrucrs the app object with players
   */
  public App(Player player1, Player player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  /**
   * Let the players place their ships on the  board
   */
  public void doPlacementPhase() throws IOException {
    player1.doPlacementPhase();
    player2.doPlacementPhase();
  }

  /**
   * Let the players start the attacking the phasse
   */
  public void doAttackingPhase() throws IOException {
    while (true) {
      player1.playOneTurnPhase(player2.getBoard(), player2.getView(), player2.getTextPlayer());
      if (player2.isLost()) {
        player1.winGame();
        return;
      }
      player2.playOneTurnPhase(player1.getBoard(), player1.getView(), player1.getTextPlayer());
      if (player1.isLost()) {
        player2.winGame();
        return;

      }
    }
  }

  static String askPlayerWhoTheyAre(String dash, BufferedReader input) throws IOException {
    System.out.println(dash +
        "Are you a human player or computer player?\nPlease choose'H' if you are a human.\nPlease choose 'C' if you are a computer.\n"
        + dash);

    String playerType = input.readLine().toUpperCase();
    if (!playerType.equals("H") && !playerType.equals("C")) {
      return "That is not a valid answer. Please try again\n.";
    }
    return playerType;

  }

  static Character getPlayerMode(PrintStream out, BufferedReader input) throws IOException {
    String dash = "---------------------------------------------------------------------------\n";
    String playerAnswer = askPlayerWhoTheyAre(dash, input);
    while (playerAnswer.length() != 1) {
      out.println(dash + playerAnswer + dash);
      playerAnswer = askPlayerWhoTheyAre(dash, input);
    }
    return playerAnswer.charAt(0);

  }
  /**
   * Intiialize the players and start the play
   */
  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    PrintStream out = System.out;
    Character playerAMode =  getPlayerMode(out,input);
    Character playerBMode =  getPlayerMode(out,input);
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
  
    V2ShipFactory factory = new V2ShipFactory();

    Player player1 = (playerAMode == 'C') ?
      new ComputerPlayer("A", b1, input, out, factory) :
                new HumanPlayer("A", b1, input, out, factory);
        Player player2 = (playerBMode == 'C') ?
          new ComputerPlayer("B", b2, input, out, factory) :
                new HumanPlayer("B", b2, input, out, factory);
    App app = new App(player1, player2);
    app.doPlacementPhase();
    app.doAttackingPhase();
  }
}

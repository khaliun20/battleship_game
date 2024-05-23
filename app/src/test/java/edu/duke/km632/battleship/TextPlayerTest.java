
package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  private TextPlayer createTextPlayer(int w, int h, String inputData, ByteArrayOutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

  @Test
  public void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
    String dash = "---------------------------------------------------------------------------\n";
    String prompt = "Please enter a location for a ship:\n";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');
    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(dash + prompt + "\n" + dash + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }
  }

  @Test

  void test_doOnePlacement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(3, 3, "A0V\n", bytes);
    String dash = "---------------------------------------------------------------------------\n";
    String ship = "Destroyer";
    String prompt = "Player A where do you want to place a Destroyer?\n";
    String expected = "  0|1|2\n" +
        "A d| |  A\n" +
        "B d| |  B\n" +
        "C d| |  C\n" +
        "  0|1|2\n";

    player.doOnePlacement(ship, player.shipCreationFns.get(ship));
    assertEquals(dash + prompt + dash + "\n" + dash + "Current ocean:\n" + expected + dash + "\n", bytes.toString());
  }

  @Test
  void test_playOneAttack()  throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer playerA = createTextPlayer(10, 20, "A0V\nA1V\nd3d\nf3l\n", bytes);
    
       
  }

  /*
   * @Test
   * 
   * @Disabled
   * void test_doPlacementPhase() throws IOException {
   * 
   * ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   * TextPlayer player = createTextPlayer(3, 3, "A0V\n", bytes);
   * 
   * String prompt =
   * "--------------------------------------------------------------------------------\n"
   * +
   * 
   * "Player A: you are going to place the following ships (which are all\n" +
   * "rectangular). For each ship, type the coordinate of the upper left\n" +
   * "side of the ship, followed by either H (for horizontal) or V (for\n" +
   * "vertical).  For example M4H would place a ship horizontally starting\n" +
   * "at M4 and going to the right.  You have\n\n" +
   * "2 \"Submarines\" ships that are 1x2\n"
   * + "3 \"Destroyers\" that are 1x3\n" + "3 \"Battleships\" that are 1x4\n" +
   * "2 \"Carriers\" that are 1x6\n"
   * +
   * "--------------------------------------------------------------------------------\n";
   * 
   * String expected = "  0|1|2\n" +
   * "A  | |  A\n" +
   * "B  | |  B\n" +
   * "C  | |  C\n" +
   * "  0|1|2\n";
   * String prompt1 = "Player A where do you want to place a Destroyer?\n";
   * 
   * String expected1 = "  0|1|2\n" +
   * "A d| |  A\n" +
   * "B d| |  B\n" +
   * "C d| |  C\n" +
   * "  0|1|2\n";
   * 
   * player.doPlacementPhase();
   * assertEquals(expected + "\n" + prompt + "\n" + prompt1 + expected1 + "\n",
   * bytes.toString());
   * 
   * }
   */
}

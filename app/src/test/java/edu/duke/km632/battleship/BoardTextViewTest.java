package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by3() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 3, 'X');
    String expectedHeader = "  0|1\n";
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader +
        "A  |  A\n" +
        "B  |  B\n" +
        "C  |  C\n" +
        expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());

  }

  @Test
  public void test_display_full_body() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(2, 3, 'X');
    for (int i = 0; i < b1.getHeight(); i++) {
      for (int j = 0; j < b1.getWidth(); j++) {
        b1.tryAddShip(new RectangleShip<Character>(new Coordinate(i, j), 's', '*'));
      }
    }
    String expectedHeader = "  0|1\n";
    BoardTextView view = new BoardTextView(b1);
    String expected = expectedHeader +
        "A s|s A\n" +
        "B s|s B\n" +
        "C s|s C\n" +
        expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());

  }

  @Test
  public void test_display_scarse() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(3, 2, 'X');
    b1.tryAddShip(new RectangleShip<Character>(new Coordinate(1, 1), 's', '*'));
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1|2\n";
    String expected = expectedHeader +

        "A  | |  A\n" +
        "B  |s|  B\n" +
        expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_enemy_board() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    AbstractShipFactory<Character> f = new V1ShipFactory();
    Ship<Character> ship1 = f.makeSubmarine(new Placement("B0H"));
    b1.tryAddShip(ship1);
    Ship<Character> ship2 = f.makeDestroyer(new Placement("A3V"));
    b1.tryAddShip(ship2);

    BoardTextView view = new BoardTextView(b1);
    String myView = "  0|1|2|3\n" +
        "A  | | |d A\n" +
        "B s|s| |d B\n" +
        "C  | | |d C\n" +
        "  0|1|2|3\n";
    assertEquals(myView, view.displayMyOwnBoard());

    b1.fireAt(new Coordinate("B0"));
    b1.fireAt(new Coordinate("B2"));

    String enemyView = "  0|1|2|3\n" +
        "A  | | |  A\n" +
        "B s| |X|  B\n" +
        "C  | | |  C\n" +
        "  0|1|2|3\n";
    assertEquals(enemyView, view.displayEnemyBoard());
  }

  @Test
  void test_displayMyBoardWithEnemyNextToIt() {
    // PlayerA
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    AbstractShipFactory<Character> f = new V1ShipFactory();
    Ship<Character> ship1 = f.makeSubmarine(new Placement("B0H"));
    b1.tryAddShip(ship1);
    Ship<Character> ship2 = f.makeDestroyer(new Placement("A3V"));
    b1.tryAddShip(ship2);
    BoardTextView myView = new BoardTextView(b1);
    b1.fireAt(new Coordinate("B0"));
    b1.fireAt(new Coordinate("B2"));
    // PlayerB
    BattleShipBoard<Character> b2 = new BattleShipBoard<Character>(4, 3, 'X');
    AbstractShipFactory<Character> f2 = new V1ShipFactory();
    Ship<Character> ship3 = f2.makeSubmarine(new Placement("A0H"));
    b2.tryAddShip(ship3);
    Ship<Character> ship4 = f2.makeDestroyer(new Placement("B0H"));
    b2.tryAddShip(ship4);
    BoardTextView enemyView = new BoardTextView(b2);
    b2.fireAt(new Coordinate("B1"));
    b2.fireAt(new Coordinate("B2"));
    b2.fireAt(new Coordinate("B3"));

    String aBoards = "     Your Ocean                              Player B's Ocean\n" +
        "  0|1|2|3                              0|1|2|3\n" +
        "A  | | |d A                          A  | | |  A\n" +
        "B *|s| |d B                          B  |d|d|X B\n" +
        "C  | | |d C                          C  | | |  C\n" +
        "  0|1|2|3                              0|1|2|3\n";

    String bBoards = "     Your Ocean                              Player B's Ocean\n" +
        "  0|1|2|3                              0|1|2|3\n" +
        "A s|s| |  A                          A  | | |  A\n" +
        "B d|*|*|  B                          B s| |X|  B\n" +
        "C  | | |  C                          C  | | |  C\n" +
        "  0|1|2|3                              0|1|2|3\n";

    assertEquals(aBoards, myView.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Player B's Ocean"));
    assertEquals(bBoards, enemyView.displayMyBoardWithEnemyNextToIt(myView, "Your Ocean", "Player B's Ocean"));

  }

  @Test
  void displayMyBoardWithEnemyNextToItF2Factory(){
   // PlayerA
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(7, 7, 'X');
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Ship<Character> ship1 = f.makeBattleship(new Placement("A0U"));
    b1.tryAddShip(ship1);
    Ship<Character> ship2 = f.makeBattleship(new Placement("A3R"));
    b1.tryAddShip(ship2);
    b1.fireAt(new Coordinate("B0"));
    b1.fireAt(new Coordinate("B2"));
    // PlayerB
    BattleShipBoard<Character> b2 = new BattleShipBoard<Character>(7, 7, 'X');
    AbstractShipFactory<Character> f2 = new V2ShipFactory();
    Ship<Character> ship3 = f2.makeCarrier(new Placement("A0U"));
    b2.tryAddShip(ship3);
    Ship<Character> ship4 = f2.makeDestroyer(new Placement("A3V"));
    b2.tryAddShip(ship4);

    b2.fireAt(new Coordinate("B1"));
    b2.fireAt(new Coordinate("B2"));
    b2.fireAt(new Coordinate("B3"));
    BoardTextView myView = new BoardTextView(b1);
    BoardTextView enemyView = new BoardTextView(b2);

    String aBoards = "     Your Ocean                                    Player B's Ocean\n" +
        "  0|1|2|3|4|5|6                                    0|1|2|3|4|5|6\n" +
        "A  |b| |b| | |  A                                A  | | | | | |  A\n" +
        "B *|b|*|b|b| |  B                                B  |X|X|d| | |  B\n" +
        "C  | | |b| | |  C                                C  | | | | | |  C\n" +
        "D  | | | | | |  D                                D  | | | | | |  D\n" +
        "E  | | | | | |  E                                E  | | | | | |  E\n" +
        "F  | | | | | |  F                                F  | | | | | |  F\n" +
        "G  | | | | | |  G                                G  | | | | | |  G\n" +
        "  0|1|2|3|4|5|6                                    0|1|2|3|4|5|6\n";

    String bBoards = "     Your Ocean                                    Player B's Ocean\n" +
        "  0|1|2|3|4|5|6                                    0|1|2|3|4|5|6\n" +
        "A c| | |d| | |  A                                A  | | | | | |  A\n" +
        "B c| | |*| | |  B                                B b| |b| | | |  B\n" +
        "C c|c| |d| | |  C                                C  | | | | | |  C\n" +
        "D c|c| | | | |  D                                D  | | | | | |  D\n" +
        "E  |c| | | | |  E                                E  | | | | | |  E\n" +
        "F  | | | | | |  F                                F  | | | | | |  F\n" +
        "G  | | | | | |  G                                G  | | | | | |  G\n" +
        "  0|1|2|3|4|5|6                                    0|1|2|3|4|5|6\n";

    assertEquals(aBoards, myView.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Player B's Ocean"));
    assertEquals(bBoards, enemyView.displayMyBoardWithEnemyNextToIt(myView, "Your Ocean", "Player B's Ocean"));
    
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
    Board<Character> wideTallBoard = new BattleShipBoard<Character>(11, 27, 'X');
    Board<Character> wideTallBoard2 = new BattleShipBoard<Character>(27, 27, 'X');
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideTallBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideTallBoard2));

  }

}

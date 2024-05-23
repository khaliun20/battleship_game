package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ComputerPlayerTest {

  @Test
  public void test_testDoPlacementPhase() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    V2ShipFactory factory = new V2ShipFactory();
    ComputerPlayer player1 = new ComputerPlayer("A", b1, input, System.out, factory);
     Ship<Character> ship = factory.makeSubmarine(new Placement("a0v"));
    
     ComputerPlayer player2 = new ComputerPlayer("B", b2, input, System.out, factory);
     //  b2.tryAddShip(ship);
  

    try {
      player1.doPlacementPhase();
            player2.doPlacementPhase();
                player1.playOneTurnPhase(player2.getBoard(), player2.getView(), player2.getTextPlayer() );
        player2.playOneTurnPhase(player1.getBoard(), player1.getView(), player1.getTextPlayer() );
 
    } catch (IOException e) { 
    }

     int size = player1.myShips.size();
     assertEquals(size, 10);

  
  }

}

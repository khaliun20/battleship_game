package edu.duke.km632.battleship;

import java.io.BufferedReader;
import java.io.PrintStream;

/**
 * This class extends the TextPlayer
 */
public class HumanPlayer extends TextPlayer {

    public HumanPlayer(String playerName, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> shipFactory) {
    super(playerName, theBoard, inputSource,out, shipFactory);


    }



}

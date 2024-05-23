/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

class AppTest {

  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main() throws IOException {
    // writes data to byte array
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    // wraps bytes and auto flashes after every new line
    PrintStream out = new PrintStream(bytes, true);
    // reads content of the file
    InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");

    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
    assertNotNull(expectedStream);

    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;

    try {
      // redirect standard input stream to InputStreamobject
      System.setIn(input);
      System.setOut(out);
      // this simulates running main without arguments
      App.main(new String[0]);

    } finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    // convert bytes read to string
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected, actual);

  }
  



  @Test
public  void test_app() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    V2ShipFactory factory = new V2ShipFactory();

    
    TextPlayer p1 = new TextPlayer("A", b1, input, System.out, factory);
    TextPlayer p2 = new TextPlayer("B", b2, input, System.out, factory);
    App app = new App(p1, p2);
    assertEquals(p1.getTextPlayer(), "A");
    assertEquals(p2.getTextPlayer(), "B");
  
    }
  
@Test
    public void testGetPlayerMode_Human() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("M\n".getBytes());
        System.setIn(in);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = System.out;
        assertThrows(NullPointerException.class , ()->App.getPlayerMode(out, input));
      
       
 
}        @Test
    public void testGetPlayerMode_Human2() throws IOException {

        ByteArrayInputStream in = new ByteArrayInputStream("C\n".getBytes());
        System.setIn(in);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = System.out;
        Character result = App.getPlayerMode(out, input);
        assertEquals(Character.valueOf('C'), result);
  

}
  
 
   @Test
    public void testGetPlayerMode_HumanAndComputer() throws IOException {
        // Prepare
        ByteArrayInputStream in = new ByteArrayInputStream("H\nC\n".getBytes());
        System.setIn(in);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = System.out;
        
        // Execute
        Character player1Mode = App.getPlayerMode(out, input);
        Character player2Mode = App.getPlayerMode(out, input);

        // Verify
        assertEquals(Character.valueOf('H'), player1Mode);
        assertEquals(Character.valueOf('C'), player2Mode);
    }
 
  

    @Test
   
    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main_C_C() throws IOException {
    // writes data to byte array
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    // wraps bytes and auto flashes after every new line
    PrintStream out = new PrintStream(bytes, true);
    // reads content of the file
    InputStream input = getClass().getClassLoader().getResourceAsStream("input1.txt");

    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output1.txt");
    assertNotNull(expectedStream);

    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;

    try {
      // redirect standard input stream to InputStreamobject
      System.setIn(input);
      System.setOut(out);
      // this simulates running main without arguments
      App.main(new String[0]);

    } finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    // convert bytes read to string
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected, actual);

    }
  
   @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main_C_H() throws IOException {
    // writes data to byte array
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    // wraps bytes and auto flashes after every new line
    PrintStream out = new PrintStream(bytes, true);
    // reads content of the file
    InputStream input = getClass().getClassLoader().getResourceAsStream("input2.txt");

    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output2.txt");
    assertNotNull(expectedStream);

    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;

    try {
      // redirect standard input stream to InputStreamobject
      System.setIn(input);
      System.setOut(out);
      // this simulates running main without arguments
      App.main(new String[0]);

    } finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    // convert bytes read to string
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected, actual);

  }

  
  

}

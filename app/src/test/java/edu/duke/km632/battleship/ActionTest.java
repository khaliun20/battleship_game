
package edu.duke.km632.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ActionTest {
  @Test
  public void test_construction() {
    Action a = new Action("f");
    assertEquals(a.getAction(), 'F');
                           
  }

    @Test
  public void test_hashStringEquals(){
    Action o = new Action("f");
    Action o1 = new Action("F");
    Action o2 = new Action("m");
    Coordinate c = new Coordinate("A0");
    assertEquals(o, o1);
    assertNotEquals(o, o2);
    assertNotEquals(o,c);
    assertNotEquals(o1, o2);
    assertEquals(o.hashCode(), o1.hashCode());
    assertNotEquals(o.hashCode(), o2.hashCode());
    assertEquals(o.toString(), o1.toString());
    assertNotEquals(o.toString(), o2.toString());

  }

  @Test
  public void test_invalidConstruction(){
    assertThrows(IllegalArgumentException.class, ()-> new Action("Fa"));
    assertThrows(IllegalArgumentException.class, ()-> new Action(""));
    assertThrows(IllegalArgumentException.class, ()-> new Action(" "));
    assertThrows(IllegalArgumentException.class, ()-> new Action("helli"));
    

  }


}

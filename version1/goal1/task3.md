## Task 3. Coordinate class

Next, we want to make a Coordinate class.  Make sure you are back in your main code
directory, create the class, and generate the skeleton (C-x C-s). Give it the following
members:
  - a private final int for row and for column
  - getters for both of the above fields
  - a constructor that takes the row and column and initalizes the members

You should be pro at the above by now, as well as writing a test method for that
(don't forget: "C-x t" to switch  to the test class!) so we'll just let you handle
that part now.

Next, we need to add three other methods:
 (1) public boolean equals(Object o)
 (2) public int hashCode()
 (3) public String toString()

All three of these should have @Override to make sure we are overriding
what we inherit from Object.

The first of these lets us check if two objects are equal.  It is
slightly annoying that it has to take an Object instead of a Coordinate,
but that is because we inherit it from Object.  We can write our
equals like this:
```java
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }
```
Yes, we have to cast, which we generally don't like, but this is a case
where it is very necessary.  Note that we check if
`o.getClass().equals(getClass())`  rather than checking
`o instanceof Coordinate`

The former requires that o has *exactly* the same class as "this" object.
If we just checked o instanceof Coordinate, and we later made a subclass
of Coordinate, then we might incorrectly see those as equal.
Likewise, we should not check if o.getClass().equals(Coordinate.class)
since we might have a subclass that wants to reuse this method for
some (or all) of its equality checking.

We need to write hashcode so we can put these Coordinates into things
like HashSets and HashMaps (and it is generally good to write all three
of these methods).  We might just want to do something simple like:
 return row + column;

but that makes all the following hash to the same thing:
row = 0 column = 3
row = 1 column = 2
row = 2 column = 1
row = 3 column = 0

For our particular case we could do something like
`return row * 1000 + column;`
but let us be more general:  once we write toString() we can just
use the fact that Java's Strings have a good hashcode:
```java
  @Override
  public String toString() {
    return "("+row+", " + column+")";
  }
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
```
Even though equals should be straightforward to test, we are still going
to note a few things.  Here is what we might test with:
```java
  @Test
  public void test_equals() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    Coordinate c4 = new Coordinate(3, 2);
    assertEquals(c1, c1);   //equals should be reflexsive
    assertEquals(c1, c2);   //different objects bu same contents
    assertNotEquals(c1, c3);  //different contents
    assertNotEquals(c1, c4);
    assertNotEquals(c3, c4);
    assertNotEquals(c1, "(1, 2)"); //different types
  }
```
Note we want to make sure we test with different objects that should
be equal (not just testing if c1.equals(c1)).  We also want to make sure
to test the behavior for a different type. When we do that, our Coordinate
has to come first.  If we put the other type first, we are using ITS
equals method instead.

We'll let you test toString, but how do we test hashCode?

We *dont* want to test that we get a specific value.  E.g., we don't want
`assertEquals(42, someCoord.hashCode());`

Instead, we want to test that equivalent objects have the same hashcode
and that different objects have different hashcodes.  We can't rule
out all possible collisions, but we can check that some of the simplest
things don't cause collisions
```java
  @Test
  public void test_hashCode() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(0, 3);
    Coordinate c4 = new Coordinate(2, 1); 
    assertEquals(c1.hashCode(), c2.hashCode());
    assertNotEquals(c1.hashCode(), c3.hashCode());
    assertNotEquals(c1.hashCode(), c4.hashCode());
  }
```


We also want to write the constructor
` public Coordinate(String descr) `
which takes in a string like "A2" and makes the Coordinate
that corresponds to that string (e.g. row=0, column =2).

We are going to let you do most of this constructor writing, but will point
out that the [String documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) is going to be quite helpful.

- In particular, you are likely to find these methods useful:
- length
- toUpperCase
- charAt
 
Also, as with C and C++, you can do "math" on characters, such as
```java
if (rowLetter < 'A' || rowLetter > 'Z')
```
and
```java
rowLetter - 'A'
```
Here are two test methods to add to your CoordinateTest.java class to get you
started on testing your new constructor:
```java
 @Test
  void test_string_constructor_valid_cases() {
    Coordinate c1 = new Coordinate("B3");
    assertEquals(1, c1.getRow());
    assertEquals(3, c1.getColumn());
    Coordinate c2 = new Coordinate("D5");
    assertEquals(3, c2.getRow());
    assertEquals(5, c2.getColumn());
    Coordinate c3 = new Coordinate("A9");
    assertEquals(0, c3.getRow());
    assertEquals(9, c3.getColumn());
    Coordinate c4 = new Coordinate("Z0");
    assertEquals(25, c4.getRow());
    assertEquals(0, c4.getColumn());

  }
  @Test
  public void test_string_constructor_error_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("00"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("AA"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("@0"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("[0"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("A/"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("A:"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("A"));
    assertThrows(IllegalArgumentException.class, () -> new Coordinate("A12"));
  }
```
Make sure all your tests pass, that you have 100% coverage,
and that you have written any documentation that you need to.
Be sure to git commit and git push.

You finished step 3.


***

>[!IMPORTANT]
> - Add all the files to source control (GitHub) using `git add -A`
> - Commit and Push 

>[!NOTE]
> - You can now proceed to [Task 4](./task4.md)
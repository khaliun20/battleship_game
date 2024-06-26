Goal 5: "Attacking Phase" of game
---------------------------------

Ok, the placement phase is done.  Now we need to do the "attacking"
phase of the game.  Let's take stock of what we need for this step,
what we have already done, and what needs to change:

 - A way to display the enemy board:  Need to do that (task 19)
     - Needs to show where we missed -> BattleShipBoard needs to track misses
     - Needs to display different info from our own board
         - Only shows ships that have been hit
         - Shows their letter when they were hit, not the * marker
     - Need to add a method to BoardTextView to handle this.
     
 - Display the two board side-by-side: Need to do that (task 20)
     - Mostly some string manipulation.

 - Checking for win/loose: Need to do that (task 21)
     - Ships have isSunk already
     - BattlehsipBoard has an ArrayList<Ship>
        -  just need to iterate over ArrayList<Ship> to see if all of
        them have isSunk() == true
     - Ships currently have recordHitAt.  We could do this phase
       and test it with just that.  However, we need to
       add something to Board to handle incoming hits at a coordinate.
       Doing that now makes testing easier
       
 - User interaction to put it all together: Need to do that (task 22)
     - Already have Coordinate constructor that takes a String
     - Reading a Coordinate is similar to Reading a Placement.
        - Need error checking for bounds of board (but that is all)
     - Display results of firing (missed, or what type of ship hit)
     - Alternate turns in main game loop.
     - Display who wins or loses


And then you will be done with version 1.  So lets get
started on task 19: a way to display the enemy board...

## Task 19. Display board for enemy


Let's head over to BattleShipBoard and add a way to track where
the enemy has missed.  This can just be
```java
  HashSet<Coordinate> enemyMisses;
```
Don't forget to initialize it in the constructor. You'll also
want to use C-c TAB to add the proper import.

At the moment, we don't have a way to "shoot" at anything on
the board anyways, so lets add a method to do that.
```java
  public Ship<T> fireAt(Coordinate c)
```
This method should search for any ship that occupies coordinate c
(you already have a method to check that)
If one is found, that Ship is "hit" by the attack and should
record it (you already have a method for that!).  Then we
should return this ship.

If no ships are at this coordinate, we should record
the miss in the enemyMisses HashSet that we just made,
and return null.

Don't forget to add fireAt to interface Board.

What should we do next?   Test this method of course
(but you already know that, right?)

I will say a few things about testing this method however,

- First, we want to use assertSame to check the return value
  of fireAt, not assertEquals.  The difference is that
  assertSame checks if the objects are == while
  assertEquals checks if they are .equals.
  Put another way, assertSame checks if they
  are exactly the same object (pointer equality).
  assertEquals checks if they are equivalent objects.

  For this, we should be exactly the same ship (same pointer)
  that we created and added to the board.


 - Second, we want to check not only the return value
   of fireAt, but also that it had the expected side
   effects: namely, that it made the ship record the hit.
   We could forget to do s.recordHitAt and still return
   the right value, but our method would be wrong.

   How could we test this?  For now, we can
   check if the ship isSunk() at the proper time.
   For each of my fireAt calls, I checked
   the value of the relevant ship's isSunk
   after the fireAt call (with assertTrue or assertFalse).

   Note that later, we will learn about mocking,
   which will let us check for the correct method
   calls that don't affect return values.


 - Third, in a similar vein, we are supposed to be
   recording misses, but don't have a way to access
   them yet---that makes it hard to check that
   side effect.  Once we learn mocking,
   this won't be a problem.  For now, we'll just
   have to hold off on our test ensuring that
   we did enemyMisses.add(c) properly until
   we do our next bit of implementation.....


Our 
```java
 public T whatIsAt(Coordinate where)
```
gives us information for our own perspective.
This information is different from an enemy's perspective
as following:
```
                                Our own               Enemy                 
square has unhit ship     ship's letter (s,d,c,b)     blank
square has hit ship               *               ship's letter (s,d,c,b)
square has miss                                         X
square is empty, unmissed
```

- Let's make the following changes:

 - Rename `whatIsAt` to `whatIsAtForSelf`
   [put the cursor on whatIsAt in BattleShipBoard.jave and do "C-c r"
    to lsp-rename it.  Then edit the minibuffer to the new name and hit enter]
    
 - Make another method called `protected T whatIsAt(Coordinate where, boolean isSelf)`
   and move the body of whatIsAtForSelf into this method.

 - Make `whatIsAtForSelf` just `return whatIsAt(where, true);`


Before you do anything else, run all your test to make sure this refactoring didn't break anything...

Now, lets go to Ship.java and change
`public T getDisplayInfoAt(Coordinate where);`
to
`public T getDisplayInfoAt(Coordinate where, boolean myShip);`

Update the documentation to reflect the new parameter.

Now head to BasicShip.  We'll need to add
```java
 protected ShipDisplayInfo<T> enemyDisplayInfo;
```

note that this gives us a huge degree of flexibility in how we display the ships.
We can use this to handle our current needs, but later we could change how we
display enemy ships to behave in completely different ways...

Now head down to getDisplayInfoAt.  Add the boolean myShip parameter,
and use that to decide whether to use myDisplayInfo or enemyDisplayInfo
to call getInfo on.

We still need to initialize enemyDisplayInfo, so lets
add a parmaeter to the BasicShip constructor for that, and initialize enemyDisplayInfo
with that parameter.

- We need to go fix RectangleShip's constructors now.  There are three of them, but
we only need to change two:

 - The one that actually calls super needs to take another parameter
 of type ShipDisplayInfo<T> and pass it to the super constructor

 - This constructors
```java
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
  }
```

  needs to change to
```java
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
  }
```
  that is, we will tell the parent constructor that for my own view display `data if not hit` or `onHit if hit`
  for the enemy view,  `nothing if not hit`, `data if hit`

We also need to change our call to getDisplayInfoAt(where) to getDisplayInfoAt(where, isSelf)
in whatIsAt in BattleShipBoard.java.

You also probably have calls to getDisplayInfoAt(somecoord) in your testing
code, and need to change them to getDisplayInfoAt(somecoord,true).
I had them in RectangleShipTest.java and V1ShipFactoryTest.java.

After these changes, you should be passing all your tests, but not have 100% coverage.
Note that you probably don't need to write an entirely new test method to handle what
you just added.  I found that I could just add 2 lines to where I was already testing
getDisplayInfoAt(c,true) to also test getDisplayInfoAt(c,false).


Ok, now that we have made that change, lets go back to BattleShipBoard.java
Lets add
```java
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }
```
Let's also add that method signature to the Board interface.

whatIsAt already does most of what we need, but we also want to
handle misses.  Specifically, if the specified coordinate cooresponds
to a ship, we want to use its display info (which we already do).
However, if it does not, and we are doing this for an enemy board (isSelf is false),
then xwe should check for a miss before we return null.

What do we return if there is a miss?  We need a "T" to return.  It isn't
the display info of any ship.  So lets add a field to the class `final T missInfo;`
as well as a parameter for the constructor (which we initialize it with), then we
can return missInfo.

We'll have to add this parameter to the other constructor. We will also have
to pass a value in the various places we have constructed a BattleShipBoard
(we should pass in 'X').

After that change, all our tests should still pass, but we are missing coverage.

Now, lets test whatIsAtForEnemy.  Doing this testing should also
help us make sure we properly put things in enemyMisses when we
wrote fireAt.

Now we are almost finally do what we set out to do: display the enemy board.
We just have one small refactoring to do first.  Head over to BoardTextView.java

We have `public String displayMyOwnBoard()`
which does almost exactly what we want.  The only difference is whether we want to
do `toDisplay.whatIsAtForSelf`  or `toDisplay.whatIsAtForEnemy` in the inside of this loop.

Lets refactor this to
```java
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn)
```
(remember that when you import Function, you will need to down arrow to java.util.function.Function).
Now, change your call inside displayAnyBoard from `toDisplay.whatIsAtForSelf` to `getSquareFn.apply`

and then write public String displayMyOwnBoard() to just make use of this
with a lambda that calls toDisplay.whatIsAtForSelf.
```java
  public String displayMyOwnBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
  }
```

Run your tests.  That refactoring should have left all tests passing with 100% coverage.

Now you can write `public String displayEnemyBoard()`
which should just be a single call to displayAnyBoard, passing in a lambda that uses
the whatIsAtForEnemy function.

Once you have written that, go test it!  The one quick note I'll give for this
is that when I tested it, the first thing I did was
```java
   String myView =
      "  0|1|2|3\n" +
      "A  | | |d A\n" +
      "B s|s| |d B\n" +
      "C  | | |d C\n" +
      "  0|1|2|3\n";
    //make sure we laid things out the way we think we did.
    assertEquals(myView, view.displayMyOwnBoard());
```
Even though I'm not trying to test displayMyOwnBoard here, it was helpful
to make sure I set up my test correctly---that the board was laid out the way
I thought it was, before I started testing view.displayEnemyBoard().
This little bit of extra checking actually proved useful since I forgot
to add my ships to the board! I made them, but forgot to call tryAddShip in
setting up the test... With the above, I failed right away, and strongly
suspected that something was wrong with my test (because I have already
tested displayMyOwnBoard extensively).  Since it reported nothing in the board,
I could quickly see my mistake.  This saved me time trying to debug
a "phantom" mistake.

Once your tests all pass, make sure you have 100% coverage (and
good tests, of course), and that your documentation is up to date.

Now git commit and git push.

Onwards to task 20!


***

>[!IMPORTANT]
> - Add all the files to source control (GitHub) using `git add -A`
> - Commit and Push 

>[!NOTE]
> - You can now proceed to [Task 20](./task20.md)
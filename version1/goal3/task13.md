Goal 3: Check for valid placements
----------------------------------

Next, we want to start checking placement validity.  Here, we don't
mean that the placement string (like "A4V") is valid, but that given
a Placement, we can legally put the specified Ship on the board.
- There are really two rules here:
    - (1) The Ship can't go out of bounds (row or column less than 0,
     or >= width/height)
    - (2) The Ship can't overlap another Ship

You could probably just implement this in a few minutes directly in
BattleshipBoard, but that would not be great from an extensibility
perpsecitve. Think about what design principles we are violating here
if we do that. What if we later want to change the placement rules?
Maybe we start requiring that battleships be at least 4 squares
from carriers.  Now we have to go change BattleshipBoard.

Instead, lets make each rule its own class.  We'll make these
classes into a composable "chain" of rules.   A placement is only
valid if every rule in the chain says it is ok.

To do this, we are going to use a slight modification
of the Chain of Responsibility pattern. This pattern is built
around the idea that you have a sequence of Objects,
any of which can handle a request (or in our case, determine
the outcome).  If the current cannot handle the request (or
cannot determine the outcome), you go to the next item in the chain.

This pattern is characterized by an classes that both has-A and
is-A the same type.  In our implementation, this means, we will
have:
```java  
  public abstract class PlacementRuleChecker<T> {
     private final PlacementRuleChecker<T> next;
     //more stuff
  }
```

That is, subclass we make is-A PlacementRuleChecker
and also has-A PlacementRuleChecker (next, which is inherits).

We'll make a public constructor which takes the next item:
```java
 public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
 }
```
Then we are going to have a protected abstract method:
 protected abstract boolean checkMyRule(Ship<T> theShip, Board<T> theBoard);

Subclasses will override this method to specify how they check their own rule.
They don't need to consider any other rule, nor the chaining logic.  Instead,
our public method handles chaining rules together:
```java
  public boolean checkPlacement (Ship<T> theShip, Board<T> theBoard) {
    //if we fail our own rule: stop the placement is not legal
    if (!checkMyRule(theShip, theBoard)) {
      return false;
    }
    //other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard); 
    }
    //if there are no more rules, then the placement is legal
    return true;
  }
```

Note that we use tail recursion, but the "this" is changing with each recursive call,
so we make progress down the chain.  Subclasses will generally NOT override
this method (none of your should).

Now we just need to make some concrete subclasses.

## Task 13. In Bounds Rule

Before we delve into the details of the concrete subclass to check if a Ship is placed
in or out of bounds, we are going to make one quick change to Ship and BasicShip.
We want to have a method that lets us see all the Coordinates that a Ship occupies.
Let's add that now.  Put this in the Ship interface:
```java
 /**
   * Get all of the Coordinates that this Ship occupies.
   * @return An Iterable with the coordinates that this Ship occupies
   */
  public Iterable<Coordinate> getCoordinates();
```
Then go implement it in BasicShip.  This method is one line, since
a HashMap has a .keySet() which returns a Set of the keys,
(so myPieces.keySet() returns a Set<Coordinate>) and Set<T> implements Iterable<T>.
Go ahead and add a test for this method, and then let's make our checker
that checks if a ship is entirely in the boundaries of the board.

Lets make InBoundsRuleChecker.java, do C-c C-s to generate a skeleton,
and then modify the declared class to be generic in <T> and to extend PlacementRuleChecker<T>.
Hitting C-c C-a will generate the placeholder for
```java
  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
     // TODO Auto-generated method stub
     return false;
  }
```
which is one of the two things we need to implement here.  All this need to do is
iterate over all the coordinates in theShip and check that they are
in bounds on theBoard (i.e. 0 <= row < height and 0<= column < width).

Remember: we do NOT want to try to check for collisions here.  That is going to be the
responsibility of a different class.

The other is just a constructor that takes a PlacementRuleChecker<T> and passes it to the
super class's constructor:
```java
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
```
My first instince was to say :

Now, lets hit C-x t and write some test code for this.

But as I started that, I realized that I'd like to change BattleShipBoard before
we do that.  Why?  I'd like BattleShipBoard's constructor to take the PlacementRuleChecker.
Let's hop back over to BattleShipBoard.java and make this quick change.

First, add a field: `private final PlacementRuleChecker<T> placementChecker;`

Then add a parameter to the constructor which you use to initialize this.
We have a whole bunch of BattleShipBoards that we create.  Do we need to go change them all?
We *could*... or we could consider add another constructor (that takes the original two arguments
and chains to this one).   In fact, we could make this constructor have our default rule chain.
E.g., for now:
```java
  public BattleShipBoard(int w, int h) {
    this(w, h, new InBoundsRuleChecker<T>(null));
  }
```
and then later we could change it to `new NoCollisionRuleChecker<>(new InBoundsRuleChecker<T>(null))`

Now, we'd like to run all our tests again.  Unless any of our tests tried to place a ship
off the edge of the board, they should all pass.   We should not have any tests
that tried to place a ship off the edge of the board yet, since we haven't been testing
that functionality.  

Now, lets go to InBoundsRuleCheckerTest.java (if you didn't make it earlier, head back
to InBoundsRuleChecker and hit C-x t).

Now lets make write test cases for this checker. Bring all your best testing skills
from 551 to think of good test cases based on what could be done wrong in implementing this
method.

- A few things to remember:
    - (1) We can use our V1ShipFactory for very easy ship creation.
    - (2) Even though we just made our 2 parameter constructor for BattleShipBoard
     use InBoundsRuleChecker, we want to explicitly make one here and pass
     it into the 3 parameter constructor.  Why?
        - (a) we don't want to rely on that behavior not changing later,
           especially since we explcitly plan to change it!
        - (b) we want direct access to the InBoundsRuleChecker so we can call
           methods on it (our BattleShipBoard doesn't use it yet!)

When you finish this, you are only going to have 80% coverage on PlacementRuleChecker.
If you look at the details, you will see that you aren't covering
```java
    if (next != null) {
      return next.checkPlacement(theShip, theBoard); 
    }
```
That is, next is always null.
To test this, we really need another rule checker!  So even though we don't really
like to leave code uncovered.... we are going to go to the next task (where
we write another rule checker).  Then we can test it (by itself) and then
test the composition of the two rules!

So go ahead and make sure that this is the only part you missed in test
coverage, and that all your documentation is up to date.


You finished step 13.

***

>[!IMPORTANT]
> - Add all the files to source control (GitHub) using `git add -A`
> - Commit and Push 

>[!NOTE]
> - You can now proceed to [Task 14](./task14.md)
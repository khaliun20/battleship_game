Goal 2: Real BasicShip class and RectangleShip
----------------------------------------------
Now that we have a working end-to-end system (something that
vaguely resembles our game), we are ready to start adding features.
For the first feature, lets replace our placeholder BasicShip
with some real code.  We are going to have this inheritance hierarchy
(which will do everything we need for version 1):
```java
interface Ship<T>
    ^
    |
abstract class BasicShip<T>
    ^
    |
class RectangleShip<T>
```

## Task 10. Make RectangleShip

Our first task here is going to be to make the RectangleShip
class, and replace the placeholder code in BasicShip with real
code. As we do this, we want to put as much code as makes
sense into BasicShip. We prefer to have code as far up the
inheritance hierarchy as possible to reduce code duplication.
Anything that can be done (appropriately) in BasicShip can
get re-used in version 2 for our non-rectangular ships.

We also want to remember that we prefer to vary in data
instead of in code whenever it makes sense to do so.

How are we going to put these two ideas together to
make BasicShip work well for rectangular and non-rectangular ships?
We'll make our BasicShip have a  `HashMap<Coordinate, Boolean>  myPieces;`
With this map, we can hold any shape of ship (by putting in all coordinates
it occupies), and track which ones have been hit.   Specifically, if we have
a coordinate c, and we look it up in the map:
```java
   if myPieces.get(c)  is null, c is not part of this Ship
   if myPieces.get(c)  is false, c is part of this ship and has not been hit
   if myPieces.get(c)  is true, c is part of this ship and has been hit
```  
For a rectangle, we can fill in the myPieces with e.g., (1,1), (1,2), (1,3).
For a non-rectangle, we can fill in the myPieces with a generic shape.

We want to have BasicShip's constructor setup myPieces (because that
is part of initializing BasicShip), but we have to be a bit careful
about how we do that.  In particular, we need the child class
to get the right information for its specific shape into the BasicShip
constructor.

This information passing is made slightly more tricky by the fact that
the parent object construction has to happen before anything in the
child constructor (this is just like C++).  We can specify how
we want to call the parent class's constructor, as we could in C++.
In C++, we did this in the initializer list, e.g.
```cpp
  //if this were C++
  RectangleShip(Coordinate upperLeft, int width, int height) : BasicShip(someParams)  /* other stuff*/
```
however, the syntax in Java is different:
```java
  public RectangleShip(Coordinate upperLeft, int width, int height) {
     super(someParams); //specify how to call parent class constructor
     /* other stuff */
  }
```
If we don't write a call to super, the first line of the constructor is
implicitly super();
(unless we use constructor chaining---where write this(params) which
 specifies how to delegate initialization to another constructor in
 the same class.  Even then, the first constructor that does not
 delegate to another will call super() one way or another).

This means we can't do, e.g.
```java
  public RectangleShip(Coordinate upperLeft, int width, int height) {
     HashSet<Coordinate> myCoords = new HashSet<Coordinate>();
     //some for loops that fill in myCoords
     super(myCoords); //not legal: super is not first line :(
     /* other stuff */
  }
```
While we can do the following, it won't work:
```java
  public RectangleShip(Coordinate upperLeft, int width, int height) {
     //super() is implicit
     this.width = width;
     this.height = height;
     this.upperLeft = upperLeft;

  }

  HashSet<Coordinate> makeCoords() {
     //code that uses width, height, upperLeft
     //to compute + return HashSet
  }
```
then have BasicShips have e.g.,
```java
   abstract HashSet<Coordinate> getCoords();
   public BasicShip() {
     HashSet<Coordinate> myCoords = makeCoords(); //dynamic dispatch to actual class
   }
```
This approach will fail because the call to getCoords() in BasicShip's constructor
happens before we initialize width, height, and upperLeft in RectangleShip's
constructor.  If we tried it, width and height would be 0, and upperLeft
would be null.

We can make this work, we just have to write a *static* method in RectangleShip
to compute the set of Coordinates to pass to the super constructor.  We generally
don't like static methods.  However, we pretty much need to use one in this case.
We can make a static method call within the parameters we pass to super(),
but we can't make instance method calls.

All of that explaination was for why we are going to do what we are going to do.

First, create RectangleShip.java, and generate its skeleton (C-c C-s).
Don't make it extend BasicShip yet---we'll do that soon, but not now.

First, write 
```java
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height)
```
This method should generate the set of coordinates for a rectangle starting
at upperLeft whose width and height are as specified. E.g. if
upperLeft = (row=1,col=2)
width = 1
height = 3

You would return the set {(row=1,col=2), (row=2,col=2), (row=3,col=2)}
Note that even though all are ships are 1x? or ?x1, we can easily support ships
that are any size by any size---nothing wrong with that (and who knows, maybe later
we want a 4x4 ship?)

Why use a set rather than an ArrayList?  We don't really care about order.  We
just want to check if the set contains a particular point (for testing) and to iterate
over it (when we write BasicShip's constructor).

After you have written this method, switch to your testing code (C-x t) and write
test cases for it.

- Now, let's head back to BasicShip and start removing placeholders and replacing them
with real code.
  - Remove the old single Coordinate and put in
    protected HashMap<Coordinate, Boolean> myPieces
  - for the moment (so our old code still works) change the constructor that takes
    a Coordinate to
      public BasicShip(Coordinate c) {
        myPieces = new HashMap<Coordinate, Boolean>();
        myPieces.put(c, false);
      }
  - Add a constructor:
    public BasicShip(Iterable<Coordinate> where)
    This should initialize myPieces to have each Coordinate in where mapped
    to false.  Note that an Iterable is something you can "For each" over. E.g.
    for (Coordinate c: where) {...}
  - Change occupiesCoordinates to work with our new way of representing
    where the Ship is.

Go ahead and run all your test cases.  Nothing should be broken: our
BasicShip(Coordinate) constructor should give us backwards compatibility
while we are doing this refactoring.  You won't have 100% coverage yet,
but thats ok---we will write more tests in a second.

Now let us head back to RectangleShip and write this constructor:
```java
  public RectangleShip(Coordinate upperLeft, int width, int height) {
    super(makeCoords(upperLeft, width, height));
  }
```
All it does is make the right coordinate set, and pass them up to the
BasicShip constructor.

Now lets go over to RectangleShipTest.java and write some test cases
that make new RectangleShips and check that the occupy the correct
coordinates.

Now that we have RectangleShip, we'd really like to make
BasicShip abstract.  Doing that is going to require a few changes
to our test code that used BasicShip as a placeholder.

One other change we need to make at some point is to parameterize
RectangleShip and BasicShip by <T>.   It would be really nice
to make that change as we are swapping out BasicShip
(so we don't have to go through and make those changes again
later).

Remember that the <T> is so that we can track different
information for different Views.  For our text views,
it will be a Character.  

To suppor that, we wanted to make:

```java
public interface ShipDisplayInfo<T> {
       public T getInfo(Coordinate where, boolean hit);
}
```
Go ahead and create that (in a file called ShipDisplayInfo.java)
and also`public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {`
(in a file called SimpleShipDisplayInfo.java: create the skeleton,
add the <T> and implements ShipDisplayInfo<T>)

Once you have the skeleton for SimpleShipDisplayInfo, use C-c C-a
to add the method required by the interface.

- All this class needs to do is
  - have two fields of type T: one for myData and one for onHit
  - have a constructor that takes two Ts and initializes myData and onHit
  - getInfo check if (hit) and returns onHit if so, and myData otherwise.

This may seem like a complicated way to have two pieces of data
(which will just be char for v1), but it makes
it so that we can extend our code later quite easily.
Hit C-x t to switch to testing code, and write some test cases
for this class.   It may seem silly (after all, it is 3 lines of code,
how could you mess it up?)  But it is better to test than to go crazy
when you have some other bug that is because you made a typo in here.

Ok, now lets head back to BasicShip.java and make the following changes:
```java
  public class BasicShip implements Ship<Character>
```
becomes
```java
  public abstract class BasicShip<T> implements Ship<T> 
```
- add a field ` protected ShipDisplayInfo<T> myDisplayInfo;`
- remove the constructor
```java
  public BasicShip(Coordinate c) {
    myPieces = new HashMap<Coordinate, Boolean>();
    myPieces.put(c, false);
  }
```
Change your constructor `public BasicShip(Iterable<Coordinate> where) ` to
`public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo)`
and make it initialize `this.myDisplayInfo` from `myDisplayInfo`.

Change `public Characters getDisplayInfoAt(Coordinate where)` to `public T getDisplayInfoAt(Coordinate where)`.

We're going to need to take a moment to change this method, since it can't just return 's'
anymore.   We don't want to spend too much time on this since we are in the middle of another refactoring.
For now, we'll write:
```java
  @Override
  public T getDisplayInfoAt(Coordinate where) {
    //TODO this is not right.  We need to
    //look up the hit status of this coordinate
    return myDisplayInfo.getInfo(where, false);
  }
```
We have some things to do here later, so we leave a TODO for ourselves.
Now go back to RectangleShip.java and change it to be `public class RectangleShip<T> extends BasicShip<T>`

We also need to change the constructor to take a ShipDisplayInfo<T> and pass
it to the super constructor.

We'll add two quick convience constructors to RectangleShip:
```java
  public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
  }
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this(upperLeft, 1, 1, data, onHit);
  }
```
Those are the refactorings of BasicShip and RectangleShip.  Now we need
to go change our code that use BasicShip to use RectangleShip.

Basically, wherever we have `BasicShip s = new BasicShip (c);`
we would replace it with `RecangleShip<Character> s = new RectangleShip<Character>(c, 's', '*');`.
Note that without the convience constructors we just added, this would instead be:
   `RecangleShip<Character> s = new RectangleShip<Character>(c, 1, 1, new SimpleShipDisplayInfo<Character>('s', '*'));`
which is kind of a mouthful.

How do you find all of these?  go to BasicShip.java, and where it says
```java
class BasicShip
      ^^^^ put your cursor on this
```
Then do "C-c u" to find the uses of BasicShip.  You can go through those in one split of the window,
while editing the others.

We also need to change our RectangleShipTest code to add 's' and '*' to the constructors and <Character>
to the type names.

Once that is all done, hit C-c C-t to run all your tests.  If you missed any changes, you'll
have compiler errors to fix.  Once you have done that, all your tests should pass.

One quick note before we move on... Having made all those code changes, and then passed
your test cases, you should be confident that you did not mess anything up in making this changes.
How would you be feeling about your code right now if you did NOT have those test cases?
Would you be confident that you did not mess things up?  Or worried that you just introduced
hundreds of strange bugs?

Make sure you have documented your code, git commit and git push!


You finished step 10.

***

>[!IMPORTANT]
> - Add all the files to source control (GitHub) using `git add -A`
> - Commit and Push 

>[!NOTE]
> - You can now proceed to [Task 11](./task11.md)
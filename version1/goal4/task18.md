## Task 18.  Error handling for placement


Now it is time for everyone's favorite thing: error handling!

- Our players might enter an invalid Placement.  This could happen in a couple ways:
    - (1) The placement string ("A0V") could be invalid (e.g. ("8ZH", "AAV", "89V" etc).
        -> This will result in new Placement(s) throwing an exception.
    - (2) The placement might have an orientation inappropriate for the ship ("A0Q")
        -> This will result in the ship creation throwing an exception
    - (3) The placement might violate our rules (e.g., goes off the board, overlaps a ship, etc).
        -> This will result in theBoard.tryAddShip(s) returning false.
    - (4) inputReader.readLine() could return null
        -> This indicates EOF.  There isn't much we can do here, beyond end the game.
            We'll throw an EOFException here.
The README says we should "explain the problem to the player".   What exactly do we explain?

We could just say "That placement is invalid".  But that is not super descriptive.  It is easy
for us as a programmer, but not super informative to the user.  At present, we aren't really setup
to give more information: tryAddShip just returns a boolean.

- Let's go change `tryAddShip` to return a String:
   - null if the placement is OK
   - any non-null String: a description of what is wrong, suitable to show the user

Doing so will require some changes to PlacementRuleChecker and its subclasses
(their methods now need to return Strings instead of booleans).

- In particular, let us give the user one of the following messages depending on the situation
for things detected by tryAddShip:
    - "That placement is invalid: the ship goes off the top of the board."
    - "That placement is invalid: the ship goes off the bottom of the board."
    - "That placement is invalid: the ship goes off the left of the board."
    - "That placement is invalid: the ship goes off the right of the board."
    - "That placement is invalid: the ship overlaps another ship."


Note that even if multiple things are wrong, we are only going to explain things
(e.g., if the ship goes off the right edge, collides with a carrier, and collides
with a destroyer, we will just give whichever one we encounter first in checking
the rules---not all 3).


For any invalid placement string (caught by the Placement constructor), we can just
report: "That placement is invalid: it does not have the correct format."


The first thing to do is to change the PlacementRuleChecker, and its subclasses,
as well as tryAddShip.   You will need to change a variety of tests to match this change
too.   I'll note that my tests for InBoundsRuleChecker already had cases
for off the top, off the left, off the bottom, and off the right (hopefully you did too!)
Note that you may wonder about these cases---after all, it is not possible
for the user to put a ship off the top or right of a board.  However, we want
to be resilient to change: we might have other ships in the future that could cause
such a problem.  I'll also note that if you didn't test all these cases in the original
testing, going off the left or top is not possible with a placement string, but
you can make a Coordinate with a row and a column and pass that to Placement's constructor.

Once you've made these changes, run all your tests before you proceed.  I'll say that
when I did this, I auto-completed the wrong variable in my changes to tryAddShip and
broke it.  Specifically, I did
```java
    String placementProblem = placementChecker.checkPlacement(toAdd, this);
    if (placementChecker == null) {   //<--oops, autocompleted wrong variable...
```
As soon as I ran my test cases, everything related to putting a ship on the board
failed.  Since I knew that I had only made a small set of changes, I could just
look at this method, see what I did wrong, and fix it.  So much debugging time
saved by having good test cases and running them all after code changes!

Now that you have made that changes, go add the error handling.

One quick note on the case where inputReader.readLine() returns null:
we might be tempted to call System.exit, but that is generally a bad idea:
we generally prefer to throw an exception.  This basically comes down to the
fact that we can't do much about System.exit anywhere else in the code,
but we can handle exceptions.  
- Consider the following:

  - If we use System.exit, it will exit even during JUnit testing.
    There are ways around this if we absoultely need, but we
    would rather only used them when we really need it.
    
  - If we were to move this code to a context where multiple
    games with different inputs were present, we'd like
    to only terminate the relevant game, not all of them.
    For example, imagine we move this to a server setting
    and multiple clients are connected to the battleship game.
    If we have a problem on the connection to one client,
    we want our server to keep running with other
    games unaffacted.


I'll note that after adding the error handling code, we should be able
to run all of our test cases (we should NOT have changed any of the behavior
of any case that did not have an invalid placement).  We won't have 100% code
coverage because we didn't cover these error cases.

I hope you are thinking "clearly the next thing I should do is go add
testcases for these various error situations" (or that you already wrote them).

- I'll note three  things about writing test cases:
  1. We can test the EOF case by just giving an empty string ("") to the StringReader
     constructor.  Trying to read from this will produce EOF immediately.
  
  2. Don't forget that while you want 100% test coverage, just having 100%
     coverage does not mean you are done.  I found that I hit 100% coverage
     as soon as I tested with "" (EOF) and "A0Q\n"  (Invalid orientation).
     I still wanted to test with at least an invalid placement string
     (such as "AAV") and something that fails tryAddShip (e.g. goes off
     the edge of the board).  Do I need to try every tryAddShip case here?
     No---because we already tested it well elsewhere!  But I would
     like to make sure that code path works well.
     
  3. It is a good idea to update the input.txt and output.txt for
     main to include this new functionality. Quick reminder of how:
```bash
  ./gradlew  installDist
  tee src/test/resources/input.txt | ./build/install/battleship/bin/battleship | tee src/test/resources/output.txt 
```
As usual, once you are happy with your tests, make sure you have 100%
coverage and check that your documentation is complete.

Then git commit and git push!

You are now done with the placement phase of the game.

All that is left now is the "attacking" phase of the game.

When you are ready, head on to goal5/task19.txt  and get started there :)

     
***Next up is Goal 5, our last one for version 1!!!***

***

>[!IMPORTANT]
> - Add all the files to source control (GitHub) using `git add -A`
> - Commit and Push 

>[!CAUTION]
> - **Generate a Release**
>   - Go to your GitHub Repository Page, and generate a new release. 
>       - Use `Extra Credit` as Tag. 
>       - Use `Goal 4` as Release name.

>[!NOTE]
> - You can now proceed to [Goal 5](../goal5/task19.md)
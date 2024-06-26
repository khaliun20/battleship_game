Goal 4: Finish up "placement phase" of game
-------------------------------------------

We now have most of the functionality that we need to handle the placement
phase of the game (and a lot for the "attacking" phase).

We just need to put it all together, so we are going to be making
a lot of changes to App.java.  Right now, App.java just
asks for one placement, places the ship, then displays the board.

If you look back at the README, we need to do a few things here.

- Display the empty board first (we can do that already)
- Print a message explaining what to do:

```
--------------------------------------------------------------------------------
Player A: you are going to place the following ships (which are all
rectangular). For each ship, type the coordinate of the upper left
side of the ship, followed by either H (for horizontal) or V (for
vertical).  For example M4H would place a ship horizontally starting
at M4 and going to the right.  You have

2 "Submarines" ships that are 1x2 
3 "Destroyers" that are 1x3
3 "Battleships" that are 1x4
2 "Carriers" that are 1x6
--------------------------------------------------------------------------------
```
- Then we need to prompt the user for what to enter. The example
 in the README is "Player A where do you want to place a Submarine?"
 Note that this is slightly different than what we are doing now,
 and requirs both the player name ("A" or "B") and the ship type
 to place.

- We need to do some error checking/reporting.  While we
   have the PlacementRuleChecker to handle the work, we need
   to hook this in.  That's task 17.

- We need to repeat this for each ship placement.

Our current task is to handle two players (A and B).
While we are at it, we'll clean up the messages and order
to conform to the specs.  We'll leave placing all the ships
to task 17 and error handling to task 18. Then we'll be done with the placement phase.

If you were paying careful attention, you will notice that the task order switched
slightly here relative to the overview.
```
Task        Overview                     Here
------------------------------------------------------- 
16        All ships                  Two Players
17        Error Checking             All Ships
18        Two Players                Error Checking
```
Why did this happen?   When I did the overview, my thought was to put the
functionality in, then refactor for two players.  This seemed an easy
approach at the time: get everything we need, then call it twice.

However, as I looked at App.java to start task 16, I thought
"I want to refactor things out per player first."  So I changed
the order of the tasks.  Sometimes that is how things go.
As you work through this, contemplate that change of plans
and see if you agree that this makes it easier.

## Task 16.  Second Player
- Take a look at App.java.  We have here, some things that are per-player:
  - theBoard
  - view

- Some things that are shared, but *could* be per player
(i.e., they are really only shared because we are doing
 this on one terminal---per player would be better)
    - inputReader
    - out

- And something that is purely functional, so it doesn't matter if it is shared:
  - shipFactory

Let's package all of those up, along with a name ("A" or "B") into a TextPlayer
class.   We can make our two players share things that are appropriate
and have separate things that are not appropriate, e.g.
```java
Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
Board<Character> b2 = new BattleShipBoard<Character>(10, 20);
BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
V1ShipFactory factory = new V1ShipFactory();
TextPlayer p1 = new TextPlayer("A", b1, input, System.out, factory);
TextPlayer p2 = new TextPlayer("B", b2, input, System.out, factory);
```
Note that we need to do all the wrapping of System.in ONCE and share
the BufferedReader. If we have two BufferedReaders operating on the same
underlying stream, bad things will happen.

The our App constructor will change to have two Players (should it be an array
of players?  We could allow any number...)  For now, we'll make it two.
It would be very easy to change later if we wanted.

Then we are going to move all the code to read the placement into
TextPlayer.  Go create TextPlayer.java (by now, you know
to use C-c C-s to create a skeleton, so I can stop saying it, right?)

- (1) Move (cut + paste) all the instance variables (fields) from App.java into TextPlayer.
- (2) Add a String name to TextPlayer
- (3) Make a constructor in TextPlayer that initializes all the fields
- (4) Move the readPlacement and doOnePlacement methods from App to TextPlayer
- (5) Give App (which now has no fields) two TextPlayer fields: player1 and player2
and a constructor which initializes them
- (6) Change main to create both players (as describe above--they need to share a
BufferedReader, and System.out), and pass them to App's constructor.
- (7) Add a doPlacementPhase() to App which for now only does
player1.doOnePlacement()
(what about player2.doOnePlacement()?  We are going to add
that AFTER we are done refactoring.)
- (8) Add a call to app.doPlacementPhase() to the end of main.

At this point, our App and TextPlayer classes should be ok, but our AppTest
won't compile because we have changed a lot of things and the test no longer
match the code.

The first thing we should do is move `test_read_placement` to `TextPlayerText`.  
We'll need to hit C-c TAB to import the various
things in TextPlayerTest.  

We'll also need to change the `App app = nwe App(stuff)` to construct a TextPlayer, e.g.
`TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory());`
then replace `app.readPlacement(prompt)` with `player.readPlacement(prompt)`

Next, we should move test_do_one_placement from AppTest to TextPlayerTest
also.

We are once again going to have to change the App declaration
and construction to a TextPlayer.   Are you feeling like you just did
this?  Me too.  That means we should have used better abstraction
in the first place!  Let's refactor to fix that right now.  I pulled
out all this code into a helper:
```java
  private TextPlayer createTextPlayer(int w, int h, String inputData, ByteArrayOutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h);
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }
```
Then I can just create my TextPlayers in my testing with:
```java
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
```
It is slightly annoying that I still need to create bytes outside of this each time
(we need to access bytes in the test code, and can't return two values).  Yes,
there are ways around it, but this is fine.
While we are at it, we can realize that there isn't really anything specific
to ByteOutputStreams about this helper.  We could make it this instead:
```java
   private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
```
this method is slightly more general.  While we don't think we are every going to want
to run tests that print somewhere else, it doesnt' hurt to improve it in this way.

Ok, at present, TextPlayerTest should be free of red underlines that show you problems.

Let's head back over to AppTest.java.   Here the only method left is
test_main.   That one is actually already free of errors, because
it calls main() and we didnt' change that interface.

- Before we run our tests, what do you think will happen?
    - (a) Tests fail
    - (b) Tests all pass, but <100% coverage
    - (c) Tests all pass, still have 100% coverage

After you have thought about it, look at the end of the file for
the right answer.


Our refactoring just moved around code, and didn't change any functionality.
This is part of the reason we didn't put in the player2.doOnePlacement() YET.
If we did, then we would expect tests to fail.  But right now, we expect
them to pass.  Anything that failed means we refactored incorrectly
That really narrows down what we have to debug (and which test failed
would tell us a lot).  Also, if you did all the refactorings right,
how good did it feel to have all those tests pass?  How confident
are you in proceeding?  Would you have wanted to do this refactoring
if you didn't have all those tests already?

Now that we have done this refactoring, we are ready to make our changes.
I'm juts going to describe them here, and you should be quite able
to do them now:


- (1) In doOnePlacement, lets change the prompt to include the Player's name,
 e.g.,:
"Player A where do you want to place a Destroyer?"
or
"Player B where do you want to place a Destroyer?"

 I know we won't always place a destroyer, but we will add the "placing different
 ships" later.

- (2) Go fix the test in TextPlayerTest to match this change.
   Note, when I did this I accidentally printed
`"Player Awhere do you want to place a Destroyer?"   `
instead of
```
"Player A where do you want to place a Destroyer?"
        ^^^ note the space here
```
And caught it right away in testing!

Our test_main in AppTest is failing because we changed the behavior.
We could fix the expected output, but we are going to change it
again in just a minute.  Instead, let's disable the test for the moment.
Put  @Disabled on that test.  Now it should say:
```java   
  @Disabled
  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main() throws IOException {
```
Run all the tests, make sure they pass, and that you have 100% code coverage
except for App.java and AppTest.java (which will be 0).

- (3) Let's add a method `public void doPlacementPhase() throws IOException` to TextPlayer.   
    - This method should
        - (a) display the starting (empty) board
        - (b) print the instructions message (from the README,
        but also shown again near the top of this file)
        - (c) call doOnePlacement to place one ship

 Hopefully by now, I don't have to tell you to add testcases
 for this right after (or right before) you write it... But
 I'll remind you anyways.  Make sure you pass them, and
 have 100% coverage except for App.java and AppTest.java

- (4) Now, lets go back to App and make `public void doPlacementPhase() throws IOException`
do the entire placement phase for both players,
using the new doPlacementPhase() method (for player1,
then player2).

Now we are ready to go fix test_main
which would cover this behavior.  

As a quick reminder
```bash
./gradlew  installDist
tee src/test/resources/input.txt | ./build/install/battleship/bin/battleship | tee src/test/resources/output.txt
```
Now you will enter two placements, then hit control-D to finish.

A quick note about being careful here.  We have to check the output carefully when we do this.
Suppose that in your instructions from doPlacementPhase, you always printed "Player A:"
regardless of the player's name (oops!).   If you just trusted the output and saved
it, you could easily miss this bug (which is exactly the sort of thing you are testing for).
However, if you scrutinize the output carefully, you could catch it and fix it, then
re-udpate the input/output files.   Of course, if you had written some test
cases for doPlacementPhase() that checked this behavior, then you could have
caught it there (if you did so, give yourself a pat on the back!)

Now head back to AppTest, and remove the @Disabled annotation from test_main.
Run your test, and all should pass with 100% coverage again.

Horray! We are setup for two players to do the placement phase.
Next up is task 17, where we will let them place all their different ships.


Don't forget to make sure all your documentation is up to date,
git commit, and git push.

P.S.
The answer to "what will happen when we run our tests" should c.
  
Done with 16!


***

>[!IMPORTANT]
> - Add all the files to source control (GitHub) using `git add -A`
> - Commit and Push 

>[!NOTE]
> - You can now proceed to [Task 17](./task17.md)
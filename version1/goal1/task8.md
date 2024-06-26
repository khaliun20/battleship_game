## Task 8. Update BoardTextView to display boards with ships

Now that our BattleShipBoard can hold Ships, lets go make it so our BoardTextView can
display them.  This *should* be a very small change.   For me, it was a few
lines of code in the inner loop of displayMyOwnBoard.  I also took this chance
to rename a few variables (row and column instead of i and j).

If you just edit the code and run all your existing tests, they should still all pass
(after all, we haven't changed the behavior of any empty boards---we have just made
it possible to have non-empty boards and changed their behavior).   However,
we should now have <100% coverage on BoardTextView (mine is 94% right now).
This is a great example of why coverages in the 90%s are NOT enough: we have
94% coverage and no idea if our change actually works!

Lets head back over to BoardTextViewTest.java and write some tests :)
We can use a fairly small board still (e.g., I used width=4, height=3), and can then
test straightforwardly: add a ship, check if the result from displayMyOwnBoard()
matches the string we expect, repeat for a few more ships.

As always, write any documentation you need to, make sure you have 100% coverage,
and git commit and push!


You finished step 8.

***

>[!IMPORTANT]
> - Add all the files to source control (GitHub) using `git add -A`
> - Commit and Push 

>[!NOTE]
> - You can now proceed to [Task 9](./task9.md)
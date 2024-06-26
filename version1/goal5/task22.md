## Task 22. Interact with user

Now you have everything you need.  You just need to put it together.
We are going to give you only a slight bit of advice:

- Write a method for playOneTurn in TextPlayer.  It needs to take in the enemy's Board,
and BoardTextView and any other information you need.  Note it should only play one turn.

- Write a method in doAttackingPhase.  This should let player1 play a turn, then
see if player 2 has lost. Then let player 2 play a turn and see if player 1 has lost.
It should repeat this until one player has lost, then report the outcome.


Both of these methods should be pretty short, as you have already written most of the
code you need in other places, you just need to call it. 

Also, if you need a reminder of how to update your input/out files for testing main:

```bash
./gradlew  installDist
tee src/test/resources/input.txt | ./build/install/battleship/bin/battleship | tee src/test/resources/output.txt
```

Be sure you have good tests and updated documentation.  Then commit and push to submit Version 1.

***

>[!IMPORTANT]
> - Add all the files to source control (GitHub) using `git add -A`
> - Commit and Push 

>[!CAUTION]
> - **Generate a Release**
>   - Go to your GitHub Repository Page, and generate a new release. 
>       - Use `Extra Credit` as Tag. 
>       - Use `Goal 4` as Release name.

>[!TIP]
> **You are on your on you own for version 2**, but should have a strong foundation in Java,
> and be well practied in good development technique: incremental testing, refactoring, abstraction,
> task planning, object-orientation, etc.
> Bring these practices with you into Version 2, and put them to good use. 

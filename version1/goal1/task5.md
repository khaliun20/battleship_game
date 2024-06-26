## Task 5. Ship interface

Now, we are going to make the Ship interface.  Create Ship.java in your main code
directory, and use "C-x C-s" to make a skeleton.  Change "class" to interface,
and add <T> to the end of Ship, since we are going to make it generic.
(Remember, "generics" are a lot like templates: they provide parametric polymorphism).

Then we are going to add 4 methods. I've put them all here along with the documentation
that I wrote for them:
```java
/**
 * This interface represents any type of Ship in our Battleship game. It is
 * generic in typename T, which is the type of information the view needs to
 * display this ship.
 */
public interface Ship<T> {
  /**
   * Check if this ship occupies the given coordinate.
   * 
   * @param where is the Coordinate to check if this Ship occupies
   * @return true if where is inside this ship, false if not.
   */
  public boolean occupiesCoordinates(Coordinate where);

  /**
   * Check if this ship has been hit in all of its locations meaning it has been
   * sunk.
   * 
   * @return true if this ship has been sunk, false otherwise.
   */
  public boolean isSunk();

  /**
   * Make this ship record that it has been hit at the given coordinate. The
   * specified coordinate must be part of the ship.
   * 
   * @param where specifies the coordinates that were hit.
   * @throws IllegalArgumentException if where is not part of the Ship
   */
  public void recordHitAt(Coordinate where);

  /**
   * Check if this ship was hit at the specified coordinates. The coordinates must
   * be part of this Ship.
   * 
   * @param where is the coordinates to check.
   * @return true if this ship as hit at the indicated coordinates, and false
   *         otherwise.
   * @throws IllegalArgumentException if the coordinates are not part of this
   *                                  ship.
   */
  public boolean wasHitAt(Coordinate where);

  /**
   * Return the view-specific information at the given coordinate. This coordinate
   * must be part of the ship.
   * 
   * @param where is the coordinate to return information for
   * @throws IllegalArgumentException if where is not part of the Ship
   * @return The view-specific information at that coordinate.
   */
  public T getDisplayInfoAt(Coordinate where);
}
```

We can't write any test cases yet, since there is no code in an interface!
We should still hit "C-c C-t" to make sure everything compiles, then
git commit and git push.


You finished step 5.

***

>[!IMPORTANT]
> - Add all the files to source control (GitHub) using `git add -A`
> - Commit and Push 

>[!NOTE]
> - You can now proceed to [Task 6](./task6.md)
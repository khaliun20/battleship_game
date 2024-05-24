# Battleship Game

## Overview 
This is a console-based battleship game! Given a 10x20 board, each player must place 10 ships before starting the game. To win the game, a player must sink all of opponents ships.

In addition to the traditional features of the battleship game, this game has 2 more interesting functionalities! 

1. A player can move their ship position anytime during the game even is the ship is partially/fully sunk! This feature is there to keep the opponent confused!
2. A player can scan the opponent's board around a specific point. This allows the player to gain more insights of the opponents ship positions.

**Tech used:** Java, Gradle, JUnit for unit testing, Clover for test coverage

## How to Build/Run

1. run ./gradlew  installDist 
2. run ./app/build/install/app/bin/app

## Screenshots showing example play

### A player is asked to place a ship: 

<img src="imgs/1.png" alt="ship-place" width="300" height="200"/>

### A player is making is to choose between 3 options: 

A player is shown not only his board but also opponents board! If the player successfully hit the opponents ship, "*" will be shown on the board coordinate.

<img src="imgs/2.png" alt="ship-place" width="300" height="200"/>

## Testing 

JUnit and Clover has been used! 100% test coverage is achieved! 

<img src="imgs/3.png" alt="ship-place" width="300" height="200"/>

# Advent of Code Day 9
## Given
Number of players and the point value of the last marble.
The game consist of placing marbles in a circle. Here are the rules:
 - Game starts with marble number 0. Becomes current module
 - Lowest-numbered remaining marble is placed between 1 and 2 marbles clock wise. Becomes current marble.
 - If Marble to be placed is multiple of 23 then:
    - The marble is kept
    - The player adds the current marble pluse the marble 7 marbles counter-clockwise from the curent marble. 
    - The marble immediatedly clockwise from the current marble becomes current module.

## Determine
The highest score.

## Analysis
We have:
 - Marbles -> Represented by an integer
 - A board -> Represented by a LinkedList
 - Players -> Represented by an array where index is (player# - 1) and value is score

 Functions:
  - Place marble
    - If marble is multiple of 23:
     - Keep marble
     - current marble at index i - 6
     - Remove marble at index i - 7
     - If current marble is at index i, we insert into index i+2
     - Add currentMarble + removedMarble to score
 
 ## Classes
 MarbleGame
 private:
 int[] players;
 LinkedList<Integer> board;
 public:
 constructor(int players, int finalMarbleScore)
 int play();


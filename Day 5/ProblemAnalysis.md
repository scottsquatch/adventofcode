# Day 5
## Problem 1
### Given
A string of letters where each represents a unit of a polymer.
Lower case and upper case represent different polarities.

### Output
The remaining polymer after reactions are considered.
Reactions only take place if we have the same unit with differing polarities
adjacent to each other.

### Analysis
~~Looks like a recursive solution may work here

Given a start index, we look at the character to the right of the index and check if it is the same letter with differing polarity

If it is, then we remove the two letters and recursively call the method with the new string

Base cases:
* index >= length - 1
* input string is empty~~
  
If the input is large the recursive solution will not work. Alternative solution:
Keep a LinkedList of units
Iterate over the characters of the sting and compare with the end of the units LinkedList
    if the end of the units reacts with the current unit, then pop from the back of the list and increase the index

### Classes
Really only need two classes here:
AdventOfCodeDay5
public:
    static void main(String[] args)
private:
    String getPolymerFromFile(String fileName)

PolymerReactor
public:
    String reducePolymer(String polymer)
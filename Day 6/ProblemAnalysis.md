# Day 6
## Objective
Find the largest non infinite area of closest points to a given point. Distance is to be calculated using Manhattan Distance.
Note that this area does not include points which are tied in distance with other points.

## Given
A list of x,y coordinates

## Analysis
### Finding unbounded points
~~Any point that is on the outsides is unbounded. The only way I know to find these points is convex hull. See https://www.geeksforgeeks.org/convex-hull-set-1-jarviss-algorithm-or-wrapping/ for explanation of Convex Hull~~
The above plan is a bit overkill.

A more simple plan is to create a bounding rectangle using:
xmin, xmax, ymin, ymax


## Classes
Point
public:
getX()
getY()
boolean equals(obj)
private:
int x
int y
static
getOrientation(Point p1, Point p2, Point p3)
parse(String pointString)


Points
public:
addPoint(Point p1)
Set<E> getConvexHull()
Point getLeftMostPoint()

## Problem 2
### Changes
Now we want to loop over points of the region see if they are in a safe zone.

To do this we get the sum of the distances to each point in the region, if
it is below a desired value, then we add in the safe range.

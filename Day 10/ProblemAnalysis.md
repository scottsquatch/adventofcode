# AdventOfCode day 10
## Given
A set of Cartesian coordinates and velocities representing light points.

## Inputs
position=< 53777,  21594> velocity=<-5, -2>

Generally:
position=< (x), (y)> velocity=<(Vx), (Vy)>

Regex:
position=<\s*([-]{0,1}[0-9]+),\s*([-]{0,1}[0-9]+)> velocity=<\s*([-]{0,1}[0-9]+),\s*([-]{0,1}[0-9]+)>
Group Number -> Value
1 -> x
2 -> y
3 -> Vx
4 -> Vy

## Output
Will go with a brute force solution here, where we will just choose a value of seconds to draw

## program
args
0 -> input file
1 -> Number of seconds to draw

## Classes:
Point
private 
int x
int y
public:
getX();
getY();

Velocity
private
int vx
int vy

public
getVx()
getVy()

Light
private:
Point position
Velocity velocity
public
passTime(int seconds)

Sky
private
ArrayList<Light> lights
public 
drawLights()
passTime(int seconds);

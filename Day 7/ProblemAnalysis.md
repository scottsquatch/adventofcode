# Advent of Code Day 7
## Given
List of dependencies in the form of
  * Step {x} must be finished before step {y} can begin. i.e. Step G must be finished before step L can begin.
  * x,y are capital letters corresponding.
## Output
The list of steps to complete in order

## Analysis
Basically, this is just a dependency graph, but the example appears more like a Priority queue.

In the example:
Step Letter    Steps which depend on it
C              A,F
A              B,D
B              E
D              E
F              E
StepLetter     Depends on
A              C
B              A
C              
D              A
E              B,D,F
F              C
          InitialQueueContents       NextQueue Contents
Step 0 -> {}                         {C}
Step 1 -> {C}                        {A,F}
Step 2 -> {A,F}                      {B,D,F}
Step 3 -> {B,D,F}                    {D,F}
Step 4 -> {D,F}                      {F}
Step 5 -> {F}                        {E}
Step 6 -> {E}                        {}

The next steps to run can be a priority queue so that we always choose the lowest entry
Hashtable<Character, List<Character>> to store the Step Letter -> Steps which depend on it
Hashtable<Character, Set<Character>> to store the StepLetter to depends on

## Algorithm:
a) Parse input file
  * Obtain dependency (x) and step (y)
  * Update both Hashtables to sue this information
b) Put the steps which are available in priority queue (have not dependencies)
c) Take the step which is first alphabetically
d) Run the step "add to list of steps run"
e) Remove the step from the steps which depend on it
f) Repeat steps b) -> f) until we have no steps left in queue

## Classes
Dependency
private:
Character step;
Character dependency;
public
Character getStep()
Character getrDependency()
static parse(dependencyString)

DependencyGraph:
private:
  Hashtable<Character, List<Character>> dependenciesToSteps;
  Hashtable<Character, Set<Character>> stepsToDependencies;
public:
  void addDependency(Dependency dependency)
  String getOrderToExecute()

public class DependencyTester
{
  public static void main(String[] args)
  {
    System.out.println("Running tests for Dependency class...");
    testToString();
    testParse();
    System.out.println("All tests passed!");
  }

  private static void testToString()
  {
    Dependency testDep = new Dependency('L', 'G');

    assert(testDep.toString().equals("Step G must be finished before step L can begin."));
  }

  private static void testParse()
  {
    Dependency testDep = Dependency.parse("Step G must be finished before step L can begin.");

    assert(testDep.getStep() == 'L');
    assert(testDep.getDependency() == 'G');
  }
}

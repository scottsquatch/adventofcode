public class DependencyGraphTester
{
  public static void main(String[] args)
  {
    System.out.println("Running tests for DependencyGraph...");
    testGetOrderToExecute();
    System.out.println("All tests passed!");
  }

  private static void testGetOrderToExecute()
  {
    DependencyGraph graph = new DependencyGraph();

    graph.addDependency(new Dependency('C', 'F'));
    graph.addDependency(new Dependency('C', 'E'));
    graph.addDependency(new Dependency('A', 'F'));

    String orderToExecute = graph.getOrderToExecute();
    assert(orderToExecute.equals("EFAC"));
  }
}

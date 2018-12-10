public class DependencyGraphTester
{
  public static void main(String[] args)
  {
    System.out.println("Running tests for DependencyGraph...");
    testGetOrderToExecute();
    testExecuteOrderWithWorkers();
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

  private static void testExecuteOrderWithWorkers()
  {
    DependencyGraph graph = new DependencyGraph();

    graph.addDependency(new Dependency('A', 'C'));
    graph.addDependency(new Dependency('F', 'C'));
    graph.addDependency(new Dependency('B', 'A'));
    graph.addDependency(new Dependency('D', 'A'));
    graph.addDependency(new Dependency('E', 'B'));
    graph.addDependency(new Dependency('E', 'D'));
    graph.addDependency(new Dependency('E', 'F'));

    DependencyGraphExecutionOrderResult result = graph.executeWithWorkers(0, 2);

    assert(result.getSecondsTaken() == 15);
    assert(result.getExecutionOrder().equals("CABFDE"));
  }
}

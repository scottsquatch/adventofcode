import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class AdventOfCodeDay7
{
  public static void main(String[] args)
  {
    if (args.length != 1)
    {
      System.out.println("Usage: AdventOfCodeDay7 <input>");
      System.out.println("Where <input> is a file containing instructions for constructing sleigh");
    }
    else
    {
      String inputFilePath = args[0];

      try
      {
        DependencyGraph graph = readDependencyGraphFromFile(inputFilePath);

        solveProblem1(graph);
        solveProblem2(graph);
      }
      catch (FileNotFoundException e)
      {
        System.err.println("Could not find file " + inputFilePath + " please verify the path is correct");
      }
      catch (IOException e)
      {
        System.err.println("IOException occurred when trying to read file. " + e.toString());
        e.printStackTrace();
      }
    }
  }

  private static DependencyGraph readDependencyGraphFromFile(String inputFilePath)
    throws FileNotFoundException, IOException
  {
    BufferedReader input = new BufferedReader( new FileReader(inputFilePath));
    DependencyGraph graph = new DependencyGraph();
    while (input.ready())
    {
      graph.addDependency(Dependency.parse(input.readLine()));
    }

    input.close();

    return graph;
  }

  private static void solveProblem1(DependencyGraph graph)
  {
    System.out.println("Order to execute steps is " + graph.getOrderToExecute());
  }

  private static void solveProblem2(DependencyGraph graph)
  {
    // TODO
  }
}

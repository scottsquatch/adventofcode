import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class AdventOfCodeDay8
{
  public static void main(String[] args)
  {
    if (args.length != 1)
    {
      System.out.println("Usage: AdventOfCodeDay8 <input>");
      System.out.println("where <input> is License file input");
    }
    else
    {
      String inputFilePath = args[0];
      try
      {
        Scanner in = new Scanner(new File(inputFilePath));

        solveProblem1(in);
        solveProblem2(in);
      }
      catch (FileNotFoundException e)
      {
        System.err.println("Could not find file at path " + inputFilePath);
      }
    }
  }

  private static void solveProblem1(Scanner in)
  {
    Tree licenseTree = Tree.parseTree(in);

    MetadataSummationVisitor summer = new MetadataSummationVisitor();

    licenseTree.walk(summer);

    System.out.println("Sum of all metadata in license file: " + summer.getTotal());
  }

  private static void solveProblem2(Scanner in)
  {
    // TODO
  }
}

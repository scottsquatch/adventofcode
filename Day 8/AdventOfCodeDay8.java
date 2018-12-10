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

        Tree licenseTree = Tree.parseTree(in);

        solveProblem1(licenseTree);
        solveProblem2(licenseTree);
      }
      catch (FileNotFoundException e)
      {
        System.err.println("Could not find file at path " + inputFilePath);
      }
    }
  }

  private static void solveProblem1(Tree licenseTree)
  {
    MetadataSummationVisitor summer = new MetadataSummationVisitor();

    licenseTree.walk(summer);

    System.out.println("Sum of all metadata in license file: " + summer.getTotal());
  }

  private static void solveProblem2(Tree licenseTree)
  {
    System.out.println("Tree value is " + licenseTree.getTreeValue());
  }
}

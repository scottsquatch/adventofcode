import java.util.Scanner;

public class TreeTester
{
  public static void main(String[] args)
  {
    System.out.println("Testing Tree class...");
    testWalk();
    testParseTree();
    System.out.println("All tests passed!");
  }

  private static void testWalk()
  {
    Scanner in = new Scanner("2 2 0 1 5 0 1 6 10 11");

    // Expect visit to be :
    //  Parent Node
    // child 1
    // child 2
    // ...
    // child n
    Tree tree = Tree.parseTree(in);

    MetadataStringVisitor testVisitor = new MetadataStringVisitor();
    tree.walk(testVisitor);

    assert(testVisitor.toString().equals("10 11 5 6 "));
  }

  private static void testParseTree()
  {
    Scanner in = new Scanner("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2");
    Tree tree = Tree.parseTree(in);

    MetadataStringVisitor testVisitor = new MetadataStringVisitor();
    tree.walk(testVisitor);

    assert(testVisitor.toString().equals("1 1 2 10 11 12 2 99 "));
  }
}


  // Test class used to facilitate testing on Tree object
  class MetadataStringVisitor implements TreeNodeVisitor
  {
    StringBuilder builder;

    public MetadataStringVisitor()
    {
       builder = new StringBuilder();
    }

    @Override
    public void visit(int[] nodeMetadata)
    {
      for (int metadata : nodeMetadata)
      {
        System.out.print(metadata + " ");
        builder.append(metadata + " ");
      }

      System.out.println();
    }

    @Override
    public String toString()
    {
      return builder.toString();
    }
  }

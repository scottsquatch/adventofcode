public class MetadataSummationVisitorTester
{
  public static void main(String[] args)
  {
    System.out.println("Testing MetadataSummationVisitor...");
    testVisit();
    System.out.println("All tests Passed!");
  }

  private static void testVisit()
  {
    MetadataSummationVisitor visitor = new MetadataSummationVisitor();

    int[] node1Metadata = {1, 2, 3};
    int[] node2Metadata = {4, 5, 6};

    visitor.visit(node1Metadata);
    assert(visitor.getTotal() == 6);

    // Make sure that the total persists and is added to the new metadata
    visitor.visit(node2Metadata);
    assert(visitor.getTotal() == 21);
  }
}

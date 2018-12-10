/**
 * provide the sum of the total metadata for a Tree
 */
public class MetadataSummationVisitor implements TreeNodeVisitor
{
  private int total;

  public MetadataSummationVisitor()
  {
    total = 0;
  }

  @Override
  public void visit(int[] treeMetadata)
  {
    for (int metadata : treeMetadata)
    {
      total += metadata;
    }
  }

  public int getTotal()
  {
    return total;
  }
}

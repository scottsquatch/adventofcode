/**
 * Provide an interace for walking through tree by exposing metadata of each node.
 */
public interface TreeNodeVisitor
{
  void visit(int[] nodeMetadata);
}

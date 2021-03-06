import java.util.Scanner;


class TreeNode
{
  TreeNode[] mChildren;
  int[] mMetadata;

  public TreeNode(int numChildren, int numMetadata)
  {
    mChildren = new TreeNode[numChildren];
    mMetadata = new int[numMetadata];
  }
}
public class Tree
{
  private TreeNode mRoot;

  private Tree()
  {
  }

  public void walk(TreeNodeVisitor v)
  {
    walk_recursive(mRoot, v);
  }

  private void walk_recursive(TreeNode node, TreeNodeVisitor v)
  {
    // First visit self
    v.visit(node.mMetadata);

    // then visit children in order
    for (TreeNode child : node.mChildren)
    {
      walk_recursive(child, v);
    }
  }

  /**
   * Using the provider Scanner, produce a tree object. Note that the Scanner
   * is expected to adhere to the rules of a License Fle:
   * Specifically, a node consists of:

    * A header, which is always exactly two numbers:
    *    The quantity of child nodes.
    *    The quantity of metadata entries.
    * Zero or more child nodes (as specified in the header).
    * One or more metadata entries (as specified in the header).
   */
  public static Tree parseTree(Scanner in)
  {
    Tree newTree = new Tree();
    newTree.mRoot = parseTreeNode(in);

    return newTree;
  }

  private static TreeNode parseTreeNode(Scanner in)
  {
    // Consume two ints to get number of children and metadata repsectively
    int numberOfChildren = in.nextInt();
    int numberOfMetadata = in.nextInt();
    TreeNode newNode = new TreeNode(numberOfChildren, numberOfMetadata);

    // Recursively resolve children
    for (int i = 0; i < numberOfChildren; i++)
    {
      newNode.mChildren[i] = parseTreeNode(in);
    }

    // Now resolve the metadata by consuming tokens
    for (int i = 0; i < numberOfMetadata; i++)
    {
      newNode.mMetadata[i] = in.nextInt();
    }

    return newNode;
  }

  public int getTreeValue()
  {
    return getNodeValue(mRoot);
  }

  private static int getNodeValue(TreeNode node)
  {
    int value = 0;
    if (node.mChildren.length == 0)
    {
      // Sum up metadata
      for (int metadata : node.mMetadata)
      {
        value += metadata;
      }
    }
    else
    {
      // The metadata now becomes an "index" with the following special rules:
      //  - The value is the childIndex + 1
      //  - If there is not child at the index, it is skipped
      //  - If the metadata value is 0, then it is skipped
      //  - Otherwise we use the sum of the node at the index + 1
      for (int metadata : node.mMetadata)
      {
        int childIndex = metadata - 1;
        if (childIndex > -1 &&
            childIndex < node.mChildren.length)
        {
          value += getNodeValue(node.mChildren[childIndex]);
        }
      }
    }

    return value;
  }
}

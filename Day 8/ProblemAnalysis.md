# Advent of Code Day 8
## Given
A License file, which consists of a list of numbers.
The numbers represent a data structure of a tree. The format is very complicated (it is a license file after all!) but it comes down to
a series of numbers which define nodes and metadata.
Looking at the example:
2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2
A----------------------------------
    B----------- C-----------
                     D-----


 A, which has 2 child nodes (B, C) and 3 metadata entries (1, 1, 2).
 B, which has 0 child nodes and 3 metadata entries (10, 11, 12).
 C, which has 1 child node (D) and 1 metadata entry (2).
 D, which has 0 child nodes and 1 metadata entry (99).


Basically we are looking at a recursive algorithm:
- Consume next two numbers (2 and 3). This means 2 children and 3 metadata items
- resolve children (2 children)
- Child 1
  - Consume next two numbers (0 and 3). This means 0 children and 3 metadata
  - There are no children to resolve
  - consume next 3 numbers for metadata (10, 11, 12)
  - return
- Still have one more child, resolve child 2
  - Consume next two numbers (1 and 1) We have 1 child and 1 metadata item
  - Resolve children (1 child)
  - child 1
    - Consumer next two numbers (0 and 1). This means we have 0 children and 1 metadata item
    - There are no children to resolve
    - Consume next number to get metadata item (99)
    - return
  - Consume next number to get metadata item (2)
  - return
- Consume next 3 items to get metadata (1, 1 2)

Now the recursive algorithm is starting to become more clear:
- Consume 2 tokens to get Nc (number of children) and Nm (number of metadata)
  - Recursively call function to resolve children. Repeat Nc times
- Consume Nm tokens to obtain metadata

## Output
The sum of the metadata entries. Even though we only need the sum, I have a sneaking
suspicion that we will need the Tree structure for the second problem.

## Structures
TreeNodeVisitor (inteface)
void visit(Integer[] metadata)

Summer : TreeNodeVisitor

TreeNode
private:
TreeNode[] mChildren
Integer[] mMetadata
public:
TreeNode[] getChildren()
Integer[] getMetadata()

Tree
private:
TreeNode root
public:
void walk(TreeNodeVisitor v)
static:
Tree parseTreeFromLicenseFile(Scanner in)

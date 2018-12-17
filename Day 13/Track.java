import java.util.Hashtable;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Track
{
  private Hashtable<Point, Vertex> vertices;
  private HashSet<Edge> edges;
  // TODO: Add support for Carts
  public Track()
  {
    vertices = new Hashtable<Point, Vertex>();
    edges = new HashSet<Edge>();
  }

  // Adds edge from one to two with the given direction
  public void addEdge(Vertex one, Vertex two, Direction direction)
  {
    Edge newEdge = new Edge(one, two);
    if (!edges.contains(newEdge))
    {
      edges.add(newEdge);

      one.addNeighbor(direction, newEdge);
      // Need to reverse the direction for the connection from two to one
      two.addNeighbor(DirectionHelper.reverseDirection(direction), newEdge);
    }
  }

  public void addVertex(Point point, Vertex newVertex)
  {
    if (!vertices.containsKey(point))
    {
      vertices.put(point, newVertex);
    }
  }

  public Vertex getVertex(Point point)
  {
    return vertices.get(point);
  }

  public static void main(String[] args)
  {
    // Run test suite
    System.out.println("Run tests for Track class");

    Track testTrack = new Track();

    Vertex testVertex = new Vertex();
    Point p = new Point();
    p.x = 9;
    p.y = 10;

    testTrack.addVertex(p, testVertex);

    assert(testVertex == testTrack.getVertex(p));

    System.out.println("All test passed!");
  }
}

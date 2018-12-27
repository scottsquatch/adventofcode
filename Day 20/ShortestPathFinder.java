import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathFinder
{
  HashSet<Point> visited;
  HashMap<Point, Point> pathTo;

  public ShortestPathFinder(Graph graph, Point start)
  {
    dfs(graph, start);
  }

  public Point[] pathTo(Point end)
  {
    LinkedList<Point> stack = new LinkedList<Point>();

    Point next = end;
    do
    {
      stack.addFirst(next);

      next = pathTo.get(next);
    } while (next != null);

    return stack.toArray(new Point[stack.size()]);
  }

  private void dfs(Graph graph, Point start)
  {
    visited = new HashSet<Point>();
    pathTo = new HashMap<Point, Point>();

    Queue<Point> queue = new LinkedList<Point>();
    queue.add(start);

    while (queue.peek() != null)
    {
      Point p = queue.remove();

      visited.add(p);

      for (Point pAdj : graph.adj(p))
      {
        if (!visited.contains(pAdj))
        {
          queue.add(pAdj);
          pathTo.put(pAdj, p);
        }
      }
    }
  }
}

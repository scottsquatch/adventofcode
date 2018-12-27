import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathFinder
{
  HashSet<Point> visited;
  HashMap<Point, Point> pathTo;
  Point start;

  public ShortestPathFinder(Graph graph, Point start)
  {
    this.start = start;
    dfs(graph, start);
  }

  public Point[] pathTo(Point end)
  {
    if (!hasPathTo(end))
    {
      return null;
    }
    LinkedList<Point> stack = new LinkedList<Point>();

    Point next = end;
    do
    {
      stack.addFirst(next);

      //System.out.print(next + " <- ");
      next = pathTo.get(next);
      //System.out.println(next);
    } while (next != null && !start.equals(next));

    return stack.toArray(new Point[stack.size()]);
  }

  private void dfs(Graph graph, Point start)
  {
    visited = new HashSet<Point>();
    pathTo = new HashMap<Point, Point>();

    Queue<Point> queue = new LinkedList<Point>();
    queue.add(start);

    //System.out.println("Starting DFS");
    while (queue.peek() != null)
    {
      Point p = queue.remove();

      for (Point pAdj : graph.adj(p))
      {
        if (!visited.contains(pAdj))
        {
          //System.out.println("Adding " + pAdj + " to processing queue");
          queue.add(pAdj);
          pathTo.put(pAdj, p);
          visited.add(pAdj);
        }
      }
    }
    //System.out.println("DFS is Finished");
  }

  public boolean hasPathTo(Point p)
  {
    return visited.contains(p);
  }
}

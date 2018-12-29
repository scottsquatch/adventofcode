import java.util.Hashtable;
import java.util.TreeMap;
import java.util.PriorityQueue;
import java.util.LinkedList;

public class ShortestPathFinder
{
  private Cave cave;
  private CaveGraphVertex start;
  private CaveGraphVertex end;
  private Hashtable<CaveGraphVertex, CaveGraphVertex> cameFrom;
  private Hashtable<CaveGraphVertex, Integer> costSoFar;

  public ShortestPathFinder(Cave cave, Point p, Tool t, Point endP, Tool endT)
  {
    this.cave = cave;
    start = new CaveGraphVertex(p, t);
    end = new CaveGraphVertex(endP, endT);
    this.cameFrom = new Hashtable<CaveGraphVertex, CaveGraphVertex>();
    this.costSoFar = new Hashtable<CaveGraphVertex, Integer>();
    initialize();
  }

  public void initialize()
  {
    PriorityQueue<AStarQueueNode> frontier = new PriorityQueue<AStarQueueNode>();
    frontier.add(new AStarQueueNode(0, start));

    costSoFar.put(start, 0);
    while (!frontier.isEmpty())
    {
      CaveGraphVertex current = frontier.poll().vertex;
      System.out.println("Visiting: " + current);

      if (current.equals(end))
      {
        break;
      }

      for (CaveGraphVertex v : cave.adj(current))
      {
        int newCost = costSoFar.get(current) + cave.getCost(current, v);
        System.out.println(v + ": " + newCost);
        if (!costSoFar.containsKey(v) ||
            newCost < costSoFar.get(v))
        {
          costSoFar.put(v, newCost);
          int priority = newCost + hueristic(end.point, v.point);
          frontier.add(new AStarQueueNode(priority, v));
          cameFrom.put(v, current);
        }
      }
    }
  }

  private int hueristic(Point a, Point b)
  {
    return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
  }

  public int getCost()
  {
    return costSoFar.get(end);
  }

  public CaveGraphVertex[] getShortestPath()
  {
    LinkedList<CaveGraphVertex> path = new LinkedList<CaveGraphVertex>();

    CaveGraphVertex current = end;
    do
    {
        path.addFirst(current);

        current = cameFrom.get(current);
    } while (current != null);

    return path.toArray(new CaveGraphVertex[path.size()]);
  }
}

class AStarQueueNode implements Comparable<AStarQueueNode>
{
  public final int cost;
  public final CaveGraphVertex vertex;

  public AStarQueueNode(int cost, CaveGraphVertex vertex)
  {
    this.cost = cost;
    this.vertex = vertex;
  }

  @Override
  public int compareTo(AStarQueueNode other)
  {
    if (cost < other.cost)
    {
      return -1;
    }
    else if (cost > other.cost)
    {
      return 1;
    }
    else
    {
      return 0;
    }
  }
}

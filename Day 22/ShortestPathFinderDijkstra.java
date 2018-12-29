import java.util.Hashtable;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ShortestPathFinderDijkstra
{
  private Cave cave;
  private CaveGraphVertex start;
  private CaveGraphVertex end;
  private Hashtable<CaveGraphVertex, CaveGraphVertex> cameFrom;
  private Hashtable<CaveGraphVertex, Integer> costSoFar;

  public ShortestPathFinderDijkstra(Cave cave, Point p, Tool t, Point endP, Tool endT)
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
    PriorityQueue<DijkstarQueueNode> frontier = new PriorityQueue<DijkstarQueueNode>();
    frontier.add(new DijkstarQueueNode(0, start));

    costSoFar.put(start, 0);
    while (!frontier.isEmpty())
    {
      CaveGraphVertex current = frontier.poll().vertex;

      System.out.println("Visiting " + current + ", end " + end);
      if (current.equals(end))
      {
        break;
      }

      for (CaveGraphVertex next : cave.adj(current))
      {
        int newCost = costSoFar.get(current) + cave.getCost(current, next);
        System.out.println("New Cost for " + next + " is " + newCost);
        if (!costSoFar.containsKey(next) ||
            newCost < costSoFar.get(next))
        {
          costSoFar.put(next, newCost);
          cameFrom.put(next, current);
          frontier.add(new DijkstarQueueNode(newCost, next));
        }
      }
    }
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

class DijkstarQueueNode implements Comparable<DijkstarQueueNode>
{
  public final int cost;
  public final CaveGraphVertex vertex;

  public DijkstarQueueNode(int cost, CaveGraphVertex vertex)
  {
    this.cost = cost;
    this.vertex = vertex;
  }

  @Override
  public int compareTo(DijkstarQueueNode other)
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

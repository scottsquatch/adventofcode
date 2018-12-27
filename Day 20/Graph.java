import java.util.TreeMap;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.ArrayList;

public class Graph
{
  private TreeSet<Point> vertices;
  private TreeMap<Point, HashMap<Direction, Edge>> edges;

  public Graph()
  {
    vertices = new TreeSet<Point>();
    edges = new TreeMap<Point, HashMap<Direction, Edge>>();
  }

  public void addVertex(Point v)
  {
    if (!vertices.contains(v))
    {
      vertices.add(v);
    }
  }

  public boolean hasVertex(Point v)
  {
    return vertices.contains(v);
  }

  public void addEdge(Point u, Point v, Direction dir)
  {
      Edge e1 = new Edge(u, v);
      Edge e2 = new Edge(v, u);

      if (!hasEdge(u, dir))
      {
        HashMap<Direction, Edge> edgeMap = edges.get(u);
        if (edgeMap == null)
        {
          edgeMap = new HashMap<Direction, Edge>();
        }

        edgeMap.put(dir, e1);

        edges.put(u, edgeMap);
      }

      Direction opp = Direction.getOpposite(dir);
      if (!hasEdge(v, opp))
      {
        HashMap<Direction, Edge> edgeMap = edges.get(v);
        if (edgeMap == null)
        {
          edgeMap = new HashMap<Direction, Edge>();
        }

        edgeMap.put(opp, e2);
        edges.put(v, edgeMap);
      }
  }

  public Edge getEdge(Point u, Direction dir)
  {
    return edges.get(u).get(dir);
  }

  public boolean hasEdge(Point u, Direction dir)
  {
    HashMap<Direction, Edge> edgeMap = edges.get(u);
    return edgeMap != null && edgeMap.containsKey(dir);
  }

  public Point[] V()
  {
    return vertices.toArray(new Point[vertices.size()]);
  }

  public Point[] adj(Point u)
  {
    HashMap<Direction, Edge> edgeMap = edges.get(u);

    if (edgeMap == null)
    {
      return new Point[0];
    }

    ArrayList<Point> adj = new ArrayList<Point>();
    for (Edge e : edgeMap.values())
    {
      adj.add(e.getOther(u));
    }

    return adj.toArray(new Point[adj.size()]);
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();

    for (Point v : vertices)
    {
      builder.append(v + " -> ");

      HashMap<Direction, Edge> edgeMap = edges.get(v);
      if (edgeMap != null)
      {
        for (Direction d : edgeMap.keySet())
        {
          builder.append("{ ");
          builder.append(d);
          builder.append(", ");
          builder.append("(" + edgeMap.get(d).getOther(v) + ")");
          builder.append("}  ");
        }
      }

      builder.append("\n");
    }

    return builder.toString();
  }
}

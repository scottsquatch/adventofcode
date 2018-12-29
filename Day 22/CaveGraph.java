import java.util.HashSet;
import java.util.Hashtable;

public class CaveGraph
{
  private HashSet<CaveGraphVertex> vertices;
  private Hashtable<CaveGraphVertex, HashSet<CaveGraphEdge>> edges;

  public CaveGraph()
  {
    vertices = new HashSet<CaveGraphVertex>();
    edges = new Hashtable<CaveGraphVertex, HashSet<CaveGraphEdge>>();
  }

  public void addVertex(Point p, Tool t)
  {
    CaveGraphVertex v = new CaveGraphVertex(p, t);

    if (!vertices.contains(v))
    {
      vertices.add(v);
    }
  }

  public void addEdge(CaveGraphVertex u, CaveGraphVertex v, double weight)
  {
    CaveGraphEdge e = new CaveGraphEdge(u, v, weight);

    HashSet<CaveGraphEdge> edgeSet = edges.get(u);
    if (edgeSet == null)
    {
      edgeSet = new HashSet<CaveGraphEdge>();
    }

    edgeSet.add(e);
    edges.put(u, edgeSet);
  }

  public CaveGraphVertex[] V()
  {
    return vertices.toArray(new CaveGraphVertex[vertices.size()]);
  }

  public CaveGraphVertex[] adj(CaveGraphVertex v)
  {
    HashSet<CaveGraphVertex> neighbors = new HashSet<CaveGraphVertex>();

    for (CaveGraphEdge e : edges.get(v))
    {
      neighbors.add(e.getOther(v));
    }

    return neighbors.toArray(new CaveGraphVertex[neighbors.size()]);
  }

  public double getScore(CaveGraphVertex u, CaveGraphVertex v)
  {
    for (CaveGraphEdge e : edges.get(u))
    {
      if (e.getOther(u).equals(v))
      {
        return e.weight;
      }
    }

    throw new IllegalArgumentException(u + " and " + v + " are not connected.");
  }
}

class CaveGraphVertex
{
  public Point point;
  public Tool tool;

  public CaveGraphVertex(Point point, Tool tool)
  {
    this.point = point;
    this.tool = tool;
  }

  @Override
  public String toString()
  {
    return point.toString() + ": " + tool;
  }

  @Override
  public int hashCode()
  {
    int hash = 27;
    hash += 27 * point.hashCode();
    hash += 27 * tool.hashCode();

    return hash;
  }

  @Override
  public boolean equals(Object other)
  {
    if (this == other)
    {
      return true;
    }
    else if (other == null)
    {
      return false;
    }
    else if (getClass() != other.getClass())
    {
      return false;
    }

    CaveGraphVertex otherVertex = (CaveGraphVertex)other;

    return point.equals(otherVertex.point) && tool == otherVertex.tool;
  }
}

class CaveGraphEdge
{
  public CaveGraphVertex u;
  public CaveGraphVertex v;
  public double weight;

  public CaveGraphEdge(CaveGraphVertex u, CaveGraphVertex v, double weight)
  {
    this.u = u;
    this.v = v;
    this.weight = weight;
  }

  public CaveGraphVertex getOther(CaveGraphVertex other)
  {
    if (other.equals(u))
    {
      return v;
    }
    else if (other.equals(v))
    {
      return u;
    }

    // Other not found
    throw new IllegalArgumentException(other + " is not a valid vertex to this edge");
  }

  @Override
  public String toString()
  {
    return "u: " + u + ", v: " + v + ", w: " + weight;
  }

  @Override
  public boolean equals(Object other)
  {
    if (this == other)
    {
      return true;
    }
    else if (other == null)
    {
      return false;
    }
    else if (getClass() != other.getClass())
    {
      return false;
    }

    CaveGraphEdge otherEdge = (CaveGraphEdge)other;

    return u.equals(otherEdge.u) && v.equals(otherEdge.v);
  }
}

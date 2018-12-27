public class Edge
{
  private Point u;
  private Point v;

  public Edge(Point u, Point v)
  {
    this.u = u;
    this.v = v;
  }

  public Point getOther(Point p)
  {
    if (p.equals(u))
    {
      return v;
    }
    else if (p.equals(v))
    {
      return u;
    }

    throw new IllegalArgumentException(p + " is not connected to this edge");
  }

  @Override
  public int hashCode()
  {
    int hash = 27;

    hash += 27 * u.hashCode();
    hash += 27 * v.hashCode();

    return hash;
  }
}

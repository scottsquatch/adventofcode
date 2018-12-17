public class Edge
{
  private Vertex one, two;

  public Edge(Vertex one, Vertex two)
  {
    this.one = one;
    this.two = two;
  }

  public Vertex getNeighbor(Vertex other)
  {
    if (other == this.one)
    {
      return this.two;
    }
    else if (other == this.two)
    {
      return this.one;
    }
    else
    {
      return null;
    }
  }

  @Override
  public int hashCode()
  {
    return one.hashCode() + two.hashCode();
  }
}

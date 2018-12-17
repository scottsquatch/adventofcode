import java.util.Hashtable;

public class Vertex
{

  private Hashtable<Direction, Edge> neighbors;
  private boolean hasCart;

  public Vertex()
  {
    this.neighbors = new Hashtable<Direction, Edge>();
    this.hasCart = false;
  }

  public boolean hasCart()
  {
    return hasCart;
  }

  public void addCart()
  {
    hasCart = true;
  }

  public void removeCart()
  {
    hasCart = false;
  }

  public void addNeighbor(Direction direction, Edge neighborEdge)
  {
    neighbors.put(direction, neighborEdge);
  }

  public boolean hasNeighbor(Direction direction)
  {
    return neighbors.containsKey(direction);
  }

  public Edge getNeighbor(Direction direction)
  {
    return neighbors.get(direction);
  }

  @Override
  public int hashCode()
  {
    return this.neighbors.hashCode();
  }
}

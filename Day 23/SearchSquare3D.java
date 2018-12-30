// Idea take from https://raw.githack.com/ypsu/experiments/master/aoc2018day23/vis.html and translated to java
public class SearchSquare3D implements Comparable<SearchSquare3D>
{
  private static final Point3D origin = new Point3D(0,0,0);
  public final Point3D bottomLeft;
  public final Point3D topRight;
  public int nanobotsInRange;
  public int size;
  public SearchSquare3D(Point3D bottomLeft, int size,
    Point3D[] positions, Nanobot[] bots)
  {
    this.bottomLeft = bottomLeft;
    this.topRight = new Point3D(bottomLeft.x + size - 1, bottomLeft.y + size - 1, bottomLeft.z + size - 1);
    this.size = size;

    populateNanobotsInRange(positions, bots);
  }

  public int getDistanceToOrigin()
  {
    return (int)origin.manhattanDistanceTo(bottomLeft);
  }

  public int getNanobotsInRange()
  {
    return nanobotsInRange;
  }

  private void populateNanobotsInRange(Point3D[] positions, Nanobot[] bots)
  {
    nanobotsInRange = 0;
    for (int i = 0; i < positions.length; i++)
    {
      if (withinSquare(positions[i], bots[i].radius))
      {
        nanobotsInRange++;
      }
    }
  }

  private boolean withinSquare(Point3D botPosition, int botRange)
  {
    int totalDistance = 0;
    // X
    totalDistance += distanceFromSquareAxis(bottomLeft.x, topRight.x, botPosition.x);
    // Y
    totalDistance += distanceFromSquareAxis(bottomLeft.y, topRight.y, botPosition.y);
    // Z
    totalDistance += distanceFromSquareAxis(bottomLeft.z, topRight.z, botPosition.z);

    return totalDistance <= botRange;
  }

  private int distanceFromSquareAxis(int min, int max, int value)
  {
    if (value < min)
    {
      return min - value;
    }
    else if (value > max)
    {
      return value - max;
    }
    else
    {
      // Within range
      return 0;
    }
  }

  @Override
  public int compareTo(SearchSquare3D other)
  {
    if (nanobotsInRange > other.nanobotsInRange)
    {
      return -1;
    }
    else if (nanobotsInRange < other.nanobotsInRange)
    {
      return 1;
    }
    else if (getDistanceToOrigin() < other.getDistanceToOrigin())
    {
      return -1;
    }
    else if (getDistanceToOrigin() > other.getDistanceToOrigin())
    {
      return 1;
    }
    else if (size < other.size)
    {
      return -1;
    }
    else if (size > other.size)
    {
      return 1;
    }
    // Otherwise they are equal
    return 0;
  }

  @Override
  public String toString()
  {

    return bottomLeft + " " + topRight + ", size: " + size +  ", nanotbots in range: " + nanobotsInRange;
  }
}

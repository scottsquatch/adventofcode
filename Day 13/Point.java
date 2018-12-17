public class Point implements Comparable<Point>
{
  public int x;
  public int y;

  public int compareTo(Point other)
  {
    if (y < other.y)
    {
      return -1;
    }
    else if (y > other.y)
    {
      return 1;
    }
    else if (x < other.x)
    {
      return -1;
    }
    else if (x > other.x)
    {
      return 1;
    }
    else
    {
      return 0;
    }
  }

  @Override
  public int hashCode()
  {
    return x * 515142 + y * 2313524;
  }

  @Override
  public String toString()
  {
    return "(" + x + "," + y + ")";
  }

  @Override
  public boolean equals(Object other)
  {
    if (this == other)
    {
      return true;
    }
    else if (other == null ||
            getClass() != other.getClass())
    {
      return false;
    }

    // We know that we have the same object so the cast should be safe
    Point otherPoint = (Point)other;

    return this.x == otherPoint.x && this.y == otherPoint.y;
  }
}

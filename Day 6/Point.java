/**
 * Class representing a poitn in the Cartesian plane
 */
public class Point
{
  private int x;
  private int y;

  public Point(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null)
    {
      return false;
    }
    // Check class type
    else if (!(obj instanceof Point))
    {
      return false;
    }
    // Check reference
    else if (this == obj)
    {
      return true;
    }
    else
    {
      Point p = (Point)obj;
      // Othewise check x and y coordinates
      return this.x == p.x &&
             this.y == p.y;
    }
  }

  public int getManhattanDistanceTo(Point otherPoint)
  {
    return Math.abs(x - otherPoint.getX()) + Math.abs(y - otherPoint.getY());
  }

  /**
   * Get the orientation of the three points.
   *
   * negative -> Points are counterclockwise
   * 0 -> Points are colinear
   * positive -> Points are clockwise
   */
  public static int getOrientation(Point p1, Point p2, Point p3)
  {
    // Equation taken from
    // https://www.geeksforgeeks.org/orientation-3-ordered-points/
    return (p2.y - p1.y) * (p3.x - p2.x) - (p3.y - p2.y) * (p2.x - p1.x);
  }

  /**
   * Parses string of the form x, y to point
   */
  public static Point parse(String pointString)
  {
    if (pointString == null)
    {
      throw new IllegalArgumentException("String was null");
    }

    pointString = pointString.trim();
    if (pointString.isEmpty())
    {
      throw new IllegalArgumentException("String was empty");
    }

    int indexOfSeparator = pointString.indexOf(",");
    if (indexOfSeparator <= -1)
    {
      throw new IllegalArgumentException(pointString + " was not of form x, y");
    }

    // All good now parse points
    int x = Integer.parseInt(pointString.substring(0, indexOfSeparator).trim());
    int y = Integer.parseInt(pointString.substring(indexOfSeparator + 1).trim());

    return new Point(x, y);
  }
}

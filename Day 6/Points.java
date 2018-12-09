import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

/**
 * Represents a group of points in the Cartesian plane
 */
public class Points
{
  private ArrayList<Point> mPoints;

  public Points()
  {
    mPoints = new ArrayList<Point>();
  }

  public Points(Collection<Point> collection)
  {
    this();

    for (Point p : collection)
    {
      addPoint(p);
    }
  }

  public void addPoint(Point p)
  {
    mPoints.add(p);
  }

  /**
   * Return the convex hull of the points.
   * See https://www.geeksforgeeks.org/convex-hull-set-1-jarviss-algorithm-or-wrapping/
   */
  public Set<Point> getConvexHull()
  {
    if (mPoints.size() < 3)
    {
      return new HashSet<Point>();
    }

    int p, q, initialP;
    HashSet<Point> convexHull = new HashSet<Point>();
    initialP = getLeftMostPointIndex();

    p = initialP;
    do
    {
      convexHull.add(mPoints.get(p));

      q = (p + 1) % mPoints.size();

      for (int r = 0; r < mPoints.size(); r++)
      {
        if ((Point.getOrientation(mPoints.get(p), mPoints.get(r), mPoints.get(q))
            < 0))
        {
          // Found better left turn
          q = r;
        }
      }

      p = q;
    } while (p != initialP);

    return convexHull;
  }

  public Points getBoundingRectangle()
  {
    Points rectangle = new Points();
    // Need at least 4 point
    if (mPoints.size() >= 4)
    {
      int xmin = Integer.MAX_VALUE;
      int xmax = Integer.MIN_VALUE;
      int ymin = Integer.MAX_VALUE;
      int ymax = Integer.MIN_VALUE;

      for (Point p : mPoints)
      {
        if (p.getX() < xmin)
        {
          xmin = p.getX();
        }
        else if (p.getX() > xmax)
        {
          xmax = p.getX();
        }

        if (p.getY() < ymin)
        {
          ymin = p.getY();
        }
        else if (p.getY() > ymax)
        {
          ymax = p.getY();
        }
      }

      System.out.println("xmin: " + xmin + ", xmax: " + xmax + ", ymin: " + ymin + ", ymax: " + ymax);

      for (int y = ymin; y <= ymax; y++)
      {
        for (int x = xmin; x <= xmax; x++)
        {
          rectangle.addPoint(new Point(x, y));
        }
      }
    }

    return rectangle;
  }

  public Point[] toArray()
  {
    return mPoints.toArray(new Point[0]);
  }

  int getLeftMostPointIndex()
  {
    if (mPoints.isEmpty())
    {
      return -1;
    }

    int indexLowestX = 0;
    for (int i = 1; i < mPoints.size(); i++)
    {
      if (mPoints.get(i).getX() < mPoints.get(indexLowestX).getX())
      {
        indexLowestX = i;
      }
    }

    return indexLowestX;
  }


}

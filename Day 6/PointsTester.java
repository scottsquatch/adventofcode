import java.util.Set;

public class PointsTester
{
  public static void main(String[] args)
  {
    System.out.println("Starting tests for Points class...");
    testGetConvexHull();
    testToArray();
    testGetBoundingRectangle();
    System.out.println("All tests Passed!");
  }

  private static void testGetConvexHull()
  {
    Point p1 =new Point(0, 3);
    Point p2 =new Point(2, 3);
    Point p3 =new Point(1, 1);
    Point p4 =new Point(2, 1);
    Point p5 =new Point(3, 0);
    Point p6 =new Point(0, 0);
    Point p7 =new Point(3, 3);

    Points points = new Points();
    // test that we need more than three points
    assert(points.getConvexHull().isEmpty());

    points.addPoint(p1);
    assert(points.getConvexHull().isEmpty());

    points.addPoint(p2);
    assert(points.getConvexHull().isEmpty());

    // Now we have 3 points, should return all three
    points.addPoint(p3);
    Set<Point> convexHull = points.getConvexHull();
    assert(convexHull.size() == 3);
    assert(convexHull.contains(p1));
    assert(convexHull.contains(p2));
    assert(convexHull.contains(p3));

    // Add the rest of the points for the non-trivial case
    points.addPoint(p4);
    points.addPoint(p5);
    points.addPoint(p6);
    points.addPoint(p7);

    convexHull = points.getConvexHull();
    assert(convexHull.size() == 4);
    assert(convexHull.contains(p1));
    assert(convexHull.contains(p6));
    assert(convexHull.contains(p5));
    assert(convexHull.contains(p7));
  }

  private static void testToArray()
  {
    Point p1 = new Point(0,2);
    Point p2 = new Point(4,4);
    Points p = new Points();
    p.addPoint(p1);
    p.addPoint(p2);

    Point[] pointArray = p.toArray();

    assert(pointArray.length == 2);
    assert(pointArray[0].equals(p1));
    assert(pointArray[1].equals(p2));
  }

  private static void testGetBoundingRectangle()
  {
    Point p1 =new Point(0, 3);
    Point p2 =new Point(2, 3);
    Point p3 =new Point(1, 1);
    Point p4 =new Point(2, 1);
    Point p5 =new Point(3, 0);
    Point p6 =new Point(0, 0);
    Point p7 =new Point(3, 3);

    Points points = new Points();
    points.addPoint(p1);
    points.addPoint(p2);
    points.addPoint(p3);
    points.addPoint(p4);
    points.addPoint(p5);
    points.addPoint(p6);
    points.addPoint(p7);

    Points rectangle = points.getBoundingRectangle();
    Point[] rectanglePointArray = rectangle.toArray();

    // Rectangle should be a 3x3 rectangle from 0,0 to 3,3
    assert(rectanglePointArray.length == (3 * 3));
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 3; j++)
      {
        Point p = rectanglePointArray[(i * 3) + j];
        assert(i == p.getY());
        assert(j == p.getX());
      }
    }
  }
}

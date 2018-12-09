public class PointTester
{
  public static void main(String[] args)
  {
    System.out.println("Starting tests of Point class");
    testGetters();
    testEquals();
    testGetOrientation();
    testParse();
    System.out.println("All tests passed!");
  }

  public static void testGetters()
  {
    Point p = new Point(4, 5);
    assert(p.getX() == 4);
    assert(p.getY() == 5);
  }

  public static void testEquals()
  {
    Point p1 = new Point(4, 5);
    Point p2 = new Point(7, 5);
    Point p3 = new Point(4, 7);
    Point p4 = new Point(4, 5);

    // Null comparison
    assert(!p1.equals(null));
    // Class type mismatch
    assert(!p1.equals(new Object()));
    // Same reference
    assert(p1.equals(p1));
    // Differing x
    assert(!p1.equals(p2));
    // Differing y
    assert(!p1.equals(p3));
    // Same coordinates
    assert(p1.equals(p4));
  }

  public static void testGetOrientation()
  {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(4, 4);
    Point p3 = new Point(1, 2);

    // Colinear
    assert(Point.getOrientation(p1, p1, p1) == 0);
    // clockwise
    assert(Point.getOrientation(p1, p3, p2) > 0);
    // counterclockwise
    assert(Point.getOrientation(p1, p2, p3) < 0);
  }

  public static void testParse()
  {
    // Null String
    Exception testException = null;
    try
    {
      Point.parse(null);
    }
    catch (Exception e)
    {
      testException = e;
    }
    assert(testException instanceof IllegalArgumentException);
    // Empty String
    testException = null;
    try
    {
      Point.parse("  ");
    }
    catch (Exception e)
    {
      testException = e;
    }
    assert(testException instanceof IllegalArgumentException);
    // Invalid format
    testException = null;
    try
    {
      Point.parse("13");
    }
    catch (Exception e)
    {
      testException = e;
    }
    assert(testException instanceof IllegalArgumentException);
    // Valid format
    Point p = Point.parse("4, 5");
    assert(p.getX() == 4);
    assert(p.getY() == 5);
  }

  private static void testGetManhattanDistanceTo()
  {
    Point p1 = new Point(4,5);
    Point p2 = new Point(6,7);

    int distanceFromP1ToP2 = p1.getManhattanDistanceTo(p2);
    assert(distanceFromP1ToP2 == (Math.abs(4 - 6) + Math.abs(5 - 7)));
    // The distance should matter when we swap the order
    assert(p2.getManhattanDistanceTo(p1) == distanceFromP1ToP2);
  }
}

import java.util.Hashtable;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Set;

public class AdventOfCodeDay6
{
  public static void main(String[] args)
  {
    if (args.length != 1)
    {
      System.out.println("Usage: AdventOfCodeDay6 <input>");
      System.out.println("Where <input> is a input file holding list of points");
    }
    else
    {
      String inputFilePath = args[0];

      try
      {
        ArrayList<Point> pointsList = readPointsFromFile(inputFilePath);

        solveProblem1(pointsList);
        solveProblem2(pointsList);
      }
      catch (FileNotFoundException e)
      {
        System.err.println("Could not find file " + inputFilePath + ". Please check path.");
      }
      catch (IOException e)
      {
        System.err.println("Exception occurred: " + e.toString());
        e.printStackTrace();
      }
    }
  }

  private static ArrayList<Point> readPointsFromFile(String inputFilePath)
    throws FileNotFoundException, IOException
  {
    ArrayList<Point> points = new ArrayList<Point>();

    BufferedReader input = new BufferedReader(
      new FileReader(inputFilePath)
    );

    while (input.ready())
    {
      points.add(Point.parse(input.readLine()));
    }

    return points;
  }

  private static void solveProblem1(ArrayList<Point> pointsList)
  {
    Hashtable<Integer, Integer> pointAreaTable = new Hashtable<Integer, Integer>();
    Points points = new Points();

    for (int i = 0; i < pointsList.size(); i++)
    {
      points.addPoint(pointsList.get(i));
      pointAreaTable.put(i, 0);
    }

    Set<Point> convexHull = points.getConvexHull();

    for (Point p1 : points.getBoundingRectangle().toArray())
    {
      int lowestDistance = Integer.MAX_VALUE;
      int lowestDistanceIndex = -1;
      boolean multipleMatches = false;
      for (int i = 0; i < pointsList.size(); i++)
      {
        Point p2 = pointsList.get(i);
        int distance = p1.getManhattanDistanceTo(p2);
        if (distance < lowestDistance)
        {
          lowestDistance = distance;
          lowestDistanceIndex = i;
          multipleMatches = false;
        }
        else if (distance == lowestDistance)
        {
          multipleMatches = true;
        }
      }

      if (!multipleMatches)
      {
        pointAreaTable.put(lowestDistanceIndex,
        pointAreaTable.get(lowestDistanceIndex) + 1);
      }
    }

    int largestArea = Integer.MIN_VALUE;
    for (Integer index : pointAreaTable.keySet())
    {
      Point p = pointsList.get(index);
      if (!convexHull.contains(p) &&
          pointAreaTable.get(index) > largestArea)
      {
        largestArea = pointAreaTable.get(index);
      }
    }

    System.out.println("The largest area is " + largestArea);

    System.out.println("Convex Hull:");
    for (Point p : convexHull)
    {
      System.out.println("(" + p.getX() + ", " + p.getY() + ")");
    }

    System.out.println("Areas by point");
    for (int i = 0; i < pointsList.size(); i++)
    {
      Point p = pointsList.get(i);
      if (pointAreaTable.containsKey(i))
      {
        System.out.print("(" + p.getX() + ", " + p.getY() + ")");
        System.out.println(": " + pointAreaTable.get(i));
      }
    }
  }

  private static void solveProblem2(ArrayList<Point> pointsList)
  {
    // TODO
  }
}

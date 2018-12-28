import java.util.Hashtable;

public class Cave
{
  private static final Point CAVE_ENTRANCE_POINT = new Point(0,0);
  private Hashtable<Point, CaveCell> map;
  private Point targetLocation;

  public Cave(int depth, Point targetLocation)
  {
    map = new Hashtable<Point, CaveCell>();
    this.targetLocation = new Point(targetLocation.x, targetLocation.y);

    populateMap(depth);
  }

  private void populateMap(int depth)
  {
    // Start with (0,0) and targetLocation as those geologic index is known
    map.put(new Point(0,0), new CaveCell(0, depth));
    map.put(targetLocation, new CaveCell(0, depth));

    // Now determine points on x/y axis
    for (int x = 1; x <= targetLocation.x; x++)
    {
      map.put(new Point(x, 0), new CaveCell(x * 16807L, depth));
    }

    for (int y = 1; y <= targetLocation.y; y++)
    {
      map.put(new Point(0, y), new CaveCell(y * 48271, depth));
    }

    // now determine the rest
    for (int y = 1; y <= targetLocation.y; y++)
    {
      for (int x = 1; x <= targetLocation.x; x++)
      {
        Point p = new Point(x, y);

        if (!p.equals(targetLocation))
        {
          Point left = new Point(p.x - 1, p.y);
          Point up = new Point(p.x, p.y - 1);
          long geologicIndex = map.get(left).erosionLevel * map.get(up).erosionLevel;
          map.put(p, new CaveCell(geologicIndex, depth));
        }
      }
    }
  }

  public long getTotalRisk()
  {
    long sum = 0;
    for (int y = 0; y <= targetLocation.y; y++)
    {
      for (int x = 0; x <= targetLocation.x; x++)
      {
        sum += map.get(new Point(x, y)).type.getRiskScore();
      }
    }

    return sum;
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    for (int y = 0; y <= targetLocation.y; y++)
    {
      for (int x = 0; x <= targetLocation.x; x++)
      {
        Point p = new Point(x, y);
        if (p.equals(CAVE_ENTRANCE_POINT))
        {
          builder.append('M');
        }
        else if (p.equals(targetLocation))
        {
          builder.append('T');
        }
        else
        {
          builder.append(map.get(p).toString());
        }
      }

      builder.append("\n");
    }

    return builder.toString();
  }
}

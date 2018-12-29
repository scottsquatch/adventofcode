import java.util.Hashtable;
import java.util.ArrayList;

public class Cave
{
  public static final Point CAVE_ENTRANCE_POINT = new Point(0,0);
  public static final Tool INITIAL_TOOL = Tool.TORCH;
  private static final int PADDING = 150;
  private Hashtable<Point, CaveCell> map;
  private int depth;
  private Point targetLocation;

  public Cave(int depth, Point targetLocation)
  {
    map = new Hashtable<Point, CaveCell>();
    this.depth = depth;
    this.targetLocation = new Point(targetLocation.x, targetLocation.y);

    populateMap(depth);
  }

  private void populateMap(int depth)
  {
    // Start with (0,0) and targetLocation as those geologic index is known
    map.put(CAVE_ENTRANCE_POINT, new CaveCell(0, depth));
    map.put(targetLocation, new CaveCell(0, depth));

    // Now determine points on x/y axis
    for (int x = 1; x <= targetLocation.x + PADDING; x++)
    {
      map.put(new Point(x, 0), new CaveCell(x * 16807L, depth));
    }

    for (int y = 1; y <= targetLocation.y + PADDING; y++)
    {
      map.put(new Point(0, y), new CaveCell(y * 48271, depth));
    }

    // now determine the rest
    for (int y = 1; y <= targetLocation.y + PADDING; y++)
    {
      for (int x = 1; x <= targetLocation.x + PADDING; x++)
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

  public CaveCell get(Point p)
  {
    if (p.x < 0 || p.y < 0)
    {
      throw new IllegalArgumentException(p + " is out of bounds");
    }

    return map.get(p);
  }

  public Point getTargetPoint()
  {
    return targetLocation;
  }

  public Point[] adj(Point p)
  {
    ArrayList<Point> adjPoints = new ArrayList<Point>(4);

    // Always have right and down
    adjPoints.add(new Point(p.x + 1, p.y));
    adjPoints.add(new Point(p.x, p.y + 1));

    if (p.x > 0)
    {
      adjPoints.add(new Point(p.x - 1, p.y));
    }

    if (p.y > 0)
    {
      adjPoints.add(new Point(p.x, p.y - 1));
    }

    return adjPoints.toArray(new Point[adjPoints.size()]);
  }

  public CaveGraphVertex[] adj(CaveGraphVertex gcm)
  {
    ArrayList<CaveGraphVertex> adjGcm = new ArrayList<CaveGraphVertex>(8);
    CaveCell gcmCell = get(gcm.point);

    // Add tool change
    adjGcm.add(new CaveGraphVertex(gcm.point, gcmCell.type.getOtherValidTool(gcm.tool)));

    // Now only moves
    for (Point p : adj(gcm.point))
    {
      CaveCell c = get(p);

      if (c.type.validTool(gcm.tool))
      {
        adjGcm.add(new CaveGraphVertex(p, gcm.tool));
      }
      /*else
      {
        for (Tool t : c.type.validTools())
        {
          if (t != gcm.tool &&
             c.type.validTool(t) &&
             gcmCell.type.validTool(t))
           {
             adjGcm.add(new CaveGraphVertex(p, t));
           }
        }
      }*/
    }

    return adjGcm.toArray(new CaveGraphVertex[adjGcm.size()]);
  }

  public int getCost(CaveGraphVertex u, CaveGraphVertex v)
  {
    int cost = 0;
    if (u.tool != v.tool)
    {
      cost += 7;
    }

    if (!u.point.equals(v.point))
    {
      cost += 1;
    }

    return cost;
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

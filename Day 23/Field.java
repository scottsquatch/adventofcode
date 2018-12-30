import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Field
{
  private static Pattern NANOBOT_REGEX = Pattern.compile("pos=<(-*[0-9]+),(-*[0-9]+),(-*[0-9]+)>, r=([0-9]+)");

  private Point3D[] positions;
  private Nanobot[] bots;
  private Pyramid3D[] rangePyramids;
  private int strongestBot;
  private Point3D min;
  private Point3D max;

  public Field(String[] lines)
  {
    positions = new Point3D[lines.length];
    bots = new Nanobot[lines.length];
    rangePyramids = new Pyramid3D[lines.length];
    strongestBot = -1;
    int xMin = Integer.MAX_VALUE, yMin = Integer.MAX_VALUE, zMin = Integer.MAX_VALUE;
    int xMax = Integer.MIN_VALUE, yMax = Integer.MIN_VALUE, zMax = Integer.MIN_VALUE;

    for (int i = 0; i < lines.length; i++)
    {
      String line = lines[i];
      Matcher matcher = NANOBOT_REGEX.matcher(line);

      if (matcher.matches())
      {
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        int z = Integer.parseInt(matcher.group(3));
        int r = Integer.parseInt(matcher.group(4));

        positions[i] = new Point3D(x,y,z);
        bots[i] = new Nanobot(r);
        rangePyramids[i] = new Pyramid3D(positions[i], bots[i].radius);

        if (strongestBot == -1 ||
            r > bots[strongestBot].radius)
        {
          strongestBot = i;
        }

        // Update max/mins
        xMin = Math.min(xMin, x - r);
        yMin = Math.min(yMin, y - r);
        zMin = Math.min(zMin, z - r);

        xMax = Math.max(xMax, x + r);
        yMax = Math.max(yMax, y + r);
        zMax = Math.max(zMax, z + r);
      }
    }

    min = new Point3D(xMin, yMin, zMin);
    max = new Point3D(xMax, yMax, zMax);

    System.out.println("Min: " + min + ", Max: " + max);
  }

  public int botsInRangeOfStrongest()
  {
    int count = 0;
    Point3D strongestBotPosition = positions[strongestBot];
    Nanobot strongestNanobot = bots[strongestBot];
    for (Point3D p : positions)
    {
      if (strongestBotPosition.manhattanDistanceTo(p) <= strongestNanobot.radius)
      {
        count++;
      }
    }

    return count;
  }

  public int getShortestDistanceFromStrongestPoint()
  {
    // Find smallest square which encloses all bots
    int maxSize = max.x - min.x;
    maxSize = Math.max(max.y - min.x, maxSize);
    maxSize = Math.max(max.z - min.z, maxSize);
    int size = 2;
    while (size < maxSize)
    {
      size *= 2;
    }

    SearchSquare3D initial = new SearchSquare3D(min, size, positions, bots);
    PriorityQueue<SearchSquare3D> queue = new PriorityQueue<SearchSquare3D>();
    queue.add(initial);

    SearchSquare3D current  = null;
    while (!queue.isEmpty())
    {
      current = queue.poll();

      System.out.println("Visiting: " + current);
      // new Scanner(System.in).nextLine();

      if (current.size == 1)
      {
        break;
      }

      // Split into 8 smaller squares
      int newSize = current.size / 2;
      int x = current.bottomLeft.x;
      int y = current.bottomLeft.y;
      int z = current.bottomLeft.z;
      queue.add(new SearchSquare3D(new Point3D(x,y,z), newSize, positions, bots));
      queue.add(new SearchSquare3D(new Point3D(x,y,z + (current.size / 2)), newSize, positions, bots));
      queue.add(new SearchSquare3D(new Point3D(x,y + (current.size / 2),z), newSize, positions, bots));
      queue.add(new SearchSquare3D(new Point3D(x,y + (current.size / 2),z + newSize), newSize, positions, bots));
      queue.add(new SearchSquare3D(new Point3D(x + (current.size / 2),y,z), newSize, positions, bots));
      queue.add(new SearchSquare3D(new Point3D(x + (current.size / 2),y,z + (current.size / 2)),newSize, positions, bots));
      queue.add(new SearchSquare3D(new Point3D(x + (current.size / 2),y + (current.size / 2),z), newSize, positions, bots));
      queue.add(new SearchSquare3D(new Point3D(x + (current.size / 2),y + (current.size / 2),z + (current.size / 2)), newSize, positions, bots));

    }

    return current.getDistanceToOrigin();
  }
}

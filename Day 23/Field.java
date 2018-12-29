import java.util.Hashtable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Field
{
  private static Pattern NANOBOT_REGEX = Pattern.compile("pos=<(-*[0-9]+),(-*[0-9]+),(-*[0-9]+)>, r=([0-9]+)");

  private Hashtable<Point3D, Nanobot> bots;
  private Nanobot strongestBot;
  private Point3D strongestBotPosition;

  public Field(String[] lines)
  {
    bots = new Hashtable<Point3D, Nanobot>();
    strongestBot = null;
    strongestBotPosition = null;

    for (String line : lines)
    {
      Matcher matcher = NANOBOT_REGEX.matcher(line);

      if (matcher.matches())
      {
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        int z = Integer.parseInt(matcher.group(3));
        long r = Long.parseLong(matcher.group(4));

        Point3D p = new Point3D(x,y,z);
        Nanobot bot = new Nanobot(r);

        if (strongestBot == null ||
            bot.radius > strongestBot.radius)
        {
          strongestBot = bot;
          strongestBotPosition = p;
        }

        bots.put(p, bot);
      }
    }
  }

  public int botsInRangeOfStrongest()
  {
    int count = 0;
    for (Point3D p : bots.keySet())
    {
      if (strongestBotPosition.manhattanDistanceTo(p) <= strongestBot.radius)
      {
        count++;
      }
    }

    return count;
  }
}

import java.util.ArrayList;

public class Army
{
  public final String name;
  private Group[] groups;

  public Army(String name, String[] lines)
  {
    this.name = name;

    groups = new Group[lines.length];
    for (int i = 0; i < lines.length; i++)
    {
      Group g = new Group(lines[i], i + 1);
      groups[i] = g;
    }
  }

  public Iterable<Group> groups()
  {
    ArrayList<Group> aliveGroups = new ArrayList<Group>();

    for (Group g : groups)
    {
      if (!g.isDead())
      {
        aliveGroups.add(g);
      }
    }

    return aliveGroups;
  }

  public Iterable<Integer> groupNumbers()
  {
    ArrayList<Integer> aliveGroupNumbers = new ArrayList<Integer>();

    for (int i = 0; i < groups.length; i++)
    {
      if (!groups[i].isDead())
      {
        aliveGroupNumbers.add(i + 1);
      }
    }

    return aliveGroupNumbers;
  }

  public Group get(int groupNumber)
  {
    return groups[groupNumber - 1];
  }

  public void update(Group g)
  {
    groups[g.getGroupNumber() - 1] = g;
  }

  public boolean allGroupsDead()
  {
    for (Group g : groups)
    {
      if (!g.isDead())
      {
        return false;
      }
    }

    return true;
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();

    builder.append(name + ":\n");
    for (Group g : groups())
    {
      builder.append(g + "\n");
    }

    return builder.toString();
  }
}

import java.util.HashSet;

public class Constellation
{
  private HashSet<Vector> members;

  public Constellation()
  {
    members = new HashSet<Vector>();
  }

  public boolean isMember(Vector v)
  {
    return members.contains(v);
  }

  public void add(Vector v)
  {
    if (!isMember(v))
    {
      members.add(v);
    }
  }

  public void mergeWith(Constellation other)
  {
    for (Vector v : other.members)
    {
      if (!isMember(v))
      {
        add(v);
      }
    }
  }
}

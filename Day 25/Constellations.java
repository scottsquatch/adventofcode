import java.util.ArrayList;

public class Constellations
{
  private ArrayList<Constellation> constellations;

  public Constellations()
  {
    constellations = new ArrayList<Constellation>();
  }

  public Constellation add()
  {
    Constellation newConstellation = new Constellation();

    constellations.add(newConstellation);

    return newConstellation;
  }

  public Constellation get(Vector v)
  {
    for (Constellation c : constellations)
    {
      if (c.isMember(v))
      {
        return c;
      }
    }

    return null;
  }

  public void remove(Constellation c)
  {
    constellations.remove(c);
  }

  public int size()
  {
    return constellations.size();
  }
}

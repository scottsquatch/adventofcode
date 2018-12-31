public class Vector implements Comparable<Vector>
{
  private int[] values;
  private int size;

  public Vector(int size)
  {
    this.size = size;
    values = new int[size];
  }

  public Vector(String s)
  {
    String[] values = s.split(",");

    size = values.length;
    this.values = new int[size];
    for (int i = 0; i < size; i++)
    {
      this.values[i] = Integer.parseInt(values[i]);
    }
  }

  public int get(int index)
  {
    return values[index];
  }

  public void set(int index, int value)
  {
    values[index] = value;
  }

  public int manhattanDistanceTo(Vector other)
  {
    int distance = 0;
    for (int i = 0; i < size; i++)
    {
      distance += Math.abs(values[i] - other.values[i]);
    }

    return distance;
  }

  @Override
  public int hashCode()
  {
    int hash = 27;
    for (int v : values)
    {
      hash += 27 * v;
    }

    return hash;
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();

    builder.append(values[0]);

    for (int i = 1; i < values.length; i++)
    {
      builder.append("," + values[i]);
    }

    return builder.toString();
  }

  @Override
  public int compareTo(Vector other)
  {
    if (size < other.size)
    {
      return -1;
    }
    else if (size > other.size)
    {
      return 1;
    }

    for (int i = 0; i < size; i++)
    {
      if (values[i] < other.values[i])
      {
        return -1;
      }
      else if (values[i] > other.values[i])
      {
        return 1;
      }
    }

    return 0;
  }
}

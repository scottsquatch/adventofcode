public class Point3D
{
  public final int x;
  public final int y;
  public final int z;

  public Point3D(int x, int y, int z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public String toString()
  {
    return "(" + x + "," + y + "," + z +")";
  }

  public long manhattanDistanceTo(Point3D other)
  {
    return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
  }

  @Override
  public int hashCode()
  {
    return (123123 * x + 113287 * y + 1321123*z) % 231412414;
  }

  @Override
  public boolean equals(Object other)
  {
    if (this == other)
    {
      return true;
    }
    else if (other == null)
    {
      return false;
    }
    else if (getClass() != other.getClass())
    {
      return false;
    }

    Point3D otherP = (Point3D)other;

    return otherP.x == x && otherP.y == y && otherP.z == z;
  }
}

import java.util.ArrayList;

public class Pyramid3D
{
  public final Point3D centerPoint;
  public final int radius;
  public Point3D[] corners;

  public Pyramid3D(Point3D center, int radius)
  {
    this.centerPoint = center;
    this.radius = radius;
    this.corners = new Point3D[8];

    fillCorners(center);
  }

  public boolean inPyramid(Point3D p)
  {
    return centerPoint.manhattanDistanceTo(p) <= radius;
  }

  private void fillCorners(Point3D center)
  {
    corners[0] = new Point3D(centerPoint.x - radius, centerPoint.y - radius, centerPoint.z - radius);
    corners[1] = new Point3D(centerPoint.x - radius, centerPoint.y - radius, centerPoint.z + radius);
    corners[2] = new Point3D(centerPoint.x - radius, centerPoint.y + radius, centerPoint.z - radius);
    corners[3] = new Point3D(centerPoint.x - radius, centerPoint.y + radius, centerPoint.z + radius);
    corners[4] = new Point3D(centerPoint.x + radius, centerPoint.y - radius, centerPoint.z - radius);
    corners[5] = new Point3D(centerPoint.x + radius, centerPoint.y - radius, centerPoint.z + radius);
    corners[6] = new Point3D(centerPoint.x + radius, centerPoint.y + radius, centerPoint.z - radius);
    corners[7] = new Point3D(centerPoint.x + radius, centerPoint.y + radius, centerPoint.z + radius);
  }
}

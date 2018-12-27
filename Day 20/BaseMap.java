import java.util.TreeMap;

public class BaseMap
{
  private static char CELL_START = 'X';
  private static char CELL_ROOM = '.';
  private static char CELL_DOOR_HORIZONTAL = '|';
  private static char CELL_DOOR_VERTICAL = '-';
  private static char CELL_WALL_KNOWN = '#';
  private static char CELL_WALL_UNKNOWN = '?';
  private static Point START_POINT = new Point(0,0);
  private boolean fullyExplored;
  private Graph g;

  public BaseMap(String regex)
  {
    fullyExplored = false;
    g = new Graph();

    // Consider 0,0 the start point
    g.addVertex(START_POINT);

    fillGraph(regex);
  }

  private void fillGraph(String regex)
  {
    // For now support simple paths with no branching
    new GraphSearcher(this, START_POINT).search(regex);
  }

  public Point move(Point p, Direction d)
  {
    Point newP = new Point(p.x, p.y);

    switch (d)
    {
      case NORTH:
      {
        newP.y -= 1;
        break;
      }

      case EAST:
      {
        newP.x += 1;
        break;
      }

      case WEST:
      {
        newP.x -= 1;
        break;
      }

      case SOUTH:
      {
        newP.y += 1;
        break;
      }
    }

    g.addVertex(newP);
    g.addEdge(p, newP, d);

    return newP;
  }

  public Graph getGraph()
  {
    return g;
  }

  public Point[] getPathToFurthestRoom()
  {
		ShortestPathFinder finder = new ShortestPathFinder(g, START_POINT);
		Point[] path = new Point[0];
		for (Point pNext : g.V())
		{
      //System.out.println("Finding shortest path to " + pNext);
			Point[] shortestPath = finder.pathTo(pNext);

			if (shortestPath != null &&
          shortestPath.length > path.length)
			{
        path = shortestPath;
			}
		}

    return path;
  }

  @Override
  public String toString()
  {
    return g.toString();
  }
}

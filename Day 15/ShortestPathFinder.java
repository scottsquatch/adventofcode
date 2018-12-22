import java.util.Hashtable;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;

// Class which finds the shortest point between start and other points in the GameMap
public class ShortestPathFinder
{
	private Hashtable<Location, Location> shortestPath;
	private HashSet<Location> visited;

	public ShortestPathFinder(Location start, GameMap map)
	{
		shortestPath = new Hashtable<Location, Location>();
		visited = new HashSet<Location>();

		bfs(start, map);
	}

	// BFS very heavily inspired by Sedgewick Alrogithms book
	private void bfs(Location start, GameMap map)
	{
		Queue<Location> queue = new LinkedList<Location>();

		queue.add(start);
		visited.add(start);
		while (queue.peek() != null)
		{
			Location current = queue.remove();

			for (Location adj : map.getAdj(current))
			{
				if (!visited.contains(adj))
				{
					visited.add(adj);
					shortestPath.put(adj, current);

					if (map.get(adj) == CellType.EMPTY)
					{
						queue.add(adj);
					}
				}
			}
		}
	}

	public Location[] getShortestPath(Location end)
	{
		LinkedList<Location> pathStack = new LinkedList<Location>();

		Location l = shortestPath.get(end);
		while (l != null)
		{
			pathStack.push(l);

			l = shortestPath.get(l);
		}

		return pathStack.toArray(new Location[pathStack.size()]);
	}

	public boolean hasPathTo(Location end)
	{
		return visited.contains(end);
	}
}			

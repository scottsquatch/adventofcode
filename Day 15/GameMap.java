import java.util.Collection;
import java.util.ArrayList;
import java.util.TreeMap;

public class GameMap
{
	private CellType[][] map;
	private TreeMap<Location, Unit> unitMap;

	public GameMap(Collection<String> lines)
	{
		map = new CellType[lines.size()][];
		unitMap = new TreeMap<Location, Unit>();

		int row = 0;
		for (String line : lines)
		{
			map[row] = new CellType[line.length()];

			for (int col = 0; col < line.length(); col++)
			{
				CellType type = CellType.getCellTypeFromSymbol(line.charAt(col));
				map[row][col] = type;

				if (type == CellType.GOBLIN || type == CellType.ELF)
				{

					Location l = new Location(row, col);

					unitMap.put(l, Unit.createUnit(type));
				}
			}

			row++;
		}
	}
	
	public CellType get(Location loc)
	{
		return map[loc.getRow()][loc.getColumn()];
	}

	public void move(Location from, Location to)
	{
		CellType fromCell = map[from.getRow()][from.getColumn()];

		map[to.getRow()][to.getColumn()] = fromCell;
		map[from.getRow()][from.getColumn()] = CellType.EMPTY;

		// Need to update the unit table
		Unit u = unitMap.remove(from);
		unitMap.put(to, u); 
	}

	public void remove(Location loc)
	{
		// First remove location from unit tree
		unitMap.remove(loc);
		map[loc.getRow()][loc.getColumn()] = CellType.EMPTY;
	}

	public Location[] getAdj(Location loc)
	{
		ArrayList<Location> adj = new ArrayList<Location>();

		// Order here is important, always return point in order: up, left, right, down so as to follow reading order
		int row = loc.getRow();
		int column = loc.getColumn();
		if (row > 0 &&
			 map[row - 1][column] != CellType.WALL)
		{
			adj.add(new Location(row - 1, column));
		}
		if (column > 0 &&
			 map[row][column - 1] != CellType.WALL)
		{
			adj.add(new Location(row, column - 1));
		}
		if (column < (map[row].length - 2) &&
			 map[row][column + 1] != CellType.WALL)
		{
			adj.add(new Location(row, column + 1));
		}
		if (row < (map.length - 2) &&
			 map[row + 1][column] != CellType.WALL)
		{
			adj.add(new Location(row + 1, column));
		}

		return adj.toArray(new Location[adj.size()]);
	}

	public TreeMap<Location, Unit> getUnits()
	{
		return unitMap;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for (CellType[] row : map)
		{
			for (CellType c : row)
			{
				builder.append(c.getSymbol());
			}

			builder.append("\n");
		}

		return builder.toString();
	}

	public void printUnitTable()
	{
		for (Location l : unitMap.keySet())
		{
			System.out.println(l.toString() + ": " + unitMap.get(l));
		}
	}
}

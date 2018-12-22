public class Location implements Comparable<Location>
{
	private int column;
	private int row;

	public Location(int row, int column)
	{
		this.column = column;
		this.row = row;
	}

	public int getColumn()
	{
		return column;
	}

	public int getRow()
	{
		return row;
	}

	@Override
	public String toString()
	{
		return row + "," + column;
	}

	@Override
	public int compareTo(Location other)
	{
		if (row < other.row)
		{
			return -1;
		}
		else if (row > other.row)
		{
			return 1;
		}
		else if (column < other.column)
		{
			return -1;
		}
		else if (column > other.column)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + row;
		hash = 31 * hash + column;

		return hash;
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

		return compareTo((Location)other) == 0;
	}

	public Location[] getAdjacentLocations()
	{
		// Always go North, East, West, then South
		Location[] adj = new Location[4];

		adj[0] = new Location(row - 1, column);
		adj[1] = new Location(row, column - 1);
		adj[2] = new Location(row, column + 1);
		adj[3] = new Location(row + 1, column);

		return adj;
	}
}

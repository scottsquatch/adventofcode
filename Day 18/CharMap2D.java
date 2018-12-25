import java.util.ArrayList;

public class CharMap2D
{
	private char[][] map;

	public CharMap2D(String[] lines)
	{
		map = new char[lines.length][];
		
		int y = 0;
		for (String line : lines)
		{
			int x = 0;
			map[y] = new char[line.length()];
			for (char c : line.toCharArray())
			{
				map[y][x++] = c;
			}
			y++;
		}
	}	

	public CharMap2D(CharMap2D otherMap)
	{
		map = new char[otherMap.map.length][];

		for (int y = 0; y < otherMap.map.length; y++)
		{
			map[y] = new char[otherMap.map[y].length];
			for (int x = 0; x < otherMap.map[y].length; x++)
			{
				map[y][x] = otherMap.map[y][x];
			}
		}
	}

	public char get(int x, int y)
	{
		return map[y][x];
	}

	public void set(int x, int y, char c)
	{
		map[y][x] = c;
	}

	public int getMaxY()
	{
		return map.length;
	}

	public int getMaxX()
	{
		return map[0].length;
	}

	/**
	 * Returns a square of the adjacent cells with the given size. Will account for edges
	 * @param x
	 * @param y
	 * @param size
	 * @return
	 */
	public ArrayList<Character> getAdj(int x, int y, int size)
	{
		ArrayList<Character> adj = new ArrayList<Character>();
		int halfSize = size / 2;
		for (int i = y - halfSize; i <= (y + halfSize); i++)
		{
			if (i >= 0 && i < map.length)
			{
				for (int j = x - halfSize; j <= (x + halfSize); j++)
				{
					if (j >= 0 && j < map[i].length &&
						(x != j || y != i))
					{
						adj.add(map[i][j]);
					}
				}
			}
		}

		return adj;
	}

	public int getNum(char c)
	{
		int sum = 0;
		for (char[] row : map)
		{
			for (char ch : row)
			{
				if (ch == c)
				{
					sum++;
				}
			}
		}

		return sum;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for (char[] row : map)
		{
			for (char c : row)
			{
				builder.append(c);
			}
			builder.append("\n");
		}

		return builder.toString();
	}
}
					

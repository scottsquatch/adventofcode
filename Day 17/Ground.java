import java.util.TreeMap;

public class Ground
{
	public static final char SYMBOL_CLAY                     = '#';
	public static final char SYMBOL_SETTLED_WATER            = '~';
	public static final char SYMBOL_SPRING                   = '+';
	public static final char SYMBOL_SAND                     = '.';
	public static final char SYMBOL_RUNNING_WATER_VERTICAL   = '|';
	public static final char SYMBOL_RUNNING_WATER_HORIZONTAL = '|';

	private TreeMap<Point, Character> occupiedPoints;
	private int minY;
	private int maxY;

	public Ground(String[] clayLines)
	{
		occupiedPoints = new TreeMap<Point, Character>();
		minY = Integer.MAX_VALUE;
		maxY = Integer.MIN_VALUE;
		for (String line : clayLines)
		{
			Point[] points = Point.parsePointRange(line);

			for (Point p : points)
			{
				occupiedPoints.put(p, '#');
				minY = Math.min(minY, p.y);
				maxY = Math.max(maxY, p.y);
			}
		}

		// Always place spring at point 500, 0
		occupiedPoints.put(new Point(500, 0), SYMBOL_SPRING);
	}	

	public char get(Point p)
	{
		if (!occupiedPoints.containsKey(p))
		{
			return SYMBOL_SAND;
		}
		

		return occupiedPoints.get(p).charValue();
	}

	public void set(Point p, char symbol)
	{
		occupiedPoints.put(p, symbol);
	}

	public Point getSpoutLocation()
	{
		return new Point(500, 0);
	}

	public int getMaxY()
	{
		return maxY;
	}

	public int getMinY()
	{
		return minY;
	}

	public int getNum(char symbol)
	{
		int sum = 0;
		for (Point p : occupiedPoints.keySet())
		{
			if (p.y >= minY &&
			    p.y <= maxY)
			{
				char c = occupiedPoints.get(p);
				if (c == symbol)
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
		// Find bounding rectangle
		BoundingRectangle r = new BoundingRectangle(occupiedPoints.keySet());
		System.out.println(r);
		StringBuilder builder = new StringBuilder();
		for (int y = r.getMinY(); y <= r.getMaxY(); y++)
		{
			for (int x = r.getMinX(); x <= r.getMaxX(); x++)
			{
				Point p = new Point(x, y);
				if (!occupiedPoints.containsKey(p))
				{
					builder.append(SYMBOL_SAND);
				}
				else
				{
					builder.append(occupiedPoints.get(p));
				}
			}
			builder.append("\n");
		}

		return builder.toString();
	}

	public static void main(String[] args)
	{
		System.out.println("Testing Ground class");

		String[] lines = 
		{
			"x=495, y=2..7",
			"y=7, x=495..501",
			"x=501, y=3..7",
			"x=498, y=2..4",
			"x=506, y=1..2",
			"x=498, y=10..13",
			"x=504, y=10..13",
			"y=13, x=498..504",
		};

		String expected = 
			".....+......\n" + 
 			"...........#\n" + 
 			"#..#.......#\n" + 
 			"#..#..#.....\n" + 
 			"#..#..#.....\n" +
 			"#.....#.....\n" + 
 			"#.....#.....\n" + 
 			"#######.....\n" + 
 			"............\n" + 
 			"............\n" + 
			"...#.....#..\n" + 
			"...#.....#..\n" +
			"...#.....#..\n" + 
			"...#######..\n";

		Ground g = new Ground(lines);
		String actual = g.toString();
		System.out.println(actual);
		assert(expected.equals(actual));

		assert(34 == g.getNum(SYMBOL_CLAY));

		assert(0 == g.getMinY());
		assert(13 == g.getMaxY());

		Point p = new Point(495, 0);
		assert('.' == g.get(p));
		g.set(p, SYMBOL_SETTLED_WATER);
		assert(SYMBOL_SETTLED_WATER == g.get(p));
		Point p2 = new Point(400, -2);
		g.set(p2, SYMBOL_CLAY);
		assert(SYMBOL_CLAY == g.get(p2));

		System.out.println("All tests passed");
	}
}
					

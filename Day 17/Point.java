import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Point implements Comparable<Point>
{
	private static final Pattern REGEX_POINT_RANGE = Pattern.compile("(x|y)=([0-9]+),\\s+(x|y)=([0-9]+)\\.\\.([0-9]+)");
	public int x;
	public int y;

	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override 
	public int compareTo(Point other)
	{
		if (y < other.y)
		{
			return -1;
		}
		else if (y > other.y)
		{
			return 1;
		}
		else if (x < other.x)
		{
			return -1;
		}
		else if (x > other.x)
		{
			return 1;
		}
		else
		{
			return 0;
		}
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
		else 
		{
			return compareTo((Point)other) == 0;
		}
	}

	@Override 
	public String toString()
	{
		return x + "," + y;
	}

	public static Point[] parsePointRange(String s)
	{
		Matcher matcher = REGEX_POINT_RANGE.matcher(s);

		if (!matcher.matches())
		{
			return null;
		}

		String staticCoordinate = matcher.group(1);
		int staticCoordinateValue = Integer.parseInt(matcher.group(2));
		String dynamicCoordinate = matcher.group(3);
		int dynamicCoordinateStart = Integer.parseInt(matcher.group(4));
		int dynamicCoordinateEnd = Integer.parseInt(matcher.group(5));

		Point[] points = new Point[dynamicCoordinateEnd - dynamicCoordinateStart + 1];
		for (int i = dynamicCoordinateStart; i <= dynamicCoordinateEnd; i++)
		{
			int idx = i - dynamicCoordinateStart;

			int x = Integer.MIN_VALUE,y = Integer.MIN_VALUE;

			switch (staticCoordinate)
			{
				case "x":
				{
					x = staticCoordinateValue;
					break;
				}
				case "y":
				{
					y = staticCoordinateValue;
					break;
				}
				default:
				{
					throw new IllegalArgumentException("Invalid coordinate value: " + staticCoordinate);
				}
			}

			switch (dynamicCoordinate)
			{
				case "x":
				{
					x = i;
					break;
				}
				case "y":
				{
					y = i;
					break;
				}
				default:
				{
					throw new IllegalArgumentException("invalid coordinate value: " + dynamicCoordinate);
				}
			}

			points[idx] = new Point(x, y);

		}

		return points;
	}

	@Override 
	public int hashCode()
	{
		return 123871283*x + 129371279*y;
	}


	public static void main(String[] args)
	{
		System.out.println("Testing point class");

		Point[] points = Point.parsePointRange("x=495, y=2..7");
		assert(6 == points.length);
		assert(new Point(495, 2).equals(points[0]));
		assert(new Point(495, 3).equals(points[1]));
		assert(new Point(495, 4).equals(points[2]));
		assert(new Point(495, 5).equals(points[3]));
		assert(new Point(495, 6).equals(points[4]));
		assert(new Point(495, 7).equals(points[5]));

		points = Point.parsePointRange("y=7, x=495..501");
		assert(7 == points.length);
		assert(new Point(495, 7).equals(points[0]));
		assert(new Point(496, 7).equals(points[1]));
		assert(new Point(497, 7).equals(points[2]));
		assert(new Point(498, 7).equals(points[3]));
		assert(new Point(499, 7).equals(points[4]));
		assert(new Point(500, 7).equals(points[5]));
		assert(new Point(501, 7).equals(points[6]));

		System.out.println("All tests passed");
	}

}

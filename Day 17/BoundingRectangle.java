import java.util.ArrayList;
import java.util.Collection;

public class BoundingRectangle
{
	private final Point pMax;
	private final Point pMin;
	public BoundingRectangle(Collection<Point> points)
	{
		int xMin = Integer.MAX_VALUE, yMin = Integer.MAX_VALUE;
		int yMax = Integer.MIN_VALUE, xMax = Integer.MIN_VALUE;

		for (Point p: points)
		{
			xMax = Math.max(p.x, xMax);
			yMax = Math.max(p.y, yMax);

			xMin = Math.min(p.x, xMin);
			yMin = Math.min(p.y, yMin);
		}

		pMax = new Point(xMax, yMax);
		pMin = new Point(xMin, yMin);
	}


	public int getMaxX()
	{
		return pMax.x;
	}

	public int getMaxY()
	{
		return pMax.y;
	}

	public int getMinX()
	{
		return pMin.x;
	}

	public int getMinY()
	{
		return pMin.y;
	}

	@Override
	public String toString()
	{
		return "Minx: " + getMinX() + ", MaxX: " + getMaxX() + ", MinY: " + getMinY() + ", MaxY: " + getMaxY();
	}

	public static void main(String[] args)
	{
		System.out.println("Starting tests for Bounding Rectangle class ");
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(5,5));
		points.add(new Point(-4,10));
		points.add(new Point(10, -1));
		points.add(new Point(-3, 20));
		points.add(new Point(21, 0));

		BoundingRectangle r = new BoundingRectangle(points);

		assert(-4 == r.getMinX());
		assert(21 == r.getMaxX());
		assert(-1 == r.getMinY());
		assert(20 == r.getMaxY());
		System.out.println("All tests passed");
	}


}

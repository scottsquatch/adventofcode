import java.util.Scanner;
import java.util.Stack;
import java.util.HashSet;

public class WaterflowSimulator
{
	private Ground ground;
	private HashSet<Point> visited;

	public WaterflowSimulator(Ground ground)
	{
		this.ground = ground;
	}

	public void simulate()
	{
		while (simulationStep()) 
		{ 
			//System.out.println(this);
			

			// Reset state so we can rerun the water flow
			for (Point p : visited)
			{
				if (ground.get(p) == Ground.SYMBOL_RUNNING_WATER_VERTICAL)
				{
					ground.set(p, Ground.SYMBOL_SAND);
				}
			}
		} 

	}

	private boolean simulationStep()
	{
		boolean waterSettled= false;
		Stack<Point> points = new Stack<Point>();
		visited = new HashSet<Point>();

		Point spoutLocation = ground.getSpoutLocation();
		spoutLocation.y += 1;
		points.push(spoutLocation);

		Point p = null;
		while (!points.empty())
		{
			p = points.pop();

			if (!visited.contains(p))
			{
				visited.add(p);

				Point pDown = new Point(p.x, p.y + 1);
				char sDown = ground.get(pDown);
				Point pLeft = new Point(p.x - 1, p.y);
				char sLeft = ground.get(pLeft);
				Point pRight = new Point(p.x + 1, p.y);
				char sRight = ground.get(pRight);

				// Always try to move down first
				if (canMove(pDown) || sDown == Ground.SYMBOL_RUNNING_WATER_VERTICAL)
				{
					if (pDown.y <= ground.getMaxY())
					{
						points.push(pDown);
					}
				}
				else
				{
					// Now we can move either right or left, check both
					boolean cantMoveRightOrLeft = true;
					if (canMove(pRight))
					{
						points.push(pRight);
						cantMoveRightOrLeft = false;
					}

					if (canMove(pLeft))
					{
						points.push(pLeft);
						cantMoveRightOrLeft = false;
					}

					if (cantMoveRightOrLeft)
					{
						waterSettled = settleWater(p);
					}
				}

				if (!waterSettled)
				{
					ground.set(p, Ground.SYMBOL_RUNNING_WATER_VERTICAL);
				}
			}

		}


		return waterSettled;
	}

	private boolean settleWater(Point p)
	{
		Stack<Point> pointsToSettle = new Stack<Point>();
		pointsToSettle.push(p);

		Point current = new Point(p.x - 1, p.y);
		// Check left
		while (ground.get(current) != Ground.SYMBOL_CLAY)
		{
			if (ground.get(current) != Ground.SYMBOL_RUNNING_WATER_VERTICAL)
			{
				return false;
			}
			pointsToSettle.push(new Point(current.x, current.y));

			current.x += -1;
		}

		// Check right
		current = new Point(p.x + 1, p.y);
		while (ground.get(current) != Ground.SYMBOL_CLAY)
		{
			if (ground.get(current) != Ground.SYMBOL_RUNNING_WATER_VERTICAL)
			{
				return false;
			}
			pointsToSettle.push(new Point(current.x, current.y));

			current.x += 1;
		}


		// Now update the points
		while (!pointsToSettle.empty())
		{
			Point p1 = pointsToSettle.pop();

			ground.set(p1, Ground.SYMBOL_SETTLED_WATER);
		}

		return true;
	}


	private boolean canMove(Point p)
	{
		char symbol = ground.get(p);
		
		return symbol == Ground.SYMBOL_SAND;
	}

	public int getTotalTilesOfWater()
	{
		return ground.getNum(Ground.SYMBOL_SETTLED_WATER) + ground.getNum(Ground.SYMBOL_RUNNING_WATER_HORIZONTAL);
			
	}

	@Override
	public String toString()
	{
		return ground.toString();
	}

	public static void main(String[] args)
	{
		System.out.println("Testing WaterflowSimulator class");
                //  44555
		//  99000
		//  89012
		//0   + 
		//1     
		//2 #   #
		//3 #   #
		//4 #####
		
		String[] lines = 
		{
			"x=498, y=2..4",
			"y=4, x=498..502",
			"x=502, y=2..4"
		};
		Ground g = new Ground(lines);

		WaterflowSimulator s = new WaterflowSimulator(g);

		s.simulate();
		System.out.println(s);

		System.out.println(s.getTotalTilesOfWater());

		// Now for a more complex example
		//
		// #
		// #          #
		// #     #    #
		// #     #    #
		// #          #
		// #          #
		// ############
	}
}

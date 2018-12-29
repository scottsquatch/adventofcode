import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class AdventOfCodeDay22
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: AdventOfCodeDay22 <input>");
		}
		else
		{
			String inputFilePath = args[0];
			try
			{
				BufferedReader input = new BufferedReader(new FileReader(inputFilePath));
				int depth = -1;
				Point t = null;

				while(input.ready())
				{
					String line = input.readLine();

					int index = line.indexOf("depth: ");
					if (index > -1)
					{
						depth = Integer.parseInt(line.substring(index + "depth: ".length()));
					}
					else if ((index = line.indexOf("target: ")) > -1)
					{
						t = Point.parsePoint(line.substring(index + "target: ".length()));
					}
				}

				solveProblem1(depth, t);
				solveProblem2(depth, t);

				input.close();
			}
			catch (FileNotFoundException e)
			{
				System.err.println("Cannot find file " + inputFilePath);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static void solveProblem1(int depth, Point t)
	{
		Cave c = new Cave(depth, t);

		System.out.println(c);
		System.out.println("Total score for rectangle around target: " + c.getTotalRisk());
	}

	private static void solveProblem2(int depth, Point t)
	{
		Cave c = new Cave(depth, t);

		ShortestPathFinder pathFinder = new ShortestPathFinder(c, Cave.CAVE_ENTRANCE_POINT, Cave.INITIAL_TOOL,
				t, Tool.TORCH);

		for (CaveGraphVertex v : pathFinder.getShortestPath())
		{
			System.out.println(v);
		}
		System.out.println();
		System.out.println(pathFinder.getCost() + " minutes to rescue the target");
	}
}

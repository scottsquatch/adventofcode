import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AdventOfCodeDay18
{
	public static void main(String[] args)
	{
		if (args.length < 1 || args.length > 2)
		{
			System.out.println("usage: AdventOfCodeDay18 <input> [numThreads]");
		}
		else
		{
			String inputFilePath = args[0];
			int numThreads = 1;
			if (args.length > 1)
			{
				numThreads = Integer.parseInt(args[1]);
			}
			try
			{
				ArrayList<String> lines = new ArrayList<String>();

				BufferedReader input = new BufferedReader(new FileReader(inputFilePath));

				while(input.ready())
				{
					lines.add(input.readLine());
				}

				solveProblem1(lines, numThreads);
				solveProblem2(lines, numThreads);

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

	private static void solveProblem1(ArrayList<String> lines, int numThreads)
	{
		BaseConstructionSimulator sim = new BaseConstructionSimulator(lines.toArray(new String[lines.size()]));
		System.out.println("Initially");
		System.out.println(sim);
		System.out.println(sim.getResourceScore());

		for (int i = 0; i < 10; i++)
		{
			sim.passMinute(numThreads);
			// System.out.println("After " + (i + 1) + " minutes:");
			System.out.println(sim);
		}

		int numTreeSquares = sim.getNum(LumberGridType.TREES);
		int numLumberyards = sim.getNum(LumberGridType.LUMBERYARD);
		System.out.println("Num wooded acres: " + numTreeSquares + ", num lumberyards: " + numLumberyards + " for a total resource value of " + sim.getResourceScore());
	}

	private static void solveProblem2(ArrayList<String> lines, int numThreads)
	{
		BaseConstructionSimulator sim = new BaseConstructionSimulator(lines.toArray(new String[lines.size()]));

		System.out.println("Total Resource Value: " + sim.getResourceScore(1000000000L, numThreads));
	}	
}


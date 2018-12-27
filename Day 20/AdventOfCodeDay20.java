import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class AdventOfCodeDay20
{
	private static boolean printDebugLogs;
	public static void main(String[] args)
	{
		if (args.length < 1 || args.length > 2)
		{
			System.out.println("usage: AdventOfCodeDay20 <input>");
		}
		else
		{
			String inputFilePath = args[0];
			if (args.length > 1)
			{
				printDebugLogs = Boolean.parseBoolean(args[1]);
			}

			try
			{
				BufferedReader input = new BufferedReader(new FileReader(inputFilePath));
				String regex = null;
				while(input.ready())
				{
					String line = input.readLine();
					if (line != null &&
					    line.trim().length() > 0)
					{
						regex = line;
					}
				}

				if (printDebugLogs)
				{
					System.out.println("Regex: " + regex);
				}

				solveProblem1(regex);
				solveProblem2(regex);

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

	private static void solveProblem1(String regex)
	{
		BaseMap m = new BaseMap(regex);

		if (printDebugLogs)
		{
			System.out.println(m);
		}

		Point[] pathToFuthestRoom = m.getPathToFurthestRoom();

		System.out.println("Map Regex: " + regex);
		System.out.println("Largest number of doors to pass through is " + pathToFuthestRoom.length	+ " doors.");
	}

	private static void solveProblem2(String regex)
	{
		BaseMap m = new BaseMap(regex);

		if (printDebugLogs)
		{
			System.out.println(m);
		}

		int num = m.getNumRoomsWithShortestPathGreaterThanOrEqualTo(1000);

		System.out.println("Number of rooms for which the shortest path goest through at least 1000 doors: "
			+ num);
	}
}

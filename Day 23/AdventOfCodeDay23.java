import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AdventOfCodeDay23
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: AdventOfCodeDay23 <input>");
		}
		else
		{
			String inputFilePath = args[0];
			try
			{
				BufferedReader input = new BufferedReader(new FileReader(inputFilePath));
				ArrayList<String> tempLines = new ArrayList<String>();

				while(input.ready())
				{
					tempLines.add(input.readLine());
				}

				String[] lines = tempLines.toArray(new String[tempLines.size()]);
				solveProblem1(lines);
				solveProblem2(lines);

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

	private static void solveProblem1(String[] lines)
	{
		Field f = new Field(lines);

		System.out.println("Number of nanobots in range of strongest bot: " + f.botsInRangeOfStrongest());
	}

	private static void solveProblem2(String[] lines)
	{
		// TODO
	}
}

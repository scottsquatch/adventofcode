import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AdventOfCodeDay24
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: AdventOfCodeDay24 <input>");
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
		String army1Name = null;
		String army2Name = null;
		ArrayList<String> army1Lines = new ArrayList<String>();
		ArrayList<String> army2Lines = new ArrayList<String>();
		for (String line : lines)
		{
			if (line != null && line.trim().length() > 0)
			{
					int colonIndex = line.indexOf(":");
					if (colonIndex > -1)
					{
						if (army1Name == null)
						{
							army1Name = line.substring(0, colonIndex);
						}
						else
						{
							army2Name = line.substring(0, colonIndex);
						}
					}
					else
					{
						if (army2Name == null)
						{
							army1Lines.add(line);
						}
						else
						{
							army2Lines.add(line);
						}
					}
			}
		}

		DiseaseSimulation ds = new DiseaseSimulation(army1Name, army1Lines.toArray(new String[army1Lines.size()]),
			army2Name, army2Lines.toArray(new String[army2Lines.size()]));
		DiseaseSimulationResult result = ds.simulate();

		int units = 0;
		for (Group g : result.remainingUnits)
		{
			units += g.numUnits;
		}

		System.out.println("Winning army has " + units + " units left");
	}

	private static void solveProblem2(String[] lines)
	{
		// TODO
	}
}

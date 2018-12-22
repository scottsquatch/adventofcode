import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AdventOfCodeDay15
{
	public static void main(String[] args)
	{
		boolean printRounds = false;
		if (args.length < 1 || args.length > 2)
		{
			System.out.println("usage: AdventOfCodeDay15 <input> [true/false]");
		}
		else
		{
			String inputFilePath = args[0];
			if (args.length > 1)
			{
				printRounds = Boolean.parseBoolean(args[1]);
			}

			try
			{
				ArrayList<String> lines = new ArrayList<String>();

				BufferedReader input = new BufferedReader(new FileReader(inputFilePath));

				while(input.ready())
				{
					lines.add(input.readLine());
				}

				GameMap map = new GameMap(lines);

				GameEngine engine = new GameEngine(map);

				engine.playGame(printRounds);
				
				// Problem 1 
				printEngine(engine);

				// Problem 2
				System.out.println("\n\nNow finding the minimum AP to ensure an elf win with no casualties\n");
				MinimumAPForElvesVictory analyzer = new MinimumAPForElvesVictory(lines);
				int minAP = analyzer.run(printRounds);
				System.out.println("Minimum AP needed: " + minAP);
				printEngine(analyzer.getFinalGameRun());

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

	private static void printAdjacentCells(GameMap map, Location loc)
	{
		Location[] adj = map.getAdj(loc);
		System.out.print("Cells Adjacent to " + loc + ": ");
		for(int i = 0; i < adj.length; i++)
		{
			System.out.print(adj[i] + " ");
		}

		System.out.println();
	}

	private static void printEngine(GameEngine engine)
	{
		int totalHitPoints = engine.getSumOfAllUnitsHealth();
		int lastFullRound = engine.getFullRounds();

		System.out.println("Combat ends after " + lastFullRound + " full rounds");
		System.out.println(engine.getWinningTeamName() + " win with "  + totalHitPoints + " total hit points left");
		System.out.println("Outcome: " + lastFullRound + " * " + totalHitPoints + " = " + ((long)totalHitPoints * lastFullRound));
	}
}


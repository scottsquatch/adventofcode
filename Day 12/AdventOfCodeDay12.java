import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class AdventOfCodeDay12
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("Usage: AdventOfCodeDay12 <input.txt>");
		}
		else
		{
			String inputFilePath = args[0];

			try
			{
				BufferedReader input = new BufferedReader( new FileReader(inputFilePath));

				if (input.ready())
				{
					String initialState = input.readLine();
					initialState = initialState.substring(initialState.indexOf(':') + 2);
					Rules rules = new Rules();

					input.readLine();

					while (input.ready())
					{
						rules.addRule(input.readLine());
					}

					SimulationDriver simulation = new SimulationDriver(initialState, rules);
					for (int i = 0; i < 20; i++)
					{
						simulation.increaseGeneration();
					}

					System.out.println("Sum of filled pots after 20 generations: " + simulation.sumFilledPots());
					
				}
			}
			catch (FileNotFoundException e)
			{
				System.err.println("Cannot find file " + inputFilePath + " please double check path");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}	

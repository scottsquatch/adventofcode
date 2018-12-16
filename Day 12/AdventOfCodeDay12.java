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

					SimulationDriverOptimized driverOptimized = new SimulationDriverOptimized(initialState, rules);

					//long startTime = System.nanoTime();

					// Had to use a little help from reddit, but since 50 Billion is far too large
					// to do pragmatically, we will have to find a convergence point and calculate the total from there.
					long previousSum;
					long previousDiff;
					long currentSum = Long.MIN_VALUE;
					long currentDiff = Long.MIN_VALUE;
					int generation = 0;
					do
					{
						previousDiff = currentDiff;
						previousSum = currentSum;

						driverOptimized.increaseGeneration();
						currentSum = driverOptimized.sumFilledPots();

						generation++;
						currentDiff = currentSum - previousSum;

						System.out.println("previousSum: " + previousSum + ", currentSum: " +
							currentSum + ", previousDiff " + previousDiff + ", currentDiff: " +
							currentDiff + " Generation: " + generation);
					} while (previousDiff != currentDiff);

					// Now that we have the convergence point, we can use simple arithmatic to
					// determine the answer
					long estimate = (50000000000L - generation) * 8 + currentSum;
					System.out.println("Estimate of sum after 50 Billion iterations: " + estimate);
					// Now that we have the convergence point and the convergence diff, we
					// can finish up the loop to find the value
					//
					// long sum = currentSum - currentDiff;
					// for (long i = generation + 1; i <= 50000000000L; i++)
					// {
					// 	currentSum += currentDiff;
					// 	System.out.println("Generation: " + i + " Sum: " + currentSum);
					// }

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

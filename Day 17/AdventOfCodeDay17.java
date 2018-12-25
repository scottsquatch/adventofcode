import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.LinkedList;

public class AdventOfCodeDay17
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: AdventOfCodeDay17 <input>");
		}
		else
		{
			String inputFilePath = args[0];
			try
			{
				ArrayList<String> lines = new ArrayList<String>();

				BufferedReader input = new BufferedReader(new FileReader(inputFilePath));

				while(input.ready())
				{
					lines.add(input.readLine());
				}

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

	private static void solveProblem1(ArrayList<String> lines)
	{
		Ground g = new Ground(lines.toArray(new String[lines.size()]));
		System.out.println("Initial ground state: ");
		System.out.println(g);


		WaterflowSimulator simulator = new WaterflowSimulator(g);	
		simulator.simulate();
		System.out.println("Final ground state: ");
		System.out.println(simulator);
		System.out.println("Total number of wet tiles: " + simulator.getTotalTilesOfWater());
	}

	private static void solveProblem2(ArrayList<String> lines)
	{
		Ground g = new Ground(lines.toArray(new String[lines.size()]));

		new WaterflowSimulator(g).simulate();

		System.out.println("Number of tiles with water after spring dries up is " + g.getNum('~'));
	}	
}


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Stack;

public class AdventOfCodeDay25
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: AdventOfCodeDay25 <input>");
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
		Vector[] points = new Vector[lines.length];

		for (int i = 0; i < lines.length; i++)
		{
			String line = lines[i];
			points[i] = new Vector(line);
		}

		// First try a naive apporoach where we check each point against each other to create chains of constellations
		int[] constellations = new int[points.length];
		boolean[] visited = new boolean[points.length];
		int currentConstellation = 1;
		for (int i = 0; i < points.length; i++)
		{
			if (!visited[i])
			{
				constellations[i] = currentConstellation;
				Queue<Integer> processingQueue = new LinkedList<Integer>();
				processingQueue.add(i);

				while (!processingQueue.isEmpty())
				{
					int index = processingQueue.poll();
					Vector point = points[index];
					if (!visited[index])
					{
						visited[index] = true;
						for (int j = 0; j < points.length; j++)
						{
							Vector other = points[j];
							if (j != index &&
									point.manhattanDistanceTo(other) <= 3)
							{
								System.out.println(point + " " + other + " -> " + point.manhattanDistanceTo(other) + " " + currentConstellation);

								constellations[j] = currentConstellation;
								processingQueue.add(j);
							}
						}
					}
				}

				currentConstellation++;
			}

		}

		HashSet<Integer> constellationSet = new HashSet<Integer>();
		for (int i = 0; i < constellations.length; i++)
		{
			if(constellations[i] > 0 &&
					!constellationSet.contains(constellations[i]))
			{
				constellationSet.add(constellations[i]);
			}
			else if (constellations[i] == 0)
			{
				System.out.println(points[i] + " is not in constellation");
			}
		}
		System.out.println("There are " + constellationSet.size() + " constellations");

		System.out.println(points.length + " points in input");
	}

	private static void solveProblem2(String[] lines)
	{
		// TODO
	}
}

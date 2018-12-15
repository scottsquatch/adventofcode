import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class AdventOfCodeDay10
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("Usage: AdventOfCodeDay10 <input> "); 
			System.out.println("Where <input> is a text file containing lights ");
		}
		else
		{
			String inputFilePath = args[0];
			
			try
			{
				BufferedReader input = new BufferedReader(new FileReader(inputFilePath));
				ArrayList<String> lines = new ArrayList<String>();
				while (input.ready())
				{
					lines.add(input.readLine());
				}

				solveProblem1(lines);
				solveProblem2(lines);

				input.close();
			}
			catch (FileNotFoundException e)
			{
				System.err.println("Cannot find file " + inputFilePath + " please verify the path is correct");
			}
			catch (IOException e)
			{
				System.err.println("Exception occured: " + e.toString());
				e.printStackTrace();
			}
		}

	}

	private static void solveProblem1(ArrayList<String> lines)
	{
		/*Sky sky = new Sky();
		for (String line : lines)
		{
			sky.addLight(Light.parse(line));
		}

		for (int i = 0; i < seconds; i++)
		{
			sky.passTime(1);
			sky.print();
		}*/

		ArrayList<Light> lights = new ArrayList<Light>();
		for (String line : lines)
		{
			lights.add(Light.parse(line));
		}
		Sky sky = new Sky(lights);
		//sky.run(true);
		/*for (int i = 0; i < seconds; i++)
		{
			sky.passTime(1);
			sky.print();
		}*/
		sky.getLetters(false);
	}


	private static void solveProblem2(ArrayList<String> lines)
	{
		// TODO
	}
}

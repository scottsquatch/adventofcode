import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.LinkedList;

public class AdventOfCodeDay19
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: AdventOfCodeDay19 <input>");
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

				String[] lineArr = lines.toArray(new String[lines.size()]);
				solveProblem1(lineArr);
				solveProblem2(lineArr);

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
		WristDeviceEmulator emulator = new WristDeviceEmulator(lines);

		RegisterState state = emulator.run();

		System.out.println(state);
		System.out.println("Value of register 0 -> " + state.getRegisterValue(0));
	}

	private static void solveProblem2(String[] trainingLines)
	{
		// TODO			
	}
}


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

	private static void solveProblem2(String[] lines)
	{
		// Very customized solution done after "decoding" the elfcode
		// The code sums the divisors, but does so in O(N^2) by using double loop.
		// Here is the "literal" translation
		// int r1 = 983; // Too lazy to figure out the logic to determine this, but by the time we get to the "function", this has already been set.
		// for (int r2 = 1; r2 <= r1; r2++)
		// {
		//   for (int r4 = 1; r4 <= r1; r4++)
		//   {
		//     r3 = r2 * r4;
		//     if (r3 == r1)
		//     {
		//       r0 += r2;
		//     }
		//   }
		// }

		int initialR0 = 1;
		long value = 0;

		value += 2;
		value *= value;
		value *= 19;
		value *= 11;
		int r3 = 6;
		r3 *= 22;
		r3 += 15;
		value += r3;

		if (initialR0 > 0)
		{
			if (initialR0 == 1)
			{
				r3 = 27;
			}

			if (initialR0 <= 2)
			{
				r3 *= 28;
			}

			if (initialR0 <= 3)
			{
				r3 += 29;
			}

			if (initialR0 <= 4)
			{
				r3 *= 30;
			}

			if (initialR0 <= 5)
			{
				r3 *= 14;
			}

			if (initialR0 <= 6)
			{
				r3 *= 32;
			}

			if (initialR0 <= 7)
			{
				value += r3;
			}
		}


		// Using https://www.geeksforgeeks.org/sum-of-all-proper-divisors-of-a-natural-number/ as a guide for an efficient algorithm to compute this
		// long value = 10551381L; // Determined by printing out values (don't judge, it was quicker than figuring out the logic)
		// long value = 983L;
		int sum = 0;

		// All divisors are less than or equal to sqrt(num)
		int loopmax = (int)Math.sqrt(value);
		for (int i = 1; i <= loopmax; i++)
		{
			//System.out.println(i + ": value % i = " + value % i);
			// If we have a divisor, then we add the divisor and number / divisor
			if (value % i == 0)
			{
				//System.out.println(i + " " + value / i);
				sum += i + value / i;
			}
		}

		System.out.println("Value of register 0 using deconstructed elfcode is: " + sum);
	}
}

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Hashtable;
import java.util.Map;
import java.io.IOException;
import java.io.FileNotFoundException;

public class CheckSumGenerator
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("Usage: ChecksumGenerator <input>");
			System.out.println("Takes the input file of IDs and generates the checksum. This solves the first problem from the second day of advent of code");
		}
		else
		{
			String inputFile = args[0];
			try
			{
				BufferedReader input = new BufferedReader(
						new FileReader(inputFile));
				
				int numIdsWithExactly2Letters = 0;
				int numIdsWithExactly3Letters = 0;
				while (input.ready())
				{
					String currentLine = input.readLine().toLowerCase();

					Hashtable<Character, Integer> letterCountTable = new Hashtable<Character, Integer>();
					for (int i = 0; i < currentLine.length(); i++)
					{
						char currentChar = currentLine.charAt(i);

						int currentCount;
						if (!letterCountTable.containsKey(currentChar))
						{
							currentCount = 0;
						}
						else
						{
							currentCount = letterCountTable.get(currentChar);
						}

						letterCountTable.put(currentChar, ++currentCount);
					}
					
					boolean hasLetterWith2Occurences = false;
					boolean hasLetterWith3Occurences = false;
					for (Character key : letterCountTable.keySet())
					{
						Integer count = letterCountTable.get(key);
						if (count == 2)
						{
							hasLetterWith2Occurences = true;
						}
						else if (count == 3)
						{
							hasLetterWith3Occurences = true;
						}

						if (hasLetterWith2Occurences &&
								hasLetterWith3Occurences)
						{
							// We have found both, no need to keep checking
							break;
						}
					}

					if (hasLetterWith2Occurences)
					{
						numIdsWithExactly2Letters++;
					}

					if (hasLetterWith3Occurences)
					{
						numIdsWithExactly3Letters++;
					}	

				}

				System.out.println("Checksum is " + (numIdsWithExactly2Letters * numIdsWithExactly3Letters));

				input.close();

			} catch (FileNotFoundException e)
			{
				System.err.println("Can not file file " + inputFile + " please make sure the path is correct");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}

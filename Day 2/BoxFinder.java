import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class BoxFinder
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("Usage: BoxFinder <input>");
			System.out.println("Takes the input file of IDs and generates the checksum. This solves the first problem from the second day of advent of code");
		}
		else
		{
			String inputFile = args[0];
			try
			{
				BufferedReader input = new BufferedReader(
						new FileReader(inputFile));
				
				ArrayList<String> boxIds = new ArrayList<String>();
				while (input.ready())
				{
					String currentLine = input.readLine().toLowerCase();
					
					boxIds.add(currentLine);
				}

				input.close();

				String[] correctBoxIds = new String[2];
				int numIds = boxIds.size();
				boolean differenceFound = false;
				for (int i = 0; i < numIds; i++)
				{
					String firstId = boxIds.get(i);
					int idLength = firstId.length();
					for (int j = i; j < numIds; j++)
					{
						String secondId = boxIds.get(j);

						// We are assuming the lengths are the same, this is verified by looking at the data
						differenceFound = false;
						for (int k = 0; k < idLength; k++)
						{
							char firstIdChar = firstId.charAt(k);
							char secondIdChar = secondId.charAt(k);
							if (firstIdChar != secondIdChar)
							{
								if (differenceFound)
								{
									// We have already found a difference, break
									differenceFound = false;
									break;
								}
								else
								{
									differenceFound = true;
								}
							}

						}
						
						if (differenceFound)
						{
							correctBoxIds[0] = firstId;
							correctBoxIds[1] = secondId;
						}
					}

					if (differenceFound)
					{
						// No need to continue
						break;
					}
				}

				System.out.println("Correct Box Ids");
				System.out.println(correctBoxIds[0]);
				System.out.println(correctBoxIds[1]);

				int idLength = correctBoxIds[0].length();
				StringBuilder commonCharacters = new StringBuilder();
				for (int i = 0; i < idLength; i++)
				{
					char firstIdChar = correctBoxIds[0].charAt(i);
					char secondIdChar = correctBoxIds[1].charAt(i);

					if (firstIdChar == secondIdChar)
					{
						commonCharacters.append(firstIdChar);
					}
					
				}

				System.out.println("Common characters are: " + commonCharacters.toString());
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

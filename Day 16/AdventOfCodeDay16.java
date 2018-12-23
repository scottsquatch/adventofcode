import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

public class AdventOfCodeDay16
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: AdventOfCodeDay16 <input>");
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

				// Split into two lists
				ArrayList<String> cpuMonitoringTraceLines = new ArrayList<String>();
				ArrayList<String> programLines = new ArrayList<String>();

				boolean parsingCPUMonitoringTraces = true;
				boolean inNewLine = false;
				for (int i = 0; i < lines.size(); i++)
				{
					String line = lines.get(i).trim();	
					if (line.length() > 0)
					{
						inNewLine = false;
						if (parsingCPUMonitoringTraces)
						{
							cpuMonitoringTraceLines.add(line);
						}
						else
						{
							programLines.add(line);
						}
					}
					else
					{
						if (inNewLine && parsingCPUMonitoringTraces)
						{
							// Found multiple newLines, store in scond section
							parsingCPUMonitoringTraces = false;
						}
						else if (!inNewLine)
						{
							inNewLine = true;
						}
					}
				}


				solveProblem1(cpuMonitoringTraceLines.toArray(new String[cpuMonitoringTraceLines.size()]));

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
		CPUTraceLogParser parser = new CPUTraceLogParser(lines);
		MatchingInstructionFinder analyzer = new MatchingInstructionFinder();
		int numSamplesMatching3OpCodes = 0;
		for (CPUMonitorTrace t : parser.getTraces())
		{
			OperationType[] matchingTypes = analyzer.getMatchingTypes(t.getBefore(), t.getInstruction(), t.getAfter());	

			if (matchingTypes.length > 2)
			{
				numSamplesMatching3OpCodes++;
			}
		}

		System.out.println("The number of samples which match 3 or more op codes is: " + numSamplesMatching3OpCodes);
	}

}


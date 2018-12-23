import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.LinkedList;

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


				String[] cpuMonitoringTraceLinesArray = cpuMonitoringTraceLines.toArray(new String[cpuMonitoringTraceLines.size()]);
				solveProblem1(cpuMonitoringTraceLinesArray);
				solveProblem2(cpuMonitoringTraceLinesArray, programLines.toArray(new String[programLines.size()]));

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

	private static void solveProblem2(String[] trainingLines, String[] programToRun)
	{
		CPUTraceLogParser parser = new CPUTraceLogParser(trainingLines);
		MatchingInstructionFinder analyzer = new MatchingInstructionFinder();

		// We will use the "Training Lines" to map the opcode to the operation
		ArrayList<OperationType>[] workingOpCodeMap = new ArrayList[16];
		HashSet<OperationType> knownOpCodes = new HashSet<OperationType>();
		// Initialize array
		for (int i = 0; i < workingOpCodeMap.length; i++)
		{
			workingOpCodeMap[i] = new ArrayList<OperationType>();
		}

		for (CPUMonitorTrace t : parser.getTraces())
		{
			int opCode = t.getInstruction().getOpCode();
			ArrayList<OperationType> possibleOperations = workingOpCodeMap[opCode];
			if (knownOpCodes.contains(opCode))
			{
				// Do nothing
			}
			else if (possibleOperations.isEmpty())
			{
				// First time we have encountered this op code, fill the possibilites
				for (OperationType opType : analyzer.getMatchingTypes(t.getBefore(), t.getInstruction(), t.getAfter()))
				{
					possibleOperations.add(opType);
				}
			}
			else if (possibleOperations.size() > 1)
			{
				// We have encountered this before, let's remove any op codes which are not in the current list
				OperationType[] types = analyzer.getMatchingTypes(t.getBefore(), t.getInstruction(), t.getAfter());
				ListIterator<OperationType> iterator = possibleOperations.listIterator();
				while (iterator.hasNext())
				{
					OperationType candidateType = iterator.next();
					boolean matchFound = false;
					for (OperationType type : types)
					{
						if (type == candidateType)
						{
							matchFound = true; 
							break;
						}
					}

					if (!matchFound)
					{
						iterator.remove();
					}
				}

				// If there is only one item left them we have found a matching op code!
				if (possibleOperations.size() == 1)
				{
					OperationType opType = possibleOperations.get(0);

					// Add to known op code
					knownOpCodes.add(opType);

					// Remove this op code from all lists
					for (int j = 0; j < workingOpCodeMap.length; j++)
					{
						if (j != opCode)
						{
							knownOpCodes.remove(opType);
						}
					}
				}
			}
		}

	
		// We now have the actual map, fill it up
		OperationType[] opCodeMap = new OperationType[16];
		LinkedList<Integer> queue = new LinkedList<Integer>();
		boolean[] visited = new boolean[16];
		for (int i = 0; i < workingOpCodeMap.length; i++)
		{
			visited[i] = false;
			queue.add(i);
		}

		while(!queue.isEmpty())
		{
			int currentOpCode = queue.remove();
			
			if (!visited[currentOpCode])
			{
				if (workingOpCodeMap[currentOpCode].size() == 1)
				{
					visited[currentOpCode] = true;
					opCodeMap[currentOpCode] = workingOpCodeMap[currentOpCode].get(0);
					knownOpCodes.add(workingOpCodeMap[currentOpCode].get(0));
				}
				else 
				{
					// try to remove the known op codes and add back to queue
					workingOpCodeMap[currentOpCode].removeAll(knownOpCodes);

					queue.add(currentOpCode);
				}
			}
						
		}

		// Print for testing
		for (int i = 0; i < opCodeMap.length; i++)
		{
			System.out.println(i + " -> " + opCodeMap[i].getName());
		}	

		// Make sure we don't have multiple mappings
		for (int i = 0; i < opCodeMap.length - 1; i++)
		{
			for (int j = i + 1; j < opCodeMap.length; j++)
			{
				assert(opCodeMap[i] != opCodeMap[j]);
			}
		}


		// Now go through the program list and process
		RegisterState state = new RegisterState(0,0,0,0);
		for (int i = 0; i < programToRun.length; i++)
		{
			Instruction instr = Instruction.parseInstruction(programToRun[i]);
			
			OperationType ot = opCodeMap[instr.getOpCode()];

			state = OperationType.applyOperation(ot, state, instr);
		}

		System.out.println(state);
				
	}
}


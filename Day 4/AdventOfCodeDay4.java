import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.PriorityQueue;
import java.util.Date;

public class AdventOfCodeDay4
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("AdventOfCodeDay4 <input>");
			System.out.println("where <input> is the input file for the problem");
		}
		else
		{
			String inputFile = args[0];

			try
			{
				BufferedReader input = new BufferedReader(
						new FileReader(inputFile));
				
				PriorityQueue<LogEntry> pq = new PriorityQueue<LogEntry>(100, new LogEntryComparator());

				while (input.ready())
				{
					String currentLine = input.readLine();
					try
					{

						LogEntry entry = LogEntry.parseLogEntry(currentLine);

						pq.add(entry);
					}
					catch (ParseException e)
					{
						System.err.println("Exception parsing line " + currentLine + ". " + e.toString());
					}
				}

				GuardSleepLogs sleepLogs = new GuardSleepLogs(pq);
				SleepyGuardInfo sleepiestGuard = sleepLogs.getSleepiestGuard();

				System.out.println("Sleepiest Guard ID: " + sleepiestGuard.getGuardId());
				System.out.println("Sleepiest minute: " + sleepiestGuard.getSleepiesMinute());
				System.out.println("Problem Answer: " + (sleepiestGuard.getGuardId() *sleepiestGuard.getSleepiesMinute()));
				input.close();
			}
			catch (FileNotFoundException e)
			{
				System.err.println("Cannot find file " + inputFile + " please double check the path");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}

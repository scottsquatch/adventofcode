import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Comparator;

public class LogEntry
{
	// Private members
	private Date mDate;
	private String mEntryText;
	private LogEntryType mEntryType;

	private static final SimpleDateFormat LOG_DATE_FORMAT =  new SimpleDateFormat("yyyy-MM-dd HH:mm");  
	private static final char DATE_START_CHAR = '[';
	private static final char DATE_END_CHAR = ']';
	private static final String WAKE_UP_TEXT = "wakes up";
	private static final String FALL_ASLEEP_TEXT = "falls asleep";

	public static LogEntry parseLogEntry(String logEntry)
		throws ParseException
	{
		LogEntry newLogEntry = new LogEntry();
		StringBuilder builder = null;
		for (int i = 0; i < logEntry.length(); i++)
		{
			char c = logEntry.charAt(i);

			if (c == DATE_START_CHAR)
			{
				builder = new StringBuilder();
			}
			else if (c == DATE_END_CHAR)
			{
				newLogEntry.mDate = LOG_DATE_FORMAT.parse(builder.toString());
				builder = new StringBuilder();
			}
			else
			{
				builder.append(c);
			}
		}
		
		newLogEntry.mEntryText = builder.toString().trim();

		// Determine Type
		if (newLogEntry.mEntryText.equals(WAKE_UP_TEXT))
		{
			newLogEntry.mEntryType = LogEntryType.WAKE_UP;
		}
		else if (newLogEntry.mEntryText.equals(FALL_ASLEEP_TEXT))
		{
			newLogEntry.mEntryType = LogEntryType.FALL_ASLEEP;
		}
		else
		{
			newLogEntry.mEntryType = LogEntryType.SHIFT_START;
		}

		return newLogEntry;
	}

	public Date getDate()
	{
		return mDate;
	}

	public String getEntryText()
	{
		return mEntryText;
	}

	public LogEntryType getEntryType()
	{
		return mEntryType;
	}
}

class LogEntryComparator implements Comparator<LogEntry>
{
	@Override
	public int compare(LogEntry entry1, LogEntry entry2)
	{
		return entry1.getDate().compareTo(entry2.getDate());
	}
}


enum LogEntryType
{
	SHIFT_START, FALL_ASLEEP, WAKE_UP
}

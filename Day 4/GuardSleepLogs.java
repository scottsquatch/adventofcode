import java.util.Hashtable;
import java.util.PriorityQueue;

public class GuardSleepLogs
{
    private Hashtable<Integer, SleepLog> mSleepLogsByGuardId;

    public GuardSleepLogs(PriorityQueue<LogEntry> chronologicalLogs)
    {
        mSleepLogsByGuardId = new Hashtable<Integer, SleepLog>();

        initialize(chronologicalLogs);
    }

    private void initialize(PriorityQueue<LogEntry> chronologicalLogs)
    {
        boolean isAsleep = false;
        LogEntry previousLogEntry = null;
        int currentGuardId = 0;
        while (!chronologicalLogs.isEmpty())
        {
            LogEntry currentLogEntry = chronologicalLogs.poll();

            LogEntryType type = currentLogEntry.getEntryType();
            if (type == LogEntryType.FALL_ASLEEP)
            {
                isAsleep = true;
            }
            else if (type == LogEntryType.WAKE_UP)
            {
                if (previousLogEntry != null)
                {
                    mSleepLogsByGuardId.get(currentGuardId).addSleepTime(
                        previousLogEntry.getDate().getMinutes(), 
                        currentLogEntry.getDate().getMinutes());
                }

                isAsleep = false;
            }
            else if (type == LogEntryType.SHIFT_START)
            {
                if (previousLogEntry != null &&
                    isAsleep)
                {
                    mSleepLogsByGuardId.get(currentGuardId).addSleepTime(
                        previousLogEntry.getDate().getMinutes(), 
                        60);
                }

                isAsleep = false;
                currentGuardId = getIdFromTextEntry(currentLogEntry.getEntryText());
                if (!mSleepLogsByGuardId.containsKey(currentGuardId))
                {
                    mSleepLogsByGuardId.put(currentGuardId, new SleepLog());
                }
            }

            previousLogEntry = currentLogEntry;
        }
    }

    private int getIdFromTextEntry(String textEntry)
    {
        int id = 0;

        int idStartIndex = textEntry.indexOf("#");

        if (idStartIndex > -1)
        {
            StringBuilder idBuilder = new StringBuilder();
            int index = idStartIndex + 1;
            char currentChar = textEntry.charAt(index);
            do
            {
                idBuilder.append(currentChar);
                currentChar = textEntry.charAt(++index);
            } while (index < (textEntry.length() - 1) &&
                Character.isDigit(currentChar));

            id = Integer.parseInt(idBuilder.toString());
        }

        return id;
    }

    public SleepyGuardInfo getSleepiestGuard()
    {
        int sleepiestGuardId = 0;
        int highestSleepTime = Integer.MIN_VALUE;
        int sleepiestMinute = Integer.MIN_VALUE;
        int timeAsleepInSleepiestMinute = Integer.MIN_VALUE;
        for (int id : mSleepLogsByGuardId.keySet())
        {
            SleepLog currentSleepLog = mSleepLogsByGuardId.get(id);

            if (currentSleepLog.getTotalMinutesAsleep() > highestSleepTime)
            {
                sleepiestGuardId = id;
                highestSleepTime = currentSleepLog.getTotalMinutesAsleep();
                sleepiestMinute = currentSleepLog.getSleepiestMinute();
                timeAsleepInSleepiestMinute = currentSleepLog.getLargestSleepInMinute();
            }
        }

        SleepyGuardInfo info = new SleepyGuardInfo(sleepiestGuardId, highestSleepTime, sleepiestMinute, timeAsleepInSleepiestMinute);

        return info;
    }

    public SleepyGuardInfo getSleepyGuardUsingMostTimeAsleepInSleepiestMinute()
    {
        int sleepiestGuardId = 0;
        int highestSleepTime = Integer.MIN_VALUE;
        int sleepiestMinute = Integer.MIN_VALUE;
        int timeAsleepInSleepiestMinute = Integer.MIN_VALUE;
        for (int id : mSleepLogsByGuardId.keySet())
        {
            SleepLog currentSleepLog = mSleepLogsByGuardId.get(id);

            if (currentSleepLog.getLargestSleepInMinute() > timeAsleepInSleepiestMinute)
            {
                sleepiestGuardId = id;
                highestSleepTime = currentSleepLog.getTotalMinutesAsleep();
                sleepiestMinute = currentSleepLog.getSleepiestMinute();
                timeAsleepInSleepiestMinute = currentSleepLog.getLargestSleepInMinute();
            }
        }

        SleepyGuardInfo info = new SleepyGuardInfo(sleepiestGuardId, highestSleepTime, sleepiestMinute, timeAsleepInSleepiestMinute);

        return info;
    }
}

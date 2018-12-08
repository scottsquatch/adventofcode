# Problem Analysis

## Problem 1

### Given
1) List of log entries that are not ordered.
2) each log entry consists of date and message. Message is one of three forms
    a) Guard #{id} begins shift -> Denotes that a new guard has started their shift
    b) wakes up -> Denotes guard has woken up. This corresponds to the guard which had most recently started their shift
    c) falls asleep -> Denotes the guard has fallen asleep. This correcsponds to the guard which had most recently started their shift

### Want to know
1) The id of the guard which has the most minutes asleep
2) The minute for which the aforementioned guard is asleep the most

### Algorithm
1) Read the log entries from the file
    a) As we read the lines, put the entries into a sorted collection using the date of the log as the sort vairable.
2) Iterate over the logs in order
    a) Three cases:
        i) ShiftStart 
            I) If guard was  previously asleep, log minutes from previousEntry to mminute 59 as shifts start once a day.
            II) Consider the guard awake
        ii) WakesUp
            I) If guard was previously asleep, log minutes from previousEntryMinute to the current entry minute, exclusive.
            II) Consider the guard awake
        iii) FallsAsleep
            I) Consider the guard asleep


### Classes
LogEntry -> Encapsulate Log Entry Info
public:
getDate()
getEntryText()
getType()

LogEntryComparator : Comparator<LogEntry> -> Compares LogEntries by date only

SleepyGuardInfo
public:
getId()
getTotalMinutesAsleep()

SleepLog:
private:
Integer[] sleepLog // Index is Minute, Value is number of shifts for which the guard was asleep at this minute
int mSleepiestMinute;
int mTotalMinutesSlept
public:
getSleepiestMinute()
getTotalMinutesAsleep()
addSleepTime(startMinute, endMinute)

GuardSleepLogs -> Stoes all of the sleep logs for the guards
private:
HashSet<String, SleepLog> mSleepLogsByGuard
public:
addSleepTime(id, startMinute, endMinute)
getSleepiestGuard()

## Problem 2
The only difference here is that we need to use different criterion for obtaining the selected guard and minute.

### Changes
* Track the time spent in the sleepiest minute in SleepLog
* Save the time spent in the sleepiest minute in SleepyGuardInfo
* In GuardsSleepLog, develop another method to choose the guard based on the largest number of times sleeping in sleepiest minute
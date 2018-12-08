public class SleepLog
{
    private int[] mTimesAsleepPerMinute;

    // Keep a running total so we don't have to waste time calculating after filling array
    private int mSleepiestMinute;
    private int mTotalMinutesSlept;
    private int mLargestSleepInMinute;

    public SleepLog()
    {
        // One entry per minute
        mTimesAsleepPerMinute = new int[60];
        mSleepiestMinute = 0;
        mTotalMinutesSlept = 0;
        mLargestSleepInMinute = 0;
    }

    // Getter Meethods
    public int getSleepiestMinute()
    {
        return mSleepiestMinute;
    }

    public int getTotalMinutesAsleep()
    {
        return mTotalMinutesSlept;
    }

    public int getLargestSleepInMinute()
    {
        return mLargestSleepInMinute;
    }

    // Add sleep time starting from startMinute to endMinute, exclusive
    public void addSleepTime(int startMinute, int endMinute)
    {
        for (int i = startMinute; i < endMinute; i++)
        {
            mTotalMinutesSlept++;
            mTimesAsleepPerMinute[i]++;

            if (mTimesAsleepPerMinute[i] > mLargestSleepInMinute)
            {
                mLargestSleepInMinute = mTimesAsleepPerMinute[i];
                mSleepiestMinute = i;
            }
        }
    }
}
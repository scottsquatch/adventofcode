public  class SleepyGuardInfo
{
    // Members
    private int mGuardId;
    private int mTotalMinutesAsleep;
    private int mSleepiestMinute;
    private int mTimesAsleepInSleepiestMinute;

    public SleepyGuardInfo(int id, int totalMinutesAsleep, int sleepiestMinute, int timesAsleepInSleepiestMinute)
    {
        mGuardId = id;
        mTotalMinutesAsleep = totalMinutesAsleep;
        mSleepiestMinute = sleepiestMinute;
        mTimesAsleepInSleepiestMinute = timesAsleepInSleepiestMinute;
    }

    public int getGuardId()
    {
        return mGuardId;
    }

    public int getTotalMinutesAsleep()
    {
        return mTotalMinutesAsleep;
    }

    public int getSleepiesMinute()
    {
        return mSleepiestMinute;
    }

    public int getTimesAsleepInSleepiestMinute()
    {
        return mTimesAsleepInSleepiestMinute;
    }
}
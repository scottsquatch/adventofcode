public  class SleepyGuardInfo
{
    // Members
    private int mGuardId;
    private int mTotalMinutesAsleep;
    private int mSleepiestMinute;

    public SleepyGuardInfo(int id, int totalMinutesAsleep, int sleepiestMinute)
    {
        mGuardId = id;
        mTotalMinutesAsleep = totalMinutesAsleep;
        mSleepiestMinute = sleepiestMinute;
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
}
public class FuelCell
{
  private int powerLevel;

  public FuelCell(int x, int y, int gridSerialNumber)
  {
    long rackID = x + 10;

    long workingPowerLevel = rackID * y;
    workingPowerLevel += gridSerialNumber;
    workingPowerLevel *= rackID;

    // To get the 100's digit we use a little trick here.
    // Since we are using integer division, we automatically truncate decimal values
    // So division can "cuttoff" numbers in that sense
    int hundredsDigit = 0;
    if (workingPowerLevel > 99)
    {
      Long truncatedLevel = workingPowerLevel / 1000;
      int remainingPowerLevel = (int)(workingPowerLevel - truncatedLevel * 1000);
      hundredsDigit = remainingPowerLevel/ 100;
    }

    powerLevel = hundredsDigit - 5;
  }

  public int getPowerLevel()
  {
    return powerLevel;
  }
}

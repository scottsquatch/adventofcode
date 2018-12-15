public class AdventOfCodeDay11
{
  public static void main(String[] args)
  {
    if (args.length != 1)
    {
      System.out.println("Usage: AdventOfCodeDay11 <gridSerialNumber>");
    }
    else
    {
      int gridSerialNumber = Integer.parseInt(args[0]);

      PowerGrid grid = new PowerGrid(300, 300, gridSerialNumber);
      LargestTotalPowerResult result = grid.getLargestTotalPower(3, 3);
      System.out.println("Start of box with largest total power for grid size 3: " +
        result.topLeftCoordinate);

      int maxTotalPower = Integer.MIN_VALUE;
      LargestTotalPowerResult maxResult = null;
      int sizeForMaxTotalPower = -1;
      for (int size = 1; size <= 300; size++)
      {
          LargestTotalPowerResult resultForSize = grid.getLargestTotalPower(size, size);

          if (resultForSize.totalPower > maxTotalPower)
          {
            sizeForMaxTotalPower = size;
            maxTotalPower = resultForSize.totalPower;
            maxResult = resultForSize;
          }
      }

      System.out.println("String for box with largest maxPower: " + maxResult.topLeftCoordinate +
        "," + sizeForMaxTotalPower);
    }
  }
}

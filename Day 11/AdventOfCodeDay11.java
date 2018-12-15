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
      System.out.println("Start of box with largest total power: " +
        grid.getLargestTotalPower(3, 3));
    }
  }
}

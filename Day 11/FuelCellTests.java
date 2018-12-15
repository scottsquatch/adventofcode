public class FuelCellTests
{
  public static void main(String[] args)
  {
    System.out.println("Starting tests for FuelCell class");
    testGetPowerLevel();
    System.out.println("All tests passed!");
  }

  private static void testGetPowerLevel()
  {
    // Positive powerLevel (max)
    assert(4 == new FuelCell(101, 153, 71).getPowerLevel());

    // Negative powerLevel (min)
    assert(-5 == new FuelCell(122, 79, 57).getPowerLevel());

    // Zero powerLevel
    assert(0 == new FuelCell(217, 196, 39).getPowerLevel());
  }
}

public class PowerGridTests
{
  public static void main(String[] args)
  {
    System.out.println("Starting tests for PowerGrid class...");
    testGetLargestTotalPower();
    System.out.println("All tests passed!");
  }

  private static void testGetLargestTotalPower()
  {
    assert("33,45".equals(new PowerGrid(300, 300, 18).getLargestTotalPower(3, 3)));
    assert("21,61".equals(new PowerGrid(300, 300, 42).getLargestTotalPower(3, 3)));
  }
}

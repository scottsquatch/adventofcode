public class PotCollectionTester
{
	public static void main(String[] args)
	{
		System.out.println("Testing PotCollection...");
		testGetPotText();
		testFillPot();
		testReset();
		testGetLeftmostPotNumber();
		testGetRightmostPotNumber();
		System.out.println("All tests passed!");
	}

	private static void testGetPotText()
	{
		PotCollection pots = new PotCollection("#..#..#");

		assert("#..#..#".equals(pots.getPotText(0, 7)));
	}

	private static void testFillPot()
	{
		PotCollection pots = new PotCollection("#..#..#");
		pots.setPotFilled(1, true);

		assert("##.#..#".equals(pots.getPotText(0,7)));
	}

	private static void testReset()
	{
		PotCollection pots = new PotCollection("#..#..#");

		pots.setPotFilled(1, true);

		assert("##.#..#".equals(pots.getPotText(0,7)));

		pots.reset();

		assert("#..#..#".equals(pots.getPotText(0, 7)));
	}


	private static void testGetLeftmostPotNumber()
	{
		PotCollection pots = new PotCollection(".#..#");

		assert(-4 == pots.getLeftmostPotNumber());
	}

	private static void testGetRightmostPotNumber()
	{
		PotCollection pots = new PotCollection("#...#..");

		assert(9  == pots.getRightmostPotNumber());
	}
}

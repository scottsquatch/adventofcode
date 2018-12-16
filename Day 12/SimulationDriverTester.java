public class SimulationDriverTester
{
	public static void main(String[] args)
	{
		System.out.println("Testing SimulationDriver...");
		testSumFilledPots();
		testIncreaseGeneration();
		System.out.println("All tests passed!");
	}

	private static void testSumFilledPots()
	{
		SimulationDriver driver = new SimulationDriver("#..#", new Rules());

		assert(3 == driver.sumFilledPots());
	}

	private static void testIncreaseGeneration()
	{
		Rules rules = new Rules();
		// .....##...#.....
		// .....
		rules.addRule("..... => .");
		//  ....#
		rules.addRule("....# => .");
		//   ...##
		rules.addRule("...## => .");
		//    ..##.
		rules.addRule("..##. => #");
		//     .##..
		rules.addRule(".##.. => .");
		//      ##...
		rules.addRule("#.... => .");
		//       #...#
		rules.addRule("....# => .");
		//        ...#.
		rules.addRule("...#. => .");
		//         ..#..
		rules.addRule("..#.. => #");
		//          .#...
		rules.addRule(".#... => .");
		//           #....
		//            .....

		SimulationDriver driver = new SimulationDriver("##...#", rules);

		driver.increaseGeneration();
		
		// 0
		// #....#
		assert(5 == driver.sumFilledPots());
	}
}

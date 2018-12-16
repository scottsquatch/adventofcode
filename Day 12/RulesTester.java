public class RulesTester
{
	public static void main(String[] args)
	{
		System.out.println("Testing Rules class...");
		testFillCurrentPot();
		System.out.println("All tests passed!");
	}

	private static void testFillCurrentPot()
	{
		Rules rules = new Rules();

		rules.addRule("##.## => #");
		rules.addRule("##..# => .");

		assert(rules.fillCurrentPot("##.##"));
		assert(!rules.fillCurrentPot("##..#"));

	}
}



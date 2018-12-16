import java.util.Hashtable;

public class SimulationDriver
{
	private Rules rules;
	private PotCollection pots;

	public SimulationDriver(String initialState, Rules rules)
	{
		this.rules = rules;
		this.pots = new PotCollection(initialState);
		//System.out.println(pots);
	}

	public void increaseGeneration()
	{
		int leftmostPotNumber = pots.getLeftmostPotNumber();
		int rightmostPotNumber = pots.getRightmostPotNumber();

		// Keep track of changes to perform so that we do not affect the generation while we are iterating over it.
		Hashtable<Integer, Boolean> changeTable = new Hashtable<Integer, Boolean>();
		for (int i = leftmostPotNumber; i <= rightmostPotNumber - 5; i++)
		{
			int currentPotNumber = i + 2;
			String potText = pots.getPotText(i, 5);
			//System.out.print("applying rule " + potText);
			boolean fillPot = rules.fillCurrentPot(potText);
			//System.out.println(", result was " + fillPot);
			if (fillPot != pots.getPot(currentPotNumber).getIsFilled())
			{
				changeTable.put(currentPotNumber, fillPot);
			}
		}

		for (Integer potNumber : changeTable.keySet())
		{
			pots.setPotFilled(potNumber, changeTable.get(potNumber));
		}
		
		//System.out.println("\n" + pots);
	}

	public int sumFilledPots()
	{
		int leftmostPotNumber = pots.getLeftmostPotNumber();
		int rightmostPotNumber = pots.getRightmostPotNumber();
		int sum = 0;
		for (int i = leftmostPotNumber; i <= rightmostPotNumber; i++)
		{
			Pot p = pots.getPot(i);

			if (p.getIsFilled())
			{
				sum += i;
				//System.out.println("adding " + i + " as it is filled");
			}
		}

		return sum;
	}

	public void reset()
	{
		pots.reset();
	}
}
